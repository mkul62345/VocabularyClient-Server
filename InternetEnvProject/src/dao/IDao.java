package dao;

import java.io.IOException;

public interface IDao<String> {

    Object find(String key) throws IllegalArgumentException, IOException;
    boolean save(String key,String value) throws IllegalArgumentException;
}


