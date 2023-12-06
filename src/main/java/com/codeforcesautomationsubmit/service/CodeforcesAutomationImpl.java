package com.codeforcesautomationsubmit.service;

import com.codeforcesautomationsubmit.exception.SubmitException;
import com.codeforcesautomationsubmit.models.SubmissionInfo;
import com.codeforcesautomationsubmit.models.SubmissionResult;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CodeforcesAutomationImpl implements CodeforcesAutomation {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @Value("${CodeForces.username}")
    String USERNAME;

    @Value("${CodeForces.password}")
    String PASSWORD;

    final String PATH = "CodeFile";

    @Autowired
    public CodeforcesAutomationImpl(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Override
    public SubmissionResult submit(String problemCode, SubmissionInfo data) {
        try {
            verifyLogin();

            wait.until(driver -> driver.findElement(By.className("submit-form")));

            submitCode(problemCode, data);

            // getting status of submitting
            WebElement status = driver.findElement(By.className("status-cell"));
            while (status.getText().contains("queue") || status.getText().contains("Running")) {
                status = driver.findElement(By.className("status-cell"));
            }

            WebElement time = driver.findElement(By.className("time-consumed-cell"));
            WebElement memory = driver.findElement(By.className("memory-consumed-cell"));

            System.out.println(time.getText());
            System.out.println(memory.getText());
            System.out.println(memory.getText());

            SubmissionResult res = new SubmissionResult();
            res.setMemory(memory.getText());
            res.setVerdict(status.getText());
            res.setSubmitTime(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a").format(LocalDateTime.now()));
            res.setTime(time.getText());

            return res;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new SubmitException("Submission Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void submitCode(String problemCode, SubmissionInfo data) {
        try {

            if (problemCode.isEmpty() || data.getSolutionCode().isEmpty() || data.getCompilerId() <= 0) {
                throw new RuntimeException("INVALID INPUT AT REQUEST BODY");
            }

            // Updated this line to read the file from the resources folder
            String codeFilePath = new ClassPathResource(PATH).getFile().getPath();
            Files.writeString(Path.of(codeFilePath), data.getSolutionCode(), StandardOpenOption.TRUNCATE_EXISTING);

            // get submission elements
            WebElement submittedProblemCode = driver.findElement(By.name("submittedProblemCode"));
            Select lang = new Select(driver.findElement(By.name("programTypeId")));
            WebElement singlePageSubmitButton = driver.findElement(By.id("singlePageSubmitButton"));
            WebElement chooseFile = driver.findElement(By.name("sourceFile"));

            System.out.println(data.getSolutionCode());

            // send data
            submittedProblemCode.sendKeys(problemCode);
            chooseFile.sendKeys(codeFilePath);
            lang.selectByValue(String.valueOf(data.getCompilerId()));
            singlePageSubmitButton.submit();

            wait.until(driver -> driver.findElement(By.className("status-frame-datatable")));

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new SubmitException("Fail to submit. The code may have been submitted before.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void login() {
        try {
            driver.get("https://codeforces.com/enter?back=/problemset/submit");
            WebElement userName = driver.findElement(By.name("handleOrEmail"));
            WebElement password = driver.findElement(By.name("password"));
            WebElement loginButton = driver.findElement(By.className("submit"));
            WebElement rememberMe = driver.findElement(By.id("remember"));

            userName.sendKeys(USERNAME);
            password.sendKeys(PASSWORD);
            rememberMe.click();
            loginButton.submit();
        } catch (Exception exception) {
            System.out.println("FAIL TO LOGIN");
            throw new SubmitException("FAIL TO LOGIN", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void verifyLogin() {
        if (!isLogin()) login();
        else driver.get("https://codeforces.com/problemset/submit");
    }

    private boolean isLogin() {
        Cookie cookie = driver.manage().getCookieNamed("X-User-Sha1");
        return cookie != null && !cookie.getValue().isEmpty();
    }
}

