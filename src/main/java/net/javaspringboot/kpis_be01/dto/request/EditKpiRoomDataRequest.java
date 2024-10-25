package net.javaspringboot.kpis_be01.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditKpiRoomDataRequest {
    private double ms_chung;
    private double chi_tieu;//chỉ tiêu để nhập liệu
    private double diem_hieu_chinh;// điểm hiệu chỉnh (điểm trừ tính vào kqcs)
    private double ts_thuc_hien;
    private String currency;//don_vi_tinh

}
