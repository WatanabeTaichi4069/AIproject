import java.util.StringJoiner;

public class AdrList{
	private Adr[] list;
	private int size;
	
	public AdrList(Adr[] list,int size){
		this.list = list;
		this.size = size;
	}
	public AdrList(Adr[] list){
		this.list = list;
		this.size = list.length;
	}
	
	public Adr[] getList(){
		return this.list;
	}
	public int getSize(){
		return this.size;
	}
	
	public Adr getAdrN(int n){ 
		return list[n];
	}
	public AdrList remove(Adr adr){  //return list without 'adr'
		Adr[] newList = new Adr[this.size - 1];
		int idx = 0;
		for(int i=0;i<this.size;i++){
			if(list[i].getX() == adr.getX() && list[i].getY() == adr.getY()){
				continue;
			}
			newList[idx] = list[i];
			idx++;
		}
		return new AdrList(newList);
	}
	public AdrList add(Adr adr){  //return list include 'adr'
		Adr[] newList = new Adr[this.size + 1];
		for(int i=0;i<this.size;i++){
			newList[i] = list[i];
		}
		newList[this.size] = adr;
		return new AdrList(newList);
	}
	public AdrList replace(Adr adr){
		Adr[] newList = new Adr[this.size];
		for(int i=0;i<this.size;i++){
			if(adr.getY() == list[i].getY()){
				newList[i] = adr;
			}else{
				newList[i] = list[i];
			}
		}
		return new AdrList(newList);
	}
	
	public String toString(){
		StringJoiner j = new StringJoiner(" ");
		for(int i=0;i<this.size;i++){
			j.add(list[i].toString());
		}
		return j.toString();
	}

	public boolean exist(Adr adr) {
		int i;
		for (i = 0; i < size; i++) {
			if (list[i].getX() == adr.getX() && list[i].getY() == adr.getY()) { return true; }
		}
		return false;
	}
}