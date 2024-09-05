package uz.result.rmcdeluxe.service.temporary.investment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.result.rmcdeluxe.entity.temporary.investment.*;
import uz.result.rmcdeluxe.entity.temporary.investment.FrequentlyAskedQuestion;
import uz.result.rmcdeluxe.entity.temporary.investment.Question;
import uz.result.rmcdeluxe.exception.AlreadyExistsException;
import uz.result.rmcdeluxe.exception.NotFoundException;
import uz.result.rmcdeluxe.payload.ApiResponse;
import uz.result.rmcdeluxe.payload.temporary.investment.FrequentlyAskedQuestionDTO;
import uz.result.rmcdeluxe.repository.temporary.investment.FrequentlyAskedQuestionRepository;
import uz.result.rmcdeluxe.repository.temporary.investment.QuestionRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FrequentlyAskedQuestionService {

    private final FrequentlyAskedQuestionRepository askedQuestionRepository;

    private  final QuestionRepository questionRepository;

    private final ObjectMapper objectMapper;

    public ResponseEntity<ApiResponse<FrequentlyAskedQuestion>> create(String json) {
        ApiResponse<FrequentlyAskedQuestion> response = new ApiResponse<>();
        Optional<FrequentlyAskedQuestion> questionOptional = askedQuestionRepository.findAll().stream().findFirst();
        try {
            if (questionOptional.isPresent()) {
                throw new AlreadyExistsException("FAQ is already created");
            }
            FrequentlyAskedQuestion question = objectMapper.readValue(json, FrequentlyAskedQuestion.class);
            FrequentlyAskedQuestion save = askedQuestionRepository.save(question);
            response.setData(save);
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<ApiResponse<FrequentlyAskedQuestion>> addQuestion(String step) {
        ApiResponse<FrequentlyAskedQuestion> response = new ApiResponse<>();
        Optional<FrequentlyAskedQuestion> questionOptional = askedQuestionRepository.findAll().stream().findFirst();

        if (!questionOptional.isPresent()) {
            throw new NotFoundException("FAQ not created");
        }

        try {
            Question question = objectMapper.readValue(step, Question.class);
            Question save1 = questionRepository.save(question);
            FrequentlyAskedQuestion question1 = questionOptional.get();
            question1.getQuestions().add(save1);
            FrequentlyAskedQuestion save = askedQuestionRepository.save(question1);
            response.setData(save);
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

    public ResponseEntity<ApiResponse<?>> get(String lang) {
        FrequentlyAskedQuestion question = askedQuestionRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("FAQ not created yet!"));
        if (lang != null) {
            ApiResponse<FrequentlyAskedQuestionDTO> response = new ApiResponse<>();
            response.setData(new FrequentlyAskedQuestionDTO(question, lang));
            return ResponseEntity.ok(response);
        }
        ApiResponse<FrequentlyAskedQuestion> response = new ApiResponse<>();
        response.setData(question);
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<ApiResponse<FrequentlyAskedQuestion>> update(FrequentlyAskedQuestion askedQuestion) {
        ApiResponse<FrequentlyAskedQuestion> response = new ApiResponse<>();
        FrequentlyAskedQuestion fromDB = askedQuestionRepository.findById(askedQuestion.getId())
                .orElseThrow(() -> new NotFoundException("FAQ is not found by id: " + askedQuestion.getId()));

        if (askedQuestion.getTitleUz() != null) {
            fromDB.setTitleUz(askedQuestion.getTitleUz());
        }
        if (askedQuestion.getTitleRu() != null) {
            fromDB.setTitleRu(askedQuestion.getTitleRu());
        }
        if (askedQuestion.getTitleEn() != null) {
            fromDB.setTitleEn(askedQuestion.getTitleEn());
        }


        if (askedQuestion.getQuestions()!=null){
            List<Question> questions = askedQuestion.getQuestions();
            List<Question> questions1 = fromDB.getQuestions();
            for (Question question : questions) {
                for (Question question1 : questions1) {
                    if (Objects.equals(question.getId(), question1.getId())){
                        if (question.getTitleUz()!=null){
                            question1.setTitleUz(question.getTitleUz());
                        }
                        if (question.getTitleRu()!=null){
                            question1.setTitleRu(question.getTitleRu());
                        }
                        if (question.getTitleEn()!=null){
                            question1.setTitleEn(question.getTitleEn());
                        }
                        if (question.getDescriptionUz()!=null){
                            question1.setDescriptionUz(question.getDescriptionUz());
                        }
                        if (question.getDescriptionEn()!=null){
                            question1.setDescriptionEn(question.getDescriptionEn());
                        }
                        if (question.getDescriptionRu()!=null){
                            question1.setDescriptionRu(question.getDescriptionRu());
                        }
                    }
                }

            }
            fromDB.setQuestions(questions1);
        }



        response.setData(askedQuestionRepository.save(fromDB));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<?> response = new ApiResponse<>();
        FrequentlyAskedQuestion askedQuestion = askedQuestionRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("FAQ not created yet"));
        askedQuestionRepository.delete(askedQuestion);
        response.setMessage("Successfully deleted");
        return ResponseEntity.ok(response);
    }

}
