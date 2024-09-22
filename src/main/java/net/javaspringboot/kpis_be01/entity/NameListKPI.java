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
@Table(name = "name_list_kpi",uniqueConstraints = @UniqueConstraint(columnNames = "kpi_name"))
public class NameListKPI implements Serializable {// implements Serializable to fix error insert value instead of obj
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    @NotBlank
    private String kpi_name;

    @Column
    @NotBlank
    private String kpi_type;//loại chỉ số kpi (tháng, quý, 6 tháng, năm)

    @Column
    @NotBlank
    private String compare_type;//loại so sánh (lớn hơn hoặc nhỏ hơn)

    //ten phong chiu trach nhiem ve chi so kpi nay
    @Column
    @NotBlank
    private String room_name;//ten phong chiu trach nhiem ve chi so kpi nay
    @Column
    @NotBlank
    private String room_responsible_symbol;
    //===============================================

    //phong nhap lieu bao cao chi so kpi cua phong nay
    @Column
    @NotBlank
    private String room_report;//ten phong nhap lieu bao cao chi so kpi cua phong nay
    @Column
    @NotBlank
    private String room_report_symbol;//phong nhap lieu bao cao chi so kpi cua phong nay
    //===================
    @Column(columnDefinition = "TEXT")// gán kiểu text cho colum trong database
    private String note;//mô tả diễn giải chỉ số tiêu tháng
    @Column
    private String created_at;
    @Column
    private String created_by;
}
