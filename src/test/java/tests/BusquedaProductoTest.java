package tests;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.aventstack.extentreports.*;
import org.testng.annotations.Test;
import java.util.List;
import utils.ExtentReportManager;
import utils.ScreenshotUtils;

public class BusquedaProductoTest {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setUp(){

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        extent = ExtentReportManager.crearReporte("Test Búsqueda de PlayStation 5");


    }

    @Test
    public void buscarProducto() throws InterruptedException {

        test = extent.createTest("Buscar playstation 5 en MercadoLibre");

        //Paso 1. Entrar al sitio web
        driver.get("https://www.mercadolibre.com");
        test.pass("Entrar al sitio web", ScreenshotUtils.adjunScreenshot(driver, test, "paso1"));
        //Paso 2. Seleccionar país México
        WebElement pais = driver.findElement(By.id("MX"));
        pais.click();
        test.pass("Seleccionar México como país", ScreenshotUtils.adjunScreenshot(driver, test, "paso2"));

        //Paso 3. Buscar "playstation 5"
        WebElement barraBusqueda = driver.findElement(By.id("cb1-edit"));
        barraBusqueda.sendKeys("playstation 5");
        barraBusqueda.submit();
        test.pass("Realizar busqueda de playstation 5", ScreenshotUtils.adjunScreenshot(driver, test, "paso3"));

        //Paso 4. Filtrar por condición de nuevos
        Thread.sleep(2000);
        WebElement condition = driver.findElement(By.xpath("//a[starts-with(@aria-label, 'Nuevo')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", condition);
        Thread.sleep(500);
        condition.click();
        test.pass("Filtrar por condición de nuevos", ScreenshotUtils.adjunScreenshot(driver, test, "paso4"));

        //Paso 5. Filtrar por ubicación de Cdmx o Distrito Federal
        Thread.sleep(2000);
        WebElement location = driver.findElement(By.xpath("//a[starts-with(@aria-label, 'Distrito Federal')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", location);
        Thread.sleep(500);
        location.click();
        test.pass("Filtrar por ubicación de cdmx o distrito federal", ScreenshotUtils.adjunScreenshot(driver, test, "paso5"));

        //Paso 6. Ordenar de mayor a menor precio
        Thread.sleep(2000);
        WebElement ordenarPor = driver.findElement(By.id(":R1b56ie:-trigger"));
        ordenarPor.click();

        WebElement mayorPrecio = driver.findElement(By.id(":R1b56ie:-menu-list-option-price_desc"));
        mayorPrecio.click();
        test.pass("Ordenar resultados de búsqueda del mayor al menor precio", ScreenshotUtils.adjunScreenshot(driver, test, "paso6"));

        //Paso 7. Obtener los nombres y precio de resultados de busqueda
        Thread.sleep(2500);
        List<WebElement> tituloResultados = driver.findElements(By.cssSelector(".poly-component__title"));
        List <WebElement> precioResultados = driver.findElements(By.cssSelector(".poly-price__current"));

        //Paso 8. Imprimir en la consola los 5 primeros resultados
        System.out.println("Primeros 5 resultados: ");
        for (int i = 0; i < 5; i++){
            String nombre = tituloResultados.get(i).getText();
            String precio = precioResultados.get(i).getText();
            System.out.println((i + 1) + ". " + nombre + " - " + precio);

            test.info((i + 1) + ". " + nombre + " - " + precio);
        }

        test.pass("Primeros 5 resultados obtenidos exitosamente", ScreenshotUtils.adjunScreenshot(driver, test, "paso7"));
    }

    @AfterClass
    public void finalizar(){
        driver.quit();
        extent.flush();
    }

}
