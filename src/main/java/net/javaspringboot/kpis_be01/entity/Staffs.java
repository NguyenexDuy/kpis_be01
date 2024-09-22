package net.javaspringboot.kpis_be01.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "staff_list", uniqueConstraints = {@UniqueConstraint(columnNames = "staff_code")}) //set unique column
public class Staffs {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long Id;
    @Column
    @NotBlank
    private String staff_code;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User username;

    @Column
    @NotBlank
    private String fullname;

    @Column
    private String email;

    @Column
    @NotBlank
    private String room_name;

    @Column
    private String birthday;

    @Column
    @NotBlank
    private String rank_code;

    @Column
    @NotBlank
    private String group_work;
}
