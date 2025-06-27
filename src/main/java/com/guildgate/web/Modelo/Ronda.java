package com.guildgate.web.Modelo;

import jakarta.persistence.CascadeType;
import java.io.Serializable;
import java.util.List;
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
        name = "Ronda",
        schema = "gremiosyraids",
        indexes = {
            @Index(name = "idx_ronda_raid", columnList = "Id_Raid")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class Ronda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ronda_seq")
    @SequenceGenerator(name = "ronda_seq", sequenceName = "ronda_seq", allocationSize = 1)
    @Column(name = "Id", updatable = false)
    private Integer id;

    @Column(name = "Nombre_Ronda", length = 100, nullable = false)
    @NotNull
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Raid", nullable = false)
    @NotNull
    private Raid raidronda;

    @OneToMany(mappedBy = "partironda", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private Set<Participaciones> listaParticipaciones;

    @OneToMany(mappedBy = "partiextraronda", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private Set<ParticipacionesExtra> listaParticipacionesExtra;

    @Version
    @Column(name = "Version")
    private Long version;
}
