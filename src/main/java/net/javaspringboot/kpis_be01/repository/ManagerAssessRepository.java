package net.javaspringboot.kpis_be01.repository;
import net.javaspringboot.kpis_be01.entity.ManagerAssessMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ManagerAssessRepository extends JpaRepository<ManagerAssessMember,Long> {


    @Query(value = "select * from manager_assess_member where room_name = :room_name",nativeQuery = true)
    List<ManagerAssessMember> getAllResultManagerAssesMember(String room_name);
}
