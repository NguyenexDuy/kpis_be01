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
@Table(name = "rank_staff", uniqueConstraints = {@UniqueConstraint(columnNames = "rank_code")})
public class RankStaff implements Serializable {
    //dùng để tính kpi năm cho cá nhân
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    @NotBlank
    private String rank_name;//cấp bậc

    @Column
    @NotBlank
    private String rank_code;//mã cấp bậc

    @Column
    private double ts_kpi;//chiếm tỷ lệ % kpi phòng

    @Column
    private double ts_mt;//chiếm tỷ lệ % kpi cá nhân trung bình 12 tháng

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_rank", referencedColumnName = "group_name")
    private GroupRankStaff group_rank_staff;//nhóm chức danh để tính kpi tháng)
}
