package staticqueue;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import staticqueue.CustomStaticQueue;
import staticqueue.CustomStaticQueueExeption;
import staticqueue.Persona;

/**
 * Clase y métodos de prueba para la cola estática personalizada
 * (CustomStaticQueue). Eres libre de avisar si algún test
 * adicional es requerido.
 * @author Duberly Guarnizo Fuentes Rivera
 *
 */
class CustomStaticQueueTest {
	CustomStaticQueue<Persona> csq;
	Persona per1;
	Persona per2;
	Persona per3;
	Persona per4;
	Persona per5;

	@BeforeEach
	void prepararTest() {
		csq = new CustomStaticQueue<>(Persona.class, 4);
		per1 = new Persona("Juan", 20);
		per2 = new Persona("Maria", 54);
		per3 = new Persona("Juliana", 22);
		per4 = new Persona("Juana la cubana", 36);
		per5 = new Persona("Maria Antonieta De Las Nieves", 41);
	}

	@Test
	void testConstructor() {
		Assertions.assertNotNull(csq);
		assertEquals(4, csq.length());
	}

	@Test
	void testAgregar() {
		try {
			csq.agregar(per1);
			csq.agregar(per2);
		}
		catch (Exception e) {
			// pass
		}
		finally {
			assertEquals(per1, csq.consultar(0));
			assertEquals(per2, csq.consultar(1));
			Assertions.assertNull(csq.consultar(2));
		}

	}

	@Test
	void testSacar() {
		try {
			csq.agregar(per1);
			csq.agregar(per2);
			csq.agregar(per3);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			assertEquals(per1, csq.sacar());
			assertEquals(per2, csq.sacar());
			assertEquals(per3, csq.consultar(0));
			assertEquals(per3, csq.sacar());
			Assertions.assertNull(csq.sacar());
		}

	}

	@Test
	void testMayoresDeCiertaEdad() {
		try {
			csq.agregar(per1);
			csq.agregar(per2);
			csq.agregar(per3);
			csq.agregar(per4);
		}
		catch (CustomStaticQueueExeption e) {
			System.out.println("Excepcion metodo agregar()!");
		}
		finally {
			int[] listaMayores = csq.mayoresDeCiertaEdad(30);
			assertEquals(1, listaMayores[0]);
			assertEquals(3, listaMayores[1]);
		}

	}

	@Test
	void testAgregarCuandoNoHayMasEspacios() {
		try {
			csq.agregar(per1);
			csq.agregar(per2);
			csq.agregar(per3);
			csq.agregar(per4);
			csq.agregar(per5);
		}
		catch (Exception e) {
			assertTrue(e.getMessage().contains("Sin espacio en la cola"));
		}
	}

}
