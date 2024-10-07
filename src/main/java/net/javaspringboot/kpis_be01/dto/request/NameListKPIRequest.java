package net.javaspringboot.kpis_be01.dto.request;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NameListKPIRequest {
    private String kpi_name;
    private String kpi_type;//loại chỉ số kpi (tháng, quý, 6 tháng, năm)
    private String compare_type;//loại so sánh (lớn hơn hoặc nhỏ hơn)
    private String room_responsible_symbol;
    private String room_report_symbol;//phong nhap lieu bao cao chi so kpi cua phong nay
    private String note;//mô tả diễn giải chỉ số tiêu tháng

}
