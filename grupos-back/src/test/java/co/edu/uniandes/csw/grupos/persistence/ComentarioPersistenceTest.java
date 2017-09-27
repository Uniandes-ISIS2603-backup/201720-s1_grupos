/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.ComentarioEntity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Prueba comentario
 * @author se.cardenas
 */
@RunWith(Arquillian.class)
public class ComentarioPersistenceTest {
    /**
     * Persitencia
     */
    @Inject
    private ComentarioPersistence persistence;
    /**
     * Entity manager
     */
    @PersistenceContext
    private EntityManager em;
    /**
     * UserTransaction
     */
    @Inject
            UserTransaction utx;
    /*
    Datos
    */
    private List<ComentarioEntity> data = new ArrayList<>();
    //Deployment
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ComentarioEntity.class.getPackage())
                .addPackage(ComentarioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    /**
     * Constructor vacío
     */
    public ComentarioPersistenceTest() {
    }
    //Setup
    @BeforeClass
    public static void setUpClass() {
    }
    //Teardown
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
    //Teardown
    @After
    public void tearDown() {
    }
    /**
     * Borra los datos
     */
    private void clearData() {
        em.createQuery("delete from ComentarioEntity").executeUpdate();
    }
    /**
     * Inserta datos
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);
            
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Test of createComentario method, of class ComentarioPersistence.
     */
    @Test
    public void testCreateComentario() throws Exception {
        PodamFactory factory = new PodamFactoryImpl();
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        ComentarioEntity result = persistence.createComentario(newEntity);
        
        Assert.assertNotNull(result);
        ComentarioEntity entity = em.find(ComentarioEntity.class, result.getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getComentario(), entity.getComentario());
    }

    /**
     * Test of find method, of class ComentarioPersistence.
     */
    @Test
    public void testFind() throws Exception {
        ComentarioEntity entity = data.get(0);
        ComentarioEntity newEntity = persistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getAutor(), entity.getAutor());
        Assert.assertEquals(newEntity.getComentario(), entity.getComentario());
    }

    /**
     * Test of findAll method, of class ComentarioPersistence.
     */
    @Test
    public void testFindAll() throws Exception {
        List<ComentarioEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ComentarioEntity ent : list) {
            boolean found = false;
            for (ComentarioEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Test of update method, of class ComentarioPersistence.
     */
    @Test
    public void testUpdate() throws Exception {
        ComentarioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);

        newEntity.setId(entity.getId());

        persistence.update(newEntity);

        ComentarioEntity resp = em.find(ComentarioEntity.class, entity.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(newEntity.getAutor(), resp.getAutor());
        Assert.assertEquals(newEntity.getComentario(), resp.getComentario());
    }

    /**
     * Test of delete method, of class ComentarioPersistence.
     */
    @Test
    public void testDelete() throws Exception {
        ComentarioEntity entity = data.get(0);
        persistence.delete(entity.getId());
        ComentarioEntity deleted = em.find(ComentarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
