package com.mrh.easyandroiddb.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.mrh.database.dao.AsyncDao;
import com.mrh.easyandroiddb.domains.Person;

/**
 * Created by haoxinlei on 2020/7/7.
 */
public class PersonDao extends AsyncDao<Person> {

    public PersonDao() {
        super("person");
    }

    @Override
    public ContentValues convertObject(Person obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", obj.name);
        contentValues.put("age", obj.age);
        contentValues.put("hobby", obj.hobby);
        contentValues.put("weight", obj.weight);
        return contentValues;
    }

    @Override
    public Person parseResult(Cursor cursor) {
        Person person = new Person();
        person.name = cursor.getString(cursor.getColumnIndex("name"));
        person.age = cursor.getInt(cursor.getColumnIndex("age"));
        person.hobby = cursor.getString(cursor.getColumnIndex("hobby"));
        person.weight = cursor.getInt(cursor.getColumnIndex("weight"));
        return person;
    }
}
