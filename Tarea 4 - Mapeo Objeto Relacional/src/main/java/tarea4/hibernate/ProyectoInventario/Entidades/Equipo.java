package tarea4.hibernate.ProyectoInventario.Entidades;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="Equipos")
public class Equipo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String numSerie, caracteristicas;
	
	@Temporal(TemporalType.DATE)
	private Date fechaAlta;
	
	@ManyToOne
	@JoinColumn(name = "aula_id", nullable = false, foreignKey = @ForeignKey(name = "Aula_FK"))
	private Aula aula;

	// Constructor
	public Equipo() {

	}
	
	// Constructor con numSerie, fecha y características pasados por parámetro
	public Equipo(String numSerie, Date fechaAlta, String caracteristicas) {
		super();
		this.numSerie = numSerie;
		this.fechaAlta = fechaAlta;
		this.caracteristicas = caracteristicas;
	}
	
	// GETTERS & SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Aula getAula() {
		return aula;
	}

	public void setAula(Aula aula) {
		this.aula = aula;
	}
}
