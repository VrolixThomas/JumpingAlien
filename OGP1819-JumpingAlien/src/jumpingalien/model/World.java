package jumpingalien.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of rectangular game worlds that are composed of a fixed number of x times y adjointly positioned,
 * non-overlapping pixels.
 * @invar	outsideBoundaries(int pixelX,int pixelY)
 * @invar	isValidX(int x)
 * @invar	isValidY(int y)
 * @invar	isValid(int x, int y)
 * @invar 	pixelPositionInWorld(Position<Integer> pixelPosition)
 * @invar	positionOutsideWorld(Position<Double> position) 
 * @invar	outsideBoundaries(int pixelX,int pixelY)
 * @invar	boolean isValidWindow()
 * 
 * @version version3.0
 * @author Danaë Van de Velde & Thomas Vrolix
 * 
 */
@SuppressWarnings("all")
public class World {
	
	
	/**
	 * Initialize this world with given tilesize, number of x and y tiles, targettile coordinates, 
	 * visible window width and height and it's geological features.
	 * 
	 * @param 	tileSize
	 * 			The tile size of this world.	
	 * @param 	nbTilesX
	 * 			The number of tiles on the horizontal axis for this world.
	 * @param 	nbTilesY
	 * 			The number of tiles on the vertical axis for this world.
	 * @param 	targetTileCoordinate
	 * 			The coordinate for the targettile of this world.
	 * @param 	visibleWindowWidth
	 * 			The width for the visible window of this world.
	 * @param 	visibleWindowHeight
	 * 			The height for the visible window of this world.
	 * @param 	geologicalFeatures
	 * 			The geological features for this world.
	 * @post	Instance == this
	 * @post	new.NB_X_TILES == nbTilesX
	 * @post	new.NB_Y_TILES == nbTilesY
	 * @post	new.getVisibleWindowDimension() == { visibleWindowWidth, visibleWindowHeight }
	 * @effect	this.setTiles(Math.abs(nbTilesX),Math.abs(nbTilesY),geologicalFeatures);	
	 * @effect 	this.setTileLenght(tileSize)
	 * @effect 	this.setTargetTile(targetTileCoordinate)
	 * @effect 	this.setSizeInPixels(Math.abs(nbTilesX),Math.abs(nbTilesY),Math.abs(tileSize));
	 * @throws 	IllegalArgumentException
	 * 			if !isValidWindow()
	 */
	public World(int tileSize, int nbTilesX, int nbTilesY, int[] targetTileCoordinate,
			int visibleWindowWidth, int visibleWindowHeight, int... geologicalFeatures) throws IllegalArgumentException {
		Instance = this;	
		setTiles(Math.abs(nbTilesX),Math.abs(nbTilesY),geologicalFeatures);
		setTileLength(Math.abs(tileSize));
		setTargetTile(targetTileCoordinate);
		NB_X_TILES = Math.abs(nbTilesX);
		NB_Y_TILES = Math.abs(nbTilesY);		
		setSizeInPixels(Math.abs(nbTilesX),Math.abs(nbTilesY),Math.abs(tileSize));
		VISIBLE_WINDOW_HEIGHT = visibleWindowHeight;
		VISIBLE_WINDOW_WIDTH = visibleWindowWidth;
		MAX_Y_POSITION = NB_Y_TILES* getTileLength();
		MAX_X_POSITION = NB_X_TILES*getTileLength();
		if(!isValidWindow()) {
			throw new IllegalArgumentException("Your world is too small!"); }
	}
	
	/**
	 * Variable registering the maximum number of game objects this world can have.
	 */
	private int MAX_OBJECTS = 100; 
	
	/**
	 * Start the game with this world.
	 *  
	 * @post 	isStarted = true
	 * @throws 	IllegalArgumentException
	 * 			this.getMazub() == null
	 */
	public void startGame() throws IllegalArgumentException {
		if (!mazubAdded) 
			throw new IllegalArgumentException("Atleast one mazub must have been added to the game."); 
		isStarted = true;
	}
	
	/**
	 * Variable registering if the game has started.
	 */
	private boolean isStarted;
	
	
	/**
	 * Check whether the given x-coordinate is a valid x-coordinate.
	 * 
	 * @param 	x
	 * 			The x-coordinate to check.
	 * 	
	 * @return	True if and only if the given x-coordinate is a valid x-coordinate and
	 * 			if the given x-coordinate is greater than or equal to the minimum x-coordinate and smaller than
	 * 			the maximum x-coordinate.
	 * 		||	result == (isValidCoordinate(x)
	 *			&& (x >= minXPosition) 
	 *			&& (x < maxXPosition))
	 */
	public boolean isValidX(int x) {
		return ((x >= MIN_X_POSITION) 
				&& (x < MAX_X_POSITION) );
	}
	
