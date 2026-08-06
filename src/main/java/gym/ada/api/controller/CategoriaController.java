package gym.ada.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import gym.ada.api.model.Categoria;
import gym.ada.api.service.ICategoriaService;




@RestController // responde con JSON automaticamente
@RequestMapping("/api/categorias")
public class CategoriaController {
	
	private ICategoriaService categoriaService;

    public CategoriaController(ICategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }
    
    
	 // GET http://localhost:8080/api/categorias
    @GetMapping("/listCategorias")
    public List<Categoria> listarTodos() {
        return categoriaService.listarCategoria();
    }
    
    
    @PostMapping("/saveCategoria")
    public Categoria guardar(@RequestBody Categoria categoria) {
        return categoriaService.guardarCategoria(categoria);
    }
    
 // GET http://localhost:8080/api/categorias/1
    @GetMapping("/searchCategoria/{id}")
    public Categoria buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarCategoriaPorId(id);
    }
    
   // DELETE http://localhost:8080/api/categorias/1
    @DeleteMapping("/deleteCategoria/{id}")
    public void eliminar(@PathVariable Long id) {
    	categoriaService.eliminarCategoria(id);
    }
    
   

}
