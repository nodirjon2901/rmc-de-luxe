package uz.result.rmcdeluxe.repository.temporary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.temporary.BuildingPageOverview;

@Repository
public interface BuildingPageOverviewRepository extends JpaRepository<BuildingPageOverview, Long> {
}
