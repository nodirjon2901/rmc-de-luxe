package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.ToRentPageHowRentForm;

@Repository
public interface ToRentPageHowRentFormRepository extends JpaRepository<ToRentPageHowRentForm, Long> {
}
