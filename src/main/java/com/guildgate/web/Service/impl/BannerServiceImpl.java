package com.guildgate.web.Service.impl;

import com.guildgate.web.Modelo.ImagenBanner;
import com.guildgate.web.Persistence.ImagenBannerJpaController;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.guildgate.web.Persistence.UsuariosJpaController;
import com.guildgate.web.Service.IBannerService;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
public class BannerServiceImpl implements IBannerService {

    private static final Logger LOGGER = Logger.getLogger(UsuarioRolesServiceImpl.class.getName());

    @Inject
    ImagenBannerJpaController ibjc;
    
    @Inject
    UsuariosJpaController ujc;

    public BannerServiceImpl() {
        this.ibjc = new ImagenBannerJpaController();
    }

    @Override
    public ImagenBanner findById(Long id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "findById falló: id es null");
            return null;
        }
        return ibjc.findImagenBanner(id);
    }

    @Override
    public ArrayList<ImagenBanner> findAll() {
        return SvUtils.toArrayList(ibjc.findImagenBannerEntities());
    }

    @Override
    public boolean create(ImagenBanner entity) {
        if (entity == null) {
            LOGGER.log(Level.WARNING, "create falló: ImagenBanner es null");
            return false;
        }
        try {
            ibjc.create(entity);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al crear ImagenBanner: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean edit(ImagenBanner entity) {
        if (entity == null || entity.getId() == null) {
            LOGGER.log(Level.WARNING, "edit falló: entidad o entidad.id es null");
            return false;
        }
        try {
            ibjc.edit(entity);
            return true;
        } catch (NonexistentEntityException nex) {
            LOGGER.log(Level.WARNING, "edit falló: entidad inexistente con ID {0}", entity.getId());
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al editar ImagenBanner: {0}", ex.toString());
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
            ibjc.destroy(id);
            return true;
        } catch (NonexistentEntityException ex) {
            LOGGER.log(Level.WARNING, "delete falló: no existe ImagenBanner con ID {0}", id);
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar ImagenBanner: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public ImagenBanner buscarBannerPorNombre(String nombreBanner) {
        if (nombreBanner == null) {
            LOGGER.log(Level.WARNING, "buscarBannerPorNombre falló: nombreBanner es null");
            return null;
        }
        return ibjc.findBannerByNombre(nombreBanner);
    }

    @Override
    public void editarNuevoBanner(int id, ImagenBanner img) {
        try {
            ujc.editBannerUser(id, img);
        } catch (Exception ex) {
            Logger.getLogger(BannerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<ImagenBanner> traerListaBannersPredeterminados() {
        List<ImagenBanner> lista = ibjc.getPredeterminedBanners();
        ArrayList<ImagenBanner> listaBannersPredeterminados = new ArrayList<>(lista);
        return listaBannersPredeterminados;
    }
}
