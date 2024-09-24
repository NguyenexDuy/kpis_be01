package net.javaspringboot.kpis_be01.repository;

import net.javaspringboot.kpis_be01.entity.SelfAssessStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeflAssessStaffRepository extends JpaRepository<SelfAssessStaff,Long> {

    @Query(value= "select * from self_assess_staff where staff_code=:staff_code",nativeQuery = true)
    List<SelfAssessStaff> getSeflAssessStaffByStaffCode(String staff_code);
}
