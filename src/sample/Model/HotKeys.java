package sample.Model;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.Controller.EditTicketController;
import sample.Controller.MainMenuController;
import sample.Controller.NewTicketController;
import sample.Controller.TicketListController;
import sample.Main;

import java.io.IOException;
import java.sql.SQLException;

public class HotKeys   {
    private int a = 0;

    public void setA(int a) {
        this.a = a;
    }

    /**Клавиши быстрого доступа:
     * Ctrl + N - создание новой заявки
     * Ctrl + S - сохранить изменения редактируемой заявки
     * Ctrl + P - печать
     * Ctrl + F - поиск
     *  */






    public void addListenerHotKey(EventHandler<KeyEvent> event){
//        if(mainMenuController !=null){mainMenuController.paneMainContent.addEventFilter(KeyEvent.KEY_PRESSED, event);}
    }

//       node.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
//
//       });
//           public void handle(KeyEvent e) {
//               if(e.getCode() == KeyCode.N && e.isControlDown()){
////                   mainMenuController.newTicket();
//               }
//               if(e.getCode() == KeyCode.F && e.isControlDown()){
////                   mainMenuController.searchTextField.requestFocus();
//               }
////               if(e.getCode() == KeyCode.S && e.isControlDown() && newTicketController != null){
////                   newTicketController.createNewTicket();
////               }
////               if(e.getCode() == KeyCode.S && e.isControlDown() && editTicketController != null){
////                   if(editTicketController.check()){
////                       editTicketController.saveEditTicket();
////                   }
////               }
//               if(e.getCode() == KeyCode.P && e.isControlDown() && checkOpenEditTicket>-1){
//                   System.out.println("printeed start");
////                   try {
////                       System.out.println("printeed");
//
////                       editTicketController.printButton.requestFocus();
////                       editTicketController.printed();
////                   } catch (IOException ex) {
////                       ex.printStackTrace();
////                   }
////                   System.out.println("printeed exit");
//               }
//           }});

}
