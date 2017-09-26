/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.entities.CategoriaEntity;
import co.edu.uniandes.csw.grupos.entities.EventoEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
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
 * @author cm.sarmiento10
 */
@RunWith(Arquillian.class)
public class GrupoPersistenceTest {
    
    /**
     * Inyección de la dependencia a la clase GrupoPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private GrupoPersistence persistence;
    
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
    private List<GrupoEntity> data = new ArrayList<GrupoEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(GrupoEntity.class.getPackage())
                .addPackage(GrupoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    public GrupoPersistenceTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    private void clearData() {
        em.createQuery("delete from GrupoEntity").executeUpdate();
    }
    
    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            GrupoEntity entity = factory.manufacturePojo(GrupoEntity.class);
            
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
    
    /**
     * Test of create method, of class GrupoPersistence.
     */
    @Test
    public void testCreate() throws Exception {
        PodamFactory factory = new PodamFactoryImpl();
        GrupoEntity newEntity = factory.manufacturePojo(GrupoEntity.class);
        GrupoEntity result = persistence.create(newEntity);
        
        Assert.assertNotNull(result);
        GrupoEntity entity = em.find(GrupoEntity.class, result.getId());
        Assert.assertNotNull(entity);
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
    /**
     * Test of update method, of class GrupoPersistence.
     */
    @Test
    public void testUpdate() throws Exception {
        GrupoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        GrupoEntity newEntity = factory.manufacturePojo(GrupoEntity.class);
        
        newEntity.setId(entity.getId());
        
        persistence.update(newEntity);
        
        GrupoEntity resp = em.find(GrupoEntity.class, entity.getId());
        
        Assert.assertEquals(newEntity.getId(), resp.getId());
    }
    
    /**
     * Test of delete method, of class GrupoPersistence.
     */
    @Test
    public void testDelete() throws Exception {
        GrupoEntity entity = data.get(0);
        persistence.delete(entity.getId());
        GrupoEntity deleted = em.find(GrupoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    /**
     * Test of find method, of class GrupoPersistence.
     */
    @Test
    public void testFind() throws Exception {
        
        GrupoEntity entity = data.get(0);
        GrupoEntity newEntity = persistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getDescripcion(), newEntity.getDescripcion());
        Assert.assertTrue(entity.equals(newEntity));
    }
    
    /**
     * Test of findAll method, of class GrupoPersistence.
     */
    @Test
    public void testFindAll() throws Exception {
        List<GrupoEntity> list = persistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (GrupoEntity ent : list) {
            boolean found = false;
            for (GrupoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Test of findByAddress method, of class GrupoPersistence.
     */
    @Test
    public void testFindByNombre() throws Exception {
        GrupoEntity entity = data.get(0);
        GrupoEntity newEntity = persistence.findByNombre(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }
    
    @Test
    public void testAsociaciones() throws Exception
    {
        PodamFactory factory = new PodamFactoryImpl();
        CategoriaEntity categoria = factory.manufacturePojo(CategoriaEntity.class);
        UsuarioEntity miembro = factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity admin = factory.manufacturePojo(UsuarioEntity.class);
        EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
        BlogEntity blog = factory.manufacturePojo(BlogEntity.class);
        NoticiaEntity noticia= factory.manufacturePojo(NoticiaEntity.class);
        
        GrupoEntity entity = data.get(0);
        entity.setAdministradores(new ArrayList<>());
        entity.setMiembros(new ArrayList<>());
        entity.setCategorias(new ArrayList<>());
        entity.setNoticiasGrupo(new ArrayList<>());
        entity.setEventosGrupo(new ArrayList<>());
        entity.setBlogsGrupo(new ArrayList<>());
        
        entity.getCategorias().add(categoria);
        entity.getAdministradores().add(admin);
        entity.getBlogsGrupo().add(blog);
        entity.getMiembros().add(miembro);
        entity.getEventosGrupo().add(evento);
        entity.getNoticiasGrupo().add(noticia);
        
        GrupoEntity grupo= persistence.update(entity);
        
        
        Assert.assertEquals(1, grupo.getAdministradores().size());        
        Assert.assertEquals(admin.getNickname(),grupo.getAdministradores().get(0).getNickname());
        
        Assert.assertEquals(1, grupo.getMiembros().size());
        Assert.assertEquals(miembro.getNickname(),grupo.getMiembros().get(0).getNickname());
        
        Assert.assertEquals(1, grupo.getNoticiasGrupo().size());
        Assert.assertEquals(noticia.getTitulo(),grupo.getNoticiasGrupo().get(0).getTitulo());
        
        Assert.assertEquals(1, grupo.getBlogsGrupo().size());
        Assert.assertEquals(blog.getTitulo(),grupo.getBlogsGrupo().get(0).getTitulo());
        
        Assert.assertEquals(1, grupo.getCategorias().size());
        Assert.assertEquals(categoria.getTipo(),grupo.getCategorias().get(0).getTipo());
        
        Assert.assertEquals(1, grupo.getEventosGrupo().size());
        Assert.assertEquals(evento.getNombre(),grupo.getEventosGrupo().get(0).getNombre());
        
        entity.getCategorias().remove(categoria);
        entity.getAdministradores().remove(admin);
        entity.getBlogsGrupo().remove(blog);
        entity.getMiembros().remove(miembro);
        entity.getEventosGrupo().remove(evento);
        entity.getNoticiasGrupo().remove(noticia);
        
        grupo= persistence.update(entity);
        
        Assert.assertEquals(0, grupo.getAdministradores().size());
        
        Assert.assertEquals(0, grupo.getMiembros().size());
        
        Assert.assertEquals(0, grupo.getAdministradores().size());
        
        Assert.assertEquals(0, grupo.getAdministradores().size());
        
        Assert.assertEquals(0, grupo.getAdministradores().size());
        
        Assert.assertEquals(0, grupo.getAdministradores().size());
        
        Assert.assertEquals(0, grupo.getAdministradores().size());
        
        
        
    }
    
}
