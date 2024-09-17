package tarea6.mongoDB;

/**
 * La clase contiene un único método, {@code mostrarMenu()}, que imprime un menú
 * con opciones numeradas en la consola para que el usuario pueda seleccionar una.
 */
public class Utilidades {
	
	/**
     * Muestra un menú en la consola con varias opciones numeradas.
     * Las opciones incluyen operaciones que el usuario puede seleccionar.
     */
	public void mostrarMenu() {
		System.out.println("  |  MENÚ PRINCIPAL  |");
		System.out.println("1. Mostrar todos los mensajes");
		System.out.println("2. Mostrar mensajes con más de 3 'me gusta'");
		System.out.println("3. Incrementar número de 'me gusta' de un usuario");
		System.out.println("4. Borrar mensajes de un usuario");
		System.out.println("5. Insertar un nuevo mensaje");
		System.out.println("6. Salir");
		System.out.println(">> Elige una opción: ");
		System.out.println("");
	}
}
