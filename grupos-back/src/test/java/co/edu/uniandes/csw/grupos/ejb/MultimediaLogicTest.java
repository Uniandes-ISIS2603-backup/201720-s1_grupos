/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.persistence.*;
import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.Deployment;

import org.jboss.shrinkwrap.api.ShrinkWrap;

import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * Prueba de la persistencia de la multimedia
 * @author s.guzmanm
 */
@RunWith(Arquillian.class)

public class MultimediaLogicTest {
    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Multimedia, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MultimediaEntity.class.getPackage())
                .addPackage(MultimediaPersistence.class.getPackage())
                .addPackage(MultimediaLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Persistencia de la multimedia
     */
    @Inject
    private MultimediaLogic logic;

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

    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    /**
     * Logger de la lógica
     */
    private static final Logger LOGGER = Logger.getLogger(MultimediaLogicTest.class.getName());
    /*
     * Lista de los datos de la persistencia de la multimedia.
     */
    private List<MultimediaEntity> data = new ArrayList<>();
   /**
     * Acción de preparar la prueba. Este procedimiento inclute iniciar la transacción, unir el manejador de persistencia,
     * borrar la información presente, e insertar datos.
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
     * Borra toda la información presente en la base de datos.
     */
    private void clearData() {
        em.createQuery("delete from MultimediaEntity").executeUpdate();
    }


    /**
     * Inserta los datos de prueba en la base de datos.
     */

         private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            MultimediaEntity entity = factory.manufacturePojo(MultimediaEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Qué hacer después de todas las pruebas
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of createEntity method, of class MultimediaPersistence.
     */
    @Test
    public void testCreateEntity() {
        boolean estaBien=true;
        MultimediaEntity test = new MultimediaEntity();
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
            test.setLink(null);
            logic.createEntity(test);
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
            test.setLink("A");
            test.setNombre(null);
            logic.createEntity(test);
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
            test.setNombre("A");
            test.setRuta(null);
            logic.createEntity(test);
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
            logic.createEntity(data.get(0));
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
            PodamFactory factory= new PodamFactoryImpl();
            MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
            MultimediaEntity result=logic.createEntity(newEntity);
            Assert.assertNotNull(result);
            MultimediaEntity found=em.find(MultimediaEntity.class, newEntity.getLink());
            Assert.assertNotNull(found);
            Assert.assertEquals(newEntity,result);
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
     * Test of updateEntity method, of class MultimediaPersistence.
     */
    @Test
    public void testUpdateEntity() {
        boolean estaBien=true;
        MultimediaEntity test = new MultimediaEntity();
        try
        {
            logic.updateEntity(null,null);
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
            test.setNombre(null);
            logic.updateEntity(null,test);
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
            test.setNombre("A");
            test.setRuta(null);
            logic.updateEntity(null,test);
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
            test =factory.manufacturePojo(MultimediaEntity.class);
            while(data.indexOf(test)>=0)
            {
                test =factory.manufacturePojo(MultimediaEntity.class);
            }
            logic.updateEntity(test.getLink(),test);
        }
        catch(EJBException e)
        {
            LOGGER.info(e.getMessage());
            estaBien=false;
        }
        catch(BusinessException e)
        {
            LOGGER.info(e.getMessage());
            estaBien=true;
        }
        if(estaBien)
        {
            Assert.fail();
        }
        estaBien=true;
        try
        {
            MultimediaEntity entity=data.get(0);
           PodamFactory factory= new PodamFactoryImpl();
           MultimediaEntity updated=factory.manufacturePojo(MultimediaEntity.class);
           updated.setLink(entity.getLink());
           logic.updateEntity(entity.getLink(),updated);

           MultimediaEntity rta= em.find(MultimediaEntity.class, entity.getLink());
           Assert.assertEquals(updated.getLink(),rta.getLink());
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

    /**
     * Test of find method, of class MultimediaPersistence.
     */
    @Test
    public void testFind() {
        MultimediaEntity entity=data.get(0);
        MultimediaEntity found=logic.getEntity(entity.getLink());
        Assert.assertNotNull(found);
        Assert.assertEquals(entity.getLink(),found.getLink());
    }

    /**
     * Test of delete method, of class MultimediaPersistence.
     */
    @Test
    public void testDelete() {
        MultimediaEntity entity= data.get(0);
        logic.deleteEntity(entity.getLink());
        MultimediaEntity deleted= em.find(MultimediaEntity.class,entity.getLink());
       Assert.assertNull(deleted);
    }
    
}
