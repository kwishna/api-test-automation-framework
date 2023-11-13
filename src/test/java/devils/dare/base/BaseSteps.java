package devils.dare.base;

import devils.dare.commons.utils.TestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class BaseSteps {

    private static final Logger LOGGER = LogManager.getLogger(BaseSteps.class);

    public TestContext ctx;

    public BaseSteps(TestContext context) {
        this.ctx = context;
    }

    private void startServices() {
        try {
            ProcessBuilder builder = new ProcessBuilder();
            Process process = builder.command("java -jar apichallenges.jar").start();
//            Process process = Runtime.getRuntime().exec("java -jar apichallenges.jar");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (process.isAlive()) process.destroyForcibly();
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
