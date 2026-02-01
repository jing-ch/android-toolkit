package edu.northeastern.numad26sp_jinghanchen;

public class Person {

    private final String name;

    private final int age;

    /**
     * Constructs a person object with the specified name and age.
     *
     * @param name - name to be given to the person.
     * @param age -  age of the person.
     */
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

