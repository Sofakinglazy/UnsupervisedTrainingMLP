package neuralnetwork;

import junit.framework.TestCase;

public class NodeTest extends TestCase {

	public void testGenerateSmallRandomNumber(){
		Node node = new Node(3);
		System.out.println(node);
	}
	
	public void testSetIntegerWeights(){
		Node node = new Node(10);
		double bias = 1d;
		node.setIntegerWeithts();
		node.setFixedBias(bias);
		System.out.println(node);
	}
}
