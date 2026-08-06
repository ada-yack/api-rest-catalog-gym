package gym.ada.api.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import gym.ada.api.model.Categoria;
import gym.ada.api.repository.ICategoriaRepository;
import gym.ada.api.service.ICategoriaService;

@Service
public class CategoriaServiceImplement implements ICategoriaService {
	
	private final  ICategoriaRepository categoriaRepository;
	
	 
	 
    public CategoriaServiceImplement(ICategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
	
	
	///estrcutura basica//
	
	public List<Categoria> listarCategoria(){
		return categoriaRepository.findAll();
	}
	
	public Categoria buscarCategoriaPorId(Long id){
		return  categoriaRepository.findById(id).orElse(null);
	}
	
	public Categoria guardarCategoria(Categoria usuario){
		return  categoriaRepository.save(usuario);
	}
	
	public void eliminarCategoria(Long id) {
		categoriaRepository.deleteById(id);
	}

}
