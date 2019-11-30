package jumpingalien.model;

import java.util.Arrays;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.util.Sprite;

/**
 * A class of Sharks with characteristics x and y position and sprites.
 * @invar  	Each Shark must have a valid position.
 *       	| isValidPosition(Position position)
 * @invar	Each Shark must have a valid array of sprites
 * 			| isValidSprites(sprites)
 * @version version 1.0
 * @author Danaë Van de Velde & Thomas Vrolix
 * 
 */
@SuppressWarnings("all")
public class Shark extends GameObject implements HorizontalMoving, VerticalMoving{
	
	/**
	 * Initialize this Shark with given x-coordinate, y-coordinate in pixels and sprites.
	 *
	 * @param	x
	 *			The x-coordinate in pixels of this new Shark.
	 * @param	y
	 * 			The y-coordinate in pixels of this new Shark.
	 * @param	sprites
	 *			The sprites for this new Shark.
	 * @post	The standard horizontal acceleration is 1.5 meters per second.
	 * 		|	new.STANDARD_HORIZONTAL_ACCELERATION == 1.5
	 * @effect	This new Shark is initialized as a new game object with
	 *			given x-position, y-position and given sprites.
	 *		|	super(owner,value)
	 * @effect	The beginning amount of hitpoints is set to 100.
	 *		|	setHitPoints(100);
	 */
	public Shark(int x, int y, Sprite... sprites) throws IllegalArgumentException {
		super(x, y, sprites);
		STANDARD_HORIZONTAL_ACCELERATION = 1.5;
		currentSprite = getAllSprites()[0];
		setHitPoints(100);
	}
	
	/**
	 * Return the horizontal velocity of this Shark. The velocity is expressed in meters per second.
	 * 
	 * @return	The value of the horizontal velocity.
	 * 		| 	result == horizontalVelocity
	 */
	@Basic
	@Override
	public double getHorizontalVelocity() {
		return horizontalVelocity; }
	
	/**
	 * 	Set the horizontal velocity of this Shark
	 * @Param 	value
	 * 			The value of the new horizontal velocity of this Shark.
	 * @Post	new.horizontalVelocity = value
	 */
	@Override
	public void setHorizontalVelocity(double value) {
		horizontalVelocity = value; 
}
	
	/**
	*	Set the minimum horizontal velocity of this Shark.
	* @param 	value
	*			The new minimum horizontal velocity of this Shark.
	* @post 	The new minimum horizontal velocity of this Shark is set to value
	*		|	new.minHorizontalVelocity = value
	*/
	@Raw
	@Override
	public void setMinHorizontalVelocity(double value) {
		minHorizontalVelocity = value;
	}

	/**
	*	Set the maximum horizontal velocity of this Shark.
	* @param 	value
	*			The new maximum horizontal velocity of this Shark.
	* @post 	The new maximum horizontal velocity of this Shark is set to value
	*		|	new.maxHorizontalVelocity = value
	*/
	@Raw
	@Override
	public void setMaxHorizontalVelocity(double value) {
		maxHorizontalVelocity = value;
	}
	
	/**
	 * Return the vertical velocity of this Shark. The velocity is expressed in meters per second.
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
	 * Set the vertical velocity of this Shark to the value.
	 * 
	 * @param 	value
	 * 		  	The new vertical velocity for this Shark.
	 * @post	The vertical velocity of this game object is equal to the given value.
	 * 		|	new.getVerticalVelocity() = value
	 *  	
	 */
	@Raw
	@Override
	public void setVerticalVelocity(double value) {
		verticalVelocity = value;
	}
	
	/**
	 * Return the velocity of this Shark. The velocity is expressed in meters per second.
	 * 
	 * @return	The values of the horizontal and vertical velocity of this Shark.
	 * 		| 	result == velocity
	 */
	@Override
	public double[] getVelocity() {
		double[] velocity = new double[2];
		velocity[0] = this.getHorizontalVelocity();
		velocity[1] = this.getVerticalVelocity();
		return velocity;
	}	
	
	/**
	 * Return the horizontal velocity of this Shark. The velocity is expressed in meters per second.
	 * 
	 * @return	The value of the horizontal acceleration.
	 * 		| 	result == horizontalAcceleration
	 */
	@Basic
	@Override
	public double getHorizontalAcceleration() {
		return horizontalAcceleration; }
	
