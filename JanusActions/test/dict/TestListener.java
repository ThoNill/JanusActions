package dict;

import org.janus.data.DataContext;
import org.janus.dict.interfaces.ActionListener;

public class TestListener  implements ActionListener {
	public int count = 0;
	
	public TestListener()  {
	}

	@Override
	public void actionPerformed(Object a, DataContext data) {
		count++;
		
	}

}
