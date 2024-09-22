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
@Table(name = "self_assess_manager")
public class SelfAssessManager {
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
    private int gan_ket_tao_dong_luc_nv;
    @Column
    private int chat_luong_to_chuc_phan_cong_cv;
    @Column
    private int ktra_giam_sat_chuyen_mon_nv;
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
}
