package net.javaspringboot.kpis_be01.service;

import net.javaspringboot.kpis_be01.entity.KpiRoomData;
import net.javaspringboot.kpis_be01.entity.NameListKPI;
import net.javaspringboot.kpis_be01.entity.RoomType;
import net.javaspringboot.kpis_be01.repository.KPIRoomDataRepository;
import net.javaspringboot.kpis_be01.repository.NameListKPIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KPIRoomService {

    @Autowired
    private NameListKPIRepository nameListKPIRepository;
    @Autowired
    private KPIRoomDataRepository kpiRoomDataRepository;

    public void InsertOrUpdateNameKPI(NameListKPI nameListKPI){nameListKPIRepository.save(nameListKPI);}

    public List<KpiRoomData> showAllKpiRoomByDate(String date){return kpiRoomDataRepository.findAllByRoomByDate(date);}


    public List<NameListKPI> getKpiNameByRoomResponsibleSymbol(String room){return nameListKPIRepository.findKPINameByRoomResponsibleSymbol(room);}

    public List<NameListKPI> getALlKpiName(){
        return  nameListKPIRepository.findAll();
    }
    public List<NameListKPI> getKpiNameByRoomReportSymbol(String room){return nameListKPIRepository.findKPINameByRoomReportSymbol(room);}
    public List<KpiRoomData> getAllKpiRoomDataByRoomRepOrResByDate(String room_symbol, String date){
        return  kpiRoomDataRepository.findAllByRoomRepOrResByDate(room_symbol, date);
    }
    public KpiRoomData getKpiRoomDataByNameDate(String kpi_name, String date){return kpiRoomDataRepository.findBcsObjByKpiNameDate(kpi_name, date);}


}
