package net.javaspringboot.kpis_be01.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "manager_assess_member")
public class ManagerAssessMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    @NotBlank
    private String staff_code;

    @Column
    @NotBlank
    private String member_name;

    @Column
    private String position;//cấp nhân sự của người đc đánh giá

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "username")
    private User username;

//    @Column
//    private int mark_manager_assess;
//
    @Column
    private double mark_member_assessed_average;
//
//    @Column
//    private double cs3_average;

    @Column
    private int ky_luat_khen_thuong_member;

    //đối với cấp quản lý như trưởng nhóm trở lên thì cột này là mức độ kiểm tra, giám sát chất lượng chuyên môn
    @Column
    private int chat_luong_chuyen_mon_member;

    @Column
    private int bang_chung_hoc_tap_pt_member;

    @Column(columnDefinition = "TEXT")
    private String manager_evaluate_cmt;

    @Column
    @NotBlank
    private String room_name;

    @Column
    @NotBlank
    private String room_symbol;

    @Column
    private int month;

    @Column
    private int year;

    @Column
    private String created_at;

    @Column
    @NotBlank
    private String assessed_by;

    //using unique username to get a unique username
    @Column
    @NotBlank
    private String unique_username;//username của người đánh giá
    @Column
    private String time_submit;//ngày submit đánh giá

    public ManagerAssessMember(String staff_code, String member_name, String assessed_by) {
        this.staff_code = staff_code;
        this.member_name = member_name;
        this.assessed_by = assessed_by;
    }

    public ManagerAssessMember(String staff_code, String member_name, User username) {
        this.staff_code = staff_code;
        this.member_name = member_name;
        this.username = username;
    }

    public ManagerAssessMember(String staff_code, String member_name) {
        this.staff_code = staff_code;
        this.member_name = member_name;
    }
}
