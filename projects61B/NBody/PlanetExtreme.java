public class PlanetExtreme */ignore this file */ {

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private static final double gravitational_C = 6.67e-11;
	private double change_x;
	private double change_y;

	public void Planet(double xPos, double yPos, double xVel,
              double yVel, double m, String img) {

			 xxPos = xPos;
			 yyPos = yPos;
			 xxVel = xVel;
			 yyVel = yVel;
			 mass = m;
			 imgFileName = img;
	}

	public Planet(Planet p) {
		/* initialize instance of the Planet class */
			xxPos = p.xxPos;
			yyPos = p.yyPos;
			xxVel = p.xxVel;
			yyVel = p.yyVel;
			mass = p.mass;
			imgFileName = p.imgFileName;
		Planet new_planet = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
	}

	public double calcDistance(Planet supplied) {
		/* calculates radial distance between two planets */
		double radial_distance;
		double change_x;
		double change_y;
			change_x = supplied.xxPos - this.xxPos;
			change_y = supplied.yyPos - this.yyPos;
			radial_distance = Math.sqrt((change_x*change_x) + (change_y*change_y));
				return radial_distance;
	}

	public double calcForceExertedBy(Planet supplied) {
		/* calculates force exerted by two planets on each other */
		double force;
			force = (gravitational_C*(supplied.mass)*(this.mass)) / (Math.pow((this.calcDistance(supplied)), 2));
				return force;
	}

	public double calcForceExertedByX(Planet supplied) {
		/* calculate the force exerted in x direction */
		double forceX;
		double change_x;
		double change_y;
			change_x = supplied.xxPos - this.xxPos;
			change_y = supplied.yyPos - this.yyPos;
			forceX = this.calcForceExertedBy(supplied)*change_x / this.calcDistance(supplied);
				return forceX;
	}

	public double calcForceExertedByY(Planet supplied) {
		/* calculate the force exerted in x direction */
		double forceY;
		double change_x;
		double change_y;
			change_x = supplied.xxPos - this.xxPos;
			change_y = supplied.yyPos - this.yyPos;
			forceY = this.calcForceExertedBy(supplied)*change_y / this.calcDistance(supplied);
				return forceY;
	}
	

	public double calcNetForceExertedByX(Planet[] args) {
		/* calculate the net force in the x direction of array of planets on specified planet */
		double netforceX = 0;
		for (int i=0; i < args.length; i +=1) {
			if (this.equals(args[i]))
				continue;
			netforceX -= args[i].calcForceExertedByX(this);
			}
		
			return netforceX;
	}

	public double calcNetForceExertedByY(Planet[] args) {
		/* calculate the net force in the x direction of array of planets on specified planet */
		double netforceY = 0;

		for (int i=0; i < args.length; i +=1) {
			if (this.equals(args[i]))
				continue;
			netforceY -= args[i].calcForceExertedByY(this);
		}
		
			return netforceY;
	}

	public void update(double dt, double fX, double fY) {
		/* calculate the new acceleration, velocity components and position components */
		double accelerationx;
		double accelerationy;
		double velocityx;
		double velocityy;
		double positionx;
		double positiony;

			accelerationx = (fX / this.mass);
			accelerationy =  (fY / this.mass);
			velocityx =  (this.xxVel + dt*accelerationx);
			velocityy =  (this.yyVel + dt*accelerationy);
			positionx = (this.xxPos + dt*velocityx); 
			positiony = (this.yyPos + dt*velocityy);

		this.xxVel = velocityx;
		this.yyVel = velocityy;
		this.xxPos = positionx;
		this.yyPos = positiony;
	}


	public void draw() {
	    	/* method used to draw each planet */
			StdDraw.picture(this.xxPos, this.yyPos, "images/" + imgFileName);
	}





}
/* there should not be a main method 
or static methods in this class */


