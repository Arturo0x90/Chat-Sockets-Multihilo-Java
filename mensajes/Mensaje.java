/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sockets.mensajes;

/**
 *
 * @author A00M
 */
public abstract class Mensaje {
    /*Cuando realmente implementaramos mas tipos de mensajes, tendriamos que 
    agregarles el metodo abstracto codificar(), para que cada mensaje llevara su propia codificacion y el servidor 
    supiera como interpretarlos. Asi como hacer un cliente claro
    */
    private String usuario;

    public String getUsuario() {
        return usuario;
    }
    
    public Mensaje(String usuario){
        this.usuario = usuario;
    }
}
