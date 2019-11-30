package jumpingalien.model;

/**
 * A generic class of Positions that extends Number, with a x and y value. 
 * 
 * @version 1.0
 * @author  Danaë Van de Velde & Thomas Vrolix
 */
public class Position<T extends Number> {

	 /** Initialize this new position with given x-position and y-position.
	 * @param 	x
	 * 		  	The x-coordinate of this new position.
	 * @param 	y
	 * 		  	The y-coordinate of this new position.
	 * @effect	The new x-coordinate of this new position is equal to x
	 * 		| 	setX(x)
	 * @effect 	The new y-coordinate of this new position is equal to x
	 *		| 	setX(x)	  
	 */	  
	public Position(T x, T y) throws IllegalArgumentException {		
		setX(x);
		setY(y);		
	}

	/**
	 * Return the x-coordinate of this position.
	 * 
	 * @return	The value of the x-coordinate.
	 * 		|	result == x
	 */
	public T getX() {
		return x;
	}
	
	/**
	 * Set the x-coordinate of this new position to the given x value.
	 * 
	 * @param 	x2
	 * 		  	The new x-coordinate for this position.
	 * @post	The x-coordinate of this new position is equal to the given x-value.
	 * 		|	new.getX() == x
	 *  	
	 */
	public void setX(T x) {
		this.x = x;
	}
	
	/**
	 * Variable registering the value of the x-coordinate of the position.
	 */
	private T x;
	
	
	/**
	 * Return the y-coordinate of this position.
	 * 
	 * @return	The value of the y-coordinate.
	 * 		|	result == y
	 */
	public T getY() {
		return y;
	}
	
	/**
	 * Set the x-coordinate of this new position to the given x value.
	 * 
	 * @param 	y
	 * 		  	The new y-coordinate for this position.
	 * @post	The y-coordinate of this new position is equal to the given y-value.
	 * 		|	new.getY() == y
	 *  	
	 */
	public void setY(T y) {
		this.y = y;
	}
	
	/**
	 * Variable registering the value of the y-coordinate of the position.
	 */
	private T y;

}