	/**
	 * Check whether the given y-coordinate is a valid y-coordinate.
	 * 
	 * @param 	y
	 * 			The y-coordinate to check.
	 * 	
	 * @return	True if and only if the given y-coordinate is a valid y-coordinate and
	 * 			if the given y-coordinate is greater than or equal to the minimum y-coordinate and smaller than
	 * 			the given y-coordinate.
	 * 		||	result = (isValidCoordinate(y) 
	 *			&& (y >= minYPosition) 
	 *			&& (y < maxYPosition)
	 */
	public boolean isValidY(int y) {
		return ((y >= MIN_Y_POSITION) 
				&& (y < MAX_Y_POSITION) );
	}
	
	/**
	 * Return whether the given coordinates are valid coordinates.
	 * 
	 * @return 	True if and only if the given x and y-coordinate are valid coordinates.
	 * 		|	result == 	!isValidX(getX()) || !isValidY(getY())	
	 */
	public boolean isValid(int x, int y) {
		if (!isValidX(x) || !isValidY(y)) 
			return false;
		else 
			return true;
	}
	
	/**
	 * Return the minimum x-position of this world.
	 */	
	@Immutable
	@Basic
	public static double getMinXPosition() {
		return MIN_X_POSITION;
	}
	
	/**
	 * Variable registering the minimum x-coordinate of this world.
	 */
	private final static double MIN_X_POSITION = 0;
	
	/**
	 * Return the maximum x-position for this world.
	 */	
	@Immutable
	@Basic
	public double getMaxXPosition() {
		return MAX_X_POSITION;
	}
	
	/**
	 * Variable registering the maximum x-coordinate of this world.
	 */
	private final double MAX_X_POSITION;

	/**
	 * Return the minimum y-position of this world.
	 */
	@Immutable
	@Basic
	public static double getMinYPosition() {
		return MIN_Y_POSITION;
	}
	/**
	 * Variable registering the minimum y-position of this world.
	 */
	private final static double MIN_Y_POSITION = 0;
	
	/**
	 * Return the maximum y-position of this world.
	 */
	@Immutable
	@Basic
	public double getMaxYPosition() {
		return MAX_Y_POSITION;
	}
	/**
	 * Variable registering the maximum y-position of this world.
	 */
	private final double MAX_Y_POSITION;

	/**
	 * Check whether the given x-position and y-position is in this world.
	 *
	 * @return	result == ((y >= getMinYPosition()) 
	 *			&& (y <= getMaxYPosition()) && (x >= getMinXPosition()) 
	 *			&& (x <= getMaxXPosition()))
	 */
	public boolean pixelPositionInWorld(Position<Integer> pixelPosition) {
		return((pixelPosition.getY() >= getMinYPosition()) 
				&& (pixelPosition.getY() <= getMaxYPosition()) && (pixelPosition.getX() >= getMinXPosition()) 
				&& (pixelPosition.getX() <= getMaxXPosition()));	
	}
	
	/**
	 * Returns whether or not the given position is outside the world.
	 * 
	 * @param position
	 * @return result == (position.getX() > getMaxXPosition()/100 || position.getY() > getMaxYPosition()/100
	 *		|| position.getX() < 0 || position.getY() < 0)
	 */
	public boolean positionOutsideWorld(Position<Double> position) {
		return (position.getX() > getMaxXPosition()/100 || position.getY() > getMaxYPosition()/100
			|| position.getX() < 0 || position.getY() < 0); 
	}

	/**
	 * Terminate this world.
	 * 
	 * @post	new.isTerminated == true
	 * @post	new.gameObjects.size() == 0
	 */
	@Raw
	public void terminate() {		
		gameObjects.stream()
		.forEach(o -> ((GameObject) o).setWorld(null));
		this.gameObjects.clear();
		this.isTerminated = true;
	}
	
	/**
	 * Variable registering if this world has been terminated.
	 */
	public boolean isTerminated = false;
	
