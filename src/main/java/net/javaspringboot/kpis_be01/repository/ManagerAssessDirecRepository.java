package net.javaspringboot.kpis_be01.repository;

import net.javaspringboot.kpis_be01.entity.ManagerAssessLeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ManagerAssessDirecRepository extends JpaRepository<ManagerAssessLeader,Long> {

    @Query(value = "select * from manager_assess_leader where unique_username = :username and staff_code = :code and room_name = :room and created_at = :date",nativeQuery = true)
    ManagerAssessLeader findObjAssessLeaderByStaffCodeRoomMonthYear(String username, String code, String room, String date);


}
