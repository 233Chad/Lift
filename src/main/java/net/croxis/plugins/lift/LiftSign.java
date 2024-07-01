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

/**
 * Sign Formats
 * Version 1
 * Line 0: "Current floor" string
 * Line 1: Current floor int only
 * Line 2: "Dest Floor" and int
 * Line 3: Dest floor name string
 * <p>
 * Version 2
 * Line 0: "Current floor" string and int
 * Line 1: Current floor name
 * Line 2: "Dest Floor" and int
 * Line 3: Dest floor name string
 */


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.List;

/**
 * Created by croxis on 4/28/17.
 */
class LiftSign {
    int signVersion = 0; // 0=hmod, 1=lift till 55, 2=lift>=56
    Config config;
    private Component sign0;
    private Component sign1;
    private Component sign2;
    private Component sign3;
    private int currentFloor = 0;
    private int destFloor = 0;
    private Component currentName = Component.text("");
    private Component destName = Component.text("");

    LiftSign(Config config, List<Component> lines) {
        this(config, lines.get(0), lines.get(1), lines.get(2), lines.get(3));
    }

    /**
     * @param config
     * @param line0
     * @param line1
     * @param line2
     * @param line3
     */

    LiftSign(Config config, Component line0, Component line1, Component line2, Component line3) {
        this.config = config;
        this.sign0 = line0;
        this.sign1 = line1;
        this.sign2 = line2;
        this.sign3 = line3;

        // Check to see if it is a new sign with just a floor name. No : allowed for floor name.
        String plainLine0 = PlainTextComponentSerializer.plainText().serialize(line0);
        if (!plainLine0.isEmpty() && !plainLine0.contains(":") &&
                ((TextComponent)line1).content().isEmpty() &&
                ((TextComponent)line2).content().isEmpty() &&
                ((TextComponent)line3).content().isEmpty()) {
            signVersion = Config.signVersion;
            this.setCurrentName(line0);
        } else if (plainLine0.isEmpty()) // Just an empty sign, no floor name
            signVersion = Config.signVersion;
        else if (plainLine0.split(":").length == 1)
            readVersion1();
        else if (plainLine0.split(":").length == 2)
            readVersion2();
    }

    private void readVersion1() {
        try {
            signVersion = 2;
            setCurrentFloor(Integer.parseInt(PlainTextComponentSerializer.plainText()
                    .serialize(this.sign1)));
            this.destFloor = Integer.parseInt(PlainTextComponentSerializer.plainText()
                    .serialize(this.sign2).split(":")[1].trim());
            this.destName = this.sign3;
        } catch (Exception e) {
            this.currentFloor = 0;
            this.destFloor = 0;
        }
    }

    private void readVersion2() {
        try {
            signVersion = 2;
            this.currentFloor = Integer.parseInt(PlainTextComponentSerializer.plainText()
                    .serialize(this.sign0).split(":")[1].trim());
            this.currentName = this.sign1;
            this.destFloor = Integer.parseInt(PlainTextComponentSerializer.plainText()
                    .serialize(this.sign2).split(":")[1].trim());
            this.destName = this.sign3;
        } catch (Exception e) {
            this.currentFloor = 0;
            this.destFloor = 0;
        }

    }

    void setCurrentFloor(int currentFloor) {
        this.sign0 = Component.text(Config.currentFloor + ": " + currentFloor);
        this.currentFloor = currentFloor;
    }

    int getCurrentFloor() {
        return currentFloor;
    }

    int getDestinationFloor() {
        return destFloor;
    }

    void setDestinationFloor(int destination) {
        this.sign2 = Component.text(Config.destination + ": " + destination);
        this.destFloor = destination;
    }

    void setDestinationName(Component destinationName) {
        this.destName = destinationName;
        this.sign3 = destinationName;
    }

    Component[] saveSign() {
        Component[] data = new Component[4];
        data[0] = this.sign0;
        data[1] = this.sign1;
        data[2] = this.sign2;
        data[3] = this.sign3;
        return data;
    }

    void setCurrentName(Component name) {
        this.currentName = name;
        sign1 = name;
    }

    Component getCurrentName() {
        return this.currentName;
    }

    Component getDebug() {
        return this.sign0
                .appendNewline().append(this.sign1)
                .appendNewline().append(this.sign2)
                .appendNewline().append(this.sign3);
    }

    boolean isEmpty() {
        return PlainTextComponentSerializer.plainText().serialize(sign0.append(sign1).append(sign2).append(sign3)).isEmpty();
    }
}
