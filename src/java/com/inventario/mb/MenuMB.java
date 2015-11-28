/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.mb;

import com.inventario.entidades.Menu;
import com.inventario.entidades.Rol;
import com.inventario.entidades.Rolmenu;
import com.inventario.general.BusquedaSegLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;

/**
 * Clase encargada del manejo de menus por rol
 * @author Rene Gomez
 * @version 1
 * @since 02/11/2015
 */
@ManagedBean
@SessionScoped
public class MenuMB implements Serializable{
    //Declaración de variables
    @EJB
    private BusquedaSegLocal busSeg;
    //Menu menu = null;
    Rolmenu rolxMenu = null;
    Rol rol = null;
    private DefaultMenuModel menu;
    private Collection<String> urls;
    
    
    public MenuMB() {
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
            System.out.println("Obtiene todos los menus");
            try {
                menuTotalLst = busSeg.obtieneMenus(null);
            } catch (Exception e) {
                System.out.println("Error obtieneMenus: "+e.getMessage());
            }
            System.out.println("Obtiene menu por rol");
            try {
                menuxRolLst = busSeg.obtieneMenuxRol(idRol);
            } catch (Exception e) {
                System.out.println("Error obtieneMenuxRol: "+e.getMessage());
            }
            
            List<String> listaUrls = new ArrayList<>();
            for(Menu m: menuTotalLst){
                if(m.getUrl()!=null){
                    listaUrls.add(m.getUrl());
                }
            }
            setUrls(listaUrls);

            if(menuTotalLst!=null && menuxRolLst!=null){
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
        DefaultMenuModel menu = new DefaultMenuModel();
        DefaultSubMenu subMenu = null;
        DefaultMenuItem item = null;
        try {
            //Crear Menu
            for(Menu opt: menuList){
                //Si valor Padre es igual a 0 crear menú 
                if(opt.getMenuFk()== null || opt.getMenuFk().getMenuId().equals(Long.valueOf("0"))){
                    menu = new DefaultMenuModel();
                    subMenu = new DefaultSubMenu();
                    item = new DefaultMenuItem();
                    item.setValue(opt.getMenuId());
                    item.setUrl(opt.getUrl());
                    item.setIcon(opt.getIcono());
                    subMenu.addElement(item);
                    menu.addElement(subMenu);
                    setMenu(menu);
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
}
