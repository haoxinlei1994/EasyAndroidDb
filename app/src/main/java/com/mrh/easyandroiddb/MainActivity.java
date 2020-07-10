package com.mrh.easyandroiddb;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mrh.database.listener.DBListener;
import com.mrh.easyandroiddb.dao.PersonDao;
import com.mrh.easyandroiddb.domains.Person;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PersonDao mPersonDao = new PersonDao();
    private TextView mContentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContentTv = findViewById(R.id.tv_content);
    }

    public void insertOne(View view) {
        Person tom = Person.buildTom();
        mPersonDao.insert(tom, new DBListener<Person>() {
            @Override
            public void onComplete(Person result) {
                Toast.makeText(MainActivity.this, "insert success", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void insertList(View view) {
        List<Person> persons = generatePersons();
        mPersonDao.insert(persons, new DBListener<List<Person>>() {
            @Override
            public void onComplete(List<Person> result) {
                Toast.makeText(MainActivity.this, "insert success", Toast.LENGTH_LONG).show();
            }
        });
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
//        //第一种删除方式
//        mPersonDao.delete("name = ?", new String[]{"tom"}, new DBListener<Person>() {
//            @Override
//            public void onComplete(Person result) {
//                Toast.makeText(MainActivity.this, "delete success", Toast.LENGTH_LONG).show();
//            }
//        });
        //第二种删除方式，使用建造者形式
        mPersonDao.newDeleter()
                .whereClause("name = ?")
                .whereArgs("tom")
                .singleResultListener(result -> Toast.makeText(MainActivity.this, "delete success", Toast.LENGTH_LONG).show())
                .delete();
    }

    public void deleteAll(View view) {
        mPersonDao.deleteAll();
    }

    public void updateTom(View view) {
        Person person = Person.buildTom();
        person.age++;
        //第一种更新方式
//        mPersonDao.update(person, "name = ?", new String[]{"tom"}, new DBListener<Person>() {
//            @Override
//            public void onComplete(Person result) {
//                Toast.makeText(MainActivity.this, "update finish", Toast.LENGTH_LONG).show();
//            }
//        });
        //第二种更新方式，使用建造者形式
        mPersonDao.newUpdater()
                .whereClause("name = ?")
                .whereArgs("tom")
                .singleResultListener(result -> Toast.makeText(MainActivity.this, "update finish", Toast.LENGTH_LONG).show())
                .update(person);
    }

    public void queryTom(View view) {
//        第一种查询方式
//        mPersonDao.queryOne("name = ?", new String[]{"tom"}, new DBListener<Person>() {
//            @Override
//            public void onComplete(Person result) {
//                mContentTv.setText(result.toString());
//            }
//        });
        //第二种查询方式,使用builder形式
        mPersonDao.newQuery()
                .whereClause("name = ?")
                .whereArgs("tom")
                .singleResultListener(result -> mContentTv.setText(result.toString()))
                .queryOne();
    }

    public void queryAll(View view) {
//        第一种查询方式
//        mPersonDao.queryAll(new DBListener<List<Person>>() {
//            @Override
//            public void onComplete(List<Person> result) {
//                StringBuilder builder = new StringBuilder();
//                for (int i = 0; i < result.size(); i++) {
//                    builder.append(result.get(i));
//                    builder.append("\n");
//                }
//                mContentTv.setText(builder.toString());
//            }
//        });
        //第二种查询方式，使用builder形式
        mPersonDao.newQuery()
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
