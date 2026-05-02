package cl.smartlogix.ms_inventario.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cl.smartlogix.ms_inventario.model.Producto;

@Repository
public interface ProductoRepository extends MongoRepository<Producto, String> {
    
    // Spring Data MongoDB ya nos regala métodos como save(), findAll(), findById(), deleteById()
    
    // Podemos crear métodos personalizados solo con nombrarlos bien. 
    // Aquí le pedimos que busque un producto exacto por su SKU:
    Optional<Producto> findBySku(String sku);
}