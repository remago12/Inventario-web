/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.mb;


import com.inventario.entidades.Tipopago;
import com.inventario.general.BusquedaGen;
import com.inventario.general.ProcesoGen;
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
public class TipopagoMB implements Serializable{
   @EJB 
    private BusquedaGen busqGen;
   @EJB 
   private  ProcesoGen genLoc;
    
   
    Tipopago tipoPago=null;
    List<Tipopago> pagoTList;
    int activeInt=1;

 
    String banG;
    
    /*
    Variables de Busqueda
    */
    
    String tipopagoId;
    String nombre;
    String descripcion;
    
    
    public TipopagoMB(){
        tipoPago= new Tipopago();
        tipoPago.setTipopagoId(Long.getLong("0"));
        pagoTList= new ArrayList<>();
    }
    
    
    public void busquedaTipoPa(){
        System.out.println("Aqui estamos");
        pagoTList = new ArrayList<>();
        System.out.println("Aqui estamos2");
        try {
            Long idvar = Long.valueOf("0");
            if (tipopagoId != null && !tipopagoId.isEmpty()) {
                 idvar=Long.valueOf(tipopagoId);
            }
            System.out.println("Llega aqui"+idvar);
            pagoTList=busqGen.busquedatipoPago(idvar,nombre);
            System.out.println("Regresa valores:"+ pagoTList.size());
            if (pagoTList == null || pagoTList.isEmpty()) {
                JsfUtil.showFacesMsg(null,"Nos se obtubieron resultados","Informe de Busqueda"
                            ,FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            JsfUtil.showFacesMsg(null,e.getMessage(),"Informe de busqueda",FacesMessage.SEVERITY_ERROR);
        
        }
        JsfUtil.updateComponent("growMessage");
    }
    
    public void selectTipopa(Tipopago sTipopa) {
        tipoPago = sTipopa;
        activeInt=1;
        banG="M";
        JsfUtil.updateComponent("");
    }   
    
    public void newTipoPago(){
        tipoPago= new Tipopago();
        banG="G";
        try {
            Long idCorrelativo;
            idCorrelativo = busqGen.obtenerCorr(Tipopago.class, "tipopagoId");
            tipoPago.setTipopagoId(idCorrelativo);
            JsfUtil.showFacesMsg(null, "Ingrese los datos solicitados", "Informe:", 
                    FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            System.out.println("Error creando objeto Tipo Pago: "+e.getMessage());
            JsfUtil.showFacesMsg(null, e.getMessage(), "Informe:", 
                    FacesMessage.SEVERITY_FATAL);
        }
        JsfUtil.updateComponent("growMessage");
    }
    
    public void saveTipopago(){
        try {
              String guardar = genLoc.saveTipopa(getTipoPago(), banG);
              JsfUtil.showFacesMsg(null,guardar,"El Tipo Pago",FacesMessage.SEVERITY_INFO);
          } catch (Exception e) {
              JsfUtil.showFacesMsg(null,e.getMessage(), "Procesos",FacesMessage.SEVERITY_FATAL);
          }
          JsfUtil.updateComponent("growMessage");
          activeInt=0;
    }
    
    
    
    /*
    Setter y getter de los atributos de la clase y de la lista que retorna todos los valores
    
    */
    public List<Tipopago> getPagoTList() {
        return pagoTList;
    }

    public void setPagoTList(List<Tipopago> pagoTList) {
        this.pagoTList = pagoTList;
    }

    public String getTipopagoId() {
        return tipopagoId;
    }

    public void setTipopagoId(String tipopagoId) {
        this.tipopagoId = tipopagoId;
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

    public Tipopago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(Tipopago tipoPago) {
        this.tipoPago = tipoPago;
    }
    
       public int getActiveInt() {
        return activeInt;
    }

    public void setActiveInt(int activeInt) {
        this.activeInt = activeInt;
    }
    
}
