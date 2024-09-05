package uz.result.rmcdeluxe.repository.temporary;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.temporary.RentHowSearchOption;

@Repository
public interface RentHowSearchOptionRepository extends JpaRepository<RentHowSearchOption, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from rent_how_search_option where id=:id", nativeQuery = true)
    void deleteByCustom(@Param("id") Long id);
}
