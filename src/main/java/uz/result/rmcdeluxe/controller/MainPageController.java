package uz.result.rmcdeluxe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.MainPageInvestment;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.service.MainPageInvestmentService;

@RestController
@RequestMapping("/v1/main-page")
@RequiredArgsConstructor
public class MainPageController {

    private final MainPageInvestmentService investmentService;

    @PostMapping("/investment/create")
    public ResponseEntity<ApiResponse<MainPageInvestment>> createInvestment(
            @RequestParam(value = "json") String investment,
            @RequestPart(value = "main-photo") MultipartFile mainPhoto,
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return investmentService.create(investment, mainPhoto, photo);
    }

    @GetMapping("/investment/get")
    public ResponseEntity<ApiResponse<?>> getInvestment(
            @RequestHeader(value = "Accept-Language",required = false) String lang
    ) {
        return investmentService.get(lang);
    }

    @PutMapping("/investment/update")
    public ResponseEntity<ApiResponse<MainPageInvestment>> updateInvestment(
            @RequestBody MainPageInvestment investment
    ) {
        return investmentService.update(investment);
    }

    @DeleteMapping("/investment/delete")
    public ResponseEntity<ApiResponse<?>> deleteInvestment() {
        return investmentService.delete();
    }

}
