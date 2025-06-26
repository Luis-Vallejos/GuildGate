package com.guildgate.web.Modelo;

import com.guildgate.web.Modelo.Participaciones;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.guildgate.web.Modelo.Ronda;

/**
 *
 * @author Juan - Luis
 */
@Entity
@Table(
        name = "Participaciones_Extra",
        schema = "gremiosyraids"
)
public class ParticipacionesExtra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "Dano_Extra")
    private long danoExtra;

    @ManyToOne
    @JoinColumn(name = "Id_Participacion")
    private Participaciones parti;

    @ManyToOne
    @JoinColumn(name = "Id_Ronda")
    private Ronda partiextraronda;

    public ParticipacionesExtra() {
    }

    public ParticipacionesExtra(int id, long danoExtra, Participaciones parti, Ronda partiextraronda) {
        this.id = id;
        this.danoExtra = danoExtra;
        this.parti = parti;
        this.partiextraronda = partiextraronda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDanoExtra() {
        return danoExtra;
    }

    public void setDanoExtra(long danoExtra) {
        this.danoExtra = danoExtra;
    }

    public Participaciones getParti() {
        return parti;
    }

    public void setParti(Participaciones parti) {
        this.parti = parti;
    }

    public Ronda getPartiextraronda() {
        return partiextraronda;
    }

    public void setPartiextraronda(Ronda partiextraronda) {
        this.partiextraronda = partiextraronda;
    }
}
