package net.javaspringboot.kpis_be01.repository;

import net.javaspringboot.kpis_be01.entity.MemberAssessManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberAssessManagerRepository extends JpaRepository<MemberAssessManager,Long> {

    @Query(value = "select * from member_assess_manager where room_name = :room and created_at = :date",nativeQuery = true)
    List<MemberAssessManager> getAllResultMemberAssessManagerByRoomDate(String room, String date);
}
