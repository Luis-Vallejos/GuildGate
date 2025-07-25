package com.guildgate.web.Bean;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
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
     *
     * @param codigoPermiso
     * @return
     */
    public boolean tienePermiso(String codigoPermiso) {
        return roles.stream()
                .flatMap(r -> r.getPermisos().stream())
                .anyMatch(p -> p.getCodigo().equalsIgnoreCase(codigoPermiso));
    }

    public String getRolPrincipal() {
        return roles.stream()
                .map(RolBean::getNombre)
                .findFirst()
                .orElse("Sin Rol");
    }

    /**
     * Devuelve todos los nombres de roles, unidos por coma. Útil si un usuario
     * puede tener varios.
     */
    public String getRolesComoTexto() {
        return roles.stream()
                .map(RolBean::getNombre)
                .collect(Collectors.joining(", "));
    }
}
