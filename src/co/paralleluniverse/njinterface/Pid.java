/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.paralleluniverse.njinterface;

import java.util.Objects;

/**
 *
 * @author pron
 */
public class Pid {
    private final String node;
    private final int id;
    private final int serial;
    private final int creation;

    public Pid(String node, int id, int serial, int creation) {
        this.node = node;
        this.id = id;
        this.serial = serial;
        this.creation = creation;
    }

    public String getNode() {
        return node;
    }

    public int getId() {
        return id;
    }

    public int getSerial() {
        return serial;
    }

    public int getCreation() {
        return creation;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.node);
        hash = 47 * hash + this.id;
        hash = 47 * hash + this.serial;
        hash = 47 * hash + this.creation;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Pid other = (Pid) obj;
        if (!Objects.equals(this.node, other.node))
            return false;
        if (this.id != other.id)
            return false;
        if (this.serial != other.serial)
            return false;
        if (this.creation != other.creation)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "<" + id + "." + serial + "." + creation + ">";
    }
    
    
}
