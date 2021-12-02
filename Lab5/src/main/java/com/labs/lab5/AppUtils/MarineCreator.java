package com.labs.lab5.AppUtils;

import com.labs.lab5.AppObjects.*;
import com.labs.lab5.Exceptions.IncorrectCommandFormatException;
import com.labs.lab5.Exceptions.InvalidValueException;

import java.util.Arrays;

import static com.labs.lab5.AppUtils.ConsoleManager.replyUser;

/**
 * Class for creating SpaceMarines
 */
public class MarineCreator {

    private CommandStruct response;
    private UserInfo userInfo;

    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private int health; //Значение поля должно быть больше 0
    private boolean loyal;
    private AstartesCategory category; //Поле не может быть null
    private MeleeWeapon meleeWeapon; //Поле не может быть null
    private Chapter chapter; //Поле может быть null


    private static class UserInfo {
        private boolean consoleMod;
        UserInfo() {
            this.consoleMod = ConsoleManager.getCommandReader().getCommandReaderMod() == CommandReaderMode.CONSOLE;
        }

        public void sendMessage(String msg){
            if (consoleMod) System.out.println(msg);
        }
        public void nameInfo(){
            if (consoleMod) System.out.print("~ Name: ");
        }
        public void coordinatesInfo(){
            if (consoleMod) System.out.print("~ Coordinates {x y}: ");
        }
        public void healthInfo(){
            if (consoleMod) System.out.print("~ Health: ");
        }
        public void loyalInfo(){
            if (consoleMod) System.out.print("~ Loyal {boolean}: ");
        }
        public void categoryInfo(){
            if (consoleMod)
                System.out.printf("~ Category {one of %s}: ", Arrays.toString(AstartesCategory.values()));
        }
        public void weaponInfo() {
            if (consoleMod)
                System.out.printf("~ Melee weapon {one of %s}: ", Arrays.toString(MeleeWeapon.values()));
        }
        public void chapterInfo(){
            if (consoleMod) System.out.print("~ Chapter {Name Score | null}: ");
        }
    }


    private void setName() {
        userInfo.nameInfo();

        response = ConsoleManager.getCommandStruct();
        if (!response.isHasCommand()) throw new IncorrectCommandFormatException("Field 'Name' must not be empty");
        if (response.isHasArgument()) throw new IncorrectCommandFormatException("Field 'Name' must be one word");

        this.name = response.getCommand();
    }

    private void setCoordinates() {
        userInfo.coordinatesInfo();

        response = ConsoleManager.getCommandStruct();
        if (!response.isHasCommand()) throw new IncorrectCommandFormatException("Field 'Coordinates' must consist of two numbers");
        if (!response.isHasArgument()) throw new IncorrectCommandFormatException("Field 'Coordinates' must consist of two numbers");

        double coordinateX = Double.parseDouble(response.getCommand()); // can throw NumberFormatException
        Double coordinateY = Double.parseDouble(response.getArgument()); // can throw NumberFormatException
        if (coordinateY > 219) throw new InvalidValueException("Field 'CoordinateY' must be less than 220");
        this.coordinates = new Coordinates(coordinateX, coordinateY);
    }

    private void setHealth() {
        userInfo.healthInfo();

        response = ConsoleManager.getCommandStruct();
        if (!response.isHasCommand()) throw new IncorrectCommandFormatException("Field 'Health' must not be empty");
        if (response.isHasArgument()) throw new IncorrectCommandFormatException("Field 'Health' must be one word");

        int healthValue = Integer.parseInt(response.getCommand()); // can throw NumberFormatException
        if (healthValue <= 0) throw new InvalidValueException("Field 'Health' must be greater than 0");
        this.health = healthValue;
    }

    private void setLoyal() {
        userInfo.loyalInfo();

        response = ConsoleManager.getCommandStruct();
        if (!response.isHasCommand()) throw new IncorrectCommandFormatException("Field 'Loyal' must not be empty");
        if (response.isHasArgument()) throw new IncorrectCommandFormatException("Field 'Loyal' must be one word");

        String loyalValue = response.getCommand();
        if (!(loyalValue.equalsIgnoreCase("true") || loyalValue.equalsIgnoreCase("false")))
            throw new InvalidValueException("Field 'Loyal' must be boolean");
        this.loyal = Boolean.parseBoolean(loyalValue);
    }

