package testmodules.chestnut.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by Chestnut on 2016/10/23.
 */

@Table("User")
public class User {

    @PrimaryKey(AssignType.AUTO_INCREMENT)	//主键
    @Column("_id")
    public int uid;
    public String name;
    public int age;
    public int sessionId;

    public User(int uid, String name, int age, int sessionId) {
        this.uid = uid;
        this.name = name;
        this.age = age;
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sessionId=" + sessionId +
                '}';
    }
}
