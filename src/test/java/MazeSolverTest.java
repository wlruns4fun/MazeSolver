import org.apache.commons.collections4.map.MultiKeyMap;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

@SuppressWarnings({"rawtypes","unchecked"})
public class MazeSolverTest {
	
	@Test
	public void main_args_throwsARuntimeExceptionIfArgsAreNull() {
		// Given:
		String[] args = null;
		
		// When:
		Exception actualException = null;
		try {
			MazeSolver.main(args);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("main() should throw an Exception", actualException);
		Assert.assertEquals("main() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void main_args_throwsARuntimeExceptionIfFilenameIsNull() {
		// Given:
		String[] args = {null};
		
		// When:
		Exception actualException = null;
		try {
			MazeSolver.main(args);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("main() should throw an Exception", actualException);
		Assert.assertEquals("main() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void mazeSolver_throwsARuntimeExceptionIfMazeIsNull() {
		// Given:
		Maze maze = null;
		
		// When:
		Exception actualException = null;
		try {
			new MazeSolver(maze);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("MazeSolver() should throw an Exception", actualException);
		Assert.assertEquals("MazeSolver() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void mazeSolver_throwsARuntimeExceptionIfMazeStartNodeIsNull() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 1);
		maze.setEndNode(new MazeNode(0, 0));
		
		// When:
		Exception actualException = null;
		try {
			new MazeSolver(maze);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("MazeSolver() should throw an Exception", actualException);
		Assert.assertEquals("MazeSolver() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void mazeSolver_throwsARuntimeExceptionIfMazeEndNodeIsNull() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		Maze maze = new Maze(map, 1, 1);
		maze.setStartNode(new MazeNode(0, 0));
		
		// When:
		Exception actualException = null;
		try {
			new MazeSolver(maze);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("MazeSolver() should throw an Exception", actualException);
		Assert.assertEquals("MazeSolver() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void initializeAllNodes_doesNotAddANodeForWallsInTheMaze() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); 
		map.put(0, 2, Maze.WALL); 
		map.put(0, 3, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 4);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 3));
		MazeSolver mazeSolver = new MazeSolver(maze);
		
		// When:
		mazeSolver.initializeAllNodes();
		
		// Then:
		MultiKeyMap unvisitedNodes = mazeSolver.getUnvisitedNodes();
		Assert.assertNotNull("initializeAllNodes() should initialize the unvisitedNodes", unvisitedNodes);
		Assert.assertEquals("initializeAllNodes() should have 3 items", 3, unvisitedNodes.size());
	}
	
	@Test
	public void initializeAllNodes_setsEachStartNodeValueToZero() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolver = new MazeSolver(maze);
		
		// When:
		mazeSolver.initializeAllNodes();
		
		// Then:
		MultiKeyMap unvisitedNodes = mazeSolver.getUnvisitedNodes();
		Assert.assertNotNull("initializeAllNodes() should initialize the unvisitedNodes", unvisitedNodes);
		
		MazeNode node = (MazeNode) unvisitedNodes.get(0, 0);
		Assert.assertNotNull("initializeAllNodes() should set each start node", node);
		Assert.assertEquals("initializeAllNodes() should set each start node distance to 0", 0, node.getDistance(), 0.0);
	}
	
	@Test
	public void initializeAllNodes_setsEachOpenNodeValueToInfinity() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN);
		map.put(0, 2, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 3);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 2));
		MazeSolver mazeSolver = new MazeSolver(maze);
		
		// When:
		mazeSolver.initializeAllNodes();
		
		// Then:
		MultiKeyMap unvisitedNodes = mazeSolver.getUnvisitedNodes();
		Assert.assertNotNull("initializeAllNodes() should initialize the unvisitedNodes", unvisitedNodes);
		
		MazeNode node = (MazeNode) unvisitedNodes.get(0, 1);
		Assert.assertNotNull("initializeAllNodes() should set each start node", node);
		Assert.assertTrue("initializeAllNodes() should set each open node distance to infinity", Double.isInfinite(node.getDistance()));
	}
	
	@Test
	public void initializeAllNodes_setsEachEndNodeValueToInfinity() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolver = new MazeSolver(maze);
		
		// When:
		mazeSolver.initializeAllNodes();
		
		// Then:
		MultiKeyMap unvisitedNodes = mazeSolver.getUnvisitedNodes();
		Assert.assertNotNull("initializeAllNodes() should initialize the unvisitedNodes", unvisitedNodes);
		
		MazeNode node = (MazeNode) unvisitedNodes.get(0, 1);
		Assert.assertNotNull("initializeAllNodes() should set each end node", node);
		Assert.assertTrue("initializeAllNodes() should set each end node distance to infinity", Double.isInfinite(node.getDistance()));
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_throwsARuntimeExceptionIfCurrentNodeIsNull() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolver = new MazeSolver(maze);
		
		mazeSolver.initializeAllNodes();
		MazeNode node = null;
		
		// When:
		Exception actualException = null;
		try {
			mazeSolver.calculateDistanceToAdjacentNodes(node);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("calculateDistanceToAdjacentNodes() should throw an Exception", actualException);
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_throwsARuntimeExceptionIfCurrentNodeIsNotInTheListOfUnvisitedNodes() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolver = new MazeSolver(maze);
		
		mazeSolver.initializeAllNodes();
		MazeNode node = new MazeNode(0, 3);
		
		// When:
		Exception actualException = null;
		try {
			mazeSolver.calculateDistanceToAdjacentNodes(node);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("calculateDistanceToAdjacentNodes() should throw an Exception", actualException);
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_doesNotUpdateTheUpNodeDistanceIfItIsNotInTheListOfUnvisitedNodes() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); 
		map.put(1, 0, Maze.OPEN); // START
		map.put(2, 0, Maze.OPEN); // END
		Maze maze = new Maze(map, 3, 1);
		maze.setStartNode(new MazeNode(1, 0));
		maze.setEndNode(new MazeNode(2, 0));
		MazeSolver mazeSolver = new MazeSolver(maze);
		
		mazeSolver.initializeAllNodes();
		mazeSolver.getUnvisitedNodes().removeMultiKey(0, 0);
		MazeNode node = new MazeNode(1, 0);
		
		// When:
		mazeSolver.calculateDistanceToAdjacentNodes(node);
		
		// Then:
		MultiKeyMap unvisitedNodes = mazeSolver.getUnvisitedNodes();
		MazeNode upNode = (MazeNode) unvisitedNodes.get(0, 0);
		Assert.assertNull("calculateDistanceToAdjacentNodes() should not update the upNode distance", upNode); 
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_doesNotUpdateTheUpNodeDistanceIfItIsMoreThanItsCurrentValue() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		map.put(1, 0, Maze.OPEN); // START
		map.put(2, 0, Maze.OPEN); // END
		Maze maze = new Maze(map, 3, 1);
		maze.setStartNode(new MazeNode(1, 0));
		maze.setEndNode(new MazeNode(2, 0));
		MazeSolver mazeSolver = new MazeSolver(maze);
		Double expectedDistance = 1.0;
		MazeNode expectedNode = new MazeNode(0, 0, expectedDistance);
		
		mazeSolver.initializeAllNodes();
		mazeSolver.getUnvisitedNodes().put(expectedNode.getRow(), expectedNode.getColumn(), expectedNode);
		MazeNode node = new MazeNode(1, 0);
		
		// When:
		mazeSolver.calculateDistanceToAdjacentNodes(node);
		
		// Then:
		MultiKeyMap unvisitedNodes = mazeSolver.getUnvisitedNodes();
		MazeNode upNode = (MazeNode) unvisitedNodes.get(0, 0);
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should not update the upNode distance", expectedDistance, upNode.getDistance(), 0.0); 
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_updatesTheUpNodeDistanceIfItIsLessThanItsCurrentValue() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		map.put(1, 0, Maze.OPEN); // START
		map.put(2, 0, Maze.OPEN); // END
		Maze maze = new Maze(map, 3, 1);
		maze.setStartNode(new MazeNode(1, 0));
		maze.setEndNode(new MazeNode(2, 0));
		MazeSolver mazeSolver = new MazeSolver(maze);
		Double expectedDistance = 1.0;
		
		mazeSolver.initializeAllNodes();
		MazeNode node = new MazeNode(1, 0);
		
		// When:
		mazeSolver.calculateDistanceToAdjacentNodes(node);
		
		// Then:
		MultiKeyMap unvisitedNodes = mazeSolver.getUnvisitedNodes();
		MazeNode upNode = (MazeNode) unvisitedNodes.get(0, 0);
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should update the upNode distance", expectedDistance, upNode.getDistance(), 0.0); 
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_updatesTheUpNodePreviousNodePathIfItIsLessThanItsCurrentValue() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		map.put(1, 0, Maze.OPEN); // START
		map.put(2, 0, Maze.OPEN); // END
		Maze maze = new Maze(map, 3, 1);
		maze.setStartNode(new MazeNode(1, 0));
		maze.setEndNode(new MazeNode(2, 0));
		MazeSolver mazeSolver = new MazeSolver(maze);
		MazeNode expectedNextNode = new MazeNode(0, 0);
		
		mazeSolver.initializeAllNodes();
		MazeNode node = new MazeNode(1, 0);
		
		// When:
		mazeSolver.calculateDistanceToAdjacentNodes(node);
		
		// Then:
		MultiKeyMap previousNodePath = mazeSolver.getPreviousNodePath();
		MazeNode prevNode = (MazeNode) previousNodePath.get(expectedNextNode.getRow(), expectedNextNode.getColumn());
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should update the upNode prevNode row", node.getRow(), prevNode.getRow());
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should update the upNode prevNode column", node.getColumn(), prevNode.getColumn()); 
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_doesNotUpdateTheDownNodeDistanceIfItIsNotInTheListOfUnvisitedNodes() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // END
		map.put(1, 0, Maze.OPEN); // START
		map.put(2, 0, Maze.OPEN); 
		Maze maze = new Maze(map, 3, 1);
		maze.setStartNode(new MazeNode(1, 0));
		maze.setEndNode(new MazeNode(0, 0));
		MazeSolver mazeSolver = new MazeSolver(maze);
		
		mazeSolver.initializeAllNodes();
		mazeSolver.getUnvisitedNodes().removeMultiKey(2, 0);
		MazeNode node = new MazeNode(1, 0);
		
		// When:
		mazeSolver.calculateDistanceToAdjacentNodes(node);
		
		// Then:
		MultiKeyMap unvisitedNodes = mazeSolver.getUnvisitedNodes();
		MazeNode downNode = (MazeNode) unvisitedNodes.get(2, 0);
		Assert.assertNull("calculateDistanceToAdjacentNodes() should not update the downNode distance", downNode); 
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_doesNotUpdateTheDownNodeDistanceIfItIsMoreThanItsCurrentValue() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // END
		map.put(1, 0, Maze.OPEN); // START
		map.put(2, 0, Maze.OPEN); 
		Maze maze = new Maze(map, 3, 1);
		maze.setStartNode(new MazeNode(1, 0));
		maze.setEndNode(new MazeNode(0, 0));
		MazeSolver mazeSolver = new MazeSolver(maze);
		Double expectedDistance = 1.0;
		MazeNode expectedNode = new MazeNode(2, 0, expectedDistance);
		
		mazeSolver.initializeAllNodes();
		mazeSolver.getUnvisitedNodes().put(expectedNode.getRow(), expectedNode.getColumn(), expectedNode);
		MazeNode node = new MazeNode(1, 0);
		
		// When:
		mazeSolver.calculateDistanceToAdjacentNodes(node);
		
		// Then:
		MultiKeyMap unvisitedNodes = mazeSolver.getUnvisitedNodes();
		MazeNode downNode = (MazeNode) unvisitedNodes.get(2, 0);
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should not update the downNode distance", expectedDistance, downNode.getDistance(), 0.0); 
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_updatesTheDownNodeDistanceIfItIsLessThanItsCurrentValue() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // END
		map.put(1, 0, Maze.OPEN); // START
		map.put(2, 0, Maze.OPEN); 
		Maze maze = new Maze(map, 3, 1);
		maze.setStartNode(new MazeNode(1, 0));
		maze.setEndNode(new MazeNode(0, 0));
		MazeSolver mazeSolver = new MazeSolver(maze);
		Double expectedDistance = 1.0;
		
		mazeSolver.initializeAllNodes();
		MazeNode node = new MazeNode(1, 0);
		
		// When:
		mazeSolver.calculateDistanceToAdjacentNodes(node);
		
		// Then:
		MultiKeyMap unvisitedNodes = mazeSolver.getUnvisitedNodes();
		MazeNode downNode = (MazeNode) unvisitedNodes.get(2, 0);
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should update the downNode distance", expectedDistance, downNode.getDistance(), 0.0); 
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_updatesTheDownnNodePreviousNodePathIfItIsLessThanItsCurrentValue() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // END
		map.put(1, 0, Maze.OPEN); // START
		map.put(2, 0, Maze.OPEN);
		Maze maze = new Maze(map, 3, 1);
		maze.setStartNode(new MazeNode(1, 0));
		maze.setEndNode(new MazeNode(0, 0));
		MazeSolver mazeSolver = new MazeSolver(maze);
		MazeNode expectedNextNode = new MazeNode(2, 0);
		
		mazeSolver.initializeAllNodes();
		MazeNode node = new MazeNode(1, 0);
		
		// When:
		mazeSolver.calculateDistanceToAdjacentNodes(node);
		
		// Then:
		MultiKeyMap previousNodePath = mazeSolver.getPreviousNodePath();
		MazeNode prevNode = (MazeNode) previousNodePath.get(expectedNextNode.getRow(), expectedNextNode.getColumn());
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should update the downNode prevNode row", node.getRow(), prevNode.getRow());
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should update the downNode prevNode column", node.getColumn(), prevNode.getColumn()); 
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_doesNotUpdateTheLeftNodeDistanceIfItIsNotInTheListOfUnvisitedNodes() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); 
		map.put(0, 1, Maze.OPEN); // START
		map.put(0, 2, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 3);
		maze.setStartNode(new MazeNode(0, 1));
		maze.setEndNode(new MazeNode(0, 2));
		MazeSolver mazeSolver = new MazeSolver(maze);
		
		mazeSolver.initializeAllNodes();
		mazeSolver.getUnvisitedNodes().removeMultiKey(0, 0);
		MazeNode node = new MazeNode(0, 1);
		
		// When:
		mazeSolver.calculateDistanceToAdjacentNodes(node);
		
		// Then:
		MultiKeyMap unvisitedNodes = mazeSolver.getUnvisitedNodes();
		MazeNode leftNode = (MazeNode) unvisitedNodes.get(0, 0);
		Assert.assertNull("calculateDistanceToAdjacentNodes() should not update the leftNode distance", leftNode); 
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_doesNotUpdateTheLeftNodeDistanceIfItIsMoreThanItsCurrentValue() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); 
		map.put(0, 1, Maze.OPEN); // START
		map.put(0, 2, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 3);
		maze.setStartNode(new MazeNode(0, 1));
		maze.setEndNode(new MazeNode(0, 2));
		MazeSolver mazeSolver = new MazeSolver(maze);
		Double expectedDistance = 1.0;
		MazeNode expectedNode = new MazeNode(0, 0, expectedDistance);
		
		mazeSolver.initializeAllNodes();
		mazeSolver.getUnvisitedNodes().put(expectedNode.getRow(), expectedNode.getColumn(), expectedNode);
		MazeNode node = new MazeNode(0, 1);
		
		// When:
		mazeSolver.calculateDistanceToAdjacentNodes(node);
		
		// Then:
		MultiKeyMap unvisitedNodes = mazeSolver.getUnvisitedNodes();
		MazeNode leftNode = (MazeNode) unvisitedNodes.get(0, 0);
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should not update the leftNode distance", expectedDistance, leftNode.getDistance(), 0.0); 
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_updatesTheLeftNodeDistanceIfItIsLessThanItsCurrentValue() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); 
		map.put(0, 1, Maze.OPEN); // START
		map.put(0, 2, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 3);
		maze.setStartNode(new MazeNode(0, 1));
		maze.setEndNode(new MazeNode(0, 2));
		MazeSolver mazeSolver = new MazeSolver(maze);
		Double expectedDistance = 1.0;
		
		mazeSolver.initializeAllNodes();
		MazeNode node = new MazeNode(0, 1);
		
		// When:
		mazeSolver.calculateDistanceToAdjacentNodes(node);
		
		// Then:
		MultiKeyMap unvisitedNodes = mazeSolver.getUnvisitedNodes();
		MazeNode leftNode = (MazeNode) unvisitedNodes.get(0, 0);
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should update the leftNode distance", expectedDistance, leftNode.getDistance(), 0.0); 
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_updatesTheLeftnNodePreviousNodePathIfItIsLessThanItsCurrentValue() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); 
		map.put(0, 1, Maze.OPEN); // START
		map.put(0, 2, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 3);
		maze.setStartNode(new MazeNode(0, 1));
		maze.setEndNode(new MazeNode(0, 2));
		MazeSolver mazeSolver = new MazeSolver(maze);
		MazeNode expectedNextNode = new MazeNode(0, 0);
		
