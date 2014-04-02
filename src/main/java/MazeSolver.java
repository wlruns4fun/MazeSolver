import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.map.MultiKeyMap;

@SuppressWarnings({"rawtypes", "unchecked"})
public class MazeSolver {

	private Maze maze;
	private MultiKeyMap unvisitedNodes;
	private MultiKeyMap previousNodePath;
	
	public static void main(String[] args) throws Exception {
		if (null == args) {
			throw new RuntimeException("args cannot be null");
		} else {
		
			for (String filename : args) {
				
				if (null == filename) {
					throw new RuntimeException("filename cannot be null");
				} else {
					
					Maze maze = new Maze(filename);
					MazeSolver mazeSolver = new MazeSolver(maze);
					
					mazeSolver.findShortestPathFromStartToEnd();
					String mazeSolution = mazeSolver.getSolution();
					
					System.out.print("\n" + mazeSolution);
				}
			}
		}
	}
	
	public MazeSolver(Maze maze) {
		if (null == maze) {
			throw new RuntimeException("maze cannot be null");
		} else if (null == maze.getStartNode()) {
			throw new RuntimeException("maze startNode cannot be null");
		} else if (null == maze.getEndNode()) {
			throw new RuntimeException("maze endNode cannot be null");
		} else {
			
			this.maze = maze;
		}
	}
	
	public void initializeAllNodes() {
		if (maze != null && maze.getMap() != null && maze.getStartNode() != null && maze.getEndNode() != null) {
			
			unvisitedNodes = new MultiKeyMap();
			previousNodePath = new MultiKeyMap();
			
			MazeNode startNode = maze.getStartNode();
			
			for (int r=0; r<maze.getNumberOfRows(); r++) {
				for (int c=0; c<maze.getNumberOfColumns(); c++) {
					
					Object mazeObj = maze.getMap().get(r, c);
					
					if (mazeObj != null && (char) mazeObj != Maze.WALL) {
						
						if (startNode.getRow() == r && startNode.getColumn() == c) {
							unvisitedNodes.put(r, c, new MazeNode(r, c, 0));
						} else {
							unvisitedNodes.put(r, c, new MazeNode(r, c, Double.POSITIVE_INFINITY));
						}
					}
				}
			}
		}
	}
	
	public void calculateDistanceToAdjacentNodes(MazeNode node) {
		if (null == node) {
			throw new RuntimeException("node cannot be null");
		} else {
			
			MazeNode currentNode = (MazeNode) unvisitedNodes.get(node.getRow(), node.getColumn());
			
			if (null == currentNode) {
				throw new RuntimeException("node("+node.getRow()+","+node.getColumn()+") is not an unvisited node");
			} else {
				
				MazeNode up = node.up();
				MazeNode down = node.down();
				MazeNode left = node.left();
				MazeNode right = node.right();
				
				MazeNode upNode = (MazeNode) unvisitedNodes.get(up.getRow(), up.getColumn());
				MazeNode downNode = (MazeNode) unvisitedNodes.get(down.getRow(), down.getColumn());
				MazeNode leftNode = (MazeNode) unvisitedNodes.get(left.getRow(), left.getColumn());
				MazeNode rightNode = (MazeNode) unvisitedNodes.get(right.getRow(), right.getColumn());
				
				double adjDistance = currentNode.getDistance() + 1;
				
				if (upNode != null && adjDistance < upNode.getDistance()) {
					
					upNode.setDistance(adjDistance);
					
					unvisitedNodes.put(upNode.getRow(), upNode.getColumn(), upNode);
					previousNodePath.put(upNode.getRow(), upNode.getColumn(), node);
				}
				
				if (downNode != null && adjDistance < downNode.getDistance()) {
					
					downNode.setDistance(adjDistance);
					
					unvisitedNodes.put(downNode.getRow(), downNode.getColumn(), downNode);
					previousNodePath.put(downNode.getRow(), downNode.getColumn(), node);
				}
				
				if (leftNode != null && adjDistance < leftNode.getDistance()) {
					
					leftNode.setDistance(adjDistance);
					
					unvisitedNodes.put(leftNode.getRow(), leftNode.getColumn(), leftNode);
					previousNodePath.put(leftNode.getRow(), leftNode.getColumn(), node);
				}
				
				if (rightNode != null && adjDistance < rightNode.getDistance()) {
					
					rightNode.setDistance(adjDistance);
					
					unvisitedNodes.put(rightNode.getRow(), rightNode.getColumn(), rightNode);
					previousNodePath.put(rightNode.getRow(), rightNode.getColumn(), node);
				}
			}
		}
	}
	
