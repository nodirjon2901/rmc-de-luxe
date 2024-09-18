package uz.result.rmcdeluxe.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.Blog;

import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    Optional<Blog> findBySlug(String slug);

    @Modifying
    @Transactional
    @Query(value = "update blog set slug=:slug where id=:id", nativeQuery = true)
    void updateSlugById(@Param("id") Long id, @Param("slug") String slug);

    @Modifying
    @Transactional
    @Query(value = "update blog b set view_counter=view_counter+1 where b.id=:id", nativeQuery = true)
    void updateViewBlog(@Param("id") Long id);
}
