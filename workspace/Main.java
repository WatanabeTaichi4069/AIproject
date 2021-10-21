import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener; 
import javax.swing.JFrame;

public class Main extends JFrame{
	public static void main(String Args[]){
		//GameRun game = new GameRun();
		//game.run();
		
		State state = init();
		boolean flag = true;
		while(true){
			System.out.printf("----------------------------------------\n---------[User Turn]");
			display(state);
			if(judge(state,"User","AI")){ break; }
			state = makeMove(state,input(true));
			System.out.printf("----------------------------------------\n---------[ AI Turn ]");
			display(state);
			if(judge(state,"User","AI")){ break; }
			state = getNext(state,5);
		}
		
/*		while(true){
			System.out.printf("----------------------------------------\n---------[User1 Turn]");
			display(state);
			state = makeMove(state,input(true));
			System.out.printf("----------------------------------------\n---------[User2 Turn]");
			display(state);
			state = makeMove(state,input(false));
		}*/
	}
	public static State init(){
		Adr[] maList = {};
		AdrList maruList = new AdrList(maList);
		Adr[] baList = {};
		AdrList batuList = new AdrList(baList);
		Adr[] skList = {new Adr(1,0),new Adr(1,1),new Adr(1,2),new Adr(1,3),new Adr(1,4),new Adr(1,5),new Adr(1,6),
						new Adr(2,0),new Adr(2,1),new Adr(2,2),new Adr(2,3),new Adr(2,4),new Adr(2,5),new Adr(2,6),
						new Adr(3,0),new Adr(3,1),new Adr(3,2),new Adr(3,3),new Adr(3,4),new Adr(3,5),new Adr(3,6),
						new Adr(4,0),new Adr(4,1),new Adr(4,2),new Adr(4,3),new Adr(4,4),new Adr(4,5),new Adr(4,6),
						new Adr(5,0),new Adr(5,1),new Adr(5,2),new Adr(5,3),new Adr(5,4),new Adr(5,5),new Adr(5,6)};
		AdrList skyList = new AdrList(skList);
		Adr[] gnList = {new Adr(0,0),new Adr(0,1),new Adr(0,2),new Adr(0,3),new Adr(0,4),new Adr(0,5),new Adr(0,6)};
		AdrList gndList = new AdrList(gnList);
		return new State(maruList,batuList,skyList,gndList);
	}
	
	public static boolean judge(State state,String player1,String player2){
		if(100 <= innerFanc(state.getMaruList(),state.getBatuList())){
			System.out.println("\n\twin: " + player1);
			return true;
		}else if(100 <= innerFanc(state.getBatuList(),state.getMaruList())){
			System.out.println("\n\twin: " + player2);
			return true;
		}else if(state.getGndList().getSize() == 0){
			System.out.println("\n\tdraw");
			return true;
		}
		return false;
	}
	
