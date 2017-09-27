/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.exceptions;

/**
 * Excepción no encontrada.
 * @author jc161
 */
public class NotFoundException extends Exception {
    
    public NotFoundException(String msj)
    {
        super(msj);
    }
}
