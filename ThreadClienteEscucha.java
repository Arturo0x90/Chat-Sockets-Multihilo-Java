/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sockets;

import java.io.PrintWriter;
import java.util.Stack;
import sockets.mensajes.Mensaje;
import sockets.mensajes.MensajeTexto;

/**
 *
 * @author A00M
 */
public class ThreadClienteEscucha extends Thread{
    
    private final PrintWriter wr;
    private static Stack<Mensaje> pila_mensajes = null;
    private Integer ultimo_mensaje;
    private final Object notificador;

    public ThreadClienteEscucha(PrintWriter wr, Integer ultimo_mensaje, Object notificador, Stack<Mensaje> pila_mensajes) {
        this.wr = wr;
        this.ultimo_mensaje = ultimo_mensaje;
        this.notificador = notificador;
        this.pila_mensajes = pila_mensajes;
    }
    
    
    
    public void imprimirMensaje(MensajeTexto msg){
        wr.println(msg.getUsuario() + ": " + msg.getContenido());
        System.out.println(msg.getUsuario() + ": " + msg.getContenido());
    }
    
    public void revisarCola() throws InterruptedException{
        while (true){
            synchronized(notificador){
                notificador.wait();
                System.out.println(pila_mensajes.get(0).getUsuario());
            }
            synchronized(pila_mensajes){
                imprimirMensaje((MensajeTexto)pila_mensajes.peek());            
            }
            
           
        }
    }
    
    
    
    @Override
    public void run(){
        try{
            revisarCola();            
        }catch(InterruptedException E){
            System.out.println(E);
    }
    }
    
}
