package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.util.Sprite;

/**
 * A class of Sneezeworts with characteristics x and y position and sprites.
 * @invar  	Each Sneezewort must have a valid position.
 *       	| isValidPosition(Position position)
 * @invar	Each Sneezewort must have a valid array of sprites
 * 			| isValidSprites(sprites)
 * @version version2.0
 * @author Danaë Van de Velde & Thomas Vrolix
 * 
 */
@SuppressWarnings("all")
public class Sneezewort extends Plant implements HorizontalMoving{
	
	/**
	 * Initialize this new Sneezewort with given x-position, given y position and given sprites.
	 * The standard vertical velocity of this sneezewort is always zero meters per second, 
	 * it can only live for 10 seconds,
	 * it only has 2 sprites and it will start moving to the left. 
	 * After a sneezewort has died, it will stay for 0.6 seconds in its world.
	 * Sneezeworts possess only 1 hitpoint.
	 *
	 * @param	x
	 *			The x-coordinate of this new sneezewort.
	 * @param	y
	 *			The y-coordinate of this new sneezewort.
	 * @param	sprites
	 *			The sprites for this new sneezewort.
	 * @effect 	This sneezewort is initialized as a new plant with a given x-coordinate, y-coordinate
	 * 			and given sprites.
	 * 		|  	this(x,y, sprites)
	 */
	@Raw
	public Sneezewort(int x, int y, Sprite...sprites) {
		super(x, y, sprites);
		setHorizontalVelocity(-0.5);
		STANDARD_VERTICAL_VELOCITY = 0;
		lifeTime = 10;
		DEATH_LIFETIME = 0.6;
		setHitPoints(1);
		minNumberOfSprites = 2;
		maxNumberOfSprites = 2;
		currentSprite = getAllSprites()[0];
		startMove("left");
		
	}
	
		
	/**
	 * Return the horizontal velocity of this Sneezewort. The velocity is expressed in meters per second.
	 * 
	 * @return	The value of the horizontal velocity.
	 * 		| 	result == horizontalVelocity
	 */
	@Basic
	@Override
	public double getHorizontalVelocity() {
		return horizontalVelocity; }
	
	/**
	 * 	Set the horizontal velocity of this Sneezewort. 
	 * @Param 	value
	 * 			The value of the new horizontal velocity of this Sneezewort.
	 * @Post	new.horizontalVelocity = value
	 */
	@Override
	public void setHorizontalVelocity(double value) {
		horizontalVelocity = value; 	
	}



	/**
	*	Set the minimum horizontal velocity of this sneezewort.
	* @param 	value
	*			The new minimum horizontal velocity of this sneezewort.
	* @post 	The new minimum horizontal velocity of this sneezewort is set to value
	*		|	new.minHorizontalVelocity = value
	*/
	@Raw
	@Override
	public void setMinHorizontalVelocity(double value) {
		minHorizontalVelocity = value;
	}

	/**
	*	Set the maximum horizontal velocity of this Sneezewort.
	* @param 	value
	*			The new maximum horizontal velocity of this Sneezewort.
	* @post 	The new maximum horizontal velocity of this Sneezewort is set to value
	*		|	new.maxHorizontalVelocity = value
	*/
	@Raw
	@Override
	public void setMaxHorizontalVelocity(double value) {
		maxHorizontalVelocity = value;
	}
	
	/**
	 * Return the velocity of this sneezewort. The velocity is expressed in meters per second.
	 * 
	 * @return	The value of the velocity.
	 * 		| 	result == velocity
	 */
	@Override
	public double[] getVelocity() {
		double[] velocity = new double[2];
		velocity[0] = this.getHorizontalVelocity();
		velocity[1] = 0;
		return velocity;
	}
	
	/**
	 * Return the horizontal acceleration of this sneezewort. The acceleration is expressed in meters per second^2.
	 * 
	 * @return	The value of the horizontal acceleration.
	 * 		| 	result == horizontalAcceleration
	 */
	@Basic
	@Override
	public double getHorizontalAcceleration() {
		return horizontalAcceleration; }

