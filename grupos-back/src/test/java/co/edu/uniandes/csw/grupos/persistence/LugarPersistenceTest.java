/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.LugarEntity;
import co.edu.uniandes.csw.grupos.persistence.LugarPersistence;
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
 * @author js.ramos14
 */
@RunWith(Arquillian.class)
public class LugarPersistenceTest {
    
    /**
     * Persistencia de lugar
     */
    @Inject
    private LugarPersistence lugarPersistence;
    /**
     * Entity manager
     */
    @PersistenceContext
    private EntityManager em;
    /**
     * User transaction
     */
    @Inject
    UserTransaction utx;
    /**
     * Datos
     */
    private List<LugarEntity> data = new ArrayList<LugarEntity>();
    //Deployment
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(LugarEntity.class.getPackage())
                .addPackage(LugarPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    //BeforeClass
    @BeforeClass
    public static void setUpClass() {
    }
    //AfterClass
    @AfterClass
    public static void tearDownClass() {
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
     *Borra los datos
     */
    private void clearData() {
        em.createQuery("delete from LugarEntity").executeUpdate();
    }
    
    /**
     * Inserta datos
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            LugarEntity entity = factory.manufacturePojo(LugarEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
        
    
    /**
     * Test de crear lugar.<br>
     * @throws Exception Lanza excepción si hay algún error
     */
    @Test
    public void createLugarTest() throws Exception{
        PodamFactory factory = new PodamFactoryImpl();
        LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
        LugarEntity result = lugarPersistence.create(newEntity);

        Assert.assertNotNull(result);

        LugarEntity entity = em.find(LugarEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    /**
     * Prueba de obtener lugares.<br>
     * @throws Exception 
     */
    @Test
    public void getLugarsTest() throws Exception{
        List<LugarEntity> list = lugarPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (LugarEntity ent : list) {
            boolean found = false;
            for (LugarEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba de obtener un lugar.<br>
     * @throws Exception 
     */
    @Test
    public void getLugarTest() throws Exception{
        LugarEntity entity = data.get(0);
        LugarEntity newEntity = lugarPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    /**
     * Prueba de actualizar un lugar
     * @throws Exception 
     */
    @Test
    public void updateLugarTest() throws Exception{
        LugarEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);

        newEntity.setId(entity.getId());
        newEntity.setNombre(entity.getNombre());
        newEntity.setDireccion(entity.getDireccion());
        newEntity.setCapacidad(entity.getCapacidad());

        lugarPersistence.update(newEntity);

        LugarEntity resp = em.find(LugarEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getDireccion(), entity.getDireccion());
        Assert.assertEquals(newEntity.getCapacidad(), entity.getCapacidad());
        Assert.assertEquals(newEntity.isDisponibilidad(), entity.isDisponibilidad());

    }
}
