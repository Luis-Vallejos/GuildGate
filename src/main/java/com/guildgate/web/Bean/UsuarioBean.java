package com.guildgate.web.Bean;

/**
 *
 * @author Lavender
 */
import java.io.Serializable;

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

    public UsuarioBean() {
    }

    public UsuarioBean(int id, String usuarioActual, String correo, String gremioActual, String descripcionGremio, String rolUsuario, String bioUsuario, int nivelUsuario, String nombreAvatar, String imagenUsuario, String nombreBanner, String banner, String posicionXBanner, String posicionYBanner, String nombreAvatarGremio, String imagenGremio, String nombreFondoGremio, String imagenFondoGremio, boolean permisoCambiarAvatarGremio, boolean permisoCambiarFondoGremio, boolean permisoCambiarNombreGremio, boolean permisoCambiarDescripcionGremio, boolean permisoCrearRaids, boolean permisoEditarRaids, boolean permisoVisualizarRaids, boolean permisoCrearRoles, boolean permisoEditarRoles, boolean permisoVisualizarRoles, boolean permisoBotarMiembros, boolean permisoSalirGremio, boolean permisoEliminarGremio, String contra) {
        this.id = id;
        this.usuarioActual = usuarioActual;
        this.correo = correo;
        this.gremioActual = gremioActual;
        this.descripcionGremio = descripcionGremio;
        this.rolUsuario = rolUsuario;
        this.bioUsuario = bioUsuario;
        this.nivelUsuario = nivelUsuario;
        this.nombreAvatar = nombreAvatar;
        this.imagenUsuario = imagenUsuario;
        this.nombreBanner = nombreBanner;
        this.banner = banner;
        this.posicionXBanner = posicionXBanner;
        this.posicionYBanner = posicionYBanner;
        this.nombreAvatarGremio = nombreAvatarGremio;
        this.imagenGremio = imagenGremio;
        this.nombreFondoGremio = nombreFondoGremio;
        this.imagenFondoGremio = imagenFondoGremio;
        this.permisoCambiarAvatarGremio = permisoCambiarAvatarGremio;
        this.permisoCambiarFondoGremio = permisoCambiarFondoGremio;
        this.permisoCambiarNombreGremio = permisoCambiarNombreGremio;
        this.permisoCambiarDescripcionGremio = permisoCambiarDescripcionGremio;
        this.permisoCrearRaids = permisoCrearRaids;
        this.permisoEditarRaids = permisoEditarRaids;
        this.permisoVisualizarRaids = permisoVisualizarRaids;
        this.permisoCrearRoles = permisoCrearRoles;
        this.permisoEditarRoles = permisoEditarRoles;
        this.permisoVisualizarRoles = permisoVisualizarRoles;
        this.permisoBotarMiembros = permisoBotarMiembros;
        this.permisoSalirGremio = permisoSalirGremio;
        this.permisoEliminarGremio = permisoEliminarGremio;
        this.contra = contra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(String usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getGremioActual() {
        return gremioActual;
    }

    public void setGremioActual(String gremioActual) {
        this.gremioActual = gremioActual;
    }

    public String getDescripcionGremio() {
        return descripcionGremio;
    }

    public void setDescripcionGremio(String descripcionGremio) {
        this.descripcionGremio = descripcionGremio;
    }

    public String getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(String rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public String getBioUsuario() {
        return bioUsuario;
    }

    public void setBioUsuario(String bioUsuario) {
        this.bioUsuario = bioUsuario;
    }

    public int getNivelUsuario() {
        return nivelUsuario;
    }

    public void setNivelUsuario(int nivelUsuario) {
        this.nivelUsuario = nivelUsuario;
    }

    public String getNombreAvatar() {
        return nombreAvatar;
    }

    public void setNombreAvatar(String nombreAvatar) {
        this.nombreAvatar = nombreAvatar;
    }

    public String getImagenUsuario() {
        return imagenUsuario;
    }

    public void setImagenUsuario(String imagenUsuario) {
        this.imagenUsuario = imagenUsuario;
    }

    public String getNombreBanner() {
        return nombreBanner;
    }

    public void setNombreBanner(String nombreBanner) {
        this.nombreBanner = nombreBanner;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getPosicionXBanner() {
        return posicionXBanner;
    }

    public void setPosicionXBanner(String posicionXBanner) {
        this.posicionXBanner = posicionXBanner;
    }

    public String getPosicionYBanner() {
        return posicionYBanner;
    }

    public void setPosicionYBanner(String posicionYBanner) {
        this.posicionYBanner = posicionYBanner;
    }

    public String getNombreAvatarGremio() {
        return nombreAvatarGremio;
    }

    public void setNombreAvatarGremio(String nombreAvatarGremio) {
        this.nombreAvatarGremio = nombreAvatarGremio;
    }

    public String getImagenGremio() {
        return imagenGremio;
    }

    public void setImagenGremio(String imagenGremio) {
        this.imagenGremio = imagenGremio;
    }

    public String getNombreFondoGremio() {
        return nombreFondoGremio;
    }

    public void setNombreFondoGremio(String nombreFondoGremio) {
        this.nombreFondoGremio = nombreFondoGremio;
    }

    public String getImagenFondoGremio() {
        return imagenFondoGremio;
    }

    public void setImagenFondoGremio(String imagenFondoGremio) {
        this.imagenFondoGremio = imagenFondoGremio;
    }

    public boolean isPermisoCambiarAvatarGremio() {
        return permisoCambiarAvatarGremio;
    }

    public void setPermisoCambiarAvatarGremio(boolean permisoCambiarAvatarGremio) {
        this.permisoCambiarAvatarGremio = permisoCambiarAvatarGremio;
    }

    public boolean isPermisoCambiarFondoGremio() {
        return permisoCambiarFondoGremio;
    }

    public void setPermisoCambiarFondoGremio(boolean permisoCambiarFondoGremio) {
        this.permisoCambiarFondoGremio = permisoCambiarFondoGremio;
    }

    public boolean isPermisoCambiarNombreGremio() {
        return permisoCambiarNombreGremio;
    }

    public void setPermisoCambiarNombreGremio(boolean permisoCambiarNombreGremio) {
        this.permisoCambiarNombreGremio = permisoCambiarNombreGremio;
    }

    public boolean isPermisoCambiarDescripcionGremio() {
        return permisoCambiarDescripcionGremio;
    }

    public void setPermisoCambiarDescripcionGremio(boolean permisoCambiarDescripcionGremio) {
        this.permisoCambiarDescripcionGremio = permisoCambiarDescripcionGremio;
    }

    public boolean isPermisoCrearRaids() {
        return permisoCrearRaids;
    }

    public void setPermisoCrearRaids(boolean permisoCrearRaids) {
        this.permisoCrearRaids = permisoCrearRaids;
    }

    public boolean isPermisoEditarRaids() {
        return permisoEditarRaids;
    }

    public void setPermisoEditarRaids(boolean permisoEditarRaids) {
        this.permisoEditarRaids = permisoEditarRaids;
    }

    public boolean isPermisoVisualizarRaids() {
        return permisoVisualizarRaids;
    }

    public void setPermisoVisualizarRaids(boolean permisoVisualizarRaids) {
        this.permisoVisualizarRaids = permisoVisualizarRaids;
    }

    public boolean isPermisoCrearRoles() {
        return permisoCrearRoles;
    }

    public void setPermisoCrearRoles(boolean permisoCrearRoles) {
        this.permisoCrearRoles = permisoCrearRoles;
    }

    public boolean isPermisoEditarRoles() {
        return permisoEditarRoles;
    }

    public void setPermisoEditarRoles(boolean permisoEditarRoles) {
        this.permisoEditarRoles = permisoEditarRoles;
    }

    public boolean isPermisoVisualizarRoles() {
        return permisoVisualizarRoles;
    }

    public void setPermisoVisualizarRoles(boolean permisoVisualizarRoles) {
        this.permisoVisualizarRoles = permisoVisualizarRoles;
    }

    public boolean isPermisoBotarMiembros() {
        return permisoBotarMiembros;
    }

    public void setPermisoBotarMiembros(boolean permisoBotarMiembros) {
        this.permisoBotarMiembros = permisoBotarMiembros;
    }

    public boolean isPermisoSalirGremio() {
        return permisoSalirGremio;
    }

    public void setPermisoSalirGremio(boolean permisoSalirGremio) {
        this.permisoSalirGremio = permisoSalirGremio;
    }

    public boolean isPermisoEliminarGremio() {
        return permisoEliminarGremio;
    }

    public void setPermisoEliminarGremio(boolean permisoEliminarGremio) {
        this.permisoEliminarGremio = permisoEliminarGremio;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }
}
