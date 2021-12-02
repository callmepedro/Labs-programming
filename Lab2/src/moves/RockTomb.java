package moves;

import ru.ifmo.se.pokemon.*;


public class RockTomb extends PhysicalMove {

    public RockTomb (double pow, double acc){
        super(Type.ROCK, pow, acc);
    }
    
    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect e = new Effect().stat(Stat.SPEED, -1);
        p.addEffect(e);
    }

    @Override
    protected String describe(){
        return "использует Rock Tomb";
    }
}
