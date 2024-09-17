package tarea5.hibernate.SegundaMano.entidades;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@Entity
@Table(name="usuarios")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String cookie;

	private String email;

	private String foto;

	private String nombre;

	private String password;

	//bi-directional many-to-one association to Articulo
	@OneToMany(mappedBy="usuario")
	private List<Articulo> articulos = new ArrayList<>(); // * Inicializo

	//bi-directional many-to-one association to Compra
	@OneToMany(mappedBy="usuario")
	private List<Compra> compras = new ArrayList<>(); // * Inicializo

	// Constructor vacío
	public Usuario() {
	}

	// Constructor con el atributo nombre *
	public Usuario(String nombre) {
		super();
		this.nombre = nombre;
	}

	// GETTERS & SETTERS
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCookie() {
		return this.cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Articulo> getArticulos() {
		return this.articulos;
	}

	public void setArticulos(List<Articulo> articulos) {
		this.articulos = articulos;
	}

	public List<Compra> getCompras() {
		return this.compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}
	
	// Métodos HELPER
	public Articulo addArticulo(Articulo articulo) {
		getArticulos().add(articulo);
		articulo.setUsuario(this);

		return articulo;
	}

	public Articulo removeArticulo(Articulo articulo) {
		getArticulos().remove(articulo);
		articulo.setUsuario(null);

		return articulo;
	}

	public Compra addCompra(Compra compra) {
		getCompras().add(compra);
		compra.setUsuario(this);

		return compra;
	}

	public Compra removeCompra(Compra compra) {
		getCompras().remove(compra);
		compra.setUsuario(null);

		return compra;
	}

}