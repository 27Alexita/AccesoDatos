package tarea4.hibernate.ProyectoInventario.CargarDatos;

import java.util.Calendar;

import javax.persistence.*;

import tarea4.hibernate.ProyectoInventario.Entidades.Aula;
import tarea4.hibernate.ProyectoInventario.Entidades.Equipo;

public class InicializacionApp {
	
	public static void precargarDatos(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			// Creación y persistencia de aulas
			Aula aula1 = new Aula ("Aula 101");
			Aula aula2 = new Aula ("Aula 102");
			Aula aula3 = new Aula ("Aula 103");
			em.persist(aula1);
			em.persist(aula2);
			em.persist(aula3);
			
			// Creación de equipos y asignación a las aulas
			Calendar calendar = Calendar.getInstance();
			
			calendar.set(2020, Calendar.JANUARY, 1);
			Equipo equipo1 = new Equipo("XYZ123", calendar.getTime(), "Equipo con 16GB RAM");
			calendar.set(2021, Calendar.FEBRUARY, 15);
			Equipo equipo2 = new Equipo("ABC456", calendar.getTime(), "Equipo con procesador i7");
			calendar.set(2019, Calendar.MARCH, 20);
			Equipo equipo3 = new Equipo("LMN789", calendar.getTime(), "Equipo con tarjeta gráfica Envidia GTX3090");
			
			aula1.addEquipo(equipo1);
			aula2.addEquipo(equipo2);
			aula3.addEquipo(equipo3);
			
			em.getTransaction().commit();
			System.out.println("\n >> Datos precargados en la base de datos");
		} catch (Exception ex) {
			if(em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			ex.printStackTrace();
		} finally {
			em.close();
		}
	}
}