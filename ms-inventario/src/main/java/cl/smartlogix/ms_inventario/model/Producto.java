package cl.smartlogix.ms_inventario.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data // Lombok: Genera Getters, Setters, toString, etc.
@NoArgsConstructor // Lombok: Constructor vacío
@AllArgsConstructor // Lombok: Constructor con todos los parámetros
@Builder // Lombok: Patrón Builder para instanciar fácilmente
@Document(collection = "productos") // Le dice a Spring que esto va en una colección de MongoDB
public class Producto {

    @Id
    private String id; // En MongoDB los IDs son alfanuméricos por defecto, por eso usamos String en vez de Long

    private String nombre;
    private String sku; // Stock Keeping Unit (Código único identificador del producto)
    private String descripcion;
    private Double precio;
    private Integer stock;

    // ¡Aquí está la magia de la flexibilidad de MongoDB!
    // Este Map guardará datos como: {"color": "rojo", "peso": "2kg", "marca": "Intel"}
    private Map<String, Object> atributosDinamicos;
}
