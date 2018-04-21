package co.edu.usbcali.banco.dao;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.banco.modelo.Cliente;
import co.edu.usbcali.banco.modelo.TipoDocumento;


@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
class TestClienteDAO {
	
	@Autowired
	private IClienteDAO clienteDAO;
	
	@Autowired
	private ITipoDocumentoDAO tipoDocumentoDAO;
	
	private BigDecimal clieId=new BigDecimal(1010);

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@DisplayName("CrearCliente")
	void atest() {
		assertNotNull(clienteDAO);
		assertNotNull(tipoDocumentoDAO);
		
	
		Cliente cliente=new Cliente();
		cliente.setActivo('S');
		cliente.setClieId(clieId);
		cliente.setDireccion("Calle 5");
		cliente.setEmail("jabadia83@gmail.com");
		//cliente.setNombre("jose abadia");
		cliente.setTelefono("5554444");
		
		TipoDocumento tipoDocumento=tipoDocumentoDAO.consultarPorId(1L);
		assertNotNull(tipoDocumento, "El tipo documento no existe");
		
		cliente.setTipoDocumento(tipoDocumento);
		
		clienteDAO.grabar(cliente);
		
	}
	
	
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@DisplayName("ModificarCliente")
	@Test
	void ctest() {
		assertNotNull(clienteDAO);
		assertNotNull(tipoDocumentoDAO);
		
		
		Cliente cliente=clienteDAO.consultarPorID(clieId);
		assertNotNull(cliente, "El cliente que desea modificar no existe");
		cliente.setActivo('S');
		cliente.setClieId(clieId);
		cliente.setDireccion("Calle 58");
		cliente.setEmail("jabadia83@gmail.com");
		cliente.setNombre("jose abadia sarria");
		cliente.setTelefono("20202020");
		
		TipoDocumento tipoDocumento=tipoDocumentoDAO.consultarPorId(2L);
		assertNotNull(tipoDocumento, "El tipo documento no existe");
		
		cliente.setTipoDocumento(tipoDocumento);
		
		clienteDAO.modificar(cliente);
	}
	
	
	
	

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@DisplayName("BorraCliente")
	@Test
	void dtest() {
		assertNotNull(clienteDAO);
		assertNotNull(tipoDocumentoDAO);
		Cliente cliente=clienteDAO.consultarPorID(clieId);
		
		assertNotNull(cliente, "El cliente que desea borra no existe");
		
		clienteDAO.borrar(cliente);
		
	}
	
	
	@Test
	@DisplayName("consultarCliente")
	void etest() {
		assertNotNull(clienteDAO);
		assertNotNull(tipoDocumentoDAO);
		List<Cliente> losClientes=clienteDAO.consultarTodos();
		assertNotNull(losClientes, "La lista de clientes es nula");
		
		assertNotEquals(0, losClientes.size());
	}

}
