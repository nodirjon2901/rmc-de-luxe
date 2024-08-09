package uz.result.rmcdeluxe.service.investment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.result.rmcdeluxe.entity.investment.IntroductionDescription;
import uz.result.rmcdeluxe.entity.investment.InvestmentsIntroduction;
import uz.result.rmcdeluxe.entity.investment.PurchaseProcess;
import uz.result.rmcdeluxe.entity.investment.PurchaseProcessStep;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.investment.PurchaseProcessDTO;
import uz.result.rmcdeluxe.repository.investment.PurchaseProcessRepository;
import uz.result.rmcdeluxe.repository.investment.PurchaseProcessStepRepository;


import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PurchaseProcessService {

    private final PurchaseProcessRepository processRepository;

    private final PurchaseProcessStepRepository stepRepository;

    private final ObjectMapper objectMapper;


    public ResponseEntity<ApiResponse<PurchaseProcess>> create(String json) {
        ApiResponse<PurchaseProcess> response = new ApiResponse<>();
        Optional<Integer> maxOrderNum = processRepository.getMaxOrderNum();
        Optional<PurchaseProcess> processOptional = processRepository.findAll().stream().findFirst();
        try {
            if (processOptional.isPresent()) {
                throw new AlreadyExistsException("Process is already created");
            }
            PurchaseProcess process = objectMapper.readValue(json, PurchaseProcess.class);
            process.setOrderNum(maxOrderNum.map(num -> num + 1).orElse(1));
            PurchaseProcess save = processRepository.save(process);
            response.setData(save);
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    public ResponseEntity<ApiResponse<PurchaseProcess>> addStep(String step) {
        ApiResponse<PurchaseProcess> response = new ApiResponse<>();
        Optional<PurchaseProcess> processOptional = processRepository.findAll().stream().findFirst();

        if (!processOptional.isPresent()) {
            throw new NotFoundException("Procces not created");
        }

        try {
            PurchaseProcessStep purchaseProcessStep = objectMapper.readValue(step, PurchaseProcessStep.class);
            PurchaseProcessStep save1 = stepRepository.save(purchaseProcessStep);
            PurchaseProcess process = processOptional.get();
            process.getSteps().add(save1);
            PurchaseProcess save = processRepository.save(process);
            response.setData(save);
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

    public ResponseEntity<ApiResponse<?>> get(String lang) {
        PurchaseProcess process = processRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Investment not created yet!"));
        if (lang != null) {
            ApiResponse<PurchaseProcessDTO> response = new ApiResponse<>();
            response.setData(new PurchaseProcessDTO(process, lang));
            return ResponseEntity.ok(response);
        }
        ApiResponse<PurchaseProcess> response = new ApiResponse<>();
        response.setData(process);
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<ApiResponse<PurchaseProcess>> update(PurchaseProcess process) {
        ApiResponse<PurchaseProcess> response = new ApiResponse<>();
        PurchaseProcess fromDB = processRepository.findById(process.getId())
                .orElseThrow(() -> new NotFoundException("Purchase process is not found by id: " + process.getId()));

        if (process.getTitleUz() != null) {
            fromDB.setTitleUz(process.getTitleUz());
        }
        if (process.getTitleRu() != null) {
            fromDB.setTitleRu(process.getTitleRu());
        }
        if (process.getTitleEn() != null) {
            fromDB.setTitleEn(process.getTitleEn());
        }


        if (process.getSteps()!=null){
            List<PurchaseProcessStep> steps = process.getSteps();
            List<PurchaseProcessStep> steps1 = fromDB.getSteps();
            for (PurchaseProcessStep step : steps) {
                for (PurchaseProcessStep step1 : steps1) {
                    if (Objects.equals(step.getId(), step1.getId())){
                        if (step.getTitleUz()!=null){
                            step1.setTitleUz(step.getTitleUz());
                        }
                        if (step.getTitleRu()!=null){
                            step1.setTitleRu(step.getTitleRu());
                        }
                        if (step.getTitleEn()!=null){
                            step1.setTitleEn(step.getTitleEn());
                        }
                        if (step.getDescriptionUz()!=null){
                            step1.setDescriptionUz(step.getDescriptionUz());
                        }
                        if (step.getDescriptionEn()!=null){
                            step1.setDescriptionEn(step.getDescriptionEn());
                        }
                        if (step.getDescriptionRu()!=null){
                            step1.setDescriptionRu(step.getDescriptionRu());
                        }
                    }
                }

            }
            fromDB.setSteps(steps1);
        }



        response.setData(processRepository.save(fromDB));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<?> response = new ApiResponse<>();
        PurchaseProcess process = processRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Purchase process not created yet"));
        processRepository.delete(process);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
