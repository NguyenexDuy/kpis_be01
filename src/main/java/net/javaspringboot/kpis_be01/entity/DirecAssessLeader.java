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
@Table(name = "director_assess_leader")
public class DirecAssessLeader {
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

    @Column
    @NotBlank
    private String room_symbol;//room code leader

    @Column
    private int ky_luat_khen_thuong_leader;

    @Column
    private int chat_luong_hoan_thanh_nv;

    @Column
    private int bang_chung_hoc_tap_pt_leader;

    @Column
    private double mark_manager_assessed_average;

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

    //using unique username to get a unique value
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "username")
    private User username;

    @Column
    @NotBlank
    private String unique_username;//username của người đánh giá

    @Column
    private String time_submit;//ngày submit đánh giá

    public DirecAssessLeader(String staff_code, String leader_name) {
        this.staff_code = staff_code;
        this.leader_name = leader_name;
    }

    public DirecAssessLeader(String staff_code, String leader_name, User username) {
        this.staff_code = staff_code;
        this.leader_name = leader_name;
        this.username = username;
    }
}
