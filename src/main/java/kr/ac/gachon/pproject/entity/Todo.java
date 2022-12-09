package kr.ac.gachon.pproject.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@ToString
public class Todo {
    @Id
    @Column(name = "todo_index")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String appId;

    @Column(nullable = false)
    private String todos;
}
