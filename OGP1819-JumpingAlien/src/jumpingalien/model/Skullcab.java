package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.util.Sprite;

/**
 * A class of Skullcabs with characteristics x and y position and sprites.
 * @invar  	Each Skullcab must have a valid position.
 *       	| isValidPosition(Position position)
 * @invar	Each Skullcab must have a valid array of sprites
 * 			| isValidSprites(sprites)
 * @version version2.0
 * @author Danaë Van de Velde & Thomas Vrolix
 * 
 */
@SuppressWarnings("all")
public class Skullcab extends Plant implements VerticalMoving{

	/**
	 * Initialize this new skullcab with given x-position, given y position and given sprites.
	 * The standard horizontal velocity of a skullcab is always zero meters per second, 
	 * it can only live for 12 seconds,
	 * it only has 2 sprites and it will start moving up. 
	 * After a skullcab has died, it will stay for 0.6 seconds in its world.
	 * Skullcab possess only 3 hitpoints.
	 *
	 * @param	x
	 *			The x-coordinate of this new plant.
	 * @param	y
	 *			The y-coordinate of this new plant.
	 * @param	sprites
	 *			The sprites for this new plant.
	 * @effect 	This plant is initialized as a new plant with a given x-coordinate, y-coordinate
	 * 			and given sprites.
	 * 		|  	this(x, y, sprites)
	 */
		public Skullcab(int x, int y, Sprite...sprites) {
			super(x, y, sprites);
			setVerticalVelocity(0.5);
			maxVerticalVelocity = 0.5;
			STANDARD_HORIZONTAL_VELOCITY = 0;
			lifeTime = 12;
			DEATH_LIFETIME = 0.6;
			setHitPoints(3);
			minNumberOfSprites = 2;
			maxNumberOfSprites = 2;
			currentSprite = getAllSprites()[0];
			startMove("up");
			
		}
		

		
		/**
		 * Return the vertical velocity of this skullcab. The velocity is expressed in meters per second.
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
		 * Set the vertical velocity of this skullcab to the value.
		 * 
		 * @param 	value
		 * 		  	The new vertical velocity for this skullcab.
		 * @post	The vertical velocity of this skullcab is equal to the given value.
		 * 		|	new.getVerticalVelocity() = value
		 *  	
		 */
		@Raw
		@Override
		public void setVerticalVelocity(double value) {
			verticalVelocity = value;
		}
		
		/**
		 * Return the vertical acceleration of this skullcab. The velocity is expressed in meters per second^2.
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
		 * Set the vertical acceleration of this skullcab to the value.
		 * 
		 * @param 	value
		 * 		  	The new vertical acceleration for this skullcab.
		 * @post	The vertical acceleration of this skullcab is equal to the given value.
		 * 		|	new.getVerticalAcceleration() = value
		 *  	
		 */
		@Override
		public void setVerticalAcceleration(double value) {
			this.verticalAcceleration = value;
		}

		/**
		 * Return the velocity of this skullcab. The velocity is expressed in meters per second.
		 * 
		 * @return	The value of the velocity.
		 * 		| 	result == velocity
		 * @post	The length of the resulting list of integer values is equal to 2.
		 *		|	double[] velocity = new double[2]
		 */
		@Override
		public double[] getVelocity() {
			double[] velocity = new double[2];
			velocity[0] = 0;
			velocity[1] = this.getVerticalVelocity();
			return velocity;
		}

		/**
		 * Return the acceleration of this skullcab. The acceleration is expressed in meters per second^2.
		 * 
		 * @return	The value of the acceleration.
		 * 		| 	result == acceleration
		 *	@post	The length of the resulting list of double values is equal to 2.
		 *		|	double[] acceleration = new double[2]
		 */
		@Override
		public double[] getAcceleration() {
			double[] acceleration = new double[2];
			acceleration[0] = 0;
			acceleration[1] = this.getVerticalAcceleration();
			return acceleration;
		}
		
