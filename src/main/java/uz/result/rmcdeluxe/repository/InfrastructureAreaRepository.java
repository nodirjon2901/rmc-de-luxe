package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.InfrastructureArea;

import java.util.Optional;

@Repository
public interface InfrastructureAreaRepository extends JpaRepository<InfrastructureArea, Long> {

    boolean existsByBuildingId(Long buildingId);

}
