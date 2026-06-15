package orbezo.ms_reserva.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreserva")
    private Long idReserva;

    @Column(name = "fechareser", nullable = false)
    private LocalDate fechaReser;

    @Column(name = "horareser", nullable = false)
    private LocalTime horaReser;

    @Column(name = "idcliente", nullable = false)
    private Long idCliente;

    @Column(name = "idprogramacion", nullable = false)
    private Long idProgramacion;

    @Column(name = "iddestino", nullable = false)
    private Long idDestino;
}
