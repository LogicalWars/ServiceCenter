/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxsocketprogramming;

import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author topman garbuja,It represents each new connection
 */
public  class TaskClientConnection implements Runnable {

    Socket socket;
    javafxsocketprogramming.ServerJavaFX server;
    // Create data input and output streams
    DataInputStream input;
    DataOutputStream output;

    public TaskClientConnection(Socket socket, javafxsocketprogramming.ServerJavaFX server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {

        try {
            // Create data input and output streams
            input = new DataInputStream(
                    socket.getInputStream());
            output = new DataOutputStream(
                    socket.getOutputStream());

            while (true) {
                //Получить сообщение от клиента
                String message = input.readUTF();

                //отправить сообщение через server broadcast
                server.broadcast(message);
                
                //добавить сообщение текстовой области пользовательского интерфейса (GUI Thread)
                Platform.runLater(() -> {                    
                    server.txtAreaDisplay.appendText(message + "\n");
                });
            }
            
            

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }

    //отправить сообщение обратно клиенту
    public void sendMessage(String message) {
          try {
            output.writeUTF(message);
            output.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        } 
       
    }

}
