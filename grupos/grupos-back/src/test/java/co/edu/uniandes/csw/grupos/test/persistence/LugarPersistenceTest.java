/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.test.persistence;

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
    
        
    @Inject
    private LugarPersistence eventoPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    private List<LugarEntity> data = new ArrayList<LugarEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(LugarEntity.class.getPackage())
                .addPackage(LugarPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    public LugarPersistenceTest()
    {
    }
        
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

       
    private void clearData() {
        em.createQuery("delete from EventoEntity").executeUpdate();
    }
    
        
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            LugarEntity entity = factory.manufacturePojo(LugarEntity.class);

            em.persist(entity);
            data.add(entity);
        }
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
    @After
    public void tearDown() {
    }

        
    @Test
    public void createEventoTest() throws Exception{
        PodamFactory factory = new PodamFactoryImpl();
        LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
        LugarEntity result = eventoPersistence.create(newEntity);

        Assert.assertNotNull(result);

        LugarEntity entity = em.find(LugarEntity.class, result.getDireccion());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
    @Test
    public void getEventosTest() throws Exception{
        List<LugarEntity> list = eventoPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (LugarEntity ent : list) {
            boolean found = false;
            for (LugarEntity entity : data) {
                if (ent.getDireccion().equals(entity.getDireccion())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }


    @Test
    public void getEventoTest() throws Exception{
        LugarEntity entity = data.get(0);
        LugarEntity newEntity = eventoPersistence.find(entity.getDireccion());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }


    @Test
    public void deleteEventoTest() throws Exception{
        LugarEntity entity = data.get(0);
        eventoPersistence.delete(entity.getDireccion());
        LugarEntity deleted = em.find(LugarEntity.class, entity.getDireccion());
        Assert.assertNull(deleted);
    }


    @Test
    public void updateEventoTest() throws Exception{
        LugarEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);

        newEntity.setDireccion(entity.getDireccion());

        eventoPersistence.update(newEntity);

        LugarEntity resp = em.find(LugarEntity.class, entity.getDireccion());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());

    }
}
