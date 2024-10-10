package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.ApplicationOfInvestment;

import java.time.LocalDateTime;

@Repository
public interface ApplicationOfInvestmentRepository extends JpaRepository<ApplicationOfInvestment, Long> {

    @Query(value = "select count(*) from investment_application where created_date>=:startDate and created_date<=:endDate", nativeQuery = true)
    Long countApplicationInOneWeek(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


}
