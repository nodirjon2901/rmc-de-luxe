package uz.result.rmcdeluxe.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.VideoFile;

@Repository
public interface VideoFileRepository extends JpaRepository<VideoFile, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from video_file where id=:id", nativeQuery = true)
    void deleteByCustom(@Param("id") Long id);
}
