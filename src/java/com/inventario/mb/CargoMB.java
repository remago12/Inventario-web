/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.mb;

import com.inventario.entidades.Cargo;

import com.inventario.general.BusquedaGenLocal;
import com.inventario.utils.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.jboss.weld.logging.Category;

/**
 *
 * @author Joseee
 */
@ManagedBean
@SessionScoped
public class CargoMB implements Serializable{
    @EJB
    private BusquedaGenLocal busqGen;
    
    /*  
    Declaracion de las Variables
    */
    
    Cargo cargo = null;
    List<Cargo> cargoLst;
    
    
    // variables de busqueda
    
    
    String idCargo = "";
    String nomCargo="";
    /**
     * Creates a new instance of CargoMB
     */
    
    public CargoMB() {
        cargo = new Cargo();
        cargo.setCargoId(Long.valueOf("0"));
        cargoLst = new ArrayList<>();
    }
    
    /**
     * metodo generador de busqueda en entidad cargo
     * @return Los campos segun la busqueda por id o nombre de cargo
     */
    public void buscarCargo(){
        System.out.println("Aqui estamos");
        cargoLst = new ArrayList<>();
        
        try {
            cargoLst= busqGen.busqCargo(Long.valueOf(idCargo), nomCargo);
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
    
    public void nuevoCargo(){
        cargo = new Cargo();
        
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
    
    
    
    
}
