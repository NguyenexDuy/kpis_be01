package net.javaspringboot.kpis_be01.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.javaspringboot.kpis_be01.dto.request.AuthenticationRequest;
import net.javaspringboot.kpis_be01.dto.response.ApiResponse;
import net.javaspringboot.kpis_be01.dto.response.AuthenticationResponse;
import net.javaspringboot.kpis_be01.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticationResponseApiResponse(@RequestBody AuthenticationRequest request){
        var result=authenticationService.authenticate(request);
        return  ApiResponse.<AuthenticationResponse>builder()
                .message("Login assess")
                .result(result)
                .build();
    }
}
