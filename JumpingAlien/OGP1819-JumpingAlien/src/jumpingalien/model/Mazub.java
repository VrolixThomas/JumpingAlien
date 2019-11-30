package jumpingalien.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.util.Sprite;


/**
 * A class of jumping aliens (Mazubs) with characteristics x and y position and sprites.
 * @invar  	Each Mazub must have a valid position.
 *       	| isValidPosition(Position position)
 * @invar	Each Mazub must have a valid array of sprites
 * 			| isValidSprites(sprites)
 * @version version2.0
 * @author Danaë Van de Velde & Thomas Vrolix
 * 
 */

@SuppressWarnings("all")
public class Mazub extends GameObject implements HorizontalMoving, VerticalMoving{
	/**
	 * Initialize this Mazub with given x-coordinate, y-coordinate in pixels and sprites. Mazub is not moving yet
	 * and is facing to the front. Its maximum vertical velocity is 8 meters per second.
	 * Its maximum horizontal velocity is 3 meters per second and minimum is 1 meter per second.
	 * Its standard horizontal acceleration is 0.9 meters per second.
	 * Mazub has 100 hitpoints to start with and after a mazub has died, it will stay for 0.6 seconds in its world.
	 * Mazub has at least 10 different sprites.
	 *
	 * @param	x
	 *			The x-coordinate in pixels of this new Mazub.
	 * @param	y
	 * 			The y-coordinate in pixels of this new Mazub.
	 * @param	sprites
	 *			The sprites for this new Mazub.
	 * @post	This Mazub is not moving yet.
	 *		|	new.isHorizontalMoving == false
	 * @post	The maximum vertical velocity is 8 meters per second.
	 *		|	new.maxVerticalVelocity == 8
	 * @post	The horizontal velocity is zero.
	 * 		|	new.horizontalVelocity == 0
	 * @post	The standard horizontal acceleration is 0.9 meters per second.
	 * 		|	new.STANDARD_HORIZONTAL_ACCELERATION == 0.9
	 * @post	The time mazub will stay in its world when it has died, is 0.6 seconds.
	 * 		|	new.DEATH_LIFETIME == 0.6
	 * @effect	This new Mazub is initialized as a new game object with
	 *			given x-position, y-position and given sprites.
	 *		|	super(s, y, sprites)
	 * @effect	The beginning vertical acceleration is set to 0.
	 *		|	setVerticalAcceleration(0)
	 * @effect	The maximum horizontal velocity is set to 3.
	 *		|	setMaxHorizontalVelocity(3);
	 * @effect	The minimum horizontal velocity is set to 1.
	 *		|	setMinHorizontalVelocity(3);
	 * @effect	The beginning amount of hitpoints is set to 100.
	 *		|	setHitPoints(100);
	 */
	@Raw
	public Mazub(int x, int y, Sprite...sprites) {
		super(x, y, sprites);
		isHorizontalMoving = false;	
		maxVerticalVelocity = 8;
		horizontalVelocity = 0;
		STANDARD_HORIZONTAL_ACCELERATION = 0.9;
		DEATH_LIFETIME = 0.6;
		timeBlocked = 0.6;
		setVerticalAcceleration(0);
		setMaxHorizontalVelocity(3);
		setMinHorizontalVelocity(1);
		setHitPoints(100);
		setMaxHitPoints(500);
		currentSprite = allSprites[0];
		NUMBER_OF_ALTERNATING_SPRITES = counter - 7;
		minNumberOfSprites = 10;
		maxNumberOfSprites = 999999;
	}
	

	
	/**
	 * Return the horizontal velocity of this Mazub. The velocity is expressed in meters per second.
	 * 
	 * @return	The value of the horizontal velocity.
	 * 		| 	result == horizontalVelocity
	 */
	@Basic
	@Override
	public double getHorizontalVelocity() {
		return horizontalVelocity; }
	
	/**
	 * Set the horizontalvelocity of this mazub to the given horizontalvelocity.
	 * @param 		horizontalVelocity
	 * 				The new horizontal velocity for this mazub.
	 * @post		If the absolute value of the given horizontal velocity is not 0 and a value between the 
	 * 				minimum horizontal velocity and the maximum horizontal velocity, 
	 * 				the new horizontal velocity is set to horizontalVelocity.
	 * 			|	if ((getMinHorizontalVelocity <= horizontalVelocity 
	 * 				&& getMaxhorizontalVelocity >= horizontalVelocity) && horizontalVelcity !=0) then 
	 * 				new.getHorizontalVelocity() = horizontalVelocity
	 * @post 		If the given horizontalVelocity is equal to 0, the new horizontalVelocity is set to 0
	 * 			|	if (horizontalVelocity == 0) then 
	 * 				new.getHorizontalVelocity == 0.
	 * @post 		If the absolute value of the given horizontal velocity is below the minimum 
	 *				horizontalvelocity the new horizontal velocity is set the the minimum horizontalvelocity
	 *			| 	if (horizontalVelocity < getMinHorizontalVelocity() then
	 *				new.getHorizontalVelocity = getMinhorizontalVelocity * horizontalVelocity / abs(horizontalVelocity)
	 * @post		If the absolute value of the given horizontal velocity exceeds the maximum 
	 * 				horizontal velocity the new horizontal velocity is set to the maximum horizontal velocity. 
	 * 			|	if (horizontalVelocity > getMaxHorizontalVelocity() then
	 *				new.getHorizontalVelocity = getMaxhorizontalVelocity * horizontalVelocity / abs(horizontalVelocity)
	 */
	@Override
	public void setHorizontalVelocity(double value) {	
		if (Math.abs(value) > maxHorizontalVelocity)
			this.horizontalVelocity = maxHorizontalVelocity*value/Math.abs(value);
		else if (Math.abs(value) < minHorizontalVelocity && value !=0)
			this.horizontalVelocity = minHorizontalVelocity;
		else
			this.horizontalVelocity = value; 
	}