    private void setCategory() {
        userInfo.categoryInfo();

        response = ConsoleManager.getCommandStruct();
        if (!response.isHasCommand()) throw new IncorrectCommandFormatException("Field 'Category' must not be empty");
        if (response.isHasArgument()) throw new IncorrectCommandFormatException("Field 'Category' must be one word");

        this.category = AstartesCategory.valueOf(response.getCommand().toUpperCase()); // can throw IllegalArgumentException
    }

    private void setMeleeWeapon() {
        userInfo.weaponInfo();

        response = ConsoleManager.getCommandStruct();
        if (!response.isHasCommand()) throw new IncorrectCommandFormatException("Field 'MeleeWeapon' must not be empty");
        if (response.isHasArgument()) throw new IncorrectCommandFormatException("Field 'MeleeWeapon' must be one word");

        this.meleeWeapon = MeleeWeapon.valueOf(response.getCommand().toUpperCase()); // can throw IllegalArgumentException
    }

    private void setChapter() {
        userInfo.chapterInfo();

        response = ConsoleManager.getCommandStruct();
        if (!response.isHasCommand()) this.chapter = null;
        else if (response.isHasArgument()) {
            String chapterName = response.getCommand();
            Long chapterScore = Long.parseLong(response.getArgument()); // can throw NumberFormatException
            if (chapterScore <= 0 || chapterScore > 1000) throw new InvalidValueException("Chapter's 'Score' must be > 0 and <= 1000");
            this.chapter = new Chapter(chapterName, chapterScore);
        }
        else {
            String chapterName = response.getCommand();
            this.chapter = new Chapter(chapterName, null);
        }
    }

    /**
     * Create a new instance of SpaceMarine
     * @return new Spacemarine
     */
    public SpaceMarine create() {
        userInfo = new UserInfo();
        boolean correctInput;

        if (ConsoleManager.getCommandReader().getCommandReaderMod() == CommandReaderMode.CONSOLE) {
            correctInput = false;
            do {
                try {
                    setName();
                    correctInput = true;
                } catch (IncorrectCommandFormatException e) {
                    userInfo.sendMessage(e.getMessage());
                }
            } while (!correctInput);

            correctInput = false;
            do {
                try {
                    setCoordinates();
                    correctInput = true;
                } catch (IncorrectCommandFormatException | InvalidValueException e) {
                    userInfo.sendMessage(e.getMessage());
                } catch (NumberFormatException e) {
                    userInfo.sendMessage("Incorrect number format");
                }
            } while (!correctInput);

            correctInput = false;
            do {
                try {
                    setHealth();
                    correctInput = true;
                } catch (IncorrectCommandFormatException | InvalidValueException e) {
                    userInfo.sendMessage(e.getMessage());
                } catch (NumberFormatException e) {
                    userInfo.sendMessage("Incorrect number format");
                }

            } while (!correctInput);

            correctInput = false;
            do {
                try {
                    setLoyal();
                    correctInput = true;
                } catch (IncorrectCommandFormatException | InvalidValueException e) {
                    userInfo.sendMessage(e.getMessage());
                }
            } while (!correctInput);

            correctInput = false;
            do {
                try {
                    setCategory();
                    correctInput = true;
                } catch (IncorrectCommandFormatException e) {
                    userInfo.sendMessage(e.getMessage());
                } catch (IllegalArgumentException e) {
                    userInfo.sendMessage("There is no such AstartesCategory");
                }
            } while (!correctInput);

            correctInput = false;
            do {
                try {
                    setMeleeWeapon();
                    correctInput = true;
                } catch (IncorrectCommandFormatException e) {
                    userInfo.sendMessage(e.getMessage());
                } catch (IllegalArgumentException e) {
                    userInfo.sendMessage("There is no such MeleeWeapon");
                }
            } while (!correctInput);

            correctInput = false;
            do {
                try {
                    setChapter();
                    correctInput = true;
                } catch (InvalidValueException e) {
                    userInfo.sendMessage(e.getMessage());
                } catch (NumberFormatException e) {
                    userInfo.sendMessage("Incorrect number format");
                }
            } while (!correctInput);


            return new SpaceMarine(name, coordinates, health, loyal, category, meleeWeapon, chapter);
        }

        try {
            setName();
            setCoordinates();
            setHealth();
            setLoyal();
            setCategory();
            setMeleeWeapon();
            setChapter();
            return new SpaceMarine(name, coordinates, health, loyal, category, meleeWeapon, chapter);
        } catch (Exception e) {
            ConsoleManager.getCommandReader().setConsoleMod();
            replyUser("SCRIPT STOPPED: Command failed. Check correctness of the data");
            return null;
        }
    }



}
