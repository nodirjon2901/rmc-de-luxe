package uz.result.rmcdeluxe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.InfrastructSection;
import uz.result.rmcdeluxe.entity.InfrastructSectionItem;
import uz.result.rmcdeluxe.entity.InfrastructureArea;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.Translation;
import uz.result.rmcdeluxe.payload.infrastructureArea.*;
import uz.result.rmcdeluxe.repository.BuildingRepository;
import uz.result.rmcdeluxe.repository.InfrastructSectionItemRepository;
import uz.result.rmcdeluxe.repository.InfrastructSectionRepository;
import uz.result.rmcdeluxe.repository.InfrastructureAreaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InfrastructureAreaService {

    private final InfrastructureAreaRepository areaRepository;

    private final InfrastructSectionItemRepository itemRepository;

    private final InfrastructSectionRepository sectionRepository;

    private final BuildingRepository buildingRepository;

    private final PhotoService photoService;

    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(InfrastructureAreaService.class);

    public ResponseEntity<ApiResponse<InfrastructureAreaResponseDTO>> create(String json, MultipartFile photoFile) {
        ApiResponse<InfrastructureAreaResponseDTO> response = new ApiResponse<>();
        try {
            InfrastructureAreaCreateDTO createDTO = objectMapper.readValue(json, InfrastructureAreaCreateDTO.class);
            if (areaRepository.existsByBuildingId(createDTO.getBuildingId())) {
                logger.warn("InfrastructureArea is already saved with this building_id: {}", createDTO.getBuildingId());
                throw new AlreadyExistsException("InfrastructureArea is already saved with this building_id: " + createDTO.getBuildingId());
            }
            InfrastructureArea area = new InfrastructureArea(createDTO);
            area.setPhoto(photoService.save(photoFile));
            area.setBuilding(buildingRepository.findById(createDTO.getBuildingId())
                    .orElseThrow(() -> {
                        logger.warn("Building is not found with id: {}", createDTO.getBuildingId());
                        return new NotFoundException("Building is not found with id: " + createDTO.getBuildingId());
                    }));
            List<InfrastructSection> sections = area.getSections();
            area.setSections(null);
            areaRepository.save(area);
            if (sections != null) {
                for (InfrastructSection section : sections) {
                    section.setInfrastructureArea(area);
                    if (section.getSectionItems() != null) {
                        for (InfrastructSectionItem item : section.getSectionItems()) {
                            item.setSection(section);
                        }
                    }
                }
                area.setSections(sections);
            }
            InfrastructureArea savedArea = areaRepository.save(area);
            response.setData(new InfrastructureAreaResponseDTO(savedArea));
            response.setMessage("Successfully created");
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            logger.error("Error processing JSON for blog creation", e);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }


    public ResponseEntity<ApiResponse<?>> findById(Long id, String lang) {
        InfrastructureArea area = areaRepository.findById(id).orElseThrow(() -> {
            logger.warn("Area is not found with id: {}", id);
            return new NotFoundException("Area is not found with id: " + id);
        });
        if (lang == null || lang.equals("-")) {
            ApiResponse<InfrastructureAreaResponseDTO> response = new ApiResponse<>();
            response.setData(new InfrastructureAreaResponseDTO(area));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<InfrastructureAreaMapper> response = new ApiResponse<>();
        response.setData(new InfrastructureAreaMapper(area, lang));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> findAll(String lang) {
        List<InfrastructureArea> all = areaRepository.findAll();
        if (lang == null || lang.equals("-")) {
            ApiResponse<List<InfrastructureAreaResponseDTO>> response = new ApiResponse<>();
            response.setData(new ArrayList<>());
            all.forEach(area -> response.getData().add(new InfrastructureAreaResponseDTO(area)));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<List<InfrastructureAreaMapper>> response = new ApiResponse<>();
        response.setData(new ArrayList<>());
        all.forEach(area -> response.getData().add(new InfrastructureAreaMapper(area, lang)));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<InfrastructureAreaResponseDTO>> update(InfrastructureAreaUpdateDTO updateDTO) {
        ApiResponse<InfrastructureAreaResponseDTO> response = new ApiResponse<>();
        InfrastructureArea fromDb = areaRepository.findById(updateDTO.getId())
                .orElseThrow(() -> {
                    logger.warn("Area is not found with id: {}", updateDTO.getId());
                    return new NotFoundException("Area is not found with id: " + updateDTO.getId());
                });

        if (updateDTO.getBuildingId() != null) {
            fromDb.setBuilding(buildingRepository.findById(updateDTO.getBuildingId())
                    .orElseThrow(() -> {
                        logger.warn("Building is not found with id: {}", updateDTO.getBuildingId());
                        return new NotFoundException("Building is not found with id: " + updateDTO.getBuildingId());
                    }));
        }

        List<InfrastructSectionResponseDTO> sections = updateDTO.getSections();
        List<InfrastructSection> dbSections = fromDb.getSections();
        List<InfrastructSection> sectionsToRemove = new ArrayList<>();

        for (InfrastructSectionResponseDTO section : sections) {
            if (section.getId() != null) {
                for (InfrastructSection dbSection : dbSections) {

                    if (dbSection.getId().equals(section.getId())) {
                        if (section.getSectionItems() != null && !section.getSectionItems().isEmpty()) {
                            List<InfrastructSectionItemResponseDTO> sectionItems = section.getSectionItems();
                            List<InfrastructSectionItem> dbSectionItems = dbSection.getSectionItems();
                            List<InfrastructSectionItem> sectionItemToRemove = new ArrayList<>();

                            for (InfrastructSectionItemResponseDTO sectionItem : sectionItems) {
                                if (sectionItem.getId() != null) {
                                    for (InfrastructSectionItem dbSectionItem : dbSectionItems) {
                                        if (dbSectionItem.getId().equals(sectionItem.getId())) {
                                            if (sectionItem.getName() != null) {
                                                Translation name = sectionItem.getName();
                                                if (name.getUz() != null) {
                                                    dbSectionItem.setNameUz(name.getUz());
                                                }
                                                if (name.getRu() != null) {
                                                    dbSectionItem.setNameRu(name.getRu());
                                                }
                                                if (name.getEn() != null) {
                                                    dbSectionItem.setNameEn(name.getEn());
                                                }
                                            }
                                            if (sectionItem.getTimeOrDistance() != null) {
                                                Translation timeOrDistance = sectionItem.getTimeOrDistance();
                                                if (timeOrDistance.getUz() != null) {
                                                    dbSectionItem.setTimeOrDistanceUz(timeOrDistance.getUz());
                                                }
                                                if (timeOrDistance.getRu() != null) {
                                                    dbSectionItem.setTimeOrDistanceRu(timeOrDistance.getRu());
                                                }
                                                if (timeOrDistance.getEn() != null) {
                                                    dbSectionItem.setTimeOrDistanceEn(timeOrDistance.getEn());
                                                }
                                            }
                                            if (sectionItem.getName() == null && sectionItem.getTimeOrDistance() == null && sectionItem.getId() != null) {
                                                sectionItemToRemove.add(dbSectionItem);
                                            }
                                        }
                                    }
                                    for (InfrastructSectionItem sectionItemRemove : sectionItemToRemove) {
                                        dbSectionItems.remove(sectionItemRemove);
                                        itemRepository.deleteCustom(sectionItemRemove.getId());
                                    }
                                } else {
                                    InfrastructSectionItem item = new InfrastructSectionItem(sectionItem);
                                    item.setSection(dbSection);
                                    dbSectionItems.add(item);
                                }
                            }
                        }
                        if (section.getTitle() != null) {
                            Translation title = section.getTitle();
                            if (title.getUz() != null) {
                                dbSection.setTitleUz(title.getUz());
                            }
                            if (title.getRu() != null) {
                                dbSection.setTitleRu(title.getRu());
                            }
                            if (title.getEn() != null) {
                                dbSection.setTitleEn(title.getEn());
                            }
                        }
                        if (section.getDescription() != null) {
                            Translation description = section.getDescription();
                            if (description.getUz() != null) {
                                dbSection.setDescriptionUz(description.getUz());
                            }
                            if (description.getRu() != null) {
                                dbSection.setDescriptionRu(description.getRu());
                            }
                            if (description.getEn() != null) {
                                dbSection.setDescriptionEn(description.getEn());
                            }
                        }
                        if (section.getSectionItems() == null && section.getTitle() == null && section.getDescription() == null && section.getId() != null) {
                            sectionsToRemove.add(dbSection);
                        }
                    }
                }
            } else {
                InfrastructSection sectionEntity = new InfrastructSection(section);
                sectionEntity.setInfrastructureArea(fromDb);
                dbSections.add(sectionEntity);
            }
            for (InfrastructSection removeSection : sectionsToRemove) {
                dbSections.remove(removeSection);
                sectionRepository.deleteCustom(removeSection.getId());
            }
        }

        response.setData(new InfrastructureAreaResponseDTO(areaRepository.save(fromDb)));
        response.setMessage("Successfully updated");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        InfrastructureArea area = areaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Area is not found with id: {}", id);
                    return new NotFoundException("Area is not found with id: " + id);
                });
        areaRepository.delete(area);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
