package uz.result.rmcdeluxe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.result.rmcdeluxe.entity.RentPageSearchProperty;
import uz.result.rmcdeluxe.entity.RentSearchPropertyOption;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.RentPageSearchPropertyDTO;
import uz.result.rmcdeluxe.repository.RentPageSearchPropertyRepository;
import uz.result.rmcdeluxe.repository.RentSearchPropertyOptionRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentPageSearchPropertyService {

    private final RentPageSearchPropertyRepository propertyRepository;

    private final RentSearchPropertyOptionRepository optionRepository;

    private final PhotoService photoService;

    private final ObjectMapper objectMapper;

    public ResponseEntity<ApiResponse<RentPageSearchProperty>> create(String json, MultipartHttpServletRequest request) {
        ApiResponse<RentPageSearchProperty> response = new ApiResponse<>();
        Optional<RentPageSearchProperty> optionalRentProperty = propertyRepository.findAll().stream().findFirst();
        try {
            if (optionalRentProperty.isPresent()) {
                throw new AlreadyExistsException("Search property form id not created yet");
            }
            RentPageSearchProperty searchProperty = objectMapper.readValue(json, RentPageSearchProperty.class);

            Iterator<String> fileNames = request.getFileNames();
            while (fileNames.hasNext()) {
                String key = fileNames.next();
                MultipartFile photo = request.getFile(key);
                setPhotoOption(key, photo, searchProperty);
            }
            RentPageSearchProperty save = propertyRepository.save(searchProperty);
            response.setData(save);
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    private void setPhotoOption(String key, MultipartFile photo, RentPageSearchProperty searchProperty) {
        int index = Integer.parseInt(key.substring(12)) - 1;
        RentSearchPropertyOption option = searchProperty.getOptions().get(index);
        option.setPhoto(photoService.save(photo));
    }

    public ResponseEntity<ApiResponse<?>> find(String lang) {
        RentPageSearchProperty searchProperty = propertyRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Search property is not created yet"));
        if (lang != null) {
            ApiResponse<RentPageSearchPropertyDTO> response = new ApiResponse<>();
            response.setData(new RentPageSearchPropertyDTO(searchProperty, lang));
            return ResponseEntity.ok(response);
        }
        ApiResponse<RentPageSearchProperty> response = new ApiResponse<>();
        response.setData(searchProperty);
        return ResponseEntity.ok(response);
    }

    @SneakyThrows
    public ResponseEntity<ApiResponse<RentPageSearchProperty>> update(String json, MultipartHttpServletRequest request) {
        RentPageSearchProperty searchProperty = objectMapper.readValue(json, RentPageSearchProperty.class);
        ApiResponse<RentPageSearchProperty> response = new ApiResponse<>();
        RentPageSearchProperty fromDb = propertyRepository.findById(searchProperty.getId())
                .orElseThrow(() -> new NotFoundException("Search property is not created yet with id: " + searchProperty.getId()));

        if (searchProperty.getTitleUz() != null) {
            fromDb.setTitleUz(searchProperty.getTitleUz());
        }
        if (searchProperty.getTitleRu() != null) {
            fromDb.setTitleRu(searchProperty.getTitleRu());
        }
        if (searchProperty.getTitleEn() != null) {
            fromDb.setTitleEn(searchProperty.getTitleEn());
        }

        if (searchProperty.getDescriptionUz() != null) {
            fromDb.setDescriptionUz(searchProperty.getDescriptionUz());
        }
        if (searchProperty.getDescriptionRu() != null) {
            fromDb.setDescriptionRu(searchProperty.getDescriptionRu());
        }
        if (searchProperty.getDescriptionEn() != null) {
            fromDb.setDescriptionEn(searchProperty.getDescriptionEn());
        }

        if (searchProperty.getOptions() != null) {
            List<RentSearchPropertyOption> propertyOptions = searchProperty.getOptions();
            List<RentSearchPropertyOption> dbOptions = fromDb.getOptions();
            List<RentSearchPropertyOption> optionsToRemove = new ArrayList<>();
            int i = 0;
            for (RentSearchPropertyOption propertyOption : propertyOptions) {
                if (propertyOption.getId() != null) {
                    for (RentSearchPropertyOption dbOption : dbOptions) {
                        if (dbOption.getId().equals(propertyOption.getId())) {

                            if (propertyOption.getNameUz() != null) {
                                dbOption.setNameUz(propertyOption.getNameUz());
                            }
                            if (propertyOption.getNameRu() != null) {
                                dbOption.setNameRu(propertyOption.getNameRu());
                            }
                            if (propertyOption.getNameEn() != null) {
                                dbOption.setNameEn(propertyOption.getNameEn());
                            }

                            if (propertyOption.getNameUz() == null && propertyOption.getNameRu() == null && propertyOption.getNameEn() == null) {
                                optionsToRemove.add(propertyOption);
                            }

                        }
                    }
                } else {
                    propertyOption.setSearchProperty(fromDb);
                    if (request != null) {
                        MultipartFile photo = request.getFile("block-index-" + (i+1));
                        if (photo != null && !photo.isEmpty())
                            propertyOption.setPhoto(photoService.save(photo));
                    }
                    dbOptions.add(propertyOption);
                }
                i++;
            }

            for (RentSearchPropertyOption removeOption : optionsToRemove) {
                dbOptions.remove(removeOption);
                optionRepository.deleteByCustom(removeOption.getId());
            }
            propertyRepository.saveAndFlush(fromDb);
        }
        response.setData(fromDb);
        return ResponseEntity.ok(response);

    }

    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<?> response = new ApiResponse<>();
        RentPageSearchProperty searchProperty = propertyRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Search property is not created yet"));
        propertyRepository.delete(searchProperty);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
