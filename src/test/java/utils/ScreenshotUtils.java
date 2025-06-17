package utils;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ScreenshotUtils {
    public static Media adjunScreenshot(WebDriver driver, ExtentTest test, String nombre){

        String folderPath = "reports/screenshots/";
        new File(folderPath).mkdirs();
        String fileName = nombre + "_" + UUID.randomUUID() + ".png";
        String relativePath = "screenshots/" + fileName;
        String fullPath = folderPath + fileName;
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(fullPath);


            try {
                FileUtils.copyFile(src, dest);
                return  MediaEntityBuilder.createScreenCaptureFromPath(relativePath).build();


            } catch (IOException e) {
                test.warning("No se pudo adjuntar screenshot: " + e.getMessage());
                return null;
            }

    }


}
