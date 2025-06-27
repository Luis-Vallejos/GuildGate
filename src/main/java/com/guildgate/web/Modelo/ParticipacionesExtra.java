package com.guildgate.web.Modelo;

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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
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
        name = "Participaciones_Extra",
        schema = "gremiosyraids",
        indexes = {
            @Index(name = "idx_participaciones_extra_participacion", columnList = "Id_Participacion"),
            @Index(name = "idx_participaciones_extra_ronda", columnList = "Id_Ronda")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class ParticipacionesExtra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participaciones_extra_seq")
    @SequenceGenerator(name = "participaciones_extra_seq", sequenceName = "participaciones_extra_seq", allocationSize = 1)
    @Column(name = "Id", updatable = false)
    private Integer id;

    @Column(name = "Dano_Extra", nullable = false)
    @NotNull
    private Long danoExtra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Participacion", nullable = false)
    @NotNull
    private Participaciones parti;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Ronda", nullable = false)
    @NotNull
    private Ronda partiextraronda;

    @Version
    @Column(name = "Version")
    private Long version;
}