	Comparator<MazeNode> compareNodesByDistance = new Comparator<MazeNode>() {
		@Override
		public int compare(MazeNode node1, MazeNode node2) {
			if (null == node1) {
				throw new RuntimeException("node1 cannot be null");
			} else if (null == node2) {
				throw new RuntimeException("node2 cannot be null");
			} else if (node1.getDistance() < node2.getDistance()) {
				return -1;
			} else if (node1.getDistance() > node2.getDistance()) {
				return 1;
			} else {
				return 0;
			}
		}
	};
	
	public MazeNode getUnvisitedNodeWithShortestDistance() {
		if (null == unvisitedNodes) {
			throw new RuntimeException("unvisitedNodes is null");
		} else if (0 == unvisitedNodes.size()) {
			return null;
		} else {
			
			List<MazeNode> unvisitedNodesByDistance = new ArrayList<MazeNode>(unvisitedNodes.values());
			Collections.sort(unvisitedNodesByDistance, compareNodesByDistance);
			
			MazeNode nodeWithShortestDistance = unvisitedNodesByDistance.get(0);
			return nodeWithShortestDistance;
		}
	}
	
	public boolean haveWeReachedTheEnd(MazeNode node) {
		if (null == node) {
			throw new RuntimeException("node is null");
		} else if (null == unvisitedNodes) {
			throw new RuntimeException("unvisitedNodes is null");
		} else if (null == maze) {
			throw new RuntimeException("maze cannot be null");
		} else {
			
			MazeNode endNode = maze.getEndNode();
			
			if (null == endNode) {
				throw new RuntimeException("maze endNode cannot be null");
			} else {
				return (node.getRow() == endNode.getRow() && node.getColumn() == endNode.getColumn());
			}
		}
	}
	
	public void findShortestPathFromStartToEnd() {
		if (null == maze) {
			throw new RuntimeException("maze cannot be null");
		} else if (null == maze.getStartNode()) {
			throw new RuntimeException("maze startNode cannot be null");
		} else if (null == maze.getEndNode()) {
			throw new RuntimeException("maze endNode cannot be null");
		} else {
			
			initializeAllNodes();
			
			while (unvisitedNodes != null && unvisitedNodes.size() > 0) {
				
				MazeNode node = getUnvisitedNodeWithShortestDistance();
				
				if (null == node) {
					throw new RuntimeException("unvisitedNodeWithShortestDistance is null");
				} else if (Double.isInfinite(node.getDistance())) {
					throw new RuntimeException("maze has no solution");
				} else if (haveWeReachedTheEnd(node)) {
					return;
				} else {

					calculateDistanceToAdjacentNodes(node);
					
					unvisitedNodes.removeMultiKey(node.getRow(), node.getColumn());
				}
			}
		}
		
	}
	
	public String getSolution() {
		if (null == maze) {
			throw new RuntimeException("maze cannot be null");
		} else if (null == maze.getStartNode()) {
			throw new RuntimeException("maze startNode cannot be null");
		} else if (null == maze.getEndNode()) {
			throw new RuntimeException("maze endNode cannot be null");
		} else if (null == previousNodePath) {
			throw new RuntimeException("previousNodePath is null");
		} else {
			
			MazeNode endNode = maze.getEndNode();
			MazeNode node = (MazeNode) unvisitedNodes.get(endNode.getRow(), endNode.getColumn());
			
			if (null == node) {
				throw new RuntimeException("unvisitedNodes does not contain the endNode");
			} else {
				
				String solutionString = "";
				List<MazeNode> solutionPath = new ArrayList<MazeNode>();
				MultiKeyMap solutionMap = new MultiKeyMap();
			
				do {
					solutionPath.add(0, node);
					solutionMap.put(node.getRow(), node.getColumn(), node);
					node = (MazeNode) previousNodePath.get(node.getRow(), node.getColumn());
				} while (node != null);
				
				for (int row=0; row<maze.getNumberOfRows(); row++) {
					for (int column=0; column<maze.getNumberOfColumns(); column++) {
						
						Object mazeObj = maze.getMap().get(row, column);
						Object solutionObj = solutionMap.get(row, column);
						
						if (null == mazeObj) {
							solutionString += Maze.UNDEFINED;
						} else if (solutionObj != null && (char) mazeObj != Maze.START && (char) mazeObj != Maze.END) {
							solutionString += Maze.SOLUTION;
						} else {
							solutionString += (char) mazeObj;
						}
					}
					
					solutionString += "\n";
				}
				
				solutionString += "\n";
				
				for (MazeNode solutionNode : solutionPath) {
					solutionString += solutionNode.toString() + "\n";
				}
				
				return solutionString;
			}
		}
	}
	
	public MultiKeyMap getUnvisitedNodes() {
		return unvisitedNodes;
	}
	
	public MultiKeyMap getPreviousNodePath() {
		return previousNodePath;
	}
}
