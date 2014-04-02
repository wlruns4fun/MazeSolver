import java.io.FileNotFoundException;

import org.apache.commons.collections4.map.MultiKeyMap;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

@SuppressWarnings({"rawtypes","unchecked"})
public class MazeTest {

	private static final String INVALID_FILE_MAZE = "FILE_DOES_NOT_EXIST";
	private static final String INVALID_CHAR_MAZE = "src/test/resources/maze_2x2_invalid_char.txt";
	private static final String NO_START_MAZE = "src/test/resources/maze_2x2_no_start.txt";
	private static final String NO_END_MAZE = "src/test/resources/maze_2x2_no_end.txt";
	private static final String SIMPLE_MAZE = "src/test/resources/maze_2x2_simple_maze.txt";
	private static final String TWO_START_MAZE = "src/test/resources/maze_1x2_two_starts.txt";
	private static final String TWO_END_MAZE = "src/test/resources/maze_1x2_two_ends.txt";
	
	@Test
	public void maze_map_numberOfRows_numberOfColumns_throwsARuntimeExceptionIfMapIsNull() {
		// Given:
		MultiKeyMap map = null;
		int numberOfRows = 1;
		int numberOfColumns = 1;
		
		// When:
		Exception actualException = null;
		try {
			new Maze(map, numberOfRows, numberOfColumns);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("Maze() should throw an Exception", actualException);
		Assert.assertEquals("Maze() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void maze_map_numberOfRows_numberOfColumns_throwsARuntimeExceptionIfNumberOfRowsIsLessThanOrEqualToZero() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		int numberOfRows = 0;
		int numberOfColumns = 1;
		
		// When:
		Exception actualException = null;
		try {
			new Maze(map, numberOfRows, numberOfColumns);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("Maze() should throw an Exception", actualException);
		Assert.assertEquals("Maze() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void maze_map_numberOfRows_numberOfColumns_throwsARuntimeExceptionIfNumberOfColumnsIsLessThanOrEqualToZero() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		int numberOfRows = 1;
		int numberOfColumns = 0;
		
		// When:
		Exception actualException = null;
		try {
			new Maze(map, numberOfRows, numberOfColumns);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("Maze() should throw an Exception", actualException);
		Assert.assertEquals("Maze() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void maze_map_numberOfRows_numberOfColumns_setsTheMapTheNumberOfRowsAndTheNumberOfColumns() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		int numberOfRows = 1;
		int numberOfColumns = 1;
		
		// When:
		Maze maze =	new Maze(map, numberOfRows, numberOfColumns);
		
		// Then:
		Assert.assertNotNull("Maze() should set the map", maze.getMap());
		Assert.assertEquals("Maze() should set the numberOfRows", numberOfRows, maze.getNumberOfRows());
		Assert.assertEquals("Maze() should set the numberOfColumns", numberOfRows, maze.getNumberOfRows());
	}
	
	@Test
	public void maze_filename_throwsARuntimeExceptionIfFilenameIsNull() {
		// Given:
		String filename = null;
		
		// When:
		Exception actualException = null;
		try {
			new Maze(filename);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("Maze() should throw an Exception", actualException);
		Assert.assertEquals("Maze() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void maze_filename_throwsAFileNotFoundExceptionIfUnableToOpenFile() {
		// Given:
		String filename = INVALID_FILE_MAZE;
		
		// When:
		Exception actualException = null;
		try {
			new Maze(filename);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("Maze() should throw an Exception", actualException);
		Assert.assertEquals("Maze() should throw a FileNotFoundException", FileNotFoundException.class, actualException.getClass());
	}
	
	@Test
	public void maze_filename_doesNotSetTheMapIfFileCharIsNotAValidMazeChar() throws Exception {
		// Given:
		String filename = INVALID_CHAR_MAZE;
		
		// When:
		Maze maze = new Maze(filename);
		
		// Then:
		MultiKeyMap map = maze.getMap();
		Object charObj = map.get(0, 1);
		Assert.assertNull("Maze() should not set the map char", charObj);
	}
	
	@Test
	public void maze_filename_setsTheMapIfFileCharIsTheOpenChar() throws Exception {
		// Given:
		String filename = SIMPLE_MAZE;
		
		// When:
		Maze maze = new Maze(filename);
		
		// Then:
		MultiKeyMap map = maze.getMap();
		Object charObj = map.get(0, 1);
		Assert.assertNotNull("Maze() should set the map char", charObj);
		Assert.assertEquals("Maze() should set the map char to open", Maze.OPEN, (char) charObj);
	}
	
	@Test
	public void maze_filename_setsTheMapIfFileCharIsTheWallChar() throws Exception {
		// Given:
		String filename = SIMPLE_MAZE;
		
		// When:
		Maze maze = new Maze(filename);
		
		// Then:
		MultiKeyMap map = maze.getMap();
		Object charObj = map.get(1, 0);
		Assert.assertNotNull("Maze() should set the map char", charObj);
		Assert.assertEquals("Maze() should set the map char to wall", Maze.WALL, (char) charObj);
	}
	
	@Test
	public void maze_filename_setsTheMapIfFileCharIsTheStartChar() throws Exception {
		// Given:
		String filename = SIMPLE_MAZE;
		
		// When:
		Maze maze = new Maze(filename);
		
		// Then:
		MultiKeyMap map = maze.getMap();
		Object charObj = map.get(0, 0);
		Assert.assertNotNull("Maze() should set the map char", charObj);
		Assert.assertEquals("Maze() should set the map char to start", Maze.START, (char) charObj);
	}
	
	@Test
	public void maze_filename_setsTheMapIfFileCharIsTheEndChar() throws Exception {
		// Given:
		String filename = SIMPLE_MAZE;
		
		// When:
		Maze maze = new Maze(filename);
		
		// Then:
		MultiKeyMap map = maze.getMap();
		Object charObj = map.get(1, 1);
		Assert.assertNotNull("Maze() should set the map char", charObj);
		Assert.assertEquals("Maze() should set the map char to end", Maze.END, (char) charObj);
	}
	
	@Test
	public void maze_filename_throwsARuntimeExceptionIfStartNodeIsNotDefined() {
		// Given:
		String filename = NO_START_MAZE;
		
		// When:
		Exception actualException = null;
		try {
			new Maze(filename);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("Maze() should throw an Exception", actualException);
		Assert.assertEquals("Maze() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void maze_filename_throwsARuntimeExceptionIfEndNodeIsNotDefined() {
		// Given:
		String filename = NO_END_MAZE;
		
		// When:
		Exception actualException = null;
		try {
			new Maze(filename);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("Maze() should throw an Exception", actualException);
		Assert.assertEquals("Maze() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void maze_filename_throwsARuntimeExceptionIfFileHasMultipleStartChars() {
		// Given:
		String filename = TWO_START_MAZE;
		
		// When:
		Exception actualException = null;
		try {
			new Maze(filename);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("Maze() should throw an Exception", actualException);
		Assert.assertEquals("Maze() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void maze_filename_throwsARuntimeExceptionIfFileHasMultipleEndChars() {
		// Given:
		String filename = TWO_END_MAZE;
		
		// When:
		Exception actualException = null;
		try {
			new Maze(filename);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("Maze() should throw an Exception", actualException);
		Assert.assertEquals("Maze() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void maze_filename_setsTheStartNodeIfFileCharIsTheStartChar() throws Exception {
		// Given:
		String filename = SIMPLE_MAZE;
		int expectedRow = 0;
		int expectedColumn = 0;
		
		// When:
		Maze maze = new Maze(filename);
		
		// Then:
		MazeNode startNode = maze.getStartNode();
		Assert.assertNotNull("Maze() should set the startNode", startNode);
		Assert.assertEquals("Maze() should set the startNode row", expectedRow, startNode.getRow());
		Assert.assertEquals("Maze() should set the startNode column", expectedColumn, startNode.getColumn());
	}
	
	@Test
	public void maze_filename_setsTheEndNodeIfFileCharIsTheEndChar() throws Exception {
		// Given:
		String filename = SIMPLE_MAZE;
		int expectedRow = 1;
		int expectedColumn = 1;
		
		// When:
		Maze maze = new Maze(filename);
		
		// Then:
		MazeNode endNode = maze.getEndNode();
		Assert.assertNotNull("Maze() should set the endNode", endNode);
		Assert.assertEquals("Maze() should set the endNode row", expectedRow, endNode.getRow());
		Assert.assertEquals("Maze() should set the endNode column", expectedColumn, endNode.getColumn());
	}
	
	@Test
	public void maze_filename_setsTheNumberOfRows() throws Exception {
		// Given:
		String filename = SIMPLE_MAZE;
		int expectedNumberOfRows = 2;
		
		// When:
		Maze maze = new Maze(filename);
		
		// Then:
		Assert.assertEquals("Maze() should set the numberOfRows", expectedNumberOfRows, maze.getNumberOfRows());
	}
	
	@Test
	public void maze_filename_setsTheNumberOfColumns() throws Exception {
		// Given:
		String filename = SIMPLE_MAZE;
		int expectedNumberOfColumns = 2;
		
		// When:
		Maze maze = new Maze(filename);
		
		// Then:
		Assert.assertEquals("Maze() should set the numberOfColumns", expectedNumberOfColumns, maze.getNumberOfColumns());
	}
	
	@Test
	public void isWithinMazeBoundaries_throwsARuntimeExceptionIfNodeIsNull() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = null;
		
		// When
		Exception actualException = null;
		try {
			maze.isWithinMazeBoundaries(node);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("isWithinMazeBoundaries() should throw an Exception", actualException);
		Assert.assertEquals("isWithinMazeBoundaries() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void isWithinMazeBoundaries_returnsFalseIfNodeIsLessThanNumRows() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = new MazeNode(-1, 0);
		
		// When
		boolean isWithinMaze = maze.isWithinMazeBoundaries(node);
		
		// Then:
		Assert.assertFalse("isWithinMazeBoundaries() should return false", isWithinMaze);
	}
	
	@Test
	public void isWithinMazeBoundaries_returnsFalseIfNodeIsGreaterThanNumberOfRows() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = new MazeNode(1, 0);
		
		// When
		boolean isWithinMaze = maze.isWithinMazeBoundaries(node);
		
		// Then:
		Assert.assertFalse("isWithinMazeBoundaries() should return false", isWithinMaze);
	}
	
	@Test
	public void isWithinMazeBoundaries_returnsFalseIfNodeIsLessThanNumberOfColumns() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = new MazeNode(0, -1);
		
		// When
		boolean isWithinMaze = maze.isWithinMazeBoundaries(node);
		
		// Then:
		Assert.assertFalse("isWithinMazeBoundaries() should return false", isWithinMaze);
	}
	
	@Test
	public void isWithinMazeBoundaries_returnsFalseIfNodeIsGreaterThanNumberOfColumns() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = new MazeNode(0, 1);
		
		// When
		boolean isWithinMaze = maze.isWithinMazeBoundaries(node);
		
		// Then:
		Assert.assertFalse("isWithinMazeBoundaries() should return false", isWithinMaze);
	}
	
	@Test
	public void isWithinMazeBoundaries_returnsTrueIfNodeIsWithinMazeBoundaries() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = new MazeNode(0, 0);
		
		// When
		boolean isWithinMaze = maze.isWithinMazeBoundaries(node);
		
		// Then:
		Assert.assertTrue("isWithinMazeBoundaries() should return true", isWithinMaze);
	}
	
	@Test
	public void setStartNode_throwsARuntimeExceptionIfNodeIsNull() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = null;
		
		// When
		Exception actualException = null;
		try {
			maze.setStartNode(node);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("setStartNode() should throw an Exception", actualException);
		Assert.assertEquals("setStartNode() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void setStartNode_throwsARuntimeExceptionIfNodeIsOutsideMazeBoundaries() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = new MazeNode(1, 0);
		
		// When
		Exception actualException = null;
		try {
			maze.setStartNode(node);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("setStartNode() should throw an Exception", actualException);
		Assert.assertEquals("setStartNode() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void setStartNode_throwsARuntimeExceptionIfAStartNodeIsAlreadyDefined() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = new MazeNode(0, 0);
		maze.setStartNode(node);
		
		// When
		Exception actualException = null;
		try {
			maze.setStartNode(node);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("setStartNode() should throw an Exception", actualException);
		Assert.assertEquals("setStartNode() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void setStartNode_setsTheStartNodeIfNodeIsNotNullAndWithinTheMazeBoundariesAndNotAlreadyDefined() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode expectedNode = new MazeNode(0, 0);
		
		// When
		maze.setStartNode(expectedNode);
		MazeNode actualNode = maze.getStartNode();
		
		// Then:
		Assert.assertNotNull("setStartNode() should set the startNode", actualNode);
		Assert.assertEquals("setStartNode() should set the startNode row", expectedNode.getRow(), actualNode.getRow());
		Assert.assertEquals("setStartNode() should set the startNode column", expectedNode.getColumn(), actualNode.getColumn());
	}
	
	@Test
	public void setStartNode_setsTheStartNodeDistanceToZero() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode expectedNode = new MazeNode(0, 0);
		
		// When
		maze.setStartNode(expectedNode);
		MazeNode actualNode = maze.getStartNode();
		
		// Then:
		Assert.assertNotNull("setStartNode() should set the startNode", actualNode);
		Assert.assertEquals("setStartNode() should set the startNode distance to 0", 0, actualNode.getDistance(), 0.0);
	}
	
	@Test
	public void setEndNode_throwsARuntimeExceptionIfNodeIsNull() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = null;
		
		// When
		Exception actualException = null;
		try {
			maze.setEndNode(node);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("setEndNode() should throw an Exception", actualException);
		Assert.assertEquals("setEndNode() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void setEndNode_throwsARuntimeExceptionIfNodeIsOutsideMazeBoundaries() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = new MazeNode(1, 0);
		
		// When
		Exception actualException = null;
		try {
			maze.setEndNode(node);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("setEndNode() should throw an Exception", actualException);
		Assert.assertEquals("setEndNode() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void setEndNode_throwsARuntimeExceptionIfAEndNodeIsAlreadyDefined() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = new MazeNode(0, 0);
		maze.setEndNode(node);
		
		// When
		Exception actualException = null;
		try {
			maze.setEndNode(node);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("setEndNode() should throw an Exception", actualException);
		Assert.assertEquals("setEndNode() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void setEndNode_setsTheEndNodeIfNodeIsNotNullAndWithinTheMazeBoundariesAndNotAlreadyDefined() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode expectedNode = new MazeNode(0, 0);
		
		// When
		maze.setEndNode(expectedNode);
		MazeNode actualNode = maze.getEndNode();
		
		// Then:
		Assert.assertNotNull("setEndNode() should set the endNode", actualNode);
		Assert.assertEquals("setEndNode() should set the endNode row", expectedNode.getRow(), actualNode.getRow());
		Assert.assertEquals("setEndNode() should set the endNode column", expectedNode.getColumn(), actualNode.getColumn());
	}
	
	@Test
	public void validateCurrentNode_throwsARuntimeExceptionIfTheCurrentNodeIsNull() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = null;
		
		// When:
		Exception actualException = null;
		try {
			maze.validateCurrentNode(node);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("validateCurrentNode() should throw an Exception", actualException);
		Assert.assertEquals("validateCurrentNode() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void validateCurrentNode_throwsARuntimeExceptionIfTheCurrentNodeIsOutsideTheMazeBoundaries() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = new MazeNode(1, 0);
		
		// When:
		Exception actualException = null;
		try {
			maze.validateCurrentNode(node);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("validateCurrentNode() should throw an Exception", actualException);
		Assert.assertEquals("validateCurrentNode() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void validateCurrentNode_throwsARuntimeExceptionIfTheCurrentNodeIsUndefined() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 1, Maze.OPEN);
		Maze maze = new Maze(map, 1, 2);
		MazeNode node = new MazeNode(0, 0);
		
		// When:
		Exception actualException = null;
		try {
			maze.validateCurrentNode(node);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("validateCurrentNode() should throw an Exception", actualException);
		Assert.assertEquals("validateCurrentNode() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void validateCurrentNode_throwsARuntimeExceptionIfTheCurrentNodeIsNotOpen() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.WALL);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = new MazeNode(0, 0);
		
		// When:
		Exception actualException = null;
		try {
			maze.validateCurrentNode(node);
		} catch (Exception exception) {
			actualException = exception;
		}
		
		// Then:
		Assert.assertNotNull("validateCurrentNode() should throw an Exception", actualException);
		Assert.assertEquals("validateCurrentNode() should throw a RuntimeException", RuntimeException.class, actualException.getClass());
	}
	
	@Test
	public void up_callsMethodToValidateCurrentNode() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze mazeSpy = Mockito.spy(new Maze(map, 1, 1));
		MazeNode node = new MazeNode(0, 0);
		
		// When:
		mazeSpy.up(node);
		
		// Then:
		Mockito.verify(mazeSpy).validateCurrentNode(node);
	}
	
	@Test
	public void up_returnsNullIfOneNodeUpFromTheCurrentNodeIsOutsideTheMazeBoundaries() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = new MazeNode(0, 0);
		
		// When:
		MazeNode upNode = maze.up(node);
		
		// Then:
		Assert.assertNull("up() should return null", upNode);
	}
	
	@Test
	public void up_returnsNullIfOneNodeUpFromTheCurrentNodeIsUndefined() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(1, 0, Maze.OPEN);
		Maze maze = new Maze(map, 2, 1);
		MazeNode node = new MazeNode(1, 0);
		
		// When:
		MazeNode upNode = maze.up(node);
		
		// Then:
		Assert.assertNull("up() should return null", upNode);
	}
	
	@Test
	public void up_returnsNullIfOneNodeUpFromTheCurrentNodeIsAWall() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.WALL);
		map.put(1, 0, Maze.OPEN);
		Maze maze = new Maze(map, 2, 1);
		MazeNode node = new MazeNode(1, 0);
		
		// When:
		MazeNode upNode = maze.up(node);
		
		// Then:
		Assert.assertNull("up() should return null", upNode);
	}
	
	@Test
	public void up_returnsAMazeNodeIfOneNodeUpFromTheCurrentNodeIsOpen() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		map.put(1, 0, Maze.OPEN);
		Maze maze = new Maze(map, 2, 1);
		MazeNode node = new MazeNode(1, 0);
		MazeNode expectedNode = new MazeNode(0, 0);
		
		// When:
		MazeNode upNode = maze.up(node);
		
		// Then:
		Assert.assertNotNull("up() should return a MazeNode", upNode);
		Assert.assertEquals("up() should return a node with the row decreased by 1", expectedNode.getRow(), upNode.getRow());
		Assert.assertEquals("up() should return a node with the same column", expectedNode.getColumn(), upNode.getColumn());
	}
	
	@Test
	public void down_callsMethodToValidateCurrentNode() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze mazeSpy = Mockito.spy(new Maze(map, 1, 1));
		MazeNode node = new MazeNode(0, 0);
		
		// When:
		mazeSpy.down(node);
		
		// Then:
		Mockito.verify(mazeSpy).validateCurrentNode(node);
	}
	
	@Test
	public void down_returnsNullIfOneNodeDownFromTheCurrentNodeIsOutsideTheMazeBoundaries() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = new MazeNode(0, 0);
		
		// When:
		MazeNode downNode = maze.down(node);
		
		// Then:
		Assert.assertNull("down() should return null", downNode);
	}
	
	@Test
	public void down_returnsNullIfOneNodeDownFromTheCurrentNodeIsUndefined() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 2, 1);
		MazeNode node = new MazeNode(0, 0);
		
		// When:
		MazeNode downNode = maze.down(node);
		
		// Then:
		Assert.assertNull("down() should return null", downNode);
	}
	
	@Test
	public void down_returnsNullIfOneNodeDownFromTheCurrentNodeIsAWall() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		map.put(1, 0, Maze.WALL);
		Maze maze = new Maze(map, 2, 1);
		MazeNode node = new MazeNode(0, 0);
		
		// When:
		MazeNode downNode = maze.down(node);
		
		// Then:
		Assert.assertNull("down() should return null", downNode);
	}
	
	@Test
	public void down_returnsAMazeNodeIfOneNodeDownFromTheCurrentNodeIsOpen() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		map.put(1, 0, Maze.OPEN);
		Maze maze = new Maze(map, 2, 1);
		MazeNode node = new MazeNode(0, 0);
		MazeNode expectedNode = new MazeNode(1, 0);
		
		// When:
		MazeNode downNode = maze.down(node);
		
		// Then:
		Assert.assertNotNull("down() should return a MazeNode", downNode);
		Assert.assertEquals("down() should return a node with the row increased by 1", expectedNode.getRow(), downNode.getRow());
		Assert.assertEquals("down() should return a node with the same column", expectedNode.getColumn(), downNode.getColumn());
	}
	
	@Test
	public void left_callsMethodToValidateCurrentNode() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze mazeSpy = Mockito.spy(new Maze(map, 1, 1));
		MazeNode node = new MazeNode(0, 0);
		
		// When:
		mazeSpy.left(node);
		
		// Then:
		Mockito.verify(mazeSpy).validateCurrentNode(node);
	}
	
	@Test
	public void left_returnsNullIfOneNodeLeftFromTheCurrentNodeIsOutsideTheMazeBoundaries() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = new MazeNode(0, 0);
		
		// When:
		MazeNode leftNode = maze.left(node);
		
		// Then:
		Assert.assertNull("left() should return null", leftNode);
	}
	
	@Test
	public void left_returnsNullIfOneNodeLeftFromTheCurrentNodeIsUndefined() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 1, Maze.OPEN);
		Maze maze = new Maze(map, 1, 2);
		MazeNode node = new MazeNode(0, 1);
		
		// When:
		MazeNode leftNode = maze.left(node);
		
		// Then:
		Assert.assertNull("left() should return null", leftNode);
	}
	
	@Test
	public void left_returnsNullIfOneNodeLeftFromTheCurrentNodeIsAWall() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.WALL);
		map.put(0, 1, Maze.OPEN);
		Maze maze = new Maze(map, 1, 2);
		MazeNode node = new MazeNode(0, 1);
		
		// When:
		MazeNode leftNode = maze.left(node);
		
		// Then:
		Assert.assertNull("left() should return null", leftNode);
	}
	
	@Test
	public void left_returnsAMazeNodeIfOneNodeLeftFromTheCurrentNodeIsOpen() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		map.put(0, 1, Maze.OPEN);
		Maze maze = new Maze(map, 1, 2);
		MazeNode node = new MazeNode(0, 1);
		MazeNode expectedNode = new MazeNode(0, 0);
		
		// When:
		MazeNode leftNode = maze.left(node);
		
		// Then:
		Assert.assertNotNull("left() should return a MazeNode", leftNode);
		Assert.assertEquals("left() should return a node with the same row", expectedNode.getRow(), leftNode.getRow());
		Assert.assertEquals("left() should return a node with the column decreased by 1", expectedNode.getColumn(), leftNode.getColumn());
	}
	
	@Test
	public void right_callsMethodToValidateCurrentNode() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze mazeSpy = Mockito.spy(new Maze(map, 1, 1));
		MazeNode node = new MazeNode(0, 0);
		
		// When:
		mazeSpy.right(node);
		
		// Then:
		Mockito.verify(mazeSpy).validateCurrentNode(node);
	}
	
	@Test
	public void right_returnsNullIfOneNodeRightFromTheCurrentNodeIsOutsideTheMazeBoundaries() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 1);
		MazeNode node = new MazeNode(0, 0);
		
		// When:
		MazeNode rightNode = maze.right(node);
		
		// Then:
		Assert.assertNull("right() should return null", rightNode);
	}
	
	@Test
	public void right_returnsNullIfOneNodeRightFromTheCurrentNodeIsUndefined() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		Maze maze = new Maze(map, 1, 2);
		MazeNode node = new MazeNode(0, 0);
		
		// When:
		MazeNode rightNode = maze.right(node);
		
		// Then:
		Assert.assertNull("right() should return null", rightNode);
	}
	
	@Test
	public void right_returnsNullIfOneNodeRightFromTheCurrentNodeIsAWall() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		map.put(0, 1, Maze.WALL);
		Maze maze = new Maze(map, 1, 2);
		MazeNode node = new MazeNode(0, 0);
		
		// When:
		MazeNode rightNode = maze.right(node);
		
		// Then:
		Assert.assertNull("right() should return null", rightNode);
	}
	
	@Test
	public void right_returnsAMazeNodeIfOneNodeRightFromTheCurrentNodeIsOpen() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		map.put(0, 1, Maze.OPEN);
		Maze maze = new Maze(map, 1, 2);
		MazeNode node = new MazeNode(0, 0);
		MazeNode expectedNode = new MazeNode(0, 1);
		
		// When:
		MazeNode rightNode = maze.right(node);
		
		// Then:
		Assert.assertNotNull("right() should return a MazeNode", rightNode);
		Assert.assertEquals("right() should return a node with the same row", expectedNode.getRow(), rightNode.getRow());
		Assert.assertEquals("right() should return a node with the column increased by 1", expectedNode.getColumn(), rightNode.getColumn());
	}
	
	@Ignore
	@Test 
	public void toString_returnsTheContentsOfTheMazeAsAString() {
		// Given:
		MultiKeyMap map = new MultiKeyMap();
		map.put(0, 0, Maze.OPEN);
		map.put(0, 1, Maze.WALL);
		map.put(0, 2, Maze.WALL);
		map.put(1, 0, Maze.OPEN);
		//map.put(1, 1, Maze.OPEN); // UNDEFINED
		map.put(1, 2, Maze.WALL);
		map.put(2, 0, Maze.WALL);
		map.put(2, 1, Maze.OPEN);
		map.put(2, 2, Maze.WALL);
		Maze maze = new Maze(map, 3, 3);
		maze.setStartNode(new MazeNode(0, 0));
		maze.setEndNode(new MazeNode(2, 2));
		
		// When:
		System.out.print(maze.toString());
	}
}
