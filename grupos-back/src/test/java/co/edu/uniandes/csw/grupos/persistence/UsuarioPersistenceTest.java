/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import co.edu.uniandes.csw.grupos.entities.TarjetaEntity;
import co.edu.uniandes.csw.grupos.entities.EmpresaEntity;
import co.edu.uniandes.csw.grupos.entities.EventoEntity;
import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.entities.PatrocinioEntity;
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
 * Pruebas de usuario
 * @author tefa
 */
@RunWith(Arquillian.class)
public class UsuarioPersistenceTest {
    //Deployment
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Inyeccines de la dependencia a la clase UsuarioPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private UsuarioPersistence persistence;
    
    @Inject
    private GrupoPersistence persistenceGrupo;
    
    @Inject
    private PatrocinioPersistence persistencePatrocinio;
    
    @Inject
    private NoticiaPersistence persistenceNoticia;
    
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
     * Datos
     */
    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();
    /**
     * Constructor vacío
     */
    public UsuarioPersistenceTest() {
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
    /**
     * Borra los datos del sistema
     */
    private void clearData() {
        em.createQuery("delete from PatrocinioEntity").executeUpdate();
        em.createQuery("delete from NoticiaEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }
    
    /**
     * Inserta datos
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            
            em.persist(entity);
            data.add(entity);
        }
    }
    //tearDown
    @After
    public void tearDown() {
    }
    /**
     * Test para crear un usuario
     */
    @Test
    public void createUsuarioEntityTest() {
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity result = persistence.createEntity(newEntity);
        
        assertNotNull(result);
        UsuarioEntity entity = em.find(UsuarioEntity.class, result.getId());
        assertNotNull(entity);
        assertEquals(newEntity.getId(), entity.getId());
    }
    /**
     * Test para obtener usuarios
     */
    @Test
    public void getUsuariosTest() {
        List<UsuarioEntity> list = persistence.findAll();
        assertEquals(data.size(), list.size());
        for (UsuarioEntity ent : list) {
            boolean found = false;
            for (UsuarioEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }
    /**
     * Test para obtener un usuario
     */
    @Test
    public void getUsuarioTest() {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity newEntity = persistence.find(entity.getId());
        assertNotNull(newEntity);
        assertEquals(entity.getId(), newEntity.getId());
    }
    /**
     * Test para obtener por nombre
     */
    @Test
    public void getUsuarioPorNombreTest() {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity newEntity = persistence.findByName(entity.getNombre());
        assertNotNull(newEntity);
        assertEquals(entity.getNombre(), newEntity.getNombre());
    }
    /**
     * Test para actualizar un usuario
     */
    @Test
    public void updateUsuarioTest() {
        UsuarioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        
        newEntity.setId(entity.getId());
        
        persistence.updateEntity(newEntity);
        
        UsuarioEntity resp = em.find(UsuarioEntity.class, entity.getId());
        
        assertEquals(newEntity.getId(), resp.getId());
    }
    /**
     * Test para borrar un usuario
     */
    @Test
    public void deleteUsuarioTest() {
        UsuarioEntity entity = data.get(0);
        persistence.delete(entity.getId());
        UsuarioEntity deleted = em.find(UsuarioEntity.class, entity.getId());
        assertNull(deleted);
    }
    
    /**
     * Prueba las relaciones de usuarios a otras entidades
     */
    @Test
    public void testAsociaciones()
    {
        //Crea entidades con podam
        PodamFactory factory = new PodamFactoryImpl();
        EmpresaEntity empresa = factory.manufacturePojo(EmpresaEntity.class);
        GrupoEntity miembro = factory.manufacturePojo(GrupoEntity.class);
        GrupoEntity admin = factory.manufacturePojo(GrupoEntity.class);
        EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
        NoticiaEntity noticia = factory.manufacturePojo(NoticiaEntity.class);
        TarjetaEntity tarjeta= factory.manufacturePojo(TarjetaEntity.class);
        BlogEntity blog = factory.manufacturePojo(BlogEntity.class);
        PatrocinioEntity patrocinio = factory.manufacturePojo(PatrocinioEntity.class);
        
        UsuarioEntity entity = data.get(0);
        //Inicialización de datos
        entity.setEmpresa(null);
        entity.setBlogsFavoritos(new ArrayList<>());
        entity.setEmpresa(empresa);
        entity.setEventos(new ArrayList<>());
        entity.setGrupos(new ArrayList<>());
        entity.setGruposAdmin(new ArrayList<>());
        entity.setNoticias(new ArrayList<>());
        entity.setTarjetas(new ArrayList<>());
        entity.setPatrocinios(new ArrayList<>());
        
        entity.getBlogsFavoritos().add(blog);
        entity.getTarjetas().add(tarjeta);
        entity.getEventos().add(evento);
        admin.getAdministradores().add(entity);
        miembro.getMiembros().add(entity);
        noticia.setAutor(entity);
        patrocinio.setUsuario(entity);
        entity.getPatrocinios().add(patrocinio);
        
        //Persistir los datos
        miembro=persistenceGrupo.update(miembro);
        admin=persistenceGrupo.update(admin);
        patrocinio= persistencePatrocinio.updateEntity(patrocinio);
        noticia= persistenceNoticia.updateEntity(noticia);
        entity= persistence.updateEntity(entity);
        entity= persistence.find(entity.getId());
        Assert.assertEquals(1, entity.getGruposAdmin().size());
        //Aserciones
        Assert.assertEquals(admin.getNombre(),entity.getGruposAdmin().get(0).getNombre());
        
        Assert.assertEquals(1, entity.getGrupos().size());
        Assert.assertEquals(miembro.getNombre(),entity.getGrupos().get(0).getNombre());
        
        Assert.assertEquals(1, entity.getNoticias().size());
        Assert.assertEquals(noticia.getTitulo(),entity.getNoticias().get(0).getTitulo());
        
        Assert.assertEquals(1, entity.getBlogsFavoritos().size());
        Assert.assertEquals(blog.getTitulo(),entity.getBlogsFavoritos().get(0).getTitulo());
        
        Assert.assertEquals(1, entity.getPatrocinios().size());
        Assert.assertEquals(patrocinio.getPago(),entity.getPatrocinios().get(0).getPago(), 0.00001);
        
        Assert.assertEquals(1, entity.getEventos().size());
        Assert.assertEquals(evento.getNombre(),entity.getEventos().get(0).getNombre());
        
        Assert.assertEquals(empresa.getNombre(),entity.getEmpresa().getNombre());
        
        Assert.assertEquals(1, entity.getTarjetas().size());
        Assert.assertEquals(tarjeta.getBanco(),entity.getTarjetas().get(0).getBanco());
        
        
        miembro.getMiembros().remove(entity);
        admin.getAdministradores().remove(entity);
        entity.getBlogsFavoritos().remove(entity.getBlogsFavoritos().get(0));
        //Remueve entidades
        entity.getPatrocinios().remove(entity.getPatrocinios().get(0));
        entity.getEventos().remove(entity.getEventos().get(0));
        entity.getTarjetas().remove(entity.getTarjetas().get(0));
        noticia.setAutor(null);
        patrocinio.setUsuario(null);
        entity.setEmpresa(null);
        
        miembro = persistenceGrupo.update(miembro);
        admin = persistenceGrupo.update(admin);
        noticia= persistenceNoticia.updateEntity(noticia);
        patrocinio= persistencePatrocinio.updateEntity(patrocinio);
        entity= persistence.updateEntity(entity);
        entity= persistence.find(entity.getId());

        //Aserciones
        
        Assert.assertEquals(0, entity.getBlogsFavoritos().size());
        
        Assert.assertEquals(0, entity.getGrupos().size());
        
        Assert.assertEquals(0, entity.getGruposAdmin().size());
        
        Assert.assertEquals(0, entity.getEventos().size());
        
        Assert.assertEquals(0, entity.getNoticias().size());
        
        Assert.assertEquals(0, entity.getTarjetas().size());
        
        Assert.assertEquals(0, entity.getPatrocinios().size());
        
        Assert.assertNull(entity.getEmpresa());
        
        
    }
}
