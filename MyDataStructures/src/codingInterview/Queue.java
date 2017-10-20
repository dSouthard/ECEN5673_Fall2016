package codingInterview;
import java.util.NoSuchElementException;

public class Queue<T> {
	private static class QueueNode<T>{
		private T data;
		private QueueNode<T> next;
		
		public QueueNode(T data){
			this.data = data;
		}
	}
	
	private QueueNode<T> head;
	private QueueNode<T> tail;
	
	public void add(T data){
		QueueNode<T> item = new QueueNode<>(data);
		if (tail != null) {
			tail.next = item;
		}
		tail = item;
		if (head == null)
			head = item;
	}
	
	public T remove(){
		if (head == null) throw new NoSuchElementException();
		
		T item = head.data;
		head = head.next;
		if (head == null){
			tail = null;
		}
		
		return item;
	}
	
	public T peek(){
		if (head == null) throw new NoSuchElementException();
		
		return head.data;
	}
	
	public boolean isEmpty(){
		return head == null;
	}
	
}
