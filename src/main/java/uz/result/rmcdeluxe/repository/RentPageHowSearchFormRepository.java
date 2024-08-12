package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.RentPageHowSearchForm;

@Repository
public interface RentPageHowSearchFormRepository extends JpaRepository<RentPageHowSearchForm, Long> {
}
