package com.guildgate.web.Service;

import com.guildgate.web.Modelo.ImagenPerfil;
import com.guildgate.web.Service.generic.ICrudService;

/**
 *
 * @author Juan - Luis
 */
public interface IPerfilService extends ICrudService<ImagenPerfil, Long> {
    
    ImagenPerfil buscarImagenPorNombre(String nombreArchivo);
}
