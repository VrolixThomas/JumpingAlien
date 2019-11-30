package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import be.kuleuven.cs.som.annotate.Value;

/**
 * An enumeration introducing different tiles used to express the the different features
 * a tile can have.
 * 
 *
 */
@Value
public enum Tile {
	AIR("0"), SOLID_GROUND("1"), WATER("2"), MAGMA("3"), ICE("4"), GAS("5");
	
	/**
	 * Initialize this tile with the given feature.
	 * 
	 * @param 	feature
	 * 			The feature for this new tile.
	 * @post	The feature for this new tile is equal to the given feature.
	 * 		|	new.getFeature() == feature		
	 */
	@Raw
	private Tile(String feature) {
		this.feature = Integer.parseInt(feature);
	}
	/**
	 * Return the feature for this tile.
	 * @return	The feature of this tile.
	 * 			
	 */
	@Immutable 
	@Basic
	public int getFeature() {
		return this.feature;
	}
	
	/**
	 * A variable registering the feature of this tile.
	 */
	private final int feature;

	
}




