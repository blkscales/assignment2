/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package World;

/**
 *
 * @author blk
 */
public class PlayerHeadquarters extends Headquarters {

    public int timeOfLastAction = -1;

    public PlayerHeadquarters(int[] order, int party, int initLifeElements, int cityID) {
        super(order, party, initLifeElements, cityID);
    }

    @Override
    public boolean tryToProduceWarrior() {
        return false;
    }

    public boolean tryToProduceWarrior(int type) throws IllegalStateException{
        if( World.WorldClock.getTimeRaw() == this.timeOfLastAction){
            throw new IllegalStateException("You have already moved.");
        }
        if(World.WorldClock.getMinute() != 0){
            //System.out.println(World.WorldClock.getMinute());   //debug purpose
            throw new IllegalStateException("The time is not 00 minute sharply!!");
        }
        this.timeOfLastAction = World.WorldClock.getTimeRaw();
        if(type == -1) return false;
        try {
            return produceWarrior(type);
        } catch (IllegalStateException e) {
            throw e;
        }
    }

}
