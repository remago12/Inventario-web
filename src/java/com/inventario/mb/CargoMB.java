/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.mb;

import com.inventario.entidades.Cargo;

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
public class CargoMB implements Serializable{
    @EJB
    private BusquedaGenLocal busqGen;
    @EJB
    private ProcesoGenLocal genLoc;
    /*  
    Declaracion de las Variables
    */
    
    Cargo cargo = null;
    List<Cargo> cargoLst;
    int activeInt;
    String banG;
    
    // variables de busqueda
    
    
    String idCargo = "";
    String nomCargo="";
    
    /**
     * Creates a new instance of CargoMB
     */
    
    public CargoMB() {
        cargo = new Cargo();
        cargo.setCargoId(Long.getLong("0"));
        cargoLst = new ArrayList<>();
        
    }
    
    /**
     * metodo generador de busqueda en entidad cargo
     * @return Los campos segun la busqueda por id o nombre de cargo
     */
    public void buscarCargo(){
        System.out.println("Aqui estamos");
        cargoLst = new ArrayList<>();
        System.out.println("Aqui estamos2");
        try {
            Long idvar = Long.valueOf("0");
            if (idCargo != null && !idCargo.isEmpty()) {
                 idvar=Long.valueOf(idCargo);
            }
            System.out.println("Llega aqui"+idvar);
            cargoLst= busqGen.busqCargo(idvar,nomCargo);
            System.out.println("Regresa valores:"+ cargoLst.size());
            if (cargoLst == null || cargoLst.isEmpty()) {
                JsfUtil.showFacesMsg(null,"Nos se obtubieron resultados","Informe de Busqueda"
                            ,FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            JsfUtil.showFacesMsg(null,e.getMessage(),"Informe de busqueda",FacesMessage.SEVERITY_ERROR);
        
        }
        JsfUtil.updateComponent("growMessage");
    }
    
    public void selectCargo(Cargo cSelect){
          cargo= cSelect;
          activeInt=1;
          banG="M";
          JsfUtil.updateComponent("IDFRMCargob");
    }  
    
    public void newCargo(){
        System.out.println("Ingresa a crear un nuevo Cargo");
        cargo = new Cargo();
        banG="G";
        try {
            Long idCorrelativo;
            idCorrelativo = busqGen.obtenerCorr(Cargo.class, "cargoId");
            cargo.setCargoId(idCorrelativo);
            JsfUtil.showFacesMsg(null, "Ingrese los datos solicitados", "Informe:", 
                    FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            System.out.println("Error creando objeto persona: "+e.getMessage());
            JsfUtil.showFacesMsg(null, e.getMessage(), "Informe:", 
                    FacesMessage.SEVERITY_FATAL);
        }
        JsfUtil.updateComponent("growMessage");
    }
      
      public void saveCargo(){
          try {
              String guardar = genLoc.guardarCargo(getCargo(), banG);
              JsfUtil.showFacesMsg(null,guardar,"El Cargo",FacesMessage.SEVERITY_INFO);
          } catch (Exception e) {
              JsfUtil.showFacesMsg(null,e.getMessage(), "Procesos",FacesMessage.SEVERITY_FATAL);
          }
          JsfUtil.updateComponent("growMessage");
          activeInt=0;
}
    
/*  
    
    --------------Getter & Setter----------------------------
*/
    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(String idCargo) {
        this.idCargo = idCargo;
    }

    public String getNomCargo() {
        return nomCargo;
    }

    public void setNomCargo(String nomCargo) {
        this.nomCargo = nomCargo;
    }

    public List<Cargo> getCargoLst() {
        return cargoLst;
    }

    public void setCargoLst(List<Cargo> cargoLst) {
        this.cargoLst = cargoLst;
    }

    public int getActiveInt() {
        return activeInt;
    }

    public void setActiveInt(int activeInt) {
        this.activeInt = activeInt;
    }
    
    
    
    
    
    
}
