package com.guildgate.web.Bean;

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
public class GuildVisualBean {

    private Integer idBackground;
    private Integer idAvatar;
    private String nomAvatarGremio;
    private String avatarGremio;
    private String nomFondoGremio;
    private String fondoGremio;
}
