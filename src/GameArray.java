import java.awt.event.*;
import java.util.Random;

public class GameArray {

	public int[][] gameNums = new int[4][4];
	public boolean[][] alreadyMerged = new boolean[4][4];
	private Random rand = new Random();
	public boolean isPredictor;
	
	
	//Constructor
	public GameArray(boolean isPredictor)
	{
		this.isPredictor = isPredictor;
		setupNums();
	}
	
	
	//Prints array in debug console
	public void printArray(boolean didMove) 
	{
		if (isPredictor != true)
		{
			for (int i = 0; i < 4; ++i) 
			{
				for (int j = 0; j < 4; ++j)
				{
					System.out.print(gameNums[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println("Moved?: " + didMove);
			System.out.println();
		}
	}
	
	
	//places the first 2 initial tiles
	public void setupNums()
	{
		newTile();
		newTile();
	}
	
	
	//Moves the game board in the direction of the key pressed, then adds a new tile if possible
	public void move(char direction)
	{
		
		int changeToI = 0;
		int changeToJ = 0;
		
		if (direction == 'U') {changeToI = -1; changeToJ = 0;}
		if (direction == 'L') {changeToI = 0; changeToJ = -1;}
		if (direction == 'D') {changeToI = 1; changeToJ = 0;}
		if (direction == 'R') {changeToI = 0; changeToJ = 1;}

		resetMergedMatrix();
		
		boolean didMove = false;
		
		
		//move Up or Left
		if (direction == 'U' || direction == 'L')
		{
				//this double loop selects a current tile (with i and j index), 
				//and moves it until it has either collided with the border or another tile
			for (int i = 0; i < 4; ++i)
			{
				for (int j = 0; j < 4; ++j)
				{
					int reps = 0;
					int currentTileValue = gameNums[i][j];
						
					try 
					{
						if (currentTileValue != 0)
						{
							while ((checkBorderCollision((i + (changeToI * reps)), (j + (changeToJ * reps)), direction) == false) 
								&& (checkTileCollision((i + (changeToI * reps)), (j + (changeToJ * reps)), direction) == false))
							{
								++reps;
								gameNums[i + (changeToI * reps)][j + (changeToJ * reps)] = currentTileValue;
								gameNums[(i + (changeToI * reps)) - changeToI][(j + (changeToJ * reps)) - changeToJ] = 0;
								didMove = true;
								
							}
							
							if ((checkTileCollision((i + (changeToI * reps)), (j + (changeToJ * reps)), direction) == true))
							{
								mergeLikeTiles((i + (changeToI * reps)), (j + (changeToJ * reps)), direction);
								if ((gameNums[(i + (changeToI * reps)) + changeToI][(j + (changeToJ * reps)) + changeToJ] == currentTileValue * 2)
									&& (gameNums[i + (changeToI * reps)][j + (changeToJ * reps)] == currentTileValue * 2))
									
								{
									didMove = true;
									
								} 
							}
			
						}
					}
					catch (Exception ArrayIndexOutOfBoundsException)
					{
						continue;
					}
				}
			}
		}
		//Move down or right
		else if (direction == 'D' || direction == 'R')
		{
			for (int i = 3; i >= 0; --i)
			{
				for (int j = 3; j >= 0; --j)
				{
					int reps = 0;
					int currentTileValue = gameNums[i][j];
					try 
						{
						if (currentTileValue != 0)
						{
							while ((checkBorderCollision((i + (changeToI * reps)), (j + (changeToJ * reps)), direction) == false) 
								&& (checkTileCollision((i + (changeToI * reps)), (j + (changeToJ * reps)), direction) == false))
							{
								++reps;
								gameNums[i + (changeToI * reps)][j + (changeToJ * reps)] = currentTileValue;
								gameNums[(i + (changeToI * reps)) - changeToI][(j + (changeToJ * reps)) - changeToJ] = 0;
								didMove = true;
								
							}
							if ((checkTileCollision((i + (changeToI * reps)), (j + (changeToJ * reps)), direction) == true))
							{
								mergeLikeTiles((i + (changeToI * reps)), (j + (changeToJ * reps)), direction);
								if ((gameNums[(i + (changeToI * reps)) + changeToI][(j + (changeToJ * reps)) + changeToJ] == currentTileValue * 2)
									&& (gameNums[i + (changeToI * reps)][j + (changeToJ * reps)] == 0))
								{
									didMove = true;
								} 
								
							}
						}
					}
					catch (Exception ArrayIndexOutOfBoundsException)
					{
						continue;
					}
				
				}
			}
		}
			if (didMove == true) {newTile();}
			printArray(didMove);
	}
	
	
	//merges tiles with same value
	public void mergeLikeTiles(int tileToDoubleI, int tileToDoubleJ, char direction)
	{
		int changeToI = 0;
		int changeToJ = 0;
		
		if (direction == 'U') {changeToI = -1; changeToJ = 0;}
		if (direction == 'L') {changeToI = 0; changeToJ = -1;}
		if (direction == 'D') {changeToI = 1; changeToJ = 0;}
		if (direction == 'R') {changeToI = 0; changeToJ = 1;} 
		
		
		if (((gameNums[tileToDoubleI][tileToDoubleJ] == gameNums[tileToDoubleI + changeToI][tileToDoubleJ + changeToJ])
			&& (alreadyMerged[tileToDoubleI + changeToI][tileToDoubleJ + changeToJ] == false)))
		{
			gameNums[tileToDoubleI + changeToI][tileToDoubleJ + changeToJ] = gameNums[tileToDoubleI + changeToI][tileToDoubleJ + changeToJ] * 2;
			gameNums[tileToDoubleI][tileToDoubleJ] = 0;
			alreadyMerged[tileToDoubleI + changeToI][tileToDoubleJ + changeToJ] = true;
		}
	}
	
	
	
	//checks if current tile had collided with another tile containing a value of higher than 0
	public boolean checkTileCollision(int currentTileI, int currentTileJ, char direction)
	{
		boolean tileCollision = false;
		
		
		if ((direction == 'U') 
			&& (gameNums[currentTileI - 1][currentTileJ] != 0))
		{
			tileCollision = true;
		}
		else if ((direction == 'D') 
			&& (gameNums[currentTileI + 1][currentTileJ] != 0))
		{
			tileCollision = true;
		}
		else if ((direction == 'L') 
			&& (gameNums[currentTileI][currentTileJ - 1] != 0))
		{
			tileCollision = true;
		}
		else if ((direction == 'R') 
			&& (gameNums[currentTileI][currentTileJ + 1] != 0))
		{
			tileCollision = true;
		}
		else
		{
			tileCollision = false;
		}
		
			return tileCollision;
	}
	
	
	//checks if current tile has collided with border
	public boolean checkBorderCollision(int currentTileI, int currentTileJ, char direction)
	{
		boolean borderCollision = false;
		
		if ((direction == 'U') && (currentTileI == 0))
		{
			borderCollision = true;
		}
		else if ((direction == 'D') && (currentTileI == 3))
		{
			borderCollision = true;
		}
		else if ((direction == 'L') && (currentTileJ == 0))
		{
			borderCollision = true;
		}
		else if ((direction == 'R') && (currentTileJ == 3))
		{
			borderCollision = true;
		}
		else
		{
			borderCollision = false;
		}
		
		return borderCollision;
	}
	
	
	//Places a new tile (2 or 4) in an empty location if there are empty spaces available
	public void newTile()
	{
		int newTileRow = rand.nextInt(4);
		int newTileColumn = rand.nextInt(4);
		int newTileValue = rand.nextInt(2);
		boolean tileIsPlaced = false;
		
		//Pick 2 or 4 for new tile
		if (newTileValue == 0) 
		{newTileValue = 2;}
		else {newTileValue = 4;}
		
		//places new tile only if there is an empty space
		if ((checkEmptyTiles() == true)) 
		{
			while (tileIsPlaced == false)
			{
				if (gameNums[newTileRow][newTileColumn] == 0)
				{
					gameNums[newTileRow][newTileColumn] = newTileValue;
					tileIsPlaced = true;
				}
				else
				{
					newTileRow = rand.nextInt(4);
					newTileColumn = rand.nextInt(4);
					continue;
				}
			}
		}
	}
	
	
	//resets all values in "alreadyMerged" to FALSE
	public void resetMergedMatrix()
	{
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				alreadyMerged[i][j] = false;
			}
		}
	}
	
	
	//checks to see if there are any 0's in the array
	public boolean checkEmptyTiles()
	{
		boolean areEmptyTiles = false;;
		
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (gameNums[i][j] == 0)
				{
					areEmptyTiles = true;
				}
				else
				{
					continue;
				}
			}
		}
		return areEmptyTiles;
	}
	
	
	//fills gameNum values with value of passed array object.
	public void fillArray(int[][] array)
	{
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				gameNums[i][j] = array[i][j];
			}
		}
	}
	
	
	//fills gameNum values with value of passed array object.
	public int[][] copyGameArray()
	{
		int[][] newArray = new int[4][4];
		
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				newArray[i][j] = gameNums[i][j];
			}
		}
		return newArray;
	}
	
	public void reset()
	{
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				gameNums[i][j] = 0;
			}
		}
		newTile();
		newTile();
	}
}
