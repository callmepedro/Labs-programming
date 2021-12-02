package moves;

import ru.ifmo.se.pokemon.*;


public class Psychic extends SpecialMove {

    public Psychic(double pow, double acc) {
        super(Type.PSYCHIC, pow, acc);
    }
    
    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect e = new Effect().chance(0.1).stat(Stat.DEFENSE, -1);
        p.addEffect(e);
    }

    @Override
    protected String describe(){
        return "использует Psychic";
    }
}
