package co.edu.usbcali.banco.jpa;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
import co.edu.usbcali.banco.modelo.TipoUsuario;
import co.edu.usbcali.banco.modelo.Transaccion;
import co.edu.usbcali.banco.modelo.Usuario;

class TestUsuario {

	private final static Logger log=LoggerFactory.getLogger(TestUsuario.class);

	static EntityManagerFactory entityManagerFactory=null;
	static EntityManager entityManager=null;
	private String user="jose.abadia";
	
	@Test
	@DisplayName("consultarUsuarioIdentificacion")
	void btest() {
		log.info("Se ejecuta la prueba consultarUsuarioIdentificacion");
		assertNotNull(entityManager, "El entitymanager es nulo");
		Usuario usuario=entityManager.find(Usuario.class, user);
		assertNotNull(usuario, "El usuario no existe");
		
		log.info("Identificacion: "+ usuario.getIdentificacion());
		log.info("Nombre: "+usuario.getNombre());
		
	}
	
	@Test
	@DisplayName("crearUsuario")
	void atest() {
		log.info("Se ejecuta la prueba crearUsuario");
		assertNotNull(entityManager, "El entitymanager es nulo");
		Usuario usuario=entityManager.find(Usuario.class, user);
		assertNull(usuario, "El usuario ya existe");
		
		
		usuario=new Usuario();
		usuario.setUsuUsuario(user);
		usuario.setClave("123456");
		usuario.setIdentificacion(new BigDecimal(14609082));
		usuario.setNombre("Jose Abadia");
		usuario.setActivo('S');
		
		TipoUsuario tipoUsuario=entityManager.find(TipoUsuario.class,1L);
		assertNotNull(tipoUsuario, "El tipo usuario no existe");

		usuario.setTipoUsuario(tipoUsuario);
		
		entityManager.getTransaction().begin();
		entityManager.persist(usuario);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("modificarUsuario")
	void ctest() {
		assertNotNull(entityManager, "El entitymanager es nulo");
		Usuario usuario=entityManager.find(Usuario.class, user);
		assertNotNull(usuario, "El usuario ya existe");
		
		
		usuario=new Usuario();
		usuario.setUsuUsuario(user);
		usuario.setClave("123456");
		usuario.setIdentificacion(new BigDecimal(14609082));
		usuario.setNombre("Jose Abadia Sarria");
		usuario.setActivo('N');
		
		TipoUsuario tipoUsuario=entityManager.find(TipoUsuario.class,1L);
		assertNotNull(tipoUsuario, "El tipo usuario no existe");
		usuario.setTipoUsuario(tipoUsuario);
		
		entityManager.getTransaction().begin();
		entityManager.merge(usuario);
		entityManager.getTransaction().commit();
	}
	
	@Test
	@DisplayName("consultarAllUsuario")
	void etest() {
		assertNotNull(entityManager, "El entitymanager es nulo");
		
		String jpql="SELECT usu FROM Usuario usu";
		
		List<Usuario> losUsuarios=entityManager.createQuery(jpql).getResultList();
		
		losUsuarios.forEach(usuario->{
			log.info("Identificacion:     "+usuario.getIdentificacion());
			log.info("Nombre del usuario: "+usuario.getNombre());
		});
		
		
	}
	
	
	@Test
	@DisplayName("BorrarUsuario")
	void dtest() {
		log.info("Se ejecuta la prueba borrarUsuario");
		assertNotNull(entityManager, "El EntityManager es nulo");
		Usuario usuario = entityManager.find(Usuario.class, user);
		assertNotNull(usuario, "El usuario no existe");
		
		entityManager.getTransaction().begin();
		entityManager.remove(usuario);
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
