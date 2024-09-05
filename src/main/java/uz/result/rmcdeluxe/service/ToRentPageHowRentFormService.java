package uz.result.rmcdeluxe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.result.rmcdeluxe.entity.ToRentHowRentOption;
import uz.result.rmcdeluxe.entity.ToRentPageHowRentForm;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.ToRentPageHowRentFormDTO;
import uz.result.rmcdeluxe.repository.ToRentHowRentOptionRepository;
import uz.result.rmcdeluxe.repository.ToRentPageHowRentFormRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToRentPageHowRentFormService {

    private final ToRentPageHowRentFormRepository rentFormRepository;

    private final ToRentHowRentOptionRepository optionRepository;

    public ResponseEntity<ApiResponse<ToRentPageHowRentForm>> create(ToRentPageHowRentForm rentForm) {
        ApiResponse<ToRentPageHowRentForm> response = new ApiResponse<>();
        Optional<ToRentPageHowRentForm> formOptional = rentFormRepository.findAll().stream().findFirst();
        if (formOptional.isPresent()) {
            throw new AlreadyExistsException("HowRent form is already created");
        }
        ToRentPageHowRentForm save = rentFormRepository.save(rentForm);
        response.setData(save);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> find(String lang) {
        ToRentPageHowRentForm rentForm = rentFormRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("HowRent form is not found"));
        if (lang != null) {
            ApiResponse<ToRentPageHowRentFormDTO> response = new ApiResponse<>();
            response.setData(new ToRentPageHowRentFormDTO(rentForm, lang));
            return ResponseEntity.ok(response);
        }
        ApiResponse<ToRentPageHowRentForm> response = new ApiResponse<>();
        response.setData(rentForm);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<ToRentPageHowRentForm>> update(ToRentPageHowRentForm rentForm) {
        ApiResponse<ToRentPageHowRentForm> response = new ApiResponse<>();
        ToRentPageHowRentForm fromDb = rentFormRepository.findById(rentForm.getId())
                .orElseThrow(() -> new NotFoundException("HowRent form is not found by id: " + rentForm.getId()));

        if (rentForm.getOptions() != null) {
            List<ToRentHowRentOption> rentOptions = rentForm.getOptions();
            List<ToRentHowRentOption> dbOptions = fromDb.getOptions();
            List<ToRentHowRentOption> optionsToRemove = new ArrayList<>();
            for (ToRentHowRentOption rentOption : rentOptions) {
                if (rentOption.getId() != null) {
                    for (ToRentHowRentOption dbOption : dbOptions) {
                        if (dbOption.getId().equals(rentOption.getId())) {

                            if (rentOption.getTitleUz() != null) {
                                dbOption.setTitleUz(rentOption.getTitleUz());
                            }
                            if (rentOption.getTitleRu() != null) {
                                dbOption.setTitleRu(rentOption.getTitleRu());
                            }
                            if (rentOption.getTitleEn() != null) {
                                dbOption.setTitleEn(rentOption.getTitleEn());
                            }

                            if (rentOption.getDescriptionUz() != null) {
                                dbOption.setDescriptionUz(rentOption.getDescriptionUz());
                            }
                            if (rentOption.getDescriptionRu() != null) {
                                dbOption.setDescriptionRu(rentOption.getDescriptionRu());
                            }
                            if (rentOption.getDescriptionEn() != null) {
                                dbOption.setDescriptionEn(rentOption.getDescriptionEn());
                            }

                            if (rentOption.getTitleUz() == null && rentOption.getTitleRu() == null && rentOption.getTitleEn() == null &&
                                    rentOption.getDescriptionUz() == null && rentOption.getDescriptionRu() == null && rentOption.getDescriptionEn() == null) {
                                optionsToRemove.add(dbOption);
                            }
                        }
                    }
                } else {
                    rentOption.setRentForm(fromDb);
                    dbOptions.add(rentOption);
                }
            }
            for (ToRentHowRentOption removeOption : optionsToRemove) {
                dbOptions.remove(removeOption);
                optionRepository.deleteByCustom(removeOption.getId());
            }
        }
        response.setData(rentFormRepository.save(fromDb));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<?> response = new ApiResponse<>();
        ToRentPageHowRentForm rentForm = rentFormRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("HowRent form is not found"));
        rentFormRepository.delete(rentForm);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
