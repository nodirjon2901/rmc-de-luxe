package uz.result.rmcdeluxe.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.MainPageAboutCompanyOption;

@Repository
public interface MainPageAboutCompanyOptionRepository extends JpaRepository<MainPageAboutCompanyOption, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from main_page_company_option where id=:id", nativeQuery = true)
    void delete(@Param("id") Long id);

}