	/**
	 * The player has won if it overlaps with the target tile.
	 * 
	 * @return result == (mazubTile1 == getTargetTile() || mazubTile2 == getTargetTile()
	 * 						|| mazubTile3 == getTargetTile()
	 * 						|| mazubTile4 == getTargetTile()) 
	 * 						|| (mazubTile1[0] <= getTargetTile()[0] 
	 * 						&& getTargetTile()[0] <= mazubTile4[0] 
	 *						&& mazubTile1[1] <= getTargetTile()[1] 
	 *						&& getTargetTile()[1] <= mazubTile4[1])
	 */
	public boolean didPlayerWin() {
		if (getMazub() != null) {
			int[] mazubTile1 = pixelToTile(getMazub().getPixelPosition().getX(), getMazub().getPixelPosition().getY());
			int[] mazubTile2 = pixelToTile(getMazub().getPixelPosition().getX(), 
					getMazub().getPixelPosition().getY()+getMazub().getCurrentSprite().getHeight());
			int[] mazubTile3 = pixelToTile(getMazub().getPixelPosition().getX()+getMazub().getCurrentSprite().getWidth(), 
					getMazub().getPixelPosition().getY());
			int[] mazubTile4 = pixelToTile(getMazub().getPixelPosition().getX()+getMazub().getCurrentSprite().getWidth(), 
					getMazub().getPixelPosition().getY()+getMazub().getCurrentSprite().getHeight());

			if(mazubTile1 == getTargetTile() || mazubTile2 == getTargetTile()|| mazubTile3 == getTargetTile()|| mazubTile4 == getTargetTile()) {
				return true; }
			if (mazubTile1[0] <= getTargetTile()[0] && getTargetTile()[0] <= mazubTile4[0] 
					&& mazubTile1[1] <= getTargetTile()[1] && getTargetTile()[1] <= mazubTile4[1]) {
				return true; } }
			return false;
	}
	
	
	/**
	 * It's game over when the player won or the mazub is removed from this world
	 * and therefore doesn't exist anymore.
	 * 
	 * @return	result == (getMazub() == null || didPlayerWin())
	 */
	public boolean isGameOver() {
		if (getMazub() == null || didPlayerWin()) 
			return true; 
		return false;
	}
	
	/**
	 * Variable registering the number of horizontal tiles.
	 */
	private final int NB_X_TILES;
	
	/**
	 * Variable registering the number of vertical tiles.
	 */
	private final int NB_Y_TILES;
	
	/**
	 * Set the target tile coordinate of this world to the given coordinate.
	 * 
	 * @param 	targetTileCoordinate
	 * 			The new target tile coordinate for this world.
	 * @post	new.getTargetTile() = targetTileCoordinate
	 * 			
	 */
	@Raw
	public void setTargetTile(int[] targetTileCoordinate) {
		assertFalse(targetTileCoordinate == null);
		assertTrue(targetTileCoordinate.length == 2);		
		targetTile = targetTileCoordinate.clone();		
	}
	
	/**
	 * Return the position of the target tile of this world in pixels.
	 */
	@Basic
	public int[] getTargetTile() {
		return targetTile;
	}
	
	/**
	 * Variable registering the position in pixels of the target tile. 
	 */
	private int[] targetTile;

	/**
	 * Set the geological feature of all tiles to given geological features.
	 * 
	 * @param 	nbTilesX
	 * 			The number of tiles on the horizontal axis for this world.
	 * @param 	nbTilesY
	 * 			The number of tiles on the vertical axis for this world.
	 * @param 	geologicalFeatures
	 * 			The geological features for this world.
	 * @post	new.tiles == new Tile[nbTilesX][nbTilesY]
	 */
	@Raw
	public void setTiles(int nbTilesX, int nbTilesY, int... geologicalFeatures) throws IllegalArgumentException {
		
		if(geologicalFeatures == null) 
			throw new IllegalArgumentException("No geological features given.");
		
		this.tiles = new Tile[nbTilesX][nbTilesY];
		for (int tileY = 0; tileY < nbTilesY; tileY++)
			for (int tileX = 0; tileX < nbTilesX; tileX++ ) {
				if (tileY*nbTilesX+tileX > geologicalFeatures.length - 1 || geologicalFeatures[tileY * nbTilesX + tileX] <= 0
						||geologicalFeatures[tileY * nbTilesX + tileX] > 5) {
					tiles[tileX][tileY] = Tile.AIR; }
				else if (geologicalFeatures[tileY * nbTilesX + tileX] == 1) {
					tiles[tileX][tileY] = Tile.SOLID_GROUND; }
				else if (geologicalFeatures[tileY * nbTilesX + tileX] == 2) {
					tiles[tileX][tileY] = Tile.WATER; }
				else if (geologicalFeatures[tileY * nbTilesX + tileX] == 3) {
					tiles[tileX][tileY] = Tile.MAGMA; } 
				else if (geologicalFeatures[tileY * nbTilesX + tileX] == 4) {
					tiles[tileX][tileY] = Tile.ICE; } 
				else if (geologicalFeatures[tileY * nbTilesX + tileX] == 5) {
					tiles[tileX][tileY] = Tile.GAS; } 		
			}
	}

	/**
	 * Variable registering the tiles and the features belonging to each tile of this world. 
	 */
	public Tile[][] tiles;

	/**
	 * Return the size of this world in pixels for both the x-axis and y-axis. 
	 * 
	 * @return	result == SizeInPixels
	 */
	@Basic
	public int[] getSizeInPixels() {
		return SizeInPixels;		
	}

