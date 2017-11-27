/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.persistence.*;
import co.edu.uniandes.csw.grupos.entities.ComentarioEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
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
 * Prueba de la persistencia de noticia.
 * @author s.guzmanm
 */
@RunWith(Arquillian.class)

public class NoticiaLogicTest {
    
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
                .addPackage(NoticiaLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Logger de la lógica
     */
    private static final Logger LOGGER = Logger.getLogger(NoticiaLogicTest.class.getName());
    /**
     * Persistencia del grupo
     */
    @Inject
    private GrupoPersistence grupoPersistence;
    /**
     * Multimedia aparte de la lista
     */
    private MultimediaEntity extra;
    /**
     * Lógica de la noticia.
     */
    @Inject 
    private NoticiaLogic logic;

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
    //BeforeClass
    @BeforeClass
    public static void setUpClass() {
    }
    //AfterClass
    @AfterClass
    public static void tearDownClass() {
    }
    
    /*
     * Lista de las entidades de noticia a persistir
     */
    private List<NoticiaEntity> data = new ArrayList<>();
    /**
     * Lista de las entiades de multimedia a persistir.
     */
    private List<MultimediaEntity> dataM= new ArrayList<>();
    /**
     * Lista de usuarios a persistir.
     */
    private List<UsuarioEntity> dataU = new ArrayList<>();
    /**
     * Lista de comentarios a persistir.
     */
    private List<ComentarioEntity> dataC= new ArrayList<>();
    /**
     * Lista de grupos a persistir
     */
    private List<GrupoEntity> dataG= new ArrayList<>();
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
     * Borra la información presente en la base de datos.
     */
    private void clearData() {
        em.createQuery("delete from NoticiaEntity").executeUpdate();
        em.createQuery("delete from ComentarioEntity").executeUpdate();
        em.createQuery("delete from MultimediaEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
        em.createQuery("delete from GrupoEntity").executeUpdate();
    }

/**
 * Inserta información en la base para realizar pruebas.
 */
         private void insertData() {
        for (int i = 0; i < 3; i++) {
            NoticiaEntity entity = popularNoticia();
            em.persist(entity);
            data.add(entity);
        }
        PodamFactory factory= new PodamFactoryImpl();
        extra= factory.manufacturePojo(MultimediaEntity.class);
        while(dataM.indexOf(extra)>=0)
        {
            extra= factory.manufacturePojo(MultimediaEntity.class);
        }
        em.persist(extra);
        
    }

    /**
     * Qué se hace después en la base de datos.
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of createEntity method, of class NoticiaLogic.
     */
    @Test
    public void testCreateEntity() {
        boolean estaBien=true;
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
        NoticiaEntity test= new NoticiaEntity();
        estaBien=true;
        try
        {
            test.setInformacion(null);
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
            test.setTitulo(null);
            test.setInformacion("A");
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
            test.setTitulo("A");
            test.setAutor(null);
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
            test.setAutor(new UsuarioEntity());
            test.setGrupo(null);
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
            PodamFactory factory = new PodamFactoryImpl();
            NoticiaEntity newEntity = factory.manufacturePojo(NoticiaEntity.class);
            int indexAutor=(int)(Math.random()*dataU.size());
            newEntity.setAutor(dataU.get(indexAutor));
            int indexGrupo=(int)(Math.random()*dataG.size());
            newEntity.setGrupo(dataG.get(indexGrupo));
            ArrayList<MultimediaEntity> listM= new ArrayList<>();
            MultimediaEntity m= new MultimediaEntity();
            m.setLink("1");
            listM.add(m);
            newEntity.setMultimedia(listM);
            newEntity.setComentarios(dataC);

            NoticiaEntity result=logic.createEntity(newEntity);
            Assert.assertNotNull(result);
            NoticiaEntity found=em.find(NoticiaEntity.class, newEntity.getId());
            Assert.assertNotNull(found);
            Assert.assertEquals(newEntity,result);
            verificarRelaciones(newEntity,found);
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
     * Test of updateEntity method, of class NoticiaLogic.
     */
    @Test
    public void testUpdateEntity() {
        
        boolean estaBien=true;
        try
        {
            logic.updateEntity(1l,null);
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
            logic.updateEntity(null,new NoticiaEntity());
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
            NoticiaEntity test=new NoticiaEntity();
            test.setId(1l);
            while(data.indexOf(test)>=0)
            {
                test.setId((long)(Math.random()*100000));
            }
            test.setAutor(dataU.get(0));
            test.setGrupo(dataG.get(0));
            test.setInformacion("A");
            test.setTitulo("A");
            logic.updateEntity(test.getId(),test);
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
            PodamFactory factory = new PodamFactoryImpl();
            NoticiaEntity entity=data.get(0);
            NoticiaEntity updated=factory.manufacturePojo(NoticiaEntity.class);
            Long id=entity.getId();

           updated.setId(id);
           updated.setAutor(entity.getAutor());
           updated.setGrupo(entity.getGrupo());
           updated.setMultimedia(dataM);
           updated.setComentarios(dataC);
            logic.updateEntity(id,updated);

            NoticiaEntity rta= em.find(NoticiaEntity.class, id);
            Assert.assertEquals(updated.getId(),rta.getId());
            verificarRelaciones(updated,rta);
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
     * Test of find method, of class NoticiaLogic.
     */
    @Test
    public void testFind() {
        boolean estaBien=true;
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
            NoticiaEntity test=new NoticiaEntity();
            test.setId(1l);
            while(data.indexOf(test)>=0)
            {
                test.setId((long)(Math.random()*100000));
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
            NoticiaEntity entity=data.get(0);
             Long id=entity.getId();
            NoticiaEntity found=logic.getEntity(id);
            Assert.assertNotNull(found);
            Assert.assertEquals(entity.getId(),found.getId());
            verificarRelaciones(entity,found);        }
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
     * Test of findAll method, of class NoticiaLogic.
     */
    @Test
    public void testFindAll() {
        
        List<NoticiaEntity> list=logic.getAll();
        Assert.assertEquals(data.size(),list.size());
        boolean found=false;
        for(NoticiaEntity e: list)
        {
            found=false;
            for(NoticiaEntity e2: data)
            {
                if(e2.getId().equals(e.getId()) /* && e2.getId().getAutor().equals(e.getId().getAutor())*/)
                {
                    found=true;
                    break;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    
    /**
     * Test of delete method, of class NoticiaLogic.
     */
    @Test
    public void testDelete() {
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
            NoticiaEntity test=new NoticiaEntity();
            test.setId(1l);
            while(data.indexOf(test)>=0)
            {
                test.setId((long)(Math.random()*100000));
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
            NoticiaEntity entity= data.get(0);
            Long id=entity.getId();
           logic.deleteEntity(id);
           NoticiaEntity deleted= em.find(NoticiaEntity.class,id);
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
   
    /**
     * Popula una noticia con datos aleatorios del podam factory.
     * @return 
     */
    private NoticiaEntity popularNoticia() {
        PodamFactory factory= new PodamFactoryImpl();
        NoticiaEntity e=factory.manufacturePojo(NoticiaEntity.class);
        List<MultimediaEntity> list= new ArrayList<>();
        List<ComentarioEntity> com= new ArrayList<>();
        //Popula subrecursos
        for(int i=0;i<3;i++)
        {
            MultimediaEntity multimedia=factory.manufacturePojo(MultimediaEntity.class);
            list.add(multimedia);
            dataM.add(multimedia);
            em.persist(multimedia);
            
            ComentarioEntity c= factory.manufacturePojo(ComentarioEntity.class);
            com.add(c);
            dataC.add(c);
            em.persist(c);
        }
        e.setMultimedia(list);
        e.setComentarios(com);
        //Agrega un usuario
        UsuarioEntity usuario=factory.manufacturePojo(UsuarioEntity.class);
        e.setAutor(usuario);
        while(e.getAutor()==null)
        {
            
            e.setAutor(factory.manufacturePojo(UsuarioEntity.class));
            usuario=e.getAutor();
        }
        dataU.add(usuario);
        em.persist(usuario);
        //Agrega un grupo
        GrupoEntity g=null;
        while(e.getGrupo()==null)
        {
            e.setGrupo(factory.manufacturePojo(GrupoEntity.class));
            g=e.getGrupo();
        }
        dataG.add(g);
        em.persist(g);
        return e;
    }
    /**
     * Verifica las relaciones existentes entre las dos entidades. La entidad antigua, y la nueva.
     * @param entidadAntigua Entidad antigua
     * @param entidadNueva Entidad nueva 
     */
    private void verificarRelaciones(NoticiaEntity entidadAntigua, NoticiaEntity entidadNueva) {
        //Verifica el autor
        Assert.assertNotNull(entidadNueva.getAutor());
        Assert.assertEquals(entidadAntigua.getAutor().getId(),entidadNueva.getAutor().getId());
        //Verifica el grupo
        Assert.assertNotNull(entidadNueva.getGrupo());
        Assert.assertEquals(entidadAntigua.getGrupo().getId(),entidadNueva.getGrupo().getId());
        //Verifica la multimedia
        Assert.assertNotNull(entidadNueva.getMultimedia());
        boolean aceptado;
        for(int i=0;i<entidadAntigua.getMultimedia().size();i++)
        {
            aceptado=false;
            for(MultimediaEntity m: entidadNueva.getMultimedia())
            {
                if(entidadAntigua.getMultimedia().get(i).getLink().equals(m.getLink())) aceptado=true;
            }
            if(!aceptado) Assert.fail("No existe la multimedia buscada");
        }
        //Verifica los comentarios
        Assert.assertNotNull(entidadNueva.getComentarios());
        for(int i=0;i<entidadAntigua.getComentarios().size();i++)
        {
            aceptado=false;
            for(ComentarioEntity m: entidadNueva.getComentarios())
            {
                if(entidadAntigua.getComentarios().get(i).getId().equals(m.getId())) aceptado=true;
            }
            if(!aceptado) Assert.fail("No existe el comentario buscado");
        }
    }
    /**
     * Test of getMultimedia
     */
    @Test
    public void testMultimediaList()
    {
        boolean estaBien=true;
        try
        {
            logic.getMultimedia(null);
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
            NoticiaEntity test=new NoticiaEntity();
            test.setId(1l);
            while(data.indexOf(test)>=0)
            {
                test.setId((long)(Math.random()*100000));
            }
            logic.getMultimedia(test.getId());
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
            NoticiaEntity entity=data.get(0);
             Long id=entity.getId();
            List<MultimediaEntity> list=logic.getMultimedia(id);
            for(MultimediaEntity m:list)
            {
                Assert.assertTrue(dataM.indexOf(m)>=0);
            }
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
     * Test of getMultimedia
     */
    @Test
    public void testMultimedia()
    {
        boolean estaBien=true;
        try
        {
            logic.getMultimedia(null,"A");
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
            NoticiaEntity test=new NoticiaEntity();
            test.setId(1l);
            while(data.indexOf(test)>=0)
            {
                test.setId((long)(Math.random()*100000));
            }
            logic.getMultimedia(test.getId(),"A");
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
            PodamFactory factory= new PodamFactoryImpl();
            NoticiaEntity entity=data.get(0);
             Long id=entity.getId();
             MultimediaEntity e= factory.manufacturePojo(MultimediaEntity.class);
             while(dataM.indexOf(e)>=0)
             {
                 e= factory.manufacturePojo(MultimediaEntity.class);
             }
            logic.getMultimedia(id,e.getLink());
        }
        catch(BusinessException e)
        {
            LOGGER.info(e.getMessage());
            estaBien=true;
        }
        catch(EJBException e)
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
            NoticiaEntity entity=data.get(0);
             Long id=entity.getId();
             
            logic.getMultimedia(id,extra.getLink());
        }
        catch(BusinessException e)
        {
            LOGGER.info(e.getMessage());
            estaBien=true;
        }
        catch(EJBException e)
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
            NoticiaEntity entity=data.get(0);
             Long id=entity.getId();
            logic.getMultimedia(id,dataM.get(0).getLink());
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
     * Test of addMultimedia
     */
    @Test
    public void testCreateMultimedia()
    {
        boolean estaBien=true;
        try
        {
            List<MultimediaEntity> anterior= data.get(1).getMultimedia();
            ArrayList<MultimediaEntity> list = new ArrayList<>();
            list.add(extra);
            List<MultimediaEntity> test=logic.addMultimedia(data.get(1).getId(),list);
            for(MultimediaEntity m:anterior)
            {
                Assert.assertTrue(test.indexOf(m)>=0);
            }
            Assert.assertTrue(test.indexOf(extra)>=0);
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
     * Test of updateMultimedia
     */
    @Test
    public void testUpdateMultimedia()
    {
        boolean estaBien=true;
        try
        {
            logic.updateMultimedia(null,null,"A");
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
            NoticiaEntity test=new NoticiaEntity();
            test.setId(1l);
            while(data.indexOf(test)>=0)
            {
                test.setId((long)(Math.random()*100000));
            }
            logic.updateMultimedia(test.getId(),null,"A");
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
            PodamFactory factory= new PodamFactoryImpl();
            NoticiaEntity entity=data.get(0);
             Long id=entity.getId();
             MultimediaEntity e= factory.manufacturePojo(MultimediaEntity.class);
             while(dataM.indexOf(e)>=0)
             {
                 e= factory.manufacturePojo(MultimediaEntity.class);
             }
            logic.updateMultimedia(id,null,e.getLink());
        }
        catch(BusinessException e)
        {
            LOGGER.info(e.getMessage());
            estaBien=true;
        }
        catch(EJBException e)
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
            NoticiaEntity entity=data.get(1);
             Long id=entity.getId();
             
            logic.updateMultimedia(id,null,extra.getLink());
        }
        catch(BusinessException e)
        {
            LOGGER.info(e.getMessage());
            estaBien=true;
        }
        catch(EJBException e)
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
            NoticiaEntity entity=data.get(0);
             Long id=entity.getId();
            PodamFactory factory = new PodamFactoryImpl();
            MultimediaEntity mult=factory.manufacturePojo(MultimediaEntity.class);
            mult.setLink(dataM.get(0).getLink());
            logic.updateMultimedia(id,mult,dataM.get(0).getLink());
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
     * Test of deleteMultimedia
     */
    @Test
    public void testDeleteMultimedia()
    {
        boolean estaBien=true;
        try
        {
            logic.deleteMultimedia(null,"A");
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
            NoticiaEntity test=new NoticiaEntity();
            test.setId(1l);
            while(data.indexOf(test)>=0)
            {
                test.setId((long)(Math.random()*100000));
            }
            logic.deleteMultimedia(test.getId(),"A");
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
            PodamFactory factory= new PodamFactoryImpl();
            NoticiaEntity entity=data.get(0);
             Long id=entity.getId();
             MultimediaEntity e= factory.manufacturePojo(MultimediaEntity.class);
             while(dataM.indexOf(e)>=0)
             {
                 e= factory.manufacturePojo(MultimediaEntity.class);
             }
            logic.deleteMultimedia(id,e.getLink());
        }
        catch(BusinessException e)
        {
            LOGGER.info(e.getMessage());
            estaBien=true;
        }
        catch(EJBException e)
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
            NoticiaEntity entity=data.get(1);
             Long id=entity.getId();
             
            logic.deleteMultimedia(id,extra.getLink());
        }
        catch(BusinessException e)
        {
            LOGGER.info(e.getMessage());
            estaBien=true;
        }
        catch(EJBException e)
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
            NoticiaEntity entity=data.get(0);
             Long id=entity.getId();
            logic.deleteMultimedia(id,dataM.get(0).getLink());
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
     * Test of findAll method, of class NoticiaLogic.
     */
    @Test
    public void testFindAllEmpty() {
        try {
            utx.begin();
            clearData();
            List<NoticiaEntity> list=logic.getAll();
            Assert.assertEquals(0,list.size());
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
    
    
    
    
}
