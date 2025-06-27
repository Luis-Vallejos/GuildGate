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
import jakarta.persistence.UniqueConstraint;

/**
 *
 * @author Juan - Luis
 */
@Entity
@Table(
        name = "Bosses",
        schema = "gremiosyraids",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "nombreBoss_unique",
                    columnNames = "Nombre_Boss"
            )
        }
)
public class Bosses implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "Nombre_Boss")
    private String nombre;

    @OneToMany(mappedBy = "boss")
    private List<Participaciones> listaParticipaciones;

    @ManyToOne
    @JoinColumn(name = "Id_Raid")
    private Raid raidboss;

    public Bosses() {
    }

    public Bosses(int id, String nombre, List<Participaciones> listaParticipaciones, Raid raidboss) {
        this.id = id;
        this.nombre = nombre;
        this.listaParticipaciones = listaParticipaciones;
        this.raidboss = raidboss;
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

    public List<Participaciones> getListaParticipaciones() {
        return listaParticipaciones;
    }

    public void setListaParticipaciones(List<Participaciones> listaParticipaciones) {
        this.listaParticipaciones = listaParticipaciones;
    }

    public Raid getRaidboss() {
        return raidboss;
    }

    public void setRaidboss(Raid raidboss) {
        this.raidboss = raidboss;
    }
}
