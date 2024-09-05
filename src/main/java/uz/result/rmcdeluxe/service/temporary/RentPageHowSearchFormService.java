package uz.result.rmcdeluxe.service.temporary;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.result.rmcdeluxe.entity.temporary.RentHowSearchOption;
import uz.result.rmcdeluxe.entity.temporary.RentPageHowSearchForm;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.temporary.RentPageHowSearchFormDTO;
import uz.result.rmcdeluxe.repository.temporary.RentHowSearchOptionRepository;
import uz.result.rmcdeluxe.repository.temporary.RentPageHowSearchFormRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentPageHowSearchFormService {

    private final RentPageHowSearchFormRepository searchFormRepository;

    private final RentHowSearchOptionRepository optionRepository;

    public ResponseEntity<ApiResponse<RentPageHowSearchForm>> create(RentPageHowSearchForm searchForm) {
        ApiResponse<RentPageHowSearchForm> response = new ApiResponse<>();
        Optional<RentPageHowSearchForm> optional = searchFormRepository.findAll().stream().findFirst();
        if (optional.isPresent()) {
            throw new AlreadyExistsException("Search form is already created");
        }
        RentPageHowSearchForm save = searchFormRepository.save(searchForm);
        response.setData(save);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> find(String lang) {
        RentPageHowSearchForm searchForm = searchFormRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Search form is not found"));
        if (lang != null) {
            ApiResponse<RentPageHowSearchFormDTO> response = new ApiResponse<>();
            response.setData(new RentPageHowSearchFormDTO(searchForm, lang));
            return ResponseEntity.ok(response);
        }
        ApiResponse<RentPageHowSearchForm> response = new ApiResponse<>();
        response.setData(searchForm);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<RentPageHowSearchForm>> update(RentPageHowSearchForm searchForm) {
        ApiResponse<RentPageHowSearchForm> response = new ApiResponse<>();
        RentPageHowSearchForm fromDb = searchFormRepository.findById(searchForm.getId())
                .orElseThrow(() -> new NotFoundException("Search form is not found by id: " + searchForm.getId()));

        if (searchForm.getOptions() != null) {
            List<RentHowSearchOption> searchFormOptions = searchForm.getOptions();
            List<RentHowSearchOption> fromDbOptions = fromDb.getOptions();
            List<RentHowSearchOption> optionsToRemove = new ArrayList<>();

            for (RentHowSearchOption searchFormOption : searchFormOptions) {
                if (searchFormOption.getId() != null) {
                    for (RentHowSearchOption fromDbOption : fromDbOptions) {
                        if (fromDbOption.getId().equals(searchFormOption.getId())) {

                            if (searchFormOption.getTitleUz() != null) {
                                fromDbOption.setTitleUz(searchFormOption.getTitleUz());
                            }
                            if (searchFormOption.getTitleRu() != null) {
                                fromDbOption.setTitleRu(searchFormOption.getTitleRu());
                            }
                            if (searchFormOption.getTitleEn() != null) {
                                fromDbOption.setTitleEn(searchFormOption.getTitleEn());
                            }

                            if (searchFormOption.getDescriptionUz() != null) {
                                fromDbOption.setDescriptionUz(searchFormOption.getDescriptionUz());
                            }
                            if (searchFormOption.getDescriptionRu() != null) {
                                fromDbOption.setDescriptionRu(searchFormOption.getDescriptionRu());
                            }
                            if (searchFormOption.getDescriptionEn() != null) {
                                fromDbOption.setDescriptionEn(searchFormOption.getDescriptionEn());
                            }

                            if (searchFormOption.getTitleUz() == null && searchFormOption.getTitleRu() == null && searchFormOption.getTitleEn() == null &&
                                    searchFormOption.getDescriptionUz() == null && searchFormOption.getDescriptionRu() == null && searchFormOption.getDescriptionEn() == null) {
                                optionsToRemove.add(fromDbOption);
                            }

                        }
                    }
                }
                else {
                    searchFormOption.setSearchForm(fromDb);
                    fromDbOptions.add(searchFormOption);
                }
            }
            for (RentHowSearchOption removeOption : optionsToRemove) {
                fromDbOptions.remove(removeOption);
                optionRepository.deleteByCustom(removeOption.getId());
            }
        }
        response.setData(searchFormRepository.save(fromDb));
        return ResponseEntity.ok(response);

    }

    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<?> response = new ApiResponse<>();
        RentPageHowSearchForm searchForm = searchFormRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Search form is not found"));
        searchFormRepository.delete(searchForm);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
