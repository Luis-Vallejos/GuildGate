package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.Region;
import com.guildgate.web.Service.impl.RegionServiceImpl;
import jakarta.inject.Inject;
import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 */
public class RegionController {

    @Inject
    RegionServiceImpl rs;

    public RegionController() {
        this.rs = new RegionServiceImpl();
    }

    public boolean creacion(Region r) {
        boolean estado = rs.create(r);
        return estado;
    }

    public boolean edicion(Region r) {
        boolean estado = rs.edit(r);
        return estado;
    }

    public boolean eliminar(Integer id) {
        boolean estado = rs.delete(id);
        return estado;
    }

    public Region buscarPorId(Integer id) {
        return rs.findById(id);
    }

    public ArrayList<Region> buscarTodos() {
        return rs.findAll();
    }

}
