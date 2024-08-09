package uz.result.rmcdeluxe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.MainPageAboutCompany;
import uz.result.rmcdeluxe.entity.MainPageAboutCompanyOption;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.MainPageAboutCompanyDTO;
import uz.result.rmcdeluxe.repository.MainPageAboutCompanyOptionRepository;
import uz.result.rmcdeluxe.repository.MainPageAboutCompanyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainPageAboutCompanyService {

    private final MainPageAboutCompanyRepository companyRepository;

    private final MainPageAboutCompanyOptionRepository companyOptionRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<MainPageAboutCompany>> create(String json, MultipartFile photoFile) {
        ApiResponse<MainPageAboutCompany> response = new ApiResponse<>();
        Optional<MainPageAboutCompany> optionalMainPageAboutCompany = companyRepository.findAll().stream().findFirst();
        if (optionalMainPageAboutCompany.isPresent()) {
            throw new AlreadyExistsException("About company form is already created");
        }
        try {
            MainPageAboutCompany mainPageAboutCompany = objectMapper.readValue(json, MainPageAboutCompany.class);
            mainPageAboutCompany.setPhoto(photoService.save(photoFile));
            MainPageAboutCompany save = companyRepository.save(mainPageAboutCompany);
            response.setData(save);
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> find(String lang) {
        MainPageAboutCompany mainPageAboutCompany = companyRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("About company form is not created yet"));
        if (lang != null) {
            ApiResponse<MainPageAboutCompanyDTO> response = new ApiResponse<>();
            response.setData(new MainPageAboutCompanyDTO(mainPageAboutCompany, lang));
            return ResponseEntity.ok(response);
        }
        ApiResponse<MainPageAboutCompany> response = new ApiResponse<>();
        response.setData(mainPageAboutCompany);
        return ResponseEntity.ok(response);
    }

    @Transactional
    public ResponseEntity<ApiResponse<MainPageAboutCompany>> update(MainPageAboutCompany company) {
        ApiResponse<MainPageAboutCompany> response = new ApiResponse<>();
        MainPageAboutCompany fromDb = companyRepository.findById(company.getId())
                .orElseThrow(() -> new NotFoundException("About company form is not created yet with id: " + company.getId()));

        if (company.getTitleUz() != null) {
            fromDb.setTitleUz(company.getTitleUz());
        }
        if (company.getTitleRu() != null) {
            fromDb.setTitleRu(company.getTitleRu());
        }
        if (company.getTitleEn() != null) {
            fromDb.setTitleEn(company.getTitleEn());
        }

        if (company.getSubtitleUz() != null) {
            fromDb.setSubtitleUz(company.getSubtitleUz());
        }
        if (company.getSubtitleRu() != null) {
            fromDb.setSubtitleRu(company.getSubtitleRu());
        }
        if (company.getSubtitleEn() != null) {
            fromDb.setSubtitleEn(company.getSubtitleEn());
        }

        if (company.getOptions() != null) {
            List<MainPageAboutCompanyOption> companyOptions = company.getOptions();
            List<MainPageAboutCompanyOption> dbOptions = fromDb.getOptions();
            List<MainPageAboutCompanyOption> optionsToRemove = new ArrayList<>();

            for (MainPageAboutCompanyOption companyOption : companyOptions) {
                if (companyOption.getId() != null) {
                    for (MainPageAboutCompanyOption dbOption : dbOptions) {
                        if (dbOption.getId().equals(companyOption.getId())) {

                            if (companyOption.getNameUz() != null) {
                                dbOption.setNameUz(companyOption.getNameUz());
                            }
                            if (companyOption.getNameRu() != null) {
                                dbOption.setNameRu(companyOption.getNameRu());
                            }
                            if (companyOption.getNameEn() != null) {
                                dbOption.setNameEn(companyOption.getNameEn());
                            }

                            if (companyOption.getDescriptionUz() != null) {
                                dbOption.setDescriptionUz(companyOption.getDescriptionUz());
                            }
                            if (companyOption.getDescriptionRu() != null) {
                                dbOption.setDescriptionRu(companyOption.getDescriptionRu());
                            }
                            if (companyOption.getDescriptionEn() != null) {
                                dbOption.setDescriptionEn(companyOption.getDescriptionEn());
                            }

                            if (companyOption.getNameUz() == null && companyOption.getNameRu() == null && companyOption.getNameEn() == null &&
                                    companyOption.getDescriptionUz() == null && companyOption.getDescriptionRu() == null && companyOption.getDescriptionEn() == null) {
                                optionsToRemove.add(dbOption);
                            }

                        }
                    }
                } else {
                    companyOption.setAboutCompany(fromDb);
                    dbOptions.add(companyOption);
                }
            }

            for (MainPageAboutCompanyOption optionRemove : optionsToRemove) {
                dbOptions.remove(optionRemove);
                companyOptionRepository.deleteById(optionRemove.getId());
                companyOptionRepository.flush();
            }
        }

        response.setData(companyRepository.saveAndFlush(fromDb));
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<?> response = new ApiResponse<>();
        MainPageAboutCompany mainPageAboutCompany = companyRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("About company form is not created yet"));
        companyRepository.delete(mainPageAboutCompany);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
