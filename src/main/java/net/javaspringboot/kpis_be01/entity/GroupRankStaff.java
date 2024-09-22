package net.javaspringboot.kpis_be01.entity;

import jakarta.persistence.*;
import jakarta.servlet.annotation.MultipartConfig;
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
@Table(name = "group_rank")
@MultipartConfig
public class GroupRankStaff implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    @NotBlank
    private String group_name;//nhóm chức danh

    //trọng số(ts) để tính kpi cá nhân theo group rank
    @Column
    private double ts1;//cs1

    @Column
    private double ts2;//cs2

    @Column
    private double ts3;//cs3
}
