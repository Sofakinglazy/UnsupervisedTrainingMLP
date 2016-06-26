package neuralnetwork;

import junit.framework.TestCase;

public class LayerTest extends TestCase {

	public void testGetOutputs(){
		Layer layer = new Layer(3, 2);
		
		double[] inputs = {1, 2, 1};
		
		layer.inputs = inputs;
		
		System.out.println(layer);
		
		layer.feedForward();
		
		System.out.println(layer);
	}
}
