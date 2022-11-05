/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author chloe
 */
public class InhousePart extends Part {
    
    // Declare fields
    private int machineId;

    //Declare constructor
    public InhousePart(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    
    //Declare methods
    public int getMachineId(){
    return machineId;
    }
    
    public void setMachineId(int machineId){
    this.machineId = machineId;
    }
    
}