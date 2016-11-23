package testmodules.chestnut.bean;

import io.realm.RealmObject;

/**
 * Created by Chestnut on 2016/10/23.
 */

public class User extends RealmObject{

    private String name;
    private int age;
    private int sessionId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sessionId=" + sessionId +
                '}';
    }
}
