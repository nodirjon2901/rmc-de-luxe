package uz.result.rmcdeluxe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.IntroductionDescription;
import uz.result.rmcdeluxe.entity.Investments;
import uz.result.rmcdeluxe.entity.InvestmentsIntroduction;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.InvestmentsDTO;
import uz.result.rmcdeluxe.payload.InvestmentsIntroductionDTO;
import uz.result.rmcdeluxe.repository.IntroductionDescriptionRepository;
import uz.result.rmcdeluxe.repository.InvestmentsIntroductionRepository;
import uz.result.rmcdeluxe.repository.PhotoRepository;

import java.util.ArrayList;
import java.util.List;
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
        IntroductionDescription introductionDescription = null;
        try {
            introductionDescription = objectMapper.readValue(descriptions, IntroductionDescription.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        investmentsIntroductionOptional.get().setDescriptions(List.of(introductionDescription));
        InvestmentsIntroduction save = introductionRepository.save(investmentsIntroductionOptional.get());
        response.setData(save);
        return ResponseEntity.ok(response);

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

        if (introduction.getSubTitleUz() != null) {
            fromDB.setSubTitleUz(introduction.getSubTitleUz());
        }
        if (introduction.getSubTitleRu() != null) {
            fromDB.setSubTitleRu(introduction.getSubTitleRu());
        }
        if (introduction.getSubTitleEn() != null) {
            fromDB.setSubTitleEn(introduction.getSubTitleEn());
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
