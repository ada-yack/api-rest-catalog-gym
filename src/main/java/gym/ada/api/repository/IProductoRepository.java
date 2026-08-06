package gym.ada.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gym.ada.api.model.Producto;

@Repository
public interface IProductoRepository extends  JpaRepository <Producto, Long>{

	Optional<Producto> findByTitulo(String titulo);
	
	List<Producto> findByActivoTrue();
	
	List<Producto> findByCategoriaId(Long categoriaId);
}
