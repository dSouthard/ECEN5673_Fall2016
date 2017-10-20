package codingInterview;

public class InterviewQuestionsThree {

	public static void main(String[] args){
		boolean cat = true, dog = false;
		AnimalShelter animalShelterA = new AnimalShelter();
		AnimalShelter animalShelterB = new AnimalShelter();
		
		animalShelterA.enqueue(cat);
		animalShelterA.enqueue(cat);
		animalShelterA.enqueue(cat);
		animalShelterA.enqueue(dog);
		animalShelterA.enqueue(cat);
		animalShelterA.enqueue(cat);
		animalShelterA.enqueue(dog);
		animalShelterA.enqueue(cat);
		
		animalShelterB.enqueue(dog);
		animalShelterB.enqueue(dog);
		animalShelterB.enqueue(cat);
		animalShelterB.enqueue(dog);
		animalShelterB.enqueue(dog);
		animalShelterB.enqueue(cat);
		animalShelterB.enqueue(dog);
		animalShelterB.enqueue(cat);
		
		System.out.println(animalShelterA);
		System.out.println(animalShelterB);
		
		while(!animalShelterA.isEmpty() && animalShelterA.dequeueCat() != null){
			System.out.println("Removed a " + animalShelterA.dequeueCat()+ " from shelter A");
			System.out.println(animalShelterA);
		}
		
		while(!animalShelterA.isEmpty() && animalShelterA.dequeueDog() != null){
			System.out.println("Removed a " + animalShelterA.dequeueDog() + " from shelter A");
			System.out.println(animalShelterA);
		}
		
		while(!animalShelterB.isEmpty()&& animalShelterB.dequeueCat() != null){
			System.out.println("Removed a " + animalShelterB.dequeueCat()+ " from shelter B");
			System.out.println(animalShelterB);
		}
		
		while(!animalShelterB.isEmpty()&& animalShelterB.dequeueDog() != null){
			System.out.println("Removed a " + animalShelterB.dequeueDog()+ " from shelter B");
			System.out.println(animalShelterB);
		}
	}
	
	
}
