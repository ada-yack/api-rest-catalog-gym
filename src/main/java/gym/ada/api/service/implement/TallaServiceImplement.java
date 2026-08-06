package gym.ada.api.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import gym.ada.api.model.Talla;
import gym.ada.api.repository.ITallaRepository;
import gym.ada.api.service.ITallaService;


@Service
public class TallaServiceImplement  implements ITallaService{

	private  final ITallaRepository tallaRepository;
		
    public TallaServiceImplement(ITallaRepository tallaRepository) {
        this.tallaRepository = tallaRepository;
    }
	
	///estrcutura basica//
	
	public List<Talla> listarTallas(){
		return tallaRepository.findAll();
	}
	
	public Talla buscarTallasPorId(Long id){
		return  tallaRepository.findById(id).orElse(null);
	}
	
	public Talla guardarTallas(Talla talla){
		return  tallaRepository.save(talla);
	}
	
	public void eliminarTallas(Long id) {
		tallaRepository.deleteById(id);
	}
	
}
