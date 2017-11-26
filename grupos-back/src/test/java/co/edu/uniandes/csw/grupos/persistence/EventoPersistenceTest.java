/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.EventoEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.entities.LugarEntity;
import co.edu.uniandes.csw.grupos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import co.edu.uniandes.csw.grupos.persistence.EventoPersistence;
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
 * @author js.ramos14
 */
@RunWith(Arquillian.class)
public class EventoPersistenceTest {
    
    /**
     * Persistencia de evento
     */
    @Inject
    private EventoPersistence eventoPersistence;
    
    /**
     * EntityManager
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Maneja las transacciones
     */
    @Inject
    UserTransaction utx;
    
    /**
     * Lista que guarda las entidades de Evento fuera de la base de datos
     */
    private List<EventoEntity> data = new ArrayList<>();
    
    /**
     * Lista que guarda las entidades de Grupo fuera de la base de datos
     */
    private List<GrupoEntity> dataG = new ArrayList<>();
    
    /**
     * Lista que guarda las entidades de Lugar fuera de la base de datos
     */
    private List<LugarEntity> dataL = new ArrayList<>();
    
    /**
     * Lista que guarda las entidades de Usuario fuera de la base de datos
     */
    private List<UsuarioEntity> dataU = new ArrayList<>();
    
    /**
     * Lista que guarda las entidades de Patrocinio fuera de la base de datos
     */
    private List<PatrocinioEntity> dataP = new ArrayList<>();
    
    /**
     * Deployment.
     * @return un JavaArchive
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Construye un test vacío.
     */
    public EventoPersistenceTest()
    {
    }
    
    /**
     * ejecutado antes de todo.
     */
    @BeforeClass
    public static void setUpClass() {
    }
    
    /**
     * ejecutado después de todo.
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Limpia la base de datos.
     */
    private void clearData() {
        em.createQuery("delete from PatrocinioEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
        em.createQuery("delete from GrupoEntity").executeUpdate();
        em.createQuery("delete from LugarEntity").executeUpdate();
    }
    
    /**
     * inserta los datos en las estructuras (listas) y en la base de datos.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        GrupoEntity grupo = factory.manufacturePojo(GrupoEntity.class);
        em.persist(grupo);
        dataG.add(grupo);
        LugarEntity lugar = factory.manufacturePojo(LugarEntity.class);
        em.persist(lugar);
        dataL.add(lugar);
        UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
        usuario.setEventos(new ArrayList<>());
        em.persist(usuario);
        dataU.add(usuario);
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            entity.setUsuarios(new ArrayList<>());
            entity.setPatrocinios(new ArrayList<>());
            em.persist(entity);
            data.add(entity);
            PatrocinioEntity patrocinio = factory.manufacturePojo(PatrocinioEntity.class);
            patrocinio.setUsuario(usuario);
            patrocinio.setEvento(entity);
            entity.getPatrocinios().add(patrocinio);
            em.persist(patrocinio);
            dataP.add(patrocinio);

        }
        EventoEntity evento = data.get(0);
        usuario.getEventos().add(evento);
        evento.setGrupo(grupo);
        evento.setLugar(lugar);
        evento.setUsuarios(dataU);
        data.set(0, evento);
        
    }
    
    /**
     * setUp ejecutado antes de cada prueba.
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
     * tearDown ejecutado después de cada prueba.
     */
    @After
    public void tearDown() {
    }

    /**
     * Test de la creación de las entidades.
     * @throws Exception 
     */
    @Test
    public void createEventoTest() throws Exception{
        PodamFactory factory = new PodamFactoryImpl();
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        EventoEntity result = eventoPersistence.create(newEntity);

        Assert.assertNotNull(result);

        EventoEntity entity = em.find(EventoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertNull(newEntity.getGrupo());
        
        entity.setGrupo(dataG.get(0));
        entity = em.find(EventoEntity.class, result.getId());
        
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getGrupo(), entity.getGrupo());
    }
    
    /**
     * Test del método findAll de la persistencia.
     * @throws Exception 
     */
    @Test
    public void getEventosTest() throws Exception{
        List<EventoEntity> list = eventoPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (EventoEntity ent : list) {
            boolean found = false;
            for (EventoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * test del método find de la persistencia.
     * @throws Exception 
     */
    @Test
    public void getEventoTest() throws Exception{
        EventoEntity entity = data.get(0);
        EventoEntity newEntity = eventoPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getPatrocinios().get(0), dataP.get(0));
        Assert.assertEquals(newEntity.getGrupo(), dataG.get(0));
        Assert.assertEquals(newEntity.getLugar(), dataL.get(0));
        Assert.assertEquals(newEntity.getUsuarios().get(0), dataU.get(0));
    }

    /**
     * test del método delete de la persistencia.
     * @throws Exception 
     */
    @Test
    public void deleteEventoTest() throws Exception{
        EventoEntity entity = data.get(0);
        eventoPersistence.delete(entity.getId());
        EventoEntity deleted = em.find(EventoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * test del método update de la persistencia.
     * @throws Exception 
     */
    @Test
    public void updateEventoTest() throws Exception{
        EventoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);

        newEntity.setId(entity.getId());
        newEntity.setGrupo(entity.getGrupo());
        newEntity.setLugar(entity.getLugar());
        newEntity.setPatrocinios(entity.getPatrocinios());
        newEntity.setUsuarios(entity.getUsuarios());

        eventoPersistence.update(newEntity);

        EventoEntity resp = em.find(EventoEntity.class, entity.getId());

        Assert.assertEquals(resp.getNombre(), newEntity.getNombre());
        Assert.assertEquals(resp.getGrupo(), newEntity.getGrupo());
        Assert.assertEquals(resp.getPatrocinios().get(0), dataP.get(0));
        Assert.assertEquals(resp.getLugar(), dataL.get(0));
        Assert.assertEquals(resp.getUsuarios().get(0), dataU.get(0));
    }
}
