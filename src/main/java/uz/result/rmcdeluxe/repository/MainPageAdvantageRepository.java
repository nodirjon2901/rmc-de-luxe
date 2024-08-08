package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.MainPageAdvantage;

@Repository
public interface MainPageAdvantageRepository extends JpaRepository<MainPageAdvantage, Long> {
}
