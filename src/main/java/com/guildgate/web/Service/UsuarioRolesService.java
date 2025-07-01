package com.guildgate.web.Service;

import com.guildgate.web.Modelo.UsuarioRoles;
import com.guildgate.web.Persistence.UsuarioRolesJpaController;
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
public class UsuarioRolesService implements IUsuarioRolesService {

    private static final Logger LOGGER = Logger.getLogger(UsuarioRolesService.class.getName());

    @Inject
    UsuarioRolesJpaController urs;

    public UsuarioRolesService() {
        this.urs = new UsuarioRolesJpaController();
    }

    @Override
    public UsuarioRoles findById(Long id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "findById falló: id es null");
            return null;
        }
        return urs.findUsuarioRoles(id);
    }

    @Override
    public ArrayList<UsuarioRoles> findAll() {
        return SvUtils.toArrayList(urs.findUsuarioRolesEntities());
    }

    @Override
    public boolean create(UsuarioRoles entity) {
        if (entity == null) {
            LOGGER.log(Level.WARNING, "create falló: UsuarioRoles es null");
            return false;
        }
        try {
            urs.create(entity);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al crear UsuarioRoles: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean edit(UsuarioRoles entity) {
        if (entity == null || entity.getId() == null) {
            LOGGER.log(Level.WARNING, "edit falló: entidad o entidad.id es null");
            return false;
        }
        try {
            urs.edit(entity);
            return true;
        } catch (NonexistentEntityException nex) {
            LOGGER.log(Level.WARNING, "edit falló: entidad inexistente con ID {0}", entity.getId());
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al editar UsuarioRoles: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "delete falló: id es null");
            return false;
        }
        try {
            urs.destroy(id);
            return true;
        } catch (NonexistentEntityException ex) {
            LOGGER.log(Level.WARNING, "delete falló: no existe UsuarioRoles con ID {0}", id);
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar UsuarioRoles: {0}", ex.toString());
            return false;
        }
    }
}
