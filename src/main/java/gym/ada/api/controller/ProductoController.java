package gym.ada.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import gym.ada.api.model.Producto;
import gym.ada.api.service.IProductoService;



@RestController // responde con JSON automaticamente
@RequestMapping("/api/productos")
public class ProductoController {
	
private final IProductoService productoService;
	
	public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }
	
	//LIST
	
    @GetMapping("/listProductos")
    public List< Producto> listarTodos() {
        return productoService.listarProductos();
    }
    
    @GetMapping("/listProductActivos")
    public List<Producto> listarActivos() {
        return productoService.listarProductosActivos();
    }
    
    //SEARCH
    @GetMapping("/searchProducto/{id}")
    public Producto buscarPorId(@PathVariable Long id) {
        return productoService.buscarProductosPorId(id);
    }
    
    //SAVE
    @PostMapping("/saveProducto")
    public Producto guardar(@RequestBody Producto producto) {
        return productoService.guardarProductos(producto);
    }
    
   //DELETE
    @DeleteMapping("/deleteProducto/{id}")
    public void eliminar(@PathVariable Long id) {
    	productoService.eliminarProductos(id);
    }
    
    //PUT
    @PutMapping("/actualizaProducto/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.actualizarProductos(id, producto);
    }
    
   

}
