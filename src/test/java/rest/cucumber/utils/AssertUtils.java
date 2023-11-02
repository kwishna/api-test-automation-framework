package rest.cucumber.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matcher;
import org.junit.Assert;
import rest.cucumber.listeners.ExtentLogger;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;

public class AssertUtils {

    private static final Logger LOGGER = LogManager.getLogger(AssertUtils.class);

    public static <T> void assertEquals(T actual, T expected, String message) {
        try {
            assertThat(message, actual, equalTo(expected));
            ExtentLogger.pass(message + "<b><i>Actual: </i> </b>" + actual + " and <b><i> Expected: equal To </i> </b>" + expected);
//            ExtentCucumberAdapter.getCurrentStep().pass(message + "<b><i>Actual: </i> </b>" + actual + " and <b><i> Expected: equal To </i> </b>" + expected);
        } catch (AssertionError assertErr) {
            ExtentLogger.fail("<b><i>" + assertErr.getMessage() + "</i></b>");
//            ExtentCucumberAdapter.getCurrentStep().fail("<b><i>" + assertErr.getMessage() + "</i></b>");
            Assert.fail(assertErr.getMessage());
        }
    }

    public static <T> void matches(T actual, Matcher<? super T> matcher) {
        try {
            assertThat(actual, matcher);
            ExtentLogger.pass("<b><i>Actual: </i> </b>" + actual + " and <b><i> Expected: " + matcher.toString() + " </i> </b>");
//            ExtentCucumberAdapter.getCurrentStep().pass("<b><i>Actual: </i> </b>" + actual + " and <b><i> Expected: " + matcher.toString() + " </i> </b>");
        } catch (AssertionError assertErr) {
            ExtentLogger.fail("<b><i>" + assertErr.getMessage() + "</i></b>");
//            ExtentCucumberAdapter.getCurrentStep().fail("<b><i>" + assertErr.getMessage() + "</i></b>");
            Assert.fail(assertErr.getMessage());
        }
    }

    public static void assertContains(String actual, String expected, String message) {
        try {
            assertThat(message, actual, containsString(expected));
            ExtentLogger.pass(message + "<b><i>Actual: </i> </b>" + actual + " and <b><i> Expected: Contains String </i> </b>" + expected);
//            ExtentCucumberAdapter.getCurrentStep().pass(message + "<b><i>Actual: </i> </b>" + actual + " and <b><i> Expected: Contains String </i> </b>" + expected);
        } catch (AssertionError assertErr) {
            ExtentLogger.fail("<b><i>" + assertErr.getMessage() + "</i></b>");
//            ExtentCucumberAdapter.getCurrentStep().fail("<b><i>" + assertErr.getMessage() + "</i></b>");
            Assert.fail(assertErr.getMessage());
        }
    }

    public static void assertContainsIgnoreCase(String actual, String expected, String message) {
        try {
            assertThat(message, actual, containsStringIgnoringCase(expected));
            ExtentLogger.pass(message + "<b><i>Actual: </i> </b>" + actual + " and <b><i> Contains String IgnoringCase: </i> </b>" + expected);
//            ExtentCucumberAdapter.getCurrentStep().pass(message + "<b><i>Actual: </i> </b>" + actual + " and <b><i> Contains String IgnoringCase: </i> </b>" + expected);
        } catch (AssertionError assertErr) {
            ExtentLogger.fail("<b><i>" + assertErr.getMessage() + "</i></b>");
//            ExtentCucumberAdapter.getCurrentStep().fail("<b><i>" + assertErr.getMessage() + "</i></b>");
            Assert.fail(assertErr.getMessage());
        }
    }

    public static <T> void assertHasItem(List<T> actual, T expected, String message) {
        try {
            assertThat(message, actual, hasItem(expected));
            ExtentLogger.pass(message + "<b><i>Actual: </i> </b>" + actual + " and <b><i> Expected: Has Item </i> </b>" + expected);
//            ExtentCucumberAdapter.getCurrentStep().pass(message + "<b><i>Actual: </i> </b>" + actual + " and <b><i> Expected: Has Item </i> </b>" + expected);
        } catch (AssertionError assertErr) {
            ExtentLogger.fail("<b><i>" + assertErr.getMessage() + "</i></b>");
//            ExtentCucumberAdapter.getCurrentStep().fail("<b><i>" + assertErr.getMessage() + "</i></b>");
            Assert.fail(assertErr.getMessage());
        }
    }

    @SafeVarargs
    public static <T> void assertHasItems(List<T> actual, T... expected) {
        try {
            assertThat(actual, hasItems(expected));
            ExtentLogger.pass("<b><i>Actual: </i> </b>" + actual + " and <b><i> Has Items: </i> </b>" + Arrays.toString(expected));
//            ExtentCucumberAdapter.getCurrentStep().pass("<b><i>Actual: </i> </b>" + actual + " and <b><i> Has Items: </i> </b>" + Arrays.toString(expected));
        } catch (AssertionError assertErr) {
            ExtentLogger.fail("<b><i>" + assertErr.getMessage() + "</i></b>");
//            ExtentCucumberAdapter.getCurrentStep().fail("<b><i>" + assertErr.getMessage() + "</i></b>");
            Assert.fail(assertErr.getMessage());
        }
    }

    public static void assertTrue(boolean result, String message) {
        try {
            assertThat(message, result, is(true));
            ExtentLogger.pass("<b><i>" + message + "</b></i>");
//            ExtentCucumberAdapter.getCurrentStep().pass("<b><i>" + message + "</b></i>");
        } catch (AssertionError assertErr) {
            ExtentLogger.fail("<b><i>" + assertErr.getMessage() + "</i></b>");
//            ExtentCucumberAdapter.getCurrentStep().fail("<b><i>" + assertErr.getMessage() + "</i></b>");
            Assert.fail(assertErr.getMessage());
        }
    }

    public static void assertFalse(boolean result, String message) {
        try {
            assertThat(message, result, is(false));
            ExtentLogger.pass("<b><i>" + message + "</b></i>");
//            ExtentCucumberAdapter.getCurrentStep().pass("<b><i>" + message + "</b></i>");
        } catch (AssertionError assertErr) {
            ExtentLogger.fail("<b><i>" + assertErr.getMessage() + "</i></b>");
//            ExtentCucumberAdapter.getCurrentStep().fail("<b><i>" + assertErr.getMessage() + "</i></b>");
            Assert.fail(assertErr.getMessage());
        }
    }

}
