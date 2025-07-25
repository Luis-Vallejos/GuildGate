package com.guildgate.web.Bean;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Juan - Luis
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class SessionUserBean {

    private UserProfileBean perfil;
    private AvatarBean avatar;
    private BannerBean banner;
    private GuildBean gremio;
    @Builder.Default
    private Set<RolBean> roles = new HashSet<>();

    /**
     * Devuelve true si alguno de sus roles contiene el permiso cuyo código es
     * igual al que se le pasa.
     * @param codigoPermiso
     * @return 
     */
    public boolean tienePermiso(String codigoPermiso) {
        return roles.stream()
                .flatMap(r -> r.getPermisos().stream())
                .anyMatch(p -> p.getCodigo().equalsIgnoreCase(codigoPermiso));
    }
}
