package com.guildgate.web.Modelo;

import java.io.Serializable;
import java.util.List;
import com.guildgate.web.Utilities.Enum;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
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
        name = "Fondo_Gremio",
        schema = "gremiosyraids",
        indexes = {
            @Index(name = "idx_fondo_gremio_nombre", columnList = "Nombre_Archivo")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
public class FondoGremio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fondo_gremio_seq")
    @SequenceGenerator(name = "fondo_gremio_seq", sequenceName = "fondo_gremio_seq", allocationSize = 1)
    @Column(name = "Id", updatable = false)
    private Long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "Data_Archivo", nullable = false)
    @NotNull
    private byte[] data;

    @Column(name = "Nombre_Archivo", length = 255, nullable = false)
    @NotNull
    private String nomArchivo;

    @Column(name = "Tipo_Archivo", length = 50, nullable = false)
    @NotNull
    private String tipoArchivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "Origen_Archivo", length = 20, nullable = false)
    @NotNull
    private Enum.OrigenArchivo origenArchivo;

    @OneToMany(mappedBy = "imgF", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fondo_gremio_id")
    private List<Gremio> listaGremios;

    @Version
    @Column(name = "Version")
    private Long version;
}
