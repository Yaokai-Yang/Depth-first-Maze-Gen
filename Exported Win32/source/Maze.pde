public class Maze
{
  public Grid gridArray[][];
  
  public Maze(int mazeWidth, int mazeHeight)
  {
    //initiallize grids
    gridArray = new Grid[mazeWidth][mazeHeight];
    for(int x = 0; x < mazeWidth; x++)
    {
      for(int y = 0; y < mazeHeight; y++)
      {
        gridArray[x][y] = new Grid(x, y);
      }
    }
  }
  
  public void drawMaze(int drawX1, int drawX2, int drawY1, int drawY2)
  {
    //Draw the maze based off of the Main.drawCords
    rectMode(CENTER);
    for(int x = 0; x < gridArray.length; x++)
    {
      for(int y = 0; y < gridArray[0].length; y++)
      {
        gridArray[x][y].drawGrid(drawX1, drawX2, drawY1, drawY2);
      }
    }
    
    return;
  }

  public void removeWall(Grid grid1, Grid grid2)
  {
    //top
    if(grid1.y - 1 == grid2.y)
    {
      grid1.walls[0] = false;
      grid2.walls[2] = false;
    }

    //Right
    if(grid1.x + 1 == grid2.x)
    {
      grid1.walls[1] = false;
      grid2.walls[3] = false;
    }

    //Bottom
    if(grid1.y + 1 == grid2.y)
    {
      grid1.walls[2] = false;
      grid2.walls[0] = false;
    }

    //Left
    if(grid1.x - 1 == grid2.x)
    {
      grid1.walls[3] = false;
      grid2.walls[1] = false;
    }
  }
  
  public boolean visitedTop(Grid grid)
  {
    //border
    if(grid.y == 0)
    {
      return true;
    }
    
    return gridArray[grid.x][grid.y - 1].visited;
  }
  
  public Grid getTop(Grid grid)
  {

    return gridArray[grid.x][grid.y - 1];
  }

  public boolean visitedRight(Grid grid)
  {
    //border
    if(grid.x == gridArray.length - 1)
    {
      return true;
    }
    
    return gridArray[grid.x + 1][grid.y].visited;
  }
  
  public Grid getRight(Grid grid)
  {
    return gridArray[grid.x + 1][grid.y];
  }

  public boolean visitedBottom(Grid grid)
  {
    //border
    if(grid.y == gridArray[0].length - 1)
    {
      return true;
    }
    
    return gridArray[grid.x][grid.y + 1].visited;
  }
  
  public Grid getBottom(Grid grid)
  {
    return gridArray[grid.x][grid.y + 1];
  }

  public boolean visitedLeft(Grid grid)
  {
    //border
    if(grid.x == 0)
    {
      return true;
    }
    
    return gridArray[grid.x - 1][grid.y].visited;
  }
  
  public Grid getLeft(Grid grid)
  {
    return gridArray[grid.x - 1][grid.y];
  }
}
