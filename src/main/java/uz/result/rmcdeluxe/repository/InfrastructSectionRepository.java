package uz.result.rmcdeluxe.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.InfrastructSection;

@Repository
public interface InfrastructSectionRepository extends JpaRepository<InfrastructSection, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from infrastruct_section where id=:id", nativeQuery = true)
    void deleteCustom(@Param("id") Long id);
}
