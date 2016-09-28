package org.janus.dict.actions;

import java.util.PriorityQueue;

import org.apache.log4j.Logger;
import org.janus.actions.Action;
import org.janus.data.DataContext;
import org.janus.data.DataDescription;
import org.janus.data.NeedDataInContext;
import org.janus.dict.interfaces.ActionListener;
import org.janus.dict.interfaces.BooleanValue;

/**
 * 
 * @author Thomas Nill
 * 
 *         Default Implementierung einer {@link Action}
 * 
 */
public class PageAction extends ActionEventSender implements Action,ActionListener,
		Comparable<PageAction> {
	Logger log = Logger.getLogger(PageAction.class);

	private static final long serialVersionUID = -1153454103549307177L;

	/**
	 * eindeutiger Index je {@link ActionDictionary} index in das Array des
	 * DataContextl;
	 */
	protected int index;

	/** Priorität bei der Aberbeitung von {@link Action} */
	private int priority=0;
	/** schaltet die Verarbeitung an oder aus */
	boolean on = true;

	BooleanValue booleanValue = null;

	protected Action kern = null;
	private ActionListener listen = null;

	public PageAction(Object kern) {
		super();
		if (kern instanceof Action) {
			this.kern = (Action) kern;
		}
		if (kern instanceof ActionListener) {
			this.listen = (ActionListener) kern;
		}
	}

	public BooleanValue getBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(BooleanValue booleanValue) {
		this.booleanValue = booleanValue;
	}

	public void debug(String text) {
		Logger l = Logger.getLogger(getClass().getSimpleName());
		if (l.isDebugEnabled()) {
			l.debug(toString());
			l.debug(text);
		}
	}


	public void error(String text) {
		Logger l = Logger.getLogger(getClass().getSimpleName());
		l.error(toString());
		l.error(text);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see janus.tech.model.Action1#isOn(janus.tech.model.DataContext)
	 */
	public boolean isOn(DataContext data) {
		if (on && booleanValue != null) {
			return booleanValue.isOk(data);
		}
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see janus.tech.model.Action1#getPriority()
	 */

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see janus.tech.model.Action1#getIndex()
	 */
	public int getContextIndex() {
		return index;
	}

	public void setContextIndex(int index) {
		this.index = index;
		if (kern instanceof NeedDataInContext) {
			((NeedDataInContext)kern).setContextIndex(index);
		}
	}

	/**
	 * Wenn eine {@link Action} beendet ist, teilt sie dies Ihren
	 * {@link ActionListener} mit, diese melden sich im {@link DataContext} zur
	 * späteren Abarbeitung an. Diese Mitteilung wir im {@link DataContext} in
	 * einer {@link PriorityQueue} gespeichert.
	 * 
	 * */

	@Override
	public void actionPerformed(Object a, DataContext data) {
		log.debug("Action performed " + toString());
		if (listen != null) {
			listen.actionPerformed(a, data);
			fireActionIsPerformed(data);
		} else {
			((ActionDictionaryContext) data).performLater(this);
		}
	}

	@Override
	public int compareTo(PageAction o) {
		return getPriority() - o.getPriority();
	}

	@Override
	public void perform(DataContext data) {
		log.debug("Action  on=" + isOn(data));
		if (isOn(data) && kern != null) {
			kern.perform(data);
			fireActionIsPerformed(data);
		}
	}

	public boolean hasContextData() {
		return (kern instanceof NeedDataInContext);
	}

	@Override
	public void configure(DataDescription description) {
		kern.configure(description);

	}

	public Action getAction() {
		return kern;
	}

	protected void setAction(Action kern) {
		this.kern = kern;
	}

	public void debug(Logger log, DataContext context) {
		
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((kern == null) ? 0 : kern.hashCode());
        result = prime * result + priority;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PageAction other = (PageAction) obj;
        if (kern == null) {
            if (other.kern != null)
                return false;
        } else if (!kern.equals(other.kern))
            return false;
        if (priority != other.priority)
            return false;
        return true;
    }



	

}

