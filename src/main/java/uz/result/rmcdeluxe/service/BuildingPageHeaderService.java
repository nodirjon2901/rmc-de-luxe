package uz.result.rmcdeluxe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.BuildingHeaderFeature;
import uz.result.rmcdeluxe.entity.BuildingPageHeader;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.BuildingPageHeaderDTO;
import uz.result.rmcdeluxe.repository.BuildingHeaderFeatureRepository;
import uz.result.rmcdeluxe.repository.BuildingPageHeaderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuildingPageHeaderService {

    private final BuildingPageHeaderRepository headerRepository;

    private final BuildingHeaderFeatureRepository featureRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<BuildingPageHeader>> create(String json, MultipartFile photoFile) {
        ApiResponse<BuildingPageHeader> response = new ApiResponse<>();
        Optional<BuildingPageHeader> optionalPageHeader = headerRepository.findAll().stream().findFirst();
        try {
            BuildingPageHeader pageHeader = objectMapper.readValue(json, BuildingPageHeader.class);
            if (optionalPageHeader.isPresent()) {
                throw new AlreadyExistsException("Page header is already created");
            }
            pageHeader.setPhoto(photoService.save(photoFile));
            BuildingPageHeader save = headerRepository.save(pageHeader);
            response.setData(save);
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> find(String lang) {
        BuildingPageHeader pageHeader = headerRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Page header is not created yet"));
        if (lang != null) {
            ApiResponse<BuildingPageHeaderDTO> response = new ApiResponse<>();
            response.setData(new BuildingPageHeaderDTO(pageHeader, lang));
            return ResponseEntity.ok(response);
        }
        ApiResponse<BuildingPageHeader> response = new ApiResponse<>();
        response.setData(pageHeader);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<BuildingPageHeader>> update(BuildingPageHeader header) {
        ApiResponse<BuildingPageHeader> response = new ApiResponse<>();
        BuildingPageHeader fromDb = headerRepository.findById(header.getId())
                .orElseThrow(() -> new NotFoundException("Page header is not created yet with id: "+header.getId()));

        if (header.getTitleUz() != null) {
            fromDb.setTitleUz(header.getTitleUz());
        }
        if (header.getTitleRu() != null) {
            fromDb.setTitleRu(header.getTitleRu());
        }
        if (header.getTitleEn() != null) {
            fromDb.setTitleEn(header.getTitleEn());
        }

        if (header.getSubtitleUz() != null) {
            fromDb.setSubtitleUz(header.getSubtitleUz());
        }
        if (header.getSubtitleRu() != null) {
            fromDb.setSubtitleRu(header.getSubtitleRu());
        }
        if (header.getSubtitleEn() != null) {
            fromDb.setSubtitleEn(header.getSubtitleEn());
        }

        if (header.getShortDescriptionUz() != null) {
            fromDb.setShortDescriptionUz(header.getShortDescriptionUz());
        }
        if (header.getShortDescriptionRu() != null) {
            fromDb.setShortDescriptionRu(header.getShortDescriptionRu());
        }
        if (header.getShortDescriptionEn() != null) {
            fromDb.setShortDescriptionEn(header.getShortDescriptionEn());
        }

        if (header.getHeaderFeatures() != null) {
            List<BuildingHeaderFeature> headerFeatures = header.getHeaderFeatures();
            List<BuildingHeaderFeature> dbHeaderFeatures = fromDb.getHeaderFeatures();
            List<BuildingHeaderFeature> featuresToRemove = new ArrayList<>();
            for (BuildingHeaderFeature headerFeature : headerFeatures) {
                if (headerFeature.getId() != null) {
                    for (BuildingHeaderFeature dbFeature : dbHeaderFeatures) {
                        if (headerFeature.getId().equals(dbFeature.getId())) {

                            if (headerFeature.getTitleUz() != null) {
                                dbFeature.setTitleUz(headerFeature.getTitleUz());
                            }
                            if (headerFeature.getTitleRu() != null) {
                                dbFeature.setTitleRu(headerFeature.getTitleRu());
                            }
                            if (headerFeature.getTitleEn() != null) {
                                dbFeature.setTitleEn(headerFeature.getTitleEn());
                            }

                            if (headerFeature.getDescriptionUz() != null) {
                                dbFeature.setDescriptionUz(headerFeature.getDescriptionUz());
                            }
                            if (headerFeature.getDescriptionRu() != null) {
                                dbFeature.setDescriptionRu(headerFeature.getDescriptionRu());
                            }
                            if (headerFeature.getDescriptionEn() != null) {
                                dbFeature.setDescriptionEn(headerFeature.getDescriptionEn());
                            }

                            if (headerFeature.getTitleUz() == null && headerFeature.getTitleRu() == null && headerFeature.getTitleEn() == null &&
                                    headerFeature.getDescriptionUz() == null && headerFeature.getDescriptionEn() == null && headerFeature.getDescriptionRu() == null) {
                                featuresToRemove.add(dbFeature);
                            }
                        }
                    }
                } else {
                    headerFeature.setHeader(fromDb);
                    dbHeaderFeatures.add(headerFeature);
                }
            }

            for (BuildingHeaderFeature removeFeature : featuresToRemove) {
                dbHeaderFeatures.remove(removeFeature);
                featureRepository.deleteById(removeFeature.getId());
            }
        }
        response.setData(headerRepository.save(fromDb));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<?> response = new ApiResponse<>();
        BuildingPageHeader pageHeader = headerRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Page header is not created yet"));
        headerRepository.delete(pageHeader);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
