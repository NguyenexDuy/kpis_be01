package net.javaspringboot.kpis_be01.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaspringboot.kpis_be01.entity.KpiRoomData;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class KPIRoomListRequest {
    List<KpiRoomData> kpiRoomInputList = new ArrayList<>();

}
