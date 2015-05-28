package dict;

import java.io.Serializable;

import org.janus.actions.Action;
import org.janus.actions.ReadValue;
import org.janus.actions.WriteValue;
import org.janus.data.DataContext;
import org.janus.data.DataDescription;
import org.janus.data.NeedDataInContext;

public class TestStringValue implements ReadValue, WriteValue, Action, NeedDataInContext{
	private int index;
	private String defaultValue; 
	
	public TestStringValue() {
		
	}

	@Override
	public void setContextIndex(int index) {
		this.index = index;
	}

	@Override
	public int getContextIndex() {
		return index;
	}

	@Override
	public void setObject(DataContext ctx, Serializable value) {
		ctx.setObject(index,value);
		
	}

	@Override
	public Serializable getObject(DataContext ctx) {
		return ctx.getObject(index);
	}

	@Override
	public void configure(DataDescription description) {
	}

	@Override
	public void perform(DataContext context) {
		setObject(context,defaultValue);		
	}

	protected String getDefaultValue() {
		return defaultValue;
	}

	protected void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
