/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.paralleluniverse.njinterface;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author pron
 */
public class Reference {
    private final String node;
    private final List<Integer> id;
    private final int creation;

    public Reference(String node, List<Integer> id, int creation) {
        this.node = node;
        this.id = id;
        this.creation = creation;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.node);
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + this.creation;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Reference other = (Reference) obj;
        if (!Objects.equals(this.node, other.node))
            return false;
        if (!Objects.equals(this.id, other.id))
            return false;
        if (this.creation != other.creation)
            return false;
        return true;
    }

    public String getNode() {
        return node;
    }

    public List<Integer> getId() {
        return id;
    }

    public int getCreation() {
        return creation;
    }
}
