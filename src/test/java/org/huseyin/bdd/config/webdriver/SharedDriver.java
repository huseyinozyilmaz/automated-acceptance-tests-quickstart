package org.huseyin.bdd.config.webdriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class SharedDriver extends EventFiringWebDriver {

    private static WebDriver REAL_DRIVER;
    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            quitGlobalInstance();
        }
    };

    static {
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    public SharedDriver() {
        super(getDriver());
    }

    private static void quitGlobalInstance() {
        if (REAL_DRIVER != null) {
            REAL_DRIVER.close();
            REAL_DRIVER.quit();
        }
    }

    private static WebDriver getDriver() {
        if (REAL_DRIVER == null) {
            REAL_DRIVER = WebDriverFactory.create();
        }
        return REAL_DRIVER;
    }

    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        try {
            super.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void quit() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException("You shouldn't quit this WebDriver. It's shared and will quit when the JVM exits.");
        }
        super.quit();
    }

    @Before
    public void deleteAllCookies() {
        System.out.println("SharedDriver:deleteAllCookies @Before");
        manage().deleteAllCookies();
    }

    @After
    public void embedScreenshotIfFailed(Scenario scenario) {
        System.out.println("SharedDriver:captureScreenshotIfFailed @After");
        if (scenario.isFailed()) {
            try {
                scenario.write("Current Page URL is " + getCurrentUrl());
                if (getWrappedDriver() instanceof TakesScreenshot) {
                    byte[] screenshot = getScreenshotAs(OutputType.BYTES);
                    scenario.embed(screenshot, "image/png");
                }
            } catch (Throwable somePlatformsDontSupportScreenshotsOrBrowserHasDied) {
                somePlatformsDontSupportScreenshotsOrBrowserHasDied.printStackTrace(System.err);
            }
        }
    }
}