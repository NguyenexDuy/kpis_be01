package net.javaspringboot.kpis_be01.exception;
import net.javaspringboot.kpis_be01.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//dung de quan ly cac exception khi su dung postman, no se tra loi cu the
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception){

        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setCode(1001);
        apiResponse.setMessage(exception.getMessage());
        return  ResponseEntity.badRequest().body(apiResponse);
    }
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingApptimeException(AppException exception){
        ErrorCode errorCode=exception.getErrorCode();
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return  ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception){

        String enumkey=exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode=ErrorCode.INVALID_KEY;

        //sử dụng trong trường hợp key sai
        try{
            errorCode=ErrorCode.valueOf(enumkey);

        }catch (IllegalArgumentException ex){

        }
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return  ResponseEntity.badRequest().body(apiResponse);
    }
}
