package ar.edu.utn.frbb.tup.presentation.validator;

import java.time.LocalDate; // Import para manejar fechas.
import java.time.format.DateTimeParseException; // Excepción lanzada al parsear fechas inválidas.

import org.springframework.stereotype.Component; // Marca esta clase como un componente de Spring.
import ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto; // DTO que representa los datos de un cliente.

@Component // Indica que esta clase es un componente gestionado por Spring (permite inyectarlo en otros lugares).
public class ClienteValidator {

    // Método principal para validar un objeto ClienteDto.
    public void validarCliente(ClienteDto clientedto) {

        // Llama a un método auxiliar para validar el formato del DNI.
        validarDNI(clientedto.getDni());

        // Valida que el DNI no sea "0".
        if (Long.parseLong(clientedto.getDni()) == 0) {
            throw new IllegalArgumentException("El dni del titular de la cuenta es obligatorio");
        }

        // Valida que el DNI no sea un número negativo.
        if (Integer.parseInt(clientedto.getDni()) <= 0) {
            throw new IllegalArgumentException("El dni del titular de la cuenta no puede ser negativo");
        }
    
        // Valida que el DNI tenga exactamente 8 dígitos.
        if (Long.parseLong(clientedto.getDni().toString()) < 10000000 || Long.parseLong(clientedto.getDni().toString()) > 99999999) {
            throw new IllegalArgumentException("El dni del titular de la cuenta debe ser de 8 digitos");
        }

        // Valida que el nombre del cliente no sea nulo o vacío.
        if (clientedto.getNombre().isEmpty() || clientedto.getNombre() == null) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio");
        }
    
        // Valida que el apellido del cliente no sea nulo o vacío.
        if (clientedto.getApellido().isEmpty() || clientedto.getApellido() == null) {
            throw new IllegalArgumentException("El apellido del cliente es obligatorio");
        }

        // Valida que la dirección del cliente no sea nula o vacía.
        if (clientedto.getDireccion().isEmpty() || clientedto.getDireccion() == null) {
            throw new IllegalArgumentException("La dirección del cliente es obligatoria");    
        }

        // Valida que el banco asociado al cliente no sea nulo o vacío.
        if (clientedto.getBanco().isEmpty() || clientedto.getBanco() == null) {
            throw new IllegalArgumentException("El banco del cliente es obligatorio");     
        }

        // Valida que la fecha de nacimiento no sea nula.
        if (clientedto.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula");
        }

        // Llama a un método auxiliar para validar el formato de la fecha de nacimiento.
        validarFechaNacimiento(clientedto.getFechaNacimiento());

        // Valida que el tipo de persona (e.g., física o jurídica) no sea nulo.
        if (clientedto.getTipoPersona() == null) {
            throw new IllegalArgumentException("El tipo de persona no puede ser nulo");
        }
    }

    // Método auxiliar para validar que la fecha de nacimiento tenga un formato válido.
    private void validarFechaNacimiento(String fechaNacimiento) {
        try {
            LocalDate.parse(fechaNacimiento); // Intenta convertir el string en un objeto LocalDate.
        } catch (DateTimeParseException e) { // Si el formato no es válido, lanza una excepción.
            throw new IllegalArgumentException("La fecha de nacimiento no tiene el formato correcto");
        }
    }

    // Método auxiliar para validar que el DNI sea un número válido.
    private void validarDNI(String dni) {
        try {
            Long.parseLong(dni); // Intenta convertir el string en un número.
        } catch (NumberFormatException e) { // Si el string no puede convertirse, lanza una excepción.
            throw new IllegalArgumentException("El dni no tiene el formato correcto");
        }
    }
}
