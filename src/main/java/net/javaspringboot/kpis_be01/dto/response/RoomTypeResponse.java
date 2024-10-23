package net.javaspringboot.kpis_be01.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)  // nếu khonong định nghĩa thì các thuộc tính là private

public class RoomTypeResponse {
    private Long room_id;
    private String room_name;
    private String room_symbol;
    private String unique_username;
    private String created_by;
    private UsserResponse user;  // Bao gồm thông tin của User
}
