/**
 * @author Alexander Gavrilov 
 *Class BigNumber - The class use two Data structures : IntList that consists of IntNodes to represent the big numbers.
 *
 *Every IntNode in the IntList of the BigNumber have int value;  0<=value<=9
 *
 *BigNumber give a solution for mathematical operations like : Add,subtract,and multiply big numbers that cannot be stored in 
 *int's or long's because of memory limitation. Also there are few more methods :
 *compareTo : compare Between two BigNumbers.
 *toString : represnt the number by a String.
 * 
 *The Objects of type BigNumber have IntList "number" - represent all the number.the first IntNode in the IntList is the "head"
 *and the head hold the LSB(list significant digit of the number). The last IntNode in the Intlist contains the MSB of the number    
 */


public class BigNumber {
	
	private IntList number;
	private IntNode head;

	
	/**
	 * Empty constructor,initialize BigNumber head to have IntNode with value 0. 
	 */
	public BigNumber() {
		this.head = new IntNode(0, null);
		number = new IntList();
		number.addToEnd(head);
	}
    
	/**
	 * @param num - gets long num,store him inside the Data structure of BigNumber
	 */
	public BigNumber(long num) {
		number = new IntList();
		IntNode lessSignificantDigit = new IntNode((int) num % 10, null);//the less significant digit is the Head
		number.addToEnd(lessSignificantDigit);// first IntNode added to the IntList number
		head = lessSignificantDigit;

		num = num / 10;
		while (num > 0) {
			number.addToEnd(new IntNode((int) num % 10, null));
			num = num / 10;
		}//while - add the remain digits to the IntList
	}

	/**
	 * The constructor gets BigNumber and creates a copy with the same data of other
	 * @param other - BigNumber we want to copy
	 */
	public BigNumber(BigNumber other) {
			
     if(other!=null && this!=null){
		this.head = new IntNode(other.head.getValue());
		this.number = new IntList(this.head);
		IntNode curr = other.head.getNext();
		while (curr != null) {
			IntNode toAdd = new IntNode(curr.getValue());
			this.number.addToEnd(toAdd);
			curr = curr.getNext();
		}
	  } 
	}
	

	/**
	 * The method gives us a BigNumber represented by String, uses the method toString(IntNode prev)
	 * for the recursion 
	 */
	public String toString() {
		if (this.number.empty())
			return "";
		return this.toString(head);
	}

	private String toString(IntNode prev) {
		if (prev.getNext() == null)
			return String.valueOf(prev.getValue());
		return toString(prev.getNext()) + String.valueOf(prev.getValue());
	}

