package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.Raid;
import com.guildgate.web.Service.RaidService;
import jakarta.inject.Inject;
import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 */
public class RaidController {

    @Inject
    RaidService rs;

    public RaidController() {
        this.rs = new RaidService();
    }

    public boolean creacion(Raid r) {
        boolean estado = rs.create(r);
        return estado;
    }

    public boolean editacion(Raid r) {
        boolean estado = rs.edit(r);
        return estado;
    }

    public boolean eliminar(Integer id) {
        boolean estado = rs.delete(id);
        return estado;
    }

    public Raid buscarPorId(Integer id) {
        return rs.findById(id);
    }

    public ArrayList<Raid> buscarTodos() {
        return rs.findAll();
    }

}
