package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * An interface of horizontal movements.
 * 
 * @version  version 1.0
 * @author	 Danaë Van de Velde & Thomas Vrolix
 * 
 */

public interface HorizontalMoving {
	
	/**
	 * Return the horizontal velocity of this GameObject. The velocity is expressed in meters per second.
	 */
	@Basic
	public double getHorizontalVelocity();
	
	/**
	 * Set the horizontal velocity of this game object to the given value.
	 */
	@Raw
	public void setHorizontalVelocity(double value);
	
	
	/**
	* Set the minimum horizontal velocity of this game object.
	*/
	@Raw
	public void setMinHorizontalVelocity(double value);

	/**
	* Set the maximum horizontal velocity of this game object.
	*/
	@Raw
	public void setMaxHorizontalVelocity(double value);
	
	
	/**
	 * Return the horizontal velocity of this GameObject. The velocity is expressed in meters per second.
	 */
	@Basic
	public double getHorizontalAcceleration();
	
	/**
	 * Set the horizontal acceleration of this game object to the value.
	 */
	@Raw
	public void setHorizontalAcceleration(double value);

	/**
	 * Return the acceleration of this GameObject. The acceleration is expressed in meters per second^2.
	 */
	public double[] getAcceleration();

	
	
}