	/**
	*	Set the minimum horizontal velocity of this Mazub.
	* @param 	value
	*			The new minimum horizontal velocity of this Mazub.
	* @post 	The new minimum horizontal velocity of this Mazub is set to value
	*		|	new.minHorizontalVelocity = value
	*/
	@Raw
	@Override
	public void setMinHorizontalVelocity(double value) {
		minHorizontalVelocity = value;
	}

	/**
	*	Set the maximum horizontal velocity of this Mazub.
	* @param 	value
	*			The new maximum horizontal velocity of this Mazub.
	* @post 	The new maximum horizontal velocity of this Mazub is set to value
	*		|	new.maxHorizontalVelocity = value
	*/
	@Raw
	@Override
	public void setMaxHorizontalVelocity(double value) {
		maxHorizontalVelocity = value;
	}
	
	/**
	 * Return the horizontal velocity of this Mazub. The velocity is expressed in meters per second.
	 * 
	 * @return	The value of the horizontal acceleration.
	 * 		| 	result == horizontalAcceleration
	 */
	@Basic
	@Override
	public double getHorizontalAcceleration() {
		return horizontalAcceleration; }

	/**
	 * Set the horizontal acceleration of this Mazub to the value.
	 * 
	 * @param 	value
	 * 		  	The new horizontal acceleration for this Mazub.
	 * @post	The horizontal acceleration of this Mazub is equal to the given value.
	 * 		|	new.getHorizontalAcceleration() = value	
	 */
	@Raw
	@Override
	public void setHorizontalAcceleration(double value) {
		this.horizontalAcceleration = value; }

	/**
	 * Return the vertical velocity of this Mazub. The velocity is expressed in meters per second.
	 * 
	 * @return	The value of the vertical velocity.
	 * 		| 	result == verticalVelocity
	 */
	@Basic
	@Override
	public double getVerticalVelocity() {
		return verticalVelocity;
	}
	
	/**
	 * Set the vertical velocity of this Mazub to the value.
	 * 
	 * @param 	value
	 * 		  	The new vertical velocity for this Mazub.
	 * @post	The vertical velocity of this Mazub is equal to the given value.
	 * 		|	new.getVerticalVelocity() = value
	 *  	
	 */
	@Raw
	@Override
	public void setVerticalVelocity(double value) {
		verticalVelocity = value;
	}
	
	/**
	 * Return the vertical velocity of this Mazub. The velocity is expressed in meters per second.
	 * 
	 * @return	The value of the vertical velocity.
	 * 		| 	result == verticalAcceleration
	 */
	@Basic
	@Override
	public double getVerticalAcceleration() {
		return verticalAcceleration;
	}

	/**
	 * Set the vertical acceleration of this Mazub to the value.
	 * 
	 * @param 	value
	 * 		  	The new vertical acceleration for this Mazub.
	 * @post	The vertical acceleration of this Mazub is equal to the given value.
	 * 		|	new.getVerticalAcceleration() = value
	 *  	
	 */
	@Override
	public void setVerticalAcceleration(double value) {
		this.verticalAcceleration = value;
	}

	
	/**
	 * Return the velocity of this Mazub. The velocity is expressed in meters per second.
	 * 
	 * @return	The value of the velocity.
	 * 		| 	result == velocity
	 * @post	The length of the resulting list of integer values is equal to 2.
	 *		|	double[] velocity = new double[2]
	 */
	@Override
	public double[] getVelocity() {
		double[] velocity = new double[2];
		velocity[0] = this.getHorizontalVelocity();
		velocity[1] = this.getVerticalVelocity();
		return velocity;
	}

	/**
	 * Return the acceleration of this Mazub. The acceleration is expressed in meters per second^2.
	 * 
	 * @return	The value of the acceleration.
	 * 		| 	result == acceleration
	 *	@post	The length of the resulting list of double values is equal to 2.
	 *		|	double[] acceleration = new double[2]
	 */
	@Override
	public double[] getAcceleration() {
		double[] acceleration = new double[2];
		acceleration[0] = this.getHorizontalAcceleration();
		acceleration[1] = this.getVerticalAcceleration();
		return acceleration;
	}
	
	/**
	 * Check whether this Mazub is vertically moving.
	 * @return result == this.getVelocity()!=0
	 */
	@Override
	public boolean isVerticalMoving() {
		return (this.getVelocity()[1] != 0); }
	
