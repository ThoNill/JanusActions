package dict;

import org.janus.actions.Action;
import org.janus.data.DataContext;
import org.janus.data.DataDescription;

public class TestAction  implements Action {
	public int count = 0;
	
	public TestAction()  {
	}
	

	@Override
	public void configure(DataDescription description) {
	}

	@Override
	public void perform(DataContext context) {
		count++;
	}

}
