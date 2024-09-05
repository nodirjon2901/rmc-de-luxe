package uz.result.rmcdeluxe.service.temporary;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.result.rmcdeluxe.entity.temporary.BuildingHeaderFeature;
import uz.result.rmcdeluxe.entity.temporary.BuildingPageHeader;
import uz.result.rmcdeluxe.entity.temporary.PropertyManagement;
import uz.result.rmcdeluxe.entity.temporary.PropertyManagementOption;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.temporary.PropertyManagementDTO;
import uz.result.rmcdeluxe.payload.temporary.PropertyManagementOptionDTO;
import uz.result.rmcdeluxe.repository.temporary.PropertyManagementOptionRepository;
import uz.result.rmcdeluxe.repository.temporary.PropertyManagementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyManagementService {

    private final PropertyManagementRepository managementRepository;

    private final PropertyManagementOptionRepository optionRepository;

    public ResponseEntity<ApiResponse<PropertyManagement>> create(PropertyManagement management) {
        ApiResponse<PropertyManagement> response = new ApiResponse<>();
        Optional<PropertyManagement> optionalPropertyManagement = managementRepository.findAll().stream().findFirst();
        if (optionalPropertyManagement.isPresent()) {
            throw new AlreadyExistsException("Property service management section is already created");
        }
        PropertyManagement save = managementRepository.save(management);
        response.setData(save);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> find(String lang) {
        PropertyManagement management = managementRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Property management service section is not created yet"));
        if (lang != null) {
            ApiResponse<PropertyManagementDTO> response = new ApiResponse<>();
            response.setData(new PropertyManagementDTO(management, lang));
            return ResponseEntity.ok(response);
        }
        ApiResponse<PropertyManagement> response = new ApiResponse<>();
        response.setData(management);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<PropertyManagement>> update(PropertyManagement management) {
        ApiResponse<PropertyManagement> response = new ApiResponse<>();
        PropertyManagement fromDb = managementRepository.findById(management.getId())
                .orElseThrow(() -> new NotFoundException("Property management service section is not found by id: " + management.getId()));

        if (management.getOptions() != null) {
            List<PropertyManagementOption> managementOptions = management.getOptions();
            List<PropertyManagementOption> dbOptions = fromDb.getOptions();
            List<PropertyManagementOption> optionsToRemove = new ArrayList<>();

            for (PropertyManagementOption managementOption : managementOptions) {
                if (managementOption.getId() != null) {
                    for (PropertyManagementOption dbOption : dbOptions) {
                        if (dbOption.getId().equals(managementOption.getId())) {
                            if (managementOption.getDescriptionUz() != null) {
                                dbOption.setDescriptionUz(managementOption.getDescriptionUz());
                            }
                            if (managementOption.getDescriptionRu() != null) {
                                dbOption.setDescriptionRu(managementOption.getDescriptionRu());
                            }
                            if (managementOption.getDescriptionEn() != null) {
                                dbOption.setDescriptionEn(managementOption.getDescriptionEn());
                            }
                            if (managementOption.getDescriptionUz() == null && managementOption.getDescriptionRu() == null && managementOption.getDescriptionEn() == null) {
                                optionsToRemove.add(dbOption);
                            }
                        }
                    }
                } else {
                    managementOption.setPropertyManagement(fromDb);
                    dbOptions.add(managementOption);
                }
            }
            for (PropertyManagementOption option : optionsToRemove) {
                dbOptions.remove(option);
                optionRepository.delete(option.getId());
            }
            managementRepository.saveAndFlush(fromDb);
        }
        response.setData(fromDb);
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<?> response = new ApiResponse<>();
        PropertyManagement management = managementRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Property management service is not created yet"));
        managementRepository.delete(management);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
