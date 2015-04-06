import java.util.*;

public class Ship {

    private ArrayList<String> location;
    private String name;

    public void setLocation(ArrayList<String> loc) {
        location = loc;
    }

    public void setName(String n) {
        name = n;
    }

    public String checkYourself(String userGuess) {
        String result = "miss";
        int index = location.indexOf(userGuess);

        if (index >= 0) {
            location.remove(index);
            if (location.isEmpty()) {
                result = "kill";   
                System.out.println("You sunk " + name + ":(");
            } else {
                result = "hit";
            }
        }
        return result;
    }
}

