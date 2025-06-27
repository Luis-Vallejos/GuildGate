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

/**
 *
 * @author Juan - Luis
 */
@Entity
@Table(
        name = "Region",
        schema = "gremiosyraids",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "nombreRegion_unique",
                    columnNames = "Nombre_Region"
            )
        }
)
public class Region implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "Nombre_Region")
    private String nombre;

    @OneToMany(mappedBy = "gremioregion")
    private List<Gremio> listaGremios;

    public Region() {
    }

    public Region(int id, String nombre, List<Gremio> listaGremios) {
        this.id = id;
        this.nombre = nombre;
        this.listaGremios = listaGremios;
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

    public List<Gremio> getListaGremios() {
        return listaGremios;
    }

    public void setListaGremios(List<Gremio> listaGremios) {
        this.listaGremios = listaGremios;
    }
}
