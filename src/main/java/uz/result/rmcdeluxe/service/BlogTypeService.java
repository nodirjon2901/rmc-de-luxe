package uz.result.rmcdeluxe.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.result.rmcdeluxe.entity.BlogType;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.Translation;
import uz.result.rmcdeluxe.payload.blog.BlogTypeCreateDTO;
import uz.result.rmcdeluxe.payload.blog.BlogTypeMapper;
import uz.result.rmcdeluxe.payload.blog.BlogTypeResponse;
import uz.result.rmcdeluxe.repository.BlogTypeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogTypeService {

    private final BlogTypeRepository typeRepository;

    private final Logger logger = LoggerFactory.getLogger(BlogTypeService.class);

    public ResponseEntity<ApiResponse<BlogTypeResponse>> create(BlogTypeCreateDTO createDTO) {
        ApiResponse<BlogTypeResponse> response = new ApiResponse<>();
        try {
            BlogType type = new BlogType(createDTO);
            BlogType savedType = typeRepository.save(type);
            response.setData(new BlogTypeResponse(savedType));
            response.setMessage("Successfully created");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DataIntegrityViolationException e) {
            response.setMessage("Error: Duplicate entry for blog type name.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            response.setMessage("Error: Unable to create blog type. Please try again later.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> findById(Long id, String lang) {
        BlogType type = typeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Type is not found with id: " + id);
                    return new NotFoundException("Type is not found with id: " + id);
                });
        if (lang == null || lang.equals("-")) {
            ApiResponse<BlogTypeResponse> response = new ApiResponse<>();
            response.setData(new BlogTypeResponse(type));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<BlogTypeMapper> response = new ApiResponse<>();
        response.setData(new BlogTypeMapper(type, lang));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> findAll(String lang) {
        List<BlogType> all = typeRepository.findAll();
        if (lang == null || lang.equals("-")) {
            ApiResponse<List<BlogTypeResponse>> response = new ApiResponse<>();
            response.setData(new ArrayList<>());
            all.forEach(type -> response.getData().add(new BlogTypeResponse(type)));
            response.setMessage("Successfully deleted");
            return ResponseEntity.ok(response);
        }
        ApiResponse<List<BlogTypeMapper>> response = new ApiResponse<>();
        response.setData(new ArrayList<>());
        all.forEach(type -> response.getData().add(new BlogTypeMapper(type, lang)));
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<BlogTypeResponse>> update(BlogTypeResponse update) {
        ApiResponse<BlogTypeResponse> response = new ApiResponse<>();
        BlogType fromDb = typeRepository.findById(update.getId())
                .orElseThrow(() -> {
                    logger.warn("Type is not found with id: {}", update.getId());
                    return new NotFoundException("Type is not found with id: " + update.getId());
                });

        if (update.getName() != null) {
            Translation name = update.getName();
            if (name.getUz() != null)
                fromDb.setNameUz(name.getUz());
            if (name.getRu() != null)
                fromDb.setNameRu(name.getRu());
            if (name.getEn() != null)
                fromDb.setNameEn(name.getEn());
        }

        response.setData(new BlogTypeResponse(typeRepository.save(fromDb)));
        response.setMessage("Successfully updated");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        BlogType type = typeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Type is not found id: {}", id);
                    return new NotFoundException("Type is not found with id: " + id);
                });
        if (type.getBlogs() != null && !type.getBlogs().isEmpty()) {
            response.setMessage("There are blogs related to this type. Therefore, you cannot delete this type");
            return ResponseEntity.status(400).body(response);
        }
        typeRepository.delete(type);
        response.setMessage("Successfully delete");
        return ResponseEntity.ok(response);
    }

}