	/**
	 * Returns if this Mazub is not moving.
	 * @return 	is true if the vertical and horizontal velocity and acceleration are all 0.
	 * 		|	result == getHorizontalVelocity() == 0 && getHorizontalAcceleration() == 0
	 */
	public boolean isStationary() {
		return (getVerticalVelocity() == 0 && getVerticalAcceleration() == 0 && 
				getHorizontalVelocity() == 0 && getHorizontalAcceleration() == 0);
	}
	
	/**
	 * Check whether this newPosition is an accessible position for this Mazub.
	 * @param	newPosition
	 * 			the position which has to be checked 
	 * Return 	returns false if this new position is in solid ground or if it makes this mazub overlap with another gameobject
	 * 		|	result == false if  !this.getWorld().validTiles(x, y, width, height) || this.getWorld().onTopOfOtherGameObject(mazub, gameObjectsCopy)
	 * 					|| mazub.isOverlappingWithGameObject(gameObjectsCopy)
	 * @post	new.wouldOverlapWithSlime = true if newPositionedShark.isOverlappingWithSlime(gameObjectsCopy)
	 * @post	new.wouldOverlapWithMazub = true if newPositionedShark.isOverlappingWithMazub(gameObjectsCopy)
	 */
	@Override
	public boolean isAccessiblePosition(Position<Double> newPosition) {
		int x = getPixelPosition(newPosition).getX();
		int y = getPixelPosition(newPosition).getY();
		int width = getCurrentSprite().getWidth();
		int height = getCurrentSprite().getHeight();
		
		Mazub mazub = new Mazub(x, y, this.getAllSprites());
		mazub.currentSprite = this.getCurrentSprite();
		Set<Object> gameObjectsCopy = this.getWorld().getAllGameObjects();
		gameObjectsCopy.remove(this);

		if(!this.getWorld().validTiles(x, y, width, height)) 
			return false; 
	
		if (mazub.isOverlappingWithPlant(gameObjectsCopy)) 
			wouldOverlapWithPlant = true;
		
		if (mazub.isOverlappingWithSlime(gameObjectsCopy)) 
			wouldOverlapWithSlime = true;
		
		if (this.getWorld().onTopOfOtherGameObject(mazub, gameObjectsCopy) ) {
			isOnTopOfOtherGameObject = true;	
			return false; }
		
		if (mazub.isOverlappingWithGameObject(gameObjectsCopy)) 
			return false;
		
		return true; }
	
	/**
	*	Change the actual position of this Mazub to a new position.
	*
	*	@param 		newPosition
	*				The position of this new position for Mazub
	*	@post 		the new position of this Mazub is newPosition
	* 			|	new.getPosition() = newPosition
	* 	@throws 	IllegalArgumentException
	*				this newPosition is not a valid position for class position
	*			|	!isAccessiblePosition(newPosition) || 	
	*				Double.isNaN((double) newPosition.getX()) || Double.isNaN((double) newPosition.getY())		
	*/
	@Override
	public void changeActualPosition(Position<Double> newPosition) throws IllegalArgumentException {
		
		
		if (Double.isNaN((double) newPosition.getX()) || Double.isNaN((double) newPosition.getY())) 
			throw new IllegalArgumentException("Cannot be NaN."); 
		if (getWorld() == null) {
			setPosition(newPosition);		
			return;	}

		else {		
			if (getWorld().positionOutsideWorld(newPosition)) {
				terminate();	
				return; }
			if(!isAccessiblePosition(newPosition)) 
				throw new IllegalArgumentException("Cannot be overlapping with another game object or can't be on impassable terrain."); 
		}	
		setPosition(newPosition);
	}
	
	/**
	 * Check whether this Mazub is on ground.
	 * 
	 * @post	isOnGround is changed to true, if and only if this Mazub has solid ground beneath it.
	 * 		|	for(int i = leftX; i<= rightX; i++) {
	 * 		|	if (this.getWorld().getGeologicalFeature(i, y) == 1 
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
						|| this.getWorld().getGeologicalFeature(i, y-1) == 1) 
					return true; 				
			}	
		}
		return false;	
	}
			
	/**
	 * Start moving this Mazub to the left or to the right, depending on the given direction. The sign of its 
	 * velocity or acceleration depends on its orientation.
	 * 
	 * @param	direction
	 * 			The direction in which this Mazub has to start moving.
	 * @pre		Mazub isn't dead.
	 * 		|	assertFalse(isDead)
	 * @pre		Mazub is not already horizontally moving.
	 * 		|	assertFalse(isHorizontalMoving)
	 * @post	isHorizontalMoving is set to true.
	 * 		|	new.isHorizontalMoving = true
	 * @effect	The new horizontal velocity is equal to the minimum horizontal velocity.
	 * 		|	setHorizontalVelocity(minHorizontalVelocity)
	 * @effect	The new horizontal acceleration is equal to the standard horizontal acceleration.
	 * 		|	setHorizontalAcceleration(STANDARD_HORIZONTAL_ACCELERATION)
	 * @effect	The new orientation is equal to 1 if Mazub starts moving right and -1 if Mazub starts moving left.
	 * 		|	setOrientation(1) || setOrientation(-1)
	 */
	@Override
	public void startMove(String direction) throws IllegalArgumentException {
		assertFalse(isDead);
		assertFalse(isHorizontalMoving);
		isHorizontalMoving = true;
		if (direction == "right") {
			lastDirection = "right";
			setOrientation(1);
			setHorizontalVelocity(minHorizontalVelocity);
			setHorizontalAcceleration(STANDARD_HORIZONTAL_ACCELERATION);
			setCurrentSprite(); }
		else if (direction == "left") {
			lastDirection = "left";
			if (getPosition().getX() != 0) { 
				this.currentSprite = getAllSprites()[8 + NUMBER_OF_ALTERNATING_SPRITES/2];
				setOrientation(-1); 
				setHorizontalVelocity(-minHorizontalVelocity);
				setHorizontalAcceleration(-STANDARD_HORIZONTAL_ACCELERATION);
				setCurrentSprite(); } }
	}
	
