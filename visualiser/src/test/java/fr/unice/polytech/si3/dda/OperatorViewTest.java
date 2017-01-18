package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.order.Product;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class OperatorViewTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    String FILE_NAME = "new-file.txt";
    Context ctx;
    File file;

    @Before
    public void setUp() throws Exception {
        file = new File(temp.getRoot(), FILE_NAME);
        BufferedWriter wrt = new BufferedWriter(new FileWriter(file));
        wrt.write("7 10 2 25 500\n"
                + "3\n"
                + "100 5 450\n"
                + "1\n"
                + "0 0\n"
                + "5 1 0\n"
                + "1\n"
                + "1 1\n"
                + "3\n"
                + "2 0 0\n");
        wrt.close();
        ctx = new ContextParser(file.getAbsolutePath()).parse();
    }

    @Test
    public void testDisplay() throws Exception {
        ctx.getFleet().getDrone(0).load(new Product(100, 0));
        ctx.getFleet().getDrone(0).load(new Product(100, 0));
        new OperatorView(ctx, null).display();
    }

}
