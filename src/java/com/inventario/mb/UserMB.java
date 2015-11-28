    /*
 * Librerias importadas para esta clase
 */
package com.inventario.mb;

import com.inventario.entidades.Menu;
import com.inventario.entidades.Rol;
import com.inventario.entidades.Rolmenu;
import com.inventario.entidades.Usuario;
import com.inventario.general.BusquedaSegLocal;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import com.inventario.utils.JsfUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;

/**
 * Clase que sirve para manejar el ingreso de usuarios al sistema.
 * @author Rene Gomez
 * @since 27/10/2015
 */
@ManagedBean
@SessionScoped
public class UserMB implements Serializable{
    @EJB
    private BusquedaSegLocal busSeg;
    
    /* Declaración de Variables */
    public Usuario usuario;
    private String usuarioLogin;
    private String passwordLogin;
    private Date fechaHoy;
    
    //Variables de menú
    Rolmenu rolxMenu = null;
    Rol rol = null;
    private DefaultMenuModel menu;
    private Collection<String> urls;
        
    public UserMB() {
        usuarioLogin = "Invitado";
    }
    
    
    public void limpiarVarLogin(){
        usuarioLogin = "";
        passwordLogin = "";
    }

    public void autenticar(){
        try{
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            String contexto = ec.getRequestContextPath();
            System.out.println(contexto);
            
            Usuario usuarioVerificacion = null;
            usuarioVerificacion = busSeg.buscarUsuario(usuarioLogin);
            if(usuarioVerificacion!=null){
                setUsuario(usuarioVerificacion);
                System.out.println("Usuario logueado: "+getUsuario().getUsuario()+ ", rol: "+usuario.getRolId().getRolId());
                if(usuario.getContrasena().equals(passwordLogin)){
                    construirMenu(usuario.getRolId().getRolId());  
                    ec.redirect(contexto+"/vista/inicio.xhtml");	
                }else{
                    JsfUtil.updateComponent("growMessage");
                    JsfUtil.showFacesMsg(null, "Su usuario o contraseña es inválido, "
                            + "Favor intente de nuevo.", "Login:",FacesMessage.SEVERITY_FATAL);
                }
            }            
            
        } catch (Exception e) {
            JsfUtil.updateComponent("growMessage");
            JsfUtil.showFacesMsg(null, e.getMessage(), "Informe:",FacesMessage.SEVERITY_FATAL);
        }
    }
    
    private void construirMenu(Long val) throws Exception{
        try {
            System.out.println("Ingresa a construir menu.. "+val);
            obtenerMenuPorRol(val);
        } catch (Exception e) {
            throw e;
        }
        
    }
    
    public void cerrarSesion(){
        try {
            System.out.println("A cerrar sesion");
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            String contexto = ec.getRequestContextPath();

            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            UserMB user = (UserMB)JsfUtil.getBean("userMB");
            user = null;			
            ec.redirect(contexto);
            System.out.println("Sesion cerrada con éxito");
        } catch (Exception e) {
            JsfUtil.showFacesMsg(null, "Error en Clase AuthenticationMB metodo cerrarSesion", "sysErrMsg",FacesMessage.SEVERITY_FATAL);
        }
    }
    
    /**
     * Método encargado de crear un Menú por Rol
     */
    public void newMenuxRol(){
        
    }
    
    public DefaultMenuModel obtenerMenuPorRol(Long idRol) throws Exception{
        try {
            System.out.println("ObtenerMenuPorRol: "+idRol);
            List<Menu> menuTotalLst = new ArrayList<>();
            List<Rolmenu> menuxRolLst = new ArrayList<>();
            
            menuTotalLst = busSeg.obtieneMenus(null);
            /*System.out.println("Obtiene menu por rol");
            menuxRolLst = busSeg.obtieneMenuxRol(idRol);
            */
            
            /*List<String> listaUrls = new ArrayList<>();
            for(Menu m: menuTotalLst){
                if(m.getUrl()!=null){
                    listaUrls.add(m.getUrl());
                }
            }
            setUrls(listaUrls);
*/
            if(menuTotalLst!=null){
                crearMenu(menuTotalLst,menuxRolLst);
            }
            return menu;
        } catch (Exception e) {
            System.out.println("Error obteniendoMenu por Rol.");
            throw new Exception(
                "Error en la clase UsuarioOpcionMenuController - metodo getMenuPorIdRol\n"
                                + e.getMessage(), e.getCause());
        }
    }
    
