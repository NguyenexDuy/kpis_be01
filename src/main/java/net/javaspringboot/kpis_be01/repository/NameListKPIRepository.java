package net.javaspringboot.kpis_be01.repository;

import net.javaspringboot.kpis_be01.entity.NameListKPI;
import net.javaspringboot.kpis_be01.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NameListKPIRepository extends JpaRepository<NameListKPI,Long> {

    @Query(value = "SELECT * FROM name_list_kpi WHERE room_responsible_symbol = :room",nativeQuery = true)
    List<NameListKPI> findKPINameByRoomResponsibleSymbol(String room);
    @Query(value = "SELECT * FROM name_list_kpi WHERE room_report_symbol = :room",nativeQuery = true)
    List<NameListKPI> findKPINameByRoomReportSymbol(String room);
}
