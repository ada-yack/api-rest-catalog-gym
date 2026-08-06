package gym.ada.api.service.implement;

import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import gym.ada.api.model.Producto;
import gym.ada.api.repository.IProductoRepository;
import gym.ada.api.service.IProductoService;


@Service
public class ProductoServiceImplement implements IProductoService {

   private final IProductoRepository productoRepository;
	
	 
	
    public ProductoServiceImplement(IProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
	
	///estrcutura basica//
	
	public List<Producto> listarProductos(){
		return productoRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}
	
	public Producto buscarProductosPorId(Long id){
		return productoRepository.findById(id).orElse(null);
	}
	
	public Producto guardarProductos(Producto producto){
		return  productoRepository.save(producto);
	}
	
	public void eliminarProductos(Long id) {
		Producto producto = productoRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

	    producto.setActivo(false);

	    productoRepository.save(producto);
	}
	
	public Producto actualizarProductos(Long id, Producto producto) {

	    Producto productoBD = productoRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

	    productoBD.setTitulo(producto.getTitulo());
	    productoBD.setDescripcion(producto.getDescripcion());
	    productoBD.setAdicional(producto.getAdicional());
	    productoBD.setPrecioUnidad(producto.getPrecioUnidad());
	    productoBD.setPrecioTotal(producto.getPrecioTotal());
	    productoBD.setActivo(producto.isActivo());
	    productoBD.setCategoria(producto.getCategoria());

	    return productoRepository.save(productoBD);
	}

	@Override
	public List<Producto> listarProductosActivos() {
	    return productoRepository.findByActivoTrue();
	}
}
