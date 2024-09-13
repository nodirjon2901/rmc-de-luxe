package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.HouseType;

@Repository
public interface HouseTypeRepository extends JpaRepository<HouseType, Long> {



}
