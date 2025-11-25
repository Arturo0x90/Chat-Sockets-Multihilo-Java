/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sockets;

import sockets.mensajes.MensajeTexto;
import sockets.mensajes.Mensaje;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Stack;

/**
 *
 * @author A00M
 */
public class ThreadMandarMensaje extends Thread{
    private final Stack<Mensaje> cola_mensajes;
    private BufferedReader br;
    private String buffer;
    private final String nickname;
    private Integer ultimo_mensaje;
    private final Object notificador;
    
    public ThreadMandarMensaje(Stack<Mensaje> cola_mensajes, BufferedReader br, String nickname, Integer ultimo_mensaje, Object notificador){
        this.cola_mensajes = cola_mensajes;
        this.br = br;
        this.nickname = nickname;
        this.ultimo_mensaje = ultimo_mensaje;
        this.notificador = notificador;
    }
    
    public MensajeTexto crearMensajeTexto(String mensaje){
        return new MensajeTexto(nickname, mensaje);
    }
    
    
    @Override
    public void run(){
        try{
            buffer = br.readLine();
            while(!(buffer.equalsIgnoreCase("SALIR"))){
                    buffer = br.readLine();
                synchronized(cola_mensajes){
                    cola_mensajes.push(crearMensajeTexto(buffer));
                    ultimo_mensaje = cola_mensajes.size();
                }
                synchronized(notificador){
                    notificador.notifyAll();
                }
            }            
        }catch(IOException IO){
            System.out.println(IO);
        }
    }
    
}
