
public class IntList {

	private IntNode head;
	
	
	public IntList(){
		head = null;
	}
	
	public IntList(IntNode node){
		head = node;
	}
	
	public IntNode getHead(){
	   return this.head;
	}
	
	public boolean empty(){
		return head == null;
	}
	
	public IntNode nextElement(IntNode node){
		return node.getNext();
	}
	
	public int getValueOfNode(IntNode node){
		return node.getValue();
	}
	
	public void addToHead(IntNode node){
		
		if(empty()){
			head = node;
		}
		IntNode tmp = head;
		head = node;
		node.setNext(tmp);	
	}
	
	public void addToEnd(IntNode node){
		
		if(empty()){
			head = node;
		}
		else{
		    IntNode curr = head;
		    while(curr.getNext()!=null){
			 curr = curr.getNext();	
		    }
		    curr.setNext(node);
		}    
	}
	
	public void remove(int num){
		if(!empty()){
			if(head.getValue()==num){
				head = head.getNext();
			}
			else{
	         
				IntNode behind = head;
				while(behind.getNext()!=null){
					if(behind.getNext().getValue()==num){
						behind.setNext(behind.getNext().getNext());
						return;
					}
					else{
						behind = behind.getNext();
					}
				}	
			}//else - if not the head
		}//if(!empty())
	}
		
	public void printList(){
		IntNode tmp = head;
		while(tmp!=null){
			System.out.print(tmp.getValue() + " --> ");
			tmp = tmp.getNext();
		}
		System.out.println("null");
	}
	
	public int length(){
		IntNode tmp = head;
		int count = 0;
		while(tmp!=null){
			count++;
			tmp = tmp.getNext();
		}
		return count;
	}
	
	public int lengthRec(){
		return lengthRec(head);
	}
	
	private int lengthRec(IntNode node){
		if(node==null)
			return 0;
		return 1+lengthRec(node.getNext());
	}
	
	public IntNode predecessor(IntNode node){
		
		if(head==null || head==node)
			return null;
		IntNode behind = head;
		while(behind.getNext()!=null){
			if(behind.getNext()== node)
				return behind;
			else
				behind = behind.getNext();
			}
		
		return null;
	}
	
	public static void main(String[] args){
		
		IntNode node = new IntNode(7);
		
		
		IntList list = new IntList();
		list.addToEnd(node);
		IntNode temp = list.head;
		System.out.println(list.head.getValue());
		System.out.println(temp.getValue());
		temp.setValue(4);
		System.out.println(list.head.getValue());
		
		
	}
}
