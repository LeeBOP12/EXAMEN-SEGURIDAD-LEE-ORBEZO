package orbezo.ms_programacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "programaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Programacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprogramacion")
    private Long idProgramacion;

    @Column(name = "idbus", nullable = false)
    private Long idBus;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;
}