	public static Ope input(boolean p){
		Scanner scan = new Scanner(System.in);
		System.out.printf("\nselect number>");
		int n = Integer.parseInt(scan.next()) - 1;
		return new Ope(n,p);
	}
	public static void display(State state){
		int[][] board = {{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};
		project(state.getMaruList(),board,1);
		project(state.getBatuList(),board,2);
		System.out.println("--------------------");
		for(int i=0;i<6;i++){
			System.out.printf("\t");
			for(int j=0;j<7;j++){
				if(board[i][j] == 0){
					System.out.printf("■");
				}else if(board[i][j] == 1){
					System.out.printf("○");
				}else if(board[i][j] == 2){
					System.out.printf("×");
				}
			}
			System.out.printf("\n");
		}
		AdrList gndList = state.getGndList();
		int indx = 0;
		System.out.printf("\t");
		for(int i=0;i<7;i++){
			if(indx >= gndList.getSize()){
				System.out.printf("　");
				continue;
			}
			if(i == gndList.getAdrN(indx).getY()){
				if(indx == 0){
					System.out.printf("１");
				}else if(indx == 1){
					System.out.printf("２");
				}else if(indx == 2){
					System.out.printf("３");
				}else if(indx == 3){
					System.out.printf("４");
				}else if(indx == 4){
					System.out.printf("５");
				}else if(indx == 5){
					System.out.printf("６");
				}else if(indx == 6){
					System.out.printf("７");
				}
				indx++;
			}else{
				System.out.printf("　");
			}
		}
		System.out.printf("\n");
	}
	public static void project(AdrList list,int[][] board,int p){
		Adr adr;
		for(int i=0;i<list.getSize();i++){
			adr = list.getAdrN(i);
			board[5 - adr.getX()][adr.getY()] = p;
		}
	}
	public static State makeMove(State state,Ope ope){
		AdrList nextMaruList;
		AdrList nextBatuList;
		AdrList nextSkyList;
		AdrList nextGndList;
		Adr adr = state.getGndList().getAdrN(ope.getN());
		if(ope.getP()){  //when p is maru
			nextMaruList = state.getMaruList().add(adr);  //add p into maruList
			nextBatuList = state.getBatuList();
		}else{ 
			nextMaruList = state.getMaruList();
			nextBatuList = state.getBatuList().add(adr);
		}
		if(adr.getX() == 5){      //takasa ga jougenn
			nextGndList = state.getGndList().remove(adr);
			nextSkyList = state.getSkyList();
		}else{
			adr = adr.getNext(); //adr no ueno basyo
			nextGndList = state.getGndList().replace(adr);   //kousinn
			nextSkyList = state.getSkyList().remove(adr);  //ueno basyo wo sakujo
		}
		return new State(nextMaruList,nextBatuList,nextSkyList,nextGndList);
	}
	
	public static State getNext(State state,int depth){
		//int size = (int)((Math.pow(7,depth)) + 1) * 16;
		ArrayList<Node> openList = new ArrayList<>();
		ArrayList<Node> closeList = new ArrayList<>();
		
		makeTree(state,openList,closeList,depth);
		calcEval(openList);
		//showEval(openList);
		//showEval(closeList);
		runMinMax(openList,closeList);
		//showEval(openList);
		//showEval(closeList);
		return makeMove(state,closeList.get(0).getNextOpe());
	}
	public static void makeTree(State state,ArrayList<Node> openList,ArrayList<Node> closeList,int depth){
		openList.add(new Node(state,new Ope(0,true),null,0));
		boolean flag = true;
		Node node;
		while(openList.get(0).getDepth() != depth){
			node = openList.remove(0);
			expand(node,openList);
			closeList.add(node);
			if(openList.size()==0){ break; }
		}
	}
	public static void expand(Node node,ArrayList<Node> openList){
		State state = node.getState();
		Ope ope;
		boolean flag = node.getOpe().getP();
		int depth = node.getDepth() + 1;
		for(int i = 0;i<state.getGndList().getSize();i++){
			ope = new Ope(i,!flag);
			openList.add(new Node(makeMove(state,ope),ope,node,depth));
		}
	}
	public static void calcEval(ArrayList<Node> list){
		for(int i=0;i<list.size();i++){
			list.get(i).setEval(evalFanc(list.get(i).getState()));
		}
	}
	public static int evalFanc(State state){
		return innerFanc(state.getBatuList(), state.getMaruList()) - innerFanc(state.getMaruList(), state.getBatuList());
		//return dammy() - dammy();
	}
	public static void runMinMax(ArrayList<Node> openList,ArrayList<Node> closeList){
		calcMinMax(openList,0);
		calcMinMax(closeList,1);
	}
	public static void calcMinMax(ArrayList<Node> list,int terminal){
		int size = list.size();
		Node node;
		for(int i = size - 1;i >= terminal;i--){
			node = list.get(i);
			//System.out.println(node.getParent().getEval() + "," + node.getEval());
			if(node.getOpe().getP() == false){
				if(node.getParent().getEval() < node.getEval()){
					node.getParent().setEval(node.getEval());
					node.getParent().setNextOpe(node.getOpe());
				}
			}else{
				if(node.getParent().getEval() > node.getEval()){
					node.getParent().setEval(node.getEval());
					node.getParent().setNextOpe(node.getOpe());
				}
			}
		}
	}
	public static void showEval(ArrayList<Node> list){
		for(int i=0;i<list.size();i++){
			System.out.printf("%d ",list.get(i).getEval());
			
		}
		System.out.println("");
	}

	public static int innerFanc(AdrList list, AdrList aite) {
		int max = 0;
		int tmp;
		for (int i = 0; i < list.getSize(); i++) {
			if (max < (tmp = count(list, aite, list.getAdrN(i)))) {
				max = tmp;
			}
		}
		Random r = new Random();
		return max*10 + r.nextInt(10);
	}

	public static int count (AdrList list, AdrList aite, Adr adr) {
		int max = 0;
		int tmp;
		if (max < (tmp = countX(list, aite, adr))) {
			max = tmp;
		}
		if (max < (tmp = countY(list, aite, adr))) {
			max = tmp;
		}
		if (max < (tmp = countXY(list, aite, adr))) {
			max = tmp;
		}
		if (max < (tmp = countYX(list, aite, adr))) {
			max = tmp;
		}
		return max;
	}

	// 

	public static int countX (AdrList list, AdrList aite, Adr adr) {
		int flag1 = 0;
		int flag2 = 0;
		int count = 1;
		int data[];
		data = ue(list,aite, adr,flag1);
		count += data[0];
		flag1 = data[1];
		data = shita(list,aite, adr,flag2);
		count += data[0];
		flag2 = data[1];
		if(count >= 4) {
			return 10;
		}else if(flag1 == 1 && flag2 == 1){
			return 0;
		}else if(flag1 == 0 && flag2 == 0){
				return count;
		}else{
				return --count;
		}
	}

	public static int countY (AdrList list,AdrList aite, Adr adr) {
		int flag1 = 0;
		int flag2 = 0;
		int count = 1;
		int data[];
		data = migi(list,aite, adr,flag1);
		count += data[0];
		flag1 = data[1];
		data = hidari(list,aite, adr,flag2);
		count += data[0];
		flag2 = data[1];
		if(count >= 4) {
			return 10;
		}else if(flag1 == 1 && flag2 == 1){
			return 0;
		}else if(flag1 == 0 && flag2 == 0){
			return count;
		}else{
				return --count;
		}
	}

	public static int countXY (AdrList list, AdrList aite, Adr adr) {
		int flag1 = 0;
		int flag2 = 0;
		int count = 1;
		int data[];
		data = migiue(list,aite, adr,flag1);
		count += data[0];
		flag1 = data[1];
		data = hidarishita(list,aite, adr,flag2);
		count += data[0];
		flag2 = data[1];
		if(count >= 4) {
			return 10;
		} else if(flag1 == 1 && flag2 == 1){
			return 0;
		} else if(flag1 == 0 && flag2 == 0){
				return count;
		}else{
				return --count;
		}
	}


 	public static int countYX (AdrList list, AdrList aite, Adr adr) { 
		int flag1 = 0;
		int flag2 = 0;
		int count = 1;
		int data[];
		data = hidariue(list,aite, adr,flag1);
		count += data[0];
		flag1 = data[1];
		data = migishita(list,aite, adr,flag2);
		count += data[0];
		flag2 = data[1];
		if(count >= 4) {
			return 10;
		} else if(flag1 == 1 && flag2 == 1){
			return 0;
		} else if(flag1 == 0 && flag2 == 0){
				return count;
		} else{
				return --count;
		}
	}

	public static int[] migi (AdrList list,AdrList aite, Adr adr,int flag) {
		int count = 1;
		while (true) {
			if (list.exist(new Adr(adr.getX(), adr.getY() + count))) {
				count++;
			} else if (aite.exist(new Adr(adr.getX(), adr.getY() + count))||adr.getY() + count >= 7) {
				flag = 1;
				break;
			} else {
				break;
			}
		}
		int data[] = new int[2];
		data[0] = count - 1;
		data[1] = flag; 
		return data;
	}

	public static int[] migiue (AdrList list, AdrList aite, Adr adr,int flag) {
		int count = 1;
		while (true) {
			if (list.exist(new Adr(adr.getX() + count, adr.getY() + count))) {
				count++;
			} else if (aite.exist(new Adr(adr.getX() + count , adr.getY() + count))|| adr.getX() + count >= 7||adr.getY() + count >= 7) {
				flag = 1;
				break;
			} else {
				break;
			}
		}
		int data[] = new int[2];
		data[0] = count - 1;
		data[1] = flag; 
		return data;
	}

	public static int[] ue (AdrList list, AdrList aite, Adr adr,int flag) {
		int count = 1;
		while (true) {
			if (list.exist(new Adr(adr.getX() + count, adr.getY()))) {
				count++;
			}
			else if (aite.exist(new Adr(adr.getX() + count , adr.getY()))|| adr.getX() + count >= 7) {
				flag = 1;
				break;
			}
			else {
				break;
			}
		}
		int data[] = new int[2];
		data[0] = count - 1;
		data[1] = flag; 
		return data;
	}

	public static int[] hidariue (AdrList list, AdrList aite, Adr adr,int flag) {
		int count = 1;
		while (true) {
			if (list.exist(new Adr(adr.getX() + count, adr.getY() - count))) {
				count++;
			} else if (aite.exist(new Adr(adr.getX() + count , adr.getY() - count))|| adr.getX() + count >= 7 || adr.getY() - count < 0) {
				flag = 1;
				break;
			}
			else {
				break;
			}
		}
		int data[] = new int[2];
		data[0] = count - 1;
		data[1] = flag; 
		return data;
	}

	public static int[] hidari (AdrList list, AdrList aite, Adr adr,int flag) {
		int count = 1;
		while (true) {
			if (list.exist(new Adr(adr.getX(), adr.getY() - count))) {
				count++;
			} else if (aite.exist(new Adr(adr.getX(), adr.getY() - count))|| adr.getY() - count  < 0) {
				flag = 1;
				break;
			}
			else {
				break;
			}
		}
		int data[] = new int[2];
		data[0] = count - 1;
		data[1] = flag; 
		return data;
	}

	public static int[] hidarishita (AdrList list, AdrList aite, Adr adr,int flag) {
		int count = 1;
		while (true) {
			if (list.exist(new Adr(adr.getX() - count, adr.getY() - count))) {
				count++;
			} else if (aite.exist(new Adr(adr.getX() - count , adr.getY() - count))|| adr.getX() - count < 0 || adr.getY() - count < 0) {
				flag = 1;
				break;
			}
			else {
				break;
			}
		}
		int data[] = new int[2];
		data[0] = count - 1;
		data[1] = flag; 
		return data;
	}

	public static int[] shita (AdrList list, AdrList aite, Adr adr,int flag) {
		int count = 1;
		while (true) {
			if (list.exist(new Adr(adr.getX() - count, adr.getY()))) {
				count++;
			} else if (aite.exist(new Adr(adr.getX() - count , adr.getY()))|| adr.getX() - count < 0) {
				flag = 1;
				break;
			}
			else {
				break;
			}
		}
		int data[] = new int[2];
		data[0] = count - 1;
		data[1] = flag; 
		return data;
	}

	public static int[] migishita (AdrList list, AdrList aite, Adr adr,int flag) {
		int count = 1;
		while (true) {
			if (list.exist(new Adr(adr.getX() - count, adr.getY() + count))) {
				count++;
			} else if (aite.exist(new Adr(adr.getX() - count , adr.getY() + count))|| adr.getX() - count < 0 || adr.getY() + count >= 7) {
				flag = 1;
				break;
			}
			else {
				break;
			}
		}
		int data[] = new int[2];
		data[0] = count - 1;
		data[1] = flag; 
		return data;
	}
	public static int dammy(){
		Random r = new Random();
		return r.nextInt(100);
	}
}