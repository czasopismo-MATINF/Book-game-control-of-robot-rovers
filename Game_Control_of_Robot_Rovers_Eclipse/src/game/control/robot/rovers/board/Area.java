package game.control.robot.rovers.board;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.io.Serializable;

public class Area implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected List<Robot> robots;
	protected List<Battery> batteries;
	protected List<Blizzard> blizzards;
	protected List<ChargingStation> chargingStations;
	protected List<String> markers;

	protected int rocks = 0;
	protected boolean hasChasm = false;

	private MotherShip motherShip;

	{
		this.robots = new ArrayList<>();
		this.batteries = new ArrayList<>();
		this.blizzards = new ArrayList<>();
		this.chargingStations = new ArrayList<>();
		this.markers = new ArrayList<>();
	}

	public Area() {
		super();
	}

	public Area(int rocks, boolean hasChasm) {
		super();
		this.rocks = rocks;
		this.hasChasm = hasChasm;
	}

	public List<Robot> getRobots() {
		return robots;
	}

	public List<Battery> getBatteries() {
		return batteries;
	}

	public List<Blizzard> getBlizzards() {
		return blizzards;
	}

	public List<ChargingStation> getChargingStations() {
		return chargingStations;
	}
	
	public List<String> getMarkers() {
		return this.markers;
	}

	public int getRocks() {
		return rocks;
	}

	public void setRocks(int rocks) {
		this.rocks = rocks;
	}

	public boolean hasChasm() {
		return hasChasm;
	}

	public void setChasm(boolean chasm) {
		this.hasChasm = chasm;
	}

	public MotherShip getMotherShip() {
		return this.motherShip;
	}

	public void setMotherShip(MotherShip motherShip) {
		this.motherShip = motherShip;
	}
	
	public boolean hasMotherShip() {
		return this.motherShip != null;
	}

	public void addBatteriesAndRocks(List<Battery> batteries, Integer rocks) {
		this.batteries.addAll(batteries);
		this.addRocks(rocks);
	}

	public void addRocks(int rocks) {
		this.rocks += rocks;
	}

	public int mineRocks(int rocks) {
		if (rocks <= this.rocks) {
			this.rocks -= rocks;
			return rocks;
		} else {
			Integer r = this.rocks;
			this.rocks = 0;
			return r;
		}
	}

	public int getBlizzardVolume() {
		return this.getBlizzards().stream().collect(Collectors.summingInt(b -> b.getVolume()));
	}

}
