package net.javaspringboot.kpis_be01.repository;

import net.javaspringboot.kpis_be01.entity.KpiRoomData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KPIRoomDataRepository extends JpaRepository<KpiRoomData,Long> {
    @Query(value = "SELECT * FROM kpi_room_data WHERE created_at = :date",nativeQuery = true)
    List<KpiRoomData> findAllByRoomByDate(String date);
    @Query(value = "SELECT * FROM kpi_room_data WHERE created_at = :date AND (room_report_symbol = :room_symbol OR room_responsible_symbol = :room_symbol)",nativeQuery = true)
    List<KpiRoomData> findAllByRoomRepOrResByDate(String room_symbol, String date);
    @Query(value = "SELECT * FROM kpi_room_data WHERE kpi_name = :name AND created_at = :date",nativeQuery = true)
    KpiRoomData findBcsObjByKpiNameDate(String name, String date);
}
