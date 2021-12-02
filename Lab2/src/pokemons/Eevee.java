package pokemons;

import moves.Tackle;
import moves.DoubleTeam;
import moves.BabyDollEyes;

import ru.ifmo.se.pokemon.*;


public class Eevee extends Pokemon {

    public Eevee(String name, int level) {
        super(name, level);

        super.setType(Type.NORMAL);
        super.setStats(55, 55, 50, 45, 65, 55);

        Tackle tackle = new Tackle(40, 100);
        DoubleTeam doubleTeam = new DoubleTeam(0,100);
        BabyDollEyes babyDollEyes = new BabyDollEyes(0, 100, 1, 1);
        
        super.setMove(tackle, doubleTeam, babyDollEyes);
    }
}
