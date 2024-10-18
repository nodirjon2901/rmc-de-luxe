package uz.result.rmcdeluxe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.result.rmcdeluxe.entity.*;
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
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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
            if (createDTO.getOptions() == null || createDTO.getOptions().isEmpty()) {
                logger.warn("At least one option must be saved");
                throw new NotFoundException("At least one option must be saved");
            }
            Iterator<String> fileNames = request.getFileNames();
            while (fileNames.hasNext()) {
                String key = fileNames.next();
                MultipartFile photo = request.getFile(key);
                setBlogPhoto(key, photo, createDTO);
            }
            Blog blog = new Blog(createDTO);
            blog.setActive(true);
            blog.setViewCounter(0);
            blog.setType(typeRepository.findByNameIgnoreCase(createDTO.getTypeName())
                    .orElseThrow(() -> {
                        logger.warn("Type of blog is not found with name: {}", createDTO.getTypeName());
                        return new NotFoundException("Type of blog is not found with name: " + createDTO.getTypeName());
                    }));
            List<BlogOption> optionList = blog.getOptions();
            for (int i = 0; i < optionList.size(); i++) {
                optionList.get(i).setOrderNum(i);
            }
            Blog save = blogRepository.save(blog);
            String slug = save.getId() + "-" + SlugUtil.makeSlug(save.getTitleRu());
            blogRepository.updateSlugById(save.getId(), slug);
            save.setSlug(slug);
            BlogResponseDTO responseDTO = new BlogResponseDTO(save);
            responseDTO.getOptions().sort(Comparator.comparing(BlogOptionResponseDTO::getOrderNum));
            response.setData(responseDTO);
            response.setMessage("Successfully created");
            return ResponseEntity.status(201).body(response);
        } catch (
                JsonProcessingException e) {
            logger.error("Error processing JSON for blog creation", e);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }


    private void setBlogPhoto(String key, MultipartFile photo, BlogCreateDTO createDTO) {
        int index = Integer.parseInt(key.substring(12));
        int size = createDTO.getOptions().size();
        if ((index + 1) > size) {
            throw new NotFoundException("The number of photos has exceeded the number of options.");
        }
        BlogOptionCreateDTO optionCreateDTO = createDTO.getOptions().get(index);
        optionCreateDTO.setPhoto(photoService.save(photo));
    }

    public ResponseEntity<ApiResponse<?>> findById(Long id, String lang) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Blog is not found with id: {}", id);
                    return new NotFoundException("Blog is not found with id: " + id);
                });
        if (lang == null || lang.equals("-")) {
            ApiResponse<BlogResponseDTO> response = new ApiResponse<>();
            BlogResponseDTO responseDTO = new BlogResponseDTO(blog);
            responseDTO.getOptions().sort(Comparator.comparing(BlogOptionResponseDTO::getOrderNum));
            response.setData(responseDTO);
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<BlogMapper> response = new ApiResponse<>();
        BlogMapper responseDTO = new BlogMapper(blog, lang);
        responseDTO.getOptions().sort(Comparator.comparing(BlogOptionMapper::getOrderNum));
        response.setData(responseDTO);
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
            BlogResponseDTO responseDTO = new BlogResponseDTO(blog);
            responseDTO.getOptions().sort(Comparator.comparing(BlogOptionResponseDTO::getOrderNum));
            response.setData(responseDTO);
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<BlogMapper> response = new ApiResponse<>();
        BlogMapper responseDTO = new BlogMapper(blog, lang);
        responseDTO.getOptions().sort(Comparator.comparing(BlogOptionMapper::getOrderNum));
        response.setData(responseDTO);
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> findAll(String lang, Integer page, Integer size, Boolean main,
                                                  Boolean popular, Boolean aNew, Boolean old, String typeName) {
        List<Blog> blogList = new ArrayList<>();
        if (page != null) {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Blog> all = blogRepository.findAll(pageable);
            blogList = all.getContent();
        } else {
            blogList = blogRepository.findAll();
        }

        if (main != null)
            blogList = blogList.stream().filter(Blog::isMain).collect(Collectors.toList());
        if (popular != null && popular) {
            blogList = blogList.stream()
                    .sorted(Comparator.comparing(Blog::getViewCounter).reversed())
                    .collect(Collectors.toList());
        }
        if (aNew != null && aNew) {
            blogList = blogList.stream()
                    .sorted(Comparator.comparing(Blog::getCreatedDate).reversed())
                    .collect(Collectors.toList());
        }
        if (old != null && old) {
            blogList = blogList.stream()
                    .sorted(Comparator.comparing(Blog::getCreatedDate))
                    .collect(Collectors.toList());
        }
        if (typeName != null)
            blogList = blogList.stream().
                    filter(blog -> blog.getType().getNameUz().equals(typeName) ||
                            blog.getType().getNameRu().equals(typeName) ||
                            blog.getType().getNameEn().equals(typeName)).collect(Collectors.toList());

        if (lang == null || lang.equals("-")) {
            ApiResponse<List<BlogResponseDTO>> response = new ApiResponse<>();
            response.setData(new ArrayList<>());
            blogList.forEach(blog -> {
                BlogResponseDTO responseDTO = new BlogResponseDTO(blog);
                responseDTO.getOptions().sort(Comparator.comparing(BlogOptionResponseDTO::getOrderNum));
                response.getData().add(responseDTO);
            });
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<List<BlogMapper>> response = new ApiResponse<>();
        response.setData(new ArrayList<>());
        blogList.forEach(blog -> {
            BlogMapper responseDTO = new BlogMapper(blog, lang);
            responseDTO.getOptions().sort(Comparator.comparing(BlogOptionMapper::getOrderNum));
            response.getData().add(responseDTO);
        });
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
        if (updateDTO.getTypeName() != null) {
            fromDb.setType(typeRepository.findByNameIgnoreCase(updateDTO.getTypeName())
                    .orElseThrow(() -> {
                        logger.warn("Type is not found with name: {}", updateDTO.getTypeName());
                        return new NotFoundException("Type is not found with name: " + updateDTO.getTypeName());
                    }));
        }
        if (updateDTO.isActive() != fromDb.isActive()) {
            fromDb.setActive(updateDTO.isActive());
        }
        if (updateDTO.isMain() != fromDb.isMain()) {
            fromDb.setMain(fromDb.isMain());
        }
        if (updateDTO.getTitle() != null) {
            Translation title = updateDTO.getTitle();
            if (title.getUz() != null) {
                fromDb.setTitleUz(title.getUz());
            }
            if (title.getRu() != null) {
                fromDb.setTitleRu(title.getRu());
                String slug = fromDb.getId() + "-" + SlugUtil.makeSlug(title.getRu());
                fromDb.setSlug(slug);
            }
            if (title.getEn() != null) {
                fromDb.setTitleEn(title.getEn());
            }
        }
        if (updateDTO.getOptions() != null && !updateDTO.getOptions().isEmpty()) {
            List<BlogOptionResponseDTO> options = updateDTO.getOptions();
            List<BlogOption> dbOptions = fromDb.getOptions();
            List<BlogOption> optionsToRemove = new ArrayList<>();

            for (BlogOptionResponseDTO option : options) {
                if (option.getId() != null) {
                    for (BlogOption dbOption : dbOptions) {
                        if (dbOption.getId().equals(option.getId())) {
                            if (option.getOrderNum() != null) {
                                dbOption.setId(option.getId());
                            }
                            if (option.getTitle() != null) {
                                Translation title = option.getTitle();
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
                            if (option.getDescription() != null) {
                                Translation description = option.getDescription();
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
                            if (option.getPhoto() != null) {
                                Photo photo = option.getPhoto();
                                if (photo.getHttpUrl() != null && photo.getId() == null) {
                                    Photo photoEntity = photoRepository.findByHttpUrl(photo.getHttpUrl())
                                            .orElseThrow(() -> {
                                                logger.warn("Photo is not found with url: {}", photo.getHttpUrl());
                                                return new NotFoundException("Photo is not found with url: " + photo.getHttpUrl());
                                            });
                                    dbOption.setPhoto(photoEntity);
                                }
                            }
                            if (option.getOrderNum() != null) {
                                dbOption.setOrderNum(option.getOrderNum());
                            }
                            if (option.getTitle() == null && option.getDescription() == null
                                    && option.getOrderNum() == null && option.getPhoto() == null && option.getId() != null) {
                                optionsToRemove.add(dbOption);
                            }
                        }
                    }
                    for (BlogOption removeOption : optionsToRemove) {
                        dbOptions.remove(removeOption);
                        optionRepository.deleteCustom(removeOption.getId());
                    }
                } else {
                    BlogOption blogOption = new BlogOption(option);
                    blogOption.setBlog(fromDb);
                    dbOptions.add(blogOption);
                }
            }
        }

        BlogResponseDTO responseDTO = new BlogResponseDTO(blogRepository.save(fromDb));
        responseDTO.getOptions().sort(Comparator.nullsLast(Comparator.comparing(
                BlogOptionResponseDTO::getOrderNum,
                Comparator.nullsFirst(Integer::compareTo)
        )));
        response.setData(responseDTO);
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

    public ResponseEntity<ApiResponse<?>> incrementView(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (!blogRepository.existsById(id)) {
            logger.warn("Blog is not found with id: {}", id);
            throw new NotFoundException("Blog is not found with id: " + id);
        }
        blogRepository.updateViewBlog(id);
        response.setMessage("Successfully increased");
        return ResponseEntity.ok(response);
    }

}
