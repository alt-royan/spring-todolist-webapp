package todolist.springtodolist.api.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import todolist.springtodolist.api.entity.User;

import java.util.Collection;
import java.util.Collections;

public class SecureUser implements UserDetails {

    private String login;
    private String password;
    private boolean isActive;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static SecureUser fromUserEntity(User user) {
        SecureUser secureUser =new SecureUser();
        secureUser.login = user.getLogin();
        secureUser.password=user.getPassword();
        secureUser.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
        secureUser.isActive=user.getStatus().isActive();
        return secureUser;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
