package uz.result.rmcdeluxe.repository.investment;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.result.rmcdeluxe.entity.investment.Question;

public interface QuestionRepository extends JpaRepository<Question,Long> {
}
