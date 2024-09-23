package net.javaspringboot.kpis_be01.repository;

import net.javaspringboot.kpis_be01.entity.SelfAssessStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SelfAccessStaffRepository extends JpaRepository<SelfAssessStaff,Long> {


    @Query(value = "select * from self_assess_staff where room_name = :room_name", nativeQuery = true)
    List<SelfAssessStaff> getAllResultSelfAssessStaff(String room_name);
}
