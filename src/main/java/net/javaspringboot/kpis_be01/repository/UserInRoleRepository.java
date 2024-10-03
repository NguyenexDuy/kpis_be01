package net.javaspringboot.kpis_be01.repository;

import net.javaspringboot.kpis_be01.entity.UserInRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInRoleRepository extends JpaRepository<UserInRole,Long> {
}
