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
        name = "Mundos",
        schema = "gremiosyraids",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "nombreMundo_unique",
                    columnNames = "Nombre_Mundo"
            )
        }
)
public class Mundos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "Nombre_Mundo")
    private String nombre;

    @OneToMany(mappedBy = "gremiomundo")
    private List<Gremio> listaGremios;

    public Mundos() {
    }

    public Mundos(int id, String nombre, List<Gremio> listaGremios) {
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
