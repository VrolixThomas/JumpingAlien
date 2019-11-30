package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.util.Sprite;

/**
 * A class of plants as a specific game object. Plants never stop moving
 * @invar  	Each Plant must have a valid position.
 *       	| isValidPosition(Position position)
 * @invar	Each Plant must have a valid array of sprites.
 * 			| isValidSprites(sprites)
 * 
 * @version version3.0
 * @author Danaë Van de Velde & Thomas Vrolix
 * 
 */

public class Plant extends GameObject {
	/**
	 * Initialize this new plant with given x-position, given y position and given sprites.
	 * The standard vertical velocity of a plant is always zero meters per second, 
	 * it can only live for 10 seconds,
	 * it only has 2 sprites and it will start moving to the left. 
	 * After a plant has died, it will stay for 0.6 seconds in its world.
	 * Plants possess only 1 hitpoint.
	 *
	 * @param	x
	 *			The x-coordinate in pixels of this new plant.
	 * @param	y
	 *			The y-coordinate in pixels of this new plant.
	 * @param	sprites
	 *			The sprites for this new plant.
     * @effect	This new plant is initialized as a new game object
     *			given x-coordinate, y-coordinate and sprites.
     * 		|	super(x, y, sprites)
	 * @post	The minimum amount of sprites is 2.
	 *		|	new.minNumberOfSprites = 2
	 * @post	The maximum amount of sprites is 2.
	 *		|	new.maxNumberOfSprites = 2
	 * @post	The current sprite is equal to the first sprite in the list of all sprites.
	 * 		|	new.currentSprite = getAllSprites()[0]
	 */
	@Raw
	public Plant(int x, int y, Sprite...sprites) {
		super(x, y, sprites);
		minNumberOfSprites = 2;
		maxNumberOfSprites = 2;
		currentSprite = getAllSprites()[0];
		
	}
	
	/**
	 * Checking whether the sprites of this game object are valid.
	 * 
	 * @param 	AllSprites
	 * 			All the sprites of this game object.		
	 * @return	True if the sprites are valid.
	 * @throws 	IllegalArgumentException
	 * 			Sprite i == null || allSprites.length != 2
	 */
	@Override
	public boolean isValidSprites(Sprite[] allSprites) throws IllegalArgumentException {
		if(allSprites == null) {
			throw new IllegalArgumentException("Illegal argument given"); }
		
		for(Sprite i : allSprites) {
			if (i == null) {
				throw new IllegalArgumentException("Sprites are null."); } }
		
			if (allSprites.length != 2) {
				throw new IllegalArgumentException("No 2 sprites for a plant"); } 
		
		return true;
	}

	/**
	 * Check whether this plant is currently falling.
	 * @post	isFalling is changed to true, if and only if this plant has no solid ground beneath it.
	 * 		|	isFalling == !isOnGround
	 */
	protected void isFalling() {
		if (!isOnGround) {
			isFalling = true; }
		else {
			isFalling = false; }
	}

	/**
	 * Variable registering how many times this plant is eaten.
	 */
	public int amountOfTimesEaten = 0;

	/**
	 * Variable registering whether this plant is eaten while it's dead.
	 */
	public boolean eatenWhileDead = false;

	/**
	 * Variable registering the time this game object is moving up continuously.
	 */
	public double timeMovingUp;
	
	/**
	 * Variable registering the time this game object is moving down continuously.
	 */
	public double timeMovingDown;
}
