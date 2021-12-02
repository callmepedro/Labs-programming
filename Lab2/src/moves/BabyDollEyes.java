package moves;

import ru.ifmo.se.pokemon.*;


public class BabyDollEyes extends StatusMove {

    public BabyDollEyes(double pow, double acc, int priority, int hits){
        super(Type.FAIRY, pow, acc, priority, hits);
    }
    
    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect e = new Effect().stat(Stat.ATTACK, -1);
        p.addEffect(e);
    }

    @Override
    protected String describe(){
        return "использует Baby-Doll Eyes";
    }
}
