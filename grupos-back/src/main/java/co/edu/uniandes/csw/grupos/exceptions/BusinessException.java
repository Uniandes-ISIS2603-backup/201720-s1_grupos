/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.exceptions;

/**
 * Excepción de negocios.<br>
 * @author jc161
 */
public class BusinessException extends Exception{
    public BusinessException (String msj)
    {
        super(msj);
    }
}
