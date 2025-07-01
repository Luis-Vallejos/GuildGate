package com.guildgate.web.Service;

import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Persistence.GremioJpaController;
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
public class GremioService implements IGremioService {

    @Inject
    GremioJpaController gpc;

    public GremioService() {
        this.gpc = new GremioJpaController();
    }

    @Override
    public Gremio findById(Integer id) {
        return gpc.findGremio(id);
    }

    @Override
    public ArrayList<Gremio> findAll() {
        ArrayList<Gremio> gremios = SvUtils.toArrayList(gpc.findGremioEntities());
        return gremios;
    }

    @Override
    public boolean create(Gremio entity) {
        gpc.create(entity);
        return true;
    }

    @Override
    public boolean edit(Gremio entity) {
        try {
            gpc.edit(entity);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(GremioService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GremioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        try {
            gpc.destroy(id);
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            Logger.getLogger(GremioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
