package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.MainPageInvestment;

@Repository
public interface MainPageInvestmentRepository extends JpaRepository<MainPageInvestment, Long> {
}
