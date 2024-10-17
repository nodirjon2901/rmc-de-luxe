package uz.result.rmcdeluxe.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.result.rmcdeluxe.entity.District;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.district.DistrictCreateDTO;
import uz.result.rmcdeluxe.payload.district.DistrictMapper;
import uz.result.rmcdeluxe.payload.district.DistrictResponseDTO;
import uz.result.rmcdeluxe.repository.DistrictRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;

    private final Logger logger = LoggerFactory.getLogger(DistrictService.class);

    public ResponseEntity<ApiResponse<DistrictResponseDTO>> create(DistrictCreateDTO createDTO) {
        ApiResponse<DistrictResponseDTO> response = new ApiResponse<>();
        try {
            District district = new District(createDTO);
            response.setData(new DistrictResponseDTO(districtRepository.save(district)));
            response.setMessage("Successfully created");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DataIntegrityViolationException e) {
            response.setMessage("Error: Duplicate entry for district name.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            response.setMessage("Error: Unable to create district. Please try again later.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> findById(Long id, String lang) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("District is not found with id: {}", id);
                    return new NotFoundException("District is not found with id: " + id);
                });
        if (lang == null || lang.equals("-")) {
            ApiResponse<DistrictResponseDTO> response = new ApiResponse<>();
            response.setData(new DistrictResponseDTO(district));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<DistrictMapper> response = new ApiResponse<>();
        response.setData(new DistrictMapper(district, lang));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> findAll(String lang) {
        List<District> all = districtRepository.findAll();
        if (lang == null || lang.equals("-")) {
            ApiResponse<List<DistrictResponseDTO>> response = new ApiResponse<>();
            response.setData(new ArrayList<>());
            all.forEach(district -> response.getData().add(new DistrictResponseDTO(district)));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<List<DistrictMapper>> response = new ApiResponse<>();
        response.setData(new ArrayList<>());
        all.forEach(district -> response.getData().add(new DistrictMapper(district, lang)));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<DistrictResponseDTO>> update(DistrictResponseDTO update) {
        District fromDb = districtRepository.findById(update.getId())
                .orElseThrow(() -> {
                    logger.warn("District is not found with id: {}", update.getId());
                    return new NotFoundException("District is not found with id: " + update.getId());
                });
        ApiResponse<DistrictResponseDTO> response = new ApiResponse<>();

        if (update.getName().getUz() != null) {
            fromDb.setNameUz(update.getName().getUz());
        }
        if (update.getName().getRu() != null) {
            fromDb.setNameRu(update.getName().getRu());
        }
        if (update.getName().getEn() != null) {
            fromDb.setNameEn(update.getName().getEn());
        }

        response.setData(new DistrictResponseDTO(districtRepository.save(fromDb)));
        response.setMessage("Successfully updated");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete(Long id) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("District is not found with id: {}", id);
                    return new NotFoundException("District is not found with id: " + id);
                });
        ApiResponse<?> response = new ApiResponse<>();
        if (district.getCatalogs() != null && !district.getCatalogs().isEmpty()) {
            response.setMessage("Catalogs are available for this district. Therefore, you cannot delete this type");
            return ResponseEntity.status(400).body(response);
        }
        districtRepository.delete(district);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
