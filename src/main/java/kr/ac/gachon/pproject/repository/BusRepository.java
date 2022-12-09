package kr.ac.gachon.pproject.repository;

import kr.ac.gachon.pproject.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Long> {
    Bus findByAppId(String appId);
}
