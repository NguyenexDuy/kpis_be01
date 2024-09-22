package net.javaspringboot.kpis_be01.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String rolename;

    public Role() {
    }

    public Role(Long id, String rolename) {
        this.id = id;
        this.rolename = rolename;
    }
}
