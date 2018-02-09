import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.*;

public class NBody {

	public static Planet[] inplanets;

	public static double xxVel;
	public static double yyVel

	 public static void main(String[] args) {

	 	double T = Double.parseDouble(args[0]);
	 	double dt = Double.parseDouble(args[1]);
	 	String filename = args[2];

	 	/* reads in the radius of universe and array of planets */
	 	Planet[] inplanets = readPlanets(filename);		
	 	double inradius = readRadius(filename);
	 	//read in inplanets && inradius; 

	 	//sets universe scale 
		StdDraw.setScale(-(inradius), inradius);

		/* Clears the drawing window. */
		StdDraw.clear();

		StdDraw.picture(0, 0, "images/starfield.jpg");

		/* Shows the drawing to the screen. */
		StdDraw.show();

		/* draws each planet in respective positions */
		for (int i = 0; i < inplanets.length; i += 1)
			inplanets[i].draw();


		StdDraw.enableDoubleBuffering();

		int timevariable = 0;
		double[] xForces = new double[inplanets.length];
		double[] yForces = new double[inplanets.length];
		while (timevariable != T) {
			
			for (int i = 0; i < inplanets.length; i +=1) {
			/* calculates net X, Y force for each planet */
					double xForce = inplanets[i].calcNetForceExertedByX(inplanets);
					double yForce = inplanets[i].calcNetForceExertedByY(inplanets);
					xForces[i] = xForce;
					yForces[i] = yForce;
				}

			for (int i = 0; i < inplanets.length; i += 1) {
				inplanets[i].update(dt, xForces[i], yForces[i]); 
			}

			StdDraw.clear();
			StdDraw.picture(0, 0, "images/starfield.jpg");

			for (int i = 0; i < inplanets.length; i += 1) {
				inplanets[i].draw();
			}

			StdDraw.show();
			StdDraw.pause(10);
			timevariable += dt;
		}

		StdOut.printf("%d\n", inplanets.length);
		StdOut.printf("%.2e\n", inradius);
		for (int i = 0; i < inplanets.length; i++) {
   	 		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  inplanets[i].xxPos, inplanets[i].yyPos, inplanets[i].xxVel,
                  inplanets[i].yyVel, inplanets[i].mass, inplanets[i].imgFileName);   

				}
		StdAudio.play("audio/2001.mid");

		
		}

	public static double readRadius(String filename) {
		/* returns the radius of the universe in specified universe*/
		In fileobject = new In(filename);
		double num_planets;
		double universe_radius;

		num_planets = Double.parseDouble(fileobject.readLine());

		universe_radius = Double.parseDouble(fileobject.readLine());

		return universe_radius; 
	}

	public static Planet[] readPlanets(String filename) {
		/* returns an array of the planets in specified universe */
		String planet;
		String str;
		double xxPos;
		double yyPos;
		double xxVel;
	    double yyVel;
	    double mass;
	    String imgFileName;

		In fileobject = new In(filename);

	    int num_planets = fileobject.readInt();

	    Planet[] planets = new Planet[num_planets];

	    fileobject.readDouble();

	    int index = 0;

			while (index < num_planets) {
				    xxPos = fileobject.readDouble();
					yyPos = fileobject.readDouble();
					xxVel = fileobject.readDouble();
					yyVel = fileobject.readDouble();
					mass = fileobject.readDouble();
					imgFileName = fileobject.readString();
					Planet new_planet = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
					planets[index] = new_planet;
					index += 1;
				}
			
		return planets;
	
	}
}
		

	