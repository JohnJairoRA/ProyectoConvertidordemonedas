package Convertidordemonedas;

import Convertidordemonedas.API.ConexionAPI;
import Convertidordemonedas.service.ServicioConvertidorMonedas;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Scanner;

@SpringBootApplication
public class ProyectoConvertidordemonedasApplication implements CommandLineRunner {

	private final Scanner scanner = new Scanner(System.in);
	private final List<String> historialConsultas = new ArrayList<>();
	private final ConexionAPI conexionAPI = new ConexionAPI();
	private final ServicioConvertidorMonedas servicioConvertidorMonedas = new ServicioConvertidorMonedas(conexionAPI);

	public static void main(String[] args) {
		SpringApplication.run(ProyectoConvertidordemonedasApplication.class, args);
	}

	@Override
	public void run(String... args) {
		mostrarBienvenida();
		esperarEnter();
		menuPrincipal();
	}

	private void mostrarBienvenida() {
		System.out.println("\n********************************************************");
		System.out.println("* *");
		System.out.println("* ¡Bienvenido al Conversor de Monedas Mundial!   *");
		System.out.println("* *");
		System.out.println("********************************************************");
	}

	private void esperarEnter() {
		System.out.println("\nPresiona ENTER para iniciar...");
		scanner.nextLine(); // Espera a que el usuario presione Enter
	}

