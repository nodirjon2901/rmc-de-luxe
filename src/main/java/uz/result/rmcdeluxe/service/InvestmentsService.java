package uz.result.rmcdeluxe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.Banner;
import uz.result.rmcdeluxe.entity.BannerSlider;
import uz.result.rmcdeluxe.entity.Investments;
import uz.result.rmcdeluxe.entity.MainPageInvestment;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.InvestmentsDTO;
import uz.result.rmcdeluxe.payload.MainPageInvestmentDTO;
import uz.result.rmcdeluxe.repository.InvestmentsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvestmentsService {

    private final InvestmentsRepository investmentsRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<Investments>> create(String json, MultipartFile photoFile) {
        ApiResponse<Investments> response = new ApiResponse<>();
        Optional<Investments> investmentOptional = investmentsRepository.findAll().stream().findFirst();
        try {
            if (investmentOptional.isPresent()) {
                throw new AlreadyExistsException("Investment is already created");
            }
            Investments investments = objectMapper.readValue(json, Investments.class);
            investments.setPhoto(photoService.save(photoFile));
            Investments save = investmentsRepository.save(investments);
            response.setData(save);
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    public ResponseEntity<ApiResponse<?>> get(String lang) {
        Investments investment = investmentsRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Investment not created yet!"));
        if (lang != null) {
            ApiResponse<InvestmentsDTO> response = new ApiResponse<>();
            response.setData(new InvestmentsDTO(investment, lang));
            return ResponseEntity.ok(response);
        }
        ApiResponse<Investments> response = new ApiResponse<>();
        response.setData(investment);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<Investments>> update(Investments investment) {
        ApiResponse<Investments> response = new ApiResponse<>();
        Investments fromDB = investmentsRepository.findById(investment.getId())
                .orElseThrow(() -> new NotFoundException("Investment is not found by id: " + investment.getId()));

        if (investment.getHeadingUz() != null) {
            fromDB.setHeadingUz(investment.getHeadingUz());
        }
        if (investment.getHeadingRu() != null) {
            fromDB.setHeadingRu(investment.getHeadingRu());
        }
        if (investment.getHeadingEn() != null) {
            fromDB.setHeadingEn(investment.getHeadingEn());
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


        response.setData(investmentsRepository.save(fromDB));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<?> response = new ApiResponse<>();
        Investments investment = investmentsRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Investment not created yet"));
        investmentsRepository.delete(investment);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
