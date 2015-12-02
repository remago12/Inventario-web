/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.mb;

import com.inventario.entidades.Tipofactura;
import com.inventario.general.BusquedaGenLocal;
import com.inventario.utils.JsfUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Scope;

/**
 *
 * @author Joseee
 */
@ManagedBean
@SessionScoped
public class TipofacturaMB {

     @EJB
    private BusquedaGenLocal busqGen;
     
    Tipofactura tipofactura = null; 
    
    /*
    Lisra donde se cargan los daros de la tabla TipodeFactura
    */
    List<Tipofactura> tipoFacList;
    
    /*VAriables de Busqueda
    */
    
    String tipofacturaId="";
    String nombre="";
    
    String descripcion="";
    
    
    
    public TipofacturaMB(){
        tipofactura = new Tipofactura();
        tipoFacList = new ArrayList<>();
        tipofactura.setTipofacturaId(Long.getLong("0"));
    }
    
   
   public void buscarTipoFac(){
       System.out.println("Aqui estamos");
        tipoFacList = new ArrayList<>();
        System.out.println("Aqui estamos2");
        try {
            Long idvar = Long.valueOf("0");
            if (tipofacturaId != null && !tipofacturaId.isEmpty()) {
                 idvar=Long.valueOf(tipofacturaId);
            }
            System.out.println("Llega aqui"+idvar);
            tipoFacList= busqGen.busqTipoFa(idvar,nombre);
            System.out.println("Regresa valores:"+ tipoFacList.size());
            if (tipoFacList == null || tipoFacList.isEmpty()) {
                JsfUtil.showFacesMsg(null,"Nos se obtubieron resultados","Informe de Busqueda"
                            ,FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            JsfUtil.showFacesMsg(null,e.getMessage(),"Informe de busqueda",FacesMessage.SEVERITY_ERROR);
        
        }
        JsfUtil.updateComponent("growMessage");
       
   }
    
    
    
    /*Setter and Getter 
    */

    public List<Tipofactura> getTipoFacList() {
        return tipoFacList;
    }

    public void setTipoFacList(List<Tipofactura> tipoFacList) {
        this.tipoFacList = tipoFacList;
    }

    public String getTipofacturaId() {
        return tipofacturaId;
    }

    public void setTipofacturaId(String tipofacturaId) {
        this.tipofacturaId = tipofacturaId;
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
    
    
    
}
