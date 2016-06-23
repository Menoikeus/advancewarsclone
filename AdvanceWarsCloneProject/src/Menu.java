import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.TrueTypeFont;

public class Menu {
	MenuType mType;
	ArrayList<String> options;
	int selected;
	
	public Menu(MenuType mT)
	{
		mType = mT;
		options = mType.getOptions();
		
		selected = 0;
	}
	public void addToSelected(int x)
	{
		selected += x;
		
		if(selected >= options.size())
			selected = options.size() - 1;
		if(selected < 0)
			selected = 0;
	}
	public int getSelected()
	{
		return selected;
	}
	public ArrayList<String> getOptionsList()
	{
		return options;
	}
	
	public String getOptionSelected()
	{
		return options.get(selected);
	}
}
