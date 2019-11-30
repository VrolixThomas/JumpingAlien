package jumpingalien.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * A class of Schools belonging to a world. 
 *  
 * @version 1.0
 * @author  Danaë Van de Velde & Thomas Vrolix
 */
@SuppressWarnings("all")
public class School {
	
	/**
	 * Initialize this new School with given world.
	 *
	 * @param	world
	 *			The world to which this school belongs.
	 * @effect 	setWorld(world)
	 */
	public School(World world) {
		
		setWorld(world);	
	}

	/**
	 * Returns the world to which this School belongs
	 * @return	result == this.hasAsWorld
	 */
	@Basic
	public World getWorld() {
		if (isTerminated()) {
			return null;}
		return hasAsWorld;}
	
	/**
	 * Variable registering if this School has yet been terminated
	 */
	private boolean terminated;
	
	/**
	 * Returns whether or not this School has been terminated.
	 * @return result == this.terminted
	 */
	@Basic
	public boolean isTerminated() {
		return terminated;
	}
	
	/**
	 * Sets the world for this School
	 * @param world
	 * 			The world to which this School will belong
	 * @throws IllegalArgumentException
	 * 			world.getAllSchools().size() >= 10
	 * @effect 	world.assSchool(this)
	 * @post	new.hasAsWorld = world
	 */
	public void setWorld(World world) throws IllegalArgumentException {
		
		if (world != null) {		
			if (world.getAllSchools().size() >= 10) {
				throw new IllegalArgumentException("Already 10 schools in this world."); } 
			else { 
			world.addSchool(this);
			hasAsWorld = world; } }
	}	
	
	/**
	 * Variable registering the world to which this school belongs.
	 */
	World hasAsWorld;
	
	/**
	 * Add a slime to this School
	 * @param slime
	 * 			The Slime that will be added if possible.
	 * @throws IllegalArgumentException
	 * 			slime.isInSchool()
	 * 			|| slime.isTerminated
	 * 			|| this.isTerminated()
	 * @effect	slime.setCurrentschool(this)
	 * @effect	allSlimes.add(slime) 
	 */
	public void addAsSlime(Slime slime) throws IllegalArgumentException {
		if (slime.isInSchool()) {
			throw new IllegalArgumentException("This slime is already in a school."); }
		
		else if (slime.isTerminated) {
			throw new IllegalArgumentException("This slime is terminated"); }
		else if (this.isTerminated()) {
			throw new IllegalArgumentException("This school is terminated."); }
		
		slime.setCurrentSchool(this);
		allSlimes.add(slime);
	}

	/**
	 * Variable registering all the Slimes in this School.
	 */
	public Set<Slime> allSlimes = new HashSet<Slime>();

	/**
	 * Returns a copy of the collection of all the Slimes in this School.
	 * @return result == allSlimes.copy()
	 */
	@Basic
	public Collection<Slime> getAllSlimes() {
		
		Collection<Slime> allSlimesCopy = new HashSet<Slime>();
		allSlimesCopy.addAll(allSlimes);
		return allSlimesCopy;
	}

	/**
	 * Removes a given slime from this School.
	 * @param slime
	 * 			The Slime that will be removed
	 * @throws IllegalArgumentException
	 * 			slime.getCurrentSchool() != this
	 * @effect	allSlimes.remove(slime)
	 * @effect	slime.setCurrentSchool(null)
	 */
	public void removeSlime(Slime slime) throws IllegalArgumentException {		
		if (slime.getCurrentSchool() == this) {
			allSlimes.remove(slime);
			slime.setCurrentSchool(null); }
		
		else throw new IllegalArgumentException("Not in school!");
	}
	
	/**
	 * Returns if this School has a certain Slime in it.
	 * @param slime
	 * 			The Slime for which it is checked if it is in this School.
	 * @return	result == getAllSlimes().contains(slime)
	 */
	public boolean hasAsSlime(Slime slime) {
		return(getAllSlimes().contains(slime));
	}
	
	/**
	 * Terminate this School.
	 * @post	new.isTerminated() == true
	 */
	public void terminate() {
		terminated = true;
	}
	
	
	
	
}

