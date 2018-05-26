package pl.lodz.p.cti.utils;

import org.springframework.security.core.GrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

public class MyGrantedAuthority extends ArrayList<GrantedAuthority> {

    @Override public String toString() {
        return this.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
    }
}
