package pokemons;

import moves.Psychic;
import moves.DazzlingGleam;

import ru.ifmo.se.pokemon.*;

public class Togepi extends Pokemon {

    public Togepi(String name, int level) {
        super(name, level);

        super.setType(Type.FAIRY);
        super.setStats(35, 20, 65, 40, 65, 20);

        Psychic psychic = new Psychic(90, 100);
        DazzlingGleam dazzlingGleam = new DazzlingGleam(80, 100);
        
        super.setMove(psychic, dazzlingGleam);
    }
}