	/**
	 * Stops moving this mazub.
	 * 
	 * @pre		The mazub is horizontally moving.
	 * 		|	assertTrue(isHorizontalMoving)
	 * @effect	The horizontal velocity of this mazub is set to 0.
	 * 		|	setHorizontalVelocity(0)
	 * @effect	The horizontal acceleration of this mazub is set to 0.
	 * 		|	setHorizontalAcceleration(0)
	 * @effect 	The vertical Acceleration of this mazub is set to 0.
	 * 		|	setVerticalAcceleration(0)
	 * @post	isHorizontalMoving is set to false.
	 * 		|	new.isHorizontalMoving == false
	 */
	public void endMove() {
		assertTrue(isHorizontalMoving);
		setHorizontalVelocity(0);	
		setHorizontalAcceleration(0);
		setVerticalAcceleration(0);	
		if(lastDirection == "right") 
			currentSprite = getAllSprites()[2];
		else if (lastDirection == "left")
			currentSprite = getAllSprites()[3];
		isHorizontalMoving = false;	
	}
	
	/**
	 * This mazub will start to duck.
	 * 
	 * @post	isDucking is set to true.
	 * 		|	new.isDucking == true
	 * @effect	The horizontalAcceleration for this mazub is set to 0.
	 * 		|	setHorizontalAcceleration(0)
	 * @effect	If this mazub is moving then it's absolute velocity is set to 1.
	 * 		|	if(getHorizontalVelocity() != 0)
	 * 		|		then setHorizontalVelocity(getHorizontalVelocity()/Math.abs(getHorizontalVelocity()))
	 */
	public void startDuck() {
		if (!isDucking && !isDead) {
			isDucking = true;
			if(getHorizontalVelocity() != 0) {
				setHorizontalVelocity(getHorizontalVelocity()/Math.abs(getHorizontalVelocity()));
				setCurrentSprite();	
			}
			setHorizontalAcceleration(0);
			setCurrentSprite();			
		}		
	}
	
	/**
	 * This mazub will stop ducking.
	 * Nothing happens if this mazub will overlap with impassable terrain when it stops ducking.
	 * 
	 * @post	isDucking is set to false.
	 * 		|	new.isDucking == false
	 * @effect	If this mazub is horizontalmoving its absolute horizontal acceleration is set 
	 * 			to the standard horizontal acceleration.
	 * 		|	if (isHorizontalMoving) 
	 * 		|		then setHorizontalAcceleration(STANDARD_HORIZONTAL_ACCELERATION*getHorizontalVelocity()
	 * 												/Math.abs(getHorizontalVelocity()))
	 */
	public void endDuck() {		
		Mazub mazubstanding = new Mazub(getPixelPosition().getX(),getPixelPosition().getY(),this.getAllSprites());
		Set<Object> gameObjectsCopy = this.getWorld().getAllGameObjects();
		gameObjectsCopy.remove(this);
		if (isDucking && !mazubstanding.isOverlappingWithGameObject(gameObjectsCopy)&& getWorld().validTiles(getPixelPosition().getX(),
				getPixelPosition().getY(),allSprites[0].getWidth(),
				allSprites[0].getHeight())) {
			isDucking = false;
		
			if (isHorizontalMoving) {
				setHorizontalAcceleration(STANDARD_HORIZONTAL_ACCELERATION*getHorizontalVelocity()/Math.abs(getHorizontalVelocity())); 
				if (getVelocity()[0] > 0) 
					currentSprite = getAllSprites()[8];	
				else 
					currentSprite = getAllSprites()[8 + NUMBER_OF_ALTERNATING_SPRITES/2]; 			
			}			
			else 
				setCurrentSprite();			
		}
	}
	
