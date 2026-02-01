package edu.northeastern.numad26sp_jinghanchen;

public class Person {

    private final String name;

    private final String url;

    /**
     * Constructs a person object with the specified name and url.
     *
     * @param name - name to be given to the person.
     * @param url -  url of the person.
     */
    public Person(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}

