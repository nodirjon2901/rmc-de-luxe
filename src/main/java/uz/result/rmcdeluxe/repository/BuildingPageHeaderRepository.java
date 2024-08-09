package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.BuildingPageHeader;

@Repository
public interface BuildingPageHeaderRepository extends JpaRepository<BuildingPageHeader, Long> {
}
