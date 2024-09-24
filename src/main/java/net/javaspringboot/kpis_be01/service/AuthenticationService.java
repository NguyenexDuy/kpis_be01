package net.javaspringboot.kpis_be01.service;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.javaspringboot.kpis_be01.dto.request.AuthenticationRequest;
import net.javaspringboot.kpis_be01.dto.response.AuthenticationResponse;
import net.javaspringboot.kpis_be01.entity.User;
import net.javaspringboot.kpis_be01.exception.AppException;
import net.javaspringboot.kpis_be01.exception.ErrorCode;
import net.javaspringboot.kpis_be01.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;
    @NonFinal
    protected static  final   String SIGNER_KEY="A7uenFLhLY3uiBQjNWiwPlrCT1enwNHniqLq3TqwPQApo+MGZ6YsrBv9xNpFm0PY";


    public AuthenticationResponse authenticate(AuthenticationRequest request){
        var user=userRepository.findByUsername(request.getUsername()).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        // check password nhap vao va password user co trong db
        boolean authenticated=passwordEncoder.matches(request.getPassword(),user.getPassword());
        if(!authenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token=generateToken(user);
        return  AuthenticationResponse.builder()
                .token(token)
                .role(user.getRoles())
                .authenticated(true)
                .build();
    }


    // tao header, payload va signature
    private String generateToken(User user){
        JWSHeader header=new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet=new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("devteria.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope",buildScope(user))
                .build();

        Payload payload=new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject=new JWSObject(header,payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return  jwsObject.serialize();
        } catch (JOSEException e) {

            throw new RuntimeException(e);
        }
    }
    private  String buildScope(User user){
        StringJoiner stringJoiner=new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(s-> stringJoiner.add(s.getRolename()));

        return  stringJoiner.toString();
    }
}
