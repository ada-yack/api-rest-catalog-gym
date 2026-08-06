package gym.ada.api.service.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import gym.ada.api.dto.ImagenDto;
import gym.ada.api.dto.ProductData;
import gym.ada.api.dto.TallaDto;
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

	@Override
	public List<ProductData> obtenerTodosLosProductos() {
		List<Producto> productos = productoRepository.findAllConDetalles();

        return productos.stream().map(producto -> {
            ProductData dto = new ProductData();
            dto.setId(producto.getId());
            dto.setTitulo(producto.getTitulo());
            dto.setDescripcion(producto.getDescripcion());
            dto.setAdicional(producto.getAdicional());
            dto.setPrecioUnidad(producto.getPrecioUnidad());
            dto.setPrecioTotal(producto.getPrecioTotal());
            
            // Asignar categoría (si existe)
            if (producto.getCategoria() != null) {
                dto.setCategoria(producto.getCategoria().getNombre());
            }

            // Mapear la lista de Imagenes a ImagenDto
            List<ImagenDto> imagenesDto = producto.getImagenes().stream().map(img -> {
                ImagenDto imgDto = new ImagenDto();
                imgDto.setId(img.getId());
                imgDto.setUrl(img.getUrl());
                imgDto.setEsPrincipal(img.isEsPrincipal());
                return imgDto;
            }).collect(Collectors.toList());
            dto.setImagenes(imagenesDto);

            // Mapear la lista de Tallas a TallaDto
            List<TallaDto> tallasDto = producto.getProductoTallas().stream().map(pt -> {
                TallaDto tDto = new TallaDto();
                tDto.setId(pt.getTalla().getId());        // ID de la talla
                tDto.setNombre(pt.getTalla().getNombre());// Nombre ("M", "L")
                tDto.setStock(pt.getStock());             // Stock de la tabla intermedia
                return tDto;
            }).collect(Collectors.toList());

            dto.setTallas(tallasDto);

            return dto;
        }).collect(Collectors.toList());
    }
}
