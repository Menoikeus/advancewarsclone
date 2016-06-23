
import java.util.ArrayList;

public enum MenuType {
	MOVE("MOVE"), MOVE_OR_FIRE("MOVE_OR_FIRE");
	
	private ArrayList<String> options = new ArrayList<String>();
	private MenuType(String type)
	{
		if(type == "MOVE")
		{
			options.add("WAIT");	
		}
		if(type == "MOVE_OR_FIRE")
		{
			options.add("FIRE");	
			options.add("WAIT");	
		}
	}
	
	public ArrayList<String> getOptions()
	{
		return options;
	}
}
