package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.BlogType;
import uz.result.rmcdeluxe.entity.District;

import java.util.Optional;

@Repository
public interface BlogTypeRepository extends JpaRepository<BlogType, Long> {

    @Query("SELECT b FROM blog_type b " +
            "WHERE LOWER(b.nameUz) = LOWER(:name) " +
            "OR LOWER(b.nameRu) = LOWER(:name) " +
            "OR LOWER(b.nameEn) = LOWER(:name)")
    Optional<BlogType> findByNameIgnoreCase(@Param("name") String name);

}
