package uz.result.rmcdeluxe.repository.investment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.result.rmcdeluxe.entity.investment.IntroductionDescription;

public interface IntroductionDescriptionRepository extends JpaRepository<IntroductionDescription,Long> {

    @Modifying
    @Query(value = "insert into  Investment_introduction_descriptions (investment_introduction_id, description_id) VALUES (?, ?)",nativeQuery = true)
    void setId(Long introductionId,Long descriptionId);
}