	/**
	 * Set the size of this world to the given number of tiles for the x-axis multiplied with
	 * the tile length and the given number of tiles for the y-axis multiplied with the tile length.
	 * 
	 * @param 	nbXTiles
	 * 			The number of tiles along the x-axis.
	 * @param 	nbYTiles
	 * 			The number of tiles along the y-axis.
	 * @param 	tileLength
	 * 			The length of one tile.
	 * @post	new.getSizeInPixels()[0] = nbXTiles*tileLength
	 * @post	new.getSizeInPixels()[1] = nbYTiles*tileLength
	 */
	@Raw
	public void setSizeInPixels(int nbXTiles, int nbYTiles,int tileLength) {
		SizeInPixels[0] = nbXTiles*tileLength;
		SizeInPixels[1] = nbYTiles*tileLength;		
	}

	/**
	 * Variable registering the size of this world in pixels for both the x-axis and y-axis. 
	 */
	private int[] SizeInPixels = new int[2];
	
	
	/**
	 * Return the length of each tile in this world.
	 */
	@Basic
	public int getTileLength() {
		return tileLength; 
	}

	/**
	 * Set the tile length of this world to the given tileLength.
	 * 
	 * @param	tileLength
	 * 			The new tile length of this world.
	 * @post	new.getTileLength() = tileLength
	 */
	@Raw
	public void setTileLength(int tileLength) {
		this.tileLength = tileLength;
	}
	
	/**
	 * Variable registering the length of a side of a square tile in pixels.
	 */
	private int tileLength;
	
	/**
	 * Convert the given x and y position from a pixel position to a tile position
	 * @param 	pixelX
	 * 			The x position in pixels which has to be converted.
	 * @param 	pixelY
	 * 			The y position in pixels which has to be converted.
	 * @return	result == tilePosition
	 */
	public int[] pixelToTile(int pixelX, int pixelY) {
		int[] tilePosition = new int[2];
		tilePosition[0] = (pixelX-pixelX%getTileLength())/getTileLength();
		tilePosition[1] = (pixelY-pixelY%getTileLength())/getTileLength();
		return (tilePosition);	
	}
	
	/**
	 * Returns the geological feature at the given x and y position in pixels.
	 * @param 	pixelX
	 * 			The x position in pixels where the geological feature is asked.
	 * @param 	pixelY
	 * 			The y position in pixel where the geological feature is asked.
	 * @return	if (outsideBoundaries(pixelX,pixelY))
	 * 				then result == 69
	 * @return	result == tiles[tilePosition[0]][tilePosition[1]].getFeature()
	 * 
	 */
	@Basic
	public int getGeologicalFeature(int pixelX, int pixelY) {
		if (outsideBoundaries(pixelX,pixelY)) 
			return 69; 
		int[] tilePosition = pixelToTile(pixelX,pixelY);
		return tiles[tilePosition[0]][tilePosition[1]].getFeature();	
	}
	
	/**
	 * Set the geological feature of the tile on the given position to the given geological feature.
	 * @param 	pixelX
	 * 			The x-position of the tile that needs to be converted.
	 * @param 	pixelY
	 * 			The number of tiles on the vertical axis for this world.
	 * @param 	geologicalFeatures
	 * 			The geological features for this world.
	 * @post	new.tiles[nbTilesX][nbTilesY] == Tile.geologicalFeature
	 * 			
	 */
	@Raw
	public void setGeologicalFeature(int pixelX, int pixelY, int geologicalFeature) {
		if (!outsideBoundaries(pixelX,pixelY)) {
			int[] tilePosition = pixelToTile(pixelX,pixelY);
			if (geologicalFeature == 0) 
				tiles[tilePosition[0]][tilePosition[1]] = Tile.AIR; 
			else if (geologicalFeature == 1) 
				tiles[tilePosition[0]][tilePosition[1]] = Tile.SOLID_GROUND; 
			else if (geologicalFeature == 2) 
				tiles[tilePosition[0]][tilePosition[1]] = Tile.WATER; 
			else if (geologicalFeature == 3) 
				tiles[tilePosition[0]][tilePosition[1]] = Tile.MAGMA;  
			else if (geologicalFeature == 4) 
				tiles[tilePosition[0]][tilePosition[1]] = Tile.ICE;  
			else if (geologicalFeature == 5) 
				tiles[tilePosition[0]][tilePosition[1]] = Tile.GAS;  
			}
	}
	
	
	/**
	 * Check whether the given position is outside of the boundaries of this world.
	 * @param	pixelX
	 * 			The x-position to check.
	 * @param	pixelY
	 * 			The y-position to check.
	 * @return	result == (pixelX < 0 || pixelY<0 || pixelX>=NB_X_TILES*getTileLength() 
	 * 				|| pixelY>=NB_Y_TILES*getTileLength())
	 */
	public boolean outsideBoundaries(int pixelX,int pixelY) {
		return (pixelX < 0 || pixelY<0 || pixelX>=NB_X_TILES*getTileLength() || pixelY>=NB_Y_TILES*getTileLength());
	}
	
	
	/**
	 * Check whether this world has the given object as game object.
	 * 
	 * @param	object
	 * 			The object to be checked.
	 * @return	result == gameObjects.contains(object)
	 */
	public boolean hasAsGameObject(Object object) {
		return gameObjects.contains(object);
	}
	
