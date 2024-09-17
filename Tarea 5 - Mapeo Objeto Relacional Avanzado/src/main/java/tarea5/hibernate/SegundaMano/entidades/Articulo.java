package tarea5.hibernate.SegundaMano.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the articulos database table.
 * 
 */
@Entity
@Table(name = "articulos")
@NamedQuery(name = "Articulo.findAll", query = "SELECT a FROM Articulo a")
@NamedQuery(name = "Articulo.findByPalabra", query = "SELECT a FROM Articulo a WHERE a.titulo LIKE :palabra OR a.descripcion LIKE :palabra") // NamedQuery																																																																				// palabra
public class Articulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Lob
	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	private BigDecimal precio;

	private String titulo;

	private byte vendido;

	// bi-directional many-to-one association to Usuario
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }) // *
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	// bi-directional many-to-many association to Categoria
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }) // *
	@JoinTable(name = "articulos_categorias", joinColumns = {
			@JoinColumn(name = "id_articulo") }, inverseJoinColumns = { @JoinColumn(name = "id_categoria") })
	private List<Categoria> categorias = new ArrayList<>(); // * Inicializo

	// bi-directional many-to-one association to Compra
	@OneToMany(mappedBy = "articulo")
	private List<Compra> compras;

	// Constructor vacío
	public Articulo() {
	}

	// Constructor con sus propiedades
	public Articulo(String descripcion, Date fecha, BigDecimal precio, String titulo, byte vendido) {
		super();
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.precio = precio;
		this.titulo = titulo;
		this.vendido = vendido;
	}

	// Constructor con propiedades y entidades
	public Articulo(String descripcion, Date fecha, BigDecimal precio, String titulo, byte vendido,
			Usuario usuario, List<Categoria> categorias, List<Compra> compras) {
		super();
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.precio = precio;
		this.titulo = titulo;
		this.vendido = vendido;
		this.usuario = usuario;
		this.categorias = categorias;
		this.compras = compras;
	}

	// GETTERS & SETTERS
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public byte getVendido() {
		return this.vendido;
	}

	public void setVendido(byte vendido) {
		this.vendido = vendido;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Categoria> getCategorias() {
		return this.categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Compra> getCompras() {
		return this.compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	// Métodos HELPERS
	public Compra addCompra(Compra compra) {
		getCompras().add(compra);
		compra.setArticulo(this);

		return compra;
	}

	public Compra removeCompra(Compra compra) {
		getCompras().remove(compra);
		compra.setArticulo(null);

		return compra;
	}

	// Categoria *
	public Categoria addCategoria(Categoria categoria) {
		categorias.add(categoria);
		categoria.getArticulos().add(this);

		return categoria;
	}

	public Categoria removeCategoria(Categoria categoria) {
		categorias.remove(categoria);
		categoria.getArticulos().remove(this);

		return categoria;
	}

	// Usuario *
	public void addUsuario(Usuario usuario) {
		this.usuario = usuario;
		usuario.getArticulos().add(this);
	}
	
	// Método toString() para mostrar la información
	@Override
	public String toString() {
		String estaVendido = this.vendido == 0 ? "Sí" : "No";
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	    String fechaFormateada = formatoFecha.format(fecha); // Aquí formateamos la fecha
		return ">> Articulo "
				+ "\nID: " + id 
				+ "\nNombre: " + titulo
				+ "\nDescripción: " + descripcion 
				+ "\nFecha: " + fechaFormateada 
				+ "\nPrecio: " + precio
				+ "\nEstado vendido (sí/no): " + estaVendido;
	}
}