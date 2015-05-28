package org.janus.dict.actions;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.janus.actions.Action;
import org.janus.actions.ReadValue;
import org.janus.actions.WriteValue;
import org.janus.data.Configurable;
import org.janus.data.DataContext;
import org.janus.data.DataDescription;
import org.janus.data.NeedDataInContext;

public class PageValue extends PageAction implements ReadValue, WriteValue  {

	
	private ReadValue read = null;
	private WriteValue write = null;
	private Action action = null;
	

	
	public PageValue(Object kern) {
		super(kern);
		if (kern instanceof ReadValue) {
			read = (ReadValue)kern;
		}
		if (kern instanceof WriteValue) {
			write = (WriteValue)kern;
		}
		if (kern instanceof Action) {
			action = (Action)kern;
		}		
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see janus.tech.model.Action1#perform(janus.tech.model.DataContext)
	 */
	
	@Override
	public void perform(DataContext data)  {
		log.debug("Action on=" + isOn(data));
		if (isOn(data) && action != null) {
			action.perform(data);
			fireActionIsPerformed(data);
		}
	}
	
	
	@Override
	public boolean hasContextData() {
		return (kern instanceof NeedDataInContext);
	}

	@Override
	public void configure(DataDescription description) {
		if (kern instanceof Configurable) {
			((Configurable)kern).configure(description);
		}
		
	}
	

	@Override
	protected void setAction(Action kern) {
		this.kern = kern;
	}

	@Override
	public void setObject(DataContext ctx,Serializable value) {
		if (isOn(ctx) && write != null) {
			write.setObject(ctx, value);
		}
		fireActionIsPerformed(ctx);
	}


	@Override
	public Serializable getObject(DataContext ctx) {
		if (read != null) {
			return read.getObject(ctx);
		}
		return null;
	}

	@Override
	public void debug(Logger log, DataContext data) {
		if (log.isDebugEnabled()) {
			log.debug(toString());
			if (data != null && hasContextData()) {
				Object v = data.getObject(index);
				log.debug("Data: [" + v + "]");
			}
		}
	}
	

}
