package com.mrh.easyandroiddb;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mrh.database.crud.AsyncSQLiteDb;
import com.mrh.easyandroiddb.domains.Person;
import com.mrh.easyandroiddb.domains.PersonDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mContentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContentTv = findViewById(R.id.tv_content);
    }

    public void insertOne(View view) {
        AsyncSQLiteDb.insertFrom(PersonDao.class)
                .singleResultListener(person -> Toast.makeText(MainActivity.this, "insert success", Toast.LENGTH_LONG).show())
                .insert(Person.buildTom());
    }

    public void insertList(View view) {
        AsyncSQLiteDb.insertFrom(PersonDao.class)
                .multipleResultListener(result -> Toast.makeText(MainActivity.this, "insert success", Toast.LENGTH_LONG).show())
                .insert(generatePersons());
    }

    private List<Person> generatePersons() {
        List<Person> persons = new ArrayList<>();
        Person tom = Person.buildTom();
        Person jerry = Person.buildJerry();
        persons.add(tom);
        persons.add(jerry);
        return persons;
    }

    public void deleteTom(View view) {
        AsyncSQLiteDb.deleteFrom(PersonDao.class)
                .whereClause("name=?")
                .whereArgs("tom")
                .singleResultListener(person -> Toast.makeText(MainActivity.this, "delete success", Toast.LENGTH_LONG).show())
                .delete();
    }

    public void deleteAll(View view) {
        AsyncSQLiteDb.deleteFrom(PersonDao.class)
                .singleResultListener(person -> Toast.makeText(MainActivity.this, "delete success", Toast.LENGTH_LONG).show())
                .deleteAll();
    }

    public void updateTom(View view) {
        Person person = Person.buildTom();
        person.age++;
        AsyncSQLiteDb.updateFrom(PersonDao.class)
                .whereClause("name=?")
                .whereArgs("tom")
                .singleResultListener(result -> Toast.makeText(MainActivity.this, "update success", Toast.LENGTH_LONG).show())
                .update(person);
    }

    public void queryTom(View view) {
        AsyncSQLiteDb.queryFrom(PersonDao.class)
                .whereClause("name=?")
                .whereArgs("tom")
                .singleResultListener(result -> mContentTv.setText(result == null ? "no result" : result.toString()))
                .queryOne();
    }

    public void queryAll(View view) {
        AsyncSQLiteDb.queryFrom(PersonDao.class)
                .multipleResultListener(result -> {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < result.size(); i++) {
                        builder.append(result.get(i));
                        builder.append("\n");
                    }
                    mContentTv.setText(builder.toString());
                })
                .queryAll();
    }
}
