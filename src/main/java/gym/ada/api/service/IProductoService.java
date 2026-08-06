package gym.ada.api.service;

import java.util.List;

import gym.ada.api.dto.ProductData;
import gym.ada.api.model.Producto;

public interface IProductoService {

	public List<Producto> listarProductos();
    
	public List<Producto> listarProductosActivos();

	public Producto buscarProductosPorId(Long id);

	public Producto guardarProductos(Producto producto);

	public Producto actualizarProductos(Long id, Producto producto);

	public void eliminarProductos(Long id);
	
	//Proveniente de dto
	public List<ProductData> obtenerTodosLosProductos();

}