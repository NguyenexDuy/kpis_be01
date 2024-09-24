package net.javaspringboot.kpis_be01.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "member_assess_manager")
public class MemberAssessManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column
    @NotBlank
    private String staff_code;

    @Column
    @NotBlank
    private String manager_name;

    @Column
    private String rank_manager;//cấp bậc (role) của nguời đc đánh giá

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "username")
    private User username;

    @Column
    private int gan_ket_tao_dong_luc_nv;

    @Column
    private int chat_luong_to_chuc_phan_cong_cv;

    @Column
    private double tb_gan_ket_va_phan_cong;

    @Column(columnDefinition = "TEXT")
    private String note_desc;

    @Column
    @NotBlank
    private String room_name;

    @Column
    private int month;

    @Column
    private int year;

    @Column
    private String created_at;

    @Column
    private String assessed_by;

    //using unique username to get a unique value
    @Column
    @NotBlank
    private String unique_username;//username của người đánh giá

    @Column
    private String position;//cấp bậc (role) của nguời đánh giá

    @Column
    private String time_submit;//ngày submit đánh giá

    public MemberAssessManager(String staff_code, String manager_name) {
        this.staff_code = staff_code;
        this.manager_name = manager_name;
    }
}
