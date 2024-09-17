package tarea4.hibernate.ProyectoInventario.Entidades;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name="Aula")
public class Aula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nombre;
	
	@OneToMany(mappedBy = "aula", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Equipo> equipos = new HashSet<>();

	// Constructor
	public Aula() {

	}
	
	// Constructor al que se le pasa por parámetro el nombre
	public Aula(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	// Método para añadir los equipos a las aulas
	public void addEquipo(Equipo equipo) {
		equipos.add(equipo);
		equipo.setAula(this);
	}

	// GETTERS & SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Equipo> getEquipos() {
		return equipos;
	}

	public void setEquipos(Set<Equipo> equipos) {
		this.equipos = equipos;
	}	
}
