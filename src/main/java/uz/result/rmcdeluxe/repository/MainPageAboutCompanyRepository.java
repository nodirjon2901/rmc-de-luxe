package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.MainPageAboutCompany;

@Repository
public interface MainPageAboutCompanyRepository extends JpaRepository<MainPageAboutCompany, Long> {
}
