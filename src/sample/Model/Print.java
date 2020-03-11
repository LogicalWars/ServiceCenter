package sample.Model;


import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.Parent;
import sample.Controller.PrintTableController;

import java.io.IOException;

public class Print {
    private double pgW;
    private double pgH;
    public void printed() throws IOException {
        System.out.println("printed");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/PrintTableView.fxml"));
        loader.setController(new PrintTableController());
        Parent pane = loader.load();
        PrinterJob job = PrinterJob.createPrinterJob();
        Printer printer = Printer.getDefaultPrinter();
        PrinterAttributes attribs = printer.getPrinterAttributes();
        JobSettings jobSettings = job.getJobSettings();
        PageLayout pageLayout = jobSettings.getPageLayout();
        pageLayout = printer.createPageLayout(Paper.A4,
        PageOrientation.PORTRAIT,Printer.MarginType.HARDWARE_MINIMUM);
        jobSettings.setPageLayout(pageLayout);
        pgW = pageLayout.getPrintableWidth();
        pgH = pageLayout.getPrintableHeight();
        if (job != null) {
            boolean success = job.printPage(pane);
            if (success) {
                job.endJob();
                System.out.println("печать завершена");
            }
        }
    }

    public double getPgW() {return pgW;}
    public double getPgH() {return pgH;}
}
