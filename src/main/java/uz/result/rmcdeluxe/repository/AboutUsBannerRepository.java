package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.AboutUsBanner;

@Repository
public interface AboutUsBannerRepository extends JpaRepository<AboutUsBanner, Long> {
}