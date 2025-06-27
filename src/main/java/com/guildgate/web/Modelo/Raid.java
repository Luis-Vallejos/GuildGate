package com.guildgate.web.Modelo;

import java.io.Serializable;
import java.util.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author Juan - Luis
 */
@Entity
@Table(
        name = "Raids",
        schema = "gremiosyraids"
)
public class Raid implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "Nombre_Raid", nullable = false)
    private String nombre;

    @Column(name = "Fecha_Inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "Fecha_Finalizacion")
    @Temporal(TemporalType.DATE)
    private Date fechaFinalizacion;

    @ManyToOne
    @JoinColumn(name = "Id_Gremio")
    private Gremio raidgremio;

    @OneToMany(mappedBy = "raidparti")
    private List<Participaciones> listaParticipaciones;

    @OneToMany(mappedBy = "raidronda")
    private List<Ronda> listaRondas;

    @OneToMany(mappedBy = "raidboss")
    private List<Bosses> listaBosses;

    public Raid() {
    }

    public Raid(int id, String nombre, Date fechaInicio, Date fechaFinalizacion, Gremio raidgremio, List<Participaciones> listaParticipaciones, List<Ronda> listaRondas, List<Bosses> listaBosses) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFinalizacion = fechaFinalizacion;
        this.raidgremio = raidgremio;
        this.listaParticipaciones = listaParticipaciones;
        this.listaRondas = listaRondas;
        this.listaBosses = listaBosses;
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public Gremio getRaidgremio() {
        return raidgremio;
    }

    public void setRaidgremio(Gremio raidgremio) {
        this.raidgremio = raidgremio;
    }

    public List<Participaciones> getListaParticipaciones() {
        return listaParticipaciones;
    }

    public void setListaParticipaciones(List<Participaciones> listaParticipaciones) {
        this.listaParticipaciones = listaParticipaciones;
    }

    public List<Ronda> getListaRondas() {
        return listaRondas;
    }

    public void setListaRondas(List<Ronda> listaRondas) {
        this.listaRondas = listaRondas;
    }

    public List<Bosses> getListaBosses() {
        return listaBosses;
    }

    public void setListaBosses(List<Bosses> listaBosses) {
        this.listaBosses = listaBosses;
    }
}
