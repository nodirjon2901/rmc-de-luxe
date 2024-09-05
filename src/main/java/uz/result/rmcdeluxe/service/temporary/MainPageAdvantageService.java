package uz.result.rmcdeluxe.service.temporary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.temporary.MainPageAdvantage;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.temporary.MainPageAdvantageDTO;
import uz.result.rmcdeluxe.repository.temporary.MainPageAdvantageRepository;
import uz.result.rmcdeluxe.service.PhotoService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainPageAdvantageService {

    private final MainPageAdvantageRepository advantageRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<MainPageAdvantage>> create(String json, MultipartFile photoFile) {
        ApiResponse<MainPageAdvantage> response = new ApiResponse<>();
        try {
            MainPageAdvantage mainPageAdvantage = objectMapper.readValue(json, MainPageAdvantage.class);
            mainPageAdvantage.setPhoto(photoService.save(photoFile));
            MainPageAdvantage save = advantageRepository.save(mainPageAdvantage);
            response.setData(save);
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> findById(Long id, String lang) {
        MainPageAdvantage mainPageAdvantage = advantageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Advantage is not found by id: " + id));
        if (lang != null) {
            ApiResponse<MainPageAdvantageDTO> response = new ApiResponse<>();
            response.setData(new MainPageAdvantageDTO(mainPageAdvantage, lang));
            return ResponseEntity.ok(response);
        }
        ApiResponse<MainPageAdvantage> response = new ApiResponse<>();
        response.setData(mainPageAdvantage);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> findAll(String lang) {
        List<MainPageAdvantage> all = advantageRepository.findAll();
        if (lang != null) {
            ApiResponse<List<MainPageAdvantageDTO>> response = new ApiResponse<>();
            response.setData(new ArrayList<>());
            all.forEach(advantage -> response.getData().add(new MainPageAdvantageDTO(advantage, lang)));
            return ResponseEntity.ok(response);
        }
        ApiResponse<List<MainPageAdvantage>> response = new ApiResponse<>();
        response.setData(new ArrayList<>());
        all.forEach(advantage -> response.getData().add(advantage));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<MainPageAdvantage>> update(MainPageAdvantage advantage) {
        ApiResponse<MainPageAdvantage> response = new ApiResponse<>();
        MainPageAdvantage fromDb = advantageRepository.findById(advantage.getId())
                .orElseThrow(() -> new NotFoundException("Advantage is not found by id: " + advantage.getId()));

        if (advantage.getTitleUz() != null) {
            fromDb.setTitleUz(advantage.getTitleUz());
        }
        if (advantage.getTitleRu() != null) {
            fromDb.setTitleRu(advantage.getTitleRu());
        }
        if (advantage.getTitleEn() != null) {
            fromDb.setTitleEn(advantage.getTitleEn());
        }

        if (advantage.getDescriptionUz() != null) {
            fromDb.setDescriptionUz(advantage.getDescriptionUz());
        }
        if (advantage.getDescriptionRu() != null) {
            fromDb.setDescriptionRu(advantage.getDescriptionRu());
        }
        if (advantage.getDescriptionEn() != null) {
            fromDb.setDescriptionEn(advantage.getDescriptionEn());
        }

        response.setData(advantageRepository.save(fromDb));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (!advantageRepository.existsById(id)) {
            throw new NotFoundException("Advantage is not found by id: " + id);
        }
        advantageRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
