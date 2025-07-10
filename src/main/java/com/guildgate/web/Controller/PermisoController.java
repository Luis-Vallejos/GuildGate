package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.Permiso;
import com.guildgate.web.Service.impl.PermisoServiceImpl;
import jakarta.inject.Inject;
import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 */
public class PermisoController {

    @Inject
    PermisoServiceImpl ps;

    public PermisoController() {
        this.ps = new PermisoServiceImpl();
    }

    public boolean creacion(Permiso p) {
        boolean estado = ps.create(p);
        return estado;
    }

    public boolean edicion(Permiso p) {
        boolean estado = ps.edit(p);
        return estado;
    }

    public boolean eliminar(Integer id) {
        boolean estado = ps.delete(id);
        return estado;
    }

    public Permiso buscarPorId(Integer id) {
        return ps.findById(id);
    }

    public ArrayList<Permiso> buscarTodos() {
        return ps.findAll();
    }

}
