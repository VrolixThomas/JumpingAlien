package jumpingalien.model;

import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.util.Sprite;

/**
 * A class of jumping slimes with characteristics x and y position and sprites.
 * @invar  	Each Slime must have a valid position.
 *       	| isValidPosition(Position position)
 * @invar	Each Slime must have a valid array of sprites
 * 			| isValidSprites(sprites)
 * @version version 1.0
 * @author Danaë Van de Velde & Thomas Vrolix
 * 
 */
public class Slime extends GameObject implements HorizontalMoving{
	
	public Slime(long id, int x, int y, School school, Sprite...sprites) throws IllegalArgumentException{
		super(x, y, sprites);
		this.id = id;
		setMaxHorizontalVelocity(2.5);
		STANDARD_HORIZONTAL_ACCELERATION = 0.7;
		setHorizontalAcceleration(STANDARD_HORIZONTAL_ACCELERATION);
		setHitPoints(100);
		minNumberOfSprites = 2;
		maxNumberOfSprites = 2;	
		currentSprite = getAllSprites()[0];		
		startMove("right");		
		if (school != null) {
		school.addAsSlime(this);}	
		if(id < 0 && !(id != (int) id)) {
			throw new IllegalArgumentException("No valid ID.");	}	
		World.Instance.addSlimeId((int)id);
	}
	
	
	/**
	 * Return the horizontal velocity of this GameObject. The velocity is expressed in meters per second.
	 * 
	 * @return	The value of the horizontal velocity.
	 * 		| 	result == horizontalVelocity
	 */
	@Basic
	@Override
	public double getHorizontalVelocity() {
		return horizontalVelocity; }
	
	/**
	 * 			Set the horizontal velocity of this Slime
	 * @Param 	value
	 * 			The value of the new horizontal velocity of this Slime.
	 * @Post	new.horizontalVelocity = value
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
	*			Set the minimum horizontal velocity of this Slime.
	* @param 	value
	*			The new minimum horizontal velocity of this Slime.
	* @post 	The new minimum horizontal velocity of this Slime is set to value
	*		|	new.minHorizontalVelocity = value
	*/
	@Raw
	@Override
	public void setMinHorizontalVelocity(double value) {
		minHorizontalVelocity = value;
	}

	/**
	*			Set the maximum horizontal velocity of this Slime.
	* @param 	value
	*			The new maximum horizontal velocity of this Slime.
	* @post 	The new maximum horizontal velocity of this Slime is set to value
	*		|	new.maxHorizontalVelocity = value
	*/
	@Raw
	@Override
	public void setMaxHorizontalVelocity(double value) {
		maxHorizontalVelocity = value;
	}
	
	/**
	 * Return the velocity of this Slime. The velocity is expressed in meters per second.
	 * 
	 * @return	The value of the velocity.
	 * 		| 	result == double (this.getHorizontalVelocity(),0)
	 */
	@Override
	public double[] getVelocity() {
		double[] velocity = new double[2];
		velocity[0] = this.getHorizontalVelocity();
		velocity[1] = 0;
		return velocity;
	}
	
	/**
	 * Return the horizontal velocity of this Slime. The velocity is expressed in meters per second.
	 * 
	 * @return	The value of the horizontal acceleration.
	 * 		| 	result == horizontalAcceleration
	 */
	@Basic
	@Override
	public double getHorizontalAcceleration() {
		return horizontalAcceleration; }

	/**
	 * Set the horizontal acceleration of this Slime to the value.
	 * 
	 * @param 	value
	 * 		  	The new horizontal acceleration for this Slime.
	 * @post	The horizontal acceleration of this Slime is equal to the given value.
	 * 		|	new.getHorizontalAcceleration() = value	
	 */
	@Raw
	@Override
	public void setHorizontalAcceleration(double value) {
		this.horizontalAcceleration = value; }

	/**
	 * Return the acceleration of this Slime. The acceleration is expressed in meters per second^2.
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
		acceleration[1] = 0;
		return acceleration;
	}
	
	/**
	 * Returns if this slime is not moving horizontally.
	 * @return result == getHorizontalVelocity() == 0 && getHorizontalAcceleration() == 0
	 */
	public boolean isStationary() {
		return (getHorizontalVelocity() == 0 && getHorizontalAcceleration() == 0);
	}
	
