package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.Counter;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Long> {

    List<Counter> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
