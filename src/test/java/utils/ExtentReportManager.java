package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    public static ExtentReports crearReporte(String nombreReporte){
        ExtentSparkReporter reporter = new ExtentSparkReporter("reports/" + nombreReporte + ".html");

        reporter.config().setDocumentTitle("Reporte del Test");
        reporter.config().setReportName("Reporte de Automatización");

        ExtentReports extent = new ExtentReports();

        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester","Rafael Baruk Reyna Contreras");
        return extent;

    }
}
