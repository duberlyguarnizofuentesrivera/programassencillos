package staticqueue;

import staticqueue.Animal;

/**
 * Clase que define a una persona
 * @author Duberly Guarnizo Fuentes Rivera
 *
 */
public class Persona implements Animal {
	private int edad = 0;
	private String nombre = null;

	public Persona(String nombre, int edad) {
		super();
		this.nombre = nombre;
		this.edad = edad;
	}

	@Override
	public int getEdad() {
		return this.edad;
	}

	public String getNombre() {
		return this.nombre;
	}

}
