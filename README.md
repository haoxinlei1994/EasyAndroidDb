Easy Android ORM database for learnning what is ORM, support sync and async CRUD、Compile time annotation and version upgrade with sql style api. 


# how to use
## 1. init database
In your Application's onCreate method:

```
new EasySQLiteHelper.Builder(this)
                .dbName("easy.db")
                .dbVersion(1)
                .build();
```

## 2. create a table
Under your app's assets dir, create a file named "create.sql", then you can write all your standard “create table sql clause” in this file. This file will be executed when app first run.

```
create Table person (
    name TEXT,
    age INTEGER
);
```

## 3. create a Person.java
The Person.java will be auto related to the person table with Annotation. Actually, there will auto generate a PersonDao.java.

```
@Table(tableName = "person", isAsync = true)
public class Person {
    @Column("name")
    public String name;
    @Column("age")
    public int age;
}
```

## 4. make your project
When you finish Person.java, make your project, then the db-compiler will auto compile the custom annotation and generate a PersonDao.java for Person.java, now you can use PersonDao to CRUD.

# CRUD API
## 1. insert

```
AsyncSQLiteDb.insertFrom(PersonDao.class)
                .singleResultListener(person -> Toast.makeText(MainActivity.this, "insert success", Toast.LENGTH_LONG).show())
                .insert(Person.buildTom());
```

## 2. delete

```
AsyncSQLiteDb.deleteFrom(PersonDao.class)
                .whereClause("name=?")
                .whereArgs("tom")
                .singleResultListener(person -> Toast.makeText(MainActivity.this, "delete success", Toast.LENGTH_LONG).show())
                .delete();
```


## 3. update


```
AsyncSQLiteDb.updateFrom(PersonDao.class)
                .whereClause("name=?")
                .whereArgs("tom")
                .singleResultListener(person -> Toast.makeText(MainActivity.this, "update success", Toast.LENGTH_LONG).show())
                .update(person);
```

## 4. query

```
AsyncSQLiteDb.queryFrom(PersonDao.class)
                .whereClause("name=?")
                .whereArgs("tom")
                .singleResultListener(person -> mContentTv.setText(person == null ? "no result" : person.toString()))
                .queryOne();
```


# database upgrade

```
--assets
	|--upgrade
		|--upgrade_2.sql
		|--upgrade_3.sql
	|--create.sql
```

When a new user first install and run app, the create.sql will be executed. when a old user install and run a high version app, the create.sql will not be executed, 
but the upgrade.sql files will be executed one by one according to you app's database version.
If you want to update person table, such as add a "hobby" column, then create a upgrade_2.sql file under assets/upgrade dir.

```
ALTER TABLE person ADD COLUMN hobby Text;
```

then upgrade the version to 2 in application:

```
new EasySQLiteHelper.Builder(this)
                .dbName("easy.db")
                .dbVersion(2)
                .build();
```

finally, update the Person.java

```
@Table(tableName = "person", isAsync = true)
public class Person {
    @Column("name")
    public String name;
    @Column("age")
    public int age;
    @Column("hobby")
    public String hobby;
}
```

make your project to generate new PersonDao.java and do something you want，the next time app run，upgrade_2.sql will be executed automatically. And the version of database is 2 now!
