public class Node{
	private State state;
	private Ope ope;
	private int eval;
	private Node parent;
	private Ope nextOpe;
	private int depth;
	
	public Node(State state,Ope ope,Node parent,int depth){
		this.state = state;
		this.ope = ope;
		if(ope.getP()){
			this.eval = -100;
		}else{
			this.eval = 100;
		}
		this.parent = parent;
		this.nextOpe = null;
		this.depth = depth;
	}
	
	public void setEval(int eval){
		this.eval = eval;
	}
	public void setNextOpe(Ope ope){
		this.nextOpe = ope;
	}
	
	public State getState(){
		return this.state;
	}
	public Ope getOpe(){
		return this.ope;
	}
	public int getEval(){
		return this.eval;
	}
	public Node getParent(){
		return this.parent;
	}
	public Ope getNextOpe(){
		return this.nextOpe;
	}
	public int getDepth(){
		return this.depth;
	}
}