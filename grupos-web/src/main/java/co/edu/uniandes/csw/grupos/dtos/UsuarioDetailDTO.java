/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.entities.EventoEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.grupos.entities.TarjetaEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import com.gs.collections.impl.list.fixed.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tefa
 */
public class UsuarioDetailDTO extends UsuarioDTO {

    /**
     * Lista de tarjetas asociadas al usuario
     */
    private List<TarjetaDTO> tarjetas;
    
    /**
     * Empresa asociada al usuario
     */
    private EmpresaDTO empresa;
    
    /**
     * Lista de grupos a los que pertenece
     */
    private List<GrupoDTO> grupos;
    
    /**
     * Lista de grupos que administra
     */
    private List<GrupoDTO> gruposAdmin;
    
    /**
     * Lista de noticias
     */
    private List<NoticiaDTO> noticias;
    
    /**
     * Lista de eventos
     */
    private List<EventoDTO> eventos;
    
    /**
     * Lista de patrocinios que ha realizado el usuario
     */
    private List<PatrocinioDTO> patrocinios;
    
    /**
     * Lista de blogs
     */
    private List<BlogDTO> blogs;
    
    /**
     * Da la lista de todos los grupos a los que pertenece el usuario
     * @return grupos
     */
    public List<GrupoDTO> getGrupos(){
        return grupos;
    }
    
    /**
     * Da la lista de todos los grupos que administra el usuario
     * @return  gruposAdmin
     */
    public List<GrupoDTO> getGruposAdmin(){
        return gruposAdmin;
    }
    
    /**
     * Da la lista de todas las noticias del usuario
     * @return noticias
     */
    public List<NoticiaDTO> getNoticias(){
        return noticias;
    }
    
    /**
     * Da la lista de todos los eventos del usuario
     * @return eventos
     */
    public List<EventoDTO> getEventos(){
        return eventos;
    }
    
    /**
     * Da la lista de todos los patrocinios que ha realizado el usuario
     * @return patrocinios
     */
    public List<PatrocinioDTO> getPatrocinios(){
        return patrocinios;
    }
    
    /**
     * Da la lista de todos los blogs
     * @return blogs
     */
    public List<BlogDTO> getBlogs(){
        return blogs;
    }
    
    /**
     * Retorna las tarjetas de un usuario
     * @return tarjetas
     */
    public List<TarjetaDTO> getTarjetas(){
        return tarjetas;
    }
    
    /**
     * Retorna la empesa asociada al usuario, null si no esta conectado con una empresa
     * @return empresa
     */
    public EmpresaDTO getEmpresa(){
        return empresa;
    }
    
    /**
     * Cambia los grupos donde el usuario esta registrado
     * @param ng nueva lista de grupos
     */
    public void setGrupos(List<GrupoDTO> ng){
        grupos = ng;
    }
    
    /**
     * Cambia los grupos donde el usuario es admin
     * @param ng nueva lista de grupos que administra
     */
    public void setGruposAdmin(List<GrupoDTO> ng){
        gruposAdmin = ng;
    }
    
    /**
     * Cambia las noticias del usuario
     * @param ne nuevas noticias
     */
    public void setNoticias(List<NoticiaDTO> nn){
        noticias = nn;
    }
    
    /**
     * Cambia los eventos del usuario
     * @param ne nueva lista de eventos
     */
    public void setEventos(List<EventoDTO> ne){
        eventos = ne;
    }
    
    /**
     * Cambia los patrocinios del usuario
     * @param np nueva lista de patrocinios
     */
    public void setPatrocinios(List<PatrocinioDTO> np){
        patrocinios = np;
    }
    
    /**
     * Cambia los blogs del usuario
     * @param nb nueva lista de blogs
     */
    public void setBlogs(List<BlogDTO> nb){
        blogs = nb;
    }
    
    /**
     * Cambia las tarjetas del usuario
     * @param nt nueva lista de tarjetas
     */
    public void setTarjetas(List<TarjetaDTO> nt){
        tarjetas = nt;
    }
    
    /**
     * Cambia la empresa asociada al usuario
     * @param nemp 
     */
    public void setEmpresa(EmpresaDTO nemp){
        empresa= nemp;
    }
    
    /**
     * Constructor vacio
     */
    public UsuarioDetailDTO(){
        
    }
    