    /**
     * Método para construir el menu
     * @param menuList Lista de menus
     * @param rolMenuLst Lista de menus por Rol
     */	
    public void crearMenu(List<Menu> menuList,List<Rolmenu> rolMenuLst) throws Exception{
        System.out.println("Crear menu...");
        menu = new DefaultMenuModel();
        DefaultSubMenu subMenu = null;
        DefaultMenuItem item = null;
        try {
            //Crear Menu
            for(Menu opt: menuList){
                //Si valor Padre es igual a 0 crear menú 
                if(opt.getMenuFk()== null || opt.getMenuFk().getMenuId().equals(Long.valueOf("0"))){
                    System.out.println("Encontró menu: "+opt.getNombre());
                    subMenu = new DefaultSubMenu();
                    subMenu.setId(opt.getMenuId().toString());
                    subMenu.setLabel(opt.getNombre());
                    subMenu.setIcon(opt.getIcono());
                    System.out.println("Agrega menu: "+subMenu.getLabel());
                    //menu.addElement(subMenu);
                    
                    //Verificar si el menu padre tiene lista de menú
                    System.out.println("Submenu...: "+opt.getMenuList().size());
                    if(opt.getMenuList() != null && !opt.getMenuList().isEmpty()){
                        System.out.println("Tiene lista de Submenu...");
                        for(Menu sub: opt.getMenuList()){
                            item = new DefaultMenuItem();
                            item.setValue(sub.getNombre());
                            item.setUrl(sub.getUrl());
                            item.setIcon(sub.getIcono());
                            subMenu.addElement(item);
                        }
                    }
                    menu.addElement(subMenu);
                }
          
                /*for(Menu optPrincipal:opt.getMenuList()){	
                    if(opt.getMenuId().equals(optPrincipal.getMenuId())){
                        subMenu=buildSubMenu(optPrincipal);
                        if(optPrincipal.getNombre().contains("Perfil")){
                            subMenu.setStyle("position: absolute; right: 85px;");
                        }
                        item = new DefaultMenuItem();
                        item.setValue(opt.getMenuId());
                        item.setUrl(opt.getUrl());
                        item.setIcon(opt.getIcono());
                        subMenu.addElement(item);
                    }
                }*/

                //menu=new DefaultMenuModel();
                //menu.addElement(subMenu);
                //setMenu(menu);
                System.out.println("Finaliza de agregar menu...: ");
            }	

        } catch (Exception e) {
            System.out.println("Error construyendo menu.. "+e.getMessage());
            throw new Exception("Error en la clase UsuarioOpcionMenuController - metodo crearMenu\n"
                + e.getMessage(), e.getCause());
        }
    }
    
    /**
    * Método para construir el Sub menu
    * @param optPrincipal opcion Principal
    * @return Submenu
    */
    public DefaultSubMenu buildSubMenu(Menu optPrincipal) throws Exception{
        DefaultSubMenu subMenu=null;
        try {
            if(getMenu()==null){
                DefaultSubMenu newSubmenu = new DefaultSubMenu();
                newSubmenu.setId(optPrincipal.getMenuId().toString());
                newSubmenu.setIcon(optPrincipal.getIcono());
                newSubmenu.setLabel(optPrincipal.getNombre());
                subMenu = new DefaultSubMenu();
            }/*else{
                List<UIComponent> opciones = getMenu().;
                boolean validador=false;
                for (UIComponent i : opciones) {
                    if (i.getId().equals(optPrincipal.getMenuId().toString())) {
                        validador=true;
                        subMenu=(DefaultMenuModel())i;
                    } 
                }
                if(!validador){
                    Submenu newSubmenu = new Submenu();
                    newSubmenu.setId(optPrincipal.getNombre().replace(" ", ""));
                    newSubmenu.setIcon(optPrincipal.getIcono());
                    newSubmenu.setLabel(optPrincipal.getNombre());
                    subMenu=newSubmenu;
                }
            }*/
            return subMenu;

        } catch (Exception e) {
            throw new Exception(
                "Error en la clase UsuarioOpcionMenuController - metodo buildSubMenu\n"
                                + e.getMessage(), e.getCause());
        } 		
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public String getPasswordLogin() {
        return passwordLogin;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public void setPasswordLogin(String passwordLogin) {
        this.passwordLogin = passwordLogin;
    }
    
    public Collection<String> getUrls() {
        return urls;
    }

    public void setUrls(Collection<String> urls) {
        this.urls = urls;
    }

    public DefaultMenuModel getMenu() {
        return menu;
    }

    public void setMenu(DefaultMenuModel menu) {
        this.menu = menu;
    }

    public Date getFechaHoy() {
        return fechaHoy;
    }

    public void setFechaHoy(Date fechaHoy) {
        this.fechaHoy = fechaHoy;
    }
    
    
}