		mazeSolver.initializeAllNodes();
		MazeNode node = new MazeNode(0, 1);
		
		// When:
		mazeSolver.calculateDistanceToAdjacentNodes(node);
		
		// Then:
		MultiKeyMap previousNodePath = mazeSolver.getPreviousNodePath();
		MazeNode prevNode = (MazeNode) previousNodePath.get(expectedNextNode.getRow(), expectedNextNode.getColumn());
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should update the leftNode prevNode row", node.getRow(), prevNode.getRow());
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should update the leftNode prevNode column", node.getColumn(), prevNode.getColumn()); 
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_doesNotUpdateTheRightNodeDistanceIfItIsNotInTheListOfUnvisitedNodes() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // END
		map.put(0, 1, Maze.OPEN); // START
		map.put(0, 2, Maze.OPEN); 
		Maze maze = new Maze(map, 1, 3);
		maze.setStartNode(new MazeNode(0, 1));
		maze.setEndNode(new MazeNode(0, 0));
		MazeSolver mazeSolver = new MazeSolver(maze);
		
		mazeSolver.initializeAllNodes();
		mazeSolver.getUnvisitedNodes().removeMultiKey(0, 2);
		MazeNode node = new MazeNode(0, 1);
		
		// When:
		mazeSolver.calculateDistanceToAdjacentNodes(node);
		
