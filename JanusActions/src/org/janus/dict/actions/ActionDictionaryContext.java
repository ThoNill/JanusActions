package org.janus.dict.actions;

import java.util.PriorityQueue;

import org.apache.log4j.Logger;
import org.janus.data.DataContextImpl;


public class ActionDictionaryContext extends DataContextImpl{
    private static final Logger LOG = Logger.getLogger(ActionDictionaryContext.class);

	private PriorityQueue<PageAction> actionQueue;

	public ActionDictionaryContext(ActionDictionary dict) {
		super(dict);
		actionQueue = new PriorityQueue<>();
	}

	
	public boolean isTheActionEmpty() {
		return actionQueue.isEmpty();
	}

	public void performAllActions() {
		while (!actionQueue.isEmpty()) {
			try {
				performNextAction();
			} catch (Exception e) {
				LOG.error("Fehler in Action",e);
			}
		}

	}

	public void performNextAction()  {
		PageAction a = actionQueue.poll();
		a.perform(this);
	}

	public void performLater(PageAction a) {
		if (!actionQueue.contains(a)) {
			actionQueue.add(a);
		}
	}

}