	/**
	 * Set the horizontal acceleration of this Shark to the value.
	 * 
	 * @param 	value
	 * 		  	The new horizontal acceleration for this Shark.
	 * @post	The horizontal acceleration of this Shark is equal to the given value.
	 * 		|	new.getHorizontalAcceleration() = value	
	 */
	@Raw
	@Override
	public void setHorizontalAcceleration(double value) {
		this.horizontalAcceleration = value; }

	/**
	 * Return the vertical velocity of this Shark. The velocity is expressed in meters per second.
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
	 * Set the vertical acceleration of this Shark to the value.
	 * 
	 * @param 	value
	 * 		  	The new vertical acceleration for this Shark.
	 * @post	The vertical acceleration of this Shark is equal to the given value.
	 * 		|	new.getVerticalAcceleration() = value
	 *  	
	 */
	@Override
	public void setVerticalAcceleration(double value) {
		this.verticalAcceleration = value;
	}

	/**
	 * Return the acceleration of this Shark. The acceleration is expressed in meters per second^2.
	 * 
	 * @return	The value of the acceleration.
	 * 		| 	result == acceleration
	 */
	@Override
	public double[] getAcceleration() {
		double[] acceleration = new double[2];
		acceleration[0] = this.getHorizontalAcceleration();
		acceleration[1] = this.getVerticalAcceleration();
		return acceleration;
	}
	
	/**
	 * Check whether this Shark is vertically moving.
	 * @return result == this.getVelocity()!=0
	 */
	@Override
	public boolean isVerticalMoving() {
		return (this.getVelocity()[1] != 0); }

	
	/**
	 * Check whether this newPosition is an accessible position for this Shark.
	 * Return 	result == newPositionedShark.isOverlappingWithMazub(gameObjectsCopy)
	 * 						&& !this.getWorld().validTiles(x, y, width, height)
	 * 						&& newPositionedShark.isOverlappingWithGameObject(gameObjectsCopy)
	 * @post	new.wouldOverlapWithSlime = true if newPositionedShark.isOverlappingWithSlime(gameObjectsCopy)
	 * @post	new.wouldOverlapWithMazub = true if newPositionedShark.isOverlappingWithMazub(gameObjectsCopy)
	 */
	@Override
	public boolean isAccessiblePosition(Position<Double> newPosition) {
		
		int x = getPixelPosition(newPosition).getX();
		int y = getPixelPosition(newPosition).getY();
		int width = getCurrentSprite().getWidth();
		int height = getCurrentSprite().getHeight();
		
			GameObject newPositionedShark = new GameObject(x, y, this.getAllSprites());		
			newPositionedShark.currentSprite = this.getCurrentSprite();
			Set<Object> gameObjectsCopy = this.getWorld().getAllGameObjects();
			gameObjectsCopy.remove(this);

			if (newPositionedShark.isOverlappingWithSlime(gameObjectsCopy)) 
				wouldOverlapWithSlime = true;
			
			if (newPositionedShark.isOverlappingWithMazub(gameObjectsCopy)) 
				wouldOverlapWithMazub = true;
			
			if (newPositionedShark.isOverlappingWithMazub(gameObjectsCopy)) 	
				return false;
			
			if(!this.getWorld().validTiles(x, y, width, height)) 
				return false;

			if (newPositionedShark.isOverlappingWithGameObject(gameObjectsCopy)) 
				return false;		
			
			return true; 		
	}
	
