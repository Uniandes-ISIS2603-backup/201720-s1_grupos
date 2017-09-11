/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.EmpresaEntity;
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
public class EmpresaPersistenceTest {
    
    @Deployment
    public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TarjetaEntity.class.getPackage())
                .addPackage(TarjetaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    
    @Inject
    private EmpresaPersistence persistence;

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
    private List<EmpresaEntity> data = new ArrayList<EmpresaEntity>();

    
    public EmpresaPersistenceTest() {
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
            EmpresaEntity entity = factory.manufacturePojo(EmpresaEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class EmpresaPersistence.
     */
    @Test
    public void testCreate() throws Exception {
        PodamFactory factory = new PodamFactoryImpl();
        EmpresaEntity newEntity = factory.manufacturePojo(EmpresaEntity.class);
        EmpresaEntity result = persistence.create(newEntity);

        Assert.assertNotNull(result);
        EmpresaEntity entity = em.find(EmpresaEntity.class, result.getNit());
        Assert.assertNotNull(entity);
        Assert.assertEquals(newEntity.getNit(), entity.getNit());

        //fail("testCreate");
    }

    /**
     * Test of findAll method, of class EmpresaPersistence.
     */
    @Test
    public void testFindAll() throws Exception {
        List<EmpresaEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (EmpresaEntity ent : list) {
            boolean found = false;
            for (EmpresaEntity entity : data) {
                if (ent.getNit() == entity.getNit()) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }

        //fail("testFindAll");

        
    }

    /**
     * Test of findByNit method, of class EmpresaPersistence.
     */
    @Test
    public void testFindByNit() throws Exception {
        EmpresaEntity entity = data.get(0);
        EmpresaEntity newEntity = persistence.findByNit(entity.getNit());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNit(), newEntity.getNit());

        //fail("testFindByNumero");

        
    }

    /**
     * Test of update method, of class EmpresaPersistence.
     */
    @Test
    public void testUpdate() throws Exception {
        
        EmpresaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        EmpresaEntity newEntity = factory.manufacturePojo(EmpresaEntity.class);

        newEntity.setNit(entity.getNit());

        persistence.update(newEntity);

        EmpresaEntity resp = em.find(EmpresaEntity.class, entity.getNit());

        Assert.assertEquals(newEntity.getNit(), resp.getNit());

        //fail("testUpdate");

    }

    /**
     * Test of delete method, of class EmpresaPersistence.
     */
    @Test
    public void testDelete() throws Exception {
        EmpresaEntity entity = data.get(0);
        persistence.delete(entity.getNit());
        EmpresaEntity deleted = em.find(EmpresaEntity.class, entity.getNit());
        Assert.assertNull(deleted);

        //fail("testDelete");

    }
    
}
