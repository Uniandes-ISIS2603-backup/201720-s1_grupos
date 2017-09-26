/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.entities.CalificacionEntity;
import co.edu.uniandes.csw.grupos.entities.ComentarioEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;
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
    
    @Inject
    private BlogPersistence persistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
            UserTransaction utx;
    
    private List<BlogEntity> data = new ArrayList<>();
    
    private List<GrupoEntity> dataG = new ArrayList<>();
    
    private List<ComentarioEntity> dataCo = new ArrayList<>();
    
    private List<MultimediaEntity> dataM = new ArrayList<>();
    
    private List<CalificacionEntity> dataCa = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BlogEntity.class.getPackage())
                .addPackage(BlogPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    public BlogPersistenceTest() {
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
        em.createQuery("delete from BlogEntity").executeUpdate();
        em.createQuery("delete from GrupoEntity").executeUpdate();
        em.createQuery("delete from MultimediaEntity").executeUpdate();
        em.createQuery("delete from ComentarioEntity").executeUpdate();
    }
    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        GrupoEntity grupo = factory.manufacturePojo(GrupoEntity.class);
        em.persist(grupo);
        dataG.add(grupo);
        for (int i = 0; i < 3; i++) {
            BlogEntity entity = factory.manufacturePojo(BlogEntity.class);
            entity.setGrupo(grupo);
            entity.setComentarios(new ArrayList<>());
            entity.setCalificaciones(new ArrayList<>());
            entity.setMultimedia(new ArrayList<>());
            em.persist(entity);
            for (int j = 0; j<2; j++) {
                ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
                CalificacionEntity calificacion = factory.manufacturePojo(CalificacionEntity.class);
                MultimediaEntity multimedia = factory.manufacturePojo(MultimediaEntity.class);
                em.persist(multimedia);
                em.persist(comentario);
                calificacion.setBlog(entity);
                em.persist(calificacion);
                entity.getComentarios().add(comentario);
                entity.getCalificaciones().add(calificacion);
                entity.getMultimedia().add(multimedia);
                dataCo.add(comentario);
                dataCa.add(calificacion);
                dataM.add(multimedia);
            }
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
        Assert.assertEquals(entity.getComentarios().size(), newEntity.getComentarios().size());
        Assert.assertEquals(entity.getCalificaciones().size(), newEntity.getCalificaciones().size());
        Assert.assertEquals(entity.getMultimedia().size(), newEntity.getMultimedia().size());
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
        newEntity.setComentarios(entity.getComentarios());
        newEntity.setCalificaciones(entity.getCalificaciones());
        newEntity.setMultimedia(entity.getMultimedia());

        persistence.update(newEntity);

        BlogEntity resp = em.find(BlogEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getTitulo(), resp.getTitulo());
        Assert.assertEquals(newEntity.getPromedio(), resp.getPromedio());
        Assert.assertEquals(newEntity.getContenido(), resp.getContenido());
        Assert.assertEquals(newEntity.getGrupo(), resp.getGrupo());
        Assert.assertEquals(newEntity.getComentarios().size(), resp.getComentarios().size());
        Assert.assertEquals(newEntity.getCalificaciones().size(), resp.getCalificaciones().size());
        Assert.assertEquals(newEntity.getMultimedia().size(), resp.getMultimedia().size());
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
        for (ComentarioEntity comentario : entity.getComentarios()) {
            ComentarioEntity comDeleted = em.find(ComentarioEntity.class, comentario.getId());
            Assert.assertNull(comDeleted);
        }
        for (CalificacionEntity calificacion : entity.getCalificaciones()) {
            CalificacionEntity calDeleted = em.find(CalificacionEntity.class, calificacion.getId());
            Assert.assertNull(calDeleted);
        }
        for (MultimediaEntity multimedia : entity.getMultimedia()) {
            MultimediaEntity mul = em.find(MultimediaEntity.class, multimedia.getLink());
            Assert.assertNotNull(mul);
        }
    }
    
    
    @Test
    public void testFindBlogGrupo() throws Exception {
        BlogEntity entity = data.get(0);
        BlogEntity newEntity = persistence.findBlogGrupo(entity.getGrupo().getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getTitulo(), newEntity.getTitulo());
        Assert.assertEquals(entity.getPromedio(), newEntity.getPromedio());
        Assert.assertEquals(entity.getContenido(), newEntity.getContenido());
        Assert.assertEquals(entity.getGrupo(), newEntity.getGrupo());
        Assert.assertEquals(entity.getComentarios().size(), newEntity.getComentarios().size());
        Assert.assertEquals(entity.getCalificaciones().size(), newEntity.getCalificaciones().size());
        Assert.assertEquals(entity.getMultimedia().size(), newEntity.getMultimedia().size());
    }
}
