package uz.result.rmcdeluxe.repository.investment;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.result.rmcdeluxe.entity.investment.Investments;

public interface InvestmentsRepository extends JpaRepository<Investments,Long> {
}
