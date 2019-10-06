package ejemplo.cajero.control;

import java.util.Scanner;

import ejemplo.cajero.modelo.Banco;
import ejemplo.cajero.modelo.Cuenta;

/**
 * Comando usado para listar las cuentas 
 */
public class ComandoListarCuentas implements Comando {

	@Override
	public String getNombre() {
		return "Consultar Saldo";
	}

	@Override
	public void ejecutar(Banco contexto) throws Exception {
		
		System.out.println("Consulta de saldo");
		System.out.println();
		
		// la clase Console no funciona bien en Eclipse
		Scanner console = new Scanner(System.in);			
		
		// Ingresa los datos
		System.out.println("Ingrese el número de cuenta");
		String numeroDeCuenta = console.nextLine();
		
		Cuenta cuenta1 = contexto.buscarCuenta(numeroDeCuenta);
		if (cuenta1 == null) {
			throw new Exception("No existe cuenta con el número " + numeroDeCuenta);
		}
		boolean encontrada = false;
	
		for (Cuenta cuenta : contexto.getCuentas()) {
			if( cuenta.getNumero().equals(numeroDeCuenta)) {
				encontrada = true;
				System.out.println(cuenta.getNumero() + " : $ " + cuenta.getSaldo());
			}
		}
		
		if( !encontrada ) {
			System.out.println("No se encontro ninguna cuenta con ese n�mero. Intentelo de nuevo");
		}

	}

}
