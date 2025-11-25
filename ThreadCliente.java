/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sockets;

import sockets.mensajes.Mensaje;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author A00M
 */
public class ThreadCliente extends Thread{
    private BufferedReader br;
    private PrintWriter wr;
    private String ip;
    private String nickname = "";
    
    private Map<String, String> nicknames;
    private static Stack<Mensaje> cola_mensajes = null;
    private Integer ultimo_mensaje;    
    private ThreadClienteEscucha cliente_escucha;
    private ThreadMandarMensaje cliente_enviar;
    private static final Object notificador = new Object();
    Socket cliente_a;
    //                                                      Este mapa seria inverso, quiere decir que por nombre, mapeamos una direccion ip, haciendo unicas a las personas. Revisamos si ya existe
    public ThreadCliente(BufferedReader br, PrintWriter wr, Map<String, String> nicknames, String ip, Stack<Mensaje> cola_mensajes, Socket cliente_a){
        this.br = br;
        this.wr = wr;
        this.nicknames = nicknames;
        this.ip = ip;
        this.cliente_a = cliente_a;
        ultimo_mensaje = cola_mensajes.size();
    }
    
    public static void setCola_mensajes(Stack<Mensaje> cola_mensajes) {
        ThreadCliente.cola_mensajes = cola_mensajes;
    }
    
    public void crearThreadsCliente(){
        try{
            cliente_escucha = new ThreadClienteEscucha(wr, ultimo_mensaje, notificador, cola_mensajes);
            cliente_enviar = new ThreadMandarMensaje(cola_mensajes, br, nickname, ultimo_mensaje, notificador);                

            cliente_escucha.start();
            cliente_enviar.start();
            cliente_enviar.join();  
        }catch(InterruptedException E){
            Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, E);
        }

    }
    
    private final String leernickname(){
        try{
            return (nickname = br.readLine());
        }catch(IOException I){
            Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, I);
        }
            return null;
    }
    
    private void cerrarcliente(){
        System.out.println("El proceso del cliente " + nickname + "ha terminado");
        cliente_escucha.interrupt();
        nicknames.remove(nickname);
        this.interrupt();
    }
    
    @Override
    public void run(){
        try{
        String buffer = "";
        
        wr.println("Introduce tu nickname: ");
        if(leernickname()==null) cerrarcliente();
        if (Servidor.agregar_usuario(nickname, ip, nicknames)){/*en un hashmap, put devuelve null si no existia ninguno igual anteriormente*/
            wr.println("Te has logueado como: " + nickname);
            crearThreadsCliente();
        /*Codigo recibir datos cliente y almacenarlos en un array*/
        }else{
            wr.println("Ya existe ese nombre, prueba de nuevo");
            cliente_a.close();
        }
        }catch (IOException E){
            System.out.println(E.toString());
        }
        cerrarcliente();
    }
}
