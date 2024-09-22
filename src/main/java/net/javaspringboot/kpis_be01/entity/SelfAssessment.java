package net.javaspringboot.kpis_be01.entity;


import jakarta.persistence.*;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.javaspringboot.kpis_be01.dto.request.SelfAssessRequest;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="self_assessment")
@MultipartConfig
public class SelfAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotBlank
    private String staff_code;
    @Column
    private String name;
    @Column
    private int month;
    @Column
    private int year;
    @Column
    private int diem_cam_ket;
    @Column
    private boolean khen_tu_bgd;
    @Column
    private String tuan_thu_quy_dinh;
    @Column
    private int gio_cong_trong_thang;
    @Column
    private int gio_cong_chuan;
    @Column
    private int diem_muc_do_hoc_tap_pt;

    @Column
    private String bang_chung_diem_hoc_tap_pt;

//    @ManyToOne(cascade = CascadeType.ALL) //để add vào luôn table tham chiếu ko cần set tay, fix error object references an unsaved transient instance
//    @JoinColumn(name = "file_name", referencedColumnName = "file_name")
//    private FileInfo file_kpi;

    //bộ chỉ số KPIs: cs1,cs2,cs4
    @Column
    private double cs1;
    @Column
    private double cs2;
    @Column
    private double cs4;

    @Column
    @NotBlank
    private String position;

    @Column
    @NotBlank
    private String room_name;

    @ManyToOne
    @JoinColumn(name = "room_symbol", referencedColumnName = "room_symbol")
    private RoomType room_symbol;//dùng để liên kết dữ liệu
    @Column
    private String created_at;
    @Column
    private String created_by;//dùng username để get unique value

    public SelfAssessment(SelfAssessRequest request) {
        this.name = request.getName();
        this.month = request.getMonth();
        this.year = request.getYear();
        this.diem_cam_ket = request.getDiem_cam_ket();
        this.khen_tu_bgd = request.isKhen_tu_bgd();
        this.tuan_thu_quy_dinh = request.getTuan_thu_quy_dinh();
        this.gio_cong_trong_thang = request.getGio_cong_trong_thang();
        this.gio_cong_chuan = request.getGio_cong_chuan();
        this.diem_muc_do_hoc_tap_pt = request.getDiem_muc_do_hoc_tap_pt();
        this.bang_chung_diem_hoc_tap_pt = request.getBang_chung_diem_hoc_tap_pt();
        this.created_at = request.getCreated_at();
        this.created_by = request.getCreated_by();
    }
}
