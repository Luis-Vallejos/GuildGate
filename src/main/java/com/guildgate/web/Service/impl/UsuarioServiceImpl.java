package com.guildgate.web.Service.impl;

import com.guildgate.web.Modelo.ImagenBanner;
import com.guildgate.web.Modelo.ImagenPerfil;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Persistence.UsuariosJpaController;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;
import com.guildgate.web.Service.IUsuarioService;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan - Luis
 */
public class UsuarioServiceImpl implements IUsuarioService {

    private static final Logger LOGGER = Logger.getLogger(UsuarioServiceImpl.class.getName());

    @Inject
    UsuariosJpaController ujc;

    public UsuarioServiceImpl() {
        this.ujc = new UsuariosJpaController();
    }

    @Override
    public Usuarios findById(Integer id) {
        if (id == null) {
            LOGGER.log(Level.WARNING, "findById falló: id es null");
            return null;
        }
        return ujc.findUsuarios(id);
    }

    @Override
    public ArrayList<Usuarios> findAll() {
        return SvUtils.toArrayList(ujc.findUsuariosEntities());
    }

    @Override
    public boolean create(Usuarios u) {
        if (u == null) {
            LOGGER.log(Level.WARNING, "create falló: Usuarios es null");
            return false;
        }
        try {
            ujc.create(u);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al crear Usuarios: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public boolean edit(Usuarios u) {
        if (u == null || u.getId() == null) {
            LOGGER.log(Level.WARNING, "edit falló: entidad o entidad.id es null");
            return false;
        }
        try {
            ujc.edit(u);
            return true;
        } catch (NonexistentEntityException nex) {
            LOGGER.log(Level.WARNING, "edit falló: entidad inexistente con ID {0}", u.getId());
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al editar Usuarios: {0}", ex.toString());
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
            ujc.destroy(id);
            return true;
        } catch (NonexistentEntityException ex) {
            LOGGER.log(Level.WARNING, "delete falló: no existe Usuarios con ID {0}", id);
            return false;
        } catch (IllegalOrphanException ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar Usuarios: {0}", ex.toString());
            return false;
        }
    }

    @Override
    public void editarInformacionBasica(Integer id, String nombre, String correo, String contrasena, String bio) {
        if (id == null || nombre == null || correo == null || contrasena == null || bio == null) {
            LOGGER.log(Level.WARNING, "edit básico falló: los valores o el id son nulos");
        }
        try {
            ujc.editBasicInfo(id, nombre, correo, contrasena, bio);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public long traerCantidadUsuarios(Integer gremioId) {
        if (gremioId == null) {
            LOGGER.log(Level.WARNING, "traerCantidadUsuarios falló: id es null");
            return -1;
        }
        return ujc.contarUsuariosPorGremio(gremioId);
    }

    @Override
    public void editarNuevoAvatar(int id, ImagenPerfil img) {
        try {
            ujc.editAvatarUser(id, img);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editarNuevoBanner(int id, ImagenBanner img) {
        try {
            ujc.editBannerUser(id, img);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
