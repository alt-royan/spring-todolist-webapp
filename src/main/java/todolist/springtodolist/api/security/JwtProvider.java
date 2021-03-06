package todolist.springtodolist.api.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import todolist.springtodolist.api.entity.Status;
import todolist.springtodolist.api.entity.User;
import todolist.springtodolist.api.exception.JwtAuthenticationException;
import todolist.springtodolist.api.service.UserService;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    private final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    //Secret word to sign jwt
    @Value("${jwt.secret}")
    private String jwtSecret;
    public static final String AUTHORIZATION = "Authorization";


    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;


    //Encode secret word
    @PostConstruct
    protected void init(){
        jwtSecret= Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }

    //Generate new jwt token
    public String generateToken(User user){
        Date date = Date.from(LocalDate.now().plusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Claims claims =Jwts.claims();
        claims.put("user_id", user.getId());
        return Jwts.builder().
                setClaims(claims).
                setSubject(user.getLogin()).
                setIssuedAt(new Date()).
                setExpiration(date).
                signWith(SignatureAlgorithm.HS512, jwtSecret).
                compact();
    }


    //Check validity of the token
    public boolean validateToken(String token) throws JwtAuthenticationException{
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            Long userId =getUserIdFromToken(token);
            return userService.getUserById(userId).getStatus() != Status.DELETE;
        } catch (JwtException | IllegalArgumentException e){
            throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }




    //Get user_id from token
    public Long getUserIdFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("user_id", Long.class);
    }

    //Get login from token
    public String getLoginFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    //Get date of issued
    public Date getIssuedAt(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getIssuedAt();
    }

    //Get date of expiration
    public Date getExpiredAt(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();

    }

    public Authentication getAuthentication(String token) throws UsernameNotFoundException {

        UserDetails userDetails= userDetailsService.loadUserByUsername(getLoginFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (bearer!=null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