	/**
	 * This Mazub starts jumping.
	 * 
	 * @post	isJumping is set to true.
	 * 		|	new.isJumping == true
	 * @effect	The new vertical velocity is equal to the maximum vertical velocity.
	 * 		|	setVerticalVelocity(maxVerticalVelocity)
	 * @effect	The new vertical acceleration is equal to the standard vertical acceleration.
	 * 		|	setVerticalAcceleration(STANDARD_VERTICAL_ACCELERATION)
	 * @throws	IllegalArgumentException
	 * 			Mazub is dead.
	 * 		|	isDead
	 * @throws	IllegalArgumentException
	 * 			Mazub is already jumping or dead.
	 * 		|	!isJumping && !isDead
	 *
	 */
	public void startJump() throws IllegalArgumentException {
		if (isDead) 
			throw new IllegalArgumentException("Mazub is dead!"); 
		if ( !isJumping && !isDead) {
			isJumping = true;
			setCurrentSprite();
			setVerticalVelocity(maxVerticalVelocity);
			setVerticalAcceleration(STANDARD_VERTICAL_ACCELERATION); }
		else 
			throw new IllegalArgumentException("Mazub is already jumping!"); 
	}
	
	/**
	 * This Mazub stops jumping.
	 * 
	 * @post	isJumping is set to false.
	 * 		|	new.isJumping == false
	 * @effect	The new vertical velocity is set to zero if it was greater than zero 
	 * 			(if this Mazub was going up).
	 * 		|	if (getVerticalVelocity() > 0)
	 * 		|		then setVerticalVelocity(0)
	 * @effect	The new vertical acceleration is equal to the standard vertical acceleration.
	 * 		|	setVerticalAcceleration(STANDARD_VERTICAL_ACCELERATION)
	 * @throws	IllegalArgumentException
	 * 			Mazub is not jumping!
	 * 		|	!isJumping
	 *
	 */
	public void endJump() throws IllegalArgumentException {
		if (isJumping) {
			isJumping = false;
			if (getVerticalVelocity() > 0) {
				setVerticalVelocity(0);
				setCurrentSprite(); }
			setCurrentSprite();	
			setVerticalAcceleration(STANDARD_VERTICAL_ACCELERATION); }
		else 
			throw new IllegalArgumentException("Mazub is not jumping!");
	}
	
	public boolean firstTimeOverlappingWithShark = true;
	
	public boolean firstTimeOverlappingWithSlime = true;
	
	/**
	 * Variable registering whether mazub gets in the water (again).
	 */
	public boolean firstTimeInWater = true;
	
	/**
	 * Variable registering whether mazub gets in magma (again).
	 */
	public boolean firstTimeInMagma = true;
	
	/**
	 * Variable registering whether mazub gets in gas (again).
	 */
	public boolean firstTimeInGas = true;
	
	/**
	 * Variable registering the time this mazub is in water and is losing hitpoints.
	 */
	double effectiveTimeInWater;
	
	/**
	 * Variable registering the amount of time this mazub will not receive damage from a slime
	 */
	double timeNoDamageFromSlime;
	
	/**
	 * Variable registering the amount of time this mazub will not receive damage from a shark.
	 */
	double timeNoDamageFromShark;
	
	//part of advancetime
	public void setSmallDt(double dt) {
		t = 0.01/((Math.sqrt(Math.pow(Math.abs(getHorizontalVelocity()), 2)
				+ Math.pow(Math.abs(getVerticalVelocity()), 2) )) + 	
				((Math.sqrt(Math.pow(Math.abs(getHorizontalAcceleration()), 2)
						+ Math.pow(Math.abs(getVerticalAcceleration()), 2) )) * dt));	
	}

	//part of advancetime
	public void setCorrectMovement() {	
		if (!isOnGround() && !isOnTopOfOtherGameObject) 
			setVerticalAcceleration(STANDARD_VERTICAL_ACCELERATION); 
		
		if (isDucking) {
			if (!isHorizontalMoving) 
				setHorizontalVelocity(0); 
			setHorizontalAcceleration(0);			
			if (isHorizontalMoving) {
				if (lastDirection == "left") 
					setHorizontalVelocity(-1); 
				else 
					setHorizontalVelocity(1); 
				setHorizontalAcceleration(0); }	
			}
		if (!isHorizontalMoving) 
			setHorizontalVelocity(0); 
		if ((isOnGround() || isOnTopOfOtherGameObject) && !isJumping) 	
			setVerticalAcceleration(0);
	}
	
	//part of advancetime
	public void setFluidTimers(double t) {
		if(this.isInMagma()) 
			timeInMagma += t; 
		else {
			firstTimeInMagma = true;
			timeInMagma = 0; }
		if(this.isInWater() && !(this.isInGas() || this.isInMagma()))   
			timeInWater += t; 
		else {
			firstTimeInWater = true;
			timeInWater = 0;
			effectiveTimeInWater = 0; }
		if(this.isInGas()) 
			timeInGas += t;
		else {
			firstTimeInGas = true;
			timeInGas = 0; }	
	}
	
	//part of advancetime
	public void setMovementTimers(double t) {
		if (isHorizontalMoving && getVelocity()[0] > 0 && !isDucking && !isJumping) 
			timeMovingRight += t; 
		else 
			timeMovingRight = 0; 
		
		if (isHorizontalMoving && getVelocity()[0] < 0 && !isDucking && !isJumping) 
			timeMovingLeft += t; 
		else 
			timeMovingLeft = 0; 
		
		if (!isHorizontalMoving) 
			timeNotMoving += t; 
		
		else if (isHorizontalMoving) {
			timeNotMoving = 0;
			
			if (getVelocity()[0] > 0) 
				lastDirection = "right";
			else 
				lastDirection = "left"; }
	}
	
