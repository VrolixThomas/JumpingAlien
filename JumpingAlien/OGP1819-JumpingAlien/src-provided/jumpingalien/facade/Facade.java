package jumpingalien.facade;

import java.util.Collection;
import java.util.Set;

import org.opentest4j.AssertionFailedError;

import jumpingalien.model.GameObject;
import jumpingalien.model.Mazub;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Position;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Skullcab;
import jumpingalien.model.Slime;
import jumpingalien.model.Sneezewort;
import jumpingalien.model.World;
import jumpingalien.util.ModelException;
import jumpingalien.util.ShouldNotImplementException;
import jumpingalien.util.Sprite;

public class Facade implements IFacade{

	@Override
	public boolean isTeamSolution() {
		return true;
	}
	
	@Override
	public boolean isLateTeamSplit() {
		return false;
	}


	@Override
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite... sprites) throws ModelException {
		try {
			Mazub mazub = new Mazub(pixelLeftX, pixelBottomY, sprites);
			return mazub;
		 } catch (IllegalArgumentException ex) {
			 throw new ModelException(ex.getMessage());
		 }
	}

	@Override
	public double[] getActualPosition(Mazub alien) throws ModelException {
		double[] position = {(double) alien.getPosition().getX(), (double) alien.getPosition().getY()};
		return position;
	}

	@Override
	public void changeActualPosition(Mazub alien, double[] newPosition) throws ModelException {		
		try {
			alien.changeActualPosition(newPosition);
		} catch(IllegalArgumentException e) {
			throw new ModelException(e.getMessage());
		}
		}
	

	@Override
	public int[] getPixelPosition(Mazub alien) throws ModelException {
		int[] pixelPosition = {(int) ((GameObject) alien).getPixelPosition().getX(),(int) ((GameObject) alien).getPixelPosition().getY()};
		return pixelPosition;
	}

	@Override
	public int getOrientation(Mazub alien) throws ModelException {
		return alien.getOrientation();
	}

	@Override
	public double[] getVelocity(Mazub alien) throws ModelException {
		return alien.getVelocity();
	}

	@Override
	public double[] getAcceleration(Mazub alien) throws ModelException {
		return alien.getAcceleration();
	}

	@Override
	public boolean isMoving(Mazub alien) throws ModelException {
		return alien.isHorizontalMoving;
	}

	@Override
	public void startMoveLeft(Mazub alien) throws ModelException {
		
		try {
			alien.startMove("left");
		 } catch (Error ex) {
			 throw new ModelException(ex.getMessage());
		 }
	}

	@Override
	public void startMoveRight(Mazub alien) throws ModelException {
		try {
			alien.startMove("right");
		 } catch (Error ex)	 {
			 throw new ModelException(ex.getMessage());
		 }

	}

	@Override
	public void endMove(Mazub alien) throws ModelException {
		try {
			alien.endMove();
		 } catch (Error ex) {
			 throw new ModelException(ex.getMessage());
		 }		
	}

	@Override
	public boolean isJumping(Mazub alien) throws ModelException {
		return (alien.isJumping);
	}

	@Override
	public void startJump(Mazub alien) throws ModelException {
		try { alien.startJump(); }
		catch(IllegalArgumentException d) {
			throw new ModelException(d.getMessage());
		}
	}

	@Override
	public void endJump(Mazub alien) throws ModelException {
		try { alien.endJump(); }
		catch (IllegalArgumentException d) {
			throw new ModelException(d.getMessage());
		}
	}

	@Override
	public boolean isDucking(Mazub alien) throws ModelException {
		return alien.isDucking;
	}

	@Override
	public void startDuck(Mazub alien) throws ModelException {
		alien.startDuck();
	}

	@Override
	public void endDuck(Mazub alien) throws ModelException {
		alien.endDuck();
	}

