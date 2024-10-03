package net.javaspringboot.kpis_be01.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateNewRoomRequest {
    private String room_name;
    private String room_symbol;
    private String unique_username;
    private Date created_at;
    private String user;

}
