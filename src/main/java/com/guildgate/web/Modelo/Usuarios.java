package com.guildgate.web.Modelo;

import com.guildgate.web.Modelo.UsuarioRoles;
import com.guildgate.web.Modelo.Participaciones;
import com.guildgate.web.Modelo.ImagenPerfil;
import com.guildgate.web.Modelo.ImagenBanner;
import com.guildgate.web.Modelo.Gremio;
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

/**
 *
 * @author Juan - Luis
 */
@Entity
@Table(
        name = "Usuarios",
        schema = "gremiosyraids",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "nombreUsuario_unique",
                    columnNames = "Nombre_Usuario"
            )
        }
)
public class Usuarios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "Nombre_Usuario", nullable = false)
    private String nombre;

    @Column(name = "Correo_Electronico", nullable = false)
    private String correo;

    @Column(name = "Contrasenia", nullable = false)
    private String contrasena;

    @Column(name = "Bio_Usuario")
    private String bio;

    @Column(name = "Nivel")
    private int nivel;

    @ManyToOne
    @JoinColumn(name = "Id_Gremio")
    private Gremio gremiousuario;

    @OneToMany(mappedBy = "userparti")
    private List<Participaciones> listaParticipaciones;

    @OneToMany(mappedBy = "usuariouserrol")
    private List<UsuarioRoles> listaUsuariosRol;

    @ManyToOne
    @JoinColumn(name = "Id_Perfil")
    private ImagenPerfil img;

    @ManyToOne
    @JoinColumn(name = "Id_Banner")
    private ImagenBanner imgB;

    public Usuarios() {
    }

    public Usuarios(int id, String nombre, String correo, String contrasena, String bio, int nivel, Gremio gremiousuario, List<Participaciones> listaParticipaciones, List<UsuarioRoles> listaUsuariosRol, ImagenPerfil img, ImagenBanner imgB) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.bio = bio;
        this.nivel = nivel;
        this.gremiousuario = gremiousuario;
        this.listaParticipaciones = listaParticipaciones;
        this.listaUsuariosRol = listaUsuariosRol;
        this.img = img;
        this.imgB = imgB;
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

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Participaciones> getListaParticipaciones() {
        return listaParticipaciones;
    }

    public void setListaParticipaciones(List<Participaciones> listaParticipaciones) {
        this.listaParticipaciones = listaParticipaciones;
    }

    public Gremio getGremiousuario() {
        return gremiousuario;
    }

    public void setGremiousuario(Gremio gremiousuario) {
        this.gremiousuario = gremiousuario;
    }

    public List<UsuarioRoles> getListaUsuariosRol() {
        return listaUsuariosRol;
    }

    public void setListaUsuariosRol(List<UsuarioRoles> listaUsuariosRol) {
        this.listaUsuariosRol = listaUsuariosRol;
    }

    public ImagenPerfil getImg() {
        return img;
    }

    public void setImg(ImagenPerfil img) {
        this.img = img;
    }

    public ImagenBanner getImgB() {
        return imgB;
    }

    public void setImgB(ImagenBanner imgB) {
        this.imgB = imgB;
    }
}
