package gym.ada.api.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import gym.ada.api.model.Usuario;
import gym.ada.api.repository.IUsuarioRepository;



@Service

public class UsuarioServiceImplement {

	private final IUsuarioRepository usuarioRepository;
	
 
	 
    public UsuarioServiceImplement(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
	
	
	///estrcutura basica//
	
	public List<Usuario> listarTodos(){
		return usuarioRepository.findAll();
	}
	
	public Usuario buscarPorId(Long id){
		return  usuarioRepository.findById(id).orElse(null);
	}
	
	public Usuario guardar(Usuario usuario){
		return  usuarioRepository.save(usuario);
	}
	
	public void eliminar(Long id) {
		usuarioRepository.deleteById(id);
	}
	
	  public Usuario actualizarAvatar(Long id, String avatarUrl) {
	        Usuario usuario = usuarioRepository.findById(id).orElse(null);
	        if (usuario == null) return null;
	        usuario.setAvatarUrl(avatarUrl);
	        return usuarioRepository.save(usuario);
	  }
}