	/**
	 * Changes the position of this Shark to a new position
	 * @param	newPosition
	 * 			The new Position for this Shark.
	 * @effect 	setPosition(newPosition)
	 * @effect	if positionOutsideWorld(newPosition) then terminate()
	 * @throws	IllegalArgumentException
	 * 			Double.isNaN(newPosition.getX()) || Double.isNaN(newPosition.getY())
	 * @throws	IllegalArgumentException
	 * 			! validTiles(newPosition)
	 * @throws	IllegalArgumentException
	 * 			!isAccessiblePosition(newPosition)
	 */
	@Override
	public void changeActualPosition(Position<Double> newPosition) throws IllegalArgumentException {
		if (Double.isNaN(newPosition.getX()) || Double.isNaN(newPosition.getY())) {
			throw new IllegalArgumentException("Cannot be NaN."); }
		
		if (getWorld() == null) {
			setPosition(newPosition);
			return;	}

		if (!getWorld().validTiles((int) (newPosition.getX()*100), 
				(int) (newPosition.getY()*100), getCurrentSprite().getWidth(), getCurrentSprite().getHeight() )) {
			throw new IllegalArgumentException("Cannot be changed to a place with solid ground or ice.");}
		
		else {
			if (getWorld().positionOutsideWorld(newPosition)) {
				terminate();
				return; }

			if(!isAccessiblePosition(newPosition)) 
				throw new IllegalArgumentException("Cannot be overlapping."); 
			}
		setPosition(newPosition); }
	
	
	@Override
	public boolean isValidSprites(Sprite[] allSprites) throws IllegalArgumentException {
		if(allSprites == null) {
			throw new IllegalArgumentException("Illegal argument given"); }
		
		for(Sprite i : allSprites) {
			if (i == null) {
				throw new IllegalArgumentException("Sprites are null."); } }
		
		if (allSprites.length != 3) 
			throw new IllegalArgumentException("No 3 sprites for a shark."); 
		
		return true;
	}
	
	/**
	 * Starts the horizontal movement for this Shark.
	 * @param	direction
	 * 			The direction this Shark has to start moving.
	 * @post	new.isHorizontalMoving == true
	 * @post	if direction == right then new.lastDirection == right, else new.lastDirection == left
	 * @effect	setHorizontalVelocity(0)
	 * @effect	if direction == right then setHorizontalAcceleration(STANDARD_HORIZONTAL_ACCELERATION);
	 *			else setHorizontalAcceleration(-STANDARD_HORIZONTAL_ACCELERATION);
	 */
	@Override
	public void startMove(String direction) {
		
		if (direction == "right") {
			isHorizontalMoving = true;
			lastDirection = "right";
			setHorizontalAcceleration(STANDARD_HORIZONTAL_ACCELERATION);
			setOrientation(1);
			currentSprite = allSprites[2]; 
			setHorizontalVelocity(0);
	
		}
		else if (direction == "left") {
			isHorizontalMoving = true;
			lastDirection = "left";
				setHorizontalAcceleration(-STANDARD_HORIZONTAL_ACCELERATION);
				setOrientation(-1); 
				currentSprite = allSprites[1];
				setHorizontalVelocity(0);
		} 
	}
	
	/**
	 * Starts the resting period for this Shark.
	 * @post 	new.timeResting == 1
	 * @effect	setHorizontalAcceleration(0)
	 * @effect	setHorizontalVelocity(0);
	 * 
	 */
	public void startRest() {
		timeResting = 1;
		setHorizontalAcceleration(0);
		setHorizontalVelocity(0);
		currentSprite = getAllSprites()[0];
	}
	
	/**
	 * Starts jumping for this Shark.
	 * @effect	if isInWater() or isOnGround then setVerticalVelocity(2)
	 * @effect	if isInWater() or isOnGround then setVerticalAcceleration(STANDARD_VERTICAL_ACCELERATION)
	 *
	 * @throws 	IllegalArgumentException
	 * 			isDead
	 */
	public void startJump() throws IllegalArgumentException {
		if (isDead) {
			throw new IllegalArgumentException("Shark is dead!"); }
			
		if (isInWater() || isOnGround()) {
			setVerticalVelocity(2);
			setVerticalAcceleration(STANDARD_VERTICAL_ACCELERATION); 
		}
	}
	
	/**
	 * Ends the jump for this Shark.
	 * @effect if getVerticalVelocity() > 0 then setVerticalVelocity(0)

	 */
	public void endJump() {
		if (getVerticalVelocity() > 0) {
			setVerticalVelocity(0);
		}
	}
	

	/**
	 * Places this Shark on top of solid ground incase it is inside solid ground.
	 * 
	 */
	public void placeOnTopOfGround() {
		while (this.isOnGround()) {
			setPosition(new Position(getPosition().getX(), getPosition().getY() + 0.01 ));
		}
		setPosition(new Position(getPosition().getX(), getPosition().getY() - 0.01));
 	}
	
	/**
	 * @param value
	 * 			The amount by which the hitpoints have to be increased
	 * @effect	setHitPoints(getHitPoints() + value)
	 * @effect	if new.getHitpoints <= 0 then setHitpoints(0) 
	 * @post	if new.getHitpoints <= 0 then new.isDead == true
	 */
	@Override 
	public void addHitPoints(double value) {
		setHitPoints(getHitPoints() + value); 

		if (this.getHitPoints() <= 0) {
			setHitPoints(0);
			this.isDead = true; }
	}
	
