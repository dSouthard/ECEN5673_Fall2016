package codingInterview;
import java.util.EmptyStackException;

public class Stack <T>{
	public static class StackNode<T> {
		protected T data;
		protected StackNode<T> next;
		
		protected StackNode(T data){
			this.data = data;
		}
	}
	
	protected StackNode<T> top;
	
	public T pop(){
		if (top == null) throw new EmptyStackException();
		T item = top.data;
		top = top.next;
		return item;
	}
	
	public void push (T item){
		StackNode<T> newNode = new StackNode(item);
		newNode.next = top;
		top = newNode;
	}
	
	public T peek(){
		if (top == null) throw new EmptyStackException();
		return top.data;
	}
	
	public boolean isEmpty(){
		return top == null;
	}

}
