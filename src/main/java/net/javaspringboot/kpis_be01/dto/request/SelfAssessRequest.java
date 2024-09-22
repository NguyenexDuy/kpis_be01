package net.javaspringboot.kpis_be01.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelfAssessRequest {

    private String name;

    private int month;

    private int year;

    private int diem_cam_ket;

    private boolean khen_tu_bgd;

    private String tuan_thu_quy_dinh;

    private int gio_cong_trong_thang;

    private int gio_cong_chuan;

    private int diem_muc_do_hoc_tap_pt;

    private String bang_chung_diem_hoc_tap_pt;

    private String created_at;

    private String created_by;
}