    /**
     * Constructor a partir de un entity
     * @param ue Usuario Entity
     */
    public UsuarioDetailDTO(UsuarioEntity ue){
        super(ue);
        if(ue!=null){
            //Guarda los grupos a los que pertence el usuario
            grupos = new ArrayList<GrupoDTO>();
            for(GrupoEntity g: ue.getGrupos()){
                grupos.add(new GrupoDTO(g));
            }
            
            //Guarda los grupos que administra el usuario
            gruposAdmin = new ArrayList<GrupoDTO>();
            for(GrupoEntity g: ue.getGruposAdmin()){
                gruposAdmin.add(new GrupoDTO(g));
            }
            
            //Guarda las noticias del usuario
            noticias= new ArrayList<NoticiaDTO>();
            for(NoticiaEntity n: ue.getNoticias()){
                noticias.add(new NoticiaDTO(n));
            }
            
            //Guarda los eventos del usuario
            eventos = new ArrayList<EventoDTO>();
            for(EventoEntity e: ue.getEventos()){
                eventos.add(new EventoDTO(e));
            }
            
            //Guarda los patrocinios del usuario
            patrocinios = new ArrayList<PatrocinioDTO>();
            for(PatrocinioEntity p: ue.getPatrocinios()){
                patrocinios.add(new PatrocinioDTO(p));
            }
            
            //Guarda los blog del usuario
            blogs= new ArrayList<BlogDTO>();
            for(BlogEntity b: ue.getBlogs()){
                blogs.add(new BlogDTO(b));
            }
            
            //Guarda las tarjetas del usuario
            tarjetas = new ArrayList<TarjetaDTO>();
            for(TarjetaEntity t: ue.getTarjetas()){
                tarjetas.add(new TarjetaDTO(t));
            }
            
            //Guarda la empresa del usuario
            if(ue.getEmpresa()!=null)
            empresa= new EmpresaDTO(ue.getEmpresa());
        }
    }
    
    /**
     * DTO a Entity
     * @return UsuarioEntity
     */
    public UsuarioEntity toEntity(){
        UsuarioEntity ue = super.toEntity();
        
        //Adiciona los grupos a los que pertence el usuario
        if(grupos!=null){
            List<GrupoEntity> gruposE= new ArrayList<GrupoEntity>();
            for(GrupoDTO g: grupos){
                gruposE.add(g.toEntity());
            }
            ue.setGrupos(gruposE);
        }
        
        //Adiciona los grupos que administra el usuario
        if(gruposAdmin!=null){
            List<GrupoEntity> gruposE= new ArrayList<GrupoEntity>();
            for(GrupoDTO g: gruposAdmin){
                gruposE.add(g.toEntity());
            }
            ue.setGruposAdmin(gruposE);
        }
        
        //Adiciona las noticias del usuario
        if(noticias!=null){
            List<NoticiaEntity> noticiasE= new ArrayList<NoticiaEntity>();
            for(NoticiaDTO n: noticias){
                noticiasE.add(n.toEntity());
            }
            ue.setNoticias(noticiasE);
        }
        
        //Adiciona los eventos del usuario
        if(eventos!=null){
            List<EventoEntity> eventosE = new ArrayList<EventoEntity>();
            for(EventoDTO e: eventos){
                eventosE.add(e.toEntity());
            }
            ue.setEventos(eventosE);
        }
        
        //Adiciona los patrocinios del usuario
        if(patrocinios!=null){
            List<PatrocinioEntity> patrociniosE = new ArrayList<PatrocinioEntity>();
            for(PatrocinioDTO p: patrocinios){
                patrociniosE.add(p.toEntity());
            }
            ue.setPatrocinios(patrociniosE);
        }
        
        //Adiciona los blogs del usuario
        if(blogs!=null){
            List<BlogEntity> blogsE = new ArrayList<BlogEntity>();
            for(BlogDTO b: blogs){
                blogsE.add(b.toEntity());
            }
            ue.setBlogs(blogsE);
        }
        
        //Adiciona las tarjetas del usuario
        if(tarjetas!=null){
            List<TarjetaEntity> tarjetasE = new ArrayList<TarjetaEntity>();
            for(TarjetaDTO t: tarjetas){
                tarjetasE.add(t.toEntity());
            }
            ue.setTarjetas(tarjetasE);
        }
        
        //Adiciona la empresa del usuario
        if(empresa !=null){
            ue.setEmpresa(empresa.toEntity());
        }
        
        return ue;
    }
    
}
