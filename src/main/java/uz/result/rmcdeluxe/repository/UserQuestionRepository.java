package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.UserQuestion;

@Repository
public interface UserQuestionRepository extends JpaRepository<UserQuestion, Long> {
}
