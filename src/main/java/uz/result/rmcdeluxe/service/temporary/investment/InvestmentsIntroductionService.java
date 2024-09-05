package uz.result.rmcdeluxe.service.temporary.investment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.temporary.investment.IntroductionDescription;
import uz.result.rmcdeluxe.entity.temporary.investment.InvestmentsIntroduction;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.temporary.investment.InvestmentsIntroductionDTO;
import uz.result.rmcdeluxe.repository.temporary.investment.IntroductionDescriptionRepository;
import uz.result.rmcdeluxe.repository.temporary.investment.InvestmentsIntroductionRepository;
import uz.result.rmcdeluxe.service.PhotoService;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvestmentsIntroductionService {

    private final InvestmentsIntroductionRepository introductionRepository;

    private final IntroductionDescriptionRepository descriptionRepository;

    private final PhotoService photoService;

    private final ObjectMapper objectMapper;


    public ResponseEntity<ApiResponse<InvestmentsIntroduction>> create(String json, MultipartFile photoFile) {
        ApiResponse<InvestmentsIntroduction> response = new ApiResponse<>();
        Optional<InvestmentsIntroduction> investmentsIntroductionOptional = introductionRepository.findAll().stream().findFirst();
        try {
            if (investmentsIntroductionOptional.isPresent()) {
                throw new AlreadyExistsException("Investment introduction is already created");
            }
            InvestmentsIntroduction investmentsIntroduction = objectMapper.readValue(json, InvestmentsIntroduction.class);
            investmentsIntroduction.setPhoto(photoService.save(photoFile));
            InvestmentsIntroduction save = introductionRepository.save(investmentsIntroduction);
            response.setData(save);
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<ApiResponse<InvestmentsIntroduction>> addDescription(String descriptions) {
        ApiResponse<InvestmentsIntroduction> response = new ApiResponse<>();
        Optional<InvestmentsIntroduction> investmentsIntroductionOptional = introductionRepository.findAll().stream().findFirst();

        if (!investmentsIntroductionOptional.isPresent()) {
            throw new NotFoundException("Investment introduction not created");
        }

        try {
            IntroductionDescription  introductionDescription = objectMapper.readValue(descriptions, IntroductionDescription.class);
            IntroductionDescription save1 = descriptionRepository.save(introductionDescription);
            InvestmentsIntroduction investmentsIntroduction = investmentsIntroductionOptional.get();
            investmentsIntroduction.getDescriptions().add(save1);
            InvestmentsIntroduction save = introductionRepository.save(investmentsIntroduction);

            response.setData(save);
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }


    public ResponseEntity<ApiResponse<?>> get(String lang) {
        InvestmentsIntroduction introduction =introductionRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Investment not created yet!"));
        if (lang != null) {
            ApiResponse<InvestmentsIntroductionDTO> response = new ApiResponse<>();
            response.setData(new InvestmentsIntroductionDTO(introduction, lang));
            return ResponseEntity.ok(response);
        }
        ApiResponse<InvestmentsIntroduction> response = new ApiResponse<>();
        response.setData(introduction);
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<ApiResponse<InvestmentsIntroduction>> update(InvestmentsIntroduction introduction) {
        ApiResponse<InvestmentsIntroduction> response = new ApiResponse<>();
        InvestmentsIntroduction fromDB = introductionRepository.findById(introduction.getId())
                .orElseThrow(() -> new NotFoundException("Investment introduction is not found by id: " + introduction.getId()));

        if (introduction.getTitleUz() != null) {
            fromDB.setTitleUz(introduction.getTitleUz());
        }
        if (introduction.getTitleRu() != null) {
            fromDB.setTitleRu(introduction.getTitleRu());
        }
        if (introduction.getTitleEng() != null) {
            fromDB.setTitleEng(introduction.getTitleEng());
        }

        if (introduction.getDescriptionUz() != null) {
            fromDB.setDescriptionUz(introduction.getDescriptionUz());
        }
        if (introduction.getDescriptionRu() != null) {
            fromDB.setDescriptionRu(introduction.getDescriptionRu());
        }
        if (introduction.getDescriptionEn() != null) {
            fromDB.setDescriptionEn(introduction.getDescriptionEn());
        }

        if (introduction.getDescriptions()!=null){
            List<IntroductionDescription> descriptions = introduction.getDescriptions();
            List<IntroductionDescription> descriptions1 = fromDB.getDescriptions();
            for (IntroductionDescription description : descriptions) {
                for (IntroductionDescription introductionDescription : descriptions1) {
                    if (Objects.equals(description.getId(), introductionDescription.getId())){
                        if (description.getDescriptionUz()!=null){
                            introductionDescription.setDescriptionUz(description.getDescriptionUz());
                        }
                        if (description.getDescriptionEn()!=null){
                            introductionDescription.setDescriptionEn(description.getDescriptionEn());
                        }
                        if (description.getDescriptionRu()!=null){
                            introductionDescription.setDescriptionRu(description.getDescriptionRu());
                        }
                    }
                }

            }
            fromDB.setDescriptions(descriptions1);
        }



        response.setData(introductionRepository.save(fromDB));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<?> response = new ApiResponse<>();
        InvestmentsIntroduction introduction = introductionRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Investment not created yet"));
        introductionRepository.delete(introduction);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }
}
