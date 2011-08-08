/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.encuestas.jpacontroller;

import com.encuestas.jpa.Encuestas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Viper
 */
@Stateless
public class EncuestasFacade extends AbstractFacade<Encuestas> {
    @PersistenceContext(unitName = "ProyectoFinalPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public EncuestasFacade() {
        super(Encuestas.class);
    }
    
}
