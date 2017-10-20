package codingInterview;

public class LinkedList {
	private ListNode head;
	private int size = 0;
	
	// Constructors
	// Empty List
	public LinkedList(){
		head = null;
		size = 0;
	}
	
	// Create a list with data to put in a node
	public LinkedList(int data) {
		this(new ListNode(data));
	}
	
	// Create a list with a node
	public LinkedList(ListNode node){
		head = node;
		size++;
	}
	
	// Add to the tail
	public void addNodeToTail(ListNode node){
		// If this is all there is
		if (head == null){
			head = node;
			return;
		}
		
		ListNode thisNode = head;
		while (thisNode.getNext() != null) {
			thisNode = thisNode.getNext();
		}
		
		thisNode.setNext(node);
		thisNode.getNext().setNext(null);
	}
	
	
	
	public void addNodeToTail(int data){
		this.addNodeToTail(new ListNode(data));
	}
	
	public void append(int data){
		this.addNodeToTail(new ListNode(data));
	}
	
	public void append(ListNode node){
		this.addNodeToTail(node);
	}
	
	// Add a node before a given node
	public int addNodeBefore(ListNode existingNode, ListNode newNode){
		ListNode thisNode = head;
		
		// Check if the existing node is the head
		if (existingNode == head) {
			newNode.setNext(head);
			head = newNode;
			return 1;
		}
		
		// Start at the head of the list and find the existing node by looking 1 step ahead to the end of the list
		while (thisNode.getNext() != null)
			if (thisNode.getNext() == existingNode){
				// Insert this one in between
				newNode.setNext(thisNode.getNext());
				thisNode.setNext(newNode);
				return 1;
			}
		
		// Have iterated through the list, existingNode was not found
		return -1;
	}
	
	public ListNode removeNode(ListNode existingNode){
		// Check if the node is the head
		ListNode returnNode = head;
		if (existingNode == head) {
			head = head.getNext();
			return returnNode;
		}
		
		while (returnNode.getNext() != null){
			if (returnNode.getNext() == existingNode){
				// Re-make the connections here
				ListNode temp = new ListNode(existingNode);
				returnNode.setNext(temp.getNext());
				return temp;
			}
		}
		
		// Node wasn't found in list
		return null;
	}
	
	public int getSize(){
		return size;
	}
	
	public boolean isEmpty(){
		return (size == 0);
	}
	
	public ListNode getHead(){
		return head;
	}
	
	@Override
	public String toString(){
		if (size == 0){
			return "This list is empty.";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		ListNode node = head;
		while (node != null) {
			stringBuilder.append(" ").append(node.getData()).append(" ");
			node = node.getNext();
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

}
