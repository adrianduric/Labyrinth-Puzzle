public class Tuppel 
{
    private int xPos;
    private int yPos;

    public Tuppel(int xPos, int yPos)
    {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public String toString()
    {
        return "(" + xPos + "," + yPos + ")";
    }

    public int getxPos() 
    {
        return xPos;
    }

    public int getyPos() 
    {
        return yPos;
    }
}
