package dict;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.janus.actions.Action;
import org.janus.dict.actions.ActionDictionary;
import org.janus.dict.actions.ActionDictionaryContext;
import org.janus.dict.actions.ActionEventSender;
import org.janus.dict.actions.PageAction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ActionDictionaryTest {

	@Before
	public void init() {
		ActionEventSender.LOG.setLevel(Level.ALL);
		ConsoleAppender con = new ConsoleAppender(new PatternLayout(
				"%-5p [%t]: %m%n "));
		ActionEventSender.LOG.addAppender(con);
	}

	@Test
	public void testAnlegen() {

		ActionDictionary dict = new ActionDictionary("test");
		ActionDictionaryContext ctx = (ActionDictionaryContext) dict
				.createDataContext();

		Assert.assertNotNull(ctx);

	}

	@Test
	public void testSendenUndDaraufHören() {
		ActionDictionary dict = new ActionDictionary("test");
		ActionDictionaryContext ctx = (ActionDictionaryContext) dict
				.createDataContext();

		ActionEventSender sender = new ActionEventSender();
		TestListener tester = new TestListener();

		sender.addActionListener(tester);

		sender.fireActionIsPerformed(ctx);

		Assert.assertEquals(tester.count, 1);

		sender.removeActionListener(tester);

		sender.fireActionIsPerformed(ctx);

		Assert.assertEquals(tester.count, 1);

	}

	@Test
	public void testPageActionsErzeugen() {
		ActionDictionary dict = new ActionDictionary("test");

		TestAction a = new TestAction();

		PageAction action = new PageAction(a);

		dict.addAction("action", action);

		ActionDictionaryContext ctx = (ActionDictionaryContext) dict
				.createDataContext();

		Action a2 = dict.getAction("action");

		Assert.assertNotEquals(a2, action);

	}

	@Test
	public void testActionAuslösen() {
		ActionDictionary dict = new ActionDictionary("test");

		TestAction a = new TestAction();

		dict.addAction("action", a);

		ActionDictionaryContext ctx = (ActionDictionaryContext) dict
				.createDataContext();

		Action a2 = dict.getAction("action");

		a2.perform(ctx);

		Assert.assertEquals(1, a.count);

	}

	@Test
	public void testWertInitialisieren() {
		ActionDictionary dict = new ActionDictionary("test");

		TestStringValue a = new TestStringValue();

		a.setDefaultValue("default");

		dict.addAction("action", a);

		ActionDictionaryContext ctx = (ActionDictionaryContext) dict
				.createDataContext();

		Action a2 = dict.getAction("action");

		a2.perform(ctx);

		Assert.assertEquals("default", a.getObject(ctx));

	}

	@Test
	public void testWertAktualisierenUndDaraufHören() {
		ActionDictionary dict = new ActionDictionary("test");

		TestStringValue a = new TestStringValue();

		a.setDefaultValue("default");

		dict.addAction("action", a);

		ActionDictionaryContext ctx = (ActionDictionaryContext) dict
				.createDataContext();

		PageAction a2 = dict.getAction("action");

		TestListener tester = new TestListener();

		a2.addActionListener(tester);

		a2.perform(ctx);

		Assert.assertEquals("default", a.getObject(ctx));

		Assert.assertEquals(1, tester.count);

	}


	@Test
	public void testPriorität1() {

		ActionDictionary dict = new ActionDictionary("test");

		TestAction a1 = new TestAction();
		TestAction b1 = new TestAction();

		TestAction c2 = new TestAction();

		PageAction pa = dict.addAction("a1", a1);
		PageAction pb = dict.addAction("b1", b1);
		PageAction pc = dict.addAction("c2", c2);

		pa.addActionListener(pc);
		pb.addActionListener(pc);
		pa.addActionListener(pb);

		ActionDictionaryContext ctx = (ActionDictionaryContext) dict
				.createDataContext();

		ctx.performLater(pa);
		ctx.performAllActions();

		Assert.assertEquals(1, a1.count);
		Assert.assertEquals(1, b1.count);
		Assert.assertEquals(2, c2.count);

	}

	@Test
	public void testPriorität2() {
		ActionDictionary dict = new ActionDictionary("test");
		
		TestAction a1 = new TestAction();
		TestAction b1 = new TestAction();
		
		TestAction c2 = new TestAction();
		
		PageAction pa = dict.addAction("a1", a1);
		PageAction pb = dict.addAction("b1", b1);
		PageAction pc = dict.addAction("c2", c2);
		pc.setPriority(2);
		
		pa.addActionListener(pc);
		pb.addActionListener(pc);
		pa.addActionListener(pb);
		
		ActionDictionaryContext ctx = (ActionDictionaryContext) dict
				.createDataContext();
		
		ctx.performLater(pa);
		ctx.performAllActions();
		
		Assert.assertEquals(1, a1.count);
		Assert.assertEquals(1, b1.count);
		Assert.assertEquals(1, c2.count);
		
	}
}
