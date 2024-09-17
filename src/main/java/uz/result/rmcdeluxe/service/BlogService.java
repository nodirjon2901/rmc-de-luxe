package uz.result.rmcdeluxe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.result.rmcdeluxe.entity.Blog;
import uz.result.rmcdeluxe.entity.BlogOption;
import uz.result.rmcdeluxe.entity.BlogType;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.Translation;
import uz.result.rmcdeluxe.payload.blog.*;
import uz.result.rmcdeluxe.repository.BlogOptionRepository;
import uz.result.rmcdeluxe.repository.BlogRepository;
import uz.result.rmcdeluxe.repository.BlogTypeRepository;
import uz.result.rmcdeluxe.repository.PhotoRepository;
import uz.result.rmcdeluxe.util.SlugUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogOptionRepository optionRepository;

    private final BlogTypeRepository typeRepository;

    private final PhotoRepository photoRepository;

    private final BlogRepository blogRepository;

    private final PhotoService photoService;

    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(BlogService.class);

    public ResponseEntity<ApiResponse<BlogResponseDTO>> create(String json, MultipartHttpServletRequest request) {
        ApiResponse<BlogResponseDTO> response = new ApiResponse<>();
        try {
            BlogCreateDTO createDTO = objectMapper.readValue(json, BlogCreateDTO.class);
            Iterator<String> fileNames = request.getFileNames();
            Blog blog = new Blog(createDTO);
            if (createDTO.getOptions() != null && !createDTO.getOptions().isEmpty()) {
                List<BlogOption> blogOptions = new ArrayList<>();
                for (BlogOptionCreateDTO optionDTO : createDTO.getOptions()) {
                    BlogOption option = new BlogOption(optionDTO);
                    option.setBlog(blog);
                    blogOptions.add(option);
                }
                blog.setOptions(blogOptions);
            }
            while (fileNames.hasNext()) {
                String key = fileNames.next();
                MultipartFile photo = request.getFile(key);
                setBlogPhoto(key, photo, blog);
            }

            blog.setActive(true);
            blog.setType(typeRepository.findById(createDTO.getTypeId())
                    .orElseThrow(() -> {
                        logger.warn("Type is not found with id: {}", createDTO.getTypeId());
                        return new NotFoundException("Type is not found with id: " + createDTO.getTypeId());
                    }));

            Blog savedBlog = blogRepository.save(blog);
            String slug = savedBlog.getId() + "-" + SlugUtil.makeSlug(savedBlog.getHeadOption().getDescriptionEn());
            blogRepository.updateSlugById(savedBlog.getId(), slug);
            savedBlog.setSlug(slug);
            response.setData(new BlogResponseDTO(savedBlog));
            response.setMessage("Successfully saved");
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            logger.error("Error processing JSON for blog creation", e);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    private void setBlogPhoto(String key, MultipartFile photo, Blog blog) {
        if (key.equalsIgnoreCase("main-photo")) {
            blog.getHeadOption().setPhoto(photoService.save(photo));
            return;
        }
        int index = Integer.parseInt(key.substring(12)) - 1;
        BlogOption option = blog.getOptions().get(index);
        option.setPhoto(photoService.save(photo));
    }

    public ResponseEntity<ApiResponse<?>> findById(Long id, String lang) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Blog is not found with id: {}", id);
                    return new NotFoundException("Blog is not found with id: " + id);
                });
        if (lang == null || lang.equals("-")) {
            ApiResponse<BlogResponseDTO> response = new ApiResponse<>();
            response.setData(new BlogResponseDTO(blog));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<BlogMapper> response = new ApiResponse<>();
        response.setData(new BlogMapper(blog, lang));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> findBySlug(String slug, String lang) {
        Blog blog = blogRepository.findBySlug(slug)
                .orElseThrow(() -> {
                    logger.warn("Blog is not found with slug: " + slug);
                    return new NotFoundException("Blog is not found with slug: " + slug);
                });
        if (lang == null || lang.equals("-")) {
            ApiResponse<BlogResponseDTO> response = new ApiResponse<>();
            response.setData(new BlogResponseDTO(blog));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<BlogMapper> response = new ApiResponse<>();
        response.setData(new BlogMapper(blog, lang));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<BlogResponseDTO>> update(BlogUpdateDTO updateDTO) {
        ApiResponse<BlogResponseDTO> response = new ApiResponse<>();
        Blog fromDb = blogRepository.findById(updateDTO.getId())
                .orElseThrow(() -> {
                    logger.warn("Blog is not found with id: {}", updateDTO.getId());
                    return new NotFoundException("Blog is not found with id: " + updateDTO.getId());
                });

        if (updateDTO.getHeadOption() != null) {
            BlogOptionResponseDTO headOption = updateDTO.getHeadOption();
            if (headOption.getTitle() != null) {
                Translation title = headOption.getTitle();
                if (title.getEn() != null) {
                    fromDb.getHeadOption().setTitleUz(title.getEn());
                    String slug = fromDb.getId() + "-" + SlugUtil.makeSlug(title.getEn());
                    fromDb.setSlug(slug);
                }
                if (title.getRu() != null) {
                    fromDb.getHeadOption().setTitleRu(title.getRu());
                }
                if (title.getUz() != null) {
                    fromDb.getHeadOption().setTitleUz(title.getUz());
                }
            }
            if (headOption.getDescription() != null) {
                Translation description = headOption.getDescription();
                if (description.getUz() != null) {
                    fromDb.getHeadOption().setDescriptionUz(description.getUz());
                }
                if (description.getRu() != null) {
                    fromDb.getHeadOption().setDescriptionRu(description.getRu());
                }
                if (description.getEn() != null) {
                    fromDb.getHeadOption().setDescriptionEn(description.getEn());
                }
            }
        }
        if (updateDTO.getTypeId() != null) {
            BlogType type = typeRepository.findById(updateDTO.getId())
                    .orElseThrow(() -> {
                        logger.warn("Type is not found with id: " + updateDTO.getTypeId());
                        return new NotFoundException("Type is not found with id: " + updateDTO.getTypeId());
                    });
            fromDb.setType(type);
        }

        if (updateDTO.getOptions() != null) {
            List<BlogOption> fromDbOptions = fromDb.getOptions();
            List<BlogOptionResponseDTO> blogOptions = updateDTO.getOptions();
            List<BlogOption> optionsToRemove = new ArrayList<>();

            for (BlogOptionResponseDTO blogOption : blogOptions) {
                for (BlogOption dbOption : fromDbOptions) {
                    if (blogOption.getId() != null && blogOption.getId().equals(dbOption.getId())) {
                        if (blogOption.getTitle() != null) {
                            Translation title = blogOption.getTitle();
                            if (title.getUz() != null) {
                                dbOption.setTitleUz(title.getUz());
                            }
                            if (title.getRu() != null) {
                                dbOption.setTitleRu(title.getRu());
                            }
                            if (title.getEn() != null) {
                                dbOption.setTitleEn(title.getEn());
                            }
                        }
                        if (blogOption.getDescription() != null) {
                            Translation description = blogOption.getDescription();
                            if (description.getUz() != null) {
                                dbOption.setDescriptionUz(description.getUz());
                            }
                            if (description.getRu() != null) {
                                dbOption.setDescriptionRu(description.getRu());
                            }
                            if (description.getEn() != null) {
                                dbOption.setDescriptionEn(description.getEn());
                            }
                        }
                        if (blogOption.getPhoto() != null && dbOption.getPhoto() == null) {
                            Photo photo = blogOption.getPhoto();
                            if (photo.getId() == null && photo.getHttpUrl() != null) {
                                Photo newPhoto = photoRepository.findByHttpUrl(photo.getHttpUrl())
                                        .orElseThrow(() -> {
                                            logger.warn("Photo is not found with url: {}", photo.getHttpUrl());
                                            return new NotFoundException("Photo is not found with url: " + photo.getHttpUrl());
                                        });
                                dbOption.setPhoto(newPhoto);
                            }
                        }
                        if (blogOption.getPhoto() == null && dbOption.getPhoto() != null) {
                            Photo photo = dbOption.getPhoto();
                            dbOption.setPhoto(null);
                            photoService.delete(photo.getId());
                        }
                        if (blogOption.getPhoto() == null && blogOption.getTitle() == null && blogOption.getDescription() == null) {
                            optionsToRemove.add(dbOption);
                        }
                    }
                    if (blogOption.getId() == null) {
                        BlogOption newBlogOption = new BlogOption(blogOption);
                        if (newBlogOption.getPhoto() != null) {
                            Photo photo = newBlogOption.getPhoto();
                            if (photo.getHttpUrl() != null && photo.getId() == null) {
                                Photo photo1 = photoRepository.findByHttpUrl(photo.getHttpUrl())
                                        .orElseThrow(() -> {
                                            logger.warn("Photo is not found with id: {}", photo.getHttpUrl());
                                            return new NotFoundException("Photo is not found with url: " + photo.getHttpUrl());
                                        });
                                newBlogOption.setPhoto(photo1);
                            }
                        }
                        newBlogOption.setBlog(fromDb);
                        fromDbOptions.add(newBlogOption);
                    }
                }
                for (BlogOption option : optionsToRemove) {
                    fromDbOptions.remove(option);
                    optionRepository.deleteCustom(option.getId());
                }
            }
        }
        response.setData(new BlogResponseDTO(blogRepository.save(fromDb)));
        response.setMessage("Successfully updated");
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<ApiResponse<?>> delete(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Blog is not found with id: {}", id);
                    return new NotFoundException("Blog is not found with id: " + id);
                });
        blogRepository.delete(blog);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
