package uz.result.rmcdeluxe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.result.rmcdeluxe.bot.RmcBot;
import uz.result.rmcdeluxe.entity.Application;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.repository.ApplicationRepository;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final RmcBot bot;

    public ResponseEntity<ApiResponse<Application>> create(Application application) {
        ApiResponse<Application> response = new ApiResponse<>();
        Application save = applicationRepository.save(application);
        bot.handleSendApplication(save);
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
