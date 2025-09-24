package common.testresultlogger;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;


    /**
     * This class handles the capture of screenshot upon Selenium based test case failure.
     * It also logs when Junit reports status upon on a test case failure, success, if it is aborted
     * or disabled. It's methods are invoked if an instance of this class is created and registered with the annotation
     * @RegisterExtension.
     * Note! If Test class is not Selenium base, pls use TestResult class instead.
     */
    public class TestResultCaptureScreen implements TestWatcher {

        Logger logger = Logger.getLogger(this.getClass().getName());
        WebDriver driver;
        String path;

        /**
         * Constructor to be used in GUI tests
         * @param driver Current driver
         * @param path Path to screenshot storage, in case of GUI test failure
         */
        public TestResultCaptureScreen(WebDriver driver, String path) {
            this.driver = driver;
            this.path = path;
        }

        /**
         * Logs package name, test class name and test case if a test case is aborted
         * @param context the current extension context; never {@code null}
         * @param throwable the throwable responsible for the test being aborted; may be {@code null}
         */
        @Override
        public void testAborted(ExtensionContext context, Throwable throwable) {
            logger.info("Test Aborted for test {" + context.getUniqueId() +  "} ");
        }

        /**
         * Logs package name, test class name and test case if a test case is Disabled
         * @param context the current extension context; never {@code null}
         * @param reason the reason the test is disabled; never {@code null} but
         * potentially <em>empty</em>
         */
        @Override
        public void testDisabled(ExtensionContext context, Optional<String> reason) {
            logger.info("{" + context.getUniqueId() + "} DISABLED because of reason :- {"
                    + reason.orElse("No reason") + "}");
        }

        /**
         * Captures screenshot and logs package name, test class name and test case if a test case fails.
         * @param context the current extension context; never {@code null}
         * @param throwable the throwable that caused test failure; may be {@code null}
         */
        @Override
        public void testFailed(ExtensionContext context, Throwable throwable) {
            ensureDriver(driver);
            captureScreenshot(driver, sanitizeFileName(context.getDisplayName()));
            logger.info(String.format("%nTEST END (FAILED): %s %n", context.getDisplayName()));
        }

        /**
         * Logs package name, test class name and test case if a test case goes successful
         * @param context the current extension context; never {@code null}
         */
        @Override
        public void testSuccessful(ExtensionContext context) {
            logger.info(String.format("%nTEST END (PASSED): %s %n", context.getDisplayName()));

        }

        /**
         * Captures a screenshot and saves it as PNG on disk
         * @param driver Current web driver
         * @param fileName Name of file
         */
        public void captureScreenshot(WebDriver driver, String fileName) {
            try {
                new File(path).mkdirs();
                try (FileOutputStream out = new FileOutputStream(path + File.separator + "screenshot-" + fileName + ".png")) {
                    out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
                    logger.info("Screenshot saved  " + path + File.separator + "screenshot-" + fileName + ".png");
                }
            } catch (IOException | WebDriverException e) {
                logger.info("screenshot failed:" + e.getMessage());
            }
        }


        /**
         * Method replaces any character that is not a letter, digit, dot, or hyphen with an underscore.
         * @param name that should be sanitized
         * @return String name valid for files
         */
        private String sanitizeFileName(String name) {
            return name.replaceAll("[^a-zA-Z0-9.-]", "_");
        }

        /**
         * Ensures that driver is set
         * @param driver Current web driver
         */
        public void ensureDriver(WebDriver driver) {
            if (this.driver == null) {
                this.driver = driver;
            }
        }

    }
