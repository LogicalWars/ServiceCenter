package sample.Model;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.Controller.EditTicketController;
import sample.Controller.MainMenuController;
import sample.Controller.NewTicketController;
import sample.Controller.TicketListController;

import java.io.IOException;
import java.sql.SQLException;

public abstract class HotKeys<eventHandler> {
    /**Клавиши быстрого доступа:
     * Ctrl + N - создание новой заявки
     * Ctrl + S - сохранить изменения редактируемой заявки
     * Ctrl + P - печать
     * Ctrl + F - поиск
     *  */

    int checkOpenEditTicket = -1;
    public int getCheckOpenEditTicket() {
        return checkOpenEditTicket;
    }

    public void setCheckOpenEditTicket(int checkOpenEditTicket) {
        this.checkOpenEditTicket = checkOpenEditTicket;
    }

    public void addListenerHotKey(Node node, EventHandler eventHandler){

       node.addEventFilter(KeyEvent.KEY_PRESSED,eventHandler);
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

    public abstract void addListenerHotKey(Node node);
}
