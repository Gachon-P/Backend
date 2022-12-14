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
public class UserOption {
    @Id
    @Column(name = "user_option_index")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String appId;

    @Column(nullable = false)
    private String sensorStart;
    @Column(nullable = false)
    private String sensorEnd;
    @Column(nullable = false)
    private String todoKeyword;
    @Column(nullable = false)
    private String weatherKeyword;
    @Column(nullable = false)
    private String busKeyword;
    @Column(nullable = false)
    private String newsKeyword;
}