	/**
	 * Add the given object to this world.
	 * 
	 * @param 	object
	 * 			The new object to be added in this world.
	 * @effect	gameObjects.add((GameObject) object)
	 * @effect	((GameObject) object).setWorld(this)
	 * @post	if object.getClass() == Mazub.class && mazubAdded == false 
	 *				then new.getMazub() == (Mazub) object && new.mazubAdded == true
	 * @throws 	IllegalArgumentException
	 * 			object == null || isStarted 
	 * 			|| !validTiles(((GameObject) object).getPixelPosition().getX(),
	 *			((GameObject) object).getPixelPosition().getY(), ((GameObject) object).allSprites[0].getWidth(),
	 *			((GameObject) object).allSprites[0].getHeight()) && !(object instanceof Plant)
	 *			|| !pixelPositionInWorld( ((GameObject) object).getPixelPosition())
	 *			|| (object instanceof Mazub && ((GameObject) object).WithMazub(getAllGameObjects()))
	 *			|| !isValid(((GameObject) object).getPixelPosition().getX(),((GameObject) object).getPixelPosition().getY() )
	 *			|| (((GameObject) object).getPosition().getX() > (double)getSizeInPixels()[0]/100 ||
	 *			((GameObject) object).getPosition().getY() > (double)getSizeInPixels()[1]/100)
	 *			|| getAllGameObjects().size() > MAX_OBJECTS
	 *			|| ((GameObject) object).isTerminated
	 *			|| isTerminated
	 *			|| ((GameObject) object).getWorld() != null
	 *				
	 */
	public void addGameObject(Object object) throws IllegalArgumentException {
		if (object == null) 
			throw new IllegalArgumentException("Object can not be null."); 
		if (isStarted) 
			throw new IllegalArgumentException("Game has already started."); 

		if (!validTiles(((GameObject) object).getPixelPosition().getX(),
				((GameObject) object).getPixelPosition().getY(), ((GameObject) object).allSprites[0].getWidth(),
				((GameObject) object).allSprites[0].getHeight()) && !(object instanceof Plant)) 
			throw new IllegalArgumentException("Object is on impassable terrain."); 
		
		if (!pixelPositionInWorld( ((GameObject) object).getPixelPosition())) 
			throw new IllegalArgumentException("The given position is outside of the boundaries."); 	

		if (!(object instanceof Plant) && ((GameObject) object).isOverlappingWithGameObject(this.getAllGameObjects())) 
			throw new IllegalArgumentException("Overlapping with other game object.");
		
		if (object instanceof Mazub && mazubAdded) 
			throw new IllegalArgumentException("There already is a Mazub added in the game."); 

		if (!isValid(((GameObject) object).getPixelPosition().getX(),((GameObject) object).getPixelPosition().getY())) 	
			throw new IllegalArgumentException("The position is not valid."); 
		
		if (((GameObject) object).getPosition().getX() > (double)getSizeInPixels()[0]/100 ||
				((GameObject) object).getPosition().getY() > (double)getSizeInPixels()[1]/100) 
			throw new IllegalArgumentException("The position is outside of this world."); 
		
		else if (getAllGameObjects().size() >= MAX_OBJECTS && !(object instanceof Mazub)) 
			throw new IllegalArgumentException("There can not be more than 100 objects."); 
		
		else if (((GameObject) object).isTerminated) 
			throw new IllegalArgumentException("Object can not be terminated."); 
		
		else if (isTerminated) 
			throw new IllegalArgumentException("this world can not be terminated."); 
		
		else if (((GameObject) object).getWorld() != null) 
			throw new IllegalArgumentException("Object is already in other world."); 
		
		gameObjects.add((GameObject) object);
		((GameObject) object).setWorld(this);
		
		if (object.getClass() == Mazub.class && mazubAdded == false ) {
			firstMazub = (Mazub) object;
			mazubAdded = true; }
 	}
	
	/**
	 * Returns a copy of the list of all the game objects in this world in a set.
	 * 			
	 * @post	List<Object> gameObjectsCopy = new ArrayList<Object>()
	 * @effect	gameObjects.stream()
	 *			.forEach(o -> gameObjectsCopy.add(o))
	 * @return	result == allGameObjectsSet
	 */
	@Basic
	public Set<Object> getAllGameObjects() {
		List<Object> gameObjectsCopy = new ArrayList<Object>();	
		
		gameObjects.stream()
		.forEach(o -> gameObjectsCopy.add(o));
		
		Set<Object> allGameObjectsSet = new HashSet<Object>(gameObjectsCopy);
		return allGameObjectsSet;
	}
	
