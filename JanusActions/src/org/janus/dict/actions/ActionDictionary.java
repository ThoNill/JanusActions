package org.janus.dict.actions;

import java.util.HashMap;

import java.util.Iterator;
import java.util.Vector;

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
	public static Logger log = Logger.getLogger(ActionDictionary.class);
	
	private static final long serialVersionUID = 1025512692106740806L;

	private HashMap<String, NamedActionValue> actionMap;
	private Vector<NamedActionValue> actionVector;
	private String name;


	public ActionDictionary(String name) {
		super();
		this.name = name;
		actionMap = new HashMap<String, NamedActionValue>();
		actionVector = new Vector<NamedActionValue>();
	}

	public Vector<NamedActionValue> getActionVector() {
		return actionVector;
	}
	
	public synchronized NamedActionValue addAction(String name,Action k) {
		NamedActionValue a = new NamedActionValue(name,k) ;
		log.debug("DataModel addAction " + a.getName());
		if (a.hasContextData()) {
			a.setContextIndex(getHandle(name));
		}
		actionVector.add(a);
		actionMap.put(a.getName(), a);
		a.setModel(this);
		return a;
	}

	public NamedActionValue getAction(String name) {
		if (!actionMap.containsKey(name)) {
			log.error("Fehler: " + name + " fehlt");
			return null;
		}
		return actionMap.get(name);
	}

	public Action getAction(int index) {
		return actionVector.get(index);
	}

	@Override
	public int getSize() {
		return actionVector.size();
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

		Iterator<NamedActionValue> i = actionVector.iterator();
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
			for (PageAction a : actionVector) {
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
		for(Action a : actionVector) {
			a.configure(this);
		}
	}

	public String getName() {
		return name;
	};
}