//	@Override
//	public void advanceTime(Mazub alien, double dt) throws ModelException {
//		alien.advanceTime(dt);
//	}
	
	@Override
	public Sprite[] getSprites(Mazub alien) throws ModelException {
		return alien.getAllSprites();

	}
	
	@Override
	public Sprite getCurrentSprite(Mazub alien) throws ModelException {
		try {
			return alien.getCurrentSprite();

		} catch (IllegalArgumentException d) {
			throw new ModelException (d.getMessage());
		}
	}

	@Override
	public World createWorld(int tileSize, int nbTilesX, int nbTilesY, int[] targetTileCoordinate,
			int visibleWindowWidth, int visibleWindowHeight, int... geologicalFeatures) throws ModelException {
		try {
		World world = new World(tileSize, nbTilesX, nbTilesY, targetTileCoordinate,
				 visibleWindowWidth, visibleWindowHeight, geologicalFeatures);
		return world;}
		catch(IllegalArgumentException e) {
			throw new ModelException(e.getMessage());
		}
		catch(Error e) {
			throw new ModelException(e.getMessage());
		}
	}

	@Override
	public void terminateWorld(World world) throws ModelException {
		world.terminate();
		
	}

	@Override
	public int[] getSizeInPixels(World world) throws ModelException {
		return world.getSizeInPixels();
	}

	@Override
	public int getTileLength(World world) throws ModelException {
		return world.getTileLength();
	}

	@Override
	public int getGeologicalFeature(World world, int pixelX, int pixelY) throws ModelException {
		try {
		return world.getGeologicalFeature(pixelX, pixelY);
		}
		catch (IllegalArgumentException e) {
			throw new ModelException(e.getMessage());
		}
	}

	@Override
	public void setGeologicalFeature(World world, int pixelX, int pixelY, int geologicalFeature) throws ModelException {
		world.setGeologicalFeature(pixelX, pixelY, geologicalFeature);
	}

	@Override
	public int[] getVisibleWindowDimension(World world) throws ModelException {
		return world.getVisibleWindowDimension();
	}
	
	@Override
	public int[] getVisibleWindowPosition(World world) throws ModelException {

		try {
			int[] windowPosition = { world.getVisibleWindowPosition().getX(), world.getVisibleWindowPosition().getY()};	
			return windowPosition;		
		} catch(IllegalArgumentException exception) {

			throw new ModelException(exception.getMessage());
		}	
	}

	@Override
	public boolean hasAsGameObject(Object object, World world) throws ModelException {
		return world.hasAsGameObject((GameObject) object);
	}

//	@Override
//	public Set<Object> getAllGameObjects(World world) throws ModelException {
//		return world.getAllGameObjects();
//	}

	@Override
	public Mazub getMazub(World world) throws ModelException {
		return (Mazub) world.getMazub();
	}

	@Override
	public void addGameObject(Object object, World world) throws ModelException {
		try {
		world.addGameObject(object);
		} catch(IllegalArgumentException e) {
			throw new ModelException(e.getMessage());
		}
	}
	@Override
	public void removeGameObject(Object object, World world) throws ModelException {
		try {
		world.removeGameObject(object);
		} catch(IllegalArgumentException e) {
			throw new ModelException(e.getMessage());
		}
	}

	@Override
	public int[] getTargetTileCoordinate(World world) throws ModelException {
		return world.getTargetTile();
	}

	@Override
	public void setTargetTileCoordinate(World world, int[] tileCoordinate) throws ModelException {
		world.setTargetTile(tileCoordinate);
	}

	@Override
	public void startGame(World world) throws ModelException {
		try {
		world.startGame();
		} catch(IllegalArgumentException e) {
			throw new ModelException(e.getMessage());
		}
		
	}

	@Override
	public boolean isGameOver(World world) throws ModelException {
		return world.isGameOver();
	}

	@Override
	public boolean didPlayerWin(World world) throws ModelException {
		return world.didPlayerWin();
	}

	@Override
	public void advanceWorldTime(World world, double dt) throws ModelException {
		try {
		world.advanceWorldTime(dt);
		} catch (IllegalArgumentException e) {
			throw new ModelException(e.getMessage());
		}
		
	}

