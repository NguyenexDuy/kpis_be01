package net.javaspringboot.kpis_be01.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserRequest {

    private String fullname;
    private String password;
    private String username;
    private String email;
    private Long rank_code_ID;//cấp nhân sự
    private String group_work;//nhóm lam viec
    private Long role_id; //cấp quyền user
    private Long role_name_ID;
    private Long room_type_ID;// khoa/phòng
    private boolean status;

}
