package uz.result.rmcdeluxe.repository.temporary.investment;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.result.rmcdeluxe.entity.temporary.investment.Investments;

public interface InvestmentsRepository extends JpaRepository<Investments,Long> {
}
