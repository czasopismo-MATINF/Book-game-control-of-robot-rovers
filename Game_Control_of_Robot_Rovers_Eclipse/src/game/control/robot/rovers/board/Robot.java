package game.control.robot.rovers.board;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.io.Serializable;

public class Robot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static int MAX_ROBOT_ID = 0;

	protected int id;
	protected MaxLoadCargo cargo;
	protected Battery[] batteries;

	public Robot(int maxLoad, Battery[] batteries) {
		this.id = ++Robot.MAX_ROBOT_ID;
		this.cargo = new MaxLoadCargo(maxLoad);
		this.batteries = batteries;
	}

	public int getId() {
		return id;
	}
	
	public MaxLoadCargo getCargo() {
		return cargo;
	}

	public Battery[] getBatteries() {
		return batteries;
	}

	public boolean insertBattery(Battery battery, int slot) {
		if (slot >=0 && slot < this.batteries.length && this.batteries[slot] == null) {
			this.batteries[slot] = battery;
			return true;
		}
		return false;
	}
	
	public int getFreeSlot() {
		for(int i = 0; i < this.getBatteries().length; ++i) {
			if(this.getBatteries()[i] == null) {
				return i;
			}
		}
		return -1;
	}

	public Battery removeBattery(int slot) {
		if (slot >= 0 && slot < this.batteries.length && this.batteries[slot] != null) {
			Battery b = this.batteries[slot];
			this.batteries[slot] = null;
			return b;
		}
		return null;
	}
	
	public List<Battery> getChargedBatteries() {
		return Arrays.asList(this.getBatteries()).stream().filter(b -> b != null && b.getEnergy() > 0).collect(Collectors.toList());
	}
	
	public List<Battery> getNotNullBatteries() {
		return Arrays.asList(this.getBatteries()).stream().filter(b -> b != null).collect(Collectors.toList());
	}
	
	public List<Battery> getNotFullBatteries() {
		return Arrays.asList(this.getBatteries()).stream().filter(b -> b != null && b.getCapacity() > b.getEnergy()).collect(Collectors.toList());
	}
	
	public String getBatteryStatus() {
		return Arrays.asList(this.getBatteries()).stream().map(b -> {
			if(b == null) return "0";
			return "1";
		}).collect(Collectors.joining());
	}

	public int getTotalEnergy() {
		return this.getChargedBatteries().stream().collect(Collectors.summingInt(b -> b.getEnergy()));
	}
	
	public int getTotalWeight() {
		int batteryWeight = Arrays.asList(this.getBatteries()).stream().filter(b -> b != null).collect(Collectors.summingInt(b -> b.getWeight()));
		return this.getCargo().load() + batteryWeight;
	}
	
	public int drainEnergy(int energy) {
		
		int total = 0;
		for(int i = 0; i < energy; ++i) {
			List<Battery> chargedBatteries = this.getChargedBatteries();
			if(chargedBatteries.size() == 0) {
				return total;
			}
			Collections.shuffle(chargedBatteries);
			chargedBatteries.get(0).drain(1);
			total += 1;
		}
		return total;
		
	}
	
	public int chargeEnergy(int energy) {
		int total = 0;
		for(int i = 0; i < energy; ++i) {
			List<Battery> notFullBatteries = this.getNotFullBatteries();
			if(notFullBatteries.size() == 0) {
				return energy - total;
			}
			Collections.shuffle(notFullBatteries);
			notFullBatteries.get(0).charge(1);
			total += 1;
		}
		return energy - total;
	}
	
	public void chargeFull() {
		this.getNotNullBatteries().forEach(b -> {
			b.chargeFull();
		});
	}
	
	public void drainAllEnergy() {
		this.getNotNullBatteries().stream().forEach(b -> b.drainAllEnergy());
	}
	
	public boolean hasEnoughEnergy(int energy) {
		return this.getTotalEnergy() >= energy;
	}

}
