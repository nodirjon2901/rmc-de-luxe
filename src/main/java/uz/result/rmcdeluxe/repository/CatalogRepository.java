package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.result.rmcdeluxe.entity.Catalog;

import java.util.Optional;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    Optional<Catalog> findByName(String name);

    Optional<Catalog> findBySlug(String slug);

    @Transactional
    @Modifying
    @Query(value = "update catalog set slug=:slug where id=:id", nativeQuery = true)
    void updateSlug(@Param("slug") String slug, @Param("id") Long catalogId);


}
