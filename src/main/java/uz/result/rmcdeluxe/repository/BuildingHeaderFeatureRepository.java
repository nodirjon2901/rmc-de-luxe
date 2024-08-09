package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.BuildingHeaderFeature;

@Repository
public interface BuildingHeaderFeatureRepository extends JpaRepository<BuildingHeaderFeature, Long> {
}
