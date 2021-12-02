package pokemons;

import moves.Psychic;
import moves.DazzlingGleam;
import moves.SweetKiss;

import ru.ifmo.se.pokemon.*;


public class Togekiss extends Pokemon {

    public Togekiss(String name, int level) {
        super(name, level);
        
        super.setType(Type.FAIRY, Type.FLYING);
        super.setStats(85, 50, 95, 120, 115, 80);

        Psychic psychic = new Psychic(90, 100);
        DazzlingGleam dazzlingGleam = new DazzlingGleam(80, 100);
        SweetKiss sweetKiss = new SweetKiss(0, 75);
        
        super.setMove(psychic, dazzlingGleam, sweetKiss);
    }
}
