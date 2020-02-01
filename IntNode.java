
public class IntNode {

	// ---------------------- fields ---------------------- 
		private int value;
		private IntNode next;
		
		// ---------------------- constructors ----------------------
		public IntNode(int value, IntNode next) {
			this.value = value;
			this.next = next;
		}
		
		public IntNode(int val) {
			value = val;
			next = null;
		}
		
		// ---------------------- Methods ----------------------
		public IntNode getNext() { 
			return next;
		}
		
		public void setNext(IntNode node){
			next = node;
		}
		
		public void setValue(int v){
			value = v;
		}
		
		public int getValue() {
		    return value;
		}

	
		public static void main(String[] args) {

		IntList list = new IntList();
		IntNode a = new IntNode(4);
		IntNode b = new IntNode(2);
		IntNode c = new IntNode(2);
		IntNode d = new IntNode(5);
		IntNode e = new IntNode(1);
		list.addToEnd(a);list.addToEnd(b);list.addToEnd(c);list.addToEnd(d);list.addToEnd(e);
		
		IntNode a1 = list.getHead();
		for(int i =1 ;i<4;i++)
			a1 = a1.getNext();
		
		IntNode tmp = a1;
		a1 = a1.getNext();
		
		
			/*
			 * BigNumber sum = big.addBigNumber(big1); String v = sum.toString();
			 * System.out.println(v); System.out.println(k + y);
			 */
		}	
		
		
		
		
		}
		