	/**
	 * Remove the given object from the list of game objects in this world. The world of this given 
	 * object is set to null, because it doesn't belong to a world anymore.
	 * @param 	object
	 * 			The object to be removed from the list.
	 * @post	gameObjects.remove(gameObjects.indexOf(object))
	 * @post	mazubAdded == false
	 * @effect	((GameObject) object).setWorld(null)
	 * @throws 	IllegalArgumentException
	 * 			!hasAsGameObject(object)
	 */
	public void removeGameObject(Object object) throws IllegalArgumentException{
		if (!hasAsGameObject(object)) 
			throw new IllegalArgumentException("This object is not an object of this world."); 
		
		gameObjects.remove(gameObjects.indexOf(object));
		((GameObject) object).setWorld(null);
		
		if (object instanceof Mazub) 
			mazubAdded = false;
			
	}
	
	/**
	 * Variable registering a list of all the game objects in this world. 
	 */
	public List<Object> gameObjects = new ArrayList<Object>();

	/**
	 * Return the first added mazub in this world. If there is no mazub added yet or the mazub 
	 * does not belong to this world, null is returned.
	 * 
	 * @return	if (firstMazub == null) 
	 *				then result == null
	 * @return	if (!hasAsGameObject(firstMazub))
	 *				then result == null
	 * @return	else result == firstMazub				
	 */
	@Basic
	public Mazub getMazub() {
		if (firstMazub == null) 
			return null; 
		else if (!hasAsGameObject(firstMazub)) 
			return null; 
		else return firstMazub;	
	}

	/**
	 * Variable registering the first mazub of this world.
	 */
	private Mazub firstMazub;

	/**
	 * Variable registering whether a mazub is added in this world.
	 */
	private boolean mazubAdded = false;

	/**
	 * Returns a list of the dimension of the visible window of the game.
	 * @return	result == new int[] {VISIBLE_WINDOW_WIDTH, VISIBLE_WINDOW_HEIGHT}
	 */
	@Basic
	public int[] getVisibleWindowDimension() {
		return new int[] {VISIBLE_WINDOW_WIDTH, VISIBLE_WINDOW_HEIGHT};
	}
	
	/**
	 * Returns the position of the visible window (bottom-left corner). If there is no mazub in this world,
	 * the window position is set to {0,0}. If the world is smaller than its visible window, then there is no position.
	 * @post	if (getMazub().getPixelPosition()[0] <= getVisibleWindowDimension()[0]/2) 
	 * 				then new.x == 0
	 * @post	else if (getMazub().getPixelPosition().getX() >= getSizeInPixels()[0] - getVisibleWindowDimension()[0]/2) 
	 * 				then new.x == getSizeInPixels()[0] - getVisibleWindowDimension()[0]
	 * @post	else new.x = getMazub().getPixelPosition().getX() - getVisibleWindowDimension()[0]/2
	 * @post	if (getMazub().getMazub().getPixelPosition().getY() <= getVisibleWindowDimension()[1]/2) 
	 * 				then new.y == 0
	 * @post	else if (getMazub().getPixelPosition().getY() >= getSizeInPixels()[0] - getVisibleWindowDimension()[1]/2) 
	 * 				then new.y == getSizeInPixels()[1] - getVisibleWindowDimension()[1]
	 * @post	else new.y = getMazub().getPixelPosition().getY() - getVisibleWindowDimension()[1]/2
	 * @return	if (getMazub() == null) {
	 *				then result ==  new int[] {0,0}
	 * @return	result == new int[] {x, y}
	 * @throws 	IllegalArgumentException
	 * 			Your world is too small!
	 * 		|	!isValidWindow()
	 */
	public Position<Integer> getVisibleWindowPosition() throws IllegalArgumentException{
		int x;
		int y;
		
		if (!isValidWindow()) 
			throw new IllegalArgumentException("Your world is too small!"); 
		
		if (getMazub() == null) 
			return new Position<Integer>(0, 0); 
		
		int xx = getMazub().getPixelPosition().getX();
		int yy = getMazub().getPixelPosition().getY();
	
		if (xx <= getVisibleWindowDimension()[0]/2) 
			x = 0; 
		else if (xx >= getSizeInPixels()[0] - getVisibleWindowDimension()[0]/2) 
			x = getSizeInPixels()[0] - getVisibleWindowDimension()[0]; 
		else 
			x = xx - getVisibleWindowDimension()[0]/2; 
		
		if (yy <= getVisibleWindowDimension()[1]/2) 
			y = 0; 
		else if (yy >= getSizeInPixels()[1] - getVisibleWindowDimension()[1]/2) 
			y = getSizeInPixels()[1] - getVisibleWindowDimension()[1]; 
		
		else 
			y = yy - getVisibleWindowDimension()[1]/2; 
			
		return new Position<Integer>(x, y);			
	}
	
