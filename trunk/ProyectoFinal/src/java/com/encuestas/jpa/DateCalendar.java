/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.encuestas.jpa;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Viper
 */
@ManagedBean
@RequestScoped
public class DateCalendar {
    
    private Date encuestaDate;

    public Date getEncuestaDate() {
        return encuestaDate;
    }

    public void setEncuestaDate(Date encuestaDate) {
        this.encuestaDate = encuestaDate;
    }

    /** Creates a new instance of DateCalendar */
    public DateCalendar() {
    }
}
