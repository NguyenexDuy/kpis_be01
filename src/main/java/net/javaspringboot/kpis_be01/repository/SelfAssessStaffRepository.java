package net.javaspringboot.kpis_be01.repository;

import net.javaspringboot.kpis_be01.entity.SelfAssessStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SelfAssessStaffRepository extends JpaRepository<SelfAssessStaff,Long> {

    @Query(value= "select * from self_assess_staff where staff_code=:staff_code",nativeQuery = true)
    List<SelfAssessStaff> getSelfAssessStaffByStaffCode(String staff_code);

    @Query(value = "select * from self_assess_staff where room_name=:room_name and created_at=:date",nativeQuery = true)
    List<SelfAssessStaff> getSelfAssessStaffByRoomDate(String room_name,String date);

    @Query(value = "SELECT * FROM self_assess_staff WHERE created_by = :username and created_at = :date",nativeQuery = true)
    List<SelfAssessStaff> getSelfAllByUserDate(String username, String date);

}