	/**
	 * Set the horizontal acceleration of this sneezewort to the value.
	 * 
	 * @param 	value
	 * 		  	The new horizontal acceleration for this sneezewort.
	 * @post	The horizontal acceleration of this sneezewort is equal to the given value.
	 * 		|	new.getHorizontalAcceleration() = value	
	 */
	@Raw
	@Override
	public void setHorizontalAcceleration(double value) {
		this.horizontalAcceleration = value; }

	/**
	 * Return the acceleration of this sneezewort. The acceleration is expressed in meters per second^2.
	 * 
	 * @return	The value of the acceleration.
	 * 		| 	result == acceleration
	 */
	@Override
	public double[] getAcceleration() {
		double[] acceleration = new double[2];
		acceleration[0] = this.getHorizontalAcceleration();
		acceleration[1] = 0;
		return acceleration;
	}
		

	
	/**
	 * Start moving this sneezewort to the left or to the right, depending on the given direction.
	 * 
	 * @param	direction
	 * 			The direction in which this sneezewort has to start moving.
	 * @effect	The new horizontal velocity is equal to 0.5 meters per second.
	 * 		|	new.getHorizontalVelocity() == 0.5
	 * @effect	The new orientation is equal to 1 if this sneezewort starts moving right and -1 if this sneezewort starts moving left.
	 * 		|	new.getOrientation() == 1 || new.getOrientation() == -1
	 * @post	The last direction of this plant is changed to direction
	 * 		|	new.getLastDirection() == direction
	 */
	@Override
	public void startMove(String direction) {
			isHorizontalMoving = true;
		if (direction == "right") {
			lastDirection = "right";
			setHorizontalVelocity(0.5);
			setOrientation(1);
			currentSprite = allSprites[1]; 
			}
		else if (direction == "left") {
			lastDirection = "left";
			if (getPosition().getX() != 0) { 
				setHorizontalVelocity(-0.5);
				setOrientation(-1); 
				currentSprite = allSprites[0]; } } 
	}
	
	//part of advancetime
	public void setCurrentHitPoints() {
		if (this.getWorld() != null ) {
			if (this.getWorld().outsideBoundaries(getPixelPosition().getX(), getPixelPosition().getY())) {
				terminate();
				return; }
			
			if (this.getWorld().getMazub() != null && this.collidesWithMazub() ) {
					if (!this.isDead) {
						this.terminate();
						return;}
					else if (this.isDead) {
						this.getWorld().getMazub().addHitPoints(-20);
						this.terminate(); 
						return;}
				
			}
		}
	}
	
	/**
	 * Variable registering if this sneezewort has been eaten yet.
	 */
	public boolean hasBeenEaten = false;

	//part of advancetime
	public void setTimers(double t) {
		if (affectLifeTime == true) {
			lifeTime -= t;}
			
			else {
				affectLifeTime = true; }

			if (lifeTime <= 0) {
				this.isDead = true;
				hitPoints = 0; }
			
			if (isHorizontalMoving && getVelocity()[0] > 0) {
				timeMovingRight += t; }
			else {
				timeMovingRight = 0; }
			
			if (isHorizontalMoving && getVelocity()[0] < 0) {
				timeMovingLeft += t; }
			else {
				timeMovingLeft = 0; }
			
			if (timeMovingLeft >= 0.5) {
				startMove("right"); 
				}
			else if(timeMovingRight >= 0.5) {
				startMove("left");
				} 
	}
	
	
	@Override
	public void advanceTime(double dt) throws IllegalArgumentException {
		//Checking if a valid t is given and corrected in case needed.
		
		if (!isDead) {
				
			double t = 0.01/(0.5);
			
			while (dt > 0 && !isDead) {
				
				t = 0.01/0.5;
				
				if (t > dt) {
					t = dt; }
				
				if (timeMovingLeft + t > 0.5)
					t = 0.5 - timeMovingLeft;
				else if (timeMovingRight + t > 0.5) 
					t = 0.5 - timeMovingRight;
				
			dt -= t;
			
	
			setPosition(new Position(getPosition().getX()+getHorizontalVelocity()*t,
					getPosition().getY()));
			
			setTimers(t);

			setCurrentHitPoints();
			}	
		}
		else {
			DEATH_LIFETIME -= dt;
			if (DEATH_LIFETIME <= 0) {
			
				terminate(); } }
	}
}
