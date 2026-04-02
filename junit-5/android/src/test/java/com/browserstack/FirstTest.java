package com.browserstack;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FirstTest extends BrowserStackJUnitTest{

  @Test
  void test() throws IOException, InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

    // Try accessibilityId first; fall back to the search container view id
    // (Wikipedia Alpha's search bar content-desc changed in some app versions)
    WebElement searchElement = wait.until(driver -> {
      try {
        WebElement el = driver.findElement(AppiumBy.accessibilityId("Search Wikipedia"));
        if (el.isDisplayed()) return el;
      } catch (NoSuchElementException ignored) {}
      try {
        WebElement el = driver.findElement(AppiumBy.id("org.wikipedia.alpha:id/search_container"));
        if (el.isDisplayed()) return el;
      } catch (NoSuchElementException ignored) {}
      return null;
    });
    searchElement.click();

    WebElement insertTextElement = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30)).until(
        ExpectedConditions.elementToBeClickable(AppiumBy.id("org.wikipedia.alpha:id/search_src_text")));
    insertTextElement.sendKeys("BrowserStack");

    Thread.sleep(5000);

    List<WebElement> allProductsName = driver.findElements(AppiumBy.className("android.widget.TextView"));
    assertTrue(allProductsName.size() > 0);
  }
}
