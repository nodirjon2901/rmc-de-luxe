package uz.result.rmcdeluxe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.result.rmcdeluxe.entity.MainPageAdvantage;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.repository.MainPageAdvantageRepository;

@Service
@RequiredArgsConstructor
public class MainPageAdvantageService {

    private final MainPageAdvantageRepository advantageRepository;


}
