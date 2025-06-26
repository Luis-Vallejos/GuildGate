package com.guildgate.web.Modelo;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import com.guildgate.web.Modelo.UsuarioRoles;

/**
 *
 * @author Juan - Luis
 */
@Entity
@Table(
        name = "Rol",
        schema = "gremiosyraids",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "nombreRol_unique",
                    columnNames = "Nombre_Rol"
            )
        }
)
public class Roles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "Nombre_Rol")
    private String nombre;

    @Column(name = "Rol_Descripcion")
    private String descripcion;

    @Column(name = "Permiso_Cambiar_Avatar_Gremio")
    private boolean permisoCambiarAvatarGremio;

    @Column(name = "Permiso_Cambiar_Fondo_Gremio")
    private boolean permisoCambiarFondoGremio;

    @Column(name = "Permiso_Cambiar_Nombre_Gremio")
    private boolean permisoCambiarNombreGremio;

    @Column(name = "Permiso_Cambiar_Descripcion_Gremio")
    private boolean permisoCambiarDescripcionGremio;

    @Column(name = "Permiso_Crear_Raids")
    private boolean permisoCrearRaids;

    @Column(name = "Permiso_Editar_Raids")
    private boolean permisoEditarRaids;

    @Column(name = "Permiso_Visualizar_Raids")
    private boolean permisoVisualizarRaids;

    @Column(name = "Permiso_Crear_Roles")
    private boolean permisoCrearRoles;

    @Column(name = "Permiso_Editar_Roles")
    private boolean permisoEditarRoles;

    @Column(name = "Permiso_Visualizar_Roles")
    private boolean permisoVisualizarRoles;

    @Column(name = "Permiso_Botar_Miembros")
    private boolean permisoBotarMiembros;

    @Column(name = "Permiso_Salir_Gremio")
    private boolean permisoSalirGremio;

    @Column(name = "Permiso_Eliminar_Gremio")
    private boolean permisoEliminarGremio;
    
    @OneToMany(mappedBy = "roluserrol")
    private List<UsuarioRoles> listaUsuariosRoles;
    
    public Roles() {
    }

    public Roles(int id, String nombre, String descripcion, boolean permisoCambiarAvatarGremio, boolean permisoCambiarFondoGremio, boolean permisoCambiarNombreGremio, boolean permisoCambiarDescripcionGremio, boolean permisoCrearRaids, boolean permisoEditarRaids, boolean permisoVisualizarRaids, boolean permisoCrearRoles, boolean permisoEditarRoles, boolean permisoVisualizarRoles, boolean permisoBotarMiembros, boolean permisoSalirGremio, boolean permisoEliminarGremio, List<UsuarioRoles> listaUsuariosRoles) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
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
        this.listaUsuariosRoles = listaUsuariosRoles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public List<UsuarioRoles> getListaUsuariosRoles() {
        return listaUsuariosRoles;
    }

    public void setListaUsuariosRoles(List<UsuarioRoles> listaUsuariosRoles) {
        this.listaUsuariosRoles = listaUsuariosRoles;
    }
}
