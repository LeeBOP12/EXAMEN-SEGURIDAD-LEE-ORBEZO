package orbezo.ms_asiento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "asientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idasiento")
    private Long idAsiento;

    @Column(name = "idbus", nullable = false)
    private Long idBus;

    @Column(name = "numasi", nullable = false, length = 5)
    private String numAsi;

    @Column(name = "estasi", nullable = false, length = 1)
    private String estAsi; // D=Disponible, O=Ocupado, R=Reservado
}
