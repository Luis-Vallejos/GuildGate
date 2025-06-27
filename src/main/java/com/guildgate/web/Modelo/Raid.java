package com.guildgate.web.Modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
        name = "Raids",
        schema = "gremiosyraids",
        indexes = {
            @Index(name = "idx_raid_nombre", columnList = "Nombre_Raid")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class Raid implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "Nombre_Raid", length = 100, nullable = false)
    @NotNull
    private String nombre;

    @Column(name = "Fecha_Inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date fechaInicio;

    @Column(name = "Fecha_Finalizacion")
    @Temporal(TemporalType.DATE)
    private Date fechaFinalizacion;

    @ManyToOne
    @JoinColumn(name = "Id_Gremio")
    private Gremio raidgremio;

    @OneToMany(mappedBy = "raidparti")
    private List<Participaciones> listaParticipaciones;

    @OneToMany(mappedBy = "raidronda")
    private List<Ronda> listaRondas;

    @OneToMany(mappedBy = "raidboss")
    private List<Bosses> listaBosses;
}
