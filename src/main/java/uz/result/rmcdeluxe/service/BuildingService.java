package uz.result.rmcdeluxe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.Building;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.entity.VideoFile;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.Translation;
import uz.result.rmcdeluxe.payload.building.BuildingCreateDTO;
import uz.result.rmcdeluxe.payload.building.BuildingMapper;
import uz.result.rmcdeluxe.payload.building.BuildingResponseDTO;
import uz.result.rmcdeluxe.payload.building.BuildingUpdateDTO;
import uz.result.rmcdeluxe.repository.BuildingRepository;
import uz.result.rmcdeluxe.repository.CatalogRepository;
import uz.result.rmcdeluxe.repository.PhotoRepository;
import uz.result.rmcdeluxe.repository.VideoFileRepository;
import uz.result.rmcdeluxe.util.SlugUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;

    private final CatalogRepository catalogRepository;

    private final PhotoRepository photoRepository;

    private final PhotoService photoService;

    private final VideoFileService videoService;

    private final VideoFileRepository videoFileRepository;

    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(BuildingService.class);

    public ResponseEntity<ApiResponse<BuildingResponseDTO>> create(String json, List<MultipartFile> photoList, List<MultipartFile> videoList) {
        ApiResponse<BuildingResponseDTO> response = new ApiResponse<>();
        try {
            BuildingCreateDTO createDTO = objectMapper.readValue(json, BuildingCreateDTO.class);
            Building building = new Building(createDTO);
            building.setActive(true);
            building.setGallery(new ArrayList<>());
            building.setVideoList(new ArrayList<>());
            Building save = buildingRepository.save(building);
            String slug = save.getId() + "-" + SlugUtil.makeSlug(save.getTitleRu());
            if (photoList != null && !photoList.isEmpty()) {
                for (MultipartFile photoFile : photoList) {
                    Photo photo = photoService.save(photoFile);
                    photo.setBuilding(save);
                    save.getGallery().add(photo);
                }
            }
            if (videoList != null && !videoList.isEmpty()) {
                for (MultipartFile videoFile : videoList) {
                    VideoFile video = Objects.requireNonNull(videoService.save(videoFile).getBody()).getData();
                    video.setBuilding(save);
                    save.getVideoList().add(video);
                }
            }
            building.setCatalog(catalogRepository.findById(createDTO.getCatalogId())
                    .orElseThrow(() -> {
                        logger.warn("Catalog is not found with id: {}", createDTO.getCatalogId());
                        return new NotFoundException("Catalog is not found with id: " + createDTO.getCatalogId());
                    }));
            save = buildingRepository.save(save);
            buildingRepository.updateSlug(slug, save.getId());
            save.setSlug(slug);
            response.setData(new BuildingResponseDTO(save));
            response.setMessage("Successfully created");
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            logger.error("Error processing JSON for blog creation", e);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> findById(Long id, String lang) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Building is not found with id: {}", id);
                    return new NotFoundException("Building is not found with id: " + id);
                });
        if (lang == null || lang.equals("-")) {
            ApiResponse<BuildingResponseDTO> response = new ApiResponse<>();
            response.setData(new BuildingResponseDTO(building));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<BuildingMapper> response = new ApiResponse<>();
        response.setData(new BuildingMapper(building, lang));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> findBySlug(String slug, String lang) {
        Building building = buildingRepository.findBySlug(slug)
                .orElseThrow(() -> {
                    logger.warn("Building is not found with slug: " + slug);
                    return new NotFoundException("Building is not found with slug: " + slug);
                });
        if (lang == null || lang.equals("-")) {
            ApiResponse<BuildingResponseDTO> response = new ApiResponse<>();
            response.setData(new BuildingResponseDTO(building));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<BuildingMapper> response = new ApiResponse<>();
        response.setData(new BuildingMapper(building, lang));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> findAll(String lang) {
        List<Building> all = buildingRepository.findAll();
        if (lang == null || lang.equals("-")) {
            ApiResponse<List<BuildingResponseDTO>> response = new ApiResponse<>();
            response.setData(new ArrayList<>());
            all.forEach(building -> response.getData().add(new BuildingResponseDTO(building)));
            response.setMessage("Successfully found");
            return ResponseEntity.ok(response);
        }
        ApiResponse<List<BuildingMapper>> response = new ApiResponse<>();
        response.setData(new ArrayList<>());
        all.forEach(building -> response.getData().add(new BuildingMapper(building, lang)));
        response.setMessage("Successfully found");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<BuildingResponseDTO>> update(BuildingUpdateDTO updateDTO) {
        ApiResponse<BuildingResponseDTO> response = new ApiResponse<>();
        Building fromDb = buildingRepository.findById(updateDTO.getId())
                .orElseThrow(() -> {
                    logger.warn("Building is not found with id: {}", updateDTO.getId());
                    return new NotFoundException("Building is not found with id: " + updateDTO.getId());
                });

        if (updateDTO.getCatalogId() != null) {
            fromDb.setCatalog(catalogRepository.findById(updateDTO.getCatalogId())
                    .orElseThrow(() -> {
                        logger.warn("Catalog is not found with id: {}", updateDTO.getCatalogId());
                        return new NotFoundException("Catalog is not found with id: " + updateDTO.getCatalogId());
                    })
            );
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
                String slug = fromDb.getId() + SlugUtil.makeSlug(title.getRu());
                fromDb.setSlug(slug);
                fromDb.setTitleRu(title.getRu());
            }
            if (title.getEn() != null) {
                fromDb.setTitleEn(title.getEn());
            }
        }
        if (updateDTO.getDescription() != null) {
            Translation description = updateDTO.getDescription();
            if (description.getUz() != null) {
                fromDb.setDescriptionUz(description.getUz());
            }
            if (description.getRu() != null) {
                fromDb.setDescriptionRu(description.getRu());
            }
            if (description.getEn() != null) {
                fromDb.setDescriptionEn(description.getEn());
            }
        }
        if (updateDTO.getGallery() != null) {
            for (Photo photo : updateDTO.getGallery()) {
                if (photo.getId() == null && photo.getHttpUrl() != null) {
                    Photo photoEntity = photoRepository.findByHttpUrl(photo.getHttpUrl())
                            .orElseThrow(() -> {
                                logger.warn("Photo is not found with url: {}", photo.getHttpUrl());
                                return new NotFoundException("Photo is not found with url: " + photo.getHttpUrl());
                            });
                    photoEntity.setBuilding(fromDb);
                    fromDb.getGallery().add(photoEntity);
                }
            }
        }
        if (updateDTO.getVideoList() != null) {
            for (VideoFile video : updateDTO.getVideoList()) {
                if (video.getId() == null && video.getVideoUrl() != null) {
                    VideoFile videoFile = videoFileRepository.findByVideoUrl(video.getVideoUrl())
                            .orElseThrow(() -> {
                                logger.warn("Video is not found with url: {}", video.getVideoUrl());
                                return new NotFoundException("Video is not found with url: " + video.getVideoUrl());
                            });
                    videoFile.setBuilding(fromDb);
                    fromDb.getVideoList().add(videoFile);
                }
            }
        }

        response.setData(new BuildingResponseDTO(buildingRepository.save(fromDb)));
        response.setMessage("Successfully updated");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Building is not found with id: {}", id);
                    return new NotFoundException("Building is not found with id: " + id);
                });
        buildingRepository.delete(building);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
