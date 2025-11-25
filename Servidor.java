/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sockets;

import sockets.mensajes.Mensaje;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author A00M
 */
public class Servidor{
    
    ServerSocket s_servidor;
    private InetAddress addr;
    private List<Thread> thread_clientes;
    private Map<String, String> apodos;
    private Stack<Mensaje> cola_mensajes;
    private Socket cliente_actual;
    public Servidor(){
        try{
            thread_clientes = new LinkedList<>();
            apodos = new HashMap<>();
            cola_mensajes = new Stack<>();
            ThreadCliente.setCola_mensajes(cola_mensajes);
            addr = InetAddress.getByName("127.0.0.1");
            s_servidor = new ServerSocket(5555, 10, addr); /*Puerto 5555 ; 10 Conexiones ; Recibir solo local*/
            while(true){
                cliente_actual = s_servidor.accept();
                crearThread_Cliente();
            /*ConcurrentLinkedQueue*/
            }
        }catch(IOException IO){
            System.out.println(IO.toString());
        }
    }
    
    private void crearThread_Cliente(){
        try{
            ThreadCliente cliente_a = new ThreadCliente(
            new BufferedReader(
                    new InputStreamReader(cliente_actual.getInputStream())), 
            new PrintWriter(cliente_actual.getOutputStream(), 
                    true), 
            apodos, 
            cliente_actual.getInetAddress().getHostAddress(), 
            cola_mensajes, cliente_actual);
            thread_clientes.add(
                    cliente_a
            );
            cliente_a.start();
            
        }catch(IOException e){
            System.out.println(e.toString());
        }
        
    }
    
    public static boolean agregar_usuario(String nombre, String ip, Map<String, String> apodos_a){
        /*Devuelve true si no existia, false si hay alguno con el mismo nombre*/
        if(apodos_a.put(nombre, ip)==null){
            return true; 
        }else{
            return false;
        }
    }
    
}