	/**
	 * Returns whether the the visible window is smaller than the size of this world.
	 * 
	 * @return	result == getSizeInPixels()[0] >= getVisibleWindowDimension()[0] 
	 * 						&& getSizeInPixels()[1] >= getVisibleWindowDimension()[1]
	 */
	@Raw
	public boolean isValidWindow() {
		return (getSizeInPixels()[0] >= getVisibleWindowDimension()[0] && getSizeInPixels()[1] >= getVisibleWindowDimension()[1] );
	}

	/**
	 * Variable registering the width of the visible window.
	 */
	private int VISIBLE_WINDOW_WIDTH;

	/**
	 * Variable registering the height of the visible window.
	 */
	private int VISIBLE_WINDOW_HEIGHT;

	/**
	 * Return whether a game object with given x-position, y-position, width and height would be on valid tiles.
	 * Mazub game objects cannot be in impassable terrain, only the down perimeter may overlap with impassable terrain.
	 * Plant game objects can hover on impassable and passable terrain. Therefor, this method does not need to be invoked for a plant.
	 * The movement of a Slime and Shark game object is blocked by impassable terrain. (solid ground or ice)
	 * @param 	x
	 * 			The x-position of the game object to be checked.
	 * @param 	y
	 * 			The y-position of the game object to be checked.
	 * @param 	width
	 * 			The width of the game object to be checked.
	 * @param 	height
	 * 			The height of the game object to be checked.
	 * @return	int i = 0, int j = 1
	 *			while i < width 
	 *			and while j < height
	 *				if getGeologicalFeature(x+i, y+j) == 1 || getGeologicalFeature(x+i, y+j) == 4
	 *					then result == false
	 */
	@Raw
	public boolean validTiles(int x, int y, int width, int height) {
		int i = 0;
		List<Integer> geologicalFeatures = new ArrayList<Integer>();
		while(i < width) {
			int j = 1;
			while (j < height) {
				geologicalFeatures.add(getGeologicalFeature(x + i , y + j));
				j ++; }
			i ++; }	
				ImpassableTerrain feature = new ImpassableTerrain() {
					@Override
					public boolean isImpassableTerrain(int geologicalFeature) {
						return (geologicalFeature == 1 || geologicalFeature == 4);
					}
				};
				return geologicalFeatures.stream()				
				.allMatch(p -> !(feature.isImpassableTerrain(p)));
	}
	
	/**
	 * Returns the x-positions of the corners of the given game object.
	 * @param 	object
	 * 			The game object of which the corners are needed.
	 * @return	result == {(int) object.getPixelPosition().getX() , 
	 * 						(int) object.getPixelPosition().getX() +object.getCurrentSprite().getWidth() }
	 */
	public 	int[] getCornersX(GameObject object) {
		int[] cornersX = new int[2];
		int width = object.getCurrentSprite().getWidth();
		cornersX[0] = (int) object.getPixelPosition().getX() ;
		cornersX[1] = (int) object.getPixelPosition().getX()+width;
		return cornersX;
	}
	
	/**
	 * Returns the y-positions of the corners of the given game object.
	 * @param 	object
	 * 			The game object of which the corners are needed.
	 * @return	result == {(int) object.getPixelPosition().getY(), 
	 * 						(int) object.getPixelPosition().getY()+object.getCurrentSprite().getHeight() }
	 */
	public 	int[] getCornersY(GameObject object) {
		int[] cornersY = new int[2];
		int height = object.getCurrentSprite().getHeight();
		cornersY[0] = (int) object.getPixelPosition().getY() ;		
		cornersY[1] = (int) object.getPixelPosition().getY()+height ;
		return cornersY;
	}
	

	/**
	 * Advance the time of this world with the given time dt.
	 */
	public void advanceWorldTime(double dt) throws IllegalArgumentException {

			
		if (Double.isNaN(dt) || Double.isInfinite(dt) || dt < 0 || dt > 0.2) 
			throw new IllegalArgumentException("Illegal dt given");
		
		if (this.getMazub() != null) {
			this.getMazub().advanceTime(dt);
		
		Set<Object> gameObjectsCopy = getAllGameObjects();
		gameObjectsCopy.remove(this.getMazub());
		gameObjectsCopy.stream()
		.forEach(o -> ((GameObject) o).advanceTime(dt));
		}
		
		else {
		getAllGameObjects().stream()
		.forEach(o -> ((GameObject) o).advanceTime(dt));}
		

	}
	
