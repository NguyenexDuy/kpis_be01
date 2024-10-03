package net.javaspringboot.kpis_be01.repository;

import net.javaspringboot.kpis_be01.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    @Query(value = "select * from user where role = :role_name", nativeQuery = true)
    List<User> findAllUserByRoleName(String role_name);

    @Query(value="select * from user where username=:uniquename",nativeQuery = true)
    User getUserNameByUniqueName(String uniquename);
}