		// Then:
		MultiKeyMap unvisitedNodes = mazeSolver.getUnvisitedNodes();
		MazeNode rightNode = (MazeNode) unvisitedNodes.get(0, 2);
		Assert.assertNull("calculateDistanceToAdjacentNodes() should not update the rightNode distance", rightNode); 
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_doesNotUpdateTheRightNodeDistanceIfItIsMoreThanItsCurrentValue() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // END
		map.put(0, 1, Maze.OPEN); // START
		map.put(0, 2, Maze.OPEN); 
		Maze maze = new Maze(map, 1, 3);
		maze.setStartNode(new MazeNode(0, 1));
		maze.setEndNode(new MazeNode(0, 0));
		MazeSolver mazeSolver = new MazeSolver(maze);
		Double expectedDistance = 1.0;
		MazeNode expectedNode = new MazeNode(0, 0, expectedDistance);
		
		mazeSolver.initializeAllNodes();
		mazeSolver.getUnvisitedNodes().put(expectedNode.getRow(), expectedNode.getColumn(), expectedNode);
		MazeNode node = new MazeNode(0, 1);
		
		// When:
		mazeSolver.calculateDistanceToAdjacentNodes(node);
		
		// Then:
		MultiKeyMap unvisitedNodes = mazeSolver.getUnvisitedNodes();
		MazeNode rightNode = (MazeNode) unvisitedNodes.get(0, 2);
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should not update the rightNode distance", expectedDistance, rightNode.getDistance(), 0.0); 
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_updatesTheRightNodeDistanceIfItIsLessThanItsCurrentValue() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // END
		map.put(0, 1, Maze.OPEN); // START
		map.put(0, 2, Maze.OPEN); 
		Maze maze = new Maze(map, 1, 3);
		maze.setStartNode(new MazeNode(0, 1));
		maze.setEndNode(new MazeNode(0, 0));
		MazeSolver mazeSolver = new MazeSolver(maze);
		Double expectedDistance = 1.0;
		
