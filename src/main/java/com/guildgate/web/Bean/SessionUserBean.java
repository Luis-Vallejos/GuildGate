package com.guildgate.web.Bean;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Juan - Luis
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class SessionUserBean {

    private UserProfileBean perfil;
    private AvatarBean avatar;
    private BannerBean banner;
    private GuildBean gremio;
    @Builder.Default
    private Set<RolBean> roles = new HashSet<>();
}
