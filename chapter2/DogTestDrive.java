class Dog {

    int size;
    String breed;
    String name;

    void bark() {
        System.out.println("Hello, world!");
    }
}

class DogTestDrive {

    public static void main (String[] args) {
        Dog d = new Dog();
        d.size = 10;
        d.bark();
    }
}