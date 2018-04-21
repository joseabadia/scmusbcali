package co.edu.usbcali.banco.jpa;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.banco.modelo.Cliente;
import co.edu.usbcali.banco.modelo.TipoDocumento;

class TestCliente {
	
	private final static Logger log=LoggerFactory.getLogger(TestCliente.class);

	static EntityManagerFactory entityManagerFactory=null;
	static EntityManager entityManager=null;
	
	private BigDecimal clieId=new BigDecimal(142020);
	

	@Test
	@DisplayName("consultarClientePorId")
	void btest() {
		assertNotNull(entityManager, "El entitymanager es nulo");
		Cliente cliente=entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente no existe");
		
		log.info("Id: "+cliente.getClieId());
		log.info("Nombre: "+cliente.getNombre());
		
	}

	@Test
	@DisplayName("crearCliente")
	void atest() {
		assertNotNull(entityManager, "El entitymanager es nulo");
		Cliente cliente=entityManager.find(Cliente.class, clieId);
		assertNull(cliente, "El cliente ya existe");
		
		cliente=new Cliente();
		cliente.setActivo('S');
		cliente.setClieId(clieId);
		cliente.setDireccion("Calle 5");
		cliente.setEmail("jabadia83@gmail.com");
		cliente.setNombre("jose abadia");
		cliente.setTelefono("5554444");
		
		TipoDocumento tipoDocumento=entityManager.find(TipoDocumento.class,2L);
		assertNotNull(tipoDocumento, "El tipo documento no existe");
		cliente.setTipoDocumento(tipoDocumento);
		
		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("ModificarCliente")
	void ctest() {
		assertNotNull(entityManager, "El entitymanager es nulo");
		Cliente cliente=entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente no existe");
		
		cliente=new Cliente();
		cliente.setActivo('S');
		cliente.setClieId(clieId);
		cliente.setDireccion("Calle 56");
		cliente.setEmail("jabadia83@gmail.com");
		cliente.setNombre("jose abadia sarria");
		cliente.setTelefono("5554444");
		
		TipoDocumento tipoDocumento=entityManager.find(TipoDocumento.class,2L);
		assertNotNull(tipoDocumento, "El tipo documento no existe");
		cliente.setTipoDocumento(tipoDocumento);
		
		entityManager.getTransaction().begin();
		entityManager.merge(cliente);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("consultarCliente")
	void etest() {
		assertNotNull(entityManager, "El entitymanager es nulo");
		
		String jpql="SELECT cli FROM Cliente cli";
		
		List<Cliente> losClientes=entityManager.createQuery(jpql).getResultList();
		
		losClientes.forEach(cliente->{
			log.info("Id: "+cliente.getClieId());
			log.info("Nombre"+cliente.getNombre());
		});
		
		/*for (Cliente cliente : losClientes) {
			log.info("Id: "+cliente.getClieId());
			log.info("Nombre"+cliente.getNombre());
		}*/
	}
	
	@Test
	@DisplayName("BorraCliente")
	void dtest() {
		assertNotNull(entityManager, "El entitymanager es nulo");
		Cliente cliente=entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente no existe");
	
		entityManager.getTransaction().begin();
		entityManager.remove(cliente);
		entityManager.getTransaction().commit();
	}
	
	
	
	
	
	@BeforeEach
	public void antes() {
		log.info("antes de la prueba");
	}
	
	@AfterEach
	public void despues() {
		log.info("despues de la prueba");
	}
	
	
	@BeforeAll
	public static void iniciar() {
		log.info("Se ejecuto el @BeforeAll");
		entityManagerFactory=Persistence.createEntityManagerFactory("banco-logica");
		entityManager=entityManagerFactory.createEntityManager();
	}
	
	@AfterAll
	public static void finalizar() {
		log.info("Se ejecuto el @AfterAll");
		entityManager.close();
		entityManagerFactory.close();
	}

}
