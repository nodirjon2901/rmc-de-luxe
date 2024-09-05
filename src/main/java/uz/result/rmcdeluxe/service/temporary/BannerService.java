package uz.result.rmcdeluxe.service.temporary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.temporary.Banner;
import uz.result.rmcdeluxe.entity.temporary.BannerSlider;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.temporary.BannerDTO;
import uz.result.rmcdeluxe.repository.temporary.BannerRepository;
import uz.result.rmcdeluxe.repository.temporary.BannerSliderRepository;
import uz.result.rmcdeluxe.service.PhotoService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    private final BannerSliderRepository bannerSliderRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<Banner>> addSlider(String json, MultipartFile photoFile) {
        ApiResponse<Banner> response = new ApiResponse<>();
        List<Banner> all = bannerRepository.findAll();
        try {
            BannerSlider bannerSlider = objectMapper.readValue(json, BannerSlider.class);
            bannerSlider.setPhoto(photoService.save(photoFile));
            bannerSlider.setActive(true);
            BannerSlider save = bannerSliderRepository.save(bannerSlider);
            if (all.isEmpty()) {
                Banner banner = new Banner();
                banner.setSliders(new ArrayList<>());
                banner.getSliders().add(save);
                response.setData(bannerRepository.save(banner));
                response.setMessage("Created first banner successfully");
                return ResponseEntity.ok(response);
            }
            Banner banner = all.get(0);
            banner.getSliders().add(save);
            banner=bannerRepository.save(banner);
            save.setBanner(banner);
            bannerSliderRepository.save(save);
            response.setData(banner);
            response.setMessage("Added slider");
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> get(String lang) {
        Banner banner = bannerRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Banner is not found, not created yet"));
        if (lang != null) {
            ApiResponse<BannerDTO> response = new ApiResponse<>();
            response.setData(new BannerDTO(banner, lang));
            return ResponseEntity.ok(response);
        }
        ApiResponse<Banner> response = new ApiResponse<>();
        response.setData(banner);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<Banner>> update(Banner banner) {
        ApiResponse<Banner> response = new ApiResponse<>();
        Banner fromDB = bannerRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Banner is not found, not created yet"));
        if (banner.getSliders() != null) {
            List<BannerSlider> sliders = banner.getSliders();
            List<BannerSlider> dbSliders = fromDB.getSliders();
            for (BannerSlider slider : sliders) {
                for (BannerSlider dbSlider : dbSliders) {
                    if (dbSlider.getId().equals(slider.getId())) {

                        if (slider.getTitleUz() != null) {
                            dbSlider.setTitleUz(slider.getTitleUz());
                        }
                        if (slider.getTitleRu() != null) {
                            dbSlider.setTitleRu(slider.getTitleRu());
                        }
                        if (slider.getTitleEn() != null) {
                            dbSlider.setTitleEn(slider.getTitleEn());
                        }

                        if (slider.getDescriptionUz() != null) {
                            dbSlider.setDescriptionUz(slider.getDescriptionUz());
                        }
                        if (slider.getDescriptionRu() != null) {
                            dbSlider.setDescriptionRu(slider.getDescriptionRu());
                        }
                        if (slider.getDescriptionEn() != null) {
                            dbSlider.setDescriptionEn(slider.getDescriptionEn());
                        }

                        if (slider.getLink() != null) {
                            dbSlider.setLink(slider.getLink());
                        }
                    }
                }
            }
        }
        response.setData(bannerRepository.save(fromDB));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<?> response = new ApiResponse<>();
        Banner banner = bannerRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Banner is not found, not created yet"));
        bannerRepository.delete(banner);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }


}
