package moves;

import ru.ifmo.se.pokemon.*;


public class SweetKiss extends StatusMove {

    public SweetKiss(double pow, double acc) {
        super(Type.FAIRY, pow, acc);
    }
    
    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect.confuse(p);
    }

    @Override
    protected String describe(){
        return "использует Sweet Kiss";
    }
}
