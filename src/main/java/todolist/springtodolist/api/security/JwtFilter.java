package todolist.springtodolist.api.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import todolist.springtodolist.api.exception.JwtAuthenticationException;
import todolist.springtodolist.api.exception.UserNotFoundException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION = "Authorization";

    @Autowired
    private JwtProvider jwtProvider;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        try {
            if (token != null && jwtProvider.validateToken(token)) {
                Authentication auth = jwtProvider.getAuthentication(token);
                if (auth.isAuthenticated()){
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }catch(JwtAuthenticationException e){
            SecurityContextHolder.clearContext();
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }catch(UserNotFoundException ex){
            SecurityContextHolder.clearContext();
            throw new UserNotFoundException(ex.getMessage());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION);
    }
}
