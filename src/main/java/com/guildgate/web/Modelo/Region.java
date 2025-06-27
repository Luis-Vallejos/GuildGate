package com.guildgate.web.Modelo;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
        name = "Region",
        schema = "gremiosyraids",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "nombreRegion_unique",
                    columnNames = "Nombre_Region"
            )
        },
        indexes = {
            @Index(name = "idx_region_nombre", columnList = "Nombre_Region")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class Region implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "Nombre_Region", nullable = false)
    @NotNull
    private String nombre;

    @OneToMany(mappedBy = "gremioregion")
    private List<Gremio> listaGremios;
}
