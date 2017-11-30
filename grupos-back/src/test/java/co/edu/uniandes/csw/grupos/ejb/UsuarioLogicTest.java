/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.entities.EmpresaEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.grupos.entities.TarjetaEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.UsuarioPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.NotFoundException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author se.cardenas
 */
@RunWith(Arquillian.class)
public class UsuarioLogicTest {
    
    @Inject
    private UsuarioLogic logic;
    
    @Inject
    UserTransaction utx;
    
    /**
     * Manejador de entidades
     */
    @PersistenceContext
    private EntityManager em;
    
    private List<UsuarioEntity> data = new ArrayList<>();
    
    private List<TarjetaEntity> dataT = new ArrayList<>();
    
    private List<NoticiaEntity> dataN = new ArrayList<>();
    
    private List<PatrocinioEntity> dataP = new ArrayList<>();
    
    private List<BlogEntity> dataB = new ArrayList<>();
    
    private GrupoEntity g;
    
    private EmpresaEntity e;
    
    
    
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
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
                .addPackage(UsuarioLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
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
    
    /**
     * Borra toda la información del sistema.
     */
    private void clearData() {
        em.createQuery("delete from NoticiaEntity").executeUpdate();
        em.createQuery("delete from BlogEntity").executeUpdate();
        em.createQuery("delete from PatrocinioEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
        em.createQuery("delete from TarjetaEntity").executeUpdate();
        em.createQuery("delete from EmpresaEntity").executeUpdate();
        em.createQuery("delete from GrupoEntity").executeUpdate();
    }
    
    /**
     * 
     * 
     * Inserta datos en el sistema para hacer las pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        g = factory.manufacturePojo(GrupoEntity.class);
        em.persist(g);
        for(int i = 0; i<3; i++) {
            BlogEntity blog = factory.manufacturePojo(BlogEntity.class);
            TarjetaEntity tarjeta = factory.manufacturePojo(TarjetaEntity.class);
            tarjeta.setNumero(darNumTarjetaNoUsado());
            blog.setGrupo(g);
            em.persist(tarjeta);
            em.persist(blog);
            dataT.add(tarjeta);
            dataB.add(blog);
        }
        for(int i = 0; i<3; i++) {
            UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
            usuario.setBlogsFavoritos(dataB);
            usuario.setTarjetas(dataT);
            usuario.setEmpresa(null);
            em.persist(usuario);
            data.add(usuario);
        }
        for(int i = 0; i<3; i++) {
            NoticiaEntity noticia = factory.manufacturePojo(NoticiaEntity.class);
            PatrocinioEntity patrocinio = factory.manufacturePojo(PatrocinioEntity.class);
            noticia.setAutor(data.get(0));
            patrocinio.setUsuario(data.get(0));
            em.persist(noticia);
            em.persist(patrocinio);
            dataN.add(noticia);
            dataP.add(patrocinio);
        }
        e = factory.manufacturePojo(EmpresaEntity.class);
    }
    
    @Test
    public void testGetUsuarios() {
        compararListas(data, logic.allUsers());
        
        UsuarioEntity usuario = logic.findByEmail(data.get(0).getEmail());
        Assert.assertEquals(usuario, data.get(0));
        Assert.assertEquals(usuario.getEmail(), data.get(0).getEmail());
        
        PodamFactory factory = new PodamFactoryImpl();
        usuario = factory.manufacturePojo(UsuarioEntity.class);
        usuario.setEmail(null);
        try {
            logic.findUserEmailPassword(usuario);
            Assert.fail();
        }
        catch(BusinessException e) {
            //no pasa nada
        }
        usuario = factory.manufacturePojo(UsuarioEntity.class);
        usuario.setPassword(null);
        try {
            logic.findUserEmailPassword(usuario);
            Assert.fail();
        }
        catch(BusinessException e) {
            //no pasa nada
        }
        usuario = factory.manufacturePojo(UsuarioEntity.class);
        usuario.setEmail(data.get(0).getEmail());
        usuario.setPassword(data.get(0).getPassword());
        try {
            Assert.assertEquals(data.get(0), logic.findUserEmailPassword(usuario));
        }
        catch(BusinessException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testCreateUsuario() {
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
        usuario.setEmail(null);
        try {
            logic.createUser(usuario);
            Assert.fail();
        }
        catch(BusinessException e) {
            //no pasa nada
        }
        usuario = factory.manufacturePojo(UsuarioEntity.class);
        usuario.setNickname(null);
        try {
            logic.createUser(usuario);
            Assert.fail();
        }
        catch(BusinessException e) {
            //no pasa nada
        }
        usuario = factory.manufacturePojo(UsuarioEntity.class);
        usuario.setEmail(data.get(0).getEmail());
        try {
            logic.createUser(usuario);
            Assert.fail();
        }
        catch(BusinessException e) {
            //no pasa nada
        }
        usuario = factory.manufacturePojo(UsuarioEntity.class);
        usuario.setNickname(data.get(0).getNickname());
        try {
            logic.createUser(usuario);
            Assert.fail();
        }
        catch(BusinessException e) {
            //no pasa nada
        }
        usuario = factory.manufacturePojo(UsuarioEntity.class);
        usuario.setEmail(darEmailNoUsado());
        usuario.setNickname(darNicknameNoUsado());
        try {
            usuario = logic.createUser(usuario);
            data.add(usuario);
        }
        catch(BusinessException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testUpdateUsuario() {
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
        try {
            logic.updateUser(darIdNoUsado(), usuario);
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        catch(BusinessException e) {
            Assert.fail();
        }
        usuario = factory.manufacturePojo(UsuarioEntity.class);
        usuario.setEmail(data.get(0).getEmail());
        try {
            logic.updateUser(data.get(data.size()-1).getId(), usuario);
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            Assert.fail();
        }
        catch(BusinessException e) {
            //no pasa nada
        }
        usuario = factory.manufacturePojo(UsuarioEntity.class);
        usuario.setNickname(data.get(0).getNickname());
        try {
            logic.updateUser(data.get(data.size()-1).getId(), usuario);
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            Assert.fail();
        }
        catch(BusinessException e) {
            //no pasa nada
        }
    }
    
    @Test
    public void testGetTarjetas() {
        compararListas(dataT, logic.listTarjetas(data.get(0).getId()));
        
        Assert.assertNull(logic.getTarjeta(data.get(0).getId(), darNumTarjetaNoUsado()));
        Assert.assertEquals(dataT.get(0), logic.getTarjeta(data.get(0).getId(), dataT.get(0).getNumero()));
    }
    
    @Test
    public void testAddTarjetas() {
        PodamFactory factory = new PodamFactoryImpl();
        TarjetaEntity tarjeta = factory.manufacturePojo(TarjetaEntity.class);
        tarjeta.setNumero(darNumTarjetaNoUsado());
        try {
            tarjeta = logic.addTarjeta(data.get(0).getId(), tarjeta);
            Assert.assertEquals(tarjeta, logic.getTarjeta(data.get(0).getId(), tarjeta.getNumero()));
            dataT.add(tarjeta);
        }
        catch(BusinessException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testUpdateTarjeta() {
        PodamFactory factory = new PodamFactoryImpl();
        TarjetaEntity tarjeta = factory.manufacturePojo(TarjetaEntity.class);
        tarjeta.setNumero(dataT.get(dataT.size()-1).getNumero());
        TarjetaEntity newTarjeta = logic.updateTarjeta(data.get(0).getId(), tarjeta);
        Assert.assertEquals(tarjeta, newTarjeta);
        Assert.assertEquals(tarjeta.getBanco(), newTarjeta.getBanco());
        Assert.assertEquals(tarjeta.getDineroDisponible(), newTarjeta.getDineroDisponible(), 0.001);
        Assert.assertEquals(tarjeta.getMaxCupo(), newTarjeta.getMaxCupo(), 0.001);
    }
    
    @Test
    public void testEmpresa() {
        Assert.assertNull(logic.getEmpresa(data.get(0).getId()));
        
        
        try {
            logic.addEmpresa(darIdNoUsado(), e);
            Assert.fail();
        }
        catch(BusinessException e) {
            //no pasa nada
        }
        try {
            EmpresaEntity newEmpresa = logic.addEmpresa(data.get(0).getId(), e);
            Assert.assertEquals(e, newEmpresa);
            Assert.assertEquals(e, logic.getEmpresa(data.get(0).getId()));
        }
        catch(BusinessException e) {
            //
        }
        
        PodamFactory factory = new PodamFactoryImpl();
        EmpresaEntity empresa = factory.manufacturePojo(EmpresaEntity.class);
        empresa.setNit(e.getNit());
        EmpresaEntity newEmpresa = logic.updateEmpresa(data.get(0).getId(), empresa);
        Assert.assertEquals(empresa, newEmpresa);
        Assert.assertEquals(empresa.getLogo(), newEmpresa.getLogo());
        Assert.assertEquals(empresa.getNombre(), newEmpresa.getNombre());
    }
    
    @Test
    public void testGetNoticias() {
        try {
            logic.getNoticias(darIdNoUsado());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        try {
            compararListas(dataN, logic.getNoticias(data.get(0).getId()));
        }
        catch(NotFoundException | EJBException e) {
            Assert.fail();
        }
        
        
        try {
            logic.getNoticia(darIdNoUsado(), dataN.get(0).getId());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            Assert.fail();
        }
        catch(BusinessException e1) {
            //no pasa nada
        }
        try {
            logic.getNoticia(data.get(0).getId(), darIdNoticiaNoUsado());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        catch(BusinessException e1) {
            Assert.fail();
        }
        try {
            logic.getNoticia(data.get(0).getId(), darIdNoticiaNoUsado());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        catch(BusinessException e1) {
            Assert.fail();
        }
        try {
            NoticiaEntity noticia = logic.getNoticia(data.get(0).getId(), dataN.get(0).getId());
            Assert.assertEquals(dataN.get(0), noticia);
            
        }
        catch(NotFoundException | EJBException | BusinessException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testAddNoticia() {
        PodamFactory factory = new PodamFactoryImpl();
        NoticiaEntity noticia = factory.manufacturePojo(NoticiaEntity.class);
        try {
            logic.addNoticia(darIdNoUsado(), noticia);
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            Assert.fail();
        }
        catch(BusinessException e1) {
            //no pasa nada
        }
        try {
            noticia.setAutor(data.get(1));
            noticia = logic.addNoticia(data.get(1).getId(), noticia);
            dataN.add(noticia);
            
        }
        catch(NotFoundException | EJBException | BusinessException e) {
            //
        }
    }
    
    @Test
    public void testUpdateNoticia() {
        PodamFactory factory = new PodamFactoryImpl();
        NoticiaEntity noticia = factory.manufacturePojo(NoticiaEntity.class);
        noticia.setAutor(data.get(0));
        try {
            logic.updateNoticia(darIdNoUsado(), dataN.get(dataN.size()-1).getId(), noticia);
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            Assert.fail();
        }
        catch(BusinessException e1) {
            //no pasa nada
        }
        
        try {
            logic.updateNoticia(data.get(1).getId(), dataN.get(dataN.size()-1).getId(), noticia);
            Assert.fail();
        }
        catch(NotFoundException | EJBException | BusinessException e) {
            //no pasa nada
        }
        
        try {
            logic.updateNoticia(data.get(0).getId(), darIdNoticiaNoUsado(), noticia);
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        catch(BusinessException e1) {
            Assert.fail();
        }
        
        try {
            
            logic.updateNoticia(data.get(0).getId(), dataN.get(dataN.size()-1).getId(), noticia);
        }
        catch(NotFoundException | EJBException | BusinessException e) {
            //
        }
    }
    
    @Test
    public void testRemoveNoticia() {
        
    }
    
    
    private void compararListas(List list1, List list2) {
        Assert.assertEquals(list1.size(), list2.size());
        for(int i = 0; i<list1.size(); i++) {
            Assert.assertTrue(list2.indexOf(list1.get(i))>=0);
        }
        
        for(int i = 0; i<list1.size(); i++) {
            Assert.assertTrue(list1.indexOf(list2.get(i))>=0);
        }
    }
    
    private Long darIdNoUsado() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId((long)0);
        while(data.indexOf(entity)>=0) {
            entity.setId((long)((Math.random())*100));
        }
        return entity.getId();
    }
    
    private Long darIdNoticiaNoUsado() {
        NoticiaEntity entity = new NoticiaEntity();
        entity.setId((long)0);
        while(dataN.indexOf(entity)>=0) {
            entity.setId((long)((Math.random())*100));
        }
        return entity.getId();
    }
    
    private Integer darNumTarjetaNoUsado() {
        TarjetaEntity entity = new TarjetaEntity();
        entity.setNumero(1);
        while(dataT.indexOf(entity)>=0) {
            entity.setNumero((int)((Math.random())*100));
        }
        return entity.getNumero();
    }
    
    private String darNicknameNoUsado() {
        String nickname = "nick";
        boolean band = false;
        while(!band) {
            band = true;
            for(UsuarioEntity u : data) {
                if(nickname.equals(u.getNickname())) {
                    band = false;
                    nickname += "a";
                }
            }
        }
        return nickname;
    }
    
    private String darEmailNoUsado() {
        String email = "email";
        boolean band = false;
        while(!band) {
            band = true;
            for(UsuarioEntity u : data) {
                if(email.equals(u.getEmail())) {
                    band = false;
                    email += "a";
                }
            }
        }
        return email;
    }
}
