/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  javafxsocketprogramming;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author topman garbuja,
 * 
 * This class is server, run this only once.
 * 
 * It also uses TaskClientConnection.java file to use in a thread which represents each new connection
 * 
 */
public class ServerJavaFX extends Application {
    public TextArea txtAreaDisplay;
    //в этом листе сохраняются подключенные клиенты
    List<javafxsocketprogramming.TaskClientConnection> connectionList = new ArrayList<javafxsocketprogramming.TaskClientConnection>();

    @Override // Переопределите метод start в классе Application
    public void start(Stage primaryStage) {
        //Текстовая область для отображения содержимого
        txtAreaDisplay = new TextArea();
        txtAreaDisplay = new TextArea();
        txtAreaDisplay.setEditable(false);
        
        ScrollPane scrollPane = new ScrollPane();   //pane to display text messages      
        scrollPane.setContent(txtAreaDisplay);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
     
        // Create a scene and place it in the stage
        Scene scene = new Scene(scrollPane, 450, 500);
        primaryStage.setTitle("Server: JavaFx Text Chat App"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        
        //create a new thread
        new Thread(() -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(javafxsocketprogramming.ConnectionUtil.port);
                
                //append message of the Text Area of UI (GUI Thread)
                Platform.runLater(()
                        -> txtAreaDisplay.appendText("New server started at " + new Date() + '\n'));

                //непрерывный цикл
                while (true) {
                    // Прослушайте запрос на подключение, добавьте новое подключение в список
                    Socket socket = serverSocket.accept();
                    javafxsocketprogramming.TaskClientConnection connection = new javafxsocketprogramming.TaskClientConnection(socket, this);
                    connectionList.add(connection);

                    //create a new thread
                    Thread thread = new Thread(connection);
                    thread.start();

                }
            } catch (IOException ex) {
                  txtAreaDisplay.appendText(ex.toString() + '\n');
            }
        }).start();
    }

    /**
     * The main method is only needed for the IDE with limited JavaFX support.
     * Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }

    //отправить сообщение всем подключенным клиентам
    public void broadcast(String message) {
        for (javafxsocketprogramming.TaskClientConnection clientConnection : this.connectionList) {
            clientConnection.sendMessage(message);
        }
    }
}
