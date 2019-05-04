package lab5;

public class Basketball {
	
	private double diametre = 0;

	private boolean isInflated = false;
	
	public Basketball(int diametre) {
		this.diametre = diametre;
	}
	
	public void inflate() {
		this.isInflated = true;
	}

	public boolean isDribbleable() {
		return this.isInflated;
	}
	
	public double getCircumfrence() {
		return new Double(1.2);
	}
	
	public double getDiametre() {
		return this.diametre;
	}
}
