package staticqueue;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

/**
 * <b>ADVERTENCIA: NO SE HAN HECHO VALIDACIONES DE DATOS!</b> Esta clase sirve
 * para probar de manera gráfica las otras clases de este paquete. Se ha
 * utilizado Java Swing.
 * 
 * @author Duberly Guarnizo Fuentes Rivera
 *
 */
public class TopicDemonstrator {

	private JFrame framePrincipal;
	private JLabel textoBienvenida;
	private JLabel textoAdvertencia;
	private JPanel contenedorPrincipal;
	CustomStaticQueue<Persona> cola;

	public TopicDemonstrator() {
		prepararUI();
	}

	public static void main(String[] args) {
		TopicDemonstrator ventana = new TopicDemonstrator();
		cambiarTema();
		ventana.mostrarControles();

	}

	public void prepararUI() {
		framePrincipal = new JFrame("Pila Estática Personalizada");
		framePrincipal.setSize(450, 450);
		framePrincipal.setLayout(new GridLayout(3, 1, 2, 2));
		textoBienvenida = new JLabel("Bienvenido. Por favor, escoje una opción", JLabel.CENTER);
		textoAdvertencia = new JLabel("Preparado. Hecho por Duberly Guarnizo.");
		framePrincipal.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		contenedorPrincipal = new JPanel();
		GridLayout acomodador = new GridLayout(5, 1);
		acomodador.setVgap(2);
		acomodador.setHgap(2);
		contenedorPrincipal.setLayout(acomodador);
		framePrincipal.add(textoBienvenida);
		framePrincipal.add(contenedorPrincipal);
		framePrincipal.add(textoAdvertencia);
		framePrincipal.setVisible(true);
	}

	public void mostrarControles() {
		JButton b1 = new JButton("Crear persona y agregar a la cola");
		JButton b2 = new JButton("Mostrar cola");
		JButton b3 = new JButton("Procesar siguiente");
		JButton b4 = new JButton("Consultar elemento en la cola");
		JButton b5 = new JButton("Filtrar por edad");
		b1.addActionListener(new EventoClick());
		b2.addActionListener(new EventoClick());
		b3.addActionListener(new EventoClick());
		b4.addActionListener(new EventoClick());
		b5.addActionListener(new EventoClick());

		b1.setActionCommand("create");
		b2.setActionCommand("show");
		b3.setActionCommand("pop");
		b4.setActionCommand("query");
		b5.setActionCommand("filter");
		contenedorPrincipal.add(b1);
		contenedorPrincipal.add(b2);
		contenedorPrincipal.add(b3);
		contenedorPrincipal.add(b4);
		contenedorPrincipal.add(b5);
		// framePrincipal.pack(); //<- para ajustar la ventana a los controles, pero se
		// ve feo

	}

	private class EventoClick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evento) {

			String orden = evento.getActionCommand();
			CustomStaticQueue<Persona> listado = TopicDemonstrator.this.cola;
			JLabel advertencia = TopicDemonstrator.this.textoAdvertencia;
			if ("create".equals(orden)) {
				if (listado == null) {
					// cola aún no se ha asignado
					String cantidadElementosEnTexto = JOptionPane.showInputDialog(contenedorPrincipal,
							"No se ha creado un lista aún! Ingresa el número de elementos");
					int cantidadElementos = Integer.parseInt(cantidadElementosEnTexto);
					cola = new CustomStaticQueue<>(Persona.class, cantidadElementos);

				}
				else {
					String nombre = JOptionPane.showInputDialog(contenedorPrincipal,
							"Ingresa el NOMBRE de la persona:");
					int edad = Integer.parseInt(
							JOptionPane.showInputDialog(contenedorPrincipal, "Ingresa la EDAD de la persona:"));
					try {
						listado.agregar(new Persona(nombre, edad));
						advertencia.setText("Elementos en lista: " + listado.conteoElementos());
					}
					catch (CustomStaticQueueExeption e) {
						mostrarError("Error: la cola ya está llena");
						advertencia.setText("Cola completa. Numero de elementos: " + listado.conteoElementos());
					}
				}

				//
			}
			else if ("show".equals(orden)) {
				
				JOptionPane.showMessageDialog(contenedorPrincipal, listado.listarElementos());
			}
			else if ("pop".equals(orden)) {

				Persona per = listado.sacar();
				if (per == null) {
					JOptionPane.showMessageDialog(contenedorPrincipal, "No queda personas por procesar!");
					advertencia.setText("Cola vacía");
				}
				else {
					JOptionPane.showMessageDialog(contenedorPrincipal,
							"Se ha procesado a la primera persona adelante en la lista (" + per.getNombre() + ", de "
									+ per.getEdad() + " años).");
					advertencia.setText("Elementos en lista: " + listado.conteoElementos());
				}

			}
			else if ("query".equals(orden)) {
				int indice = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el indice que desea consultar:"));
				if (indice < listado.length()) {
					if (indice < listado.conteoElementos()) {
						// para el caso en que existen elementos vacíos, que se deben tratar
						// diferenciadamente
						advertencia.setText("El lugar en la cola corresponde a " + listado.consultar(indice).getNombre()
								+ " de " + listado.consultar(indice).getEdad() + " años.");
					}
					else {
						advertencia.setText("Ese lugar en la cola está vacío.");
					}
				}
				else {
					advertencia.setText("Error: indice fuera de rango.");
				}

			}
			else if ("filter".equals(orden)) {
				int filtroEdad = Integer.parseInt(JOptionPane.showInputDialog(contenedorPrincipal,
						"Indicar cual es la edad desde la que dese filtrar:"));
				int[] resultadoFiltro = listado.mayoresDeCiertaEdad(filtroEdad);
				String cadenaResultado = "Listado de personas mayores de " + filtroEdad + " años:";
				for (int personaFiltrada : resultadoFiltro) {
					Persona p = listado.consultar(personaFiltrada);
					cadenaResultado = cadenaResultado + "- " + p.getNombre() + " ( " + p.getEdad() + " ).\n";
				}
				JOptionPane.showMessageDialog(contenedorPrincipal, cadenaResultado);
			}

		}

		private void mostrarError(String mensaje) {
			JOptionPane.showMessageDialog(contenedorPrincipal, mensaje, "Error!", JOptionPane.WARNING_MESSAGE);
		}

	}

	private static void cambiarTema() {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException e) {
			// handle exception
		}
		catch (ClassNotFoundException e) {
			// handle exception
		}
		catch (InstantiationException e) {
			// handle exception
		}
		catch (IllegalAccessException e) {
			// handle exception
		}

	}

}
