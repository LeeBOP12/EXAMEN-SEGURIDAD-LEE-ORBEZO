package orbezo.ms_cliente.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcliente")
    private Long idCliente;

    @Column(name = "nomcli", nullable = false, length = 50)
    private String nomCli;

    @Column(name = "apecli", nullable = false, length = 50)
    private String apeCli;

    @Column(name = "edadcli")
    private Integer edadCli;

    @Column(name = "sexocli", length = 1)
    private String sexoCli;
}