		mazeSolver.initializeAllNodes();
		MazeNode node = new MazeNode(0, 1);
		
		// When:
		mazeSolver.calculateDistanceToAdjacentNodes(node);
		
		// Then:
		MultiKeyMap unvisitedNodes = mazeSolver.getUnvisitedNodes();
		MazeNode rightNode = (MazeNode) unvisitedNodes.get(0, 2);
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should update the rightNode distance", expectedDistance, rightNode.getDistance(), 0.0); 
	}
	
	@Test
	public void calculateDistanceToAdjacentNodes_updatesTheRightnNodePreviousNodePathIfItIsLessThanItsCurrentValue() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // END
		map.put(0, 1, Maze.OPEN); // START
		map.put(0, 2, Maze.OPEN); 
		Maze maze = new Maze(map, 1, 3);
		maze.setStartNode(new MazeNode(0, 1));
		maze.setEndNode(new MazeNode(0, 0));
		MazeSolver mazeSolver = new MazeSolver(maze);
		MazeNode expectedNextNode = new MazeNode(0, 2);
		
		mazeSolver.initializeAllNodes();
		MazeNode node = new MazeNode(0, 1);
		
		// When:
		mazeSolver.calculateDistanceToAdjacentNodes(node);
		
		// Then:
		MultiKeyMap previousNodePath = mazeSolver.getPreviousNodePath();
		MazeNode prevNode = (MazeNode) previousNodePath.get(expectedNextNode.getRow(), expectedNextNode.getColumn());
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should update the rightNode prevNode row", node.getRow(), prevNode.getRow());
		Assert.assertEquals("calculateDistanceToAdjacentNodes() should update the rightNode prevNode column", node.getColumn(), prevNode.getColumn()); 
	}
	
	@Test
	public void getUnvisitedNodeWithShortestDistance_throwsARuntimeExceptionIfUnvisitedNodesIsNull() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolver = new MazeSolver(maze);
		
		// When:
		Exception actualException = null;
		try {
			mazeSolver.getUnvisitedNodeWithShortestDistance();
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("getUnvisitedNodeWithShortestDistance() should throw an Exception", actualException);
		Assert.assertEquals("getUnvisitedNodeWithShortestDistance() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void getUnvisitedNodeWithShortestDistance_returnsNullIfUnvisitedNodesIsEmpty() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolver = new MazeSolver(maze);
		
		mazeSolver.initializeAllNodes();
		mazeSolver.getUnvisitedNodes().clear();
		
		// When:
		MazeNode shortestDistanceNode = mazeSolver.getUnvisitedNodeWithShortestDistance();
		
		// Then:
		Assert.assertNull("getUnvisitedNodeWithShortestDistance() should return null", shortestDistanceNode);
	}
	
	@Test
	public void getUnvisitedNodeWithShortestDistance_returnsTheUnvisitedNodeWithTheShortestDistance() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolver = new MazeSolver(maze);
		Double expectedDistance = 0.0;
		
		mazeSolver.initializeAllNodes();
		
		// When:
		MazeNode node = mazeSolver.getUnvisitedNodeWithShortestDistance();
		
		// Then:
		Assert.assertNotNull("getUnvisitedNodeWithShortestDistance() should return node with the shortestDistance", node);
		Assert.assertEquals("getUnvisitedNodeWithShortestDistance() should return the shortestDistance", expectedDistance, node.getDistance(), 0.0);
	}
	
	@Test
	public void haveWeReachedTheEnd_throwsARuntimeExceptionIfTheCurrentNodeIsNull() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolver = new MazeSolver(maze);
		MazeNode node = null;
		
		// When:
		Exception actualException = null;
		try {
			mazeSolver.haveWeReachedTheEnd(node);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("haveWeReachedTheEnd() should throw an Exception", actualException);
		Assert.assertEquals("haveWeReachedTheEnd() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void haveWeReachedTheEnd_throwsARuntimeExceptionIfTheUnvisitedNodesIsNull() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolver = new MazeSolver(maze);
		MazeNode node = new MazeNode(0, 0);
		
		// When:
		Exception actualException = null;
		try {
			mazeSolver.haveWeReachedTheEnd(node);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("haveWeReachedTheEnd() should throw an Exception", actualException);
		Assert.assertEquals("haveWeReachedTheEnd() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void haveWeReachedTheEnd_returnsFalseIfCurrentNodeIsNotTheEndNode() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolver = new MazeSolver(maze);
		MazeNode node = new MazeNode(0, 0);
		
		mazeSolver.initializeAllNodes();
		
		// When:
		boolean haveWeReachedTheEnd = mazeSolver.haveWeReachedTheEnd(node);
		
		// Then:
		Assert.assertFalse("haveWeReachedTheEnd() should return false", haveWeReachedTheEnd);
	}
	
	@Test
	public void haveWeReachedTheEnd_returnsTrueIfCurrentNodeIsTheEndNode() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolver = new MazeSolver(maze);
		MazeNode node = new MazeNode(0, 1);
		
		mazeSolver.initializeAllNodes();
		
		// When:
		boolean haveWeReachedTheEnd = mazeSolver.haveWeReachedTheEnd(node);
		
		// Then:
		Assert.assertTrue("haveWeReachedTheEnd() should return true", haveWeReachedTheEnd);
	}
	
	@Test
	public void findShortestPathFromStartToEnd_callsMethodToInitializeAllNodes() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolverSpy = Mockito.spy(new MazeSolver(maze));
		
		// When:
		try {
			mazeSolverSpy.findShortestPathFromStartToEnd();
		} catch (Exception exception){
			// ignore exception
		}
		
		// Then:
		Mockito.verify(mazeSolverSpy).initializeAllNodes();
	}
	
	@Test
	public void findShortestPathFromStartToEnd_throwsARuntimeExceptionIfNextNodeIsNull() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolverSpy = Mockito.spy(new MazeSolver(maze));
		MazeNode expectedNode = null;
		
		mazeSolverSpy.initializeAllNodes();
		Mockito.when(mazeSolverSpy.getUnvisitedNodeWithShortestDistance()).thenReturn(expectedNode);
		
		// When:
		Exception actualException = null;
		try {
			mazeSolverSpy.findShortestPathFromStartToEnd();
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("findShortestPathFromStartToEnd() should throw an Exception", actualException);
		Assert.assertEquals("findShortestPathFromStartToEnd() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void findShortestPathFromStartToEnd_throwsARuntimeExceptionIfNextNodeDistanceIsInfinity() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolverSpy = Mockito.spy(new MazeSolver(maze));
		MazeNode expectedNode = new MazeNode(0, 0, Double.POSITIVE_INFINITY);
		
		mazeSolverSpy.initializeAllNodes();
		Mockito.when(mazeSolverSpy.getUnvisitedNodeWithShortestDistance()).thenReturn(expectedNode);
		
		// When:
		Exception actualException = null;
		try {
			mazeSolverSpy.findShortestPathFromStartToEnd();
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("findShortestPathFromStartToEnd() should throw an Exception", actualException);
		Assert.assertEquals("findShortestPathFromStartToEnd() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void findShortestPathFromStartToEnd_callsMethodToCalculateTheDistanceToAdjacentNodes() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolverSpy = Mockito.spy(new MazeSolver(maze));
		
		Mockito.doNothing().when(mazeSolverSpy).calculateDistanceToAdjacentNodes(Matchers.any(MazeNode.class));
		
		// When:
		try {
			mazeSolverSpy.findShortestPathFromStartToEnd();
		} catch (Exception exception){
			// ignore exception
		}
			
		// Then:
		Mockito.verify(mazeSolverSpy).calculateDistanceToAdjacentNodes(Matchers.any(MazeNode.class));
	}
	
	@Test
	public void findShortestPathFromStartToEnd_removesNextNode() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolver = new MazeSolver(maze);
		
		// When:
		mazeSolver.findShortestPathFromStartToEnd();
			
		// Then:
		MultiKeyMap unvisitedNodes = mazeSolver.getUnvisitedNodes();
		Assert.assertEquals("findShortestPathFromStartToEnd() should remove the next node", 1, unvisitedNodes.size());
	}
	
	@Test
	public void getSolution_throwsARuntimeExceptionIfPreviousNodePathIsNull() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolver = new MazeSolver(maze);

		// When:
		Exception actualException = null;
		try {
			mazeSolver.getSolution();
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("getSolution() should throw an Exception", actualException);
		Assert.assertEquals("getSolution() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void getSolution_throwsARuntimeExceptionIfUnvisitedNodesDoesNotContainTheEndNode() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolver = new MazeSolver(maze);
		
		mazeSolver.initializeAllNodes();
		mazeSolver.getUnvisitedNodes().clear();
		
		// When:
		Exception actualException = null;
		try {
			mazeSolver.getSolution();
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("getSolution() should throw an Exception", actualException);
		Assert.assertEquals("getSolution() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Ignore
	@Test
	public void getSolution_returnsTheSolutionPathString() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN); // START
		map.put(0, 1, Maze.OPEN); // END
		Maze maze = new Maze(map, 1, 2);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(0, 1));
		MazeSolver mazeSolver = new MazeSolver(maze);
		
		mazeSolver.findShortestPathFromStartToEnd();
		
		// When:
		System.out.print(mazeSolver.getSolution());
	}
}
