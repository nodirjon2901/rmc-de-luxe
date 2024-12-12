package uz.result.rmcdeluxe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.result.rmcdeluxe.bot.RmcBot;
import uz.result.rmcdeluxe.entity.Application;
import uz.result.rmcdeluxe.entity.ApplicationForToRent;
import uz.result.rmcdeluxe.entity.ApplicationOfInvestment;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.repository.ApplicationForToRentRepository;
import uz.result.rmcdeluxe.repository.ApplicationOfInvestmentRepository;
import uz.result.rmcdeluxe.repository.ApplicationRepository;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final ApplicationOfInvestmentRepository applicationOfInvestmentRepository;

    private final ApplicationForToRentRepository applicationForToRentRepository;

    private final RmcBot bot;

    public ResponseEntity<ApiResponse<Application>> create(Application application) {
        ApiResponse<Application> response = new ApiResponse<>();
        Application save = applicationRepository.save(application);
        bot.handleSendApplication(save);
        response.setMessage("Successfully created");
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<ApplicationForToRent>> createToRentApp(ApplicationForToRent applicationForToRent) {
        ApiResponse<ApplicationForToRent> response = new ApiResponse<>();
        ApplicationForToRent save = applicationForToRentRepository.save(applicationForToRent);
        bot.handleSendApplicationForToRent(save);
        response.setMessage("Successfully created");
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<ApplicationForToRent>> createToSellApp(ApplicationForToRent applicationForToRent) {
        ApiResponse<ApplicationForToRent> response = new ApiResponse<>();
        ApplicationForToRent save = applicationForToRentRepository.save(applicationForToRent);
        bot.handleSendApplicationForToSell(save);
        response.setMessage("Successfully created");
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<ApplicationOfInvestment>> createInvApplication(ApplicationOfInvestment applicationOfInvestment) {
        ApiResponse<ApplicationOfInvestment> response = new ApiResponse<>();
        ApplicationOfInvestment save = applicationOfInvestmentRepository.save(applicationOfInvestment);
        bot.handleSendInvestmentApplication(save);
        response.setMessage("Successfully created");
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<?>> delete(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Application is not found with id: " + id));
        applicationRepository.delete(application);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
