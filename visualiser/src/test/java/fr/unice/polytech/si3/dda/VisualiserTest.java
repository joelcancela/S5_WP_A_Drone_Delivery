package fr.unice.polytech.si3.dda;

import fr.unice.polytech.si3.dda.common.Context;
import fr.unice.polytech.si3.dda.instruction.Instruction;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class VisualiserTest {

	@Rule
	public TemporaryFolder temp = new TemporaryFolder();

	String CONTEXT_NAME = "context.in";
	String SCHEDULE_NAME = "schedule.out";
	Context ctx;
	File context, schedule;
	List<Instruction> instructionsList;

	@Before
	public void setUp() throws Exception {
		context = new File(temp.getRoot(), CONTEXT_NAME);
		BufferedWriter wrt = new BufferedWriter(new FileWriter(context));
		wrt.write("7 10 2 25 500\n"
				+ "3\n"
				+ "100 5 450\n"
				+ "1\n"
				+ "0 0\n"
				+ "5 1 1\n"
				+ "1\n"
				+ "5 5\n"
				+ "3\n"
				+ "2 0 0\n");
		wrt.close();
		ctx = new ContextParser(context.getAbsolutePath()).parse();
		schedule = new File(temp.getRoot(), SCHEDULE_NAME);
		wrt = new BufferedWriter(new FileWriter(schedule));
		wrt.write("0 L 0 0 2\n"
				+ "0 D 0 0 2\n"
				+ "1 W 2\n"
				+ "1 L 0 2 1\n"
				+ "1 D 0 2 1\n");
		wrt.close();
		instructionsList = new ScheduleParser(schedule.getAbsolutePath()).parse();
	}

	@Test
	public void testDisplay() throws Exception {
		new Visualiser(ctx, instructionsList).display();
	}

}
