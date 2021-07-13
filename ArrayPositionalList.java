/**
 * Justin Baraniuk
 */

public class ArrayPositionalList<E> implements PositionalList<E> {
	  
	private static class ArrPos<E> implements Position<E> {
	        private int index;  
	        private E element;  
	        
	        public ArrPos(E e, int i){
	            index = i;
	            element = e;
	        }

	        public E getElement() throws IllegalStateException {
	           if (index == -1)
	               throw new IllegalStateException("Position no longer valid");
	           return element;
	        }

	        public void setElement(E e){
	            element = e;
	        }

	        public int getIndex(){
	            return index;
	        }

	        public void setIndex(int i){
	            index = i;
	        }
	       
	        public String toString() {
	    		return "["+ index + "] " + element.toString() + " ";
	    	}
	        
	        public boolean equals(Object o) {
	    		Position<E> p = (Position<E>) o;
	    		return this.getElement() == p.getElement();
	        }
	        
	}
	
	private static final int CAPACITY = 16;
	private ArrPos<E>[] data; 
	private int size = 0;
	
	// a) Constructors
	public ArrayPositionalList() { this(CAPACITY); }
	public ArrayPositionalList(int capacity) {
		data = (ArrPos<E>[]) new ArrPos[capacity];
	}
	
	// b) size and isEmpty
	public int size( ) { return size; }
	public boolean isEmpty() { return size == 0; }
	
	// c) first and last
	public Position<E> first() { return data[0]; }
	public Position<E> last() { return data[size - 1]; }
	
	public Position<E> before(Position<E> p) throws IllegalArgumentException {
		ArrPos<E> arrPos = validate(p);
		return data[arrPos.getIndex() - 1];
	}
	
	public Position<E> after(Position<E> p) throws IllegalArgumentException {
		ArrPos<E> arrPos = validate(p);
		return data[arrPos.getIndex() + 1];
	}
	
	public Position<E> addFirst(E e) {
		add(0, e);
		return first();
	}
	
	public Position<E> addLast(E e) {
		add(size, e);
		return last();
	}
	
	public Position<E> addAfter(Position<E> p, E e) {
		ArrPos<E> arrPos = validate(p);
		add(arrPos.getIndex()+1, e);
		return arrPos;
	}
	
	public Position<E> addBefore(Position<E> p, E e) {
		ArrPos<E> arrPos = validate(p);
		add(arrPos.getIndex(), e);
		return arrPos;
	}
		
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		ArrPos<E> arrPos = validate(p);
		checkIndex(arrPos.getIndex(), size);
		arrPos.setElement(e);
		return e;
	}
	
	public E remove(Position<E> p) throws IllegalArgumentException {
		ArrPos<E> arrPos = validate(p);
		E temp = arrPos.getElement();
		int index = arrPos.getIndex();
		for (int k = index; k < size - 1; k++) {
			data[k+1].setIndex(k);
			data[k] = data[k+1];
		}
		data[size-1] = null;
		arrPos = null;
		size--;
		return temp;
		
	}
	
	public void add(int i, E e) throws IndexOutOfBoundsException,
											IllegalStateException {
		checkIndex(i, size + 1);
		if (size == data.length)
			throw new IllegalStateException("Array is full");
		for (int k = size - 1; k >= i; k--) {
			data[k].setIndex(k+1);
			data[k+1] = data[k];		
		}
		data[i] = new ArrPos<E>(e, i);
		size++;
	}
	
	private ArrPos<E> validate(Position<E> p) throws IllegalArgumentException {
		if(!(p instanceof ArrPos)) throw new IllegalArgumentException("Invalid p");
		ArrPos<E> arrPos = (ArrPos<E>) p;
		if (data[arrPos.getIndex()] == null)
			throw new IllegalArgumentException("p is no longer in the list");
		return arrPos;
	}

	protected void checkIndex(int i, int n) throws IndexOutOfBoundsException { 
		if (i < 0 || i >= n)
			throw new IndexOutOfBoundsException("Illegal index: " + i);
		}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) 
			return false;
		if (getClass() != o.getClass()) 
			return false;	
	
		ArrayPositionalList<E> other = (ArrayPositionalList<E>) o;
		if (size != other.size()) 
			return false;
		for (int i = 0; i < size; i++) {
			if (!data[i].equals(other.data[i])) 
				return false;
		}
		return true;
	}
		
	public String toString() {
		int index = 0;
		String s = "";
		ArrPos<E> position = data[index];
		while (position != null) {
			s = s += position.toString();
			position = data[++index];
		}
		return s;
	}
}
