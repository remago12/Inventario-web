/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.mb;

import com.inventario.entidades.Cargo;
import com.inventario.entidades.Productocategoria;
import com.inventario.general.BusquedaGenLocal;
import com.inventario.general.ProcesoGenLocal;
import com.inventario.utils.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Joseee
 */
@ManagedBean
@SessionScoped
public class ProcategoriaMB implements Serializable{
    
    @EJB
    private BusquedaGenLocal busqGen;
    @EJB
    private ProcesoGenLocal  genLoc;    
    
    Productocategoria proCat=null;
    List<Productocategoria> proList;
    
    String prodcatId="";
    String nombre="";
    String descripcion="";
    int activeInt;
    String banG ;
    
    public ProcategoriaMB(){
         proCat= new Productocategoria();
         proCat.setProdcatId(Long.getLong("0"));
         proList= new ArrayList<>();
         buscarCategoria();
    }
    
    public void buscarCategoria(){
        System.out.println("Aqui estamos");
        proList = new ArrayList<>();
        System.out.println("Aqui estamos2");
        try {
            Long idvar = Long.valueOf("0");
            if (prodcatId != null && !prodcatId.isEmpty()) {
                 idvar=Long.valueOf(prodcatId);
            }
            System.out.println("Llega aqui"+idvar);
            proList= busqGen.buscarProcat(idvar,nombre);
                    System.out.println(""+proList);
            System.out.println("Regresa valores:"+ proList.size());
            if (proList == null || proList.isEmpty()) {
                JsfUtil.showFacesMsg(null,"Nos se obtubieron resultados","Informe de Busqueda"
                            ,FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            JsfUtil.showFacesMsg(null,e.getMessage(),"Informe de busqueda",FacesMessage.SEVERITY_ERROR);
        
        }
        JsfUtil.updateComponent("growMessage");
        
    }
    
    public void selectProcat(Productocategoria proSelec){
        proCat = proSelec;
        activeInt=1;
        banG="M";
        
    }
    
       public void newProCat(){
        System.out.println("Ingresa a crear un nuevo Cargo");
         proCat= new Productocategoria();
        banG="G";
        try {
            Long idCorrelativo;
            idCorrelativo = busqGen.obtenerCorr(Productocategoria.class, "prodcatId");
            proCat.setProdcatId(idCorrelativo);
            JsfUtil.showFacesMsg(null, "Ingrese los datos solicitados", "Informe:", 
                    FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            System.out.println("Error creando objeto persona: "+e.getMessage());
            JsfUtil.showFacesMsg(null, e.getMessage(), "Informe:", 
                    FacesMessage.SEVERITY_FATAL);
        }
        JsfUtil.updateComponent("growMessage");
    }
       
       
    public void saveProCat(){
          try {
              String guardar = genLoc.proCateg(getProCat(), banG);
              JsfUtil.showFacesMsg(null,guardar,"El Cargo",FacesMessage.SEVERITY_INFO);
          } catch (Exception e) {
              JsfUtil.showFacesMsg(null,e.getMessage(), "Procesos",FacesMessage.SEVERITY_FATAL);
          }
          JsfUtil.updateComponent("growMessage");
          activeInt=0;
    }   
    
    
    

    public List<Productocategoria> getProList() {
        return proList;
    }

    public void setProList(List<Productocategoria> proList) {
        this.proList = proList;
    }

    public String getProdcatId() {
        return prodcatId;
    }

    public void setProdcatId(String prodcatId) {
        this.prodcatId = prodcatId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Productocategoria getProCat() {
        return proCat;
    }

    public void setProCat(Productocategoria proCat) {
        this.proCat = proCat;
    }

    public int getActiveInt() {
        return activeInt;
    }

    public void setActiveInt(int activeInt) {
        this.activeInt = activeInt;
    }
    
    
}
