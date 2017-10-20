package codingInterview;
import java.util.HashMap;
import java.util.Map;

public class InterviewQuestionsTwo {

	public static void main (String[] args){
		
		LinkedList testList = new LinkedList(5);
		LinkedList testList2 = new LinkedList(14);
		
		for (int i = 0; i < 15; i++){
			testList.append((int)Math.ceil(Math.random() * 50));
		}
		for (int i = 0; i < 20; i++){
			testList2.append((int)Math.ceil(Math.random() * 100));
		}
		/*
		System.out.println("Testing removeDupes");
		removeDupes(testList);
		System.out.println("Finished testing.");
		
		System.out.println("Testing kToTheLast");
		kToTheLast(testList, 5);
		kToTheLast(testList2, 45);
		kToTheLast(testList, 0);
		kToTheLast(testList, 1);
		System.out.println("Finished testing.");
		
		System.out.println("Testing listPartition");
		listPartition(testList, 40);
		listPartition(testList2, 30);
		System.out.println("Finished testing.");
		*/
		
		LinkedList test3 = new LinkedList(1);
		test3.append(2);
		test3.append(3);
		test3.append(4);
		test3.append(5);
		test3.append(7);
		test3.append(5);
		test3.append(4);
		test3.append(3);
		test3.append(2);
		test3.getHead().getNext().getNext().getNext().getNext().getNext().setNext(test3.getHead().getNext().getNext());
		
		
		/*
		System.out.println("Testing isListPalindrome");
		isListPalindrome(testList);
		isListPalindrome(test3);
		System.out.println("Finished testing.");
		
		
		LinkedList test3 = new LinkedList(testList.getHead().getNext().getNext().getNext());
		System.out.println("Testing listIntersection");
		listIntersection(testList, testList2);
		listIntersection(test3, testList);
		System.out.println("Finished testing.");
		*/
		
		System.out.println("Testing loopDetection");
		loopDetection(testList);
		loopDetection(test3);
		System.out.println("Finished testing.");
		
		
		
	}
	
	public static void listIntersection(LinkedList listA, LinkedList listB){
		if (listA == null || listB == null || listA.isEmpty() || listB.isEmpty())
			return;
		
		ListNode head = listA.getHead();
		while (head != null){
			if (head == listB.getHead()){
				System.out.println("These two lists do intersect! " + listA + listB );
				return;
			}
			head = head.getNext();
		}
		
		head = listB.getHead();
		while (head != null){
			if (head == listA.getHead()){
				System.out.println("These two lists do intersect! " + listA + listB );
				return;
			}
			head = head.getNext();
		}
		
		System.out.println("These two lists do not intersect.");
	}
	
	
	public static void loopDetection(LinkedList list){
		if (list == null || list.isEmpty())
			return;
		
		Map<ListNode, Integer> map = new HashMap<>();
		ListNode node = list.getHead();
		
		while (node != null){
			if (map.get(node) == null){
				map.put(node, 1);
				node = node.getNext();
			}
			else {
				System.out.println("Corrupted list!!! The start of this corruption is: " + node);
				return;
			}
		}
		
		System.out.println("This is not a corrupted circular list." + list);
	}
	
	public static void isListPalindrome(LinkedList list){
		if (list == null || list.isEmpty() || list.getHead().getNext() == null)
			return;
		
		ListNode start = list.getHead(), end = list.getHead();
		LinkedList reverse = new LinkedList(new ListNode(end));
		int size = 1;
		
		while (end.getNext() != null){
			end = end.getNext();
			size++;
			reverse.addNodeBefore(reverse.getHead(), new ListNode(end));
		}
		
		end = reverse.getHead();
		
		for (int i = 0; i < size/2; i++) {
			if (end.getData() != start.getData()){
				System.out.println("This list is not a palindrome: " + list);
				return;
			}
			end = end.getNext();
			start = start.getNext();
		}
		
		System.out.println("This list IS a palindrome: " + list);
	}
	
	
	public static void listPartition(LinkedList list, int partition){
		if (list == null || list.isEmpty())
			return;
		
		System.out.println("Before: partition = " + String.valueOf(partition));
		System.out.println(list);
		
		ListNode partitionNode = null, node = list.getHead(), partitionHead = null;
		// Check first node
		if (node.getData() > partition){
			partitionNode = new ListNode(node);
			partitionHead = partitionNode;
		}
		
		while (node != null){
			if (node.getNext() == null){
				// Connect the two lists together
				node.setNext(new ListNode(partitionHead));
				break;
			}
			
			else if (node.getNext().getData() > partition){
				if (partitionNode == null){
					partitionNode = new ListNode(node.getNext());
					partitionHead = partitionNode;
					partitionNode.setNext(null);
				}
				else {
					partitionNode.setNext(new ListNode(node.getNext()));
					partitionNode = partitionNode.getNext();
					partitionNode.setNext(null);
				}
				node.setNext(node.getNext().getNext());
			}
			else {
				node = node.getNext();
			}
		}
		
		System.out.println("After: ");
		System.out.println(list);
	}
	
	public static void kToTheLast(LinkedList list, int k){
		if (list == null || list.isEmpty() || k < 0)
			return;
		
		System.out.println("Before: ");
		System.out.println(list);
		
		ListNode kSeeker = list.getHead(), endSeeker = list.getHead();
		int distance = 0;
		
		while (endSeeker != null){
			endSeeker = endSeeker.getNext();
			distance++;
			if (distance > k && kSeeker.getNext() != null){
				kSeeker = kSeeker.getNext();
				distance--;
			}
		}
		
		System.out.printf("The %dth element from the end is %d.\n", k, kSeeker.getData());
		
	}
	public static void removeDupes(LinkedList list){
		if (list == null || list.isEmpty() || list.getHead().getNext() == null)
			return;
		
		System.out.println("Before: ");
		System.out.println(list);
		
		Map<Integer,Integer> map = new HashMap<>();
		ListNode currentNode = list.getHead();
		
		// Add current node to this map
		map.put(currentNode.getData(), 1);
		
		// Go through list and remove duplicates
		while (currentNode != null && currentNode.getNext() != null){
			if (currentNode.getNext().getData() == 1);
			if (map.get(currentNode.getNext().getData()) == null){
				// First time seeing this node
				map.put(currentNode.getNext().getData(), 1);
				currentNode = currentNode.getNext();
			}
			else {
				// The next node is a duplicate; remove it
				currentNode.setNext(currentNode.getNext().getNext());
			}
		}
		
		System.out.println("After: ");
		System.out.println(list);
	}
	
	
	
}