	//part of advancetime
	public void setNoDamageTimers(double t) {
		if (timeNoDamageFromSlime - t < 0) 
			timeNoDamageFromSlime = 0;	
		else 
			timeNoDamageFromSlime -= t;	
		if (timeNoDamageFromShark - t < 0) 
			timeNoDamageFromShark = 0;
		else 
			timeNoDamageFromShark -= t;
	}

		@Override
	public boolean isValidSprites(Sprite[] allSprites) throws IllegalArgumentException {
		if(allSprites == null) 
			throw new IllegalArgumentException("Illegal argument given"); 
		
		for(Sprite i : allSprites) {
			if (i == null) {
				throw new IllegalArgumentException("Sprites are null."); } }
		
		if (allSprites.length < 10 || allSprites.length % 2 != 0) 
				throw new IllegalArgumentException("More than 10 sprites for mazub.");
		
		return true;
	}

	public void setCurrentSprite() throws IllegalArgumentException {
		
		if (isTerminated) 
			return;

		if (!isHorizontalMoving) {
			if (timeNotMoving >= 1) {
				if (!isDucking) {
					if (this.getWorld() != null && this.getWorld().validTiles(getPixelPosition().getX(),
							getPixelPosition().getY(),allSprites[0].getWidth(),
							allSprites[0].getHeight())) 
						currentSprite = getAllSprites()[0]; 
					}
				
				else if (isDucking) {
					if (isJumping) 
						currentSprite = getAllSprites()[1]; 
					else if(!isJumping) {
						currentSprite = getAllSprites()[1]; } } } 
			else if (timeNotMoving < 1){
				if (!isDucking) {
					if(lastDirection == "right") 
						currentSprite = getAllSprites()[2]; 
					else {
						currentSprite = getAllSprites()[3]; } }
				else if(isDucking) {
					currentSprite = getAllSprites()[1]; } } }	
		
		else if (isHorizontalMoving){
			if (!isJumping){
				if (!isDucking) {
					if(lastDirection == "right")  {
						if (timeMovingRight ==0) {
						this.currentSprite = getAllSprites()[8]; } } 
					else if (lastDirection == "left"){
						if (timeMovingLeft==0) {
						int i = NUMBER_OF_ALTERNATING_SPRITES/2;
						this.currentSprite = getAllSprites()[8 + i]; } }}
				else if(isDucking) {
					if(lastDirection == "right") {
						currentSprite = getAllSprites()[6]; } 
					else {
						currentSprite = getAllSprites()[7]; } } }	
			if (isJumping) {
				if (!isDucking) {
					if(lastDirection == "right") {
						currentSprite = getAllSprites()[4]; } 
					else if(lastDirection == "left") {
						currentSprite = getAllSprites()[5]; } }
				else if(isDucking) {
					if(lastDirection == "right") {
						currentSprite = getAllSprites()[6]; } 
					else {
						currentSprite = getAllSprites()[7]; } } } }
		
		if ((timeMovingRight >=0.075 || timeMovingLeft >=0.075) && (isOnGround() || !isJumping) && !isDucking) {
			//updating the sprites in case this mazub is horizontal moving while on solid ground.
			if (timeMovingRight >=0.075 && (isOnGround() || !isJumping)) { 
				boolean changed = false;
				while (timeMovingRight >= 0.075) {
					timeMovingRight -= 0.075;
				int i = 8;
				while (i < 8 + NUMBER_OF_ALTERNATING_SPRITES/2 && !changed){

					if (getCurrentSprite() == allSprites[7 + NUMBER_OF_ALTERNATING_SPRITES/2] && !isDucking) {
						currentSprite = allSprites[8];
						changed = true; }
					else if (getCurrentSprite() == allSprites[i] && !isDucking) {
						currentSprite = allSprites[i+1];
						changed = true; }
					i += 1; }
				changed = false; } }
			
			else if (timeMovingLeft>=0.075 && (isOnGround() || !isJumping)) { 
				boolean changed = false;
				while (timeMovingLeft >= 0.075) {
					timeMovingLeft -= 0.075;
				int i = 8 + NUMBER_OF_ALTERNATING_SPRITES/2;
				
				while (i  <= counter  && !changed ){
					if (getCurrentSprite() == allSprites[7 + NUMBER_OF_ALTERNATING_SPRITES] ) {
						currentSprite = allSprites[8 + NUMBER_OF_ALTERNATING_SPRITES/2 ];
						changed = true; }
					else if (getCurrentSprite() == allSprites[i] ) {
						currentSprite = allSprites[i+1];
						changed = true; }					
					i += 1;	}
				changed = false; } } }
	}
	
	public void setCurrentSpriteEndedMove() {
		if(lastDirection == "right") {
			currentSprite = getAllSprites()[2]; } 
		else if (lastDirection == "left"){
			currentSprite = getAllSprites()[3]; }
	}
	
