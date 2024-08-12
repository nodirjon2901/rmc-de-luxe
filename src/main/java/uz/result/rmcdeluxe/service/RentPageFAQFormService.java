package uz.result.rmcdeluxe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.result.rmcdeluxe.entity.RentPageFAQForm;
import uz.result.rmcdeluxe.entity.RentPageFAQOption;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.RentPageFAQFormDTO;
import uz.result.rmcdeluxe.repository.RentPageFAQFormRepository;
import uz.result.rmcdeluxe.repository.RentPageFAQOptionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentPageFAQFormService {

    private final RentPageFAQFormRepository faqFormRepository;

    private final RentPageFAQOptionRepository optionRepository;

    public ResponseEntity<ApiResponse<RentPageFAQForm>> create(RentPageFAQForm faqForm) {
        ApiResponse<RentPageFAQForm> response = new ApiResponse<>();
        Optional<RentPageFAQForm> formOptional = faqFormRepository.findAll().stream().findFirst();
        if (formOptional.isPresent()) {
            throw new AlreadyExistsException("FAQ form is already created");
        }
        RentPageFAQForm save = faqFormRepository.save(faqForm);
        response.setData(save);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> find(String lang) {
        RentPageFAQForm faqForm = faqFormRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("FAQ form is not found"));
        if (lang != null) {
            ApiResponse<RentPageFAQFormDTO> response = new ApiResponse<>();
            response.setData(new RentPageFAQFormDTO(faqForm, lang));
            return ResponseEntity.ok(response);
        }
        ApiResponse<RentPageFAQForm> response = new ApiResponse<>();
        response.setData(faqForm);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<RentPageFAQForm>> update(RentPageFAQForm faqForm) {
        ApiResponse<RentPageFAQForm> response = new ApiResponse<>();
        RentPageFAQForm fromDB = faqFormRepository.findById(faqForm.getId())
                .orElseThrow(() -> new NotFoundException("FAQ form is not found by id: " + faqForm.getId()));

        if (faqForm.getOptions() != null) {
            List<RentPageFAQOption> formOptions = faqForm.getOptions();
            List<RentPageFAQOption> dbOptions = fromDB.getOptions();
            List<RentPageFAQOption> optionsToRemove = new ArrayList<>();

            for (RentPageFAQOption formOption : formOptions) {
                if (formOption.getId() != null) {
                    for (RentPageFAQOption dbOption : dbOptions) {
                        if (dbOption.getId().equals(formOption.getId())) {

                            if (formOption.getQuestionUz() != null) {
                                dbOption.setQuestionUz(formOption.getQuestionUz());
                            }
                            if (formOption.getQuestionRu() != null) {
                                dbOption.setQuestionRu(formOption.getQuestionRu());
                            }
                            if (formOption.getQuestionEn() != null) {
                                dbOption.setQuestionEn(formOption.getQuestionEn());
                            }

                            if (formOption.getAnswerUz() != null) {
                                dbOption.setAnswerUz(formOption.getAnswerUz());
                            }
                            if (formOption.getAnswerRu() != null) {
                                dbOption.setAnswerRu(formOption.getAnswerRu());
                            }
                            if (formOption.getAnswerEn() != null) {
                                dbOption.setAnswerEn(formOption.getAnswerEn());
                            }
                            if (formOption.getAnswerUz() == null && formOption.getAnswerRu() == null && formOption.getAnswerEn() == null &&
                                    formOption.getQuestionUz() == null && formOption.getQuestionRu() == null && formOption.getQuestionEn() == null) {
                                optionsToRemove.add(dbOption);
                            }

                        }
                    }
                } else {
                    formOption.setFaqForm(fromDB);
                    dbOptions.add(formOption);
                }
            }
            for (RentPageFAQOption removeOption : optionsToRemove) {
                dbOptions.remove(removeOption);
                optionRepository.deleteByCustom(removeOption.getId());
            }
        }
        response.setData(faqFormRepository.save(fromDB));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<?> response = new ApiResponse<>();
        RentPageFAQForm faqForm = faqFormRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("FAQ form is not found"));
        faqFormRepository.delete(faqForm);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
