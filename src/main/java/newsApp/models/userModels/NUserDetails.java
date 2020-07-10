package newsApp.models.userModels;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class NUserDetails implements UserDetails {

    private final UUID id;
    private final String email;
    private final String fullName;
    private final String password;
    private final String[] roles;
    private final boolean enable;

    public NUserDetails(UUID id, String email, String fullName, String password, String[] roles, boolean enable) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.roles = roles;
        this.enable = enable;
    }

    @Override
    public String toString() {
        return String.format("[%s,%s,%s,%s,%s]",id.toString(),email,fullName,password, Arrays.toString(roles));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(this.roles)
                .map(s->"ROLE_"+s)
                .map(s->new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return s;
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enable;
    }
}
