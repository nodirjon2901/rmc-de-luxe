package uz.result.rmcdeluxe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.Investments;
import uz.result.rmcdeluxe.entity.InvestmentsIntroduction;
import uz.result.rmcdeluxe.entity.MainPageInvestment;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.service.InvestmentsIntroductionService;
import uz.result.rmcdeluxe.service.InvestmentsService;

@RestController
@RequestMapping("/v1/investments")
@RequiredArgsConstructor
public class InvestmentsController {

    private final InvestmentsService investmentsService;

    private final InvestmentsIntroductionService introductionService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Investments>> createInvestment(
            @RequestParam(value = "json") String investment,
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return investmentsService.create(investment, photo);
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<?>> getInvestment(
            @RequestHeader(value = "Accept-Language",required = false) String lang
    ) {
        return investmentsService.get(lang);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Investments>> update(
            @RequestBody Investments investments
    ) {
        return investmentsService.update(investments);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<?>> deleteInvestment() {
        return investmentsService.delete();
    }

    @PostMapping("/introduction/create")
    public ResponseEntity<ApiResponse<InvestmentsIntroduction>> createIntroduction(
            @RequestParam(value = "json") String introduction,
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return introductionService.create(introduction, photo);
    }

    @PostMapping("/introduction/add-description")
    public ResponseEntity<ApiResponse<InvestmentsIntroduction>> addDescription(
            @RequestParam(value = "json") String description
    )
    {
        return introductionService.addDescription(description);
    }

    @GetMapping("/introduction/get")
    public ResponseEntity<ApiResponse<?>> getIntroduction(
            @RequestHeader(value = "Accept-Language",required = false) String lang
    ) {
        return introductionService.get(lang);
    }

  /*  @PutMapping("/update")
    public ResponseEntity<ApiResponse<InvestmentsIntroduction>> updateIntroduction(
            @RequestBody InvestmentsIntroduction introduction
    ) {
        return introductionService.update(introduction);
    }*/

    @DeleteMapping("/investment/delete")
    public ResponseEntity<ApiResponse<?>> deleteIntroduction() {
        return introductionService.delete();
    }

}
