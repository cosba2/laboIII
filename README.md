# Documentación

Este proyecto simula el manejo de clientes, cuentas, transferencias, etc en una aplicación de banco. En lugar de usar una base de datos, se utiliza un archivo de texto para almacenar la información. Además, se emplea Mockito para realizar pruebas unitarias sobre el controlador.

## ClienteController

Este controlador maneja las operaciones CRUD relacionadas con la entidad **Cliente** a través de endpoints REST.

### 1. **POST /clientes - Crear Cliente**
- **Descripción**: Crea un nuevo cliente en el sistema.
- **Parámetros**:
  - `@RequestBody ClienteDto clientedto`: Un objeto DTO que contiene los datos del cliente (nombre, apellido, DNI, edad, etc.).
- **Validaciones**:
  - Se utiliza **ClienteValidator** para validar los datos del cliente.
- **Excepciones**:
  - `ClienteAlreadyExistsException`: Se lanza si el cliente ya existe.
  - `ClienteMenorEdadException`: Se lanza si el cliente es menor de edad.
- **Respuesta**: `201 CREATED` con el cliente creado.

---

### 2. **DELETE /clientes/{dni} - Eliminar Cliente**
- **Descripción**: Elimina un cliente basado en su DNI.
- **Parámetros**:
  - `@PathVariable long dni`: El DNI del cliente que se desea eliminar.
- **Excepciones**:
  - `ClienteNoEncontradoException`: Se lanza si no existe un cliente con el DNI especificado.
- **Respuesta**: `200 OK` con el cliente eliminado.

---

### 3. **PUT /clientes/{dni} - Modificar Cliente**
- **Descripción**: Modifica los datos de un cliente existente.
- **Parámetros**:
  - `@PathVariable long dni`: El DNI del cliente que se desea modificar.
  - `@RequestBody ClienteDto clientedto`: Un objeto DTO con los datos actualizados del cliente.
- **Validaciones**:
  - Se utiliza **ClienteValidator** para validar los datos actualizados del cliente.
- **Excepciones**:
  - `ClienteNoEncontradoException`: Se lanza si el cliente no existe.
- **Respuesta**: `200 OK` con el cliente modificado.

---

### 4. **GET /clientes/{dni} - Obtener Cliente por DNI**
- **Descripción**: Devuelve los datos de un cliente específico según su DNI.
- **Parámetros**:
  - `@PathVariable long dni`: El DNI del cliente que se desea consultar.
- **Excepciones**:
  - `ClienteNoEncontradoException`: Se lanza si el cliente no existe.
- **Respuesta**: `200 OK` con los datos del cliente.

---

### 5. **GET /clientes - Obtener Todos los Clientes**
- **Descripción**: Devuelve una lista con todos los clientes registrados en el sistema.
- **Parámetros**: No recibe parámetros.
- **Excepciones**:
  - `ClienteNoEncontradoException`: Se lanza si no hay clientes registrados.
- **Respuesta**: `200 OK` con una lista de clientes.

---

### **Validaciones**
- Se utiliza la clase **ClienteValidator** para verificar la validez de los datos de entrada en los métodos POST y PUT.
- Se valida, por ejemplo:
  - Que el cliente no sea menor de edad.
  - Que los datos proporcionados sean correctos.

### **Excepciones**
El controlador maneja excepciones personalizadas:
- `ClienteAlreadyExistsException`: Cliente duplicado.
- `ClienteMenorEdadException`: Cliente no apto por ser menor de edad.
- `ClienteNoEncontradoException`: Cliente no existente en la base de datos.

---

## CuentaController

Este controlador maneja las operaciones relacionadas con la entidad **Cuenta** mediante endpoints REST.

### 1. **POST /cuentas/crearCuenta - Crear Cuenta**
- **Descripción**: Crea una nueva cuenta bancaria para un cliente existente.
- **Parámetros**:
  - `@RequestBody CuentaDto cuentaDto`: Un objeto DTO que contiene los datos de la cuenta (CBU, tipo de cuenta, saldo inicial, etc.).
- **Validaciones**:
  - Se utiliza **CuentaValidator** para validar los datos de la cuenta.
- **Excepciones**:
  - `ClienteNoEncontradoException`: Se lanza si el cliente asociado a la cuenta no existe.
- **Respuesta**: `201 CREATED` con la cuenta creada.

---

### 2. **GET /cuentas/mostrar/{dni} - Obtener Cuentas por DNI**
- **Descripción**: Devuelve todas las cuentas asociadas a un cliente según su DNI.
- **Parámetros**:
  - `@PathVariable long dni`: El DNI del cliente cuyas cuentas se desean consultar.
- **Excepciones**:
  - `CuentaNoEncontradaException`: Se lanza si no se encuentran cuentas asociadas al DNI.
  - `ClienteNoEncontradoException`: Se lanza si el cliente no existe.
