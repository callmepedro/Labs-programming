package pokemons;

import moves.Psychic;
import moves.DazzlingGleam;
import moves.SweetKiss;

import ru.ifmo.se.pokemon.*;


public class Togetic extends Pokemon {

    public Togetic(String name, int level) {
        super(name, level);
        
        super.setType(Type.FAIRY, Type.FLYING);
        super.setStats(55, 40, 85, 80, 105, 40);

        Psychic psychic = new Psychic(90, 100);
        DazzlingGleam dazzlingGleam = new DazzlingGleam(80, 100);
        SweetKiss sweetKiss = new SweetKiss(0, 75);
        
        super.setMove(psychic, dazzlingGleam, sweetKiss);
    }
}
