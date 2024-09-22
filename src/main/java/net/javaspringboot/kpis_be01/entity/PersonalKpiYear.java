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
@Table(name = "result_personal_kpi_year")
public class PersonalKpiYear implements Serializable {
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
    private double kpi_room_year;//kpi tb năm của khoa/phòng

    @Column
    private double kpi_mt_average_year;//kpi trung bình năm trong 12 tháng

    @Column
    private double kpi_mn;//kpi năm của cá nhân

    @Column
    private double resultKPI;

    @Column
    private int year;

    @Column
    private String date;

    @Column
    private String rank_code;
}
