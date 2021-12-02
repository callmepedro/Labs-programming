package moves;

import ru.ifmo.se.pokemon.*;


public class Confide extends StatusMove {

    public Confide(double pow, double acc) {
        super(Type.NORMAL, pow, acc);
    }
    
    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect e = new Effect().stat(Stat.SPECIAL_ATTACK, -1);
        p.addEffect(e);
    }
    
    @Override
    protected String describe(){
        return "использует Confide";
    }
    
}
