package uz.result.rmcdeluxe.repository.temporary;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.temporary.PropertyManagementOption;

@Repository
public interface PropertyManagementOptionRepository extends JpaRepository<PropertyManagementOption, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE from property_management_service where id=:id", nativeQuery = true)
    void delete(@Param("id") Long id);
}
