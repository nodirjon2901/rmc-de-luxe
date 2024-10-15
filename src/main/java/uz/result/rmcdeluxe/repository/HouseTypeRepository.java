package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.District;
import uz.result.rmcdeluxe.entity.HouseType;

import java.util.Optional;

@Repository
public interface HouseTypeRepository extends JpaRepository<HouseType, Long> {

    @Query("SELECT h FROM house_type h " +
            "WHERE LOWER(h.nameUz) = LOWER(:name) " +
            "OR LOWER(h.nameRu) = LOWER(:name) " +
            "OR LOWER(h.nameEn) = LOWER(:name)")
    Optional<HouseType> findByNameIgnoreCase(@Param("name") String name);

}
