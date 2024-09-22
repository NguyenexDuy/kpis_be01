package net.javaspringboot.kpis_be01.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "leader_assess_manager")
public class LeaderAssessManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    @NotBlank
    private String staff_code;

    @Column
    @NotBlank
    private String manager_name;

    @Column
    @NotBlank
    private String room_name_manager;

    @Column
    @NotBlank
    private String room_symbol_manager;

    @Column
    private String rank_manager;//cấp bậc (role) của nguời đc đánh giá

    @Column
    private int ky_luat_khen_thuong_manager;

    @Column
    private int ktra_giam_sat_thuc_hien_chuyen_mon_nv;

    @Column
    private int bang_chung_hoc_tap_pt_manager;

    @Column
    private double tb_tap_the_danh_gia;

    @Column(columnDefinition = "TEXT")
    private String leader_cmt;

    @Column
    private int month;

    @Column
    private int year;

    @Column
    private String created_at;

    @Column
    private String assessed_by;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "username")
    private User username;

    //using unique username to get a unique value
    @Column
    @NotBlank
    private String unique_username;//username của người đánh giá

    @Column
    private String time_submit;//ngày submit đánh giá

    public LeaderAssessManager(String staff_code, String manager_name) {
        this.staff_code = staff_code;
        this.manager_name = manager_name;
    }

    public LeaderAssessManager(String staff_code, String manager_name, User username) {
        this.staff_code = staff_code;
        this.manager_name = manager_name;
        this.username = username;
    }
}
