/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
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
 * @author se.cardenas
 */
@RunWith(Arquillian.class)
public class BlogPersistenceTest {
    /**
     * Persistencia de blog
     */
    @Inject
    private BlogPersistence persistence;
    /**
     * Manejador de entidades
     */
    @PersistenceContext
    private EntityManager em;
    /**
     * User transaction
     */
    @Inject
            UserTransaction utx;
    /**
     * Datos de blog
     */
    private List<BlogEntity> data = new ArrayList<>();
    /**
     * Datos de grupo
     */
    private List<GrupoEntity> dataG = new ArrayList<>();
    /**
     * Deployment.<br>
     * @return Deploy del archivo
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BlogEntity.class.getPackage())
                .addPackage(BlogPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    //constructor vacío
    public BlogPersistenceTest() {
    }
    /**
     * Antes de
     */
    @BeforeClass
    public static void setUpClass() {
    }
    /**
     * Después de
     */
    @AfterClass
    public static void tearDownClass() {
    }
    /**
     * Setip
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
     * Después de
     */
    @After
    public void tearDown() {
    }
    /**
     * Borra datos
     */
    private void clearData() {
        em.createQuery("delete from BlogEntity").executeUpdate();
    }
    /**
     * inserta datos
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        GrupoEntity grupo = factory.manufacturePojo(GrupoEntity.class);
        em.persist(grupo);
        dataG.add(grupo);
        for (int i = 0; i < 3; i++) {
            BlogEntity entity = factory.manufacturePojo(BlogEntity.class);
            entity.setGrupo(grupo);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Test of createBlog method, of class BlogPersistence.
     */
    @Test
    public void testCreateBlog() throws Exception {
        PodamFactory factory = new PodamFactoryImpl();
        BlogEntity newEntity = factory.manufacturePojo(BlogEntity.class);
        GrupoEntity grupo = dataG.get(0);
        newEntity.setGrupo(grupo);
        BlogEntity result = persistence.createBlog(newEntity);
        
        Assert.assertNotNull(result);
        BlogEntity entity = em.find(BlogEntity.class, result.getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(newEntity.getTitulo(), entity.getTitulo());
        Assert.assertEquals(newEntity.getPromedio(), entity.getPromedio());
        Assert.assertEquals(newEntity.getContenido(), entity.getContenido());
        Assert.assertEquals(newEntity.getGrupo(), entity.getGrupo());
    }
    
    /**
     * Test of find method, of class BlogPersistence.
     */
    @Test
    public void testFind() throws Exception {
        BlogEntity entity = data.get(0);
        BlogEntity newEntity = persistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getTitulo(), newEntity.getTitulo());
        Assert.assertEquals(entity.getPromedio(), newEntity.getPromedio());
        Assert.assertEquals(entity.getContenido(), newEntity.getContenido());
        Assert.assertEquals(entity.getGrupo(), newEntity.getGrupo());
    }
    
    /**
     * Test of findAll method, of class BlogPersistence.
     */
    @Test
    public void testFindAll() throws Exception {
        List<BlogEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (BlogEntity ent : list) {
            boolean found = false;
            for (BlogEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Test of update method, of class BlogPersistence.
     */
    @Test
    public void testUpdate() throws Exception {
        BlogEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        BlogEntity newEntity = factory.manufacturePojo(BlogEntity.class);

        newEntity.setId(entity.getId());
        newEntity.setGrupo(entity.getGrupo());

        persistence.update(newEntity);

        BlogEntity resp = em.find(BlogEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getTitulo(), resp.getTitulo());
        Assert.assertEquals(newEntity.getPromedio(), resp.getPromedio());
        Assert.assertEquals(newEntity.getContenido(), resp.getContenido());
        Assert.assertEquals(newEntity.getGrupo(), resp.getGrupo());
    }
    
    /**
     * Test of delete method, of class BlogPersistence.
     */
    @Test
    public void testDelete() throws Exception {
        BlogEntity entity = data.get(0);
        persistence.delete(entity.getId());
        BlogEntity deleted = em.find(BlogEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Encuentra grupos de un blog.<br>
     * @throws Exception Si hay problema alguno.
     */
    @Test
    public void testFindBlogGrupo() throws Exception {
        BlogEntity entity = data.get(0);
        BlogEntity newEntity = persistence.findBlogGrupo(entity.getGrupo().getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getTitulo(), newEntity.getTitulo());
        Assert.assertEquals(entity.getPromedio(), newEntity.getPromedio());
        Assert.assertEquals(entity.getContenido(), newEntity.getContenido());
        Assert.assertEquals(entity.getGrupo(), newEntity.getGrupo());
    }
}
