package net.javaspringboot.kpis_be01.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "manager_assess_leader")
public class ManagerAssessLeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column
    @NotBlank
    private String staff_code;

    @Column
    @NotBlank
    private String leader_name;

    @Column
    private String rank_leader;//cấp bậc (role) của nguời đc đánh giá

    @Column
    @NotBlank
    private String room_name;//room name leader

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "username")
    private User username;

    @Column
    private int gan_ket_tao_dong_luc_nv;

    @Column
    private int chat_luong_ho_tro_giai_quyet_vd;

    @Column
    private double tb_gan_ket_va_ho_tro;

    @Column(columnDefinition = "TEXT")
    private String note_desc;

    @Column
    private int month;

    @Column
    private int year;

    @Column
    private String created_at;

    @Column
    private String assessed_by;

    @Column
    @NotBlank
    private String room_name_asser;//room name người đánh giá

    //using unique username to get a unique value
    @Column
    @NotBlank
    private String unique_username;//username của người đánh giá

    @Column
    private String rank_asser;//cấp bậc (role) của nguời đánh giá

    @Column
    private String time_submit;//ngày submit đánh giá

    public ManagerAssessLeader(String staff_code, String leader_name, String assessed_by) {
        this.staff_code = staff_code;
        this.leader_name = leader_name;
        this.assessed_by = assessed_by;
    }

    public ManagerAssessLeader(String staff_code, String leader_name) {
        this.staff_code = staff_code;
        this.leader_name = leader_name;
    }

    public ManagerAssessLeader(String staff_code, String leader_name, User username) {
        this.staff_code = staff_code;
        this.leader_name = leader_name;
        this.username = username;
    }
}
