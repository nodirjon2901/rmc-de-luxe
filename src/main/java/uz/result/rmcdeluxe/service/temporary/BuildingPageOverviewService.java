package uz.result.rmcdeluxe.service.temporary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.temporary.BuildingPageOverview;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.temporary.BuildingPageOverviewDTO;
import uz.result.rmcdeluxe.repository.temporary.BuildingPageOverviewRepository;
import uz.result.rmcdeluxe.service.PhotoService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuildingPageOverviewService {

    private final BuildingPageOverviewRepository overviewRepository;

    private final PhotoService photoService;

    private final ObjectMapper objectMapper;

    public ResponseEntity<ApiResponse<BuildingPageOverview>> create(String json, MultipartFile photoFile) {
        ApiResponse<BuildingPageOverview> response = new ApiResponse<>();
        Optional<BuildingPageOverview> overviewOptional = overviewRepository.findAll().stream().findFirst();
        if (overviewOptional.isPresent()) {
            throw new AlreadyExistsException("Building overview is already created");
        }
        try {
            BuildingPageOverview pageOverview = objectMapper.readValue(json, BuildingPageOverview.class);
            pageOverview.setPhoto(photoService.save(photoFile));
            BuildingPageOverview save = overviewRepository.save(pageOverview);
            response.setData(save);
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> find(String lang) {
        BuildingPageOverview pageOverview = overviewRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Building overview is not created yet"));
        if (lang != null) {
            ApiResponse<BuildingPageOverviewDTO> response = new ApiResponse<>();
            response.setData(new BuildingPageOverviewDTO(pageOverview, lang));
            return ResponseEntity.ok(response);
        }
        ApiResponse<BuildingPageOverview> response = new ApiResponse<>();
        response.setData(pageOverview);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<BuildingPageOverview>> update(BuildingPageOverview overview) {
        ApiResponse<BuildingPageOverview> response = new ApiResponse<>();
        BuildingPageOverview fromDb = overviewRepository.findById(overview.getId())
                .orElseThrow(() -> new NotFoundException("Building overview is not found by id: " + overview.getId()));

        if (overview.getTitleUz() != null) {
            fromDb.setTitleUz(overview.getTitleUz());
        }
        if (overview.getTitleRu() != null) {
            fromDb.setTitleRu(overview.getTitleRu());
        }
        if (overview.getTitleEn() != null) {
            fromDb.setTitleEn(overview.getTitleEn());
        }

        if (overview.getDescriptionUz() != null) {
            fromDb.setDescriptionUz(overview.getDescriptionUz());
        }
        if (overview.getDescriptionRu() != null) {
            fromDb.setDescriptionRu(overview.getDescriptionRu());
        }
        if (overview.getDescriptionEn() != null) {
            fromDb.setDescriptionEn(overview.getDescriptionEn());
        }
        response.setData(overviewRepository.save(fromDb));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<?> response = new ApiResponse<>();
        BuildingPageOverview pageOverview = overviewRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Building overview is not created"));
        overviewRepository.delete(pageOverview);
        response.setMessage("Successfully deleted!");
        return ResponseEntity.ok(response);
    }

}
