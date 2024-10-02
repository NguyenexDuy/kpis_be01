package net.javaspringboot.kpis_be01.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SelfAssessStaffRequest {
    private String staff_code;

    private String name;

    private String rank;

    private String group_rank;

    private int month;

    private int year;

    private int ky_luat_va_thuong;

    private int muc_do_phoi_hop;

    private int chat_luong_chuyen_mon;

    private int diem_muc_do_hoc_tap_pt;

    private String note;

    private String room_name;

    private String room_symbol;
    private String created_at;
    private  String created_by;
    private String time_submit;
}
