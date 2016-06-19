
public class MoveCalc {
	public static void startSearch(int[][] map, int[][] objectMap, int x, int y, float count)
	{
		recurseFind(map, objectMap, "bottom", count, x + 1, y);
		recurseFind(map, objectMap, "top", count, x - 1, y);
		recurseFind(map, objectMap, "left", count, x, y - 1);
		recurseFind(map, objectMap, "right", count, x, y + 1);
	}
	
	public static void recurseFind(int[][] map, int[][] objectMap, String side, float count, int x, int y)
	{
		if(map[x][y] == 0)
			count -= 1.0;
		else if(map[x][y] == 1)
			count -= 2.0;
		else if(map[x][y] == 2)
			count -= .5;
		
		System.out.println(count);
		
		if(!(count < -.01))
		{
			objectMap[x][y] = 1;
			
			if(side != "top" && x + 1 < map.length)
				recurseFind(map, objectMap, side, count, x + 1, y);
			if(side != "bottom" && x - 1 >= 0)
				recurseFind(map, objectMap, side, count, x - 1, y);
			if(side != "right" && y - 1 >= 0)
				recurseFind(map, objectMap, side, count, x, y - 1);
			if(side != "left" && y + 1 < map[0].length)
				recurseFind(map, objectMap, side, count, x, y + 1);
		}
			
	}
}
