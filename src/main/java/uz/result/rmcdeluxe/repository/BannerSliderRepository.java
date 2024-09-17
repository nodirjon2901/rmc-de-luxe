package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.BannerSlider;

import java.util.Optional;

@Repository
public interface BannerSliderRepository extends JpaRepository<BannerSlider, Long> {

    @Query(value = "select Max(orderNum) from banner_slider ")
    Optional<Integer> getMaxOrderNum();

}
