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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
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
        name = "Participaciones",
        schema = "gremiosyraids",
        indexes = {
            @Index(name = "idx_participaciones_usuario", columnList = "Id_Usuario"),
            @Index(name = "idx_participaciones_boss", columnList = "Id_Boss"),
            @Index(name = "idx_participaciones_raid", columnList = "Id_Participacion"),
            @Index(name = "idx_participaciones_ronda", columnList = "Id_Ronda")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class Participaciones implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participaciones_seq")
    @SequenceGenerator(name = "participaciones_seq", sequenceName = "participaciones_seq", allocationSize = 1)
    @Column(name = "Id", updatable = false)
    private Integer id;

    @Column(name = "Dano", nullable = false)
    @NotNull
    private Long danio;

    @Column(name = "Fecha_Participacion", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Usuario", nullable = false)
    @NotNull
    private Usuarios userparti;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Boss", nullable = false)
    @NotNull
    private Bosses boss;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Participacion", nullable = false)
    @NotNull
    private Raid raidparti;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Ronda", nullable = false)
    @NotNull
    private Ronda partironda;

    @OneToMany(mappedBy = "parti", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private Set<ParticipacionesExtra> listaPartiExtra;

    @Version
    @Column(name = "Version")
    private Long version;
}
