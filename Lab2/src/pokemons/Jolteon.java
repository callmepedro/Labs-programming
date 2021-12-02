package pokemons;

import moves.Tackle;
import moves.DoubleTeam;
import moves.BabyDollEyes;
import moves.ThunderFang;

import ru.ifmo.se.pokemon.*;


public class Jolteon extends Pokemon {

    public Jolteon(String name, int level) {
        super(name, level);

        super.setType(Type.ELECTRIC);
        super.setStats(65, 65, 60, 110, 95, 130);
        
        Tackle tackle = new Tackle(40, 100);
        DoubleTeam doubleTeam = new DoubleTeam(0,100);
        BabyDollEyes babyDollEyes = new BabyDollEyes(0, 100, 1, 1);
        ThunderFang thunderFang = new ThunderFang(5, 95);
        
        super.setMove(thunderFang);
    }
}
