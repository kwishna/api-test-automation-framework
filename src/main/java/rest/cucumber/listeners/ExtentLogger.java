package rest.cucumber.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.markuputils.Markup;

/**
 * Can be removed, if not required.
 */
public final class ExtentLogger {

    private ExtentLogger() {
    }

    public static void pass(String message) {
        ExtentCucumberAdapter.getCurrentStep().pass(message);
    }

    public static void pass(Markup message) {
        ExtentCucumberAdapter.getCurrentStep().pass(message);
    }

    public static void fail(String message) {
        ExtentCucumberAdapter.getCurrentStep().fail(message);
    }

    public static void fail(Markup message) {
        ExtentCucumberAdapter.getCurrentStep().fail(message);
    }

    public static void skip(String message) {
        ExtentCucumberAdapter.getCurrentStep().skip(message);
    }

    public static void skip(Markup message) {
        ExtentCucumberAdapter.getCurrentStep().skip(message);
    }

    public static void info(Markup message) {
        ExtentCucumberAdapter.getCurrentStep().info(message);
    }

    public static void info(String message) {
        ExtentCucumberAdapter.getCurrentStep().info(message);
    }

    public static void pass(String message, boolean isScreenshotNeeded) {
        if (isScreenshotNeeded) {
//            ExtentCucumberAdapter.getCurrentStep().pass(message, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
            ExtentCucumberAdapter.getCurrentStep().pass(message);
        } else {
            pass(message);
        }
    }

    public static void pass(Markup message, boolean isScreenshotNeeded) {
        if (isScreenshotNeeded) {
//            ExtentCucumberAdapter.getCurrentStep().pass(MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
            ExtentCucumberAdapter.getCurrentStep().pass(message);
        } else {
            pass(message);
        }
    }

    public static void fail(String message, boolean isScreenshotNeeded) {
        if (isScreenshotNeeded) {
//            ExtentCucumberAdapter.getCurrentStep().fail(message, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
            ExtentCucumberAdapter.getCurrentStep().fail(message);
        } else {
            fail(message);
        }
    }

    public static void fail(Markup message, boolean isScreenshotNeeded) {
        if (isScreenshotNeeded) {
//            ExtentCucumberAdapter.getCurrentStep().fail(MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
            ExtentCucumberAdapter.getCurrentStep().fail(message);
        } else {
            fail(message);
        }
    }

    public static void skip(String message, boolean isScreenshotNeeded) {
        if (isScreenshotNeeded) {
//            ExtentCucumberAdapter.getCurrentStep().skip(message, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
            ExtentCucumberAdapter.getCurrentStep().skip(message);
        } else {
            skip(message);
        }
    }

    public static void skip(Markup message, boolean isScreenshotNeeded) {
        if (isScreenshotNeeded) {
//            ExtentCucumberAdapter.getCurrentStep().skip(MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
            ExtentCucumberAdapter.getCurrentStep().skip(message);
        } else {
            skip(message);
        }
    }

    public static void info(String message, MediaEntityBuilder builder) {
        ExtentCucumberAdapter.getCurrentStep().info(message, builder.build());
    }
}
