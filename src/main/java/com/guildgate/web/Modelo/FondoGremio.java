package com.guildgate.web.Modelo;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import com.guildgate.web.Modelo.Gremio;

/**
 *
 * @author Lavender
 */
@Entity
@Table(
        name = "Fondo_Gremio",
        schema = "gremiosyraids"
)
public class FondoGremio implements Serializable {
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
    private FondoGremio.OrigenArchivo origenArchivo;

    @OneToMany(mappedBy = "imgF")
    private List<Gremio> listaGremios;

    public FondoGremio() {
    }

    public FondoGremio(long id, byte[] data, String nomArchivo, String tipoArchivo, OrigenArchivo origenArchivo, List<Gremio> listaGremios) {
        this.id = id;
        this.data = data;
        this.nomArchivo = nomArchivo;
        this.tipoArchivo = tipoArchivo;
        this.origenArchivo = origenArchivo;
        this.listaGremios = listaGremios;
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

    public List<Gremio> getListaGremios() {
        return listaGremios;
    }

    public void setListaGremios(List<Gremio> listaGremios) {
        this.listaGremios = listaGremios;
    }
    
    public enum OrigenArchivo {
        PREDETERMINADA,
        USUARIO
    }
}
