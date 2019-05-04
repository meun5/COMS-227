package lab10;

import java.util.*;
import library.redux.*;

public class LibraryTestRedux {
	public static void main(String[] args) {
		library.Book b = new library.Book("Ron Jon Jovenson");
		
		Book bb = new Book("Ron Jon Jovenson");
		
		System.out.println(b.getTitle());
		System.out.println(bb.getTitle());
		Calendar c = new GregorianCalendar();
		
		b.checkOut(new library.Patron(), c.getTime());
		bb.checkOut(new Patron(), c.getTime());
		
		System.out.println(b.isCheckedOut());
		System.out.println(bb.isCheckedOut());
		
		c.add(Calendar.MONTH, 3);
		
		System.out.println(b.getFine(c.getTime()));
		System.out.println(bb.getFine(c.getTime()));
	}
}
