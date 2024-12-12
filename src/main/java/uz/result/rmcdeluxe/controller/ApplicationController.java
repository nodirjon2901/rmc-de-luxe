package uz.result.rmcdeluxe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.result.rmcdeluxe.entity.Application;
import uz.result.rmcdeluxe.entity.ApplicationForToRent;
import uz.result.rmcdeluxe.entity.ApplicationOfInvestment;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.service.ApplicationService;

@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Application>> create(
            @RequestBody Application application
    ) {
        return applicationService.create(application);
    }

    @PostMapping("/investment/create")
    public ResponseEntity<ApiResponse<ApplicationOfInvestment>> createInvestmentApp(
            @RequestBody ApplicationOfInvestment applicationOfInvestment
    ) {
        return applicationService.createInvApplication(applicationOfInvestment);
    }

    @PostMapping("/to-rent/create")
    public ResponseEntity<ApiResponse<ApplicationForToRent>> createToRentApp(
            @RequestBody ApplicationForToRent applicationForToRent
    ) {
        return applicationService.createToRentApp(applicationForToRent);
    }

    @PostMapping("/to-sell/create")
    public ResponseEntity<ApiResponse<ApplicationForToRent>> createToSellApp(
            @RequestBody ApplicationForToRent applicationForToRent
    ) {
        return applicationService.createToSellApp(applicationForToRent);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> delete(
            @PathVariable Long id
    ) {
        return applicationService.delete(id);
    }

}
