package uz.result.rmcdeluxe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.result.rmcdeluxe.entity.Button;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.service.CounterService;

@RestController
@RequestMapping("/api/counter")
@RequiredArgsConstructor
public class CounterController {

    private final CounterService counterService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<?>> addCallNum(
            @RequestParam(value = "button") Button button
    ) {
        return counterService.addCallNumber(button);
    }

}
