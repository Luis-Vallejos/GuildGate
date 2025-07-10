package com.guildgate.web.Controller;

import com.guildgate.web.Bean.AvatarBean;
import com.guildgate.web.Bean.BannerBean;
import com.guildgate.web.Bean.GuildBean;
import com.guildgate.web.Bean.RolBean;
import com.guildgate.web.Bean.SessionUserBean;
import com.guildgate.web.Bean.UserProfileBean;
import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Modelo.Roles;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Service.impl.BannerServiceImpl;
import com.guildgate.web.Service.mapper.PerfilBeanMapper;
import com.guildgate.web.Service.mapper.AvatarBeanMapper;
import com.guildgate.web.Service.mapper.BannerBeanMapper;
import com.guildgate.web.Service.mapper.GuildBeanMapper;
import com.guildgate.web.Service.mapper.RolBeanMapper;
import com.guildgate.web.Service.impl.GremioServiceImpl;
import com.guildgate.web.Service.impl.PerfilServiceImpl;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Juan - Luis
 */
public class AuthController {

    // Service
    @Inject
    GremioServiceImpl gs;

    @Inject
    PerfilServiceImpl ps;

    @Inject
    BannerServiceImpl bs;

    // Mapper
    @Inject
    PerfilBeanMapper pbm;
    @Inject
    AvatarBeanMapper abm;
    @Inject
    BannerBeanMapper bbm;
    @Inject
    GuildBeanMapper gbm;
    @Inject
    RolBeanMapper rbm;

    public AuthController() {
        this.gs = new GremioServiceImpl();
        this.ps = new PerfilServiceImpl();
        this.bs = new BannerServiceImpl();
        this.pbm = new PerfilBeanMapper();
        this.abm = new AvatarBeanMapper();
        this.bbm = new BannerBeanMapper();
        this.gbm = new GuildBeanMapper();
        this.rbm = new RolBeanMapper();
    }

    public void processSuccessfulLogin(HttpServletResponse response,
            HttpServletRequest request,
            Usuarios user) throws IOException {
        HttpSession session = request.getSession(true);

        // 1. Perfil
        UserProfileBean perfilBean = pbm.toBean(user);

        // 2. Avatar de usuario
        AvatarBean avatarBean = abm.toBean(user.getImg(), ps);

        // 3. Banner de usuario
        BannerBean bannerBean = bbm.toBean(user.getImgB(), bs);

        // 4. Gremio y su visual
        Gremio gremioEntidad = user.getGremiousuario();
        GuildBean guildBean = null;
        if (gremioEntidad != null) {
            // refresca la entidad completa
            Optional<Gremio> opt = SvUtils.findGremioById(gremioEntidad.getId(),
                    gs.findAll());
            guildBean = opt.map(g -> gbm.toBean(g)).orElse(null);
        }

        // 5. Roles y permisos
        // Supongo que SvUtils.obtenerObjetoRolPorUsuario devuelve un Roles
        Optional<Roles> optRol = SvUtils.obtenerObjetoRolPorUsuario(user);
        Set<RolBean> roles = optRol
                .map(r -> Set.of(rbm.toBean(r)))
                .orElse(Set.of());

        // 6. Construyo el SessionUserBean
        SessionUserBean sessionUser = SessionUserBean.builder()
                .perfil(perfilBean)
                .avatar(avatarBean)
                .banner(bannerBean)
                .gremio(guildBean)
                .roles(roles)
                .build();

        // 7. Lo dejo en la sesión
        session.setAttribute("sessionUserBean", sessionUser);
    }
}
