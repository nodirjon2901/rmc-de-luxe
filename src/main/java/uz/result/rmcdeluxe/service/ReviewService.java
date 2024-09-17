package uz.result.rmcdeluxe.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.result.rmcdeluxe.entity.Review;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.Translation;
import uz.result.rmcdeluxe.payload.review.ReviewCreateDTO;
import uz.result.rmcdeluxe.payload.review.ReviewMapper;
import uz.result.rmcdeluxe.payload.review.ReviewResponseDTO;
import uz.result.rmcdeluxe.payload.review.ReviewUpdateDTO;
import uz.result.rmcdeluxe.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    public ResponseEntity<ApiResponse<ReviewResponseDTO>> create(ReviewCreateDTO createDTO) {
        ApiResponse<ReviewResponseDTO> response = new ApiResponse<>();
        Review review = new Review(createDTO);
        review.setActive(true);
        response.setData(new ReviewResponseDTO(reviewRepository.save(review)));
        response.setMessage("Successfully created");
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<?>> findById(Long id, String lang) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Review is not found with id: {}", id);
                    return new NotFoundException("Review is not found with id: " + id);
                });
        if (lang == null || lang.equals("-")) {
            ApiResponse<ReviewResponseDTO> response = new ApiResponse<>();
            response.setData(new ReviewResponseDTO(review));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<ReviewMapper> response = new ApiResponse<>();
        response.setData(new ReviewMapper(review, lang));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> findAll(String lang, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Review> all = reviewRepository.findAll(pageable);
        if (lang == null || lang.equals("-")) {
            ApiResponse<List<ReviewResponseDTO>> response = new ApiResponse<>();
            response.setData(new ArrayList<>());
            all.forEach(review -> response.getData().add(new ReviewResponseDTO(review)));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<List<ReviewMapper>> response = new ApiResponse<>();
        response.setData(new ArrayList<>());
        all.forEach(review -> response.getData().add(new ReviewMapper(review, lang)));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<ReviewResponseDTO>> update(ReviewUpdateDTO updateDTO) {
        ApiResponse<ReviewResponseDTO> response = new ApiResponse<>();
        Review fromDb = reviewRepository.findById(updateDTO.getId())
                .orElseThrow(() -> {
                    logger.warn("Review is not found with id: " + updateDTO.getId());
                    return new NotFoundException("Review is not found with id: " + updateDTO.getId());
                });

        if (updateDTO.getTitle() != null) {
            Translation title = updateDTO.getTitle();
            if (title.getUz() != null) {
                fromDb.setTitleUz(title.getUz());
            }
            if (title.getRu() != null) {
                fromDb.setTitleRu(title.getRu());
            }
            if (title.getEn() != null) {
                fromDb.setTitleEn(title.getEn());
            }
        }
        if (updateDTO.getDescription() != null) {
            Translation description = updateDTO.getDescription();
            if (description.getUz() != null) {
                fromDb.setDescriptionUz(description.getUz());
            }
            if (description.getRu() != null) {
                fromDb.setDescriptionRu(description.getRu());
            }
            if (description.getEn() != null) {
                fromDb.setDescriptionEn(description.getEn());
            }
        }
        if (updateDTO.isActive() != fromDb.isActive()) {
            fromDb.setActive(updateDTO.isActive());
        }

        response.setData(new ReviewResponseDTO(reviewRepository.save(fromDb)));
        response.setMessage("Successfully updated");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Review is not found with id: {}", id);
                    return new NotFoundException("Review is not found with id: " + id);
                });
        reviewRepository.delete(review);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
