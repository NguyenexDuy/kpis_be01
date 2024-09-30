package net.javaspringboot.kpis_be01.repository;

import net.javaspringboot.kpis_be01.entity.ResultPersonalKPI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResultRepo extends JpaRepository<ResultPersonalKPI,Long> {

    @Query(value = "select * from ket_qua_kpi_ca_nhan where room_symbol = :symbol and date = :date",nativeQuery = true)
     List<ResultPersonalKPI> resultListByRoomSymbolDate(String symbol, String date);
    @Query(value = "select * from ket_qua_kpi_ca_nhan where date = :date",nativeQuery = true)
    List<ResultPersonalKPI> resultListByDate(String date);

}
