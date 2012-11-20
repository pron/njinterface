/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.paralleluniverse.njinterface.epmd;

/**
 *
 * @author pron
 */
public enum EpmdConst {
    ntypeR6(110), ntypeR4Erlang(109), ntypeR4Hidden(104);
    
    public final int value;
    
    private EpmdConst(int value) {
        this.value = value;
    }
}
