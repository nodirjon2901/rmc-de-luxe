package uz.result.rmcdeluxe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.AboutUsBody;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.Translation;
import uz.result.rmcdeluxe.payload.aboutUs.*;
import uz.result.rmcdeluxe.repository.AboutUsBodyRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AboutUsBodyService {

    private final AboutUsBodyRepository bodyRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    private final Logger logger = LoggerFactory.getLogger(AboutUsBodyService.class);

    public ResponseEntity<ApiResponse<AboutUsBodyResponseDTO>> create(String json, MultipartFile photoFile) {
        Optional<AboutUsBody> optional = bodyRepository.findAll().stream().findFirst();
        if (optional.isPresent()) {
            logger.warn("Body is already exists");
            throw new AlreadyExistsException("Banner is already exists. You can only update its");
        }
        ApiResponse<AboutUsBodyResponseDTO> response = new ApiResponse<>();
        try {
            AboutUsBodyCreateDTO createDTO = objectMapper.readValue(json, AboutUsBodyCreateDTO.class);
            AboutUsBody body = new AboutUsBody(createDTO);
            body.setPhoto(photoService.save(photoFile));
            response.setData(new AboutUsBodyResponseDTO(bodyRepository.save(body)));
            response.setMessage("Successfully deleted");
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            logger.error("Error processing JSON for blog creation", e);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> find(String lang) {
        AboutUsBody body = bodyRepository.findAll().stream().findFirst()
                .orElseThrow(() -> {
                    logger.warn("Body is not found");
                    return new NotFoundException("Body is not found");
                });
        if (lang == null || lang.equals("-")) {
            ApiResponse<AboutUsBodyResponseDTO> response = new ApiResponse<>();
            response.setData(new AboutUsBodyResponseDTO(body));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<AboutUsBodyMapper> response = new ApiResponse<>();
        response.setData(new AboutUsBodyMapper(body, lang));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<AboutUsBodyResponseDTO>> update(AboutUsBodyUpdateDTO updateDTO) {
        ApiResponse<AboutUsBodyResponseDTO> response = new ApiResponse<>();
        AboutUsBody fromDb = bodyRepository.findById(Long.valueOf(updateDTO.getId()))
                .orElseThrow(() -> {
                    logger.warn("Body is not found");
                    return new NotFoundException("Body is not found");
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

        if (updateDTO.getSubtitle() != null) {
            Translation subtitle = updateDTO.getSubtitle();
            if (subtitle.getUz() != null) {
                fromDb.setSubtitleUz(subtitle.getUz());
            }
            if (subtitle.getRu() != null) {
                fromDb.setSubtitleRu(subtitle.getRu());
            }
            if (subtitle.getEn() != null) {
                fromDb.setSubtitleEn(subtitle.getEn());
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

        response.setData(new AboutUsBodyResponseDTO(fromDb));
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<?> response = new ApiResponse<>();
        AboutUsBody body = bodyRepository.findAll().stream().findFirst()
                .orElseThrow(() -> {
                    logger.warn("Body is not found");
                    return new NotFoundException("Body is not found");
                });
        bodyRepository.delete(body);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
