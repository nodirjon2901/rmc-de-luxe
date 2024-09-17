package uz.result.rmcdeluxe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.Banner;
import uz.result.rmcdeluxe.entity.BannerSlider;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.Translation;
import uz.result.rmcdeluxe.payload.banner.*;
import uz.result.rmcdeluxe.repository.BannerRepository;
import uz.result.rmcdeluxe.repository.BannerSliderRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    private final BannerSliderRepository sliderRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    private final Logger logger = LoggerFactory.getLogger(BannerService.class);

    public ResponseEntity<ApiResponse<BannerResponseDTO>> create(String json, MultipartFile photoFile) {
        ApiResponse<BannerResponseDTO> response = new ApiResponse<>();
        Optional<Banner> optionalBanner = bannerRepository.findAll().stream().findFirst();
        Banner banner = new Banner();
        if (optionalBanner.isPresent()) {
            banner = optionalBanner.get();
        } else {
            banner = bannerRepository.save(banner);
        }
        Optional<Integer> maxOrderNum = sliderRepository.getMaxOrderNum();
        try {
            BannerSliderCreateDTO sliderCreateDTO = objectMapper.readValue(json, BannerSliderCreateDTO.class);
            BannerSlider slider = new BannerSlider(sliderCreateDTO);
            slider.setActive(true);
            slider.setPhoto(photoService.save(photoFile));
            slider.setOrderNum(maxOrderNum.map(num -> num + 1).orElse(1));
            slider.setBanner(banner);
            BannerSlider save = sliderRepository.save(slider);
            response.setData(new BannerResponseDTO(save.getBanner()));
            response.setMessage("Successfully added");
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            logger.error("Error processing JSON for blog creation", e);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> findById(Long id, String lang) {
        BannerSlider bannerSlider = sliderRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Slidet is not found with id: {}", id);
                    return new NotFoundException("Slider is not found with id: " + id);
                });
        if (lang == null || lang.equals("-")) {
            ApiResponse<BannerSliderResponseDTO> response = new ApiResponse<>();
            response.setData(new BannerSliderResponseDTO(bannerSlider));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<BannerSliderMapper> response = new ApiResponse<>();
        response.setData(new BannerSliderMapper(bannerSlider, lang));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> findAll(String lang) {
        Banner banner = bannerRepository.findAll().stream().findFirst()
                .orElseThrow(() -> {
                    logger.warn("Banner is not found");
                    return new NotFoundException("Banner is not found");
                });
        if (lang == null || lang.equals("-")) {
            ApiResponse<BannerResponseDTO> response = new ApiResponse<>();
            response.setData(new BannerResponseDTO(banner));
            response.setMessage("Successfully banner");
            return ResponseEntity.ok(response);
        }
        ApiResponse<BannerMapper> response = new ApiResponse<>();
        response.setData(new BannerMapper(banner, lang));
        response.setMessage("Successfully banner");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<BannerSliderResponseDTO>> update(BannerSliderUpdateDTO updateDTO) {
        ApiResponse<BannerSliderResponseDTO> response = new ApiResponse<>();
        BannerSlider fromDb = sliderRepository.findById(updateDTO.getId())
                .orElseThrow(() -> {
                    logger.warn("Slider is not found with id: {}", updateDTO.getId());
                    return new NotFoundException("Slider is not found with id: " + updateDTO.getId());
                });

        if (updateDTO.getLink() != null) {
            fromDb.setLink(updateDTO.getLink());
        }
        if (updateDTO.getOrderNum() != null) {
            fromDb.setOrderNum(updateDTO.getOrderNum());
        }
        if (updateDTO.isActive() != fromDb.isActive()) {
            fromDb.setActive(updateDTO.isActive());
        }

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
        if (updateDTO.getShortDescription() != null) {
            Translation shortDescription = updateDTO.getShortDescription();
            if (shortDescription.getUz() != null) {
                fromDb.setShortDescriptionUz(shortDescription.getUz());
            }
            if (shortDescription.getRu() != null) {
                fromDb.setShortDescriptionRu(shortDescription.getRu());
            }
            if (shortDescription.getEn() != null) {
                fromDb.setShortDescriptionEn(shortDescription.getEn());
            }
        }

        response.setData(new BannerSliderResponseDTO(sliderRepository.save(fromDb)));
        response.setMessage("Successfully updated");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        BannerSlider bannerSlider = sliderRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Slider is not found with id: {}", id);
                    return new NotFoundException("Slider is not found with id: " + id);
                });
        sliderRepository.delete(bannerSlider);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
