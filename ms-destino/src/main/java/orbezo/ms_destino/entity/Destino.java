package orbezo.ms_destino.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "destinos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Destino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddestino")
    private Long idDestino;

    @Column(name = "ciudest", nullable = false, length = 100)
    private String ciuDest;

    @Column(name = "costdest", nullable = false, precision = 10, scale = 2)
    private BigDecimal costDest;
}