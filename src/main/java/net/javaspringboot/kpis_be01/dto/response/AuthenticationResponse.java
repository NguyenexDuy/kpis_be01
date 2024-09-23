package net.javaspringboot.kpis_be01.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)  // nếu khonong định nghĩa thì các thuộc tính là private

public class AuthenticationResponse {
    String token;
    boolean authenticated;
}
