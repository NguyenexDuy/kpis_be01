package net.javaspringboot.kpis_be01.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.javaspringboot.kpis_be01.entity.Role;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)  // nếu khonong định nghĩa thì các thuộc tính là private

public class AuthenticationResponse {
    String token;
    Set<Role> role;
    boolean authenticated;
}
