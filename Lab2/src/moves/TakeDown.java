package moves;

import ru.ifmo.se.pokemon.*;
import static java.lang.Math.round;


public class TakeDown extends PhysicalMove {

    public TakeDown (double pow, double acc){
        super(Type.NORMAL, pow, acc);
    }
    
    @Override
    protected void applySelfEffects(Pokemon p) {
        int selfDamage = (int)round(p.getStat(Stat.ATTACK) * 0.25);
        Effect e = new Effect().stat(Stat.HP, -selfDamage);
        p.setCondition(e);
    }
    
    @Override
    protected String describe(){
        return "использует Take Down";
    }
    
}
