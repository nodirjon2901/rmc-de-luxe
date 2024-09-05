package uz.result.rmcdeluxe.repository.temporary;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.temporary.RentSearchPropertyOption;

@Repository
public interface RentSearchPropertyOptionRepository extends JpaRepository<RentSearchPropertyOption, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from rent_search_property_option where id=:id", nativeQuery = true)
    void deleteByCustom(@Param("id") Long id);
}
