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
        name = "Rol",
        schema = "gremiosyraids",
        uniqueConstraints = {
            @UniqueConstraint(name = "nombreRol_unique", columnNames = "Nombre_Rol")
        },
        indexes = {
            @Index(name = "idx_roles_nombre", columnList = "Nombre_Rol")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
    @SequenceGenerator(name = "roles_seq", sequenceName = "roles_seq", allocationSize = 1)
    @Column(name = "Id", updatable = false)
    private Integer id;

    @Column(name = "Nombre_Rol", length = 100, nullable = false)
    @NotNull
    private String nombre;

    @Column(name = "Rol_Descripcion", length = 255)
    private String descripcion;

    @Column(name = "Permiso_Cambiar_Avatar_Gremio", nullable = false)
    private boolean permisoCambiarAvatarGremio;

    @Column(name = "Permiso_Cambiar_Fondo_Gremio", nullable = false)
    private boolean permisoCambiarFondoGremio;

    @Column(name = "Permiso_Cambiar_Nombre_Gremio", nullable = false)
    private boolean permisoCambiarNombreGremio;

    @Column(name = "Permiso_Cambiar_Descripcion_Gremio", nullable = false)
    private boolean permisoCambiarDescripcionGremio;

    @Column(name = "Permiso_Crear_Raids", nullable = false)
    private boolean permisoCrearRaids;

    @Column(name = "Permiso_Editar_Raids", nullable = false)
    private boolean permisoEditarRaids;

    @Column(name = "Permiso_Visualizar_Raids", nullable = false)
    private boolean permisoVisualizarRaids;

    @Column(name = "Permiso_Crear_Roles", nullable = false)
    private boolean permisoCrearRoles;

    @Column(name = "Permiso_Editar_Roles", nullable = false)
    private boolean permisoEditarRoles;

    @Column(name = "Permiso_Visualizar_Roles", nullable = false)
    private boolean permisoVisualizarRoles;

    @Column(name = "Permiso_Botar_Miembros", nullable = false)
    private boolean permisoBotarMiembros;

    @Column(name = "Permiso_Salir_Gremio", nullable = false)
    private boolean permisoSalirGremio;

    @Column(name = "Permiso_Eliminar_Gremio", nullable = false)
    private boolean permisoEliminarGremio;

    @OneToMany(mappedBy = "roluserrol", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private Set<UsuarioRoles> listaUsuariosRoles;

    @Version
    @Column(name = "Version")
    private Long version;
}
