package com.guildgate.web.Service;

import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Persistence.UsuariosJpaController;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan - Luis
 */
public class UsuarioService implements IUsuarioService {

    @Inject
    UsuariosJpaController ujc;

    public UsuarioService() {
        this.ujc = new UsuariosJpaController();
    }

    @Override
    public Usuarios findById(Integer id) {
        return ujc.findUsuarios(id);
    }

    @Override
    public ArrayList<Usuarios> findAll() {
        ArrayList<Usuarios> users = SvUtils.toArrayList(ujc.findUsuariosEntities());
        return users;
    }

    @Override
    public boolean create(Usuarios u) {
        ujc.create(u);
        return true;
    }

    @Override
    public boolean edit(Usuarios u) {
        try {
            ujc.edit(u);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        try {
            ujc.destroy(id);
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public void editarInformacionBasica(Integer id, String nombre, String correo, String contrasena, String bio) {
        try {
            ujc.editBasicInfo(id, nombre, correo, contrasena, bio);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public long traerCantidadUsuarios(Integer gremioId) {
        return ujc.contarUsuariosPorGremio(gremioId);
    }
}
