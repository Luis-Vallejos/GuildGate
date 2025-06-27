package com.guildgate.web.Service;

import com.guildgate.web.Modelo.ImagenPerfil;
import com.guildgate.web.Persistence.ImagenPerfilJpaController;
import jakarta.inject.Inject;
import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 */
public class PerfilService implements IPerfilService {

    @Inject
    ImagenPerfilJpaController ijc;

    public PerfilService() {
        this.ijc = new ImagenPerfilJpaController();
    }
    
    @Override
    public ImagenPerfil findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ImagenPerfil> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean create(ImagenPerfil entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean edit(ImagenPerfil entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ImagenPerfil buscarImagenPorNombre(String nombreArchivo) {
        return ijc.findImagenByNombre(nombreArchivo);
    }
}
