package com.guildgate.web.Modelo;

import com.guildgate.web.Modelo.Bosses;
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
import com.guildgate.web.Modelo.ParticipacionesExtra;
import com.guildgate.web.Modelo.Raid;
import com.guildgate.web.Modelo.Ronda;
import com.guildgate.web.Modelo.Usuarios;

/**
 *
 * @author Juan - Luis
 */
@Entity
@Table(
        name = "Participaciones",
        schema = "gremiosyraids"
)
public class Participaciones implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "Dano")
    private long danio;

    @Column(name = "Fecha_Participacion")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "Id_Usuario")
    private Usuarios userparti;

    @ManyToOne
    @JoinColumn(name = "Id_Boss")
    private Bosses boss;

    @ManyToOne
    @JoinColumn(name = "Id_Participacion")
    private Raid raidparti;

    @ManyToOne
    @JoinColumn(name = "Id_Ronda")
    private Ronda partironda;

    @OneToMany(mappedBy = "parti")
    private List<ParticipacionesExtra> listaPartiExtra;

    public Participaciones() {
    }

    public Participaciones(int id, long danio, Date fecha, Usuarios userparti, Bosses boss, Raid raidparti, Ronda partironda, List<ParticipacionesExtra> listaPartiExtra) {
        this.id = id;
        this.danio = danio;
        this.fecha = fecha;
        this.userparti = userparti;
        this.boss = boss;
        this.raidparti = raidparti;
        this.partironda = partironda;
        this.listaPartiExtra = listaPartiExtra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public long getDanio() {
        return danio;
    }

    public void setDanio(long danio) {
        this.danio = danio;
    }

    public Usuarios getUserparti() {
        return userparti;
    }

    public void setUserparti(Usuarios userparti) {
        this.userparti = userparti;
    }

    public Bosses getBoss() {
        return boss;
    }

    public void setBoss(Bosses boss) {
        this.boss = boss;
    }

    public Raid getRaidparti() {
        return raidparti;
    }

    public void setRaidparti(Raid raidparti) {
        this.raidparti = raidparti;
    }

    public List<ParticipacionesExtra> getListaPartiExtra() {
        return listaPartiExtra;
    }

    public void setListaPartiExtra(List<ParticipacionesExtra> listaPartiExtra) {
        this.listaPartiExtra = listaPartiExtra;
    }

    public Ronda getPartironda() {
        return partironda;
    }

    public void setPartironda(Ronda partironda) {
        this.partironda = partironda;
    }
}
