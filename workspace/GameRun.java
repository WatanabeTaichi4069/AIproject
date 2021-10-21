import java.awt.event.KeyEvent;
import java.awt.event.KeyListener; 
import javax.swing.JFrame;

public class GameRun extends Main implements Runnable,KeyListener{
	State state;
	boolean flag;
	public GameRun(){
		this.state = init();
		this.flag = true;
	}
	public void run(){
		try{
			while(flag){
				System.out.printf("----------------------------------------\n---------[User Turn]");
				display(state);
				if(judge(state,"User","AI")){ break; }
				state = makeMove(state,input(true));
				System.out.printf("----------------------------------------\n---------[ AI Turn ]");
				display(state);
				if(judge(state,"User","AI")){ break; }
				state = getNext(state,5);
			}
		}catch(Exception e){}
	}
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT:
			System.out.println("->");
			break;
		case KeyEvent.VK_RIGHT:
			System.out.println("<-");
			break;
		}
	}
	public void keyTyped(KeyEvent e){
	}
	public void keyReleased(KeyEvent e){
	}
}