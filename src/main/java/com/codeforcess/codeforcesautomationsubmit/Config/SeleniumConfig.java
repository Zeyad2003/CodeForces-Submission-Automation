package com.codeforcess.codeforcesautomationsubmit.Config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfig {

    @Bean
    public WebDriver getDriver(){
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless=new");
        return new FirefoxDriver(options);
    }

}
