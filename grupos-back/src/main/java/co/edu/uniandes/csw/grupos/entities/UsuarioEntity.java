/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author tefa
 */
@Entity
public class UsuarioEntity implements Serializable {
    
    /**
     * Identificador unico del usuario
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Nombre del usuario
     */
    private String nombre;
    
    /**
     * Apellido del usuario
     */
    private String apellido;
    
    /**
     * Apodo del usuario
     */
    private String nickname;
    
    /**
     * Contraseña del usuario
     */
    private String password;
    
    /**
     * Email del usuario
     */
    private String email;
    
    /**
     * Lista de tarjetas asociadas al usuario
     */
    @PodamExclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
    private List<TarjetaEntity> tarjetas= new ArrayList<TarjetaEntity>();
    
    /**
     * Empresa asociada al usuario
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private EmpresaEntity empresa = null;
    
    /**
     * Lista de grupos a los que pertenece
     */
    @PodamExclude
    @ManyToMany(mappedBy="miembros") 
    private List<GrupoEntity> grupos= new ArrayList<GrupoEntity>();
    
    /**
     * Lista de grupos que administra
     */
    @PodamExclude
    @ManyToMany(mappedBy="administradores")
    private List<GrupoEntity> gruposAdmin= new ArrayList<GrupoEntity>();
    
    /**
     * Lista de noticias
     */
    @PodamExclude
    @OneToMany(mappedBy = "autor")
    private List<NoticiaEntity> noticias= new ArrayList<NoticiaEntity>();
    
    /**
     * Lista de eventos
     */
    @PodamExclude
    @ManyToMany
    private List<EventoEntity> eventos= new ArrayList<EventoEntity>();
    
    /**
     * Lista de patrocinios que ha realizado el usuario
     */
    @PodamExclude
    @OneToMany(mappedBy="usuario",cascade = CascadeType.ALL, orphanRemoval=true)
    private List<PatrocinioEntity> patrocinios= new ArrayList<PatrocinioEntity>();
    
    /**
     * Lista de blogs
     */
    @PodamExclude
    @OneToMany
    private List<BlogEntity> blogs;
    
    /**
     * Da id del usuario
     * @return id
     */
    public Long getId(){
        return id;
    }
    
    /**
     * Da el nombre del usuario
     * @return nombre
     */
    public String getNombre(){
        return nombre;
    }
    
    /**
     * Da el apellido del usuario
     * @return apellido
     */
    public String getApellido(){
        return apellido;
    }
    
    /**
     * Da el apodo del usuario
     * @return nickname
     */
    public String getNickname(){
        return nickname;
    }
    
    /**
     * Da la contraseña del usuario
     * @return password
     */
    public String getPassword(){
        return password;
    }
    
    /**
     * Da el email del usuario
     * @return email
     */
    public String getEmail(){
        return email;
    }
    
    /**
     * Da la lista de todos los grupos a los que pertenece el usuario
     * @return grupos
     */
    public List<GrupoEntity> getGrupos(){
        return grupos;
    }
    
    /**
     * Da la lista de todos los grupos que administra el usuario
     * @return  gruposAdmin
     */
    public List<GrupoEntity> getGruposAdmin(){
        return gruposAdmin;
    }
    
    /**
     * Da la lista de todas las noticias del usuario
     * @return noticias
     */
    public List<NoticiaEntity> getNoticias(){
        return noticias;
    }
    
    /**
     * Da la lista de todos los eventos del usuario
     * @return eventos
     */
    public List<EventoEntity> getEventos(){
        return eventos;
    }
    
    /**
     * Da la lista de todos los patrocinios que ha realizado el usuario
     * @return patrocinios
     */
    public List<PatrocinioEntity> getPatrocinios(){
        return patrocinios;
    }
    
    /**
     * Da la lista de todos los blogs
     * @return blogs
     */
    public List<BlogEntity> getBlogs(){
        return blogs;
    }
    
    /**
     * Retorna las tarjetas de un usuario
     * @return tarjetas
     */
    public List<TarjetaEntity> getTarjetas(){
        return tarjetas;
    }
    
    /**
     * Retorna la empesa asociada al usuario, null si no esta conectado con una empresa
     * @return empresa
     */
    public EmpresaEntity getEmpresa(){
        return empresa;
    }
    
    /**
     * Cambia el id del usuario
     * @param pId 
     */
    public void setId(Long pId){
        id= pId;
    }
    
    /**
     * Cambia el nombre del usuario
     * @param nNombre 
     */
    public void setNombre(String nNombre){
        nombre= nNombre;
    }
    
    /**
     * Cambia el apellido del usuario
     * @param nApellido 
     */
    public void setApellido(String nApellido){
        apellido= nApellido;
    }
    
    /**
     * Cambia el apodo del usuario
     * @param nNickname 
     */
    public void setNickname(String nNickname){
        nickname= nNickname;
    }
    
    /**
     * Cambia la contraseña del usuario
     * @param nPassword 
     */
    public void setPassword(String nPassword){
        password= nPassword;
    }
    
    /**
     * Cambia el email del usuario
     * @param nEmail 
     */
    public void setEmail(String nEmail){
        email= nEmail;
    }
    
    /**
     * Cambia los grupos donde el usuario esta registrado
     * @param ng nueva lista de grupos
     */
    public void setGrupos(List<GrupoEntity> ng){
        grupos = ng;
    }
    
    /**
     * Cambia los grupos donde el usuario es admin
     * @param ng nueva lista de grupos que administra
     */
    public void setGruposAdmin(List<GrupoEntity> ng){
        gruposAdmin = ng;
    }
    
    /**
     * Cambia las noticias del usuario
     * @param ne nuevas noticias
     */
    public void setNoticias(List<NoticiaEntity> nn){
        noticias = nn;
    }
    
    /**
     * Cambia los eventos del usuario
     * @param ne nueva lista de eventos
     */
    public void setEventos(List<EventoEntity> ne){
        eventos = ne;
    }
    
    /**
     * Cambia los patrocinios del usuario
     * @param np nueva lista de patrocinios
     */
    public void setPatrocinios(List<PatrocinioEntity> np){
        patrocinios = np;
    }
    
    /**
     * Cambia los blogs del usuario
     * @param nb nueva lista de blogs
     */
    public void setBlogs(List<BlogEntity> nb){
        blogs = nb;
    }
    
    /**
     * Cambia las tarjetas del usuario
     * @param nt nueva lista de tarjetas
     */
    public void setTarjetas(List<TarjetaEntity> nt){
        tarjetas = nt;
    }
    
    /**
     * Cambia la empresa asociada al usuario
     * @param nemp 
     */
    public void setEmpresa(EmpresaEntity nemp){
        empresa= nemp;
    }
    
    /**
     * Cambia la noticia para actualizarla, dada por parámetro
     * @param idNoticia identificador de noticia
     * @param news noticiaEntity nueva
     */
    public void cambiarNoticia(Long idNoticia, NoticiaEntity news){
        for(NoticiaEntity n: noticias){
            if(n.getId() == idNoticia){
                n= news;
            }
        }
    }
    
    public void deleteNoticia(Long idNoticia){
        int i=0;
        for(NoticiaEntity n: noticias){
            if(n.getId() == idNoticia){
                break;
            }
            i++;
        }
        noticias.remove(i);
    }
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof UsuarioEntity))
            return false;
        UsuarioEntity u=(UsuarioEntity) o;
        return id.equals(u.getId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
