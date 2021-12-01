package todolist.springtodolist.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtProvider.getTokenFromRequest((HttpServletRequest) servletRequest);
        try {
            if (token != null && jwtProvider.validateToken(token)) {
                Authentication auth = jwtProvider.getAuthentication(token);
                if (auth.isAuthenticated()){
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }catch(JwtAuthenticationException | UsernameNotFoundException e){
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
