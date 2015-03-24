import java.util.*;

public class StarWar {

    private GameHelper helper = new GameHelper();
    private ArrayList<Ship> ships = new ArrayList<Ship>();
    private numOfGuesses = 0;

    private void setUpGame() {
        // first make some ships and give them location
        Ship one = new Ship();
        Ship two = new Ship();
        Ship three = new Ship();
        one.setName("Guangzhou");
        two.setName("Shanghai");
        three.setName("Beijing");
        ships.add(one);
        ships.add(two);
        ships.add(three);

        System.out.println("Your goal is to sink three ships.");
        System.out.println("Guangzhou, Shanghai, Beijing");
        System.out.println("Try to sink them all in the fewest number of guesses");

        for (Ship shipToSet : ships) {
            ArrayList<String> newLoc = helper.placeShip(3);
            shipToSet.setLocation(newLoc);
        }
    }

    private void startPlaying() {
        while (!ships.isEmpty()) {
            String userGuess = helper.getUserInput("Enter a guess");
            checkUserGuess(userGuess);
        }

        finishGame();
    }

    private void checkUserGuess(String userGuess) {
        numOfGuesses++;
        String result = "miss";

        for (int x = 0; x < ships.size(); x++) {
            result = ships.get(x).checkYourself(userGuess);
            if (result.equals("hit")) {
                break;
            }
            if (result.equals("kill")) {
                ships.remove(x);
                break;
            }
        }

        System.out.println(result);
    }


}