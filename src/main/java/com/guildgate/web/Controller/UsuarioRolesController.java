package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.UsuarioRoles;
import com.guildgate.web.Service.impl.UsuarioRolesServiceImpl;
import jakarta.inject.Inject;
import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 */
public class UsuarioRolesController {

    @Inject
    UsuarioRolesServiceImpl urs;

    public UsuarioRolesController() {
        this.urs = new UsuarioRolesServiceImpl();
    }

    public boolean creacion(UsuarioRoles ur) {
        boolean estado = urs.create(ur);
        return estado;
    }

    public boolean edicion(UsuarioRoles ur) {
        boolean estado = urs.edit(ur);
        return estado;
    }

    public boolean eliminar(Long id) {
        boolean estado = urs.delete(id);
        return estado;
    }

    public UsuarioRoles buscarPorId(Long id) {
        return urs.findById(id);
    }

    public ArrayList<UsuarioRoles> buscarTodos() {
        return urs.findAll();
    }
}
