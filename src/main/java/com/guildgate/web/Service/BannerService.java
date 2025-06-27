package com.guildgate.web.Service;

import com.guildgate.web.Modelo.ImagenBanner;
import com.guildgate.web.Persistence.ImagenBannerJpaController;
import jakarta.inject.Inject;
import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 */
public class BannerService implements IBannerService {

    @Inject
    ImagenBannerJpaController ibjc;

    public BannerService() {
        this.ibjc = new ImagenBannerJpaController();
    }

    @Override
    public ImagenBanner findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<ImagenBanner> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean create(ImagenBanner entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean edit(ImagenBanner entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ImagenBanner buscarBannerPorNombre(String nombreBanner) {
        return ibjc.findBannerByNombre(nombreBanner);
    }
}
