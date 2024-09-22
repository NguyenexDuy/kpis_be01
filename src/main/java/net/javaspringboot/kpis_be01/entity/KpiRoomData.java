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
@Table(name = "kpi_room_data")
public class KpiRoomData implements Serializable {
    //tên chỉ số KPI mà các K/P tự thiết kế mỗi tháng
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotBlank
    private String kpi_name;

    @ManyToOne
    @JoinColumn(name = "kpi_name_id", referencedColumnName = "Id")
    private NameListKPI kpi_name_id;

    @Column
    @NotBlank
    private String kpi_type; // loại kpi (tháng,quý,năm)

    @Column
    private String room_report;//ten phong bao cao chi so kpi
    @Column
    @NotBlank
    private String room_report_symbol;

    @Column
    private String room_responsible;//ten phong chiu trach nhiem kpi
    @Column
    @NotBlank
    private String room_responsible_symbol;

    @Column
    private double ts_thuc_hien;
    @Column
    private double ms_chung;
    @Column
    private double chi_tieu;//chỉ tiêu để nhập liệu
    @Column
    private String currency;//don_vi_tinh
    @Column
    private String compare_type;// kiểu so sánh
    @Column(columnDefinition = "TEXT")// gán kiểu text cho colum trong database
    private String goal;//mô tả chỉ tiêu tháng
    @Column
    private double kq_cs;
    @Column
    private double diem_hieu_chinh;// điểm hiệu chỉnh (điểm trừ tính vào kqcs)
    @Column
    private double kq_kpi;// kết quả kpi theo tháng
    @Column
    private int month;
    @Column
    private int year;
    @Column
    private String created_at;
    @Column
    private String created_by;
    @Column
    private String time_submit;//ngày submit kết quả

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User username;

    public KpiRoomData(String room_report_symbol, String room_responsible_symbol) {
        this.room_report_symbol = room_report_symbol;
        this.room_responsible_symbol = room_responsible_symbol;
    }

    public KpiRoomData(String room_report_symbol) {
        this.room_report_symbol = room_report_symbol;
    }

    public KpiRoomData(String kpi_name, String room_report_symbol, String room_report, String room_responsible_symbol, String room_responsible, String goal, int month, int year) {
        this.kpi_name = kpi_name;
        this.room_report_symbol = room_report_symbol;
        this.room_report = room_report;
        this.room_responsible_symbol = room_responsible_symbol;
        this.room_responsible = room_responsible;
        this.goal = goal;
        this.month = month;
        this.year = year;
    }

    public KpiRoomData(String kpi_name, String kpi_type, String room_report, String room_report_symbol, String room_responsible, String room_responsible_symbol, String goal, String compare_type, int month, int year) {
        this.kpi_name = kpi_name;
        this.kpi_type = kpi_type;
        this.room_report = room_report;
        this.room_report_symbol = room_report_symbol;
        this.room_responsible = room_responsible;
        this.room_responsible_symbol = room_responsible_symbol;
        this.goal = goal;
        this.compare_type = compare_type;
        this.month = month;
        this.year = year;
    }

    public KpiRoomData(Long id, String kpi_name, String kpi_type, String room_report, String room_report_symbol, String room_responsible, String room_responsible_symbol, String goal, String compare_type, int month, int year) {
        this.id = id;
        this.kpi_name = kpi_name;
        this.kpi_type = kpi_type;
        this.room_report = room_report;
        this.room_report_symbol = room_report_symbol;
        this.room_responsible = room_responsible;
        this.room_responsible_symbol = room_responsible_symbol;
        this.goal = goal;
        this.compare_type = compare_type;
        this.month = month;
        this.year = year;
    }
}