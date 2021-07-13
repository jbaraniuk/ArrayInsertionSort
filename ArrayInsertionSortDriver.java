/**
 * Justin Baraniuk
 */

public class ArrayInsertionSortDriver {

	public static void main(String[] args) {
		ArrayPositionalList<Integer> list;
		ArrayPositionalList<Integer> list2;
		
		// Illustrate all implemented public methods
		list = new ArrayPositionalList<>();
		for (int i = 0; i < 5; i++) 
			list.add(list.size(), i+1);
		System.out.println("Creating initial list\n" + list);
		
		System.out.println("isEmpty\n" + list.isEmpty());
		System.out.println("size\n" + list.size());
		
		Position<Integer> position = list.first();
		System.out.println("position first element\n" + position);
		
		position = list.last();
		System.out.println("last element\n" + position);
		
		position = list.before(position);
		System.out.println("element before position\n" + position);
		
		position = list.after(list.first());
		System.out.println("element after the first position\n" + position);
		
		list.addFirst(42);
		System.out.println("add 42 to first position\n" + list);
		
		list.addLast(99);
		System.out.println("add 99 to last position\n" + list);
		
		list.addBefore(position, 101);
		System.out.println("add 101 before position\n" + list);
		
		list.addAfter(position, 1007);
		System.out.println("add 1007 after position\n" + list);
		
		list.set(position, 404);
		System.out.println("set position to 404\n" + list);
		
		list.remove(list.first());
		System.out.println("remove first position\n" + list);
		
		
		// Illustrate sorting method
		list2 = new ArrayPositionalList<>();
		list2.addLast(8); 
		list2.addLast(6);
		list2.addLast(7);
		list2.addLast(5);
		list2.addLast(3);
		list2.addLast(0);
		list2.addLast(9);
		System.out.println("\nIllustrating sorting method:\n" + list2);
		insertionSort(list2);
		System.out.println(list2);
	}

	public static void insertionSort(ArrayPositionalList<Integer> list) {
		Position<Integer> marker = list.first();
		while (marker != list.last()) {
			Position<Integer> pivot = list.after(marker);
			int value = pivot.getElement();
			if (value > marker.getElement())
				marker = pivot;
			else {
				Position<Integer> walk = marker;
				while (walk != list.first() && list.before(walk).getElement() > value)
					walk = list.before(walk);
				list.remove(pivot);
				list.addBefore(walk, value);
			}
		}
	}
}
