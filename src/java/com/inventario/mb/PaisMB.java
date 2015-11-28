/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventario.mb;

import com.inventario.entidades.Pais;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Rene Gomez
 */
@ManagedBean
@ViewScoped
public class PaisMB {

    Pais pais;
    /**
     * Creates a new instance of PaisMB
     */
    public PaisMB() {
        pais = new Pais();
    }
    
    public void newPais(){
        pais = new Pais();
    }

    /**
     * Getters y Setters de la clase
     */
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
    
    
}
