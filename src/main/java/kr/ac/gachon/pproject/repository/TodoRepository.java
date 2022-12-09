package kr.ac.gachon.pproject.repository;

import kr.ac.gachon.pproject.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Todo findByAppId(String appId);
}
