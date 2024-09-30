package net.javaspringboot.kpis_be01.repository;

import net.javaspringboot.kpis_be01.entity.MemberAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberAssessRepository extends JpaRepository<MemberAssessment,Long> {

    @Query(value = "select * from member_assess where room_name = :room and created_at = :date",nativeQuery = true)
    List<MemberAssessment> getListMarkOfMemberByMonthYear(String room, String date);
    @Query(value = "select * from member_assess where room_name = :room_name and staff_code <> :code and created_at = :date", nativeQuery = true)
    List<MemberAssessment> getAllResultMemberAssessByRoomDate(String room_name, String code, String date);
    @Query(value = "select * from member_assess where  unique_username=:unique_username and created_at=:date",nativeQuery = true)
    List<MemberAssessment> getAllResultMemberAssessByNameDate(String unique_username, String date);
    @Query(value = "select * from member_assess",nativeQuery = true)
    List<MemberAssessment> getAllMemberAssess();

    @Query(value = "select * from member_assess where staff_code = :code and room_name = :room and created_at = :date",nativeQuery = true)
    List<MemberAssessment> getListMarkOfMemberByCodeRoomDate(String code, String room, String date);
}
