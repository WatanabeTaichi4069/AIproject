public class Adr{
	private int X;
	private int Y;
	public Adr(int x,int y){
		this.X = x;
		this.Y = y;
	}
	
	public int getX(){
		return this.X;
	}
	public int getY(){
		return this.Y;
	}
	public String toString(){
		return String.format("(%d,%d)",X,Y);
	}
	
	public Adr getNext(){     //ueno adoresu wo kaesu
		return new Adr(this.X + 1,this.Y);
	}
}