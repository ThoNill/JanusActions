package org.janus.dict.actions;



import org.janus.actions.DefaultValue;
import org.janus.actions.ReadValue;
import org.janus.data.DataContext;
import org.janus.dict.interfaces.ActionListener;
import org.janus.single.ObjectCreator;

public class ListeningValue extends DefaultValue implements ActionListener{

	public ListeningValue(ObjectCreator creator) {
		super(creator);
	}

	@Override
	public void actionPerformed(Object a, DataContext ctx) {
		if (a instanceof ReadValue) {
			setObject(ctx, ((ReadValue)a).getObject(ctx));
		}
		
	}

	

}
