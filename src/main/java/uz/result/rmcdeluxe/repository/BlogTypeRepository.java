package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.BlogType;

@Repository
public interface BlogTypeRepository extends JpaRepository<BlogType, Long> {
}
