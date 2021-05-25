package staticqueue;

import java.lang.reflect.Array;

import staticqueue.Animal;
import staticqueue.CustomStaticQueueExeption;

/**
 * Clase que crea una cola estática (tamaño fijo), de sistema First Input -
 * First Output Contiene métodos para agregar, consultar, filtrar y remover
 * elementos de la cola.
 * 
 * @author Duberly Guarnizo Fuentes Rivera
 *
 * @param <T>: Tipo de dato genérico, que implementa la interfaz <b>Animal</b>
 */
public class CustomStaticQueue<T extends Animal> {
	// creamos una variable del tipo genérico e indicamos que contendrá un array
	private T[] elementos;
	private int lenght = 0;
	private int elementosEnCola = 0;

	/**
	 * Java no permite asignar arrays con elementos genéricos, es decir elementos =
	 * T[tamaño], ya que no compila (la razón de esto es bien interesante pero
	 * también larga. Para empezar, al constructor no solo le pasamos el tamaño del
	 * array (size) sino también la "clase" del tipo de datos que usará. Todos los
	 * objeto tienen el atributo "class", que guarda una referencia a la clase, o
	 * molde que lo creó.
	 * 
	 * @param cls:  la clase del tipo de objeto que usaremos. Ejemplo:
	 *              <b>String.class</b>
	 * @param size: el tamaño de la cola, numero entero positivo
	 */
	@SuppressWarnings("unchecked")
	public CustomStaticQueue(Class<T> cls, int size) {
		// Entonces, una vez teniendo la clase del objeto, y el tamaño, usamos el método
		// newInstance (nueva instancia) que crea una nueva instancia de una clase. En
		// este caso, crea una nueva instancia de la clase Array (que es la clase que
		// corresponde a la estructura primitiva "[]"... este método requiere la "clase"
		// del objeto a crear y el número de elementos que definirá el tamaño del array.
		// Creado este array, le hacemos "cast" (lo convertimos a otro tipo de objeto)
		// hacia un array del tipo genérico T ("T[]) mediante el uso de paréntesis.
		// Es posible que esto genere una advertencia, ya que el IDE no puede
		// garantizar la conversión, pero si funciona, así que no pasa nada.
		elementos = (T[]) Array.newInstance(cls, size);
		this.lenght = size;
	}

	/**
	 * Devuelve la dimensión del array interno, es decir, el numero de elementos que
	 * contiene la cola. Se determina en el constructor.
	 * 
	 * @return tamaño de la cola
	 */
	public int length() {
		return this.lenght;
	}

	/**
	 * Devuelve la cantidad de elementos creados dentro de la cola. La diferencia
	 * con <b>length</b> es que este ultimo indica el tamaño la cola, mientras que
	 * <b>conteoElementos</b> devuelve cuantos espacios dentro de la cola están
	 * ocupados.
	 * 
	 * @return
	 */
	public int conteoElementos() {
		return this.elementosEnCola;
	}

	/**
	 * 
	 * @param index: índice (iniciando de cero) del elemento a consultar. Solo
	 *               devuelve el dato más no lo elimina ni hace cambios en la cola
	 * @return Objeto genérico ubicado en el índice indicado.
	 */
	public T consultar(int index) {
		return this.elementos[index];
	}

	/**
	 * Agrega un elemento (Que implemente la interfaz <b>Animal</b>) a la cola, al
	 * final de la misma
	 * 
	 * @param element: el objeto genérico que implementa <b>Animal</b> a ser
	 *                 agregado
	 * @throws CustomStaticQueueExeption: Cuando no existe espacio en la cola.
	 */
	public void agregar(T element) throws CustomStaticQueueExeption {
		// Agregamos nuevos elementos. El nuevo elemento siempre se agrega al final
		if (elementosEnCola == lenght) {
			// la cola está llena
			throw new CustomStaticQueueExeption("Sin espacio en la cola: hay " + this.elementosEnCola + " de "
					+ this.lenght + " espacios utilizados");
		}
		else {
			if (elementosEnCola == 0) {
				// no existen aún elementos en la cola
				elementos[0] = element;
			}
			else {
				// existe al menos un elemento en la cola
				elementos[elementosEnCola] = element;
			}
			elementosEnCola++;
		}

	}

	/**
	 * Devuelve el primer elemento en la cola, quitándolo permanentemente de la
	 * misma. Equivale al método <b>pop()</b> de las listas.
	 * 
	 * @return Objeto genérico ubicado al inicio de la cola.
	 */
	public T sacar() {
		T resultado = null;
		if (elementosEnCola == 0) {
			// no hay elementos
			return resultado;
		}
		else {
			// hay elementos
			resultado = elementos[0];
			T[] auxiliar = elementos;
			for (int i = 0; i < elementosEnCola - 1; i++) {
				elementos[i] = auxiliar[i + 1];
			}
			elementosEnCola--;
			return resultado;
		}
	}

	/**
	 * Muestra en la consola el contenido de la cola. Para propósitos de debug,
	 * únicamente.
	 */
	public String listarElementos() {
		String resultado = "Listado de personas en la cola:\n";
		for (T p : elementos) {
			resultado = resultado + "- " + p.getNombre() + " -> " + p.getEdad() + "\n";
		}
		return resultado;
	}

	/**
	 * Método exclusivo para los objetos que implementan la interfaz <b>Animal</b>
	 * Genera y retorna un <b>int array</b> que contiene los indices de los objetos
	 * cuyo método <b>getEdad()</b> cumple con la regla de ser menores que el
	 * parámetro.
	 * 
	 * @param edad: edad utilizada para filtrar la cola
	 * @return int[], lista con los indices de los objetos filtrados
	 */
	public int[] mayoresDeCiertaEdad(int edad) {
		int[] resultado;
		int contadorCoincidencias = 0;
		for (T dato : elementos) {
			if (dato.getEdad() >= edad) {
				contadorCoincidencias++;
			}
		}
		resultado = new int[contadorCoincidencias];
		contadorCoincidencias = 0;
		for (int i = 0; i < elementosEnCola; i++) {
			if (elementos[i].getEdad() >= edad) {
				resultado[contadorCoincidencias++] = i;
			}
		}
		return resultado;
	}

}

/**
 * Personalización de excepción para este trabajo
 * 
 * @author Duberly Guarnizo Fuentes Rivera
 *
 */
class CustomStaticQueueExeption extends Exception {
	private static final long serialVersionUID = -4071353461008976083L;

	public CustomStaticQueueExeption(String message) {
		super(message);
	}
}
