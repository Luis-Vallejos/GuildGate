package com.guildgate.web.Modelo;

import jakarta.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
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
        name = "UsuarioRoles",
        schema = "gremiosyraids",
        indexes = {
            @Index(name = "idx_usuario_roles_usuario", columnList = "ID_Usuario"),
            @Index(name = "idx_usuario_roles_rol", columnList = "ID_Rol"),
            @Index(name = "idx_usuario_roles_gremio", columnList = "ID_Gremio")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class UsuarioRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_roles_seq")
    @SequenceGenerator(name = "usuario_roles_seq", sequenceName = "usuario_roles_seq", allocationSize = 1)
    @Column(name = "Id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Usuario", nullable = false)
    @NotNull
    private Usuarios usuariouserrol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Rol", nullable = false)
    @NotNull
    private Roles roluserrol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Gremio", nullable = false)
    @NotNull
    private Gremio gremiouserrol;

    @Column(name = "Fecha_Asignacion", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    private LocalDate fechaAsignacion;

    @Version
    @Column(name = "Version")
    private Long version;
}
