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

    @Inject
    RolesJpaController rjc;

    public RoleService() {
        this.rjc = new RolesJpaController();
    }

    @Override
    public Roles findById(Integer id) {
        return rjc.findRoles(id);
    }

    @Override
    public ArrayList<Roles> findAll() {
        ArrayList<Roles> roles = SvUtils.toArrayList(rjc.findRolesEntities());
        return roles;
    }

    @Override
    public boolean create(Roles entity) {
        rjc.create(entity);
        return true;
    }

    @Override
    public boolean edit(Roles entity) {
        try {
            rjc.edit(entity);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(RoleService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RoleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        try {
            rjc.destroy(id);
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            Logger.getLogger(RoleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

}
