package moves;

import ru.ifmo.se.pokemon.*;


public class BrutalSwing extends PhysicalMove {

    public BrutalSwing (double pow, double acc) {
        super(Type.DARK, pow, acc);
    }
    
    @Override
    protected String describe(){
        return "использует BrutalSwingls";
    }
    
}
