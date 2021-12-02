import pokemons.*;
import ru.ifmo.se.pokemon.*;


public class Main {

    public static void main(String[] args) {
        Battle b = new Battle();

        Togepi p1 = new Togepi("Эрнест", 1);
        Togetic p2 = new Togetic("Иосиф", 1);
        Togekiss p3 = new Togekiss("Лаврентий", 1);
        Passimian p4 = new Passimian("Семен", 1);
        Eevee p5 = new Eevee("Тарас", 1);
        Jolteon p6 = new Jolteon("Антон", 1);

        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);

        b.go();
    }
}
