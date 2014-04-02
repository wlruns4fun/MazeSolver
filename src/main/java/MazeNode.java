public class MazeNode {

	private int row;
	private int column;
	private double distance; 
	
	public MazeNode(int row, int column) {
		this.row = row;
		this.column = column;
		this.distance = Double.POSITIVE_INFINITY;
	}
	
	public MazeNode(int row, int column, double distance) {
		this.row = row;
		this.column = column;
		this.distance = distance;
	}
	
	public MazeNode up() {
		return new MazeNode(row-1, column);
	}
	
	public MazeNode down() {
		return new MazeNode(row+1, column);
	}
	
	public MazeNode left() {
		return new MazeNode(row, column-1);
	}
	
	public MazeNode right() {
		return new MazeNode(row, column+1);
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	@Override
	public String toString() {
		return "(" + row + ", " + column + ") := " + Double.toString(distance);
	}
}
