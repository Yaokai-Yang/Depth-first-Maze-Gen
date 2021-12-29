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
