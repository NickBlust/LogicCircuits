/**
 * 
 */
package app;

import gates.*;

/**
 * @author Dominik Baumann, Philipp Grzywaczyk, Cameron McGregor
 *
 */
public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello there!");
		testWithSAMEgates();
		testWithTRUEFALSEgates();
	}

	/**
	 * How to use the classes working with the SAMEgate class.
	 */
	private static void testWithSAMEgates() {
		// use SAMEgates to specify input
		Gate s1 = new SAMEgate(true);
		Gate s2 = new SAMEgate(false);
		Gate s3 = new SAMEgate(true);
		Gate s4 = new SAMEgate(false);
		
		// assign gates
		Gate g1 = new ANDgate();
		g1.setInput(s1, 0);
		g1.setInput(s2, 1);
		Gate g2 = new ORgate();
		g2.setInput(s3, 0);
		g2.setInput(s4, 1);
		Gate g3 = new XORgate();
		g3.setInput(g1, 0);
		g3.setInput(g2, 1);
		// get value of final gate
		System.out.println("With SAMEgates: " + g3.output());
	}
	
	/**
	 * How to use the classes working with the SAMEgate class.
	 */
	private static void testWithTRUEFALSEgates() {
		// use SAMEgates to specify input
		Gate s1 = new TRUEgate();
		Gate s2 = new FALSEgate();
		Gate s3 = new TRUEgate();
		Gate s4 = new FALSEgate();
		
		// assign gates
		Gate g1 = new ANDgate();
		g1.setInput(s1, 0);
		g1.setInput(s2, 1);
		Gate g2 = new ORgate();
		g2.setInput(s3, 0);
		g2.setInput(s4, 1);
		Gate g3 = new XORgate();
		g3.setInput(g1, 0);
		g3.setInput(g2, 1);
		// get value of final gate
		System.out.println("With TRUEgates and FALSEgates: " + g3.output());
	}

}
