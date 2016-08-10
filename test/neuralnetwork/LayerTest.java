package neuralnetwork;


import junit.framework.TestCase;

public class LayerTest extends TestCase {

	public void testGetOutputs(){
		Layer layer = new Layer(3, 2);
		double[] inputs = {1, 2, 1};
		layer.inputs = inputs;
		layer.feedForward();
	}
	
	public void testConstructorOfLayerFL(){
		LayerFL firstLayer = new LayerFL(5, 2, 1d);
		
		System.out.println(firstLayer.nodes[0]);
		
		firstLayer.setIntegerWeithts();
		
		System.out.println(firstLayer.nodes[0]);
	}
	
	public void testSetAsLastTime(){
		LayerFL layer = new LayerFL(3, 2, 1);
		
		double[] inputs = {1, 2, 1};
		layer.inputs = inputs;
		
		layer.feedForward();
	}
	
	public void testCloneLayer(){
		LayerFL layer = new LayerFL(3, 2, 1);
		LayerFL clone = layer.cloneLayer();
		
		assertEquals(clone.inputs.length, 3);
		assertEquals(clone.nodes.length, 2);
		for (int i = 0; i < layer.nodes.length; i++){
			assertTrue(layer.nodes[i].equals(clone.nodes[i]));
		}
	}
}
