package uz.result.rmcdeluxe.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.BlogOption;

@Repository
public interface BlogOptionRepository extends JpaRepository<BlogOption, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from blog_option where id=:id", nativeQuery = true)
    void deleteCustom(@Param("id") Long id);
}
