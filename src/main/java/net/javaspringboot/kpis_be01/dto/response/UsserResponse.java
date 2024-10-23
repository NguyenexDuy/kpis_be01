package net.javaspringboot.kpis_be01.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UsserResponse {
    private Long id;
    private String fullname;
    private String username;
    private String email;
}
