/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.mb;

import com.inventario.entidades.Unidadmedida;
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

/**
 *
 * @author Joseee
 */
@ManagedBean
@SessionScoped
public class UnidadMedidaMB implements Serializable{
    @EJB
    private BusquedaGenLocal busqGen;
    @EJB
    private ProcesoGenLocal   genLoc;      
    
    Unidadmedida unidad = null;

  
    
    /*
    Variables de busqueda
    */
    
    String unidadId ="";
    
    String nombre="";
    
    
    String abreviatura="";
    String descripcion="";
    List<Unidadmedida> lstMe;
    int activeInt;

  
    String banG="";
    
   
    
    public UnidadMedidaMB(){
        unidad= new Unidadmedida();
        unidad.setUnidadId(Long.getLong("0"));
        lstMe = new ArrayList<>();
    }
    
     public void buscarUnidad(){
        System.out.println("Aqui estamos");
        lstMe = new ArrayList<>();
        System.out.println("Aqui estamos2");
        try {
            Long idvar = Long.valueOf("0");
            if (unidadId != null && !unidadId.isEmpty()) {
                 idvar=Long.valueOf(unidadId);
            }
            System.out.println("Llega aqui"+idvar);
            lstMe= busqGen.buscarUnidaM(idvar,nombre);
            System.out.println("Regresa valores:"+ lstMe.size());
            if (lstMe == null || lstMe.isEmpty()) {
                JsfUtil.showFacesMsg(null,"Nos se obtubieron resultados","Informe de Busqueda"
                            ,FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            JsfUtil.showFacesMsg(null,e.getMessage(),"Informe de busqueda",FacesMessage.SEVERITY_ERROR);
        
        }
        JsfUtil.updateComponent("growMessage");
    }
     
     public void selecUnidad(Unidadmedida sUnidad){
         unidad= sUnidad;
         activeInt=1;
         banG="M";
         JsfUtil.updateComponent("IDFRMUnidad");
         
     }
     
     public void newUnidadMedida(){
         unidad= new Unidadmedida();
         banG="G";
         try {
             Long IdCorelativo;
             IdCorelativo= busqGen.obtenerCorr(Unidadmedida.class,"unidadId");
             unidad.setUnidadId(IdCorelativo);
             JsfUtil.showFacesMsg(null, "Ingrese los Datos Solicitados!","Informe"
             ,FacesMessage.SEVERITY_INFO);
         } catch (Exception e) {
             System.out.println("Error creado de objeto persona"+e.getMessage());
             JsfUtil.showFacesMsg(null, e.getMessage(),"Informe", FacesMessage.SEVERITY_FATAL);
         }
            JsfUtil.updateComponent("growMessage");
     }
     public void saveUnidad(){
          try {
              String guardar = genLoc.guardarUnidad(getUnidad(), banG);
              JsfUtil.showFacesMsg(null,guardar,"La Unidad de Medida",FacesMessage.SEVERITY_INFO);
          } catch (Exception e) {
              JsfUtil.showFacesMsg(null,e.getMessage(), "Procesos",FacesMessage.SEVERITY_FATAL);
          }
          JsfUtil.updateComponent("growMessage");
          activeInt=0;
     }
     
     
     
     public Unidadmedida getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidadmedida unidad) {
        this.unidad = unidad;
    }
    

    public String getUnidadId() {
        return unidadId;
    }

    public void setUnidadId(String unidadId) {
        this.unidadId = unidadId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Unidadmedida> getLstMe() {
        return lstMe;
    }

    public void setLstMe(List<Unidadmedida> lstMe) {
        this.lstMe = lstMe;
    }
      public int getActiveInt() {
        return activeInt;
    }

    public void setActiveInt(int activeInt) {
        this.activeInt = activeInt;
    }
    
    
}
