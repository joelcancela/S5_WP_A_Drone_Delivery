package fr.unice.polytech.si3.dda.scheduler;

import fr.unice.polytech.si3.dda.exception.MalformedContextException;
import fr.unice.polytech.si3.dda.util.Coordinates;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by alexh on 17/01/2017.
 */
public class SchedulerTest {
    @Test
    public void computeStrat() throws Exception {

    }

    @Test
    public void scheduleWithAMalformedBody() throws Exception {

        Context context = new Context.ContextBuilder(4, 4, 3, 25, 150)
                .addProducts(new ArrayList<>())
                .addWarehouse(new Coordinates(0, 0))
                .build();

        Scheduler scheduler = new Scheduler(context, true);
        scheduler.schedule();
        List<String> str = Files.readAllLines(Paths.get("scheduler.out"));
        new File("scheduler.out").delete();

        assertEquals("0 W 25", str.get(0));
        assertEquals("1 W 25", str.get(1));
        assertEquals("2 W 25", str.get(2));
    }


    @Test(expected = MalformedContextException.class)
    public void scheduleWithAnEmptyBody() throws Exception{
        Scheduler scheduler = new Scheduler(null, true);
        scheduler.schedule();
    }


}