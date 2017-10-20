package codingInterview;

// Implementing my own version of a linked list node

public class ListNode {
	// TODO: Made this accept any data type
	private int data;
	private ListNode next = null;
	
	// Constructor
	public ListNode(int data) {
		this(data, null);
	}
	
	public ListNode(int data, ListNode next) {
		this.data = data;
		this.next = next;
	}
	
	public ListNode(ListNode node){
		this.data = node.getData();
		this.next = node.getNext();
	}
	
	public ListNode getNext(){
		return next;
	}
	
	public void setNext(ListNode next){
		this.next = next;
	}
	
	public int getData(){
		return data;
	}
	
	public void setData(int data) {
		this.data = data;
	}
}
