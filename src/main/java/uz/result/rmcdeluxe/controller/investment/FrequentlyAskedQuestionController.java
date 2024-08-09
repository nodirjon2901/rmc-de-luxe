package uz.result.rmcdeluxe.controller.investment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.result.rmcdeluxe.entity.investment.FrequentlyAskedQuestion;
import uz.result.rmcdeluxe.entity.investment.PurchaseProcess;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.service.investment.FrequentlyAskedQuestionService;
import uz.result.rmcdeluxe.service.investment.PurchaseProcessService;

@RestController
@RequestMapping("/v1/investments")
@RequiredArgsConstructor
public class FrequentlyAskedQuestionController {

    private final FrequentlyAskedQuestionService questionService;

    @PostMapping("/question/create")
    public ResponseEntity<ApiResponse<FrequentlyAskedQuestion>> createQuestion(
            @RequestParam(value = "json") String askedQuestion
    ) {
        return questionService.create(askedQuestion);
    }

    @PostMapping("/question/add-question")
    public ResponseEntity<ApiResponse<FrequentlyAskedQuestion>> addQuestion(
            @RequestParam(value = "json") String question
    )
    {
        return questionService.addQuestion(question);
    }

    @GetMapping("/question/get")
    public ResponseEntity<ApiResponse<?>> getProcess(
            @RequestHeader(value = "Accept-Language",required = false) String lang
    ) {
        return questionService.get(lang);
    }

    @PutMapping("/question/update")
    public ResponseEntity<ApiResponse<FrequentlyAskedQuestion>> updateProcess(
            @RequestBody FrequentlyAskedQuestion askedQuestion
    ) {
        return questionService.update(askedQuestion);
    }

    @DeleteMapping("/question/delete")
    public ResponseEntity<ApiResponse<?>> deleteProcess() {
        return questionService.delete();
    }
}
