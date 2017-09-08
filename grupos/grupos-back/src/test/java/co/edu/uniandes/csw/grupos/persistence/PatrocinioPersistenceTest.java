/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.PatrocinioEntity;
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
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author tefa
 */
@RunWith(Arquillian.class)
public class PatrocinioPersistenceTest {
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PatrocinioEntity.class.getPackage())
                .addPackage(PatrocinioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Inyección de la dependencia a la clase XYZPersistence cuyos métodos
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
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;

     /**
     *
     */
    private List<PatrocinioEntity> data = new ArrayList<PatrocinioEntity>();
    
    public PatrocinioPersistenceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
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
    
    private void clearData() {
        em.createQuery("delete from PatrocinioEntity").executeUpdate();
    }


    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            PatrocinioEntity entity = factory.manufacturePojo(PatrocinioEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    @After
    public void tearDown() {
        fail("tearDown");
    }
    
    @Test
    public void createXYZEntityTest() {
        PodamFactory factory = new PodamFactoryImpl();
        PatrocinioEntity newEntity = factory.manufacturePojo(PatrocinioEntity.class);
        PatrocinioEntity result = persistence.createEntity(newEntity);

        assertNotNull(result);
        PatrocinioEntity entity = em.find(PatrocinioEntity.class, result.getId());
        assertNotNull(entity);
        assertEquals(newEntity.getId(), entity.getId());
    }
    
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
            assertTrue(found);
        }
    }
    
    @Test
    public void getPatrocinioTest() {
        PatrocinioEntity entity = data.get(0);
        PatrocinioEntity newEntity = persistence.find(entity.getId());
        assertNotNull(newEntity);
        assertEquals(entity.getId(), newEntity.getId());
    }
    
    @Test
    public void getPatrocinioPorIdTest() {
        PatrocinioEntity entity = data.get(0);
        PatrocinioEntity newEntity = persistence.find(entity.getId());
        assertNotNull(newEntity);
        assertEquals(entity.getId(), newEntity.getId());
    }
    
    @Test
    public void updatePatrocinioTest() {
        PatrocinioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PatrocinioEntity newEntity = factory.manufacturePojo(PatrocinioEntity.class);

        newEntity.setId(entity.getId());

        persistence.updateEntity(newEntity);

        PatrocinioEntity resp = em.find(PatrocinioEntity.class, entity.getId());

        assertEquals(newEntity.getId(), resp.getId());
    }
    
    @Test
    public void deletePatrocinioTest() {
        PatrocinioEntity entity = data.get(0);
        persistence.delete(entity.getId());
        PatrocinioEntity deleted = em.find(PatrocinioEntity.class, entity.getId());
        assertNull(deleted);
    }
}
