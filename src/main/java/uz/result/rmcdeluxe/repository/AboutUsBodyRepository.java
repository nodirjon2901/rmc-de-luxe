package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.AboutUsBody;

@Repository
public interface AboutUsBodyRepository extends JpaRepository<AboutUsBody, Long> {


}