	/**
	 * Check whether this newPosition is an accessible position for this Slime.
	 * Return 	result == newPositionedSlime.isOverlappingWithMazub(gameObjectsCopy)
	 * 						&& !this.getWorld().validTiles(x, y, width, height)
	 * 						&& newPositionedSlime.isOverlappingWithGameObject(gameObjectsCopy)
	 * @post	new.wouldOverlapWithSlime = true if newPositionedShark.isOverlappingWithSlime(gameObjectsCopy)
	 * @post	new.wouldOverlapWithMazub = true if newPositionedShark.isOverlappingWithMazub(gameObjectsCopy)
	 */
	@Override
	public boolean isAccessiblePosition(Position<Double> newPosition) {
		
		int x = getPixelPosition(newPosition).getX();
		int y = getPixelPosition(newPosition).getY();
		int width = getCurrentSprite().getWidth();
		int height = getCurrentSprite().getHeight();
		
			GameObject newPositionedSlime = new GameObject(x, y, 
					 this.getAllSprites());		
			newPositionedSlime.currentSprite = this.getCurrentSprite();
			Set<Object> gameObjectsCopy = this.getWorld().getAllGameObjects();
			gameObjectsCopy.remove(this);

			if (newPositionedSlime.isOverlappingWithMazub(gameObjectsCopy)) 
				wouldOverlapWithMazub = true;	
			
			if (newPositionedSlime.isOverlappingWithSlime(gameObjectsCopy)) 
				wouldOverlapWithSlime = true;

			if(!this.getWorld().validTiles(x, y, width, height)) 
				return false;
			
			if (newPositionedSlime.isOverlappingWithGameObject(gameObjectsCopy)) 
				return false;		
			
			return true; 	
	}
	
	/**
	 * Changes the position of this Slime to a new position
	 * @param	newPosition
	 * 			The new Position for this Slime.
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
		
		
			if (allSprites.length != 2) 
				throw new IllegalArgumentException("No 2 sprites for a slime"); 
		return true;
	}
	
	/**
	 * Variable registering the amount of time this slime will not receive damage from mazub.
	 */
	private double timeNoDamageFromMazub;
	
	/**
	 * Return the id of this Slime
	 * @return result == this.id
	 */
	@Basic
	public long getId() {
		return id;
	}
	
	/**
	 * Variable registering the id of this slime.
	 */
	private final long id;

	/**
	 * Starts the horizontal movement for this Slime.
	 * @param	direction
	 * 			The direction this Slime has to start moving.
	 * @post	new.isHorizontalMoving == true
	 * @post	if direction == right then new.lastDirection == right, else new.lastDirection == left
	 * @effect	setHorizontalVelocity(0)
	 * @effect	if direction == right then setHorizontalAcceleration(STANDARD_HORIZONTAL_ACCELERATION);
	 *			else setHorizontalAcceleration(-STANDARD_HORIZONTAL_ACCELERATION);
	 *
	 */
	@Override
	public void startMove(String direction) {
		isHorizontalMoving = true;
	if (direction == "right") {
		lastDirection = "right";
		setHorizontalVelocity(0);
		setHorizontalAcceleration(STANDARD_HORIZONTAL_ACCELERATION);
		setOrientation(1);
		currentSprite = allSprites[0]; 
		}
	else if (direction == "left") {
		lastDirection = "left";
		if (getPosition().getX() != 0) { 
			setHorizontalVelocity(0);
			setHorizontalAcceleration(-STANDARD_HORIZONTAL_ACCELERATION);
			setOrientation(-1); 
			currentSprite = allSprites[1]; } } 
	}
	
	public void reverseDirection() {
		if (lastDirection == "right") {
			startMove("left");
		}
		else if (lastDirection == "left") {
			startMove("right");
		}
		
	}
	public School getCurrentSchool() {
		return currentSchool;
	}

	public void setCurrentSchool(School currentSchool) {
		this.currentSchool = currentSchool;
	}
	
	public boolean isInSchool() {
		return(this.getCurrentSchool() != null);
	}
	
	public void switchSchool(School school) throws IllegalArgumentException {
		if (getCurrentSchool() == null) {
			throw new IllegalArgumentException("Has no school.");
		}
		
		
		for (Slime i: this.getCurrentSchool().getAllSlimes()) {
			if(i != this) {
				i.addHitPoints(1);
				this.addHitPoints(-1);} }

		this.getCurrentSchool().removeSlime(this);
		school.addAsSlime(this);
		
		for (Slime i: this.getCurrentSchool().getAllSlimes()) {
			if(i != this) {
				i.addHitPoints(-1);
				this.addHitPoints(1);} }
		
		
	}

	private School currentSchool;

