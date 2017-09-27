/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author tefa
 */
@RunWith(Arquillian.class)
public class PatrocinioPersistenceTest {
    
    /**
     * Deployment
     * @return Un JavaArchive
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PatrocinioEntity.class.getPackage())
                .addPackage(PatrocinioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Inyección de la dependencia a la clase PatrocinioPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private PatrocinioPersistence persistence;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para marcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;

    /**
     * Lista que guarda las entidades Patrocinio fuera de la base de datos
     */
    private List<PatrocinioEntity> data = new ArrayList<>();
    
    /**
     * Lista que guarda las entidades de Usuario fuera de la base de datos
     */
    private List<UsuarioEntity> dataU = new ArrayList<>();
    
    public PatrocinioPersistenceTest() {
    }
    
    /**
     * Se ejecuta antes de todo.
     */
    @BeforeClass
    public static void setUpClass() {
    }
    
    /**
     * Se ejecuta después de todo.
     */
    @AfterClass
    public static void tearDownClass() {
    }
    
    /**
     * setUp ejecutado antes de cada prueba.
     */
    @Before
    public void setUp() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    /**
     * Limpia la base de datos.
     */
    private void clearData() {
        em.createQuery("delete from PatrocinioEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos en estructuras (listas) y en la base de datos.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
        em.persist(usuario);
        dataU.add(usuario);
        for (int i = 0; i < 3; i++) {
            PatrocinioEntity entity = factory.manufacturePojo(PatrocinioEntity.class);
            entity.setUsuario(usuario);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * tearDown ejecutado después de cada prueba.
     */
    @After
    public void tearDown() {
        
    }
    
    /**
     * Test que prueba la creación de las entidades.
     */
    @Test
    public void createEntityTest() {
        PodamFactory factory = new PodamFactoryImpl();
        PatrocinioEntity newEntity = factory.manufacturePojo(PatrocinioEntity.class);
        newEntity.setUsuario(dataU.get(0));
        
        PatrocinioEntity result = persistence.createEntity(newEntity);

        Assert.assertNotNull(result);
        PatrocinioEntity entity = em.find(PatrocinioEntity.class, result.getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(entity.getId(), result.getId());
        Assert.assertEquals(entity.getPago(), result.getPago(), 0.0001);
        Assert.assertEquals(entity.getUsuario(), result.getUsuario());
    }
    
    /**
     * Test del método findAll en la persistencia.
     */
    @Test
    public void getPatrociniosTest() {
        List<PatrocinioEntity> list = persistence.findAll();
        assertEquals(data.size(), list.size());
        for (PatrocinioEntity ent : list) {
            boolean found = false;
            for (PatrocinioEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Test del método find de la persistencia.
     */
    @Test
    public void getPatrocinioTest() {
        PatrocinioEntity entity = data.get(0);
        PatrocinioEntity newEntity = persistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getPago(), entity.getPago(), 0.0001);
        Assert.assertEquals(newEntity.getUsuario(), dataU.get(0));
    }
    
    /**
     * Test del método updateEntity de la persistencia.
     */
    @Test
    public void updatePatrocinioTest() {
        PatrocinioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PatrocinioEntity newEntity = factory.manufacturePojo(PatrocinioEntity.class);

        newEntity.setId(entity.getId());
        newEntity.setUsuario(dataU.get(0));

        persistence.updateEntity(newEntity);

        PatrocinioEntity resp = em.find(PatrocinioEntity.class, entity.getId());

        assertEquals(resp.getId(), newEntity.getId());
        Assert.assertEquals(resp.getPago(), newEntity.getPago(), 0.0001);
        Assert.assertEquals(resp.getUsuario(), dataU.get(0));
    }
    
    /**
     * Test del método delete de la persistencia.
     */
    @Test
    public void deletePatrocinioTest() {
        PatrocinioEntity entity = data.get(0);
        persistence.delete(entity.getId());
        PatrocinioEntity deleted = em.find(PatrocinioEntity.class, entity.getId());
        assertNull(deleted);
    }
}
