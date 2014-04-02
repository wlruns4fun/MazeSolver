import org.junit.Assert;
import org.junit.Test;

public class MazeNodeTest {
	
	@Test
	public void up_returnsTheNodeOneUpFromTheCurrentNode() {
		// Given:
		MazeNode node = new MazeNode(1, 0);
		MazeNode expectedNode = new MazeNode(0, 0);
		
		// When:
		MazeNode actualNode = node.up();
		
		// Then:
		Assert.assertNotNull("up() should not return null", actualNode);
		Assert.assertEquals("up() should decrease the row by 1", expectedNode.getRow(), actualNode.getRow());
		Assert.assertEquals("up() should keep the same col", expectedNode.getColumn(), actualNode.getColumn());
	}
	
	@Test
	public void down_returnsTheNodeOneDownFromTheCurrentNode() {
		// Given:
		MazeNode node = new MazeNode(0, 0);
		MazeNode expectedNode = new MazeNode(1, 0);
		
		// When:
		MazeNode actualNode = node.down();
		
		// Then:
		Assert.assertNotNull("down() should not return null", actualNode);
		Assert.assertEquals("down() should increase the row by 1", expectedNode.getRow(), actualNode.getRow());
		Assert.assertEquals("down() should keep the same col", expectedNode.getColumn(), actualNode.getColumn());
	}
	
	@Test
	public void left_returnsTheNodeOneLeftFromTheCurrentNode() {
		// Given:
		MazeNode node = new MazeNode(0, 1);
		MazeNode expectedNode = new MazeNode(0, 0);
		
		// When:
		MazeNode actualNode = node.left();
		
		// Then:
		Assert.assertNotNull("left() should not return null", actualNode);
		Assert.assertEquals("left() should keep the same row", expectedNode.getRow(), actualNode.getRow());
		Assert.assertEquals("left() should decrease the col by 1", expectedNode.getColumn(), actualNode.getColumn());
	}
	
	@Test
	public void right_returnsTheNodeOneRightFromTheCurrentNode() {
		// Given:
		MazeNode node = new MazeNode(0, 0);
		MazeNode expectedNode = new MazeNode(0, 1);
		
		// When:
		MazeNode actualNode = node.right();
		
		// Then:
		Assert.assertNotNull("right() should not return null", actualNode);
		Assert.assertEquals("right() should keep the same row", expectedNode.getRow(), actualNode.getRow());
		Assert.assertEquals("right() should increase the col by 1", expectedNode.getColumn(), actualNode.getColumn());
	}
}
