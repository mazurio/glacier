package io.mazur.glacier.file.model;

import java.io.Serializable;

public class User implements Serializable {
    public String mName;
    public String mSurname;

    public User(String name, String surname) {
        mName = name;
        mSurname = surname;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getName() {
        return mName;
    }

    public void setSurname(String mSurname) {
        this.mSurname = mSurname;
    }

    public String getSurname() {
        return mSurname;
    }
}
