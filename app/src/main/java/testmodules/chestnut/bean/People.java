package testmodules.chestnut.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by Chestnut on 2016/10/6.
 */
@Table("People")
public class People {

    @PrimaryKey(AssignType.AUTO_INCREMENT)	//主键
    @Column("_id")
    private int id;

    @NotNull
    private String name;
    @NotNull
    private String sex;
    @NotNull
    private int year;

    public People(String name, String sex, int year) {
        this.name = name;
        this.sex = sex;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", year=" + year +
                '}';
    }
}
