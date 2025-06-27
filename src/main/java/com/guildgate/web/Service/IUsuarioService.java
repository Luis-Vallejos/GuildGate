package com.guildgate.web.Service;

import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Service.generic.ICrudService;

/**
 *
 * @author Juan - Luis
 */
public interface IUsuarioService extends ICrudService<Usuarios, Integer> {

    void editarInformacionBasica(Integer id, String nombre, String correo, String contrasena, String bio);
    
    public long traerCantidadUsuarios(Integer gremioId);
}
