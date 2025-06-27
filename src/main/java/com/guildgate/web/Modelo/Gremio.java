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
        name = "Gremio",
        schema = "gremiosyraids",
        uniqueConstraints = {
            @UniqueConstraint(name = "nombre_unique", columnNames = "Nombre_Gremio")
        },
        indexes = {
            @Index(name = "idx_gremio_nombre", columnList = "Nombre_Gremio"),
            @Index(name = "idx_gremio_region", columnList = "Id_Region"),
            @Index(name = "idx_gremio_mundo", columnList = "Id_Mundo"),
            @Index(name = "idx_gremio_avatar", columnList = "Id_Avatar"),
            @Index(name = "idx_gremio_fondo", columnList = "Id_Fondo")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class Gremio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gremio_seq")
    @SequenceGenerator(name = "gremio_seq", sequenceName = "gremio_seq", allocationSize = 1)
    @Column(name = "Id", updatable = false)
    private Integer id;

    @Column(name = "Nombre_Gremio", length = 100, nullable = false)
    @NotNull
    private String nombre;

    @Column(name = "Descripcion_Gremio", length = 255)
    private String descripcion;

    @Column(name = "CantMaxima_Gremio", nullable = false)
    @NotNull
    private Integer cantidad;

    @OneToMany(mappedBy = "raidgremio", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private Set<Raid> listaRaid;

    @OneToMany(mappedBy = "gremiousuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private Set<Usuarios> listaUsuarios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Region", nullable = false)
    @NotNull
    private Region gremioregion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Mundo", nullable = false)
    @NotNull
    private Mundos gremiomundo;

    @OneToMany(mappedBy = "gremiouserrol", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private Set<UsuarioRoles> listaUsuariosRoles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Avatar", nullable = false)
    @NotNull
    private AvatarGremio img;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Fondo", nullable = false)
    @NotNull
    private FondoGremio imgF;

    @Version
    @Column(name = "Version")
    private Long version;
}
