package model;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.util.concurrent.*;

@WebListener
public class AuctionManager implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        /*
        scheduler.scheduleAtFixedRate(new SomeDailyJob(), 0, 1, TimeUnit.DAYS);
        scheduler.scheduleAtFixedRate(new SomeHourlyJob(), 0, 1, TimeUnit.HOURS);
        scheduler.scheduleAtFixedRate(new SomeQuarterlyJob(), 0, 15, TimeUnit.MINUTES);
        */
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

    public void startAuction(Auction NewAuction){
        scheduler.scheduleAtFixedRate(NewAuction, 0, 3, TimeUnit.MINUTES);
    }

    public void stopAuction(ScheduledFuture<Auction> AuctionFuture){
        AuctionFuture.cancel(false);
    }

}