	/**
	 * 
	 * Increase or decrease the current amount of hitpoints of this game object with a given value.
	 * 
	 * @param 	value
	 * 			The amount of hitpoints to be added to the current hitpoints of this Mazub.
	 * @post	If the current amount of hitpoints of this game objet are zero, this Mazub will be dead.
	 * 		|	if (this.getHitPoints() == 0) then
	 * 		|		new.isDead = true
	 * @post	If the new amount of hitpoints (increased by value) is less than zero, this mazub will be dead.
	 * 		|	if(value < 0 && (this.getHitPoints() - Math.abs(value) < 0)) then
	 * 		|		new.isDead = true
	 * @effect	If the new amount of hitpoints (increased by value) is equal to or
	 * 			bigger than 500, the new amount of hitpoints will be set to 500.
	 * 		|	if (value >= 0 && (this.getHitPoints() + value) >= 500)) then
	 * 		|		setHitPoints(500)
	 * @effect	If the new amount of hitpoints (increased by value) is less than zero,
	 * 			the new amount of hitpoints will be set to zero.
	 * 		|	if (this.getHitPoints() - Math.abs(value) < 0))) then
	 * 		|		setHitPoints(0)
	 * @effect	Otherwise, the amount of hitpoints is increased by value.
	 * 		|	setHitPoints(getHitPoints() + value)
	 * 
	 */
	@Override
	public void addHitPoints(double value) {
		
		if (this.getHitPoints() == 0) 
			this.isDead = true; 
		
			if (value >= 0 && (this.getHitPoints() + value) >= getMaxHitPoints()) 
				setHitPoints(getMaxHitPoints()); 
			
			else if(value < 0 && (this.getHitPoints() - Math.abs(value) < 0)) {
				setHitPoints(0);
				this.isDead = true; }
			
			else 
				setHitPoints(getHitPoints() + value); 			
	}

	//part of advancetime 
	public void setCurrentHitPoints() {	
		if(this.getHitPoints() == 0) {
			this.isDead = true;
			return;	}
		
		if (this.getWorld() != null) {
				hitPointsByGameObjects();				
			if (this.getWorld().outsideBoundaries(getPixelPosition().getX(), getPixelPosition().getY())) {
				terminate();
				return; } 
			
			if (this.isInMagma()) {				
				if(firstTimeInMagma) {				
					this.addHitPoints(-50);
					firstTimeInMagma = false;
					return; }
				else if (timeInMagma >= 0.2){
					this.addHitPoints((int) ((-250)*timeInMagma + 0.2)); 
					return; } }		
			
			if (this.isInWater() && !this.isInMagma() && !this.isInGas()) {
				if(timeInWater >= 0.2) {
					effectiveTimeInWater += timeInWater;
					while(effectiveTimeInWater >= 0.2) {		
						this.addHitPoints(-2);
						effectiveTimeInWater -= 0.2; }
					effectiveTimeInWater -= timeInWater; } } }
		
			if (this.isInGas() && !this.isInMagma()) {	
				if(firstTimeInGas) {			
					this.addHitPoints(-4);
					firstTimeInGas = false;
					return;}
				else if (timeInGas >= 0.2) {
					while (timeInGas >= 0.2) {
						this.addHitPoints(-4);
						timeInGas -= 0.2;
					}
					return;
				}
			}
		if(getHitPoints() == 0) {
			isDead = true; }		
	}
	
	//part of advancetime
	public void hitPointsByGameObjects() {

		for (Object i : getWorld().getAllGameObjects()) {			
			if (isOnTopOfOtherGameObject || this.isNextTo((GameObject) i)) {
				if (i instanceof Plant) {
						if (this.collidesWith((GameObject) i)) {	
							if (this.getHitPoints() < 500) {		
								if (i instanceof Sneezewort) {
									if (!((Sneezewort) i).isDead && !((Sneezewort) i).hasBeenEaten) {
										((Sneezewort) i).hasBeenEaten= true;
										this.addHitPoints(50); }
									else if (((Sneezewort) i).isDead && !((Sneezewort) i).eatenWhileDead) {		
										this.addHitPoints(-20);
										((Sneezewort) i).eatenWhileDead = true;			
									} 
							}
	
							else if (i instanceof Skullcab)	 {
								if (((Skullcab) i).amountOfTimesEaten<3) {
								if (((Skullcab) i).firstTimeOverlappingWithMazub) {
									
									this.addHitPoints(50);
									((Skullcab) i).firstTimeOverlappingWithMazub = false;
									((Skullcab) i).amountOfTimesEaten ++;
									return; }
								
								else if(((Skullcab) i).timeOverlappingWithMazub >= 0.6) {						
									((Skullcab) i).effectiveTimeOverlappingWithMazub += ((Skullcab) i).timeOverlappingWithMazub;
									
									while(((Skullcab) i).effectiveTimeOverlappingWithMazub >= 0.6) {
										this.addHitPoints(50);
										((Skullcab) i).amountOfTimesEaten ++;

										((Skullcab) i).effectiveTimeOverlappingWithMazub -= 0.6; }
									((Skullcab) i).effectiveTimeOverlappingWithMazub -= ((Skullcab) i).timeOverlappingWithMazub; } }}
								} 						
							} 
						}
				else if (i instanceof Slime) {
						if (this.collidesWith((GameObject) i) || isOnTopOfOtherGameObject || wouldOverlapWithSlime) {
						if (!this.isStationary()) {
							if (timeNoDamageFromSlime == 0) {
								timeNoDamageFromSlime += 0.6;
								this.addHitPoints(-20); } } } }
					
				else if (i instanceof Shark) {
					if (!isStationary())
						if (timeNoDamageFromShark == 0) {
							timeNoDamageFromShark += 0.6;
							this.addHitPoints(-50); } 
				}
			}	
		}
	}

