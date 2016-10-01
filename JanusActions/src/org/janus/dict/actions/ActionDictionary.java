package org.janus.dict.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.List; import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.janus.actions.Action;
import org.janus.data.DataContext;
import org.janus.data.DataDescription;
import org.janus.data.DataDescriptionImpl;
import org.janus.dict.interfaces.NeedCompletion;

/**
 * 
 * @author Thomas Nill
 * 
 *         Ein {@link ActionDictionary} entspricht einer Klasse, der
 *         {@link DataContext} einer Klasseninstanz
 * 
 */
public class ActionDictionary extends DataDescriptionImpl implements Action, NeedCompletion {
	public static final Logger LOG = Logger.getLogger(ActionDictionary.class);
	
	private static final long serialVersionUID = 1025512692106740806L;

	private HashMap<String, NamedActionValue> actionMap;
	private List<NamedActionValue> actionList;
	private String name;


	public ActionDictionary(String name) {
		super();
		this.name = name;
		actionMap = new HashMap<String, NamedActionValue>();
		actionList = new ArrayList<NamedActionValue>();
	}

	public List<NamedActionValue> getActionList() {
		return actionList;
	}
	
	public synchronized NamedActionValue addAction(String name,Action k) {
		NamedActionValue a = new NamedActionValue(name,k) ;
		LOG.debug("DataModel addAction " + a.getName());
		if (a.hasContextData()) {
			a.setContextIndex(getHandle(name));
		}
		actionList.add(a);
		actionMap.put(a.getName(), a);
		a.setModel(this);
		return a;
	}

	public NamedActionValue getAction(String name) {
		if (!actionMap.containsKey(name)) {
			LOG.error("Fehler: " + name + " fehlt");
			return null;
		}
		return actionMap.get(name);
	}

	public Action getAction(int index) {
		return actionList.get(index);
	}

	@Override
	public int getSize() {
		return actionList.size();
	}

	public DataContext createDataContext() {
		return new ActionDictionaryContext(this);
	}

	
	@Override
	public void perform(DataContext data)  {
		((ActionDictionaryContext)data).performAllActions();
	}

	
	public void actionPerformed(Object a, DataContext data) {

	}

		
	@Override
	public void completeObject() {

		Iterator<NamedActionValue> i = actionList.iterator();
		while (i.hasNext()) {
			Object obj = i.next();
			if (obj instanceof NeedCompletion) {
				((NeedCompletion) obj).completeObject();
			}
		}

	}



	public void debug(Logger log, DataContext context) {
		if (log.isDebugEnabled()) {
			log.debug("Log ActionDictionary " + getName());
			for (PageAction a : actionList) {
				a.debug(log,context);
			}
		}
	}

	public void debug(DataContext context) {
		Logger logger = Logger.getLogger(getClass().getSimpleName());
		debug(logger, context);
	}

	@Override
	public void configure(DataDescription description) {
		configure();
	};
	
	public void configure() {
		for(Action a : actionList) {
			a.configure(this);
		}
	}

	public String getName() {
		return name;
	};
}
