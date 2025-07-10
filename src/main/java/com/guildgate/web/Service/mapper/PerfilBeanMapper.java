package com.guildgate.web.Service.mapper;

import com.guildgate.web.Bean.UserProfileBean;
import com.guildgate.web.Modelo.Usuarios;
import java.util.Optional;

/**
 *
 * @author Juan - Luis
 */
public class PerfilBeanMapper {

    public UserProfileBean toBean(Usuarios usuario) {
        return UserProfileBean.builder()
                .id(usuario.getId())
                .nomUserActual(usuario.getNombre())
                .correo(usuario.getCorreo())
                .biografia(Optional.ofNullable(usuario.getBio()).orElse(""))
                .contrasenia(Optional.ofNullable(usuario.getContrasena()).orElse(""))
                .nivel(String.valueOf(usuario.getNivel()))
                .build();
    }
}
