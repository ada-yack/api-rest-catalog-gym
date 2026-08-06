package gym.ada.api.service;


import java.util.List;

import gym.ada.api.model.Categoria;
public interface ICategoriaService {
	
	public List<Categoria> listarCategoria();

	public Categoria buscarCategoriaPorId(Long id);

	public Categoria guardarCategoria(Categoria categoria);

	public void eliminarCategoria(Long id);

}
