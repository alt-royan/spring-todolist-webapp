package todolist.springtodolist.api.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import todolist.springtodolist.api.security.JwtProvider;

import java.util.Date;

@Getter
@ApiModel(description = "Response for successful authentication")
public class AuthResponseDTO {

    @ApiModelProperty(example = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyX2lkIjoxMiwic3ViIjoiVHJ1ZUFkbWluMSIsImlhdCI6MTYzODMwMTUzOCwiZXhwIjoxNjM4MzA2MDAwfQ.kkWtfBYBPPLYdCc_MfQmhUShbwRUydoeQPZYzVnIcMswm9AXbxGJHlKwb6CCGCXGxJY1gOXNP7CIUgf7Q5AFew")
    private final String token;
    @ApiModelProperty(example = "2021-11-30T19:45:38.000+00:00")
    private final Date issuedAt;
    @ApiModelProperty(example = "2021-11-30T21:00:00.000+00:00")
    private final Date expiredAt;
    @ApiModelProperty(example = "12")
    private final Long userId;
    @ApiModelProperty(example = "test_user")
    private final String login;


    public AuthResponseDTO(String token, JwtProvider jwtProvider){
        this.token=token;
        this.issuedAt=jwtProvider.getIssuedAt(token);
        this.expiredAt=jwtProvider.getExpiredAt(token);
        this.userId=jwtProvider.getUserIdFromToken(token);
        this.login=jwtProvider.getLoginFromToken(token);
    }

}
