package net.javaspringboot.kpis_be01.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "kpi_room_result")
public class KpiRoomResult implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private String room_name;

    @Column
    private String room_symbol;

    @Column
    private double result_average;

    @Column
    private int month;

    @Column
    private int year;

    @Column
    private String created_at;

    public KpiRoomResult(int month, int year) {
        this.month = month;
        this.year = year;
    }
}
