/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.TarjetaEntity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author af.lopezf
 */
@RunWith(Arquillian.class)
public class TarjetaPersistenceTest {
    
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TarjetaEntity.class.getPackage())
                .addPackage(TarjetaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    
    
    @Inject
    private TarjetaPersistence persistence;
    
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
    private List<TarjetaEntity> data = new ArrayList<TarjetaEntity>();
    
    
    public TarjetaPersistenceTest() {
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
        em.createQuery("delete from TarjetaEntity").executeUpdate();
    }
    
    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            TarjetaEntity entity = factory.manufacturePojo(TarjetaEntity.class);
            
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of create method, of class TarjetaPersistence.
     */
    @Test
    public void testCreate() throws Exception {
        PodamFactory factory = new PodamFactoryImpl();
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        TarjetaEntity result = persistence.create(newEntity);
        
        Assert.assertNotNull(result);
        TarjetaEntity entity = em.find(TarjetaEntity.class, result.getNumero());
        Assert.assertNotNull(entity);
        Assert.assertEquals(newEntity.getNumero(), entity.getNumero());
        
        //fail("testCreate");
    }
    
    /**
     * Test of findAll method, of class TarjetaPersistence.
     */
    @Test
    public void testFindAll() throws Exception {
        List<TarjetaEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (TarjetaEntity ent : list) {
            boolean found = false;
            for (TarjetaEntity entity : data) {
                if (ent.getNumero() == entity.getNumero()) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
        
        //fail("testFindAll");
    }
    
    /**
     * Test of findByNumero method, of class TarjetaPersistence.
     */
    @Test
    public void testFindByNumero() throws Exception {
        TarjetaEntity entity = data.get(0);
        TarjetaEntity newEntity = persistence.findByNumero(entity.getNumero());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNumero(), newEntity.getNumero());
        
        //fail("testFindByNumero");
    }
    
    /**
     * Test of update method, of class TarjetaPersistence.
     */
    @Test
    public void testUpdate() throws Exception {
        
        TarjetaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        
        newEntity.setNumero(entity.getNumero());
        newEntity.setBanco(entity.getBanco());
        newEntity.setDineroDisponible(entity.getDineroDisponible());
        newEntity.setMaxCupo(entity.getMaxCupo());
        
        persistence.update(newEntity);
        
        TarjetaEntity resp = em.find(TarjetaEntity.class, entity.getNumero());
        
        Assert.assertEquals(newEntity.getNumero(), resp.getNumero());
        Assert.assertEquals(newEntity.getBanco(), resp.getBanco());
        Assert.assertEquals(newEntity.getDineroDisponible(), resp.getDineroDisponible(), 0.00001);
        Assert.assertEquals(newEntity.getMaxCupo(), resp.getMaxCupo(), 0.00001);
        //fail("testUpdate");
    }
    
    /**
     * Test of delete method, of class TarjetaPersistence.
     */
    @Test
    public void testDelete() throws Exception {
        TarjetaEntity entity = data.get(0);
        persistence.delete(entity.getNumero());
        TarjetaEntity deleted = em.find(TarjetaEntity.class, entity.getNumero());
        Assert.assertNull(deleted);
        
        //fail("testDelete");
    }
    
}