- **Respuesta**: `200 OK` con la lista de cuentas del cliente.

---

### 3. **GET /cuentas/mostrar - Obtener Todas las Cuentas**
- **Descripción**: Devuelve una lista con todas las cuentas registradas en el sistema.
- **Parámetros**: No recibe parámetros.
- **Excepciones**:
  - `CuentaNoEncontradaException`: Se lanza si no hay cuentas registradas.
- **Respuesta**: `200 OK` con la lista de todas las cuentas.

---

### 4. **DELETE /cuentas/eliminar/{cbu} - Eliminar Cuenta por CBU**
- **Descripción**: Elimina una cuenta bancaria específica mediante su CBU.
- **Parámetros**:
  - `@PathVariable long cbu`: El CBU de la cuenta que se desea eliminar.
- **Excepciones**:
  - `CuentaNoEncontradaException`: Se lanza si no existe una cuenta con el CBU especificado.
- **Respuesta**: `200 OK` con los datos de la cuenta eliminada.

---

### **Validaciones**
- Se utiliza la clase **CuentaValidator** para validar los datos de entrada en el método POST.
- La validación garantiza, por ejemplo:
  - Que los datos de la cuenta sean correctos.
  - Que el cliente asociado exista.

### **Excepciones**
Este controlador lanza las siguientes excepciones personalizadas:
- `ClienteNoEncontradoException`: Cliente no existente en el sistema.
- `CuentaNoEncontradaException`: Cuenta no encontrada en las consultas o eliminación.

---

## MovimientosController

Este controlador maneja las operaciones relacionadas con los **movimientos** de cuentas bancarias (depósitos, retiros y consultas de operaciones).

### 1. **POST /movimientos/deposito/{cbu} - Realizar Depósito**
- **Descripción**: Permite realizar un depósito en una cuenta específica mediante su CBU.
- **Parámetros**:
  - `@PathVariable long cbu`: El CBU de la cuenta donde se realizará el depósito.
  - `@RequestParam double monto`: El monto a depositar en la cuenta.
- **Excepciones**:
  - `CuentaNoEncontradaException`: Se lanza si no existe una cuenta con el CBU especificado.
- **Respuesta**: `200 OK` con los detalles del movimiento realizado (depósito).

---

### 2. **POST /movimientos/retiro/{cbu} - Realizar Retiro**
- **Descripción**: Permite realizar un retiro de una cuenta específica mediante su CBU.
- **Parámetros**:
  - `@PathVariable long cbu`: El CBU de la cuenta de la cual se retirará dinero.
  - `@RequestParam double monto`: El monto a retirar de la cuenta.
- **Excepciones**:
  - `CuentaNoEncontradaException`: Si no existe una cuenta con el CBU especificado.
  - `CuentaSinSaldoException`: Si la cuenta no tiene saldo suficiente.
- **Respuesta**: `200 OK` con los detalles del movimiento realizado (retiro).

---

### 3. **GET /movimientos/operaciones/{cbu} - Obtener Operaciones por CBU**
- **Descripción**: Devuelve la lista de movimientos realizados en una cuenta específica.
- **Parámetros**:
  - `@PathVariable long cbu`: El CBU de la cuenta.
- **Excepciones**:
  - `MomivientosVaciosException`: Si la cuenta no tiene movimientos registrados.
- **Respuesta**: `200 OK` con la lista de movimientos.

---

## TransferenciaController

Este controlador maneja las operaciones relacionadas con las **transferencias** entre cuentas.

### 1. **GET /api/cuenta/{cbu}/transacciones - Obtener Transacciones por CBU**
- **Descripción**: Devuelve la lista de transferencias realizadas en una cuenta.
- **Parámetros**:
  - `@PathVariable long cbu`: El CBU de la cuenta.
- **Respuesta**: `200 OK` con la lista de transferencias realizadas.

---

### 2. **POST /api/transferencia - Realizar Transferencia**
- **Descripción**: Permite realizar una transferencia entre cuentas.
- **Parámetros**:
  - `@RequestBody TransferenciaDto transferenciaDto`: Información de la transferencia (CBU origen, CBU destino, monto y tipo de moneda).
- **Validaciones**:
  - Se utiliza **TransferenciaValidator** para validar los datos.
- **Excepciones**:
  - `CuentaNoEncontradaException`: Si no se encuentra alguna cuenta.
  - `CuentaSinSaldoException`: Si la cuenta origen no tiene saldo suficiente.
  - `TipoMonedasInvalidasException`: Si las cuentas tienen tipos de moneda diferentes.
- **Respuesta**: `200 OK` con los detalles de la transferencia realizada.


## Uso de Mockito para pruebas unitarias

Se utilizan pruebas unitarias con **Mockito** para simular el comportamiento de los servicios y verificar el correcto funcionamiento del controlador.
