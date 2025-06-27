package com.guildgate.web.Modelo;

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

/**
 *
 * @author Juan - Luis
 */
@Entity
@Table(
        name = "Ronda",
        schema = "gremiosyraids"
)
public class Ronda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "Nombre_Ronda")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "Id_Raid")
    private Raid raidronda;

    @OneToMany(mappedBy = "partironda")
    private List<Participaciones> listaParticipaciones;

    @OneToMany(mappedBy = "partiextraronda")
    private List<ParticipacionesExtra> listaParticipacionesExtra;

    public Ronda() {
    }

    public Ronda(int id, String nombre, Raid raidronda, List<Participaciones> listaParticipaciones, List<ParticipacionesExtra> listaParticipacionesExtra) {
        this.id = id;
        this.nombre = nombre;
        this.raidronda = raidronda;
        this.listaParticipaciones = listaParticipaciones;
        this.listaParticipacionesExtra = listaParticipacionesExtra;
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

    public Raid getRaidronda() {
        return raidronda;
    }

    public void setRaidronda(Raid raidronda) {
        this.raidronda = raidronda;
    }

    public List<Participaciones> getListaParticipaciones() {
        return listaParticipaciones;
    }

    public void setListaParticipaciones(List<Participaciones> listaParticipaciones) {
        this.listaParticipaciones = listaParticipaciones;
    }

    public List<ParticipacionesExtra> getListaParticipacionesExtra() {
        return listaParticipacionesExtra;
    }

    public void setListaParticipacionesExtra(List<ParticipacionesExtra> listaParticipacionesExtra) {
        this.listaParticipacionesExtra = listaParticipacionesExtra;
    }
}
