package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Photo findByIdOrName(Long id, String name);

}
