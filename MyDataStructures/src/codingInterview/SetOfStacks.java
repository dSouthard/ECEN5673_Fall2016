package codingInterview;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import codingInterview.Stack.StackNode;

public class SetOfStacks<T> {
	private class SizedStack<T> extends Stack<T> {
		private int size;

		public T pop(){
			size--;
			return super.pop();
			
		}
			
		public void push (T item){
			super.push(item);
			size++;
		}
		
		public int getSize(){
			return size;
		}
	}
	
	private List<SizedStack<T>> stacks;
	private int capacity;
	private int currentStack = -1;
	
	public SetOfStacks(int capacity, T data){
		this.capacity = capacity;
		stacks = new ArrayList<>();
		addNewStack(data);
	}
	
	public void push(T item){
		if (!stacks.isEmpty()){
			// Push onto the current stack
			if (stacks.get(currentStack).getSize() < this.capacity) {
				stacks.get(currentStack).push(item);
				return;
			}
		}
		// Create a new stack and push onto that
		addNewStack(item);
	}
	
	private void addNewStack(T item){
		stacks.add(new SizedStack<T>());
		currentStack++;
		stacks.get(currentStack).push(item);
	}
	
	public T pop(){
		if (!stacks.isEmpty()){
			if (!(stacks.get(currentStack).isEmpty())){
				T item = stacks.get(currentStack).pop();
				if (stacks.get(currentStack).isEmpty()){
					currentStack--;
				}
				return item;
			}
			else {
				currentStack--;
				while (stacks.get(currentStack) != null)
					if (!stacks.get(currentStack).isEmpty()){
						return stacks.get(currentStack).pop();
					}
					else {
						currentStack--;
					}
			}
		}
		return null;
	}
	
	public T popAt(int index){
		if (stacks.get(index) == null || stacks.get(index).isEmpty())
			return null;
		
		return stacks.get(index).pop();
	}
	
}
