package gym.ada.api.model;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
 
@Entity  //Para que Spring sepa que esto es un tabal :)
@Table (name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 100, nullable = false)
	@Size(max = 100)
	@NotBlank
	private String nombre;
	
	@Column(length = 150, unique = true, nullable = false)
	@Size(max = 150)
	@NotBlank
	@Email
	
	private String email;
	
	@Column(name = "password_hash", length = 255, nullable = false)
	@Size(max = 225)
	@NotBlank
	
	private String passwordHash;
	

	private boolean activo = true;
	
	@Column(length = 20)
	private String rol = "cliente";
	
	@Column(name = "avatar_url", length = 500)
    private String avatarUrl;
	
	
	public String getRol() { return rol; }
	public void setRol(String rol) { this.rol = rol; }
	
	public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}
