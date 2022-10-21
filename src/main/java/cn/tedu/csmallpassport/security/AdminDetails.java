package cn.tedu.csmallpassport.security;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
@Getter
@Setter
@EqualsAndHashCode
@ToString(callSuper = true)
public class AdminDetails extends User {
    private Long id;

    public AdminDetails(String username,
                        String password,
                        boolean enabled,
                        Collection<? extends GrantedAuthority>
                                authorities) {
        super(username, password,
                enabled, true,
                true, true,
                authorities);
    }


}
