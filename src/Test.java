
public class Test {

	
	static String s;
	 A name;
	Test(){
		System.out.println("inside cons Test()");
		name=new A();
	}
	public static void main(String[] args) {
		new Test();
		System.out.println("s=="+Test.s);
	}
	
	class A{
		public A() {
			System.out.println("inside cons A()");
			Test.s="changed";
		}
	}
}
