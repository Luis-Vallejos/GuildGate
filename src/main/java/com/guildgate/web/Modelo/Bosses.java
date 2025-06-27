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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
        name = "Bosses",
        schema = "gremiosyraids",
        uniqueConstraints = {
            @UniqueConstraint(name = "nombreBoss_unique", columnNames = "Nombre_Boss")
        },
        indexes = {
            @Index(name = "idx_bosses_nombre", columnList = "Nombre_Boss"),
            @Index(name = "idx_bosses_raid", columnList = "Id_Raid")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class Bosses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bosses_seq")
    @SequenceGenerator(name = "bosses_seq", sequenceName = "bosses_seq", allocationSize = 1)
    @Column(name = "Id", updatable = false)
    private Integer id;

    @Column(name = "Nombre_Boss", length = 200)
    @NotNull
    private String nombre;

    @OneToMany(mappedBy = "boss", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Participaciones> listaParticipaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Raid")
    private Raid raidboss;

    @Version
    @Column(name = "Version")
    private Long version;
}
