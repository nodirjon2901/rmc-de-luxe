package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.MainPageAboutCompanyOption;

@Repository
public interface MainPageAboutCompanyOptionRepository extends JpaRepository<MainPageAboutCompanyOption, Long> {
}
