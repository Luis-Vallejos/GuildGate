package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.Ronda;
import com.guildgate.web.Service.impl.RondaServiceImpl;
import jakarta.inject.Inject;
import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 */
public class RondaController {

    @Inject
    RondaServiceImpl rs;

    public RondaController() {
        this.rs = new RondaServiceImpl();
    }

    public boolean creacion(Ronda r) {
        boolean estado = rs.create(r);
        return estado;
    }

    public boolean edicion(Ronda r) {
        boolean estado = rs.edit(r);
        return estado;
    }

    public boolean eliminar(Integer id) {
        boolean estado = rs.delete(id);
        return estado;
    }

    public Ronda buscarPorId(Integer id) {
        return rs.findById(id);
    }

    public ArrayList<Ronda> buscarTodos() {
        return rs.findAll();
    }
}
