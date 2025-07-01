package com.guildgate.web.Service;

import com.guildgate.web.Modelo.Roles;
import com.guildgate.web.Persistence.RolesJpaController;
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
public class RoleService implements IRoleService {

    private static final Logger LOGGER = Logger.getLogger(RoleService.class.getName());

    @Inject
    RolesJpaController rjc;

    public RoleService() {
        this.rjc = new RolesJpaController();
    }

    @Override
    public Roles findById(Integer id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "findById falló: id es null");
            return null;
        }
        return rjc.findRoles(id);
    }

    @Override
    public ArrayList<Roles> findAll() {
        return SvUtils.toArrayList(rjc.findRolesEntities());
    }

    @Override
    public boolean create(Roles entity) {
        if (entity == null) {
            LOGGER.log(Level.WARNING, "create falló: Roles es null");
            return false;
        }
        try {
            rjc.create(entity);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al crear Roles: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean edit(Roles entity) {
        if (entity == null || entity.getId() == null) {
            LOGGER.log(Level.WARNING, "edit falló: entidad o entidad.id es null");
            return false;
        }
        try {
            rjc.edit(entity);
            return true;
        } catch (NonexistentEntityException nex) {
            LOGGER.log(Level.WARNING, "edit falló: entidad inexistente con ID {0}", entity.getId());
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al editar Roles: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "delete falló: id es null");
            return false;
        }
        try {
            rjc.destroy(id);
            return true;
        } catch (NonexistentEntityException ex) {
            LOGGER.log(Level.WARNING, "delete falló: no existe Roles con ID {0}", id);
            return false;
        } catch (IllegalOrphanException ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar Roles: {0}", ex.toString());
            return false;
        }
    }

}
