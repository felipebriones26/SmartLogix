package cl.smartlogix.ms_inventario.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.smartlogix.ms_inventario.model.Producto;
import cl.smartlogix.ms_inventario.repositories.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // 1. Obtener todo el catálogo
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    // 2. Crear un nuevo producto validando el SKU
    public Producto crearProducto(Producto producto) {
        if (productoRepository.findBySku(producto.getSku()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un producto con el SKU: " + producto.getSku());
        }
        return productoRepository.save(producto);
    }

    // 3. Obtener un producto por su ID
    public Producto obtenerPorId(String id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con el ID: " + id));
    }
}