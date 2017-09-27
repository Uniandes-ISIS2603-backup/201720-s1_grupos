/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.CategoriaEntity;
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
 * @author cm.sarmiento10
 */
@RunWith(Arquillian.class)
public class CategoriaPersistenceTest {
    
    /**
     * Inyección de la dependencia a la clase CategoriaPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private CategoriaPersistence persistence;

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
     * Datos de categoría
     */
    private List<CategoriaEntity> data = new ArrayList<CategoriaEntity>();
    //Deployment
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CategoriaEntity.class.getPackage())
                .addPackage(CategoriaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    /**
     * Constructor vacío
     */
    public CategoriaPersistenceTest() {
    }
    //Setup
    @BeforeClass
    public static void setUpClass() {
    }
    //Teardown
    @AfterClass
    public static void tearDownClass() {
    }
    //ClearData
    private void clearData() {
        em.createQuery("delete from CategoriaEntity").executeUpdate();
    }

/**
 * Inserta datos
 */
 private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            CategoriaEntity entity = factory.manufacturePojo(CategoriaEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    //Setup
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
    //TearDown
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class CategoriaPersistence.
     */
    @Test
    public void testCreate() throws Exception {
         PodamFactory factory = new PodamFactoryImpl();
    CategoriaEntity newEntity = factory.manufacturePojo(CategoriaEntity.class);
    CategoriaEntity result = persistence.create(newEntity);

    Assert.assertNotNull(result);
    CategoriaEntity entity = em.find(CategoriaEntity.class, result.getId());
    Assert.assertNotNull(entity);
    Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
    }

    /**
     * Test of update method, of class CategoriaPersistence.
     */
    @Test
    public void testUpdate() throws Exception {
     CategoriaEntity entity = data.get(0);
    PodamFactory factory = new PodamFactoryImpl();
    CategoriaEntity newEntity = factory.manufacturePojo(CategoriaEntity.class);

    newEntity.setId(entity.getId());

    persistence.update(newEntity);

    CategoriaEntity resp = em.find(CategoriaEntity.class, entity.getId());

    Assert.assertEquals(newEntity.getTipo(), resp.getTipo());
}

    /**
     * Test of delete method, of class CategoriaPersistence.
     */
    @Test
    public void testDelete() throws Exception {
    CategoriaEntity entity = data.get(0);
    persistence.delete(entity.getId());
    CategoriaEntity deleted = em.find(CategoriaEntity.class, entity.getId());
    Assert.assertNull(deleted);
}

    /**
     * Test of find method, of class CategoriaPersistence.
     */
    @Test
    public void testFind() throws Exception {
        
     CategoriaEntity entity = data.get(0);
    CategoriaEntity newEntity = persistence.find(entity.getId());
    Assert.assertNotNull(newEntity);
    Assert.assertEquals(entity.getTipo(), newEntity.getTipo());
}

    /**
     * Test of findAll method, of class CategoriaPersistence.
     */
    @Test
    public void testFindAll() throws Exception {
   List<CategoriaEntity> list = persistence.findAll();
    Assert.assertEquals(data.size(), list.size());
    for (CategoriaEntity ent : list) {
        boolean found = false;
        for (CategoriaEntity entity : data) {
            if (ent.getId().equals(entity.getId())) {
                found = true;
            }
        }
        Assert.assertTrue(found);
    }
    }

    /**
     * Test of findByAddress method, of class CategoriaPersistence.
     */
    @Test
    public void testFindByTipo() throws Exception {
    CategoriaEntity entity = data.get(0);
    CategoriaEntity newEntity = persistence.findByTipo(entity.getTipo());
    Assert.assertNotNull(newEntity);
    Assert.assertEquals(entity.getTipo(), newEntity.getTipo());
}
    
}
