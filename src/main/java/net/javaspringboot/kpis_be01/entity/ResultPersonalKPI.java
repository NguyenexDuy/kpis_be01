package net.javaspringboot.kpis_be01.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ket_qua_kpi_ca_nhan")
public class ResultPersonalKPI implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String staff_code;

    @Column
    private String fullname;

    @Column
    @NotBlank
    private String room_name;
    @Column
    @NotBlank
    private String room_symbol;

    @Column
    private double team_mark_assess;

    @Column
    private double cs1;

    @Column
    private double cs2;

    @Column
    private double cs3;

    @Column
    private double resultKPI;

    @Column
    private int month;

    @Column
    private int year;

    @Column
    private String date;

    @Column
    private String time_submit;//ngày submit đánh giá

    @Column
    private String rank_code;

    public ResultPersonalKPI(String staff_code, String fullname, String room_name, String room_symbol, int month, int year, String date, String rank_code) {
        this.staff_code = staff_code;
        this.fullname = fullname;
        this.room_name = room_name;
        this.room_symbol = room_symbol;
        this.month = month;
        this.year = year;
        this.date = date;
        this.rank_code = rank_code;
    }
}
