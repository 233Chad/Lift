/*
 * This file is part of Lift.
 *
 * Copyright (c) ${project.inceptionYear}-2013, croxis <https://github.com/croxis/>
 *
 * Lift is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Lift is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Lift. If not, see <http://www.gnu.org/licenses/>.
 */
package net.croxis.plugins.lift;

import net.kyori.adventure.text.Component;

import java.util.UUID;

public class Floor {
	private Component name = Component.text("");
	private int floor;
	int buttonX;
    int buttonY;
	int buttonZ;
	UUID worldID;

	public Floor(UUID id, final int x, final int y, final int z){
	    this.worldID = id;
		this.buttonX = x;
	    this.buttonY = y;
	    this.buttonZ = z;
	}

	public Component getName() {
		return name;
	}
	public void setName(Component name) {
		this.name = name;
		if (name == null)
			this.name = Component.text("");
	}
	public int getY() {
		return buttonY;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}

}
