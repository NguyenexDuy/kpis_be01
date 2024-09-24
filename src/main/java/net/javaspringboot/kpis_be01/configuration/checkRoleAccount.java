package net.javaspringboot.kpis_be01.configuration;

import org.springframework.security.core.context.SecurityContextHolder;

public class checkRoleAccount {
    //check Role account is logging
    public static boolean hasRole (String roleName)
    {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }
}
