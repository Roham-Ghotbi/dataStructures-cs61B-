public class NBody {
	public static double readRadius(String file){
		In input = new In(file);
		input.readInt();
		return input.readDouble();
	}
	public static Planet[] readPlanets(String file){
		In input = new In(file);
		int SIZE = input.readInt();
		Planet[] planets = new Planet[SIZE];
		input.readDouble();	//just to skip the universe Radius
		
		for(int i = 0; i < SIZE; i++){
			Planet planet = new Planet(input.readDouble(), input.readDouble(),
									   input.readDouble(), input.readDouble(),
									   input.readDouble(), input.readString());
			planets[i] = planet;
		}

		return planets;
	}

	public static void main(String[]args){
		if (args.length == 0){
			System.out.println("Please input 2 numbers and a planets source");
			System.exit(0);
		}

		double time = 0;
		double T = Double.parseDouble(args[0]);	//inputs
		double dt = Double.parseDouble(args[1]);
		String file = args[2];
		String bckgrnd = "./images/starfield.jpg";
		double radius = readRadius(file);
		Planet[] planets = readPlanets(file);		//setting planets up
		int SIZE = planets.length;

		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0, 0, bckgrnd);				//scaling and drawing planets
		for(int i = 0; i < SIZE; i++){
			planets[i].draw();
		}
		StdAudio.play("audio/2001.mid");
		while(time < T){
			double[] xForces = new double[SIZE];
			double[] yForces = new double[SIZE];

			for(int i = 0; i < SIZE; i++){
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);	//calculating the forces
			}
			
			for(int i = 0; i < SIZE; i++){
				planets[i].update(dt, xForces[i], yForces[i]);
			}
			StdDraw.picture(0, 0, bckgrnd);
			for(int i = 0; i < SIZE; i++){
				planets[i].draw();
			}
			StdDraw.show(10);
			time += dt;
			
		}

		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		
		for (int i = 0; i < planets.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
   				planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);	
		}	
	}
}
