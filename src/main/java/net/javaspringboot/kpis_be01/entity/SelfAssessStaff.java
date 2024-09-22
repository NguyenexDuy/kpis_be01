package net.javaspringboot.kpis_be01.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="self_assess_staff")
//phần tự đánh giá cho nhân viên
public class SelfAssessStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotBlank
    private String staff_code;
    @Column
    private String name;
    @Column
    @NotBlank
    private String rank;
    @Column
    private String group_rank;
    @Column
    private int month;
    @Column
    private int year;
    @Column
    private int ky_luat_va_thuong;
    @Column
    private int muc_do_phoi_hop;
    @Column
    private int chat_luong_chuyen_mon;
    @Column
    private int diem_muc_do_hoc_tap_pt;
    @Column(columnDefinition = "TEXT")
    private String note;

    @Column
    @NotBlank
    private String room_name;
    @Column
    @NotBlank
    private String room_symbol;
    @Column
    private String created_at;
    @Column
    private String created_by;//dùng username để get unique value
    @Column
    private String time_submit;//ngày submit đánh giá

    public SelfAssessStaff(String staff_code, String name, String rank, String group_rank, int month, int year, int ky_luat_va_thuong,
                           int muc_do_phoi_hop, int chat_luong_chuyen_mon, int diem_muc_do_hoc_tap_pt, String note,
                           String room_name, String room_symbol) {
        this.staff_code = staff_code;
        this.name = name;
        this.rank = rank;
        this.group_rank = group_rank;
        this.month = month;
        this.year = year;
        this.ky_luat_va_thuong = ky_luat_va_thuong;
        this.muc_do_phoi_hop = muc_do_phoi_hop;
        this.chat_luong_chuyen_mon = chat_luong_chuyen_mon;
        this.diem_muc_do_hoc_tap_pt = diem_muc_do_hoc_tap_pt;
        this.note = note;
        this.room_name = room_name;
        this.room_symbol = room_symbol;
    }
}
