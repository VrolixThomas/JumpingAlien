package jumpingalien.model;

import java.util.Arrays;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

/**
 * A class of game objects with a position, amount of hitpoints and sprites such as the player character Mazub, 
 * enemy characters and collectable items. 
 * 
 * @invar  Each GameObject must have a valid position.
 *       | isValidPosition(Position position)
 * 
 * @version 3.0
 * @author  Danaë Van de Velde & Thomas Vrolix
 */

@SuppressWarnings("all")
	public class GameObject {

	
	/**
	 * Initialize this game object with given x-coordinate, y-coordinate in meters and sprites.
	 *
	 * @param  	x
	 *          The x-coordinate of this new game object in pixels.
	 * @param   y
	 *          The y-coordinate of this new game object in pixels.
	 * @param   sprites
	 *          The sprites for this new game object.
	 * @effect	setPixelPosition(new Position<Integer>(x, y))
	 * @effect	setPosition(new Position<Double>(((double) x)/100, ((double) y)/100))
	 * @effect	setAllSprites(sprites)
	 * @throws 	IllegalArgumentException !isValidCoordinate(((double) x)/100) || !isValidCoordinate(((double) y)/100)
	 */
	protected GameObject(int x, int y, Sprite... sprites) {
		double doubleX =  ((double) x)/100;
		double doubleY =  ((double) y)/100;
		if (!isValidCoordinate(doubleX) || !isValidCoordinate(doubleY))
			throw new IllegalArgumentException("No valid coordinate.");
		
		setPixelPosition(new Position<Integer>(x, y));
		setPosition(new Position<Double>(doubleX, doubleY));
		setAllSprites(sprites);
	}
	
	/**
	 * Variable registering the last direction this game object was moving in.
	 */
	protected String lastDirection;

	/**
	 * Variable registering whether this game object is horizontally moving.
	 */
	public boolean isHorizontalMoving = false;

	/**
	 * Variable registering whether this game object is vertically moving.
	 */
	public boolean isVerticalMoving;

	/**
	 * Variable registering whether this game object is ducking.
	 */
	public boolean isDucking = false;

	/**
	 * Variable registering whether this game object is jumping.
	 */
	public boolean isJumping = false;

	/**
	 * Variable registering whether this game object is falling.
	 */
	protected boolean isFalling;

	/**
	 * Variable registering whether this game object is on solid ground.
	 */
	protected boolean isOnGround;

	/**
	 * Variable registering whether this game object is dead.
	 */
	public boolean isDead = false;
	
	
	/**
	 * Variable registering the time this game object needs to get one pixel further.
	 */
	double t;
	
	/**
	 * Variable registering the time this game object is moving left continuously.
	 */
	protected double timeMovingLeft;

	/**
	 * Variable registering the time this game object is moving right continuously.
	 */
	protected double timeMovingRight;

	/**
	 * Variable registering the time this game object has not moved.
	 */
	protected double timeNotMoving = Double.POSITIVE_INFINITY;
	
	/**
	 * Variable registering the time this game object is in magma continuously.
	 */
	protected double timeInMagma;

	/**
	 * Variable registering the time this game object is in water continuously.
	 */
	public double timeInWater;

	/**
	 * Variable registering the time this game object is in gas continuously.
	 */
	public double timeInGas;
	
	/**
	 * Variable registering the time this game object's movement is blocked.
	 */
	public double timeBlocked;
	
	/**
	 * Variable registering whether the lifetime of a game object may be affected.
	 */
	public boolean affectLifeTime = true;

	/**
	 * Variable registering the time this game object has left to live. 
	 */
	public double lifeTime;

	/**
	 * Variable registering the time this game object has to stay in its world before being removed
	 * when it has died. 
	 */
	public double DEATH_LIFETIME;

	/**
	 * Variable registering whether this game object is terminated.
	 */
	public boolean isTerminated = false;
	
	/**
	 * Terminate this game object.
	 *
	 * @effect If the world is not null, the game object will be removed from the world.
	 *       | this.getWorld().removeGameObject(this)
	 * @post   The object is now terminated.
	 *       | new.isTerminated == true
	 */
	public void terminate() {
		if (this.getWorld()!=null) 
			this.getWorld().removeGameObject(this); 
		isTerminated = true;
	}
	
	/**
	 * Check whether the given coordinate is a valid coordinate for any 
	 * position with a given coordinate.
	 * 
	 * @param 	coordinate
	 * 			The coordinate to check.
	 * 	
	 * @return	True if and only if the given coordinate is not Not a Number and is not Infinity.
	 * 		||	result == !(Double.isNaN(coordinate) || Double.isInfinite(coordinate) || value < 0)
	 * 
	 */
	public boolean isValidCoordinate(double value) {
		return (!(Double.isNaN(value) || Double.isInfinite(value) || value < 0));
	}
	

	
	/**
	 * Check whether the given position is a valid position for any 
	 * given position.
	 * 
	 * @param 	position
	 * 			The position to check.
	 * 	
	 * @return	False if the given position is null. 
	 * 		|	if (position == null)
	 * 		|		then result == false
	 * @return	False if the given position is not a valid position.
	 * 		|	if (!position.isValid())
	 * 		|		then result == false
	 * @return	Otherwise, true.
	 * 		|	result == true
	 *  
	 */
	public boolean isValidPosition(Position<Integer> position) {
		if (position == null) 
			return false; 
		
		if(this.getWorld() != null) {
			if (!this.getWorld().isValid(position.getX(), position.getY())) 
				return false; }
		
		return true;
	}

	/**  
	 * Returns a position as defined in class position that represents the coordinates  
	 * of the given game object.
	 * Coordinates are expressed in meters.
	 */
	public Position<Double> getPosition() {
		return position; }

	/**
	 * Set the position of this game object to the given position.
	 * 
	 * @param 	position
	 * 		  	The new position for this game object.
	 * @post	The position of this game object is equal to the given position.
	 * 		|	new.getPosition() == position
	 * @effect	setPixelPosition()
	 *  	
	 */
	public void setPosition(Position<Double> position) throws IllegalArgumentException {
		this.position = position; 
		setPixelPosition();		
	}
	
	/**
	*	Returns the pixel position of this GameObject.
	*
	*	@return		result == new.pixelPosition
	*/
	public Position<Integer> getPixelPosition() {
		return pixelPosition;
	}
	
	
	/**
	*	Returns the pixel position of this Position.
	*
	*	@post	 	If the pixel position of a position is wrongly rounded, the rounding error is fixed.
	*	@return		The pixel position of this GameObject.
	*			|	new Position<Integer>(x, y)
	*/
	public Position<Integer> getPixelPosition(Position<Double> position) {
		int x;
		int y;
		
		if (position.getX() < 0 && position.getX() > -0.01) 
			x = -1;
	
		else if (position.getX()*100 - (int)(position.getX()*100) >= 0.999999) 
			x = (int)(position.getX()*100) +1;
		
		else 
			x = (int)(position.getX()*100);
		
		if (position.getY() < 0 && position.getY() > -0.01) 
			y = -1;
	
		else if (position.getY()*100 - (int)(position.getY()*100) >= 0.999999) 
			y = (int)(position.getY()*100) +1;
		
		else 
			y = (int) (position.getY()*100);
		
		return new Position<Integer>(x, y); 	
	}
	
	/**
	*	Sets the pixelPosition of this game object to the updated pixel position.
	*
	* @post		If the pixel position of a position is wrongly rounded, the rounding error is fixed.
	*/
	public void setPixelPosition() {
		
		
		if (getPosition().getX() < 0 && getPosition().getX() > -0.01) 
			pixelPosition.setX(-1);
	
		else if (getPosition().getX()*100 - (int)(getPosition().getX()*100) >= 0.999999) 
			pixelPosition.setX((int)(getPosition().getX()*100) +1);
		
		else 
			pixelPosition.setX((int)(getPosition().getX()*100));
		
		if (getPosition().getY() < 0 && getPosition().getY() > -0.01) 
			pixelPosition.setY(-1);
	
		else if (getPosition().getY()*100 - (int)(getPosition().getY()*100) >= 0.999999)
			pixelPosition.setY((int)(getPosition().getY()*100) +1);
		
		else 
			pixelPosition.setY((int)(getPosition().getY()*100));

	}

	/**
	 * Set the position of this game object to the given position.
	 * 
	 * @param 	pixelPosition
	 * 		  	The new pixel position for this game object.
	 * @post	The position of this game object is equal to the given position.
	 * 		|	new.pixelPosition = pixelPosition;
	 *  	
	 */
	public void setPixelPosition(Position<Integer> pixelPosition) throws IllegalArgumentException {
		this.pixelPosition = pixelPosition; 
		
	}
	
	/**
	 * Variable registering the position of the new game object in meters.
	 */
	protected Position<Double> position;
	
	/**
	 * Variable registering the position of the new game object in pixels.
	 */
	protected Position<Integer> pixelPosition;
	
	/**
	 * Variable registering whether a new position of a game object would be a valid position.
	 * 
	 */
	public boolean isValidNewPosition;

	/**
	 * Variable registering whether this game object is on top of another game object. 
	 * The lowest perimeter of this game object may overlap with the upper perimeter of another game object.
	 */
	public boolean isOnTopOfOtherGameObject = false;
	
	/**
	 * Try if a new position is a valid position for this gameObject if this gameObject is of class Mazub.
	 */
	public boolean isAccessiblePosition(Position<Double> newPosition) {
		return false;
	}
	
	/**
	*	Change the actual position of this GameObject to a new position.
	*/
	public void changeActualPosition(Position<Double> newPosition) throws IllegalArgumentException {
	}
	
	/**
	*	Change the actual position of this GameObject to a new position.
	*
	* @param 	newPosition
	*			The new position for this gameObject
	* @effect	changeActualPosition(position)
	* @throws 	IllegalArgumentException
	*			Given position is null.
	*		|	if(newPosition == null)
	* @throws	IllegalArgumentException
	*			Given position is not good size.
	*		|	if(newPosition.length != 2					
	*/
	public void changeActualPosition(double[] newPosition) throws IllegalArgumentException {
		if(newPosition == null) 
			throw new IllegalArgumentException("Given position is null."); 
		if(newPosition.length != 2) 
			throw new IllegalArgumentException("Given position is not good size."); 
		
		position = new Position(newPosition[0], newPosition[1]);
		changeActualPosition(position);
	}
	
	/**
	 * 	Returns the orientation of this GameObject.
	 *  The resulting value is negative if this GameObject is oriented to the left,
	 *  0 if it is oriented to the front and positive if it is oriented to the right
	 */
	@Basic
	public int getOrientation() {
		return orientation; }
	
	/**
	*			Set the orientation of this GameObject.
	* @param 	orientation
	*			The orientation to register. The resulting value is negative if this GameObject is oriented to the left,
	*  			0 if it is oriented to the front and positive if it
	*  			is oriented to the right.
	* @post 	The new orientation of this game object is set to orientation
	*		|	new.getOrientation() = orientation
	*/
	@Raw
	public void setOrientation(int orientation) {
		this.orientation = orientation; }
	
	/**
	 * Variable registering the orientation of this GameObject.
	 */
	protected int orientation;

	/**
	 * Variable registering the horizontal velocity of this game object.
	 */
	protected double horizontalVelocity;

	/**
	 * Variable registering the standard horizontal velocity of this game object. This is useful if the horizontal 
	 * velocity is immutable for a game object in a specific state.
	 */
	protected double STANDARD_HORIZONTAL_VELOCITY;


	/**
	 * Variable registering the minimum horizontal velocity of this game object.
	 */
	protected double minHorizontalVelocity;


	/**
	 * Variable registering the maximum horizontal velocity of this game object.
	 */
	protected double maxHorizontalVelocity;
	
	/**
	 * Variable registering the vertical velocity of this game object.
	 */
	protected double verticalVelocity;

	/**
	 * Variable registering the standard vertical velocity of this game object. This is useful if the vertical
	 * velocity is immutable for a game object in a specific state.
	 */
	protected double STANDARD_VERTICAL_VELOCITY;

	/**
	 * Variable registering the maximum vertical velocity of this game object.
	 */
	protected double maxVerticalVelocity;

	/**
	 * Return the velocity of this game object. The velocity is expressed in meters per second.
	 */
	public double[] getVelocity() {
		return null;
	}
	
	/**
	 * Variable registering the horizontal acceleration of this game object.
	 */
	protected double horizontalAcceleration;

	/**
	 * Variable registering the standard horizontal acceleration of this game object. This is useful if the horizontal 
	 * acceleration is immutable for a game object in a specific state.
	 */
	protected double STANDARD_HORIZONTAL_ACCELERATION;

	
	/**
	 * Variable registering the vertical acceleration of this game object.
	 */
	protected double verticalAcceleration;

	/**
	 * Variable registering the standard vertical acceleration of this game object. This is useful if the horizontal 
	 * acceleration is immutable for a game object in a specific state. Due to the gravity, this will be set to -10 meters per second^2.
	 */
	protected final double STANDARD_VERTICAL_ACCELERATION = -10;

	/**
	 * Return the acceleration of this GameObject. The acceleration is expressed in meters per second^2.
	 * 
	 * @return	The value of the acceleration.
	 * 		| 	result == acceleration
	 *	@post	The length of the resulting list of double values is equal to 2.
	 *		|	double[] acceleration = new double[2]
	 */
	public double[] getAcceleration() {
		return null;

	}
	
	/**
	 * Checking whether the sprites of this game object are valid.
	 * 
	 * @param 	AllSprites
	 * 			All the sprites of this game object.		
	 * @return	True if the sprites are valid.
	 * @throws 	IllegalArgumentException
	 * 			allSprites == null
	 */
	public boolean isValidSprites(Sprite[] allSprites) throws IllegalArgumentException {
		if(allSprites == null) 
			throw new IllegalArgumentException("Illegal argument given"); 
		
		for(Sprite i : allSprites) {
			if (i == null) 
				throw new IllegalArgumentException("Sprites are null."); }
		return true;
	}
	
	/**
	 * Checking whether a sprite of this game object is valid.
	 * 
	 * @param 	sprite	
	 * 			The sprite to be checked.		
	 * @return	restult == true
	 * @throws 	IllegalArgumentException
	 */
	public boolean isValidSprite(Sprite sprite) throws IllegalArgumentException {
		if(sprite == null) 
			throw new IllegalArgumentException("Illegal argument given"); 
		return true; 
	}
	
	/**
	 * Variable registering the minimum amount of sprites.
	 */
	protected int minNumberOfSprites;

	/**
	 * Variable registering the maximum amount of sprites.
	 */
	protected int maxNumberOfSprites;

	/**
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Sprite[] getAllSprites() throws IllegalArgumentException {
		Sprite[] clonedSprites = Arrays.copyOf(allSprites, allSprites.length);

		return clonedSprites; }
	
	/**
	 * Variable registering all the sprites of this game object.
	 */
	protected Sprite[] allSprites;

	/**
	 * @param 	sprites
	 * @throws 	IllegalArgumentException
	 */
	protected void setAllSprites(Sprite[] sprites) throws IllegalArgumentException {
		if (isValidSprites(sprites)) {
			Sprite[] clonedSprites = Arrays.copyOf(sprites, sprites.length);

			this.allSprites = new Sprite[clonedSprites.length];
			
			
			
			for(Sprite i : clonedSprites) {
				allSprites[counter] = i;
				counter ++; }
			counter --; 
			
		}
		else {
			throw new IllegalArgumentException("Illegal argument given"); }
	}

	/**
	 * Variable registering the index when iterating.
	 */
	protected int counter = 0;

	/**
	 * Variable registering the number of alternating sprites.
	 */
	protected int NUMBER_OF_ALTERNATING_SPRITES;

	/**
	 * @return result == currentSprite
	 */
	@Basic
	public Sprite getCurrentSprite() {
			return currentSprite; }
	
	/**
	 * Variable registering the current Sprite of this GameObject.
	 */
	protected Sprite currentSprite;


	/**
	 * Return the amount of hitpoints of this game object.
	 * 
	 * @return	The amount of hitpoints.
	 * 		| 	result == this.hitPoints
	 */
	@Basic
	public int getHitPoints() {
		return this.hitPoints;
	}
	
	/**
	 * Set the amount of hitpoints of this game object to the value. Hitpoints always are integer numbers.
	 * 
	 * @param 	value
	 * 		  	The new amount of hitpoints for this game object.
	 * @post	The amount of hitpoints of this game object is equal to the given value.
	 * 		|	new.getHitPoints() = (int) value
	 *  	
	 */
	@Raw
	public void setHitPoints(double value) {
		this.hitPoints = (int) value;
	}
	
	/**
	 * 
	 * Increase or decrease the current amount of hitpoints of this game object with a given value.
	 */
	@Raw
	public void addHitPoints(double value) {
		
		if (this.getHitPoints() == 0) 
			this.isDead = true; 
		setHitPoints(getHitPoints() + value); 
	}
	
	/**
	 * Variable registering the amount of hitpoints. Hitpoints always are integer numbers.
	 */
	protected int hitPoints;
	
	public boolean isMaxHitPoints() {
		return (getHitPoints() == getMaxHitPoints());
	}
	
	/**
	 * Variable registering the maximum amount of hitpoints. Hitpoints always are integer numbers.
	 */
	protected int maxHitPoints;
	

	/**
	 * Return the amount of maximum hitpoints of this game object.
	 * 
	 * @return	The amount of maximum hitpoints.
	 * 		| 	result == this.maxHitPoints
	 */
	@Basic
	public int getMaxHitPoints() {
		return maxHitPoints;
	}

	/**
	 * Set the amount of maximum hitpoints of this game object.
	 * 
	 * @post	new.maxHitPoints == maxHitPoints
	 */
	@Raw
	public void setMaxHitPoints(int maxHitPoints) {
		this.maxHitPoints = maxHitPoints;
	}

	/**  
	 * Returns the world as defined in the class World, this game object is living in.
	 * A null reference is returned if this game object has no world.
	 * @return	The world this game object is living in.
	 * 		|	result == this.hasAsWorld
	 * @return	Null if this game object has no world.
	 * 		|	if (isTerminated) then
	 * 		|		result == null
	 * 		
	 */
	@Basic
	public World getWorld() {
		if (isTerminated) 
			return null;
		return hasAsWorld;}
	
	/**
	 * Set the world of this game object to given world as defined in the class World.
	 * 
	 * @param 	world
	 * 		  	The new world for this game object to live in.
	 * @post	The new world of this game object is equal to the given world.
	 * 		|	new.getWorld() = world
	 *  	
	 */
	@Raw
	public void setWorld(World world) {
		hasAsWorld = world;}
	
	/**
	 * Variable registering the world as defined in the class World, this game object is living in.
	 */
	protected World hasAsWorld;

	/**
	 * Returns whether or not this GameObject is currently in water.
	 * @return	for integer i between the left and right x position of this gameObject
	 * 				for integer j between the upper and lower y position of this gameObject
	 * 					if (this.getWorld().getGeologicalFeature(i, j) == 2) then
	 * 						result == true
	 * 			
	 */
	public boolean isInWater() {
				
		int width = getCurrentSprite().getWidth();
		int height = getCurrentSprite().getHeight();
		int leftX = getPixelPosition().getX();
		int rightX = getPixelPosition().getX() + width ;
		int underY = getPixelPosition().getY();
		int upperY = getPixelPosition().getY() + height;
		
		if (this.getWorld() != null) {
			for(int i = leftX; i<= rightX; i++) {
				for(int j = underY; j<= upperY; j++) {
					if (this.getWorld().getGeologicalFeature(i, j) == 2) {				
					return true; } } } }	
		return false;	
	}
	
	/**
	 * Returns whether or not this GameObject is currently submerged in water.
	 * @return	for integer i between the left and right x position of this gameObject
	 * 					if (this.getWorld().getGeologicalFeature(i, getPixelPosition().getY() + height) == 2) then
	 * 						result == true		
	 */
	public boolean submergedInWater() {
		int width = getCurrentSprite().getWidth();
		int height = getCurrentSprite().getHeight();
		int leftX = getPixelPosition().getX();
		int rightX = getPixelPosition().getX() + width ;
		int upperY = getPixelPosition().getY() + height;
		
		if (this.getWorld() != null) {
			for(int i = leftX; i<= rightX; i++) {
					if (this.getWorld().getGeologicalFeature(i, upperY) == 2) {				
					return true; } } }
		return false;	
	}
	
	/**
	 Returns whether or not this GameObject is currently in magma.
	 * @return	for integer i between the left and right x position of this gameObject
	 * 				for integer j between the upper and lower y position of this gameObject
	 * 					if (this.getWorld().getGeologicalFeature(i, j) == 3) then
	 * 						result == true
	 * 			
	 */
	public boolean isInMagma() {
		int width = getCurrentSprite().getWidth();
		int height = getCurrentSprite().getHeight();
		int leftX = getPixelPosition().getX();
		int rightX = getPixelPosition().getX() + width;
		int underY = getPixelPosition().getY();
		int upperY = getPixelPosition().getY() + height;
		
		if (this.getWorld() != null) {
			for(int i = leftX; i<= rightX; i++) {
				for(int j = underY; j<= upperY; j++) {
					if (this.getWorld().getGeologicalFeature(i, j) == 3) {
							return true; } } } }
		return false;	
	}
	
	/**
	 Returns whether or not this GameObject is currently in gas.
	 * @return	for integer i between the left and right x position of this gameObject
	 * 				for integer j between the upper and lower y position of this gameObject
	 * 					if (this.getWorld().getGeologicalFeature(i, j) == 5) then
	 * 						result == true
	 * 			
	 */
	public boolean isInGas() {
		int width = getCurrentSprite().getWidth() ;
		int height = getCurrentSprite().getHeight()  ;
		int leftX = getPixelPosition().getX() ;
		int rightX = getPixelPosition().getX() + width;
		int underY = getPixelPosition().getY()  ;
		int upperY = getPixelPosition().getY() + height;
		
		if (this.getWorld() != null) {
			for(int i = leftX; i<= rightX; i++) {
				for(int j = underY; j<= upperY; j++) {
					if (this.getWorld().getGeologicalFeature(i, j) == 5) {
							return true; } } } }
		return false;	
	}
	
	/**
	 * Check whether this game object is on ground.
	 * @post	isOnGround is changed to true, if and only if this plant has solid ground beneath it.
	 * 		|	for(int i = leftX; i<= rightX; i++) {
	 * 		|	if (new.getWorld().getGeologicalFeature(i, y) == 1 
	 *		|		|| this.getWorld().getGeologicalFeature(i, y-1) == 1) 
	 *					then new.isOnGround == true	
	 *  
	 */
	public boolean isOnGround() {
		int width = getAllSprites()[0].getWidth();
		int leftX = getPixelPosition().getX()+1;
		int rightX = getPixelPosition().getX() + width-1;
		int y = getPixelPosition().getY();
		
		if (this.getWorld() != null) {
		for(int i = leftX; i<= rightX; i++) {
			if (this.getWorld().getGeologicalFeature(i, y) == 1 
					|| this.getWorld().getGeologicalFeature(i, y-1) == 1) {
				return true; } } }
		return false;	
	}
	
	
	/**
	 * Check whether this game object is next to another game object.
	 *
	 * @param	 other
	 * @return	!(((new.getPixelPosition().getX()+(new.getCurrentSprite().getWidth())) < other.getPixelPosition().getX() ) 
	 *			|| ((other.getPixelPosition().getX()+(other.getCurrentSprite().getWidth())) < new.getPixelPosition().getX() )
	 *			|| ((new.getPixelPosition().getY()+ (new.getCurrentSprite().getHeight() )) < other.getPixelPosition().getY() )
	 *			|| ((other.getPixelPosition().getY() + (other.getCurrentSprite().getHeight())) < new.getPixelPosition().getY() ))
	 */
	public boolean isNextTo(GameObject other) {
		return (!(((this.getPixelPosition().getX()+(this.getCurrentSprite().getWidth())) < other.getPixelPosition().getX() ) 
				|| ((other.getPixelPosition().getX()+(other.getCurrentSprite().getWidth())) < this.getPixelPosition().getX() )
				|| ((this.getPixelPosition().getY()+ (this.getCurrentSprite().getHeight() )) < other.getPixelPosition().getY() )
				|| ((other.getPixelPosition().getY() + (other.getCurrentSprite().getHeight())) < this.getPixelPosition().getY() )));
	}

	/**
	 * Check whether this game object is colliding with another game object.
	 * 
	 * @param 	other
	 * 			The other game object that will be used to check whether this game object is colliding with another game object.
	 * @return	True if and only if the second last right-side-perimeter of this game object is smaller than
	 * 			the x-pixelposition of the other game object and
	 * 			if and only if the second last right-side-perimeter of the other game object is smaller than 
	 * 			the x-pixelposition of this game object and
	 * 			if and only if the second last upper-perimeter of this game object is smaller than
	 * 			the y-pixelposition of the other game object and
	 * 			if and only if the second last upper-perimeter of the other game object is smaller than
	 * 			the y-pixelposition of this game object.
	 *       | result ==
	 *       |   (!(((this.getPixelPosition()[0]+(this.getCurrentSprite().getWidth()-1)) < other.getPixelPosition()[0] ) 
	 *		 |	|| ((other.getPixelPosition()[0]+(other.getCurrentSprite().getWidth()-1)) < this.getPixelPosition()[0] )
	 *		 |	|| ((this.getPixelPosition()[1] + (this.getCurrentSprite().getHeight()-1 )) < other.getPixelPosition()[1] )
	 *		 |	|| ((other.getPixelPosition()[1] + (other.getCurrentSprite().getHeight() -1)) < this.getPosition().getPixelPosition()[1] )))
	 */
	public boolean collidesWith(GameObject other) {
		
		return (!(((this.getPixelPosition().getX()+(this.getCurrentSprite().getWidth()-1)) < other.getPixelPosition().getX() ) 
				|| ((other.getPixelPosition().getX()+(other.getCurrentSprite().getWidth()-1)) < this.getPixelPosition().getX() )
				|| ((this.getPixelPosition().getY() + (this.getCurrentSprite().getHeight()-1 )) < other.getPixelPosition().getY() )
				|| ((other.getPixelPosition().getY() + (other.getCurrentSprite().getHeight() -1)) < this.getPixelPosition().getY() )));
	}
	
	/**
	 * Check whether this game object is overlapping with another game object.
	 * @return 	for (Object i : gameObjectsCopy) {
	 *				if (i != new && !(i instanceof Plant)) {
	 *					if (new.collidesWith((GameObject) i)) {
	 *						restult == true
	 *  
	 */
	public boolean isOverlappingWithGameObject(Set<Object> gameObjectsCopy) {
		for (Object i : gameObjectsCopy) {
			if (i != this && !(i instanceof Plant)) {
				if (this.collidesWith((GameObject) i)) {
					return true; } } }
		return false;
	}

	/**
	 * Check whether this game object is colliding with another mazub.
	 * 
	 * @param	other
	 * 			The other game object that will be used to check whether this game object is colliding with another game object.
	 * @return	The result of the method collidesWith if and only if this game object is a mazub.
	 * 		|	if(other instanceof Mazub) then
	 * 		|		result == collidesWith(other)
	 * @return	False otherwise.
	 * 		|		result == false
	 */
	public boolean collidesWithMazub() {
			return collidesWith(this.getWorld().getMazub());
	}
	
	
	/**
	 * Check whether this game object is overlapping with another game object.
	 * 
	 * @return	True if and only if this game object is overlapping with another mazub or another plant.
	 *		|	result == (isOverlappingWithMazub(getWorld().getAllGameObjects()) 
	 *		|			|| isOverlappingWithPlant(getWorld().getAllGameObjects()))
	 */
	public boolean isOverlapping() {
		return(isOverlappingWithGameObject(getWorld().getAllGameObjects()) 
				|| isOverlappingWithPlant(getWorld().getAllGameObjects()));
	}
	
	/**
	 * Check whether this game object is overlapping with another mazub.
	 * 
	 * @param	gameObjectsCopy
	 * 			A copy of the list of all existing game objects.
	 * @return	True if and only if this game object is overlapping with another mazub (not itself).
	 *		|	for (Object i:gameObjectsCopy)
	 *		|			if ((i instanceof Mazub && i != this) && (this.collidesWith((Mazub) i))) then
	 *		|				result == true
	 */
	public boolean isOverlappingWithMazub(Set<Object> gameObjectsCopy) {			
		for (Object i:gameObjectsCopy) {
				if (i instanceof Mazub && i != this) {
					if (this.collidesWith((Mazub) i)) {
						return true; } } } 
			return false;
	}
	
	/**
	 * Check whether this game object is overlapping with another plant.
	 * 
	 * @param	gameObjectsCopy
	 * 			A copy of the list of all existing game objects.
	 * @return	True if and only if this game object is overlapping with another plant (not itself).
	 *		|	for (Object i:gameObjectsCopy)
	 *		|			if ((i instanceof Plant && i != this) && (this.collidesWith((Plant) i))) then
	 *		|				result == true
	 */
	public boolean isOverlappingWithPlant(Set<Object> gameObjectsCopy) {		
		for (Object i:gameObjectsCopy) {		
				if (i instanceof Plant && i != this) {
					if (this.collidesWith((Plant) i)) {
						return true; } } } 
			return false;
	}
	
	/**
	 * Check whether this game object is overlapping with another skullcab.
	 * 
	 * @param	gameObjectsCopy
	 * 			A copy of the list of all existing game objects.
	 * @return	True if and only if this game object is overlapping with another  skullcab (not itself).
	 *		|	for (Object i:gameObjectsCopy)
	 *		|			if ((i instanceof Skullcab && i != this) && (this.collidesWith((Skullcab) i))) then
	 *		|				result == true
	 */
	public boolean isOverlappingWithSkullcab(Set<Object> gameObjectsCopy) {		
		for (Object i:gameObjectsCopy) {		
				if (i instanceof Skullcab && i != this) {
					if (this.collidesWith((Skullcab) i)) {
						return true; } } } 
			return false;
	}
	
	/**
	 * Check whether this game object is overlapping with another Sneezewort.
	 * 
	 * @param	gameObjectsCopy
	 * 			A copy of the list of all existing game objects.
	 * @return	True if and only if this game object is overlapping with another plant (not itself).
	 *		|	for (Object i:gameObjectsCopy)
	 *		|			if ((i instanceof Sneezewort && i != this) && (this.collidesWith((Sneezewort) i))) then
	 *		|				result == true
	 */
	public boolean isOverlappingWithSneezewort(Set<Object> gameObjectsCopy) {		
		for (Object i:gameObjectsCopy) {		
				if (i instanceof Sneezewort && i != this) {
					if (this.collidesWith((Sneezewort) i)) {
						return true; } } } 
			return false;
	}
	
	/**
	 * Check whether this game object is overlapping with another Slime.
	 * 
	 * @param	gameObjectsCopy
	 * 			A copy of the list of all existing game objects.
	 * @return	True if and only if this game object is overlapping with another plant (not itself).
	 *		|	for (Object i:gameObjectsCopy)
	 *		|			if ((i instanceof Slime && i != this) && (this.collidesWith((Slime) i))) then
	 *		|				result == true
	 */
	public boolean isOverlappingWithSlime(Set<Object> gameObjectsCopy) {	
		for (Object i:gameObjectsCopy) {		
				if (i instanceof Slime && i != this) {
					if (this.collidesWith((Slime) i)) {
						return true; } } } 
			return false;
	}
	
	/**
	 * Check whether this game object is overlapping with another plant.
	 * 
	 * @param	gameObjectsCopy
	 * 			A copy of the list of all existing game objects.
	 * @return	True if and only if this game object is overlapping with another plant (not itself).
	 *		|	for (Object i:gameObjectsCopy)
	 *		|			if ((i instanceof Shark && i != this) && (this.collidesWith((Shark) i))) then
	 *		|				result == true
	 */
	public boolean isOverlappingWithShark(Set<Object> gameObjectsCopy) {
		for (Object i:gameObjectsCopy) {		
				if (i instanceof Shark && i != this) {
					if (this.collidesWith((Shark) i)) {
						return true; } } } 
			return false;
	}
	
	/**
	 * Variable registering whether this game object would overlap with a mazub.
	 */
	public boolean wouldOverlapWithMazub = false;
	
	/**
	 * Variable registering whether this game object would overlap with a slime.
	 */
	public boolean wouldOverlapWithSlime = false;
	
	/**
	 * Variable registering whether this game object would overlap with a plant.
	 */
	public boolean wouldOverlapWithPlant = false;
	
	/**
	 * Start moving this game object to the left or to the right, depending on the given direction. The sign of its 
	 * velocity or acceleration depends on its orientation.
	 */
	public void startMove(String direction) {
	}
	
	public void advanceTime(double dt) throws IllegalArgumentException {	
	}

}
	