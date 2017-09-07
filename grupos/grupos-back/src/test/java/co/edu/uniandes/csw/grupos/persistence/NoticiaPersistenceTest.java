/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaId;
import java.util.ArrayList;
import java.util.List;
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
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.Deployment;

import org.jboss.shrinkwrap.api.ShrinkWrap;

import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
/**
 *
 * @author s.guzmanm
 */
@RunWith(Arquillian.class)

public class NoticiaPersistenceTest {
    
    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Noticia, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(NoticiaEntity.class.getPackage())
                .addPackage(NoticiaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    
    @Inject
    private NoticiaPersistence persistence;

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
    private List<NoticiaEntity> data = new ArrayList<NoticiaEntity>();
    
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
        em.createQuery("delete from NoticiaEntity").executeUpdate();
    }


         private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            NoticiaEntity entity = factory.manufacturePojo(NoticiaEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createEntity method, of class NoticiaPersistence.
     */
    @Test
    public void testCreateEntity() {
        
        PodamFactory factory= new PodamFactoryImpl();
        NoticiaEntity newEntity = factory.manufacturePojo(NoticiaEntity.class);
        NoticiaEntity result=persistence.createEntity(newEntity);
        Assert.assertNotNull(result);
        NoticiaId id= newEntity.getId();
        NoticiaEntity found=em.find(NoticiaEntity.class, newEntity.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(newEntity,result);
    }

    /**
     * Test of updateEntity method, of class NoticiaPersistence.
     */
    @Test
    public void testUpdateEntity() {
        
        NoticiaEntity entity=data.get(0);
        PodamFactory factory= new PodamFactoryImpl();
        NoticiaEntity updated=factory.manufacturePojo(NoticiaEntity.class);
        NoticiaId id=entity.getId();
       
       updated.setId(id);
        persistence.updateEntity(updated);
        
        NoticiaEntity rta= em.find(NoticiaEntity.class, id);
        Assert.assertEquals(updated.getId().getTitulo(),rta.getId().getTitulo());
        //        Assert.assertEquals(updated.getId().getAutor(),rta.getId().getAutor());

    }

    /**
     * Test of find method, of class NoticiaPersistence.
     */
    @Test
    public void testFind() {
         NoticiaEntity entity=data.get(0);
         NoticiaId id=entity.getId();
        NoticiaEntity found=persistence.find(id);
        Assert.assertNotNull(found);
        Assert.assertEquals(entity.getId().getTitulo(),found.getId().getTitulo());
      //  Assert.assertEquals(entity.getAutor(),found.getAutor());
    }

    /**
     * Test of findAll method, of class NoticiaPersistence.
     */
    @Test
    public void testFindAll() {
        
        List<NoticiaEntity> list=persistence.findAll();
        Assert.assertEquals(data.size(),list.size());
        boolean found=false;
        for(NoticiaEntity e: list)
        {
            found=false;
            for(NoticiaEntity e2: data)
            {
                if(e2.getId().getTitulo().equals(e.getId().getTitulo()) /* && e2.getId().getAutor().equals(e.getId().getAutor())*/)
                {
                    found=true;
                    break;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Test of delete method, of class NoticiaPersistence.
     */
    @Test
    public void testDelete() {
        NoticiaEntity entity= data.get(0);
        NoticiaId id=entity.getId();
       // id.setAutor(entity.getAutor());
       persistence.delete(id);
       NoticiaEntity deleted= em.find(NoticiaEntity.class,id);
       Assert.assertNull(deleted);
    }
    
}
