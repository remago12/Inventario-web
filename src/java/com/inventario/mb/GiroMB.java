/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.mb;

import com.inventario.entidades.Giro;
import com.inventario.general.BusquedaGenLocal;
import com.inventario.utils.JsfUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Rene Gomez
 */
@ManagedBean
@ViewScoped
public class GiroMB {

    //Llamado a EJBS
    @EJB
    private BusquedaGenLocal busqGen;
    
    //Declaración de Variables
    Long idGiro;
    String nombre;
    Giro giro = null;
    List<Giro> giroLst = null; 
    
    public GiroMB() {
    }
    
    /**
     * Método que sirve para buscar lista de giros 
     * sino recibe filtros los obtiene todos.
     */
    public void buscarGiros(){
        giroLst = new ArrayList<>();
        try {
            giroLst = busqGen.buscarGiros(idGiro, nombre);
            if(giroLst==null || giroLst.isEmpty()){
                JsfUtil.showFacesMsg(null, "No se obtuvieron resultados.", 
                        "Informe de búsqueda: ", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception ex) {
            JsfUtil.showFacesMsg(null, ex.getMessage(), "Informe búsqueda: ", 
                    FacesMessage.SEVERITY_ERROR);
        }
    }
    
    /******************************************************************
     * GETTERS Y SETTERS
     *****************************************************************/
    public Long getIdGiro() {
        return idGiro;
    }

    public void setIdGiro(Long idGiro) {
        this.idGiro = idGiro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Giro getGiro() {
        return giro;
    }

    public void setGiro(Giro giro) {
        this.giro = giro;
    }

    public List<Giro> getGiroLst() {
        return giroLst;
    }

    public void setGiroLst(List<Giro> giroLst) {
        this.giroLst = giroLst;
    }
    
    
    
}
