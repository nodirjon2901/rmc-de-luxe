package uz.result.rmcdeluxe.controller.investment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.investment.InvestmentsIntroduction;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.service.investment.InvestmentsIntroductionService;

@RestController
@RequestMapping("/v1/investments")
@RequiredArgsConstructor
public class InvestmentIntroductionController {

    private final InvestmentsIntroductionService introductionService;


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

    @PutMapping("/update/introduction")
    public ResponseEntity<ApiResponse<InvestmentsIntroduction>> updateIntroduction(
            @RequestBody InvestmentsIntroduction introduction
    ) {
        return introductionService.update(introduction);
    }

    @DeleteMapping("/introduction/delete")
    public ResponseEntity<ApiResponse<?>> deleteIntroduction() {
        return introductionService.delete();
    }


}
