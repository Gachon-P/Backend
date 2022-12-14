package kr.ac.gachon.pproject.repository;

import kr.ac.gachon.pproject.entity.UserOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOptionRepository extends JpaRepository<UserOption, Long> {
    UserOption findByAppId(String appId);
}
