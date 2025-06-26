package com.guildgate.web.Modelo;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
import com.guildgate.web.Modelo.Usuarios;

/**
 *
 * @author Juan - Luis
 */
@Entity
@Table(
        name = "Imagenes_Perfil",
        schema = "gremiosyraids"
)
public class ImagenPerfil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id")
    private long id;

    @Lob
    @Column(name = "Data_Archivo")
    private byte[] data;

    @Column(name = "Nombre_Archivo")
    private String nomArchivo;

    @Column(name = "Tipo_Archivo")
    private String tipoArchivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "Origen_Archivo")
    private OrigenArchivo origenArchivo;

    @OneToMany(mappedBy = "img")
    private List<Usuarios> listaUsuarios;

    public ImagenPerfil() {
    }

    public ImagenPerfil(long id, byte[] data, String nomArchivo, String tipoArchivo, OrigenArchivo origenArchivo, List<Usuarios> listaUsuarios) {
        this.id = id;
        this.data = data;
        this.nomArchivo = nomArchivo;
        this.tipoArchivo = tipoArchivo;
        this.origenArchivo = origenArchivo;
        this.listaUsuarios = listaUsuarios;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getNomArchivo() {
        return nomArchivo;
    }

    public void setNomArchivo(String nomArchivo) {
        this.nomArchivo = nomArchivo;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public OrigenArchivo getOrigenArchivo() {
        return origenArchivo;
    }

    public void setOrigenArchivo(OrigenArchivo origenArchivo) {
        this.origenArchivo = origenArchivo;
    }

    public List<Usuarios> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public enum OrigenArchivo {
        PREDETERMINADA,
        USUARIO
    }
}
