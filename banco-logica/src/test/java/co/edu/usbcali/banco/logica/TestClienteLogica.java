package co.edu.usbcali.banco.logica;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import co.edu.usbcali.banco.modelo.Cliente;
import co.edu.usbcali.banco.modelo.TipoDocumento;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
class TestClienteLogica {

	@Autowired
	private IClienteLogica clienteLogica;

	@Autowired
	private ITipoDocumentoLogica tipoDocumentoLogica;

	private BigDecimal clieId = new BigDecimal(1010);

	@Test
	@DisplayName("CrearCliente")
	void atest() throws Exception {
		assertNotNull(clienteLogica);
		assertNotNull(tipoDocumentoLogica);

		Cliente cliente = new Cliente();
		cliente.setActivo('S');
		cliente.setClieId(clieId);
		cliente.setDireccion("Calle 5");
		cliente.setEmail("jabadia83@gmail.com");
		// cliente.setNombre("jose abadia");
		cliente.setTelefono("5554444");

		TipoDocumento tipoDocumento = tipoDocumentoLogica.consultarPorId(1L);
		assertNotNull(tipoDocumento, "El tipo documento no existe");

		cliente.setTipoDocumento(tipoDocumento);

		clienteLogica.grabar(cliente);

	}

	@DisplayName("ModificarCliente")
	@Test
	void ctest() throws Exception {
		assertNotNull(clienteLogica);
		assertNotNull(tipoDocumentoLogica);

		Cliente cliente = clienteLogica.consultarPorID(clieId);
		assertNotNull(cliente, "El cliente que desea modificar no existe");
		cliente.setActivo('S');
		cliente.setClieId(clieId);
		cliente.setDireccion("Calle 58");
		cliente.setEmail("jabadia83@gmail.com");
		cliente.setNombre("jose abadia sarria");
		cliente.setTelefono("20202020");

		TipoDocumento tipoDocumento = tipoDocumentoLogica.consultarPorId(2L);
		assertNotNull(tipoDocumento, "El tipo documento no existe");

		cliente.setTipoDocumento(tipoDocumento);

		clienteLogica.modificar(cliente);
	}

	@DisplayName("BorraCliente")
	@Test
	void dtest() throws Exception {
		assertNotNull(clienteLogica);
		assertNotNull(tipoDocumentoLogica);
		Cliente cliente = clienteLogica.consultarPorID(clieId);

		assertNotNull(cliente, "El cliente que desea borra no existe");

		clienteLogica.borrar(cliente);

	}

	@Test
	@DisplayName("consultarCliente")
	void etest() {
		assertNotNull(clienteLogica);
		assertNotNull(tipoDocumentoLogica);
		List<Cliente> losClientes = clienteLogica.consultarTodos();
		assertNotNull(losClientes, "La lista de clientes es nula");

		assertNotEquals(0, losClientes.size());
	}

}
