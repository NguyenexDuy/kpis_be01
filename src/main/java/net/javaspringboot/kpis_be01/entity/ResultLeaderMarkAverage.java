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
@Table(name = "ket_qua_tap_the_leader")
public class ResultLeaderMarkAverage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String staff_code;

    @Column
    private String leader_name;

    @Column
    private String rank_code;

    @Column
    @NotBlank
    private String room_name;
    @Column
    @NotBlank
    private String room_symbol;

    @Column
    private double team_mark_assess_average;

    @Column
    private int month;

    @Column
    private int year;

    @Column
    private String date;

    @Column
    private String time_submit;//ngày làm mới đánh giá

    public ResultLeaderMarkAverage(String staff_code, String leader_name, String rank_code, String room_name, String room_symbol, int month, int year, String date, String time_submit) {
        this.staff_code = staff_code;
        this.leader_name = leader_name;
        this.rank_code = rank_code;
        this.room_name = room_name;
        this.room_symbol = room_symbol;
        this.month = month;
        this.year = year;
        this.date = date;
        this.time_submit = time_submit;
    }
}
