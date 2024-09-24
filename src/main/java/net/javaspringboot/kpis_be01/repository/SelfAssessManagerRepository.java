package net.javaspringboot.kpis_be01.repository;

import net.javaspringboot.kpis_be01.entity.SelfAssessManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SelfAssessManagerRepository extends JpaRepository<SelfAssessManager,Long> {

    @Query(value = "select * from self_assess_manager where room_name = :room and created_at = :date", nativeQuery = true)
    List<SelfAssessManager> getAllByRoom_nameDate(String room, String date);

    @Query(value = "SELECT * FROM self_assess_manager WHERE created_at = :date",nativeQuery = true)
    List<SelfAssessManager> getAllByDate(String date);
}
