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
        name = "Imagenes_Banner",
        schema = "gremiosyraids"
)
public class ImagenBanner implements Serializable {

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

    @Column(name = "Posicion_X")
    private String posicionX;

    @Column(name = "Posicion_Y")
    private String posicionY;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Origen_Archivo")
    private OrigenArchivo origenArchivo;
    
    @OneToMany(mappedBy = "imgB")
    private List<Usuarios> listaJugadores;

    public ImagenBanner() {
    }

    public ImagenBanner(long id, byte[] data, String nomArchivo, String tipoArchivo, String posicionX, String posicionY, OrigenArchivo origenArchivo, List<Usuarios> listaJugadores) {
        this.id = id;
        this.data = data;
        this.nomArchivo = nomArchivo;
        this.tipoArchivo = tipoArchivo;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.origenArchivo = origenArchivo;
        this.listaJugadores = listaJugadores;
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

    public String getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(String posicionX) {
        this.posicionX = posicionX;
    }

    public String getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(String posicionY) {
        this.posicionY = posicionY;
    }

    public OrigenArchivo getOrigenArchivo() {
        return origenArchivo;
    }

    public void setOrigenArchivo(OrigenArchivo origenArchivo) {
        this.origenArchivo = origenArchivo;
    }

    public List<Usuarios> getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(List<Usuarios> listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    public enum OrigenArchivo {
        PREDETERMINADA,
        USUARIO
    }
}
