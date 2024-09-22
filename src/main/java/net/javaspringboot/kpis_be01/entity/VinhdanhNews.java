package net.javaspringboot.kpis_be01.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "vinh_danh_news")
public class VinhdanhNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long news_id;
    private String title;
    private String image;
    //a short description for news to view before click
    @Lob
    @Column(name = "description", length = 512)
    private String description;
    @Lob
    @Column(name = "content",length = 512)
    private String content;
    private boolean published;
    private String created_at;
    private String created_by;
}
