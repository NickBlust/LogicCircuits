package logicCircuits;
//import java.lang.Number;

/**
 * I might use this somewhere, if not, will delete
 * @author Philipp A. Grzywaczyk
 *
 */
public class Tuple {
	private int num_1;
	private int num_2;
	
	public Tuple(int num_1t, int num_2t) {
		this.num_1 = num_1t;
		this.num_2 = num_2t;
	}
	
	public int get_num_1() {
		return this.num_1;
	}
	
	public int get_num_2() {
		return this.num_2;
	}
	
	public void set_num_1(int n) {
		this.num_1 = n;
	}
	 
	public void set_num_2(int n) {
		this.num_2 = n;
	}
	
//	public int sign_num_1() {
//		int s;
//		if (num_1.doubleValue() > 0) {
//			s = 1; 
//		} else if (num_1.doubleValue() < 0) {
//			s = -1;
//		} else s = 0;
//		return s;
//	}
//	
//	public int sign_num_2() {
//		int s;
//		if (num_2.doubleValue() > 0) {
//			s = 1; 
//		} else if (num_2.doubleValue() < 0) {
//			s = -1;
//		} else s = 0;
//		return s;
//	}
}