//	@Override
//	public Plant createPlant(int pixelLeftX, int pixelBottomY, Sprite... sprites) throws ModelException {
//		try {
//			Plant plant = new Plant(pixelLeftX, pixelBottomY, sprites);
//			return plant;
//		 } catch (IllegalArgumentException ex) {
//			 throw new ModelException(ex.getMessage());
//		 }
//		 
//	}

	@Override
	public void terminateGameObject(Object gameObject) throws ModelException {
		((GameObject) gameObject).terminate();
	}

	@Override
	public boolean isTerminatedGameObject(Object gameObject) throws ModelException {
			return ((GameObject) gameObject).isTerminated;
	}

	@Override
	public boolean isDeadGameObject(Object gameObject) throws ModelException {
		return ((GameObject) gameObject).isDead;
	}

	@Override
	public double[] getActualPosition(Object gameObject) throws ModelException {
		double[] position = {(double) ((GameObject) gameObject).getPosition().getX(),(double) ((GameObject) gameObject).getPosition().getY()};
		return position;

	}

	@Override
	public void changeActualPosition(Object gameObject, double[] newPosition) throws ModelException {
		((GameObject) gameObject).changeActualPosition(newPosition);
		
	}

	@Override
	public int[] getPixelPosition(Object gameObject) throws ModelException {
		int[] pixelPosition = {(int) ((GameObject) gameObject).getPixelPosition().getX(),(int) ((GameObject) gameObject).getPixelPosition().getY()};	
		return pixelPosition;
	}

	@Override
	public int getOrientation(Object gameObject) throws ModelException {
		return ((GameObject) gameObject).getOrientation();
	}

	@Override
	public double[] getVelocity(Object gameObject) throws ModelException {
		return ((GameObject) gameObject).getVelocity();
	}

	@Override
	public World getWorld(Object object) throws ModelException {
		if (object instanceof GameObject) {
		return ((GameObject) object).getWorld(); }
		else if (object instanceof School) {
			return ((School) object).getWorld(); }
		return null;
		
	}

	@Override
	public int getHitPoints(Object object) throws ModelException {
		return ((GameObject) object).getHitPoints();
	}

	@Override
	public Sprite[] getSprites(Object gameObject) throws ModelException {
		return ((GameObject) gameObject).getAllSprites();
	}

	@Override
	public void advanceTime(Object gameObject, double dt) throws ModelException {
		if(Double.isNaN(dt)) {
			throw new ModelException("Illegal time!");
		}
		
		((GameObject) gameObject).advanceTime(dt);
		
	}
	
	@Override
	public Sprite getCurrentSprite(Object gameObject) throws ModelException {
		try {
			return ((GameObject) gameObject).getCurrentSprite();

		} catch (IllegalArgumentException d) {
			throw new ModelException (d.getMessage());
		}

	}

	@Override
	public Set<? extends Object> getAllGameObjects(World world) throws ModelException {
		return world.getAllGameObjects();	
		}

	

	@Override
	public Set<School> getAllSchools(World world) throws ModelException {
		return world.getAllSchools();
	}

	

	@Override
	public double[] getAcceleration(Object gameObject) throws ModelException {
		return ((GameObject) gameObject).getAcceleration();
	}

	@Override
	public Sneezewort createSneezewort(int pixelLeftX, int pixelBottomY, Sprite... sprites) throws ModelException {
		try {
			Sneezewort sneezewort = new Sneezewort(pixelLeftX, pixelBottomY, sprites);
			return sneezewort;
		 } catch (IllegalArgumentException ex) {
			 throw new ModelException(ex.getMessage());
		 }
	}

	@Override
	public Skullcab createSkullcab(int pixelLeftX, int pixelBottomY, Sprite... sprites) throws ModelException {
		try {
			Skullcab skullcap = new Skullcab(pixelLeftX, pixelBottomY, sprites);
			return skullcap;
		 } catch (IllegalArgumentException ex) {
			 throw new ModelException(ex.getMessage());
		 }
	}

	@Override
	public Slime createSlime(long id, int pixelLeftX, int pixelBottomY, School school, Sprite... sprites)
			throws ModelException {
		try { 
			Slime slime = new Slime(id, pixelLeftX, pixelBottomY, school, sprites);
			return slime;
		} catch (IllegalArgumentException ex) {
			 throw new ModelException(ex.getMessage());
		}
	}

	@Override
	public long getIdentification(Slime slime) throws ModelException {
		return slime.getId();	}

	@Override
	public void cleanAllSlimeIds() {
		World.Instance.cleanAllSlimes();
	}

	@Override
	public School createSchool(World world) throws ModelException {
		try { 
			School school = new School(world);
			return school;
		} catch (IllegalArgumentException ex) {
			 throw new ModelException(ex.getMessage());
		}
	}

	@Override
	public void terminateSchool(School school) throws ModelException {
		school.terminate();
	}

	@Override
	public boolean hasAsSlime(School school, Slime slime) throws ModelException {
		return school.hasAsSlime(slime);
	}

	@Override
	public Collection<? extends Slime> getAllSlimes(School school) {	
		return school.getAllSlimes();
	}

	@Override
	public void addAsSlime(School school, Slime slime) throws ModelException {
		try {
		school.addAsSlime(slime);
		} catch (IllegalArgumentException e) {
			throw new ModelException(e.getMessage());
		}
		
	}

	@Override
	public void removeAsSlime(School school, Slime slime) throws ModelException {
		try { 
			school.removeSlime(slime);
		} catch (IllegalArgumentException e) {
			throw new ModelException(e.getMessage());
		}
	}

	@Override
	public void switchSchool(School newSchool, Slime slime) throws ModelException {
		try {
		slime.switchSchool(newSchool);}
		catch (Exception e) {
			throw new ModelException(e.getMessage());
		}
		
	}

	@Override
	public School getSchool(Slime slime) throws ModelException {
		return slime.getCurrentSchool();
	}
	
	@Override
	public Shark createShark(int pixelLeftX, int pixelBottomY, Sprite... sprites) throws ModelException {
		try { 
			Shark shark = new Shark(pixelLeftX, pixelBottomY, sprites);
			return shark;
		} catch (IllegalArgumentException ex) {
			 throw new ModelException(ex.getMessage());
		}
	}

	@Override
	public boolean hasImplementedWorldWindow() {
		return true;
	}

	
	
}