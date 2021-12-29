import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Main extends PApplet {

//variables
public int textSize = height / 100 * 23;
public int maxCanvasWidth = width;
public int maxCanvasHeight = height;

public boolean UPDATE = false;      //Broken
public static int MAZE_WIDTH = 50;
public static int MAZE_HEIGHT = 50;

public static int GRID_SIZE = 0;
public Generator mazeGen;
public Maze maze; 

//Setup
public void setup()
{
  //Setup
  
  textAlign(CENTER, CENTER);
  noLoop();
  
  //Fullscreen-based variables
  textSize = height / 100 * 23;
  maxCanvasWidth = width;
  maxCanvasHeight = height;
  
  //Grid Size
  GRID_SIZE = Math.round(Math.min((maxCanvasWidth * 0.8f) / MAZE_WIDTH, (maxCanvasHeight * 0.8f) / MAZE_HEIGHT));
  
  //Generate
  mazeGen = new Generator();
  maze = mazeGen.generateNewMaze(MAZE_WIDTH, MAZE_HEIGHT, UPDATE);
  
  //Display the maze
  redraw();
}

//Update Display
public void draw()
{
  background(51);
  mazeGen.maze.drawMaze(0, maxCanvasWidth, 0, maxCanvasHeight);
}
public class Generator
{  
  private boolean visitAll = false;
  public boolean update = false;
  
  public Maze maze;
  
  public Generator()
  {
  }
  
  public Maze generateNewMaze(int mazeWidth, int mazeHeight, boolean update)
  {
    this.update = update;
    visitAll = false;
    maze = new Maze(mazeWidth, mazeHeight);
    
    //List of visited grids
    ArrayList<Grid> visited = new ArrayList<Grid>();
    //Add first grid
    int randX = PApplet.parseInt(random(0, Main.MAZE_WIDTH));
    int randY = PApplet.parseInt(random(0, Main.MAZE_HEIGHT));
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
    boolean deadEnd = false;
    ArrayList<Grid> avaliableGrids = new ArrayList<Grid>();
    
    //find avaliable dirs to move & edit maze
      //Find possible Dirs'
    if(!funcMaze.visitedTop(curGrid))
    {
      avaliableGrids.add(funcMaze.getTop(curGrid));
    }
    else
    {
      deadEnd = true;
    }

    if(!funcMaze.visitedRight(curGrid))
    {
      avaliableGrids.add(funcMaze.getRight(curGrid));
    }
    else
    {
      deadEnd = true;
    }

    if(!funcMaze.visitedBottom(curGrid))
    {
      avaliableGrids.add(funcMaze.getBottom(curGrid));
    }
    else
    {
      deadEnd = true;
    }

    if(!funcMaze.visitedLeft(curGrid))
    {
      avaliableGrids.add(funcMaze.getLeft(curGrid));
    }
    else
    {
      deadEnd = true;
    }

    //Randomly choose an avaliable grid to visit
    if(avaliableGrids.size() != 0)
    {
      Grid newGrid = avaliableGrids.get(PApplet.parseInt(random(0, avaliableGrids.size())));
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

    //Draw if "update"
    if(update)
    {
      redraw();
    }
    
    return;
  }
}
public class Grid
{
  //Clockwise walls [top, right, down, left]
  public boolean walls[] = new boolean[] {true, true, true, true};
  public int x;
  public int y;
  public boolean visited;
  
  public Grid(int x, int y)
  {
    this.x = x;
    this.y = y;
  }
  
  public void drawGrid(int drawX1, int drawX2, int drawY1, int drawY2) 
  {
    //Border Cords
    float canvasWidth = drawX2 - drawX1;
    float canvasHeight = drawY2 - drawY1;
    float centerX = (drawX1 + canvasWidth / 2f) + Main.GRID_SIZE * (x + 0.5f - Main.MAZE_WIDTH / 2f);
    float centerY = (drawY1 + canvasHeight / 2f) + Main.GRID_SIZE * (y + 0.5f - Main.MAZE_HEIGHT / 2f);
    
    //Formatting
    noStroke();
    
    if(!visited)
    {
      fill(150);
    }
    else
    {
      fill(255);
    }
    
    //Draw
    rect(centerX, centerY, Main.GRID_SIZE, Main.GRID_SIZE);    //Box
    
    strokeWeight(Main.GRID_SIZE / 10);
    stroke(0);
    if(walls[0])
    {
      line(centerX - Main.GRID_SIZE / 2, centerY - Main.GRID_SIZE / 2, centerX + Main.GRID_SIZE / 2, centerY - Main.GRID_SIZE / 2);   //Top wall
    }
    if(walls[1])
    {
      line(centerX + Main.GRID_SIZE / 2, centerY + Main.GRID_SIZE / 2, centerX + Main.GRID_SIZE / 2, centerY - Main.GRID_SIZE / 2);   //Right wall
    }
    if(walls[2])
    {
      line(centerX - Main.GRID_SIZE / 2, centerY + Main.GRID_SIZE / 2, centerX + Main.GRID_SIZE / 2, centerY + Main.GRID_SIZE / 2);   //Bottom wall
    }
    if(walls[3])
    {
      line(centerX - Main.GRID_SIZE / 2, centerY + Main.GRID_SIZE / 2, centerX - Main.GRID_SIZE / 2, centerY - Main.GRID_SIZE / 2);   //Left wall
    }
    
    return;
  }
}
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
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "Main" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
