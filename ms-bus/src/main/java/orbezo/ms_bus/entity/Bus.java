package orbezo.ms_bus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "buses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idbus")
    private Long idBus;

    @Column(name = "modbus", nullable = false, length = 50)
    private String modBus;

    @Column(name = "placabus", nullable = false, length = 10, unique = true)
    private String placaBus;

    @Column(name = "capbus", nullable = false)
    private Integer capBus;
}