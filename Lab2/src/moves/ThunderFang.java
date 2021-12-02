package moves;

import ru.ifmo.se.pokemon.*;


public class ThunderFang extends PhysicalMove {

    public ThunderFang (double pow, double acc){
        super(Type.ELECTRIC, pow, acc);
    }
    
    private static boolean chance(double value) {
        return value >= Math.random();
    }
    
    @Override
    protected void applyOppEffects(Pokemon p) {
        if (ThunderFang.chance(0.1)) {
            Effect.paralyze(p);
        }
        
        if (ThunderFang.chance(0.1)) {
            Effect.flinch(p); 
        }
    }

    @Override
    protected String describe(){
        return "использует Thunder Fang";
    }
}
