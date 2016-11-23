package testmodules.chestnut;

import android.content.Context;

import java.util.List;

/**
 * Created by Chestnut on 2016/11/12.
 * 规范定义了DB的行为：
 *
 */

public interface DB {
    DB getInstance();
    DB init();
    boolean add(Object o);
    boolean addOrUpdate(Object o);
    boolean update(Object o);
    boolean delete(Object o);
    List<Object> find(Object rules);
}
