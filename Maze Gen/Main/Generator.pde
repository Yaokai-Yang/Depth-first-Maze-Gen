public class Generator
{  
  private boolean visitAll = false;
  
  public Maze maze;
  
  public Generator()
  {
  }
  
  public Maze generateNewMaze(int mazeWidth, int mazeHeight)
  {
    visitAll = false;
    maze = new Maze(mazeWidth, mazeHeight);
    
    //List of visited grids
    ArrayList<Grid> visited = new ArrayList<Grid>();
	
    //Add first grid
    int randX = int(random(0, Main.MAZE_WIDTH));
    int randY = int(random(0, Main.MAZE_HEIGHT));
    visited.add(maze.gridArray[randX][randY]);          
    maze.gridArray[randX][randY].visited = true;
    
    //Depth First
    while(!visitAll)
    {
      move(maze, visited, visited.size() - 1);
    }
    
    return maze;
  }
  
  public void move(Maze funcMaze, ArrayList<Grid> visited, int marker)
  {
    Grid curGrid = visited.get(marker);
    ArrayList<Grid> avaliableGrids = new ArrayList<Grid>();
    
    //find avaliable dirs to move & edit maze
      //Find possible Dirs'
    if(!funcMaze.visitedTop(curGrid))
    {
      avaliableGrids.add(funcMaze.getTop(curGrid));
    }

    if(!funcMaze.visitedRight(curGrid))
    {
      avaliableGrids.add(funcMaze.getRight(curGrid));
    }

    if(!funcMaze.visitedBottom(curGrid))
    {
      avaliableGrids.add(funcMaze.getBottom(curGrid));
    }

    if(!funcMaze.visitedLeft(curGrid))
    {
      avaliableGrids.add(funcMaze.getLeft(curGrid));
    }

    //Randomly choose an avaliable grid to visit
    if(avaliableGrids.size() != 0)
    {
      Grid newGrid = avaliableGrids.get(int(random(0, avaliableGrids.size())));
      newGrid.visited = true;
      visited.add(newGrid);
      maze.removeWall(curGrid, newGrid);
    }
    //if dead end, recurse back to last move
    else
    {
      //if back at begining, set visitAll to true
      if(marker-- == 0)
      {
        visitAll = true;
        return;
      }
      
      move(funcMaze, visited, marker--);
    }
    
    return;
  }
}
