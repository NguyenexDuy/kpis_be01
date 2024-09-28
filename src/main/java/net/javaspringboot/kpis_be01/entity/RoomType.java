package net.javaspringboot.kpis_be01.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
//use @getter,@setter instead of @data to avoid error java.lang.StackOverflowError: null
@Getter
@Setter
@Entity
@Table(name = "room_type", uniqueConstraints = {@UniqueConstraint(columnNames ={"room_name","room_symbol"}), @UniqueConstraint(columnNames = "room_symbol")})
public class RoomType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long room_id;
    @Column(unique = true)
    private String room_name;
    @Column(unique = true)
    private String room_symbol;
    //user bgđ phụ trách khoa/phòng
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_name", referencedColumnName = "username")
    private User user;//dùng để liên kết dữ liệu
    @Column
    private String unique_username;
    @Column
    private Date created_at;
    @Column
    private String created_by;
}
