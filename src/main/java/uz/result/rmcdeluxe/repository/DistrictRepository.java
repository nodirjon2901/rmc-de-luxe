package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.District;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    @Query("SELECT d FROM district d " +
            "WHERE LOWER(d.nameUz) = LOWER(:name) " +
            "OR LOWER(d.nameRu) = LOWER(:name) " +
            "OR LOWER(d.nameEn) = LOWER(:name)")
    Optional<District> findByNameIgnoreCase(@Param("name") String name);

}
