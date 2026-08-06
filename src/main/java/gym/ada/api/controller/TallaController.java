package gym.ada.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import gym.ada.api.model.Talla;
import gym.ada.api.service.ITallaService;




@RestController // responde con JSON automaticamente
@RequestMapping("/api/tallas")
public class TallaController {

	
    private final ITallaService TallaService;
	
	public TallaController(ITallaService TallaService) {
        this.TallaService = TallaService;
    }
	
	 
    @GetMapping("/listTallas")
    public List<Talla> listarTodos() {
        return TallaService.listarTallas();
    }
    
 
    @GetMapping("/searchTalla/{id}")
    public Talla buscarPorId(@PathVariable Long id) {
        return TallaService.buscarTallasPorId(id);
    }
    
    @PostMapping("/saveTalla")
    public Talla guardar(@RequestBody Talla talla) {
        return TallaService.guardarTallas(talla);
    }
    
  
    @DeleteMapping("/deleteTalla/{id}")
    public void eliminar(@PathVariable Long id) {
    	TallaService.eliminarTallas(id);
    }
}
