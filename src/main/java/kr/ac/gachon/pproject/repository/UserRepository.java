package kr.ac.gachon.pproject.repository;

import kr.ac.gachon.pproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByMacId(String macId);
}
