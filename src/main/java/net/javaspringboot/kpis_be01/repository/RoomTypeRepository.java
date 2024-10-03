package net.javaspringboot.kpis_be01.repository;

import net.javaspringboot.kpis_be01.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomTypeRepository extends JpaRepository<RoomType,Long> {
    @Query(value = "select * from room_type where room_symbol = :symbol",nativeQuery = true)
    RoomType findRoomTypeByRoomSymbol(String symbol);

}
