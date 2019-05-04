package library.redux;

import java.util.Date;

/**
 * A Documentary is a library item that can be checked out for 14 days and
 * cannot be renewed. If overdue, the fine is 1.00 per day for the first 5 days
 * and .50 per day thereafter, up to a maximum equal to the item's replacement
 * cost.
 */
public class Documentary extends AbstractItem implements Item {
	/**
	 * Constructs a Documentary with the given title, replacement cost, and
	 * duration.
	 * 
	 * @param givenTitle
	 *            title for this item
	 * @param givenCost
	 *            replacement cost for this item, in dollars
	 * @param givenDuration
	 *            duration of this item, in minutes
	 */
	public Documentary(String givenTitle, double givenCost, int givenDuration) {
		super();

		checkOutDays = 14;

		title = givenTitle;
		replacementCost = givenCost;
		duration = givenDuration;
	}

	@Override
	public void renew(Date now) {
		// cannot be renewed
	}

	@Override
	public double getFine(Date now) {
		if (isCheckedOut() && isOverdue(now)) {
			// how late is it, in milliseconds
			double elapsed = now.getTime() - dueDate.getTime();

			// how late is it, in days
			int millisPerDay = 24 * 60 * 60 * 1000;
			int daysLate = (int) Math.ceil(elapsed / millisPerDay);

			// compute the fine
			double fine;
			if (daysLate <= 5) {
				fine = daysLate;
			} else {
				fine = 5 + (daysLate - 5) * .50;
			}

			return Math.min(fine, replacementCost);
		} else {
			return 0;
		}
	}
}
