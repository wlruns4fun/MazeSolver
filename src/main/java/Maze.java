import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.commons.collections4.map.MultiKeyMap;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Maze {
	
	public static final char OPEN = '.';
	public static final char WALL = 'X';
	public static final char START = 'S';
	public static final char END = 'E';
	public static final char UNDEFINED = '#';
	public static final char SOLUTION = '*';

	private MultiKeyMap map;
	private int numberOfRows;
	private int numberOfColumns;

	private MazeNode startNode;
	private MazeNode endNode;
	
	public Maze(MultiKeyMap map, int numberOfRows, int numberOfColumns) {
		if (null == map) {
			throw new RuntimeException("map cannot be null");
		} else {
			this.map = map;
		}
		
		if (numberOfRows <= 0) {
			throw new RuntimeException("numberOfRows("+numberOfRows+") must be > 0");
		} else {
			this.numberOfRows = numberOfRows;
		}
		
		if (numberOfColumns <= 0) {
			throw new RuntimeException("numberOfCols("+numberOfColumns+") must be > 0");
		} else {
			this.numberOfColumns = numberOfColumns;
		}
	}
	
	public Maze(String filename) throws Exception {
		if (null == filename) {
			throw new RuntimeException("maze filename is null");
		} else {
			
			numberOfRows = 0;
			numberOfColumns = 0;
			map = new MultiKeyMap();
			MazeNode startNode = null;
			MazeNode endNode = null;
			
			FileInputStream fileInputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;
			
			try {
				
				fileInputStream = new FileInputStream(filename);
				inputStreamReader = new InputStreamReader(fileInputStream);
				bufferedReader = new BufferedReader(inputStreamReader);
				
				String mazeRowString = null;
				while ((mazeRowString = bufferedReader.readLine()) != null) {
					
					for (int column=0; column<mazeRowString.length(); column++) {
						int row = numberOfRows;
						
						char mazeChar = mazeRowString.charAt(column);
						
						if (Maze.OPEN == mazeChar || Maze.WALL == mazeChar || Maze.START == mazeChar || Maze.END == mazeChar) {
							
							map.put(row, column, mazeChar);
							
							if (Maze.START == mazeChar && startNode != null) {
								throw new RuntimeException("startNode is already defined");
							} else if (Maze.START == mazeChar /*&& null == startNode*/) {
								startNode = new MazeNode(row, column);
							}
							
							if (Maze.END == mazeChar && endNode != null) {
								throw new RuntimeException("endNode is already defined");
							} else if (Maze.END == mazeChar /*&& null == endNode*/) {
								endNode = new MazeNode(row, column);
							}
						}
						
					}
					
					numberOfRows++;
					if (mazeRowString.length() > numberOfColumns) {
						numberOfColumns = mazeRowString.length();
					}
				}
				
				if (null == startNode) {
					throw new RuntimeException("startNode is not defined");
				} else {
					setStartNode(startNode);
				}
				
				if (null == endNode) {
					throw new RuntimeException("endNode is not defined");
				} else {
					setEndNode(endNode);
				}
				
			} finally {
				
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			}
		}
	}
	
	public boolean isWithinMazeBoundaries(MazeNode node) {
		if (null == node) {
			throw new RuntimeException("node cannot be null");
		} else {
			return (node.getRow() >= 0 && node.getRow() < numberOfRows && node.getColumn() >= 0 && node.getColumn() < numberOfColumns);
		}
	}
	
	public void setStartNode(MazeNode node) {
		if (null == node) {
			throw new RuntimeException("node cannot be null");
		} else if (!isWithinMazeBoundaries(node)) {
			throw new RuntimeException("startNode("+node.getRow()+","+node.getColumn()+") must be between (0,0) & ("+(numberOfRows-1)+","+(numberOfColumns-1)+")");
		} else if (startNode != null) {
			throw new RuntimeException("startNode is already defined");
		} else {
			startNode = new MazeNode(node.getRow(), node.getColumn(), 0);
			map.put(startNode.getRow(), startNode.getColumn(), START);
		}
	}
	
	public void setEndNode(MazeNode node) {
		if (null == node) {
			throw new RuntimeException("node cannot be null");
		} else if (!isWithinMazeBoundaries(node)) {
			throw new RuntimeException("endNode("+node.getRow()+","+node.getColumn()+") must be between (0,0) & ("+(numberOfRows-1)+","+(numberOfColumns-1)+")");
		} else if (endNode != null) {
			throw new RuntimeException("endNode is already defined");
		} else {
			endNode = node;
			map.put(endNode.getRow(), endNode.getColumn(), END);
		}
	}
	
	public void validateCurrentNode(MazeNode node) {
		if (null == node) {
			throw new RuntimeException("node cannot be null");
		} else {
			
			Object nodeObj = map.get(node.getRow(), node.getColumn());
			
			if (!isWithinMazeBoundaries(node)) {
				throw new RuntimeException("node("+node.getRow()+","+node.getColumn()+") must be between (0,0) & ("+(numberOfRows-1)+","+(numberOfColumns-1)+")");
			} else if (null == nodeObj) {
				throw new RuntimeException("node("+node.getRow()+","+node.getColumn()+") has not been defined");
			} else if ((char) nodeObj != OPEN) {
				throw new RuntimeException("node("+node.getRow()+","+node.getColumn()+") is not an open node");
			}
		}
	}
	
	public MazeNode up(MazeNode node) {
		validateCurrentNode(node);
				
		MazeNode upNode = node.up();
		Object upNodeObj = map.get(upNode.getRow(), upNode.getColumn());
		
		if (!isWithinMazeBoundaries(upNode) || null == upNodeObj || (char) upNodeObj != OPEN) {
			return null;
		} else {
			return upNode;
		}
	}
	
	public MazeNode down(MazeNode node) {
		validateCurrentNode(node);
				
		MazeNode downNode = node.down();
		Object downNodeObj = map.get(downNode.getRow(), downNode.getColumn());
		
		if (!isWithinMazeBoundaries(downNode) || null == downNodeObj || (char) downNodeObj != OPEN) {
			return null;
		} else {
			return downNode;
		}
	}
	
	public MazeNode left(MazeNode node) {
		validateCurrentNode(node);
				
		MazeNode leftNode = node.left();
		Object leftNodeObj = map.get(leftNode.getRow(), leftNode.getColumn());
		
		if (!isWithinMazeBoundaries(leftNode) || null == leftNodeObj || (char) leftNodeObj != OPEN) {
			return null;
		} else {
			return leftNode;
		}
	}
	
	public MazeNode right(MazeNode node) {
		validateCurrentNode(node);
				
		MazeNode rightNode = node.right();
		Object rightNodeObj = map.get(rightNode.getRow(), rightNode.getColumn());
		
		if (!isWithinMazeBoundaries(rightNode) || null == rightNodeObj || (char) rightNodeObj != OPEN) {
			return null;
		} else {
			return rightNode;
		}
	}
	
	public MultiKeyMap getMap() {
		return map;
	}
	
	public int getNumberOfRows() {
		return numberOfRows;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}
	
	public MazeNode getStartNode() {
		return startNode;
	}
	
	public MazeNode getEndNode() {
		return endNode;
	}
	
	@Override
	public String toString() {
		String mazeString = "";
		
		for (int row=0; row<numberOfRows; row++) {
			for (int column=0; column<numberOfColumns; column++) {
				
				Object node = map.get(row, column);
				if (null == node) {
					mazeString += UNDEFINED;
				} else {
					mazeString += (char) node;
				}
			}
			
			mazeString += "\n";
		}
		
		return mazeString;
	}
}
