package com.guildgate.web.Modelo;

import com.guildgate.web.Modelo.FondoGremio;
import com.guildgate.web.Modelo.AvatarGremio;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import com.guildgate.web.Modelo.Mundos;
import com.guildgate.web.Modelo.Raid;
import com.guildgate.web.Modelo.Region;
import com.guildgate.web.Modelo.UsuarioRoles;
import com.guildgate.web.Modelo.Usuarios;

/**
 *
 * @author Lavender
 */
@Entity
@Table(
        name = "Gremio",
        schema = "gremiosyraids",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "nombre_unique",
                    columnNames = "Nombre_Gremio"
            )
        }
)
public class Gremio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "Nombre_Gremio")
    private String nombre;

    @Column(name = "Descripcion_Gremio")
    private String descripcion;

    @Column(name = "CantMaxima_Gremio")
    private int cantidad;

    @OneToMany(mappedBy = "raidgremio")
    private List<Raid> listaRaid;

    @OneToMany(mappedBy = "gremiousuario")
    private List<Usuarios> listaUsuarios;

    @ManyToOne
    @JoinColumn(name = "Id_Region")
    private Region gremioregion;

    @ManyToOne
    @JoinColumn(name = "Id_Mundo")
    private Mundos gremiomundo;

    @OneToMany(mappedBy = "gremiouserrol")
    private List<UsuarioRoles> listaUsuariosRoles;

    @ManyToOne
    @JoinColumn(name = "Id_Avatar")
    private AvatarGremio img;
    
    @ManyToOne
    @JoinColumn(name = "Id_Fondo")
    private FondoGremio imgF;

    public Gremio() {
    }

    public Gremio(int id, String nombre, String descripcion, int cantidad, List<Raid> listaRaid, List<Usuarios> listaUsuarios, Region gremioregion, Mundos gremiomundo, List<UsuarioRoles> listaUsuariosRoles, AvatarGremio img, FondoGremio imgF) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.listaRaid = listaRaid;
        this.listaUsuarios = listaUsuarios;
        this.gremioregion = gremioregion;
        this.gremiomundo = gremiomundo;
        this.listaUsuariosRoles = listaUsuariosRoles;
        this.img = img;
        this.imgF = imgF;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public List<Raid> getListaRaid() {
        return listaRaid;
    }

    public void setListaRaid(List<Raid> listaRaid) {
        this.listaRaid = listaRaid;
    }

    public List<Usuarios> setListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaJugadores(List<Usuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Region getGremioregion() {
        return gremioregion;
    }

    public void setGremioregion(Region gremioregion) {
        this.gremioregion = gremioregion;
    }

    public Mundos getGremiomundo() {
        return gremiomundo;
    }

    public void setGremiomundo(Mundos gremiomundo) {
        this.gremiomundo = gremiomundo;
    }

    public List<UsuarioRoles> getListaUsuariosRoles() {
        return listaUsuariosRoles;
    }

    public void setListaUsuariosRoles(List<UsuarioRoles> listaUsuariosRoles) {
        this.listaUsuariosRoles = listaUsuariosRoles;
    }

    public List<Usuarios> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public AvatarGremio getImg() {
        return img;
    }

    public void setImg(AvatarGremio img) {
        this.img = img;
    }

    public FondoGremio getImgF() {
        return imgF;
    }

    public void setImgF(FondoGremio imgF) {
        this.imgF = imgF;
    }
}
