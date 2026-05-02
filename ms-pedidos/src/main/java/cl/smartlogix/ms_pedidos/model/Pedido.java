package cl.smartlogix.ms_pedidos.model;

import java.time.LocalDateTime;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data // Lombok para getters y setters
@Entity
@Table(name = "pedidos")
// ¡Esta es la magia! Guarda todos los tipos de pedidos en la misma tabla:
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// Y usa esta columna para diferenciar si es NACIONAL, REGIONAL o EXPRESS:
@DiscriminatorColumn(name = "tipo_pedido", discriminatorType = DiscriminatorType.STRING)
public abstract class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;      // Quién lo compra (viene de ms-usuarios)
    private String productoSku;  // Qué compra (viene de ms-inventario)
    private Integer cantidad;
    private Double costoEnvio;   // Este valor cambiará según el tipo de pedido

    // Atributo clave para el Patrón Saga más adelante
    private String estadoSaga;   // Ej: CREANDO, PENDIENTE_STOCK, CONFIRMADO, CANCELADO

    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.estadoSaga = "CREANDO"; // Estado inicial del Saga
    }
    
    // Método abstracto que cada hijo deberá implementar a su manera
    public abstract void calcularCostoEnvio();
}