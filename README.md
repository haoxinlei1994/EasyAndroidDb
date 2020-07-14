Android ORM database, support Async CRUD、 Annotation and version upgrade. There are two kinds of easy API to CRUD.

# install
xxx
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

```
PersonDao mPersonDao = new PersonDao();
```

## 1. insert

```
Person tom = Person.buildTom();
mPersonDao.insert(tom, new DBListener<Person>() {
            @Override
            public void onComplete(Person result) {
                Toast.makeText(MainActivity.this, "insert success", Toast.LENGTH_LONG).show();
            }
        });
```

## 2. delete

```
mPersonDao.newDeleter()
                .whereClause("name = ?")
                .whereArgs("tom")
                .singleResultListener(result -> Toast.makeText(MainActivity.this, "delete success", Toast.LENGTH_LONG).show())
                .delete();
```

or

```
mPersonDao.delete("name = ?", new String[]{"tom"}, new DBListener<Person>() {
            @Override
            public void onComplete(Person result) {
                Toast.makeText(MainActivity.this, "delete success", Toast.LENGTH_LONG).show();
            }
        });
```

## 3. update

```
Person person = Person.buildTom();
person.age++;
mPersonDao.newUpdater()
                .whereClause("name = ?")
                .whereArgs("tom")
                .singleResultListener(result -> Toast.makeText(MainActivity.this, "update finish", Toast.LENGTH_LONG).show())
                .update(person);
```

or

```
mPersonDao.update(person, "name = ?", new String[]{"tom"}, new DBListener<Person>() {
            @Override
            public void onComplete(Person result) {
                Toast.makeText(MainActivity.this, "update finish", Toast.LENGTH_LONG).show();
            }
        });
```

## 4. query

```
mPersonDao.newQuery()
                .whereClause("name = ?")
                .whereArgs("tom")
                .singleResultListener(person -> mContentTv.setText(person.toString()))
                .queryOne();
```

or

```
mPersonDao.queryOne("name = ?", new String[]{"tom"}, new DBListener<Person>() {
            @Override
            public void onComplete(Person person) {
                mContentTv.setText(person.toString());
            }
        });
```

# database upgrade

```
--assets
	|--upgrade
		|--upgrade_2.sql
		|--upgrade_3.sql
	|--create.sql
```

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