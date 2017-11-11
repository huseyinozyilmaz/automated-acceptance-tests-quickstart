package org.huseyin.bdd.config.webdriver;

import static java.lang.String.format;
import static java.lang.System.getProperty;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

final class WebDriverFactory {

    private static String OS = System.getProperty("os.name").toLowerCase();

    private WebDriverFactory() {

    }

    static WebDriver create() {
        String chromeDriver = getProperty("webdriver.chrome.driver");
        String webDriverProperty = getProperty("webdriver");

        if (webDriverProperty == null || webDriverProperty.isEmpty()) {
            webDriverProperty = "CHROME";
        }

        if (chromeDriver == null || chromeDriver.isEmpty()) {
            File driverDirectory = new File("node_modules/chromedriver/lib/chromedriver/chromedriver");
            //chromeDriver = String.join(File.separator, resourcesDirectory.getAbsolutePath(), "chromedriver");
            chromeDriver = driverDirectory.getAbsolutePath();
            if (isWindows()) {
                chromeDriver = String.join(".", chromeDriver, "exe");
            }
            System.setProperty("webdriver.chrome.driver", chromeDriver);
        }

        try {
            return Drivers.valueOf(webDriverProperty.toUpperCase()).newDriver();
        } catch (IllegalArgumentException e) {
            String msg = format("The webdriver system property '%s' did not match any " +
                            "existing browser or the browser was not supported on your operating system. " +
                            "Valid values are %s",
                    webDriverProperty, stream(Drivers
                            .values())
                            .map(Enum::name)
                            .map(String::toLowerCase)
                            .collect(toList()));

            throw new IllegalStateException(msg, e);
        }
    }

    static boolean isWindows() {
        return (OS.contains("win"));
    }

    static boolean isMac() {
        return (OS.contains("mac"));
    }

    private enum Drivers {
        FIREFOX {
            @Override
            public WebDriver newDriver() {
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                return new FirefoxDriver(capabilities);
            }
        }, CHROME {
            @Override
            public WebDriver newDriver() {
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("disable-infobars");
                options.addArguments("start-maximized");
                options.addArguments("disable-extensions");
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                return new ChromeDriver(capabilities);
            }
        }, IE {
            @Override
            public WebDriver newDriver() {
                DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
                return new InternetExplorerDriver(capabilities);
            }
        }, EDGE {
            @Override
            public WebDriver newDriver() {
                DesiredCapabilities capabilities = DesiredCapabilities.edge();
                return new EdgeDriver(capabilities);
            }
        };

        public abstract org.openqa.selenium.WebDriver newDriver();
    }

}
