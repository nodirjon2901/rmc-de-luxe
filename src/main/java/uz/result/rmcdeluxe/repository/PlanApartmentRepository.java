package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.PlanApartment;

@Repository
public interface PlanApartmentRepository extends JpaRepository<PlanApartment, Long> {

}
