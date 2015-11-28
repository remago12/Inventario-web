/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.mb;

import com.inventario.entidades.Contacto;
import com.inventario.entidades.Documento;
import com.inventario.entidades.Persona;
import com.inventario.general.BusquedaGenLocal;
import com.inventario.general.ProcesoGenLocal;
import com.inventario.utils.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;


/**
 * Clase encargada de la administración de Personas
 * @author Rene Gomez <rgomez1291@gmail.com>
 * @since 02/11/2015
 * @version 1
 */
@ManagedBean
@ViewScoped
public class PersonaMB implements Serializable{
    /**
     * Declaración de Variables
     */
    @EJB
    private BusquedaGenLocal busqGen;
    @EJB
    private ProcesoGenLocal procGen;
    private Persona persona;
    List<Persona> personaLst;
    Documento docSelect;
    
    //Variables de busqueda
    String nombres = "", apellidos="", dui="";
    private Date fechaHoy;
    
    
    public PersonaMB() {
        personaLst = new ArrayList<>();
        persona = new Persona();
    }
    
    /**
     * Método que retorna listado de personas según filtro
     */
    public void busqPersona(){
        personaLst = new ArrayList<>();
        System.out.println("Ingresa a buscar persona");
        try {
            personaLst = busqGen.busqPersonas(nombres, apellidos, dui);
            System.out.println("Devolvio registros: " + personaLst.size());
            if(personaLst==null || personaLst.isEmpty()){
                JsfUtil.showFacesMsg(null, "No se obtuvieron resultados.", 
                        "Informe de búsqueda: ", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            JsfUtil.showFacesMsg(null, e.getMessage(), "Informe búsqueda:", 
                    FacesMessage.SEVERITY_ERROR);
        }
        JsfUtil.updateComponent("growMessage");
    }
    
    /**
     * Método encargado de crear una nueva persona
     * con sus respectivas listas y objetos.
     */
    public void newPersona(){
        System.out.println("Ingresa a crear persona");
        persona = new Persona();
        try {
            Long idCorrelativo;
            idCorrelativo = busqGen.obtenerCorr(Persona.class, "personaId");
            persona.setPersonaId(idCorrelativo);
            persona.setDocumentoList(new ArrayList<Documento>());
            persona.setContactoList(new ArrayList<Contacto>());
            JsfUtil.showFacesMsg(null, "Ingrese los datos solicitados", "Informe:", 
                    FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            System.out.println("Error creando objeto persona: "+e.getMessage());
            JsfUtil.showFacesMsg(null, e.getMessage(), "Informe:", 
                    FacesMessage.SEVERITY_FATAL);
        }
        JsfUtil.updateComponent("growMessage");
    }

    public void guardarPer(){
        System.out.println("Guardar Persona");
        try {
            System.out.println("PersonaID: "+getPersona().getPersonaId());
            System.out.println("Nombres: "+getPersona().getNombres());
            System.out.println("Apellidos: "+getPersona().getApellidos());
            System.out.println("Fecha: "+getPersona().getFechanac());
            System.out.println("Tipo Per: "+getPersona().getTipoper());
            System.out.println("Genero "+getPersona().getGenero());
            
            String guardar = procGen.guardarPersona(getPersona());
            JsfUtil.showFacesMsg(null, guardar, "Proceso: ", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            JsfUtil.showFacesMsg(null, e.getMessage(), "Proceso: ", 
                    FacesMessage.SEVERITY_FATAL);
        }
        
        JsfUtil.updateComponent("growMessage");
    }
    
    
    /***************************************************************************
     * Getters y Setters
     * @return 
     ***************************************************************************/
    
    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Documento getDocSelect() {
        return docSelect;
    }

    public void setDocSelect(Documento docSelect) {
        this.docSelect = docSelect;
    }

    public List<Persona> getPersonaLst() {
        return personaLst;
    }

    public void setPersonaLst(List<Persona> personaLst) {
        this.personaLst = personaLst;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public Date getFechaHoy() {
        return fechaHoy;
    }

    public void setFechaHoy(Date fechaHoy) {
        this.fechaHoy = fechaHoy;
    }
    
}
