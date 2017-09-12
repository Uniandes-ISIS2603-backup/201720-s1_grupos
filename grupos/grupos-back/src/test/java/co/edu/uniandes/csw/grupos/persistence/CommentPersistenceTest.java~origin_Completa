/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.CommentEntity;
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
 *
 * @author se.cardenas
 */
@RunWith(Arquillian.class)
public class CommentPersistenceTest {
    
    @Inject
    private CommentPersistence persistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
            UserTransaction utx;
    
    private List<CommentEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CommentEntity.class.getPackage())
                .addPackage(CommentPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    public CommentPersistenceTest() {
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
    
    @After
    public void tearDown() {
    }

    private void clearData() {
        em.createQuery("delete from CommentEntity").executeUpdate();
    }
    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            CommentEntity entity = factory.manufacturePojo(CommentEntity.class);
            
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Test of createComment method, of class CommentPersistence.
     */
    @Test
    public void testCreateComment() throws Exception {
        PodamFactory factory = new PodamFactoryImpl();
        CommentEntity newEntity = factory.manufacturePojo(CommentEntity.class);
        CommentEntity result = persistence.createComment(newEntity);
        
        Assert.assertNotNull(result);
        CommentEntity entity = em.find(CommentEntity.class, result.getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(newEntity.getAuthor(), entity.getAuthor());
        Assert.assertEquals(newEntity.getComment(), entity.getComment());
    }

    /**
     * Test of find method, of class CommentPersistence.
     */
    @Test
    public void testFind() throws Exception {
        CommentEntity entity = data.get(0);
        CommentEntity newEntity = persistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getAuthor(), entity.getAuthor());
        Assert.assertEquals(newEntity.getComment(), entity.getComment());
    }

    /**
     * Test of findAll method, of class CommentPersistence.
     */
    @Test
    public void testFindAll() throws Exception {
        List<CommentEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (CommentEntity ent : list) {
            boolean found = false;
            for (CommentEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Test of update method, of class CommentPersistence.
     */
    @Test
    public void testUpdate() throws Exception {
        CommentEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        CommentEntity newEntity = factory.manufacturePojo(CommentEntity.class);

        newEntity.setId(entity.getId());

        persistence.update(newEntity);

        CommentEntity resp = em.find(CommentEntity.class, entity.getId());
        Assert.assertNotNull(resp);
        Assert.assertEquals(newEntity.getAuthor(), resp.getAuthor());
        Assert.assertEquals(newEntity.getComment(), resp.getComment());
    }

    /**
     * Test of delete method, of class CommentPersistence.
     */
    @Test
    public void testDelete() throws Exception {
        CommentEntity entity = data.get(0);
        persistence.delete(entity.getId());
        CommentEntity deleted = em.find(CommentEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
