package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.BannerSlider;

@Repository
public interface BannerSliderRepository extends JpaRepository<BannerSlider, Long> {
    
}
