package uz.result.rmcdeluxe.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.ToRentWorkProcessOption;

@Repository
public interface ToRentWorkProcessOptionRepository extends JpaRepository<ToRentWorkProcessOption, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from to_rent_work_process_option where id=:id", nativeQuery = true)
    void deleteByCustom(@Param("id") Long id);

}
