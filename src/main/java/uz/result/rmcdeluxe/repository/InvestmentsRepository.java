package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.result.rmcdeluxe.entity.Investments;

public interface InvestmentsRepository extends JpaRepository<Investments,Long> {
}
