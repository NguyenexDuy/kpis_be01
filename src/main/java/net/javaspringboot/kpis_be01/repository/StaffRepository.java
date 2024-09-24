package net.javaspringboot.kpis_be01.repository;

import net.javaspringboot.kpis_be01.entity.MemberAssessment;
import net.javaspringboot.kpis_be01.entity.Staffs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staffs, Long> {
        @Query(value = "select * from staff_list where username = :username", nativeQuery = true)
        Optional<Staffs> getStaffsByUsername(String username);
        @Query(value = "select * from staff_list where staff_code = :staff_code", nativeQuery = true)
        Optional<Staffs> getStaffsByStaff_code(String staff_code);


}