	private void menuPrincipal() {
		int opcion;
		do {
			System.out.println("\n--- MENÚ PRINCIPAL ---");
			System.out.println("1. Explicación de uso del programa");
			System.out.println("2. Iniciar Conversión");
			System.out.println("3. Descargar Historial de Consultas");
			System.out.println("4. Finalizar programa");
			System.out.print("Elige una opción: ");

			try {
				opcion = scanner.nextInt();
				scanner.nextLine(); // Consumir el salto de línea

				switch (opcion) {
					case 1:
						mostrarExplicacionUso();
						break;
					case 2:
						iniciarConversion();
						break;
					case 3:
						descargarHistorial();
						break;
					case 4:
						mostrarDespedida();
						break;
					default:
						System.out.println("Opción no válida. Por favor, elige una opción del 1 al 4.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Entrada inválida. Por favor, ingresa un número.");
				scanner.nextLine(); // Limpiar el buffer del scanner
				opcion = 0; // Para mantener el bucle
			}
		} while (opcion != 4);
	}

	private void mostrarExplicacionUso() {
		System.out.println("\n--- EXPLICACIÓN DE USO ---");
		System.out.println("Este conversor te permite obtener la tasa de cambio actual entre diferentes monedas.");
		System.out.println("1. Se te pedirá que ingreses el código de la moneda de la que deseas convertir (ej. USD, EUR, COP).");
		System.out.println("2. Luego, ingresarás el monto que deseas convertir (puede ser un número entero o decimal).");
		System.out.println("3. Finalmente, se te pedirá el código de la moneda a la que deseas convertir (ej. MXN, BRL, ARS).");
		System.out.println("4. El programa te mostrará el valor convertido y te dará la opción de realizar más consultas o descargar el historial.");
		System.out.println("¡Asegúrate de usar los códigos de moneda correctos (ISO 4217) para obtener resultados precisos!");
		System.out.println("Presiona ENTER para volver al menú principal...");
		scanner.nextLine();
	}

	private void iniciarConversion() {
		boolean continuarConversion = true;
		while (continuarConversion) {
			try {
				System.out.println("\n--- INICIAR CONVERSIÓN ---");

				System.out.print("Ingresa el código de la moneda de origen (ej. USD, EUR, COP): ");
				String fromCurrency = scanner.nextLine().toUpperCase(); // Convertir a mayúsculas para consistencia

				System.out.print("Ingresa el monto a convertir (ej. 100.50): ");
				double amount = scanner.nextDouble();
				scanner.nextLine(); // Consumir el salto de línea

				System.out.print("Ingresa el código de la moneda destino (ej. MXN, BRL, ARS): ");
				String toCurrency = scanner.nextLine().toUpperCase(); // Convertir a mayúsculas

				double convertedAmount = servicioConvertidorMonedas.convert(amount, fromCurrency, toCurrency);

				if (convertedAmount != -1) { // -1 indica un error en la conversión
					String resultado = String.format("%.2f %s son %.2f %s", amount, fromCurrency, convertedAmount, toCurrency);
					System.out.println("\n" + resultado);
					String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
					historialConsultas.add(String.format("[%s] %s", fechaHora, resultado));
				} else {
					System.out.println("No se pudo realizar la conversión. Verifica los códigos de moneda o la conexión.");
				}

				System.out.print("\n¿Deseas realizar otra consulta? (si/no): ");
				String respuesta = scanner.nextLine().toLowerCase();

				if (respuesta.equals("si")) {
					System.out.print("¿Cuántas consultas adicionales deseas realizar? ");
					int numConsultas = scanner.nextInt();
					scanner.nextLine(); // Consumir el salto de línea
					for (int i = 0; i < numConsultas; i++) {
						System.out.println("\n--- Consulta Adicional #" + (i + 1) + " ---");
						// Aquí podrías repetir la lógica de pedir monedas y monto,
						// pero para simplificar, se asume que el bucle principal ya lo hace.
						// Para este ejemplo, simplemente volvemos a pedir los datos en la siguiente iteración del while.
						// Si quieres que las N consultas adicionales sean automáticas sin volver a preguntar "si/no",
						// tendrías que refactorizar esta parte.
						// Por ahora, el 'while(continuarConversion)' ya maneja esto.
					}
				} else {
					continuarConversion = false;
				}

			} catch (InputMismatchException e) {
				System.out.println("Entrada inválida para el monto o número de consultas. Asegúrate de ingresar números válidos.");
				scanner.nextLine(); // Limpiar el buffer del scanner
				continuarConversion = false; // Salir para evitar bucle infinito con entrada inválida
			} catch (IOException | InterruptedException e) {
				System.out.println("Error de comunicación con la API: " + e.getMessage());
				continuarConversion = false;
			} catch (Exception e) {
				System.out.println("Ocurrió un error inesperado: " + e.getMessage());
				e.printStackTrace();
				continuarConversion = false;
			}
		}
		// Después de salir del bucle de conversión, preguntar si desea volver al menú o finalizar
		System.out.print("\n¿Qué deseas hacer ahora? (1. Retornar al menú principal / 2. Finalizar programa): ");
		try {
			int opcionFinal = scanner.nextInt();
			scanner.nextLine(); // Consumir el salto de línea
			if (opcionFinal == 2) {
				mostrarDespedida();
			}
			// Si elige 1, el bucle do-while del menú principal se encargará de mostrarlo de nuevo
		} catch (InputMismatchException e) {
			System.out.println("Entrada inválida. Volviendo al menú principal.");
			scanner.nextLine();
		}
	}

	private void descargarHistorial() {
		if (historialConsultas.isEmpty()) {
			System.out.println("\nEl historial de consultas está vacío. Realiza algunas conversiones primero.");
			return;
		}

		String nombreArchivo = "historial_consultas.txt";
		try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
			for (String consulta : historialConsultas) {
				writer.println(consulta);
			}
			System.out.println("\nHistorial de consultas descargado exitosamente en: " + nombreArchivo);
		} catch (IOException e) {
			System.err.println("Error al escribir el historial en el archivo: " + e.getMessage());
		}
		System.out.println("Presiona ENTER para volver al menú principal...");
		scanner.nextLine();
	}

	private void mostrarDespedida() {
		System.out.println("\n********************************************************");
		System.out.println("* *");
		System.out.println("* ¡Gracias por usar el Conversor de Monedas!      *");
		System.out.println("* ¡Hasta pronto!                       *");
		System.out.println("* *");
		System.out.println("********************************************************");
		// Aquí podrías añadir la lógica para descargar el historial automáticamente al finalizar
		// si el usuario no lo hizo antes.
		descargarHistorial(); // Descarga el historial al finalizar
		System.exit(0); // Termina la aplicación
	}
}
