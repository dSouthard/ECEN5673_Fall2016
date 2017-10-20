package codingInterview;

public class AnimalShelter{
	public class AnimalNode{
		private boolean cat;
		private AnimalNode next;
		
		public AnimalNode(boolean isCat){
			this.cat = isCat;
			next = null;
		}
		
		public void setNext(AnimalNode next){
			this.next = next;
		}
		
		public AnimalNode getNext(){
			return next;
		}
		
		public void setCat(boolean cat){
			this.cat = cat;
		}
		
		public boolean isCat(){
			return cat;
		}
		
		@Override
		public String toString(){
			return cat? "cat" : "dog";
		}
	}
	
	private AnimalNode head = null;
	
	public void enqueue(boolean cat){
		if (head != null){
			AnimalNode node = head;
			while (node.getNext() != null){
				node = node.getNext();
			}
			node.setNext(new AnimalNode(cat));
		}
		else {
			head = new AnimalNode(cat);
		}
	}
	
	public AnimalNode dequeueAny(){
		if (head != null){
			AnimalNode node = head;
			head = head.getNext();
			return node;
		}
		else {
			return null;
		}
	}
	
	public AnimalNode dequeueCat(boolean getCat){
		if (head != null){
			AnimalNode node = head;
			if (node.isCat() && getCat || !node.isCat() && getCat){
				return dequeueAny();	// First node was a cat, wanted to find a cat, so return the first node
			}
			while (node.getNext() != null){
				// Either looking for a cat node or a dog node
				if ((node.getNext().isCat() && getCat) || (!node.getNext().isCat() && getCat)){
					AnimalNode temp = node.getNext();
					node.setNext(node.getNext().getNext());
					return temp;
				}
			}
		}
		return null;
	}
	
	public AnimalNode dequeueDog(){
		return dequeueCat(false);
	}
	
	public AnimalNode dequeueCat(){
		return dequeueCat(true);
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("Animals in the shelter: ");
		AnimalNode node = head;
		while (node != null){
			if (node.isCat())
				builder.append(" cat ");
			else
				builder.append(" dog ");
			node = node.getNext();
		}		
		return builder.toString();
	}
	
	public boolean isEmpty(){
		return head == null;
	}
}
