package moves;

import ru.ifmo.se.pokemon.*;


public class DazzlingGleam extends SpecialMove {

    public DazzlingGleam(double pow, double acc) {
        super(Type.FAIRY, pow, acc);
    }

    @Override
    protected String describe(){
        return "использует Dazzling Gleam";
    }
}