	/**
	 * The method add two BigNumber's. if they have the same length - n digits,Runtime is O(n)
	 * if one of them is bigger, one have n digits and the other have m, and n>m still Runtime will be O(n)
	 * 
	 * @param other - BigNumber to add to the BigNumber on which the method was applied
	 * 
	 * @return BigNumber -return new BigNumber - the sum of the two BigNumbers 
	 */
	public BigNumber addBigNumber(BigNumber other) {
		
		if(other==null || this==null)
			return null;

		BigNumber output = new BigNumber();
		IntNode curr1 = this.head;
		IntNode curr2 = other.head;
		int carry = 0;//if the sum of two digits is more then 9,we will have carry that we add to the next sum
		int tmp = curr1.getValue() + curr2.getValue();//sum of two digits
		output.head = new IntNode(0);
		output.number.addToEnd(output.head);
		
		
		//if and else for the head of the output, the sum of the LSB of the two numbers
		if (tmp > 9) {
			output.head.setValue((tmp) % 10);
			carry = 1;
		} else {
			output.head.setValue(tmp);
		}

		curr1 = curr1.getNext();
		curr2 = curr2.getNext();
		
		// calculate the numbers till one of them is null,that means that we remain with the longer number only
		while (curr1 != null && curr2 != null) {

			IntNode toAdd = new IntNode(0);
			tmp = curr1.getValue() + curr2.getValue();

			if (carry == 0) {
				if (tmp <= 9) {
					toAdd.setValue(tmp);
					output.number.addToEnd(toAdd);	
				}
				else{// tmp>9
					toAdd.setValue(tmp % 10);
					carry = 1;
					output.number.addToEnd(toAdd);
				}	
			}
			else {// if carry == 1
				if (tmp + 1 <= 9) {
					toAdd.setValue(tmp + 1);
					output.number.addToEnd(toAdd);
					carry = 0;
				} 
				else {// tmp+1>9
					toAdd.setValue((tmp + 1) % 10);
					output.number.addToEnd(toAdd);
					carry = 1;
				}
			}
			//promote the while
			curr1 = curr1.getNext();
			curr2 = curr2.getNext();
		}//end of while,now we know for sure that one of the currents is null,maybe the two of them

		//if the two currents are null && carry is 1, add the last IntNode and return output
		if (curr1 == null && curr2 == null && carry == 1) {
			IntNode finalNode = new IntNode(1);
			output.number.addToEnd(finalNode);
			return output;
		}

		
		
		/*
		 * This part of the code add the digits of the longer number
		 * we already summed all the digits of one of the numbers, so we remain only with the other
		 * curr1 or curr2, one of them is null for sure
		 */
		if (curr1 != null) {
			while (carry == 1 && curr1 != null) {
				int val = curr1.getValue() + 1;
				if (val == 10) {
					IntNode toAdd = new IntNode(0);
					output.number.addToEnd(toAdd);
				}
				if (val < 10) {
					carry = 0;
					IntNode toAdd = new IntNode(val);
					output.number.addToEnd(toAdd);
				}
				curr1 = curr1.getNext();
			}
			if (carry == 1 && curr1 == null) {// it means curr1 is null
				IntNode finalNode = new IntNode(1);
				output.number.addToEnd(finalNode);
			}

			while (curr1 != null) {
				IntNode toAdd = new IntNode(curr1.getValue());
				output.number.addToEnd(toAdd);
				curr1 = curr1.getNext();
			}
		}
		
		if (curr2 != null) {
			while (carry == 1 && curr2 != null) {
				int val = curr2.getValue() + 1;
				if (val == 10) {
					IntNode toAdd = new IntNode(0);
					output.number.addToEnd(toAdd);
				}
				if (val < 10) {
					carry = 0;
					IntNode toAdd = new IntNode(val);
					output.number.addToEnd(toAdd);
				}
				curr2 = curr2.getNext();
			}
			if (carry == 1 && curr2 == null) {// it means curr1 is null
				IntNode finalNode = new IntNode(1);
				output.number.addToEnd(finalNode);
			}

			while (curr2 != null) {
				IntNode toAdd = new IntNode(curr2.getValue());
				output.number.addToEnd(toAdd);
				curr2 = curr2.getNext();
			}
		}

		return output;
	}

	/**
	 * Runtime -> O(n)
	 * This method gets parameter of type long and transform the long into BigNumber and add it
	 * to the BigNumber on which the method was applied
	 * @param num - long
	 * @return BigNumber -> the sum of the BigNumber on which the method was applied and the long
	 */
	public BigNumber addLong(long num) {
		BigNumber longToBigNumber = new BigNumber(num);
		BigNumber output = this.addBigNumber(longToBigNumber);
		return output;
	}