	/**
	 * Checks whether the given object is positioned on top of another gameobject in the list gameObjectsCopy
	 * @param object
	 * 			Object that will be checked it if it is on top of another object
	 * @param gameObjectsCopy
	 * 			Set of GameObjects on which the given object is possibly on top.
	 * @return	result == true if (((GameObject) i).getPixelPosition()[1] + ((GameObject) i).getCurrentSprite().getHeight() == rowToCheck) &&
	 * 							if (j >= ((GameObject) i).getPixelPosition()[0] && j <= ((GameObject) i).getPixelPosition()[0] + ((GameObject) i).getCurrentSprite().getWidth())
	 * 							for (Object i : gameObjectsCopy)
	 * 			else result == false
	 */
	public boolean onTopOfOtherGameObject(GameObject object, Set<Object> gameObjectsCopy) {

		int rowToCheck = object.getPixelPosition().getY()+1;
		for (Object i : gameObjectsCopy) {

			if (!(i instanceof Plant)) {
				if (((GameObject) i).getPixelPosition().getY() + ((GameObject) i).getCurrentSprite().getHeight() == rowToCheck) {
					for (int j = object.getPixelPosition().getX(); j <= object.getPixelPosition().getX()+ object.getCurrentSprite().getWidth();j++) {
						if (j >= ((GameObject) i).getPixelPosition().getX() && j <= ((GameObject) i).getPixelPosition().getX() + ((GameObject) i).getCurrentSprite().getWidth() )			
							return true; } } }
			return false; }
	return false;	
	}

	/**
	 * Checks if a certain school is in this World
	 * @param school
	 * 			The school that is checked if it is in this World.
	 * @return 	schools.contains(school)
	 */
	public boolean hasAsSchool(Object school) {
		return schools.contains(school);
	}
	
	/**
	 * Adds a school to this World.
	 * @param school
	 * 			The school that has to be added to this world
	 * @post	this.hasAsSchool(school) == true
	 * @throws	IllegalArgumentException
	 * 			this.getAllSchools().size() >= 10
	 */
	public void addSchool(School school) throws IllegalArgumentException {
		if (this.getAllSchools().size() >= 10) 
			throw new IllegalArgumentException("There are already 10 schools in this world.");
 		schools.add((School) school);	
	}
	
	/**
	 * Returns a set containing all the schools in this World, this set is a copy of schools.
	 * @return school for school in schools.
	 */
	public Set<School> getAllSchools() {
		List<School> schoolsCopy = new ArrayList<School>();
		
		schools.stream()
		.forEach(s -> schoolsCopy.add(s));

		Set<School> allSchoolsSet = new HashSet<School>(schoolsCopy);
		return allSchoolsSet;
	}
	
	/**
	 * Removes a school from this world.
	 * @param object
	 * 			The object which has to be removed from this world.
	 * @throws IllegalArgumentException
	 * 			!hasAsSchool(object)
	 * @post	this.hasAsSchool(object) == false
	 * @effect 	object.setWorld(null)
	 */
	public void removeSchool(Object object) throws IllegalArgumentException{
		if (!hasAsSchool(object)) 
			throw new IllegalArgumentException("This school is not an object of this world."); 
		
		schools.remove(schools.indexOf(object));
		((School) object).setWorld(null);
		
	}

	/**
	 * Variable registering all the schools in this World.
	 */
	public List<School> schools = new ArrayList<School>();

	/**
	 * A static World used to represent the universe.
	 */
	public static World Instance = new World(10, 100, 200, new int[] { 10, 20 }, 20, 10);
	
	/**
	 * Variable registering the slimeIds in this World.
	 */
	public Set<Integer> slimeIds = new HashSet<Integer>();
	
	/**
	 * Returns if a certain slimeId is in this world.
	 * @param   id
	 * 			The id that might be in this world.
	 * @return 	result == slimeIds.contains(id)
	 */
	public boolean containsId(int id) {
		return (slimeIds.contains(id)); 
	}
	
	/**
	 * Adds a slimeId to the list of slimeIds
	 * @param 	id
	 * 			The id which has to be added.
	 * @throws	IllegalArgumentException
	 * 			containsId(id)
	 * @effect	slimeIds.add(id)
	 */
	public void addSlimeId(int id) throws IllegalArgumentException{
		if(containsId(id)) 
			throw new IllegalArgumentException("ID already exists."); 	
		slimeIds.add(id);
	}
	
	/**
	 * Removes a certain slimeId from the set of slimeIds
	 * @param id
	 * 			The id that has to be removed
	 * @effect	slimeIds.remove(id)
	 */
	public void removeSlimeId(int id) {
		slimeIds.remove(id);
	}
	
	/**
	 * Removes all the slimeIds
	 * @effect	slimeIds.clear()
	 */
	public void cleanAllSlimes() {
		slimeIds.clear();
	}
}