	//part of advancetime
	@Override
	public void advanceTime(double dt) throws IllegalArgumentException {
		setSmallDt(dt);	
		if(getHitPoints() == 0) {
			isDead = true; }
		
		while(dt > 0) {
			
			isOnTopOfOtherGameObject = false;
			wouldOverlapWithSlime = false;
			wouldOverlapWithPlant = false;
			
		if (!isDead && getHitPoints() != 0) {
			
			//Changing the velocity and acceleration to the required velocity and acceleration for
			//the current movement.
			setCorrectMovement();
				
			while (dt > 0 && !isDead) {
					if (t > dt) {
						t = dt; }
				dt -= t;
				
				
				//Updating how long this mazub is in magma/water/gas
				setFluidTimers(t);
	
				if (this.getWorld() != null) {
				for (Object i : this.getWorld().getAllGameObjects()) {
					if (i instanceof Skullcab) {
						if (this.collidesWith((GameObject) i)) {
							((Skullcab) i).timeOverlappingWithMazub += t; }
						
						else {
							((Skullcab) i).timeOverlappingWithMazub = 0;
							((Skullcab) i).firstTimeOverlappingWithMazub = true; } } }  }
				
	
	
				//Updating how long this mazub is moving in a certain direction in case it is moving
				setMovementTimers(t);
	
				//Updating how long this mazub hasn't been damaged by a slime or shark.
				setNoDamageTimers(t);
				
				//Implementing all aspects concerning the horizontal and vertical movement.
				
				try {	
					
					changeActualPosition(new Position<Double>(getPosition().getX()+getHorizontalVelocity()
					*t+getHorizontalAcceleration()
					*Math.pow(t, 2)/2,
					getPosition().getY()+getVerticalVelocity()
					*t+getVerticalAcceleration()*Math.pow(t, 2)/2));
					
					setHorizontalVelocity(getHorizontalVelocity()+t*getHorizontalAcceleration());
	
					setVerticalVelocity(getVerticalVelocity()+t*getVerticalAcceleration());
					
	
				} catch (IllegalArgumentException ex) {
	
					//als zowel de horizontale verplaatsing en de verticale verplaatsing apart mogelijk zijn, maar niet
					//tesamen, dan mag mazub de beweging niet uitvoeren
		
					if (isAccessiblePosition(new Position<Double>(getPosition().getX()+getHorizontalVelocity()
					*t+getHorizontalAcceleration()
					*Math.pow(t, 2)/2,
					getPosition().getY())) && 
					isAccessiblePosition(new Position<Double>(getPosition().getX(),
					getPosition().getY()+getVerticalVelocity()
					*t+getVerticalAcceleration()*Math.pow(t, 2)/2)) 
					 
					) {
					setHorizontalVelocity(0);
	
					setVerticalVelocity(0);
					setHorizontalAcceleration(0);
					setVerticalAcceleration(0);
					setCurrentSpriteEndedMove();
					endJump();
					return; }
	
					//als maar 1 van de twee wel kan veranderen dan deze veranderen, andere niet
					else if (isAccessiblePosition(new Position<Double>(getPosition().getX()+getHorizontalVelocity()
					*t+getHorizontalAcceleration()
					*Math.pow(t, 2)/2,
					getPosition().getY()))) {
	
	
						
						if (isAccessiblePosition(new Position<Double>(getPosition().getX(),getPosition().getY()+
								getVerticalVelocity()*t+getVerticalAcceleration()*Math.pow(t, 2)/2))) {
							setVerticalAcceleration(0); }
						
						if (isOnGround() || isOnTopOfOtherGameObject) 
							setVerticalAcceleration(0);
						
						
						else if (!isOnGround()) {
							setVerticalAcceleration(STANDARD_VERTICAL_ACCELERATION);
							dt += t; 
							}
	
						setVerticalVelocity(0);
						setHorizontalVelocity(getHorizontalVelocity()+t*getHorizontalAcceleration()); 
						}
					
					else {
						if (isHorizontalMoving) {
						if (getVelocity()[0] > 0) {
							lastDirection = "right";} 
						else {
							lastDirection = "left"; }
						endMove();
						}
						setCurrentSpriteEndedMove();
	
						setVerticalVelocity(getVerticalVelocity()+t*getVerticalAcceleration()); 
						} 
					}
			
				setCurrentHitPoints();
				setCurrentSprite(); } } 
	
		else {
			DEATH_LIFETIME -= dt;
			dt = 0;
			if (DEATH_LIFETIME <= 0) {
				terminate(); } } }
		
		if(getHitPoints() == 0) {
			isDead = true; }
	} 
	
}