	//part of advancetime
	public void setCurrentHitPoints() {
		
		if (this.getWorld() != null) {
			hitPointsByGameObjects(); }
		
		if(this.getHitPoints() == 0) {
			this.isDead = true;
			return;	}
		
		if (!this.isInWater()) {
				if(timeOutOfWater >= 0.2) {
					while(timeOutOfWater >= 0.2 && !isDead) {
						this.addHitPoints(-6);
						timeOutOfWater -= 0.2; } } }
	}
	
	//part of advancetime
	public void hitPointsByGameObjects() {
		
		for (Object i : getWorld().getAllGameObjects()) {
	
			Set<Object> gameObjectsCopy = new HashSet<Object>();
			gameObjectsCopy.add((Object) i);
		
				if (i instanceof Slime && i != this) {					
					if ( this.collidesWith((GameObject) i)  || this.isNextTo((GameObject) i) || wouldOverlapWithSlime) {
						this.addHitPoints(10); } }
				if (i instanceof Mazub && i != this) {				
					if ((this.isNextTo((GameObject) i) || wouldOverlapWithMazub)  && ((Mazub) i).firstTimeOverlappingWithShark) {
							if (timeNoDamageFromMazub == 0) {
								timeNoDamageFromMazub += 0.6;
							((Mazub) i).firstTimeOverlappingWithShark = false;
							this.addHitPoints(-50);
							}
						else {
							((Mazub) i).firstTimeOverlappingWithShark = true; } } }
		}
	}
	
	/**
	 * Variable registering how much longer this Shark is resting.
	 */
	private double timeResting = 0;
	
	/**
	 * Variable registering how much longer this Shark's movement is blocked by colliding with a slime previously
	 */
	public double timeBlockedBySlime;
	
	/**
	 * Variable registering how much longer this Shark will not be damaged by Mazub.
	 */
	private double timeNoDamageFromMazub;
	
	
	public boolean firstTimeOutOfWater = true;
	
	/**
	 * Variable registering how long this Shark is out of the water.
	 */
	public double timeOutOfWater;
	
	public double effectiveTimeOutOfWater;
	
	//part of advancetime
	public void setSmallDt(double dt) {

		t = 0.01/((Math.sqrt(Math.pow(Math.abs(getHorizontalVelocity()), 2)
				+ Math.pow(Math.abs(getVerticalVelocity()), 2) )) + 	
				((Math.sqrt(Math.pow(Math.abs(getHorizontalAcceleration()), 2)
						+ Math.pow(Math.abs(getVerticalAcceleration()), 2) )) * dt));	
	}
	
	//part of advancetime
	public void setCorrectMovement() {
		
		if (!submergedInWater()) {
			setVerticalAcceleration(STANDARD_VERTICAL_ACCELERATION);}
	
		if (isOnGround()) {
			placeOnTopOfGround(); }
		
		if ((submergedInWater() && getVelocity()[1] < 0) || (isOnGround() && getVelocity()[1] < 0 )) {			
			setVerticalVelocity(0);
			setVerticalAcceleration(0); }

		if(isDoneJumping) {
			endJump(); }
		
	}
	
	//hoort bij advancetime
	public void setPeriodTimers(double t, double dt) {
		if (timeMovingRight + t > 0.5) {
			
			t_copy = 0.5 - timeMovingRight;
			isDoneJumping = true;
		}
		else if (timeMovingLeft + t > 0.5) {
			t_copy = 0.5 - timeMovingLeft;
			isDoneJumping = true;

		}
		else if (timeResting != 0 && timeResting - t < 0) {
			t_copy = timeResting;
		}
		else {
			t_copy = t;
		}
		
		if (t_copy > dt) {
			t_copy = dt; }
	}
	
	//part of advancetime
	public void setFluidTimers(double t) {
		
		if(!this.isInWater())   
			timeOutOfWater += t_copy; 
		else 
			timeOutOfWater = 0;
			
	}
	
