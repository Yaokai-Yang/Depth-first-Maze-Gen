//variables
public int textSize = height / 100 * 23;
public int maxCanvasWidth = width;
public int maxCanvasHeight = height;

public static int MAZE_WIDTH = 20;
public static int MAZE_HEIGHT = 20;

public static int GRID_SIZE = 0;
public Generator mazeGen;
public Maze maze; 

//Setup
void setup()
{
  //Setup
  fullScreen();
  textAlign(CENTER, CENTER);
  noLoop();
  
  //Fullscreen-based variables
  textSize = height / 100 * 23;
  maxCanvasWidth = width;
  maxCanvasHeight = height;
  
  //Grid Size
  GRID_SIZE = Math.round(Math.min((maxCanvasWidth * 0.8) / MAZE_WIDTH, (maxCanvasHeight * 0.8) / MAZE_HEIGHT));
  
  //Generate
  mazeGen = new Generator();
  maze = mazeGen.generateNewMaze(MAZE_WIDTH, MAZE_HEIGHT);
  
  //Display the maze
  redraw();
}

//Update Display
void draw()
{
  background(51);
  mazeGen.maze.drawMaze(0, maxCanvasWidth, 0, maxCanvasHeight);
}
