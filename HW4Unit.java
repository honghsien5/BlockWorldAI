package hw4;

import static org.junit.Assert.*;

import org.junit.Test;

public class HW4Unit {

	@Test
	public void test() {
		BlockWorld world = new BlockWorld();
		world.createBlock("A");
		world.createBlock("B");
		world.createBlock("C");
		world.createBlock("D");
		world.stack("D","C");
		System.out.println(world.printState());
		
	}

}