		/**
		 * Check whether this skullcab is vertically moving.
		 * @return result == this.getVelocity()!=0
		 */
		@Override
		public boolean isVerticalMoving() {
			return (this.getVelocity()[1] != 0); }
		

		
		/**
		 * Start moving this skullcab up or down, depending on the given direction.
		 * 
		 * @param	direction
		 * 			The direction in which this Plant has to start moving.
		 * @effect	The new vertical velocity is equal to 0.5 meters per second.
		 * 		|	new.getverticalVelocity() == 0.5
		 * @effect	The new orientation is equal to 1 if this skullcab starts moving up and -1 if this Plant starts moving down.
		 * 		|	new.getOrientation() == 1 || new.getOrientation() == -1
		 * @post	The last direction of this skull is changed to direction
		 * 		|	new.getLastDirection() == direction
		 */
		@Override
		public void startMove(String direction) {
				isVerticalMoving = true;
			if (direction == "up") {
				lastDirection = "up";
				setVerticalVelocity(0.5);
				setOrientation(1);
				currentSprite = allSprites[0]; 
				}
			else if (direction == "down") {
				lastDirection = "down";
				if (getPosition().getY() != 0) { 
					setVerticalVelocity(-0.5);
					setOrientation(-1); 
					currentSprite = allSprites[1]; } } 
		}
		
		
		double timeOverlappingWithMazub = 0;
		
		double effectiveTimeOverlappingWithMazub;
		
		boolean firstTimeOverlappingWithMazub = true;
		

		//part of advancetime
		public void setTimers(double t) {
			//Updating how long this plant is moving in a certain direction in case it is moving
			if (isVerticalMoving && getVelocity()[1] > 0) {
				
				timeMovingUp += t; }
				
			else {
				
				timeMovingUp = 0; }
			
			if (isVerticalMoving && getVelocity()[1] < 0) {
				timeMovingDown += t; }
			else {
				timeMovingDown = 0; }
			//Updating how long this plant would be overlapping with Mazub, if they are overlapping.
			
			if (this.getWorld() != null) {
				if(this.getWorld().getMazub() != null && this.collidesWithMazub())
					timeOverlappingWithMazub += t;
				else {				
					timeOverlappingWithMazub = 0;
					firstTimeOverlappingWithMazub = true; } } }
		
		@Override
		public void advanceTime(double dt) throws IllegalArgumentException {

			if (!isDead) {
				
				double t = 0.01/(0.5);
				
				while (dt > 0 && !isDead) {
					t = 0.01/0.5;
					
					if (t > dt) 
						t = dt; 	
					
					if (timeMovingUp+t > 0.5)
						t = 0.5 - timeMovingUp;
					else if (timeMovingDown + t > 0.5)
						t = 0.5 - timeMovingDown;
					
					dt -= t;
										
					if (affectLifeTime == true) {
					lifeTime -= t;}
					else {
						affectLifeTime = true; }
					
					if (lifeTime <= 0) {
						this.isDead = true;
						hitPoints = 0; }
					
					setTimers(t);

					//Implementing all aspects concerning the horizontal movement.
					setPosition(new Position(getPosition().getX(),
							getPosition().getY()+getVerticalVelocity()*t));
					
					if (timeMovingUp >= 0.5) 
						startMove("down"); 
						
					else if(timeMovingDown >= 0.5) 
						startMove("up");
						setCurrentHitPoints(); } }
			
			
			
			else {
				DEATH_LIFETIME -= dt;
				if (DEATH_LIFETIME <= 0) {
					terminate(); } } }

		//part of advancetime
		public void setCurrentHitPoints() {
				
					if (this.getWorld() != null) {
						
						if (this.getWorld().outsideBoundaries(getPixelPosition().getX(), getPixelPosition().getY())) {
							terminate();
							return; } 
						
						if (this.getWorld().getMazub() != null && this.collidesWithMazub()) {
							if (firstTimeOverlappingWithMazub || this.getHitPoints() == 3) {

								this.addHitPoints(-1);
								this.firstTimeOverlappingWithMazub = false;
								return; }

								if(timeOverlappingWithMazub >= 0.6) {
									
									effectiveTimeOverlappingWithMazub += timeOverlappingWithMazub;
									
									while(effectiveTimeOverlappingWithMazub >= 0.6) {
										
										this.addHitPoints(-1);
										
										if (this.getHitPoints() == 0)
											terminate();

										effectiveTimeOverlappingWithMazub -= 0.6; }
									
									effectiveTimeOverlappingWithMazub -= timeOverlappingWithMazub; } }
						
						if (this.getHitPoints() == 0) {
							isDead = true; }
						if (this.getHitPoints() > 3 - amountOfTimesEaten)
							setHitPoints(3-amountOfTimesEaten); } }
	}

