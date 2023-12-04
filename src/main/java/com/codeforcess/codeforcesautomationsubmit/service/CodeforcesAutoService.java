package com.codeforcess.codeforcesautomationsubmit.service;

import com.codeforcess.codeforcesautomationsubmit.exception.SubmitException;
import com.codeforcess.codeforcesautomationsubmit.models.ProblemSubmitData;
import com.codeforcess.codeforcesautomationsubmit.models.ProblemSubmitResult;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
public class CodeforcesAutoService {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @Value("${codeforces.username}")
    final String USER_NAME = "YOUR USERNAME";

    @Value("${codeforces.password}")
    final String PASSWORD = "YOUR PASSWORD";
    final String PATH = "E:\\works\\Projects java\\codeforces-automation-sumtit\\src\\main\\resources\\CodeFile";

    Set<Cookie> cookieSet;

    @Autowired
    public CodeforcesAutoService(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver , Duration.ofSeconds(15));
    }

    public ProblemSubmitResult submit(String problemCode , ProblemSubmitData data){
        try {

            if(!isLogin()) {
                login();
            }
            else {
                driver.get("https://codeforces.com/problemset/submit");
            }

            wait.until(driver -> driver.findElement(By.className("submit-form")));

            submitCode(problemCode , data);

            // getting status of submitting
            WebElement status = driver.findElement(By.className("status-cell"));
            while(status.getText().contains("queue") || status.getText().contains("Running") ){
                status = driver.findElement(By.className("status-cell"));
            }
            
            WebElement time = driver.findElement(By.className("time-consumed-cell"));
            WebElement memory = driver.findElement(By.className("memory-consumed-cell"));

            System.out.println(time.getText());
            System.out.println(memory.getText());
            System.out.println(memory.getText());

            ProblemSubmitResult res = new ProblemSubmitResult();
            res.setMemory(memory.getText());
            res.setVerdict(status.getText());
            res.setSubmitTime(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a").format(LocalDateTime.now()));
            res.setTime(time.getText());

            return res;
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            throw new SubmitException("Submission Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void submitCode(String problemCode , ProblemSubmitData data){
        try {

            if(problemCode.isEmpty() || data.getSolutionCode().isEmpty() || data.getCompilerId() <= 0){
                throw new RuntimeException("INVALID INPUT AT REQUEST BODY");
            }

            Files.writeString(Path.of(PATH) , data.getSolutionCode() , StandardOpenOption.TRUNCATE_EXISTING);
            // get submission elements
            WebElement submittedProblemCode = driver.findElement(By.name("submittedProblemCode"));
            Select lang = new Select(driver.findElement(By.name("programTypeId")));
            WebElement singlePageSubmitButton = driver.findElement(By.id("singlePageSubmitButton"));
//            WebElement textArea = driver.findElement(By.className("ace_text-input"));
            WebElement chooseFile = driver.findElement(By.name("sourceFile"));

            System.out.println(data.getSolutionCode());

            // send data
            submittedProblemCode.sendKeys(problemCode);
//            textArea.clear();
//            textArea.sendKeys(data.getCode());
            chooseFile.sendKeys(PATH);
            lang.selectByValue(String.valueOf(data.getCompilerId()));
            singlePageSubmitButton.submit();

            wait.until(driver -> driver.findElement(By.className("status-frame-datatable")));

        }catch (Exception exception){
            System.out.println(exception.getMessage());
            throw new SubmitException("FAil to Submission may be the code submitted before", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private void login(){
        try {
            driver.get("https://codeforces.com/enter?back=/problemset/submit");
            WebElement userName = driver.findElement(By.name("handleOrEmail"));
            WebElement password = driver.findElement(By.name("password"));
            WebElement submit = driver.findElement(By.className("submit"));
            userName.sendKeys(USER_NAME);
            password.sendKeys(PASSWORD);
            submit.submit();
        }catch (Exception exception){
            System.out.println("FAIL TO LOGIN");
            throw new SubmitException("FAIL TO LOGIN", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private boolean isLogin(){
        Cookie cookie = driver.manage().getCookieNamed("X-User-Sha1");
        return cookie != null && !cookie.getValue().isEmpty();
    }
}
