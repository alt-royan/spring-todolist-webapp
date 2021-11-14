package todolist.springtodolist.api.dto;


import lombok.Getter;
import todolist.springtodolist.api.security.JwtProvider;

import java.util.Date;

@Getter
public class AuthResponseDTO {

    private final String token;
    private final Date issuedAt;
    private final Date expiredAt;
    private final Long userId;
    private final String login;


    public AuthResponseDTO(String token, JwtProvider jwtProvider){
        this.token=token;
        this.issuedAt=jwtProvider.getIssuedAt(token);
        this.expiredAt=jwtProvider.getExpiredAt(token);
        this.userId=jwtProvider.getUserIdFromToken(token);
        this.login=jwtProvider.getLoginFromToken(token);
    }

}
