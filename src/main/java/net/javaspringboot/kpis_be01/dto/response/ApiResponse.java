package net.javaspringboot.kpis_be01.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Chỉ hiện các field không null
public class ApiResponse<T> {
    private int code = 1000;  // Mã phản hồi mặc định
    private String message="SUCCESS";   // Thông điệp phản hồi
    private T result;         // Dữ liệu kết quả trả về


}
