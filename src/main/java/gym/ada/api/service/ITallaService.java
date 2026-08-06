package gym.ada.api.service;

import java.util.List;

import gym.ada.api.model.Talla;

public interface ITallaService {

    public List<Talla> listarTallas();

    public Talla buscarTallasPorId(Long id);

    public Talla guardarTallas(Talla talla);

    public void eliminarTallas(Long id);

}