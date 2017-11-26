/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.LugarEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.LugarPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJBException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
public class LugarLogicTest {
    
    /**
     * Lógica del lugar
     */
    @Inject
    private LugarLogic logic;
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
    private List<LugarEntity> data = new ArrayList<>();
    //Deployment
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(LugarEntity.class.getPackage())
                .addPackage(LugarPersistence.class.getPackage())
                .addPackage(LugarLogic.class.getPackage())
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
     * Logger de la lógica
     */
    private static final Logger LOGGER = Logger.getLogger(TarjetaLogicTest.class.getName());


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
     * @ Lanza excepción si hay algún error
     */
    @Test
    public void createLugarTest(){
        boolean estaBien=true;
        try
        {
            logic.createEntity(null);
        }
        catch(BusinessException e)
        {
            LOGGER.info(e.getMessage());
            estaBien=false;
        }
        if(estaBien)
        {
            Assert.fail();
        }
        estaBien=true;
        try
        {
            PodamFactory factory = new PodamFactoryImpl();
            LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
            LugarEntity result = logic.createEntity(newEntity);

            Assert.assertNotNull(result);

            LugarEntity entity = em.find(LugarEntity.class, result.getId());

            Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        }
        catch(BusinessException e)
        {
            LOGGER.info(e.getMessage());
            estaBien=false;
        }
        if(!estaBien)
        {
            Assert.fail();
        }
    }
    /**
     * Prueba de obtener lugares.<br>
     * @ 
     */
    @Test
    public void getLugarsTest() {
        List<LugarEntity> list = logic.getAll();
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
     * @ 
     */
    @Test
    public void getLugarTest() {
        LugarEntity entity = data.get(0);
        boolean estaBien=true;
        try
        {
            logic.getEntity(null);
        }
        catch(BusinessException e)
        {
            LOGGER.info(e.getMessage());
            estaBien=false;
        }
        if(estaBien)
        {
            Assert.fail();
        }
        estaBien=true;
        LugarEntity newEntity;
        try
        {
            newEntity=new LugarEntity();
            newEntity.setId(1l);
            while(data.indexOf(newEntity)>=0)
            {
                newEntity.setId((long)(Math.random()*10000));
            }
            logic.getEntity(newEntity.getId());
        }
        catch(EJBException e)
        {
            estaBien=false;
            LOGGER.info(e.getMessage());
        }
        catch(BusinessException e)
        {
            estaBien=true;
            LOGGER.info(e.getMessage());
        }
        if(estaBien)
        {
            Assert.fail();
        }
        estaBien=true;
        try
        {
            newEntity = logic.getEntity(entity.getId());
            Assert.assertNotNull(newEntity);
            Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        }
        catch(BusinessException e)
        {
            estaBien=false;
        }
        if(!estaBien)
        {
            Assert.fail();
        }
    }
    /**
     * Prueba de actualizar un lugar
     * @ 
     */
    @Test
    public void updateLugarTest() {
        boolean estaBien=true;
        try
        {
            logic.updateEntity(null);
        }
        catch(BusinessException e)
        {
            LOGGER.info(e.getMessage());
            estaBien=true;
        }
        catch(EJBException e)
        {
            estaBien=false;
            LOGGER.info(e.getMessage());
        }
        if(estaBien)
        {
            Assert.fail();
        }
        estaBien=true;
        try
        {
            LugarEntity entity = data.get(0);
            PodamFactory factory = new PodamFactoryImpl();
            LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);

            newEntity.setId(entity.getId());
            newEntity.setNombre(entity.getNombre());
            newEntity.setDireccion(entity.getDireccion());
            newEntity.setCapacidad(entity.getCapacidad());

            logic.updateEntity(newEntity);

            LugarEntity resp = em.find(LugarEntity.class, entity.getId());

            Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
            Assert.assertEquals(newEntity.getDireccion(), entity.getDireccion());
            Assert.assertEquals(newEntity.getCapacidad(), entity.getCapacidad());
            Assert.assertEquals(newEntity.isDisponibilidad(), entity.isDisponibilidad());
        }
        catch(BusinessException e)
        {
            estaBien=false;
            LOGGER.info(e.getMessage());
        }
        if(!estaBien)
        {
            Assert.fail();
        }
        
    }
}
