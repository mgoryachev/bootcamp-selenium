package ru.stqa.selenium.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import ru.stqa.selenium.app.Application;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin={"pretty"}
)
public class RunSpecs {

}
