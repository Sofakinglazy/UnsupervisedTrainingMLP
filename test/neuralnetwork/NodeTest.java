package neuralnetwork;

import junit.framework.TestCase;

public class NodeTest extends TestCase {

	public void testGenerateSmallRandomNumber(){
		Node node = new Node(3);
		System.out.println(node);
	}
}
