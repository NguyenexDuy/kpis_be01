package net.javaspringboot.kpis_be01.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
//use @getter,@setter instead of @data to avoid error java.lang.StackOverflowError: null
@Getter
@Setter
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})//set unique column
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotBlank
    private String fullname;
    @Column
    private String password;
    @Column
    @NotBlank
    private String username;
    @Column
    @NotBlank
    private String email;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rank_code",referencedColumnName = "rank_code")
    private RankStaff rank_code;//cấp bậc cụ thể (để tính kpi năm)

    @Column
    @NotBlank
    private String group_work;//phân nhóm nhỏ để trưởng nhóm, tổ trưởng đánh giá phụ trưởng khoa phòng

    @ManyToMany(
            fetch = FetchType.EAGER, cascade = CascadeType.ALL) //FetchType.EAGER nạp data sớm, FetchType.LAZY nạp data trễ (must be had fetch)
    @JoinTable(name = "userinrole", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")//referencedColumnName là tên cột của table tham chiếu đến
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)//(cascade = CascadeType.ALL) để auto add vào luôn table tham chiếu ko cần set tay
    @JoinColumn(name="role", referencedColumnName = "rolename")
    private Role role_name;

    @ManyToOne
    @JoinColumn(name = "room_type", referencedColumnName = "room_name")
    private RoomType room_type;//tên K/P

    @Column
    private boolean status;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column
    private Date created_at;//using java.sql.Date not java.util.Date to create field type date in MySql DB


    public User(String username, String password, String fullname, String email, Set<Role> roles, Role role_name,
                RoomType room_type, boolean status, Date created_at) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.roles = roles;
        this.role_name = role_name;
        this.room_type = room_type;
        this.status = status;
        this.created_at = created_at;
    }

    public User(Long id, String fullname, String password, String username, String email, Set<Role> roles, Role role_name, boolean status, Date created_at) {
        this.id = id;
        this.fullname = fullname;
        this.password = password;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.role_name = role_name;
        this.status = status;
        this.created_at = created_at;
    }
}