	//part of advancetime
	public void setMovementTimers() {
		
		if (timeResting == 0 && timeMovingRight == 0 && timeMovingLeft == 0) {
			if (lastDirection == null || lastDirection == "right" ) {
				startMove("left"); }
			
			else if (lastDirection == "left") {
				startMove("right"); }
			
			startJump(); }

		if (isHorizontalMoving && (getVelocity()[0] > 0 || getAcceleration()[0] > 0)) {
			timeMovingRight += t_copy; }
		
		else {
			timeMovingRight = 0; }
		
		if (isHorizontalMoving && (getVelocity()[0] < 0 || getAcceleration()[0] < 0)) {
			timeMovingLeft += t_copy; }
		
		else {
			timeMovingLeft = 0; }
		
		if (getVelocity()[0] == 0) {
			if (timeResting - t_copy <= 0) {
				timeResting = 0;
			}
			else {
			timeResting -= t_copy;}
		}
		
		if (timeNoDamageFromMazub - t_copy < 0) {
			timeNoDamageFromMazub = 0;
		}
		else {
			timeNoDamageFromMazub -= t_copy;
		}
		
		if (timeResting == 0) {
			if (timeMovingLeft >= 0.5) {
				timeResting += 1;
				startRest();
				 }
			else if(timeMovingRight >= 0.5) {
				timeResting += 1;
				startRest();	 
		
			} 
		}
	}
	
	//part of advancetime
	double t_copy;
	
	protected boolean isDoneJumping = false;

	@Override
	public void advanceTime(double dt) throws IllegalArgumentException {
		//Checking if a valid t is given and corrected in case needed.	

		if (!isDead) {		
			setSmallDt(dt);
			if (t > dt) {
				t = dt; }

		while (dt > 0 && !isDead) {
			
			wouldOverlapWithMazub = false;
			
			wouldOverlapWithSlime = false;
			
			isDoneJumping = false;
			
			setPeriodTimers(t, dt);
		
			dt -= t_copy;
	
			setMovementTimers();		
	
			setFluidTimers(t_copy);
			
			setCorrectMovement();
	
			
			try {
				changeActualPosition(new Position(getPosition().getX()+getHorizontalVelocity()*t_copy + getHorizontalAcceleration()
				*Math.pow(t_copy, 2)/2,
				getPosition().getY()+getVerticalVelocity()
				*t_copy+getVerticalAcceleration()*Math.pow(t_copy, 2)/2));
	
				setHorizontalVelocity((getHorizontalVelocity()+t_copy*getHorizontalAcceleration()));
				setVerticalVelocity(getVerticalVelocity()+t_copy*getVerticalAcceleration());
			
				}catch (IllegalArgumentException ex) {
						
					//als zowel de horizontale verplaatsing en de verticale verplaatsing apart mogelijk zijn, maar niet
					//tesamen, dan mag mazub de beweging niet uitvoeren
					if (isAccessiblePosition(new Position(getPosition().getX()+getHorizontalVelocity()
					*t_copy+getHorizontalAcceleration()
					*Math.pow(t_copy, 2)/2,
					getPosition().getY())) && 
					isAccessiblePosition(new Position(getPosition().getX(),
					getPosition().getY()+getVerticalVelocity()
					*t_copy+getVerticalAcceleration()*Math.pow(t_copy, 2)/2)) 
					) {
					
						setHorizontalVelocity(0);
						setVerticalVelocity(0);
						setVerticalAcceleration(0);
					
					return; }
					
					
					//als maar 1 van de twee wel kan veranderen dan deze veranderen, andere niet
					else if (isAccessiblePosition(new Position(getPosition().getX()+getHorizontalVelocity()
					*t_copy+getHorizontalAcceleration()
					*Math.pow(t_copy, 2)/2,
					getPosition().getY()))) {
	
						if (!isOnGround()) {
							setVerticalAcceleration(STANDARD_VERTICAL_ACCELERATION);
							}
						
						else if (isAccessiblePosition(new Position(getPosition().getX(),getPosition().getY()+
								getVerticalVelocity()*t_copy+getVerticalAcceleration()*Math.pow(t_copy, 2)/2))) {
							setVerticalAcceleration(0); }
						
						if (isOnGround()) {
							setVerticalAcceleration(0); }
						
						setVerticalVelocity(0);
						setHorizontalVelocity(getHorizontalVelocity()+t_copy*getHorizontalAcceleration()); }
					
					else {
						setHorizontalVelocity(0);
						setVerticalVelocity(getVerticalVelocity()+t_copy*getVerticalAcceleration()); }
				}
			setCurrentHitPoints(); } }
	}

	
}

