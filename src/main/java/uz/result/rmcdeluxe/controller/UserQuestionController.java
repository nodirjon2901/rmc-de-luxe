package uz.result.rmcdeluxe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.result.rmcdeluxe.entity.UserQuestion;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.service.UserQuestionService;

import java.util.List;

@RestController
@RequestMapping("/v1/user-question")
@RequiredArgsConstructor
public class UserQuestionController {

    private final UserQuestionService userQuestionService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UserQuestion>> create(
            @RequestBody UserQuestion question
    ) {
        return userQuestionService.create(question);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<UserQuestion>>> findAll() {
        return userQuestionService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> delete(
            @PathVariable Long id
    ) {
        return userQuestionService.delete(id);
    }

}
