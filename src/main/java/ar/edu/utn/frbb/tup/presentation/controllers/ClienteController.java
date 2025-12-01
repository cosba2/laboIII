package ar.edu.utn.frbb.tup.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ar.edu.utn.frbb.tup.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.exception.ClienteMenorEdadException;
import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;

import ar.edu.utn.frbb.tup.model.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import ar.edu.utn.frbb.tup.presentation.validator.ClienteValidator; // Validador personalizado
import ar.edu.utn.frbb.tup.service.ClienteService; // Servicio de lógica de negocio
import ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto; // DTO para transferencia de datos

import org.springframework.web.bind.annotation.*;

import java.util.List; // Importación para manejar listas de clientes

/**
 * Este controlador maneja todas las operaciones relacionadas con los clientes,
 * tales como creación, modificación, eliminación y consulta.
 */
@RestController // Indica que esta clase es un controlador REST y manejará solicitudes HTTP.
@RequestMapping("/clientes") // Define el endpoint base para todas las rutas de este controlador.
public class ClienteController {

    @Autowired // Inyección de dependencia para el servicio que maneja la lógica de negocio.
    private ClienteService clienteService;

    @Autowired // Inyección de dependencia para el validador que verifica la validez de los datos.
    private ClienteValidator clienteValidator;

    /**
     * Crea un nuevo cliente en el sistema.
     * 
     * @param clientedto Datos del cliente enviados en el cuerpo de la solicitud.
     * @return Un objeto ResponseEntity con el cliente creado y el código de estado HTTP 201 (CREATED).
     * @throws ClienteAlreadyExistsException Si el cliente ya existe en el sistema.
     * @throws ClienteMenorEdadException Si el cliente es menor de edad y no puede ser registrado.
     */
    @PostMapping  //json por postamn
    public ResponseEntity<Cliente> darDeAltaCliente(@RequestBody ClienteDto clientedto)
            throws ClienteAlreadyExistsException, ClienteMenorEdadException {
        clienteValidator.validarCliente(clientedto); // Validación personalizada de los datos.
        return new ResponseEntity<>(clienteService.darDeAltaCliente(clientedto), HttpStatus.CREATED);
    }

    /**
     * Elimina un cliente existente basado en su DNI.
     * 
     * @param dni DNI del cliente a eliminar.
     * @return Un objeto ResponseEntity con el cliente eliminado y el código de estado HTTP 200 (OK).
     * @throws ClienteNoEncontradoException Si el cliente no se encuentra en el sistema.
     */ 
    // endpoint con parametro de ruta.. que es diferente al paramtro de consulta
    @DeleteMapping("/{dni}")
    public ResponseEntity<Cliente> borrarCliente(@PathVariable long dni) throws ClienteNoEncontradoException {
        return new ResponseEntity<>(clienteService.borrarCliente(dni), HttpStatus.OK);
    }

    /**
     * Modifica los datos de un cliente existente.
     * 
     * @param dni DNI del cliente a modificar.
     * @param clientedto Datos actualizados del cliente enviados en el cuerpo de la solicitud.
     * @return Un objeto ResponseEntity con el cliente modificado y el código de estado HTTP 200 (OK).
     * @throws ClienteNoEncontradoException Si el cliente no se encuentra en el sistema.
     */
    // la mejjor manera es crear un dto para que se valide y luego si pasa la validacion -> se transforma en la clase original.
    @PutMapping("/{dni}")
    public ResponseEntity<Cliente> modificarCliente(@PathVariable long dni, @RequestBody ClienteDto clientedto)
            throws ClienteNoEncontradoException {
        clienteValidator.validarCliente(clientedto); // Validación personalizada de los datos.
        return new ResponseEntity<>(clienteService.modificarCliente(clientedto), HttpStatus.OK);
    }

    /**
     * Obtiene los datos de un cliente específico basado en su DNI.
     * 
     * @param dni DNI del cliente a buscar.
     * @return Un objeto ResponseEntity con los datos del cliente y el código de estado HTTP 200 (OK).
     * @throws ClienteNoEncontradoException Si el cliente no se encuentra en el sistema.
     */
    @GetMapping("/{dni}")
    public ResponseEntity<Cliente> mostrarCliente(@PathVariable long dni) throws ClienteNoEncontradoException {
        return new ResponseEntity<>(clienteService.mostrarCliente(dni), HttpStatus.OK);
    }

    /**
     * Obtiene una lista con todos los clientes registrados en el sistema.
     * 
     * @return Un objeto ResponseEntity con la lista de clientes y el código de estado HTTP 200 (OK).
     * @throws ClienteNoEncontradoException Si no hay clientes registrados en el sistema.
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> getTodosLosClientes() throws ClienteNoEncontradoException {
        return new ResponseEntity<>(clienteService.mostrarTodosLosClientes(), HttpStatus.OK);
    }
}
