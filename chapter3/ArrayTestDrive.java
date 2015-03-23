class Dog {

    int size;
    String breed;
    String name;

    void bark() {
        System.out.println("Hello, world!");
    }
}

class ArrayTestDrive {

    public static void main (String[] args) {
        int[] myint = new int[3];
        Dog[] myDogs = new Dog[3];

        // should output 0
        System.out.println("int[0]:");
        System.out.println(myint[0]);

        // should output null
        System.out.println("dogs[0]:");
        System.out.println(myDogs[0]);

    }
}