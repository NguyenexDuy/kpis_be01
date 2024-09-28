package net.javaspringboot.kpis_be01.repository;

import net.javaspringboot.kpis_be01.entity.LeaderAssessManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeaderAssessManagerRepository extends JpaRepository<LeaderAssessManager,Long> {
    @Query(value = "select * from leader_assess_manager where room_name_manager = :room and created_at = :date",nativeQuery = true)
    List<LeaderAssessManager> findListLeaderAssessManagerByRoomDate(String room, String date);

}
