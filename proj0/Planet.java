import java.lang.Math;

public class Planet{

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Planet(double xxPos, double yyPos, double xxVel,double yyVel, double mass, String imgFileName){
		this.xxPos = xxPos;
		this.yyPos = yyPos;
		this.xxVel = xxVel;
		this.yyVel = yyVel;
		this.mass  = mass;
		this.imgFileName = imgFileName;
	}
	public Planet(Planet p){
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass  = p.mass;
		this.imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p){
		double xVal = this.xxPos - p.xxPos;			//for more legibility
		double yVal = this.yyPos - p.yyPos;
		return java.lang.Math.sqrt (xVal * xVal + yVal * yVal);
	}
	public double calcForceExertedBy(Planet p){
		double dist = calcDistance(p);				//for more legibility
		double g = 6.67 * java.lang.Math.pow(10, -11);
		return g * this.mass * p.mass /(dist * dist);
	}
	public double calcForceExertedByX(Planet p){
		double force = calcForceExertedBy(p);
		double dist = calcDistance(p);				//for more legibility
		return force * (p.xxPos - this.xxPos) / dist;
	}
	public double calcForceExertedByY(Planet p){
		double force = calcForceExertedBy(p);
		double dist = calcDistance(p);				//for more legibility
		return force * (p.yyPos - this.yyPos) / dist;
	}
	public double calcNetForceExertedByX(Planet[] planets){
		double netFX = 0;

		for (int i = 0; i < planets.length; i++){
			if(!this.equals(planets[i])){
				netFX += calcForceExertedByX(planets[i]);
			}
		}
		return netFX;
	}
	public double calcNetForceExertedByY(Planet[] planets){
		double netFY = 0;

		for (int i = 0; i < planets.length; i++){
			if(!this.equals(planets[i])){
				netFY += calcForceExertedByY(planets[i]);
			}
		}
		return netFY;
	}
	public void update(double dt,double fX,double fY){
		
		this.xxVel += fX / this.mass * dt;
		this.yyVel += fY / this.mass * dt;
		this.xxPos += this.xxVel * dt;
		this.yyPos += this.yyVel * dt;
	}
	public void draw(){
		StdDraw.picture(this.xxPos, this.yyPos, this.imgFileName);
	}

}