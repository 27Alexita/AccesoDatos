package tarea5.hibernate.SegundaMano.entidades;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the categorias database table.
 * 
 */
@Entity
@Table(name="categorias")
@NamedQuery(name="Categoria.findAll", query="SELECT c FROM Categoria c")
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String categoria;

	//bi-directional many-to-many association to Articulo
	@ManyToMany(mappedBy="categorias")
	private List<Articulo> articulos = new ArrayList<>(); // * Inicializo

	// Constructor vacío
	public Categoria() {
	}

	// Constructor con su propiedad nombre (categoría en este caso)
	public Categoria(String categoria) {
		super();
		this.categoria = categoria;
	}
	
	// GETTERS & SETTERS
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<Articulo> getArticulos() {
		return this.articulos;
	}

	public void setArticulos(List<Articulo> articulos) {
		this.articulos = articulos;
	}

}