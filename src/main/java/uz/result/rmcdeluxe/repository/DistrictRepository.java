package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
}
