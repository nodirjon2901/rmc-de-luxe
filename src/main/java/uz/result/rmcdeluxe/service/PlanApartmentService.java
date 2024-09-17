package uz.result.rmcdeluxe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.PlanApartment;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.Translation;
import uz.result.rmcdeluxe.payload.building.PlanApartmentCreateDTO;
import uz.result.rmcdeluxe.payload.building.PlanApartmentMapper;
import uz.result.rmcdeluxe.payload.building.PlanApartmentResponseDTO;
import uz.result.rmcdeluxe.payload.building.PlanApartmentUpdateDTO;
import uz.result.rmcdeluxe.repository.PlanApartmentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanApartmentService {

    private final PlanApartmentRepository apartmentRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    private final Logger logger = LoggerFactory.getLogger(PlanApartmentService.class);

    public ResponseEntity<ApiResponse<PlanApartmentResponseDTO>> create(String json, MultipartFile photoFile) {
        ApiResponse<PlanApartmentResponseDTO> response = new ApiResponse<>();
        try {
            PlanApartmentCreateDTO createDTO = objectMapper.readValue(json, PlanApartmentCreateDTO.class);
            PlanApartment entity = new PlanApartment(createDTO);
            entity.setActive(true);
            entity.setPhoto(photoService.save(photoFile));
            entity.setBuilding(null);
            response.setData(new PlanApartmentResponseDTO(apartmentRepository.save(entity)));
            response.setMessage("Successfully created");
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            logger.error("Error processing JSON for blog creation", e);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> findById(String lang, Long id) {
        PlanApartment planApartment = apartmentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Apartment is not found with id: {}", id);
                    return new NotFoundException("Apartment is not found with id: " + id);
                });
        if (lang == null || lang.equals("-")) {
            ApiResponse<PlanApartmentResponseDTO> response = new ApiResponse<>();
            response.setData(new PlanApartmentResponseDTO(planApartment));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<PlanApartmentMapper> response = new ApiResponse<>();
        response.setData(new PlanApartmentMapper(planApartment, lang));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> findAll(String lang, int page, int size, Double fromPrice,
                                                  Double toPrice, String roomNumber, Integer floorCount) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PlanApartment> catalogList = apartmentRepository.findAll(pageable);
        List<PlanApartment> all = catalogList.getContent();

        if (fromPrice != null)
            all = all.stream().filter(planApartment -> planApartment.getPrice() >= fromPrice).toList();
        if (toPrice != null)
            all = all.stream().filter(planApartment -> planApartment.getPrice() <= toPrice).toList();
        if (roomNumber != null)
            all = all.stream().filter(planApartment -> planApartment.getRoomCountUz().equals(roomNumber) || planApartment.getRoomCountRu().equals(roomNumber) || planApartment.getRoomCountEn().equals(roomNumber)).toList();
        if (floorCount != null)
            all = all.stream().filter(planApartment -> planApartment.getFloorNum().equals(floorCount)).toList();

        if (lang == null || lang.equals("-")) {
            ApiResponse<List<PlanApartmentResponseDTO>> response = new ApiResponse<>();
            response.setData(new ArrayList<>());
            all.forEach(planApartment -> response.getData().add(new PlanApartmentResponseDTO(planApartment)));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<List<PlanApartmentMapper>> response = new ApiResponse<>();
        response.setData(new ArrayList<>());
        all.forEach(planApartment -> response.getData().add(new PlanApartmentMapper(planApartment, lang)));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<PlanApartmentResponseDTO>> update(PlanApartmentUpdateDTO updateDTO) {
        ApiResponse<PlanApartmentResponseDTO> response = new ApiResponse<>();
        PlanApartment fromDb = apartmentRepository.findById(updateDTO.getId())
                .orElseThrow(() -> {
                    logger.warn("Apartment is not found with id: {}", updateDTO.getId());
                    return new NotFoundException("Apartment is not found with id: " + updateDTO.getId());
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
        if (updateDTO.getRoomCount() != null) {
            Translation roomCount = updateDTO.getRoomCount();
            if (roomCount.getUz() != null) {
                fromDb.setRoomCountUz(roomCount.getUz());
            }
            if (roomCount.getRu() != null) {
                fromDb.setRoomCountRu(roomCount.getRu());
            }
            if (roomCount.getEn() != null) {
                fromDb.setRoomCountEn(roomCount.getEn());
            }
        }

        if (updateDTO.getPrice() != null) {
            fromDb.setPrice(updateDTO.getPrice());
        }
        if (updateDTO.getBuildingNum() != null) {
            fromDb.setBuildingNum(updateDTO.getBuildingNum());
        }
        if (updateDTO.getFloorNum() != null) {
            fromDb.setFloorNum(updateDTO.getFloorNum());
        }
        if (updateDTO.getEntranceNum() != null) {
            fromDb.setEntranceNum(updateDTO.getEntranceNum());
        }
        if (updateDTO.getBuildingId() != null) {
            fromDb.setBuilding(null);// not finished yet
        }

        response.setData(new PlanApartmentResponseDTO(apartmentRepository.save(fromDb)));
        response.setMessage("Successfully updated");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        PlanApartment planApartment = apartmentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Apartment is not found with id: {}", id);
                    return new NotFoundException("Apartment is not found with id: " + id);
                });
        apartmentRepository.delete(planApartment);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
