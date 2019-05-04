package lab10;

public class IntTestRedux {
	public static void main(String[] args) {
		IntListSortedRedux a = new IntListSortedRedux();
		
//		a.add(5);
//		a.add(1);
//		a.add(5);
//		a.add(2);
//		a.add(5);
//		a.add(4);
//		a.add(1);
//		a.add(6);
//		a.add(1);
//		a.add(1);
//		a.add(1);
		
		a.add(1);
		a.add(3);
		a.add(0);
		a.add(10);
		
		System.out.println(a.toString());
		System.out.println(a.getMaximum());
		System.out.println(a.getMinimum());
		System.out.println(a.getMedian());
	}
}
