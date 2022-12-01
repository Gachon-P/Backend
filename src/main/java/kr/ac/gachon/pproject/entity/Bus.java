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
public class Bus {
    @Id
    @Column(name = "bus_index")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String lineCode;

    @Column(nullable = false)
    private String stationCode;
}
