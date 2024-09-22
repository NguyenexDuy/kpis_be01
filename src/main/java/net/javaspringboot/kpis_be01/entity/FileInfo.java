package net.javaspringboot.kpis_be01.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "file_info")
public class FileInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column
    private String file_name;
    @Column
    private String url_file;
    @Column
    private String created_at;
    @Column
    private String created_by;

    public FileInfo(String file_name, String url_file) {
        this.file_name = file_name;
        this.url_file = url_file;
    }
}
