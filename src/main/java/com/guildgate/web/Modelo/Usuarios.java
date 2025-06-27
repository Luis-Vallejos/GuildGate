package com.guildgate.web.Modelo;

import jakarta.persistence.CascadeType;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Juan - Luis
 */
@Entity
@Table(
        name = "Usuarios",
        schema = "gremiosyraids",
        uniqueConstraints = {
            @UniqueConstraint(name = "nombreUsuario_unique", columnNames = "Nombre_Usuario")
        },
        indexes = {
            @Index(name = "idx_usuarios_nombre", columnList = "Nombre_Usuario"),
            @Index(name = "idx_usuarios_gremio", columnList = "Id_Gremio"),
            @Index(name = "idx_usuarios_perfil", columnList = "Id_Perfil"),
            @Index(name = "idx_usuarios_banner", columnList = "Id_Banner")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_seq")
    @SequenceGenerator(name = "usuarios_seq", sequenceName = "usuarios_seq", allocationSize = 1)
    @Column(name = "Id", updatable = false)
    private Integer id;

    @Column(name = "Nombre_Usuario", length = 100, nullable = false)
    @NotNull
    private String nombre;

    @Column(name = "Correo_Electronico", length = 255, nullable = false)
    @NotNull
    @Email
    private String correo;

    @Column(name = "Contrasenia", length = 255, nullable = false)
    @NotNull
    private String contrasena;

    @Column(name = "Bio_Usuario", length = 500)
    private String bio;

    @Column(name = "Nivel", nullable = false)
    @NotNull
    private Integer nivel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Gremio")
    private Gremio gremiousuario;

    @OneToMany(mappedBy = "userparti", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private Set<Participaciones> listaParticipaciones;

    @OneToMany(mappedBy = "usuariouserrol", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private Set<UsuarioRoles> listaUsuariosRol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Perfil")
    private ImagenPerfil img;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Banner")
    private ImagenBanner imgB;

    @Version
    @Column(name = "Version")
    private Long version;
}
