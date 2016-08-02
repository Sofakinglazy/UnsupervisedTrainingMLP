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
	
	public void testConstructorOfLayerFL(){
		LayerFL firstLayer = new LayerFL(5, 2, 1d);
		
		System.out.println(firstLayer.nodes[0]);
		
		firstLayer.setIntegerWeithts();
		
		System.out.println(firstLayer.nodes[0]);
	}
}
