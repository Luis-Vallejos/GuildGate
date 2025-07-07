package com.guildgate.web.Bean;

import java.io.Serializable;
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
public class UsuarioBean implements Serializable {

    private int id;
    private String usuarioActual;
    private String correo;
    private String gremioActual;
    private String descripcionGremio;
    private String rolUsuario;
    private String bioUsuario;
    private int nivelUsuario;
    private String nombreAvatar;
    private String imagenUsuario;
    private String nombreBanner;
    private String banner;
    private String posicionXBanner;
    private String posicionYBanner;
    private String nombreAvatarGremio;
    private String imagenGremio;
    private String nombreFondoGremio;
    private String imagenFondoGremio;
    private boolean permisoCambiarAvatarGremio;
    private boolean permisoCambiarFondoGremio;
    private boolean permisoCambiarNombreGremio;
    private boolean permisoCambiarDescripcionGremio;
    private boolean permisoCrearRaids;
    private boolean permisoEditarRaids;
    private boolean permisoVisualizarRaids;
    private boolean permisoCrearRoles;
    private boolean permisoEditarRoles;
    private boolean permisoVisualizarRoles;
    private boolean permisoBotarMiembros;
    private boolean permisoSalirGremio;
    private boolean permisoEliminarGremio;
    private String contra;
}
