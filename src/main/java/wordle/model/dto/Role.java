package wordle.model.dto;

import org.springframework.security.core.authority.SimpleGrantedAuthority;


public enum Role {
    ROLE_user("user"),
    ROLE_admin("admin");

    private final String permission;

    Role(String permission) {
        this.permission = permission;
    }

    public String getPermissions() {
        return permission;
    }

    public SimpleGrantedAuthority getAuthorities() {
        return new SimpleGrantedAuthority(permission);
    }

}
