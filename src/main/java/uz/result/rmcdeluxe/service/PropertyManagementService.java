package uz.result.rmcdeluxe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.result.rmcdeluxe.entity.PropertyManagement;
import uz.result.rmcdeluxe.entity.PropertyManagementOption;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.PropertyManagementDTO;
import uz.result.rmcdeluxe.payload.PropertyManagementOptionDTO;
import uz.result.rmcdeluxe.repository.PropertyManagementOptionRepository;
import uz.result.rmcdeluxe.repository.PropertyManagementRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyManagementService {

    private final PropertyManagementRepository managementRepository;

    private final PropertyManagementOptionRepository optionRepository;

    public ResponseEntity<ApiResponse<PropertyManagement>> addOption(PropertyManagement management){
        ApiResponse<PropertyManagement> response=new ApiResponse<>();
        Optional<PropertyManagement> optionalPropertyManagement = managementRepository.findAll().stream().findFirst();
        if (optionalPropertyManagement.isPresent()) {
            return update(management);
        }
        PropertyManagement save = managementRepository.save(management);
        response.setData(save);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> find(String lang) {
        PropertyManagement management = managementRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Property management service is not created yet"));
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
        PropertyManagement fromDB = managementRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Property management service is not created yet"));

        if (management.getOptions() != null) {
            List<PropertyManagementOption> managementOptions = management.getOptions();
            List<PropertyManagementOption> dbOptions = fromDB.getOptions();

            for (PropertyManagementOption managementOption : managementOptions) {
                for (PropertyManagementOption dbOption : dbOptions) {
                    if (managementOption.getId() == null) {
                        PropertyManagementOption save = optionRepository.save(managementOption);
                        dbOptions.add(save);
                    }
                    if (managementOption.getId().equals(dbOption.getId())) {

                        if (managementOption.getDescriptionUz() != null) {
                            dbOption.setDescriptionUz(managementOption.getDescriptionUz());
                        }
                        if (managementOption.getDescriptionRu() != null) {
                            dbOption.setDescriptionRu(managementOption.getDescriptionRu());
                        }
                        if (managementOption.getDescriptionEn() != null) {
                            dbOption.setDescriptionEn(managementOption.getDescriptionEn());
                        }

                        if (managementOption.getDescriptionUz() == null &&
                                managementOption.getDescriptionEn() == null &&
                                managementOption.getDescriptionRu() == null) {
                            optionRepository.deleteById(managementOption.getId());
                        }
                    }
                }
            }

        }

        response.setData(managementRepository.save(fromDB));
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
