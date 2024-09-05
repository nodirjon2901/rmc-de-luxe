package uz.result.rmcdeluxe.service.temporary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.temporary.MainPageInvestment;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.temporary.MainPageInvestmentDTO;
import uz.result.rmcdeluxe.repository.temporary.MainPageInvestmentRepository;
import uz.result.rmcdeluxe.service.PhotoService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainPageInvestmentService {

    private final MainPageInvestmentRepository investmentRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<MainPageInvestment>> create(String json, MultipartFile mainPhoto, MultipartFile photo) {
        ApiResponse<MainPageInvestment> response = new ApiResponse<>();
        Optional<MainPageInvestment> investmentOptional = investmentRepository.findAll().stream().findFirst();
        try {
            if (investmentOptional.isPresent()) {
                throw new AlreadyExistsException("Investment is already created");
            }
            MainPageInvestment mainPageInvestment = objectMapper.readValue(json, MainPageInvestment.class);
            mainPageInvestment.setMainPhoto(photoService.save(mainPhoto));
            mainPageInvestment.setPhoto(photoService.save(photo));
            MainPageInvestment save = investmentRepository.save(mainPageInvestment);
            response.setData(save);
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> get(String lang) {
        MainPageInvestment investment = investmentRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Investment not created yet!"));
        if (lang != null) {
            ApiResponse<MainPageInvestmentDTO> response = new ApiResponse<>();
            response.setData(new MainPageInvestmentDTO(investment, lang));
            return ResponseEntity.ok(response);
        }
        ApiResponse<MainPageInvestment> response = new ApiResponse<>();
        response.setData(investment);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<MainPageInvestment>> update(MainPageInvestment investment) {
        ApiResponse<MainPageInvestment> response = new ApiResponse<>();
        MainPageInvestment fromDB = investmentRepository.findById(investment.getId())
                .orElseThrow(() -> new NotFoundException("Investment is not found by id: " + investment.getId()));

        if (investment.getTitleUz() != null) {
            fromDB.setTitleUz(investment.getTitleUz());
        }
        if (investment.getTitleRu() != null) {
            fromDB.setTitleRu(investment.getTitleRu());
        }
        if (investment.getTitleEn() != null) {
            fromDB.setTitleEn(investment.getTitleEn());
        }

        if (investment.getDescriptionUz() != null) {
            fromDB.setDescriptionUz(investment.getDescriptionUz());
        }
        if (investment.getDescriptionRu() != null) {
            fromDB.setDescriptionRu(investment.getDescriptionRu());
        }
        if (investment.getDescriptionEn() != null) {
            fromDB.setDescriptionEn(investment.getDescriptionEn());
        }

        if (investment.getLink() != null) {
            fromDB.setLink(investment.getLink());
        }

        response.setData(investmentRepository.save(fromDB));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<?> response = new ApiResponse<>();
        MainPageInvestment investment = investmentRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Investment not created yet"));
        investmentRepository.delete(investment);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
