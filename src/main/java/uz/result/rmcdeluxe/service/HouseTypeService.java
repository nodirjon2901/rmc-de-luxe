package uz.result.rmcdeluxe.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.result.rmcdeluxe.entity.HouseType;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.houseType.HouseTypeCreateDTO;
import uz.result.rmcdeluxe.payload.houseType.HouseTypeMapper;
import uz.result.rmcdeluxe.payload.houseType.HouseTypeResponseDTO;
import uz.result.rmcdeluxe.repository.HouseTypeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseTypeService {

    private final HouseTypeRepository houseTypeRepository;

    private final Logger logger = LoggerFactory.getLogger(HouseTypeService.class);

    public ResponseEntity<ApiResponse<HouseTypeResponseDTO>> create(HouseTypeCreateDTO createDTO) {
        ApiResponse<HouseTypeResponseDTO> response = new ApiResponse<>();
        HouseType houseType = new HouseType(createDTO);
        HouseType save = houseTypeRepository.save(houseType);
        response.setData(new HouseTypeResponseDTO(save));
        response.setMessage("Successfully created");
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<?>> findById(Long id, String lang) {
        HouseType houseType = houseTypeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Type is not found with id: {}", id);
                    return new NotFoundException("Type is not found with id: " + id);
                });
        if (lang == null || lang.equals("-")) {
            ApiResponse<HouseTypeResponseDTO> response = new ApiResponse<>();
            response.setData(new HouseTypeResponseDTO(houseType));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<HouseTypeMapper> response = new ApiResponse<>();
        response.setData(new HouseTypeMapper(houseType, lang));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> findAll(String lang) {
        List<HouseType> all = houseTypeRepository.findAll();
        if (lang == null || lang.equals("-")) {
            ApiResponse<List<HouseTypeResponseDTO>> response = new ApiResponse<>();
            response.setData(new ArrayList<>());
            all.forEach(type -> response.getData().add(new HouseTypeResponseDTO(type)));
            response.setMessage("Type list");
            return ResponseEntity.ok(response);
        }
        ApiResponse<List<HouseTypeMapper>> response = new ApiResponse<>();
        response.setData(new ArrayList<>());
        all.forEach(type -> response.getData().add(new HouseTypeMapper(type, lang)));
        response.setMessage("Type list");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<HouseTypeResponseDTO>> update(HouseTypeResponseDTO update) {
        HouseType fromDb = houseTypeRepository.findById(update.getId())
                .orElseThrow(() -> {
                    logger.warn("Type is not found with id: {}", update.getId());
                    return new NotFoundException("Type is not found with id: " + update.getId());
                });
        ApiResponse<HouseTypeResponseDTO> response = new ApiResponse<>();

        if (update.getName().getUz() != null) {
            fromDb.setNameUz(update.getName().getUz());
        }
        if (update.getName().getRu() != null) {
            fromDb.setNameRu(update.getName().getRu());
        }
        if (update.getName().getEn() != null) {
            fromDb.setNameEn(update.getName().getEn());
        }

        response.setData(new HouseTypeResponseDTO(houseTypeRepository.save(fromDb)));
        response.setMessage("Successfully updated");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete(Long id) {
        HouseType houseType = houseTypeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Type is not found with id: {}", id);
                    return new NotFoundException("Type is not found with id: " + id);
                });
        ApiResponse<?> response = new ApiResponse<>();
        if (houseType.getCatalogs() != null && !houseType.getCatalogs().isEmpty()) {
            response.setMessage("There are catalogs related to this type. Therefore, you cannot delete this type");
            return ResponseEntity.status(400).body(response);
        }
        houseTypeRepository.delete(houseType);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
