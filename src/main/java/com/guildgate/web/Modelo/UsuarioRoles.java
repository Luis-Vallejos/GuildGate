package com.guildgate.web.Modelo;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author Juan - Luis
 */
@Entity
@Table(
        name = "UsuarioRoles",
        schema = "gremiosyraids"
)
public class UsuarioRoles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_Usuario")
    private Usuarios usuariouserrol;

    @ManyToOne
    @JoinColumn(name = "ID_Rol")
    private Roles roluserrol;

    @ManyToOne
    @JoinColumn(name = "ID_Gremio")
    private Gremio gremiouserrol;

    @Temporal(TemporalType.DATE)
    private Date fechaAsignacion;

    public UsuarioRoles() {
    }

    public UsuarioRoles(Long id, Usuarios usuariouserrol, Roles roluserrol, Gremio gremiouserrol, Date fechaAsignacion) {
        this.id = id;
        this.usuariouserrol = usuariouserrol;
        this.roluserrol = roluserrol;
        this.gremiouserrol = gremiouserrol;
        this.fechaAsignacion = fechaAsignacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuarios getUsuariouserrol() {
        return usuariouserrol;
    }

    public void setUsuariouserrol(Usuarios usuariouserrol) {
        this.usuariouserrol = usuariouserrol;
    }

    public Roles getRoluserrol() {
        return roluserrol;
    }

    public void setRoluserrol(Roles roluserrol) {
        this.roluserrol = roluserrol;
    }

    public Gremio getGremiouserrol() {
        return gremiouserrol;
    }

    public void setGremiouserrol(Gremio gremiouserrol) {
        this.gremiouserrol = gremiouserrol;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}
