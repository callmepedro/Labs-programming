package pokemons;

import moves.RockTomb;
import moves.BrutalSwing;
import moves.Confide;
import moves.TakeDown;

import ru.ifmo.se.pokemon.*;


public class Passimian extends Pokemon {

    public Passimian(String name, int level) {
        super(name, level);

        super.setType(Type.FIGHTING);
        super.setStats(100, 120, 90, 40, 60, 80);

        RockTomb rockTomb = new RockTomb(60, 95);
        BrutalSwing brutalSwing = new BrutalSwing(60, 100);
        Confide confide = new Confide(0, 100);
        TakeDown takeDown = new TakeDown(90, 85);
        
        super.setMove(rockTomb, brutalSwing, confide, takeDown);
    }
}
