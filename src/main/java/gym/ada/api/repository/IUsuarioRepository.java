package gym.ada.api.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gym.ada.api.model.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository <Usuario, Long> {
	
	//Jpa Lo hace por ti 
	// Buscar usuario Por emai 
	Optional<Usuario> findByEmail(String email);
	
	//buscar usuario activos
	 List<Usuario> findByActivoTrue();
	 
	 ////////////////////////////////////
	 ///                               //
	 ///        INCREIBLE             ///
	 ///                              ///
	 ///////////////////////////////////
    
	
}
