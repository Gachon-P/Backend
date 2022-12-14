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
    private String appId;

    @Column(nullable = false)
    private String lineNumber;

    @Column(nullable = false)
    private String stationName;

    @Column(nullable = false)
    private String stationId;

    @Column(nullable = false)
    private String routeId;

    @Column(nullable = false)
    private String staOrder;
}
