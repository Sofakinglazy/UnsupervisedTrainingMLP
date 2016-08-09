package neuralnetwork;

import java.util.Calendar;

import junit.framework.TestCase;

public class NodeTest extends TestCase {

	public void testGenerateSmallRandomNumber(){
		Node node = new Node(3);
		System.out.println(node);
	}
	
	public void testSetIntegerWeights(){
		Node node = new Node(10, 1d);
		node.setIntegerWeithts();
		System.out.println(node);
	}
	
	public void testFormatLogFileName(){
		System.out.println(Calendar.getInstance().getTime());
	}
	
	public void testSetAsLastTime(){
		Node node = new Node(3);
		assertEquals(null, node.lastTime);
		node.saveAsLastTime();
	}
}
