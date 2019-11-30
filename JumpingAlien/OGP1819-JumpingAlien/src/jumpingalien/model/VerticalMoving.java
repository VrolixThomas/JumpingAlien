package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * An interface of vertical movements.
 * 
 * @version  version 1.0
 * @author	 Danaë Van de Velde & Thomas Vrolix
 * 
 */

public interface VerticalMoving {
	
	/**
	 * Return the vertical velocity of this game object. The velocity is expressed in meters per second.
	 */
	@Basic
	public double getVerticalVelocity();
	
	/**
	 * Set the vertical velocity of this game object to the value.
	 */
	public void setVerticalVelocity(double value);
	
	/**
	 * Return the vertical velocity of this GameObject. The velocity is expressed in meters per second.
	 */
	@Basic
	public double getVerticalAcceleration();
	
	/**
	 * Set the vertical acceleration of this game object to the value.
	 *  	
	 */
	public void setVerticalAcceleration(double value);
	
	/**
	 * Return the acceleration of this GameObject. The acceleration is expressed in meters per second^2.
	 */
	public double[] getAcceleration();
	
	
	/**
	 * Check whether this game object is vertically moving.
	 */
	public boolean isVerticalMoving();

}
