package sample.Model;


import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.Parent;
import sample.Controller.PrintTableController;

import java.io.IOException;

public class Print {

    private double pgW;
    private double pgH;
    private String idPrint;
    private String labelTop;
    private String labelMiddle;
    private String labelBottomRight;
    private String labelBottomLeft;
    private String labelNameTicket;
    private String labelFullName;
    private String labelPhone;
    private String labelNote;
    private String labelDate;
    private String labelDevice;
    private String labelModel;
    private String labelCondition;

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
    public String getIdPrint() {return idPrint;}
    public String getLabelTop() {return labelTop;}
    public String getLabelMiddle() {return labelMiddle;}
    public String getLabelBottomRight() {return labelBottomRight;}
    public String getLabelBottomLeft() {return labelBottomLeft;}
    public String getLabelNameTicket() {return labelNameTicket;}
    public String getLabelFullName() {return labelFullName;}
    public String getLabelPhone() {return labelPhone;}
    public String getLabelNote() {return labelNote;}
    public String getLabelDate() {return labelDate;}
    public String getLabelDevice() {return labelDevice;}
    public String getLabelModel() {return labelModel;}
    public String getLabelCondition() {return labelCondition;}
}
