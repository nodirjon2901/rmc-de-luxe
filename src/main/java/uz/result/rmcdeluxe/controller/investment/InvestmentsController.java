package uz.result.rmcdeluxe.controller.investment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.investment.Investments;
import uz.result.rmcdeluxe.entity.investment.InvestmentsIntroduction;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.service.investment.InvestmentsIntroductionService;
import uz.result.rmcdeluxe.service.investment.InvestmentsService;

@RestController
@RequestMapping("/v1/investments")
@RequiredArgsConstructor
public class InvestmentsController {

    private final InvestmentsService investmentsService;



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


}
