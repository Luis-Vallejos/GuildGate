package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.Roles;
import com.guildgate.web.Service.impl.RoleServiceImpl;
import jakarta.inject.Inject;
import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 */
public class RolesController {

    @Inject
    RoleServiceImpl rs;

    public RolesController() {
        this.rs = new RoleServiceImpl();
    }

    public boolean creacion(Roles r) {
        boolean estado = rs.create(r);
        return estado;
    }

    public boolean edicion(Roles r) {
        boolean estado = rs.edit(r);
        return estado;
    }

    public boolean eliminar(Integer id) {
        boolean estado = rs.delete(id);
        return estado;
    }

    public Roles buscarPorId(Integer id) {
        return rs.findById(id);
    }

    public ArrayList<Roles> buscarTodos() {
        return rs.findAll();
    }

}
