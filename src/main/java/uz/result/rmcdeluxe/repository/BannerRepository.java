package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
}