	/**
	 * Runtime O(n) 
	 * The method gets two BigNumbers and subtract them, 
	 * @param other - BigNumber 
	 * @return BigNumber output - the result of the subtraction of the two BigNumbers
	 */
	public BigNumber subtractBigNumber(BigNumber other) {
		
		if(other==null || this==null)
			return null;

		BigNumber thisBig = new BigNumber(this);//copy of this
		BigNumber otherBig = new BigNumber(other);//copy of other
		BigNumber output = new BigNumber();
		IntNode bigger = new IntNode(0);
		IntNode smaller = new IntNode(0);
	
		
		// if we subtract two equal numbers we get 0
		if (thisBig.compareTo(otherBig) == 0)
			return new BigNumber(0);

		// find who is the bigger number so we can subtract the bigger from the smaller
		if (thisBig.compareTo(otherBig) == 1) {
			bigger = thisBig.head;
			smaller = otherBig.head;
		} 
		
		else if (this.compareTo(other) == -1) {
			bigger = otherBig.head;
			smaller = thisBig.head;
		} 
		
		//---------------------now we know who is the bigger number-------------------------------------

		IntNode borrowNode = bigger.getNext();// the node we will borrow from if we will need borrow
		int resultOfSubtraction = bigger.getValue() - smaller.getValue(); //tmp - subtract the two values of the current nodes

		
		//this if and else are for the first IntNode in the output,the LSB of each number
		//after we have the first Node in the output we calculate the rest of the subtraction 
		if (resultOfSubtraction >= 0) {
			output.head = new IntNode(resultOfSubtraction);
			output.number.addToEnd(output.head);
		}
		
		else {// tmp is negetive
			if (borrowNode.getValue() == 0) {
				while (borrowNode != null && borrowNode.getValue() == 0) {
					borrowNode.setValue(9);
					borrowNode = borrowNode.getNext();
				}	
				borrowNode.setValue(borrowNode.getValue() - 1);
				resultOfSubtraction = resultOfSubtraction + 10;
				output.head = new IntNode(resultOfSubtraction);
				output.number.addToEnd(output.head);
			} 
			else {
				borrowNode.setValue(borrowNode.getValue() - 1);
				resultOfSubtraction = resultOfSubtraction + 10;
				output.head = new IntNode(resultOfSubtraction);
				output.number.addToEnd(output.head);
			}
		}
		
		if(bigger.getNext()==null)
			return output;

	
		bigger = bigger.getNext();//the next digit OF the bigger number after the LSB 
		smaller = smaller.getNext();//the next digit of the smaller number after the LSB
		borrowNode = bigger.getNext();//the node that follow bigger 
		
		
		/*
		 * At this point we have subtracted the LSB of the two big numbers 
		 * Now we want to subtract the next remaining digits 
		 */
		while (smaller != null && bigger!=null  ) {

			IntNode toAdd = new IntNode(0);
			resultOfSubtraction = bigger.getValue() - smaller.getValue();

			//the subtraction of the last two nodes
			if(bigger.getNext()==null && smaller.getNext()==null){
			    if(resultOfSubtraction!=0){
				toAdd = new IntNode(resultOfSubtraction);
				output.number.addToEnd(toAdd);
				return output;
			    }
			    else{
			    	return output;
			    }
			}
				
			
			if (resultOfSubtraction == 0 ) {
				if( this.toString().length()==other.toString().length()  && fromThisPointEverythingIsZero( bigger, smaller)){
					return output;
				}
				if( fromThisPointEverythingIsZero1( bigger, smaller)){
					return output;
				}	
				else{
					toAdd.setValue(resultOfSubtraction);
					output.number.addToEnd(toAdd);
					bigger = bigger.getNext();
					smaller = smaller.getNext();
					borrowNode = bigger.getNext();
				}
			}
			//no need for borrow,the result is positive
			else if (resultOfSubtraction > 0 && borrowNode!=null) {
				toAdd.setValue(resultOfSubtraction);
				output.number.addToEnd(toAdd);
				bigger = bigger.getNext();
				smaller = smaller.getNext();
				borrowNode = bigger.getNext();
			}
			/*
			 * tmp is negative - so we search for borrow,if the borrowNode value
			 * is 0 -> we need to move the borrowNode further till we get to a
			 * none zero value then we borrow from him
			 */
			else{// search for borrow

				while (borrowNode != null && borrowNode.getValue() == 0) {
					borrowNode.setValue(9);
					borrowNode = borrowNode.getNext();
				}

				if (borrowNode != null && borrowNode.getValue() != 0) {
					borrowNode.setValue(borrowNode.getValue() - 1);
					resultOfSubtraction = resultOfSubtraction + 10;
					toAdd.setValue(resultOfSubtraction);
					output.number.addToEnd(toAdd);
					bigger = bigger.getNext();
					smaller = smaller.getNext();
					borrowNode = bigger.getNext();
				}
			}
		}//end of while
		
	     

		// now we know that smaller is null for sure
		while (bigger != null) {
			if(bigger.getNext()==null && bigger.getValue()==0){
				return output;
			}	
			IntNode toAdd = new IntNode(bigger.getValue());
			output.number.addToEnd(toAdd);
			bigger = bigger.getNext();
		}
	
		return output;
	}
	/**
	 * Two BigNumbers with the same String length -> when the subtraction give us result 0, we want to check 
	 * if the rest of the subtraction give us zero till the end, if all the rest subtractions from this point are zero
	 * we will return output without adding new IntNodes
	 * @param bigger -> IntNode from BigNumber
	 * @param smaller -> IntNode from SmallNumber
	 * @return if all the subtraction from the point where we get result zero are zeros as well
	 * if all the subtractions are zeros the method return true, if not the method return false;
	 */
    private boolean fromThisPointEverythingIsZero(IntNode bigger,IntNode smaller){
		
		int result = 0;
		while(bigger!=null && smaller!=null && result==0){
			result = bigger.getValue() - smaller.getValue();
			bigger = bigger.getNext();
			smaller = smaller.getNext();
	        if(result!=0)
	        	return false;
		}
		return true;
	}
    /**
     * same method only for BigNumbers that have different lengths
     * @param bigger
     * @param smaller
     * @return
     */
    private boolean fromThisPointEverythingIsZero1(IntNode bigger,IntNode smaller){
    	
    	int result = 0;
		while( smaller!=null && bigger!=null && result==0){
			result = bigger.getValue() - smaller.getValue();
			bigger = bigger.getNext();
			smaller = smaller.getNext();
	        if(result!=0)
	        	return false;
		}
		while(bigger!=null){
			result = bigger.getValue();
			bigger = bigger.getNext();
			if(result!=0)
				return false;
		}
		return true;
	}
    

