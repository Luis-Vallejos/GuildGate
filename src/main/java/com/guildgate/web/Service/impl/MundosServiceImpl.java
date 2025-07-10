package com.guildgate.web.Service.impl;

import com.guildgate.web.Modelo.Mundos;
import com.guildgate.web.Persistence.MundosJpaController;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import com.guildgate.web.Service.IMundosService;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan - Luis
 */
public class MundosServiceImpl implements IMundosService {

    private static final Logger LOGGER = Logger.getLogger(MundosServiceImpl.class.getName());

    @Inject
    MundosJpaController mjc;

    public MundosServiceImpl() {
        this.mjc = new MundosJpaController();
    }

    @Override
    public Mundos findById(Integer id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "findById falló: id es null");
            return null;
        }
        return mjc.findMundos(id);
    }

    @Override
    public ArrayList<Mundos> findAll() {
        return SvUtils.toArrayList(mjc.findMundosEntities());
    }

    @Override
    public boolean create(Mundos entity) {
        if (entity == null) {
            LOGGER.log(Level.WARNING, "create falló: Mundos es null");
            return false;
        }
        try {
            mjc.create(entity);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al crear Mundos: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean edit(Mundos entity) {
        if (entity == null || entity.getId() == null) {
            LOGGER.log(Level.WARNING, "edit falló: entidad o entidad.id es null");
            return false;
        }
        try {
            mjc.edit(entity);
            return true;
        } catch (NonexistentEntityException nex) {
            LOGGER.log(Level.WARNING, "edit falló: entidad inexistente con ID {0}", entity.getId());
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al editar Mundos: {0}", ex.toString());
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
            mjc.destroy(id);
            return true;
        } catch (NonexistentEntityException ex) {
            LOGGER.log(Level.WARNING, "delete falló: no existe Mundos con ID {0}", id);
            return false;
        } catch (IllegalOrphanException ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar Mundos: {0}", ex.toString());
            return false;
        }
    }

}
