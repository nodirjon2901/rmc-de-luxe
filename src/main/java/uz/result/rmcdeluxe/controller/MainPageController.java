package uz.result.rmcdeluxe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.result.rmcdeluxe.entity.MainPageAdvantage;
import uz.result.rmcdeluxe.entity.MainPageInvestment;
import uz.result.rmcdeluxe.entity.PropertyManagement;
import uz.result.rmcdeluxe.entity.PropertyManagementOption;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.service.MainPageAdvantageService;
import uz.result.rmcdeluxe.service.MainPageInvestmentService;
import uz.result.rmcdeluxe.service.PropertyManagementService;

@RestController
@RequestMapping("/v1/main-page")
@RequiredArgsConstructor
public class MainPageController {

    private final MainPageInvestmentService investmentService;

    private final MainPageAdvantageService advantageService;

    private final PropertyManagementService managementService;

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
            @RequestHeader(value = "Accept-Language", required = false) String lang
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

    @PostMapping("/advantage/create")
    public ResponseEntity<ApiResponse<MainPageAdvantage>> createAdvantage(
            @RequestParam(value = "json") String advantage,
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return advantageService.create(advantage, photo);
    }

    @GetMapping("/advantage/get/{id}")
    public ResponseEntity<ApiResponse<?>> findAdvantageById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ) {
        return advantageService.findById(id, lang);
    }

    @GetMapping("/advantage/get-all")
    public ResponseEntity<ApiResponse<?>> findAllAdvantage(
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ) {
        return advantageService.findAll(lang);
    }

    @PutMapping("/advantage/update")
    public ResponseEntity<ApiResponse<MainPageAdvantage>> updateAdvantage(
            @RequestBody MainPageAdvantage advantage
    ) {
        return advantageService.update(advantage);
    }

    @DeleteMapping("/advantage/delete/{id}")
    public ResponseEntity<ApiResponse<?>> delete(
            @PathVariable Long id
    ) {
        return advantageService.delete(id);
    }

    @PostMapping("/service/add")
    public ResponseEntity<ApiResponse<PropertyManagement>> addService(
            @RequestBody PropertyManagement management
    ) {
        return managementService.addOption(management);
    }

    @GetMapping("/service/get")
    public ResponseEntity<ApiResponse<?>> findService(
            @RequestHeader(value = "Accept-Language", required = false) String lang
    ) {
        return managementService.find(lang);
    }

    @PutMapping("/service/update")
    public ResponseEntity<ApiResponse<PropertyManagement>> updateService(
            @RequestBody PropertyManagement management
    ) {
        return managementService.update(management);
    }

    @DeleteMapping("/service/delete")
    public ResponseEntity<ApiResponse<?>> deleteService() {
        return managementService.delete();
    }

}
