package uz.result.rmcdeluxe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.AboutUsBanner;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.aboutUs.AboutUsBannerCreateDTO;
import uz.result.rmcdeluxe.payload.aboutUs.AboutUsBannerMapper;
import uz.result.rmcdeluxe.payload.aboutUs.AboutUsBannerResponseDTO;
import uz.result.rmcdeluxe.payload.aboutUs.AboutUsBannerUpdateDTO;
import uz.result.rmcdeluxe.repository.AboutUsBannerRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AboutUsBannerService {

    private final AboutUsBannerRepository bannerRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    private final Logger logger = LoggerFactory.getLogger(AboutUsBannerService.class);

    public ResponseEntity<ApiResponse<AboutUsBannerResponseDTO>> create(String json, MultipartFile photoFile) {
        Optional<AboutUsBanner> optional = bannerRepository.findAll().stream().findFirst();
        if (optional.isPresent()) {
            logger.warn("Banner is already exists");
            throw new AlreadyExistsException("Banner is already exists. You can only update its");
        }
        ApiResponse<AboutUsBannerResponseDTO> response = new ApiResponse<>();
        try {
            AboutUsBannerCreateDTO createDTO = objectMapper.readValue(json, AboutUsBannerCreateDTO.class);
            AboutUsBanner banner = new AboutUsBanner(createDTO);
            banner.setPhoto(photoService.save(photoFile));
            response.setData(new AboutUsBannerResponseDTO(bannerRepository.save(banner)));
            response.setMessage("Successfully deleted");
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            logger.error("Error processing JSON for blog creation", e);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> find(String lang) {
        AboutUsBanner banner = bannerRepository.findAll().stream().findFirst()
                .orElseThrow(() -> {
                    logger.warn("Banner is not found");
                    return new NotFoundException("Banner is not found");
                });
        if (lang == null || lang.equals("-")) {
            ApiResponse<AboutUsBannerResponseDTO> response = new ApiResponse<>();
            response.setData(new AboutUsBannerResponseDTO(banner));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<AboutUsBannerMapper> response = new ApiResponse<>();
        response.setData(new AboutUsBannerMapper(banner, lang));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<AboutUsBannerResponseDTO>> update(AboutUsBannerUpdateDTO updateDTO) {
        AboutUsBanner fromDb = bannerRepository.findById(Long.valueOf(updateDTO.getId()))
                .orElseThrow(() -> {
                    logger.warn("Banner is not found with id: {}", updateDTO.getId());
                    return new NotFoundException("Banner is not found with id: " + updateDTO.getId());
                });
        ApiResponse<AboutUsBannerResponseDTO> response = new ApiResponse<>();
        if (updateDTO.getTitle()!=null){
            if (updateDTO.getTitle().getUz() != null) {
                fromDb.setTitleUz(updateDTO.getTitle().getUz());
            }
            if (updateDTO.getTitle().getRu() != null) {
                fromDb.setTitleRu(updateDTO.getTitle().getRu());
            }
            if (updateDTO.getTitle().getEn() != null) {
                fromDb.setTitleEn(updateDTO.getTitle().getEn());
            }
        }

        if (updateDTO.getShortDescription()!=null){
            if (updateDTO.getShortDescription().getUz() != null) {
                fromDb.setShortDescriptionUz(updateDTO.getShortDescription().getUz());
            }
            if (updateDTO.getShortDescription().getRu() != null) {
                fromDb.setShortDescriptionRu(updateDTO.getShortDescription().getRu());
            }
            if (updateDTO.getShortDescription().getEn() != null) {
                fromDb.setShortDescriptionEn(updateDTO.getShortDescription().getEn());
            }
        }


        response.setData(new AboutUsBannerResponseDTO(bannerRepository.save(fromDb)));
        response.setMessage("Successfully updated");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete() {
        AboutUsBanner banner = bannerRepository.findAll().stream().findFirst()
                .orElseThrow(() -> {
                    logger.warn("Banner is not found");
                    return new NotFoundException("Banner is not found");
                });
        ApiResponse<?> response = new ApiResponse<>();
        bannerRepository.delete(banner);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }


}
