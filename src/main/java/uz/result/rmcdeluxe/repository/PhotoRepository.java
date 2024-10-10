package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.result.rmcdeluxe.entity.Photo;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Optional<Photo> findByName(String name);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM photo WHERE id= :id", nativeQuery = true)
    void delete(Long id);

    Optional<Photo> findByHttpUrl(String url);

    @Modifying
    @Query(value = "UPDATE photo SET http_url= :url WHERE id= :id", nativeQuery = true)
    void setUrl(String url, Long id);

    @Modifying
    @jakarta.transaction.Transactional
    @Query(value = "delete from photo where id=:id", nativeQuery = true)
    void deleteByCustom(@Param("id") Long id);

}
