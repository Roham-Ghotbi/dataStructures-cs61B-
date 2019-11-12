public class TestPlanets{
	public static void main(String[]args){
		Planet p1 = new Planet(4,23,5,7,4000000,null);
		Planet p2 = new Planet(5,3,15,55,9000000,null);
		System.out.println("the Force Exerted by planet 1 to 2 is: " + p2.calcForceExertedBy(p1));
	}
}