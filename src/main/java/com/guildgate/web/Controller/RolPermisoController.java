package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.RolPermiso;
import com.guildgate.web.Modelo.RolPermisoId;
import com.guildgate.web.Service.impl.RolPermisoServiceImpl;
import jakarta.inject.Inject;
import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 */
public class RolPermisoController {

    @Inject
    RolPermisoServiceImpl rps;

    public RolPermisoController() {
        this.rps = new RolPermisoServiceImpl();
    }

    public boolean creacion(RolPermiso rp) {
        boolean estado = rps.create(rp);
        return estado;
    }

    public boolean edicion(RolPermiso rp) {
        boolean estado = rps.edit(rp);
        return estado;
    }

    public boolean eliminar(RolPermisoId id) {
        boolean estado = rps.delete(id);
        return estado;
    }

    public RolPermiso buscarPorId(RolPermisoId id) {
        return rps.findById(id);
    }

    public ArrayList<RolPermiso> buscarTodos() {
        return rps.findAll();
    }
}