	/**
	 * Runtime - suppose the runtime is O(n^2)
	 * 
	 * Given two BigNumbers the method return the multiplication of the two using private method intMultBigNumber
	 * and addBigNumber
	 * @param other -> other multiple by the BigNumber on which the method was applied
	 * @return - the result of the multiplication stored in new BigNumber
	 */
	public BigNumber multBigNumber(BigNumber other) {
		
		if(other==null || this==null)
			return null;
		
		BigNumber output = new BigNumber();
		BigNumber bigger = new BigNumber();
		BigNumber smaller = new BigNumber();
			
		if(this.compareTo(other)==1){
			bigger = new BigNumber(this);
			smaller = new BigNumber(other);
		}
		if(this.compareTo(other)==-1){
			bigger = new BigNumber(other);
			smaller = new BigNumber(this);
		}
		else{
			bigger = new BigNumber(this);
			smaller = new BigNumber(other);
		}
		//now we determined who is the smaller number between the two
		
	
		IntNode smallCurr = smaller.head;
		int zeroPedding = 0;//number of zeros to add to multiplication
		
		
		/*
		 * The while loop in each iteration use two methods -> addBigNumber and IntMultBigNumber
		 * to multiple the BigNumbers and to add the result to the output
		 */
		while(smallCurr!=null){	
			int val =  smallCurr.getValue();//2
			   if(val!=0){
			      BigNumber mult = IntMultBigNumber(bigger,val,zeroPedding);
			      output = output.addBigNumber(mult); 
			      zeroPedding++;
			      smallCurr = smallCurr.getNext();
			   }
			   else{
				zeroPedding++;
				smallCurr = smallCurr.getNext();
			   }
		}
		return output;	
	}
	
	
	
