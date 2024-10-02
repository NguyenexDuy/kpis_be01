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
@Table(name = "member_assess")
public class MemberAssessment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column
    private String assessed_by;

    @Column
    private String created_at;

    @Column
    private int mark_member_assess;

    @Column
    @NotBlank
    private String member_name;


    @Column
    private int month;


    @Column
    private String note_desc;

    @Column
    private String position;//cấp bậc (role) của nguời đánh giá

    @Column
    @NotBlank
    private String room_name;

    @Column
    @NotBlank
    private String staff_code;

    //using unique username to get a unique value
    @Column
    @NotBlank
    private String unique_username;//username của người đánh giá

    @Column
    private int year;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "username")
    private User username;

    @Column
    private String time_submit;//ngày submit đánh giá
    public MemberAssessment(String staff_code, String member_name, User username) {
        this.staff_code = staff_code;
        this.member_name = member_name;
        this.username = username;
    }
}
