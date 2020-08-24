package spy;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SpyWareApp {
    public static void main(String[] args) {
        new SpyWare().scheduledSpyWork();
    }
}