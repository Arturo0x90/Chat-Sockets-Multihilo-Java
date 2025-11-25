/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sockets.mensajes;

import sockets.mensajes.Mensaje;

/**
 *
 * @author A00M
 */
public class MensajeTexto extends Mensaje {
    private final String contenido;
    public MensajeTexto(String usuario, String contenido){
        super(usuario);
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }
    
}
