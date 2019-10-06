package ejemplo.cajero;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import ejemplo.cajero.control.Comando;
import ejemplo.cajero.control.ComandoConsignar;
import ejemplo.cajero.control.ComandoListarCuentas;
import ejemplo.cajero.control.ComandoRetirar;
import ejemplo.cajero.control.ComandoTransferir;
import ejemplo.cajero.modelo.Banco;
import ejemplo.cajero.modelo.Cuenta;

/**
 * Simulador de un Cajero de Banco
 */
public class Cajero{
	
	/**
	 * Programa principal
	 * @param args parámetros de línea de comandos. Son ignorados por el programa.
	 */
	public static void main(String[] args) throws IOException{
		List<String> list = Arrays.asList(args);
		System.out.print(list);
		
		// crea el banco
		Banco banco = new Banco();
		
		if( list.contains("SaldoReducido") ) {
			// crea unas cuentas, para la prueba
			banco.agregarCuenta(new Cuenta("1", "clave", 1000,200));
			banco.agregarCuenta(new Cuenta("2", "clave", 2000,200));
			banco.agregarCuenta(new Cuenta("3", "clave", 3000,200));
		} else {
			// crea unas cuentas, para la prueba
			banco.agregarCuenta(new Cuenta("1", "clave", 1000,0));
			banco.agregarCuenta(new Cuenta("2", "clave", 2000,0));
			banco.agregarCuenta(new Cuenta("3", "clave", 3000,0));
		}
		
		if( list.contains("Imprimir") ) {
			System.out.println("Los registros de las operraciones se estan imprimiendo");
		}
		
		
		// crea los comandos que se van a usar en la aplicación
		List<Comando> comandos = cargaComandos(list);
		
		
		// Ciclo del Programa
		// ==================

		System.out.println("Cajero Automático");
		System.out.println("=================");
		System.out.println();
		
		boolean fin = false;
		do {
			
			// muestra los nombres de los comandos
			muestraMenuConComandos(comandos);
			System.out.println("X.- Salir");
			
			// la clase Console no funciona bien en Eclipse
			Scanner console = new Scanner(System.in);			
			String valorIngresado = console.nextLine();
			
			// obtiene el comando a ejecutar
			Comando comandoSeleccionado = retornaComandoSeleccionado(comandos, valorIngresado);
			if (comandoSeleccionado != null) {
				
				// intenta ejecutar el comando
				try {
					comandoSeleccionado.ejecutar(banco);
					
				} catch (Exception e) {
					// si hay una excepción, muestra el mensaje
					System.err.println(e.getMessage());
				}
				
			} 
			// si no se obtuvo un comando, puede ser la opción de salir
			else if (valorIngresado.equalsIgnoreCase("X")) {
				fin = true;
			}
			
			System.out.println();
		} while ( !fin );
		
		System.out.println("Gracias por usar el programa.");
	}
	
	
	// Manejo de los comandos de la aplicación
	// =======================================
	
	// carga los comandos usados en el programa
	private static List<Comando> cargaComandos(List<String> list) {
		
		// crea los comandos que se van a usar en la aplicación
		List<Comando> comandos = new ArrayList<>();
		
		if ( list.contains("ConsultarSaldo") ) {
			comandos.add(new ComandoListarCuentas());
		}
		if( list.contains("Retiros") ) {
			comandos.add(new ComandoRetirar());
		}
		if( list.contains("Tranfererencia") ) {
			comandos.add(new ComandoTransferir());
		}
		if( list.contains("Consignaciones") ) {
			comandos.add(new ComandoConsignar());
		}
		return comandos;
	}
	
	
	// Muestra el listado de comandos, para mostrar un menú al usuario
	// muestra el índice en el arreglo de comandos y el nombre del comando
	private static void muestraMenuConComandos(List<Comando> comandos) {
		
		// muestra los nombres de los comandos 
		for (int i=0; i < comandos.size(); i++) {
			System.out.println(i + ".-" + comandos.get(i).getNombre());
		}
	}
	
	
	// dado el texto ingresado por el usuario, retorna el comando correspondiente
	// retorna null si el texto no es un número o no existe ningún comando con ese índice
	private static Comando retornaComandoSeleccionado(List<Comando> comandos, String valorIngresado) {
		
		Comando comandoSeleccionado = null;
		
		// el valorIngresado es un número ?
		if (valorIngresado.matches("[0-9]")) {			
			int valorSeleccionado = Integer.valueOf(valorIngresado);
			// es un índice válido para la lista de comandos
			if (valorSeleccionado < comandos.size()) {
				comandoSeleccionado = comandos.get(valorSeleccionado);
			}
		}
		
		return comandoSeleccionado;
	}
	
}
