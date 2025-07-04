package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.Participaciones;
import com.guildgate.web.Service.ParticipacionesService;
import jakarta.inject.Inject;
import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 */
public class ParticipacionesController {

    @Inject
    ParticipacionesService ps;

    public ParticipacionesController(ParticipacionesService ps) {
        this.ps = new ParticipacionesService();
    }

    public boolean creacion(Participaciones p) {
        boolean estado = ps.create(p);
        return estado;
    }

    public boolean edicion(Participaciones p) {
        boolean estado = ps.edit(p);
        return estado;
    }

    public boolean eliminar(Integer id) {
        boolean estado = ps.delete(id);
        return estado;
    }

    public Participaciones buscarPorId(Integer id) {
        return ps.findById(id);
    }

    public ArrayList<Participaciones> buscarTodo() {
        return ps.findAll();
    }

}
