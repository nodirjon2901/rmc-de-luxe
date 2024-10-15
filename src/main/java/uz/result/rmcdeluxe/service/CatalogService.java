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
import uz.result.rmcdeluxe.entity.Catalog;
import uz.result.rmcdeluxe.entity.District;
import uz.result.rmcdeluxe.entity.HouseType;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.catalog.CatalogCreateDTO;
import uz.result.rmcdeluxe.payload.catalog.CatalogMapper;
import uz.result.rmcdeluxe.payload.catalog.CatalogResponseDTO;
import uz.result.rmcdeluxe.payload.catalog.CatalogUpdateDTO;
import uz.result.rmcdeluxe.repository.CatalogRepository;
import uz.result.rmcdeluxe.repository.DistrictRepository;
import uz.result.rmcdeluxe.repository.HouseTypeRepository;
import uz.result.rmcdeluxe.util.SlugUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;

    private final DistrictRepository districtRepository;

    private final HouseTypeRepository typeRepository;

    private final PhotoService photoService;

    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(CatalogService.class);

    public ResponseEntity<ApiResponse<CatalogResponseDTO>> create(String json, MultipartFile photoFile) {
        ApiResponse<CatalogResponseDTO> response = new ApiResponse<>();
        try {
            CatalogCreateDTO createDTO = objectMapper.readValue(json, CatalogCreateDTO.class);
            Optional<Catalog> optionalCatalog = catalogRepository.findByName(createDTO.getName());
            if (optionalCatalog.isPresent()) {
                logger.warn("Catalog is already exists with name:{}", createDTO.getName());
                throw new AlreadyExistsException("Catalog is already exists with name: " + createDTO.getName());
            }
            if (createDTO.getPrice() < 0) {
                logger.warn("Invalid price value: " + createDTO.getPrice());
                throw new NotFoundException("Invalid price value: " + createDTO.getPrice());
            }
            Catalog catalog = new Catalog(createDTO);
            catalog.setPhoto(photoService.save(photoFile));
            catalog.setActive(true);
            catalog.setDistrict(districtRepository.findByNameIgnoreCase(createDTO.getDistrictName())
                    .orElseThrow(() -> {
                        logger.warn("District is not found with name: " + createDTO.getDistrictName());
                        return new NotFoundException("District is not found with name: " + createDTO.getDistrictName());
                    })
            );
            catalog.setType(typeRepository.findByNameIgnoreCase(createDTO.getTypeName())
                    .orElseThrow(() -> {
                        logger.warn("Type is not found with type: {}", createDTO.getTypeName());
                        return new NotFoundException("Type is not found with type: " + createDTO.getTypeName());
                    })
            );
            Catalog save = catalogRepository.save(catalog);
            String slug = save.getId() + "-" + SlugUtil.makeSlug(save.getName());
            catalogRepository.updateSlug(slug, save.getId());
            save.setSlug(slug);
            response.setData(new CatalogResponseDTO(save));
            response.setMessage("Successfully created");
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            logger.error("Error processing JSON for blog creation", e);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> findById(Long id, String lang) {
        Catalog catalog = catalogRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Catalog is not found with id: {}", id);
                    return new NotFoundException("Catalog is not found with id: " + id);
                });
        if (lang == null || lang.equals("-")) {
            ApiResponse<CatalogResponseDTO> response = new ApiResponse<>();
            response.setData(new CatalogResponseDTO(catalog));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<CatalogMapper> response = new ApiResponse<>();
        response.setData(new CatalogMapper(catalog, lang));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> findSlug(String slug, String lang) {
        Catalog catalog = catalogRepository.findBySlug(slug)
                .orElseThrow(() -> {
                    logger.warn("Catalog is not found with slug: " + slug);
                    return new NotFoundException("Catalog is not found with slug: " + slug);
                });
        if (lang == null || lang.equals("-")) {
            ApiResponse<CatalogResponseDTO> response = new ApiResponse<>();
            response.setData(new CatalogResponseDTO(catalog));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<CatalogMapper> response = new ApiResponse<>();
        response.setData(new CatalogMapper(catalog, lang));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> findAll(String lang, String districtName, Double fromPrice, Double toPrice,
                                                  String typeName, String roomNumber, String deadline, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Catalog> all = catalogRepository.findAll(pageable);
        List<Catalog> catalogList = all.getContent();

        if (districtName != null)
            catalogList = all.stream().filter(catalog -> {
                return catalog.getDistrict().getNameUz().equals(districtName) || catalog.getDistrict().getNameRu().equals(districtName) ||catalog.getDistrict().getNameEn().equals(districtName);
            }).toList();
        if (fromPrice != null)
            catalogList = all.stream().filter(catalog -> catalog.getPrice() >= fromPrice).toList();
        if (toPrice != null)
            catalogList = all.stream().filter(catalog -> catalog.getPrice() <= toPrice).toList();
        if (typeName != null)
            catalogList = all.stream().filter(catalog -> {
                return catalog.getType().getNameUz().equals(typeName) || catalog.getType().getNameRu().equals(typeName)||catalog.getType().getNameEn().equals(typeName);
            }).toList();
        if (roomNumber != null)
            catalogList = all.stream().filter(catalog -> catalog.getNumberOfRoomsRu().equals(roomNumber) || catalog.getNumberOfRoomsUz().equals(roomNumber) || catalog.getNumberOfRoomsEn().equals(roomNumber)).toList();
        if (deadline != null)
            catalogList = all.stream().filter(catalog -> catalog.getCompletionDateUz().equals(deadline) || catalog.getCompletionDateRu().equals(deadline) || catalog.getCompletionDateEn().equals(deadline)).toList();

        if (lang == null || lang.equals("-")) {
            ApiResponse<List<CatalogResponseDTO>> response = new ApiResponse<>();
            response.setData(new ArrayList<>());
            catalogList.forEach(catalog -> response.getData().add(new CatalogResponseDTO(catalog)));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<List<CatalogMapper>> response = new ApiResponse<>();
        response.setData(new ArrayList<>());
        catalogList.forEach(catalog -> response.getData().add(new CatalogMapper(catalog, lang)));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<CatalogResponseDTO>> update(CatalogUpdateDTO updateDTO) {
        Catalog fromDb = catalogRepository.findById(updateDTO.getId())
                .orElseThrow(() -> {
                    logger.warn("Catalog is not found with id: {}", updateDTO.getId());
                    return new NotFoundException("Catalog is not found with id: " + updateDTO.getId());
                });
        ApiResponse<CatalogResponseDTO> response = new ApiResponse<>();
        if (updateDTO.getName() != null) {
            if (checkName(updateDTO.getName())) {
                logger.warn("Catalog is already exists with name: " + updateDTO.getName());
                throw new AlreadyExistsException("Catalog is already exists with name: " + updateDTO.getName());
            }
            fromDb.setName(updateDTO.getName());
            String slug = fromDb.getId() + "-" + SlugUtil.makeSlug(updateDTO.getName());
            fromDb.setSlug(slug);
        }
        if (updateDTO.getDistrictName() != null) {
            District district = districtRepository.findByNameIgnoreCase(updateDTO.getDistrictName())
                    .orElseThrow(() -> {
                        logger.warn("District is not found with name: {}", updateDTO.getDistrictName());
                        return new NotFoundException("District is not found with name: " + updateDTO.getDistrictName());
                    });
            fromDb.setDistrict(district);
        }
        if (updateDTO.getPrice() != null) {
            if (updateDTO.getPrice() >= 0) {
                fromDb.setPrice(updateDTO.getPrice());
            } else {
                logger.warn("Invalid price value: " + updateDTO.getPrice());
                throw new NotFoundException("Invalid price value: " + updateDTO.getPrice());
            }
        }
        if (updateDTO.getTypeName() != null) {
            HouseType type = typeRepository.findByNameIgnoreCase(updateDTO.getTypeName())
                    .orElseThrow(() -> {
                        logger.warn("Type is not found with name: {}", updateDTO.getTypeName());
                        return new NotFoundException("Type is not found with name: " + updateDTO.getTypeName());
                    });
            fromDb.setType(type);
        }
        if (updateDTO.getNumberOfRooms() != null) {
            if (updateDTO.getNumberOfRooms().getUz() != null) {
                fromDb.setNumberOfRoomsUz(updateDTO.getNumberOfRooms().getUz());
            }
            if (updateDTO.getNumberOfRooms().getRu() != null) {
                fromDb.setNumberOfRoomsRu(updateDTO.getNumberOfRooms().getRu());
            }
            if (updateDTO.getNumberOfRooms().getEn() != null) {
                fromDb.setNumberOfRoomsEn(updateDTO.getNumberOfRooms().getEn());
            }
        }
        if (updateDTO.getCompletionDate() != null) {
            if (updateDTO.getCompletionDate().getUz() != null) {
                fromDb.setCompletionDateUz(updateDTO.getCompletionDate().getUz());
            }
            if (updateDTO.getCompletionDate().getRu() != null) {
                fromDb.setCompletionDateRu(updateDTO.getCompletionDate().getRu());
            }
            if (updateDTO.getCompletionDate().getEn() != null) {
                fromDb.setCompletionDateEn(updateDTO.getCompletionDate().getEn());
            }

            if (updateDTO.isActive() != fromDb.isActive()) {
                fromDb.setActive(updateDTO.isActive());
            }
        }


        response.setData(new CatalogResponseDTO(catalogRepository.save(fromDb)));
        response.setMessage("Successfully updated");
        return ResponseEntity.ok(response);
    }

    private boolean checkName(String name) {
        return catalogRepository.findByName(name).isPresent();
    }

    public ResponseEntity<ApiResponse<?>> delete(Long id) {
        Catalog catalog = catalogRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Catalog is not found with id: {}", id);
                    return new NotFoundException("Catalog is not found with id: " + id);
                });
        ApiResponse<?> response = new ApiResponse<>();
        if (catalog.getBuildingList() != null && !catalog.getBuildingList().isEmpty()) {
            response.setMessage("This catalog contains relevant buildings. Therefore, you cannot delete this type");
            return ResponseEntity.status(400).body(response);
        }
        catalogRepository.delete(catalog);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }


}
