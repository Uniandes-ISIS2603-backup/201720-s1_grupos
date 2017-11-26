/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.persistence.*;
import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.entities.CalificacionEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.ArrayList;
import java.util.Date;
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
 * Prueba sobre la persistencia de calificación
 * @author s.guzmanm
 */
@RunWith(Arquillian.class)
public class CalificacionLogicTest {
    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Calificacion, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
                .addPackage(CalificacionLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
     /**
     * Logger de la lógica
     */
    private static final Logger LOGGER = Logger.getLogger(CalificacionLogicTest.class.getName());

    /**
     * Inyecta la lógica de la calse
     */
    @Inject
    private CalificacionLogic logic;

    /**
     * Variable para marcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;
    /**
     * Manejador de entidades
     */
    @PersistenceContext
    private EntityManager em;

    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    /**
     * Lista de entiades de calificación a persistir.
     */
    private List<CalificacionEntity> data = new ArrayList<>();
    /**
     * Lista de entidades de usuario a persistir.
     */
    private List<UsuarioEntity> dataU= new ArrayList<>();
    /**
     * Lista de entiades de blog a persistir.
     */
    private List<BlogEntity> dataB= new ArrayList<>();
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
     * Borra toda la información del sistema.
     */
    private void clearData() {
        em.createQuery("delete from CalificacionEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
        em.createQuery("delete from BlogEntity").executeUpdate();
    }
    /**
     * Inserta datos en el sistema para hacer las pruebas.
     */
         private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        BlogEntity blog= factory.manufacturePojo(BlogEntity.class);
        dataB.add(blog);
        for (int i = 0; i < 3; i++) {
            CalificacionEntity entity = factory.manufacturePojo(CalificacionEntity.class);
            entity.setBlog(blog);
            UsuarioEntity calificador=factory.manufacturePojo(UsuarioEntity.class);
            entity.setCalificador(calificador);
            em.persist(blog);
            em.persist(calificador);
            em.persist(entity);
            data.add(entity);
            dataU.add(calificador);
            
        }
    }
    /**
     * Qué se debe realizar después de la prueba
     * */
    @After
    public void tearDown() {
    }

    /**
     * Test of createEntity method, of class CalificacionPersistence.
     */
    @Test
    public void testCreateEntity()  {
        PodamFactory factory= new PodamFactoryImpl();

        int index = (int)(Math.random()*2);
        
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setBlog(dataB.get(0));
        newEntity.setCalificador(dataU.get(index));
        
        CalificacionEntity result;
        validarReglasNegocioCreate(newEntity);
       
        boolean estaBien=true;
        try
        {
            newEntity.setCalificacion(2.0);
            newEntity.setFecha(new Date());
            result=logic.createEntity(newEntity);
            Assert.assertNotNull(result);
            CalificacionEntity found=em.find(CalificacionEntity.class, newEntity.getId());
            Assert.assertNotNull(found);
            Assert.assertEquals(newEntity.getId(),result.getId());
            Assert.assertNotNull(result.getBlog());
            Assert.assertEquals(newEntity.getBlog().getId(),result.getBlog().getId());
            Assert.assertNotNull(result.getCalificador());
            Assert.assertEquals(newEntity.getCalificador().getId(),result.getCalificador().getId());
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
     * Test of updateEntity method, of class CalificacionPersistence.
     */
    @Test
    public void testUpdateEntity()  {
        CalificacionEntity entity=data.get(0);
        PodamFactory factory= new PodamFactoryImpl();
        CalificacionEntity updated=factory.manufacturePojo(CalificacionEntity.class);
        validarReglasNegocioUpdate(entity.getId(),updated);
        updated.setId(entity.getId());

        updated.setBlog(entity.getBlog());
        updated.setCalificador(entity.getCalificador());
        boolean estaBien=true;
        try
        {
            logic.updateEntity(entity.getId(),updated);
        }
        catch (BusinessException e)
        {
            estaBien=false;
        }
        if(!estaBien)
        {
            Assert.fail();
        }
        
        CalificacionEntity rta= em.find(CalificacionEntity.class, entity.getId());
        Assert.assertEquals(updated.getId(),rta.getId());

        Assert.assertNotNull(rta.getBlog());
        Assert.assertEquals(updated.getBlog().getId(),rta.getBlog().getId());
        Assert.assertNotNull(rta.getCalificador());
        Assert.assertEquals(updated.getCalificador().getId(),rta.getCalificador().getId());

        
    }

    /**
     * Test of find method, of class CalificacionPersistence.
     */
    @Test
    public void testFind()  {
        CalificacionEntity entity=data.get(0);
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
        try
        {
            PodamFactory factory = new PodamFactoryImpl();
            CalificacionEntity test=factory.manufacturePojo(CalificacionEntity.class);
            while(data.indexOf(test)>=0)
            {
                test=factory.manufacturePojo(CalificacionEntity.class);
            }
            logic.getEntity(test.getId());
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
            CalificacionEntity found=logic.getEntity(entity.getId());
            Assert.assertNotNull(found);
            Assert.assertEquals(entity.getId(),found.getId());

            Assert.assertNotNull(found.getBlog());
            Assert.assertEquals(entity.getBlog().getId(),found.getBlog().getId());
            Assert.assertNotNull(found.getCalificador());
            Assert.assertEquals(entity.getCalificador().getId(),found.getCalificador().getId());
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
     * Test of findAll method, of class CalificacionPersistence.
     */
    @Test
    public void testFindAll()  {
        List<CalificacionEntity> list=logic.getAll();
        Assert.assertEquals(data.size(),list.size());
        boolean found=false;
        for(CalificacionEntity e: list)
        {
            found=false;
            for(CalificacionEntity e2: data)
            {
                if(e2.getId().equals(e.getId()))
                {
                    found=true;
                    break;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Test of delete method, of class CalificacionPersistence.
     */
    @Test
    public void testDelete()  {
        boolean estaBien=true;
        try
        {
            logic.deleteEntity(null);
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
            CalificacionEntity test=factory.manufacturePojo(CalificacionEntity.class);
            while(data.indexOf(test)>=0)
            {
                test=factory.manufacturePojo(CalificacionEntity.class);
            }
            logic.deleteEntity(test.getId());
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
            CalificacionEntity entity= data.get(0);
            logic.deleteEntity(entity.getId());
            CalificacionEntity deleted= em.find(CalificacionEntity.class,entity.getId());
           Assert.assertNull(deleted);
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

    private void validarReglasNegocioCreate(CalificacionEntity newEntity) {
        boolean estaBien=true;
        try
        {
            while(data.indexOf(newEntity)<0)
            {
                newEntity.setId((long)(Math.random()*1000));
            }
            newEntity.setCalificacion(null);
            logic.createEntity(newEntity);
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
            newEntity.setCalificacion(-1.0);
            logic.createEntity(newEntity);
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
            newEntity.setCalificacion(2.0);
            newEntity.setFecha(null);
            logic.createEntity(newEntity);
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
    }
    
    private void validarReglasNegocioUpdate(Long id,CalificacionEntity newEntity) {
        boolean estaBien=true;
        try
        {
            newEntity.setId(null);
            logic.updateEntity(null,newEntity);
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
            newEntity.setId((long)(Math.random()*1000));
            while(data.indexOf(newEntity)>=0)
            {
                newEntity.setId((long)(Math.random()*1000));
            }
            newEntity.setCalificacion(2.0);
            newEntity.setFecha(new Date());
            logic.updateEntity(id,newEntity);
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
        if(estaBien)
        {
            Assert.fail();
        }
        
        estaBien=true;
        try
        {
            newEntity.setId(id);
            newEntity.setCalificacion(null);
            logic.updateEntity(id,newEntity);
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
            newEntity.setCalificacion(-1.0);
            logic.updateEntity(id,newEntity);
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
            newEntity.setCalificacion(2.0);
            newEntity.setFecha(null);
            logic.updateEntity(id,newEntity);
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
            newEntity.setCalificacion(2.0);
            newEntity.setFecha(new Date());
            UsuarioEntity calificador=logic.getEntity(id).getCalificador();
            PodamFactory factory = new PodamFactoryImpl();
            UsuarioEntity test=factory.manufacturePojo(UsuarioEntity.class);
            while(calificador.equals(test))
            {
                test=factory.manufacturePojo(UsuarioEntity.class);
            }
            newEntity.setCalificador(test);
            logic.updateEntity(id,newEntity);
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
        
    }
    
}
