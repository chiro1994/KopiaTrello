package pl.lodz.p.cti.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.thymeleaf.expression.Strings;
import pl.lodz.p.cti.utils.MyGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserPrincipal implements UserDetails {
    private UserModel user;

    public MyUserPrincipal(UserModel user){
        this.user = user;
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() {
        MyGrantedAuthority list = new MyGrantedAuthority();
        list.add(() -> user.getRole());
        return list;
    }

    @Override public String getPassword() {
        return user.getPassword();
    }

    @Override public String getUsername() {
        return user.getUsername();
    }

    @Override public boolean isAccountNonExpired() {
        return true;
    }

    @Override public boolean isAccountNonLocked() {
        return true;
    }

    @Override public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override public boolean isEnabled() {
        return true;
    }
}