	@Override
	public void terminate() {
		if (this.getWorld()!=null) {	
			this.getWorld().removeGameObject(this); }
		isDead = true;
		isTerminated = true;
		if (isInSchool()) {
			getCurrentSchool().removeSlime(this); }
		World.Instance.removeSlimeId((int) id );	
	}
	
	public void setCurrentSprite() {
		if (this.getVelocity()[0] > 0) 
			currentSprite = getAllSprites()[0];
		else if (this.getVelocity()[0] < 0) 
			currentSprite = getAllSprites()[1];	
	}
	
	public void setCurrentHitPoints() {
	
	
	if (this.getWorld() != null) {
		hitPointsByGameObjects(); }
		
		if(this.getHitPoints() == 0) {
			this.isDead = true;
			return;	}
		
		if (this.getWorld() != null) {
				
			if (this.getWorld().outsideBoundaries(getPixelPosition().getX(), getPixelPosition().getY())) {
				terminate();
				return; } 
			
			if (this.isInMagma()) {
				this.terminate();
	
					return; }
			
			if (this.isInWater()) {
				if(timeInWater >= 0.4) {
					while(timeInWater >= 0.4) {
						this.addHitPoints(-4);
						reduceHitPointsSchool();
						timeInWater -= 0.4; } } } 
		
			if (this.isInGas()) {
				if (timeInGas >= 0.3) {
					while (timeInGas >= 0.3) {
						this.addHitPoints(2);
						timeInGas -= 0.3; } } } }
	
		
	}

	public void hitPointsByGameObjects() {
		
		for (Object i:this.getWorld().getAllGameObjects()) {		
			if (i instanceof Slime && i != this) {
				if ((this.collidesWith((Slime) i ) || wouldOverlapWithSlime) && !reversedDirection) {
					
					if(!this.isStationary()) {
					
					this.reverseDirection();				
					reversedDirection = true;
	
					if(this.isInSchool() && ((Slime) i).isInSchool()) {
						if (this.getCurrentSchool().getAllSlimes().size() < ((Slime) i).getCurrentSchool().getAllSlimes().size()) {
							this.switchSchool(((Slime) i).getCurrentSchool()); } } }
				}	
			}
			if (i instanceof Mazub && i != this) {
				
				if ((this.isNextTo((GameObject) i) || wouldOverlapWithMazub)&& ((Mazub) i).firstTimeOverlappingWithSlime) {
					if (!this.isStationary()) {
						if (timeNoDamageFromMazub == 0) {
							timeNoDamageFromMazub += 0.6;
						((Mazub) i).firstTimeOverlappingWithSlime = false;
						this.addHitPoints(-30);
						reduceHitPointsSchool();
						}
					else {
						((Mazub) i).firstTimeOverlappingWithSlime = true; } } } }
		}
	}

	public void reduceHitPointsSchool () {
		if (this.getCurrentSchool() != null) {
			for (Slime i : this.getCurrentSchool().getAllSlimes() ) {
				if (this != i) {
					i.addHitPoints(-1); } } }
	}
	
	public boolean reversedDirection = false;
	
	@Override
	public void advanceTime(double dt) throws IllegalArgumentException {
		if (!isDead) {
			reversedDirection = false;
			
		double t = 0.01/(0.5);
		
		while (dt>0 && !isDead) {
			
			wouldOverlapWithMazub = false;
			wouldOverlapWithSlime = false;
				
			if (t > dt) {
				t = dt; }
			
		dt -= t;
		
		if (timeNoDamageFromMazub - t < 0) {
			timeNoDamageFromMazub = 0;
		}
		else {
			timeNoDamageFromMazub -= t;
		}
		
		if(this.isInMagma()) {
			terminate();
			return;}
		else {
			timeInMagma = 0; }
		if(this.isInWater() && !(this.isInGas() || this.isInMagma()))   {
			timeInWater += t; }
		else {
			timeInWater = 0;
			 }
		if(this.isInGas()) {
			timeInGas += t; }
		else {
			timeInGas = 0; }
		

		try {
		changeActualPosition(new Position<Double>(getPosition().getX()+getHorizontalVelocity()*t + getHorizontalAcceleration()
		*Math.pow(t, 2)/2,
		getPosition().getY()));
		
		setHorizontalVelocity((getHorizontalVelocity()+t*getHorizontalAcceleration()));
		
		} catch (IllegalArgumentException ex) {		
			setHorizontalVelocity(0);
		}
		setCurrentHitPoints(); 
		setCurrentSprite();
		
		if (this.getWorld() != null) {
			if (this.getWorld().outsideBoundaries(getPixelPosition().getX(), getPixelPosition().getY())) {
				terminate();
				return; } } } }
		
		}
}