	/**
	 * The method takes one digit from BigNumber and give us the solution of the multiplication
	 * of the digit*BigNumber. By the location of the digit we know how many zeros we need to add to the result :
	 * for example if we multiply : 123*456, we see that 2*456 = 912, but because this is the the second digit in the number
	 * 123, we know that it is actually 20*456 = 9120,so we add one zero to the result
	 * 
	 * @param bigger - the bigger BigNumber between the two BigNumbers that sent to the method : multBigNumber
	 * @param value - value is an int, this int is the value of one of the digits in the smaller BigNumber
	 * @param zero - int zero,this int tell us how many zeros we need to add to the multiplication
	 * 
	 * @return BigNumber --> the result of multiplication represented by BigNumber
	 */
	private BigNumber IntMultBigNumber(BigNumber bigger,int value,int zero){
		
		BigNumber output = new BigNumber();
		
		IntNode curr = bigger.head;
		int product = value*curr.getValue();
		int carry = 0;
		
		if(product<=9)
			output.head.setValue(product);
		else{//product > 9 --> there is carry
			output.head.setValue(product%10);
			carry = product/10;
		} 
		curr = curr.getNext();
		
		while(curr!=null){
		   product = value*curr.getValue() + carry;
           IntNode toAdd = new IntNode(0);
           IntNode toAddLast = new IntNode(0);
		   
		   if(curr.getNext()!=null){
			   if(product<=9){
				 toAdd.setValue(product);
				 output.number.addToEnd(toAdd);
				 curr = curr.getNext();
				 carry = 0;
			   }
			   else{
				 toAdd.setValue(product%10);
				 carry = product/10;
				 output.number.addToEnd(toAdd);
				 curr = curr.getNext();
			   }
		    }
		    else{//curr.getNext() is null
		    
		       
			   if(product<10){
				toAdd.setValue(product);
				output.number.addToEnd(toAdd);
				curr = curr.getNext();
				carry = 0;
			   }
			   else{//product is greater then 9,so we add two nodes
				toAdd.setValue(product%10);
				toAddLast.setValue(product/10);
				output.number.addToEnd(toAdd);
				output.number.addToEnd(toAddLast);
				curr = curr.getNext();
				carry = 0;
			   }
		    }
	   }
		/*
		 * after the while loop we remain with the MSB of the BigNumber,so if the product will be greater then 9
		 * we need to add two IntNodes too the output
		 */
		
		
		for(int i = zero;i>0;i--){//the for loop adds the zeros to the result
			IntNode finale = new IntNode(0);
			finale.setNext(output.head);
			output.head = finale;
		}
		
		if(carry!=0){
			IntNode finale = new IntNode(carry);
			output.number.addToEnd(finale);
		}
		
		return output;	
	}
	


	/**
	 * @param other - compare between other and the BigNumber on which the method was applied
	 * @return int - if returns 1 we know that "this" is bigger,if returns -1 other is bigger
	 * if return 0 the numbers are equal
	 */
	public int compareTo(BigNumber other) {
		
		int comp = this.compareToByStringLength(other);
		if (comp == 1 || comp == -1)
			return comp;
		/*
		 * if we got to this part of code we know that other & this have the
		 * same number of digits So we want to compare between the two
		 * BigNumbers and we will do it with their toStrings
		 */
		String s1 = this.toString();
		String s2 = other.toString();
		int length = s1.length();

		for (int i = 0; i < length; i++) {
			if (Character.getNumericValue(s1.charAt(i)) > Character.getNumericValue(s2.charAt(i)))
				return 1;
			else if (Character.getNumericValue(s1.charAt(i)) < Character.getNumericValue(s2.charAt(i)))
				return -1;
		}
		// if all the characters are equal return 0
		return 0;
	}

	/**
	 * The method compares between two BigNumbers by their String length
	 * @param other 
	 * @return int - if it returns -1 or 1 we know that one of them larger by String,
	 * if it returns 0 we know that string length equal and we need the compare by digits
	 */
	private int compareToByStringLength(BigNumber other) {
		String big1 = this.toString();
		String big2 = other.toString();
		if (big1.length() > big2.length())
			return 1;
		if (big2.length() > big1.length())
			return -1;
		return 0;
	}
	
	
}