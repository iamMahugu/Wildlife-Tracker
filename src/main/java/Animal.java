import org.sql2o.*;
import java.util.List;

public class Animal {
   public String animal_name;
   public String endangered;
   public int id;

   public Animal(String name, String endangered) {
      this.animal_name = name;
      this.endangered = endangered;
   }

   public String getName() {
      return animal_name;
   }

   public String getEndangered() {
      return endangered;
   }

   public int getId() {
      return id;
   }

   public boolean checkEndangered(String endangered, String health, String age) {
      if (endangered.equals("") || health.equals("") || age.equals("")) {
         throw new IllegalArgumentException("Please enter details for endangered animal.");
      }
      else {
         return true;
      }
   }

   public void save() {
      try(Connection con = DB.sql2o.open()) {
         String sql = "INSERT INTO animals (animal_name, endangered) VALUES (:animal_name, :endangered)";
         this.id = (int) con.createQuery(sql, true)
        .addParameter("animal_name", this.animal_name)
        .addParameter("endangered", this.endangered)
        .throwOnMappingFailure(false)
        .executeUpdate()
        .getKey();
      }
   }

   public void setAsEndangered(String endangered, String animal_health, String animal_age) {
      try(Connection con = DB.sql2o.open()) {
         String sql = "UPDATE animals SET endangered = :endangered, animal_health = :animal_health, animal_age = :animal_age WHERE id = :id";
         con.createQuery(sql)
         .addParameter("endangered", endangered)
         .addParameter("animal_health", animal_health)
         .addParameter("animal_age", animal_age)
         .addParameter("id", id)
         .executeUpdate();
      }
   }

   public boolean checkInput(String animal_name) {
      if (animal_name.equals("")) {
         throw new IllegalArgumentException("Please enter animal name.");
      }
      else {
         return true;
      }
   }

   public static List<Animal> all() {
      String sql = "select * from animals";
      try(Connection con = DB.sql2o.open()) {
         return con.createQuery(sql)
         .throwOnMappingFailure(false)
         .executeAndFetch(Animal.class);
      }
   }

   public static Animal find(int id) {
      try(Connection con = DB.sql2o.open()) {
         String sql = "SELECT * FROM animals WHERE id=:id";
         Animal blog = con.createQuery(sql)
         .addParameter("id", id)
         .executeAndFetchFirst(Animal.class);
         return blog;
      }
   }

   public static String getAnimalName(int id) {
      try(Connection con = DB.sql2o.open()) {
         String sql = "SELECT name FROM animals WHERE id = :id;";
         String name = con.createQuery(sql)
         .addParameter("id", id)
         .executeAndFetch(String.class);
      }
   }

   public static String getAnimalEndangered(int id) {
      try(Connection con = DB.sql2o.open()) {
         String sql = "SELECT endangered FROM animals WHERE id = :id;";
         String name = con.createQuery(sql)
         .addParameter("id", id)
         .executeAndFetch(String.class);
      }
   }

   public static String getAnimalHealth(int id) {
      try(Connection con = DB.sql2o.open()) {
         String sql = "SELECT health FROM animals WHERE id = :id;";
         String name = con.createQuery(sql)
         .addParameter("id", id)
         .executeAndFetch(String.class);
      }
   }

   public static String getAnimalAge(int id) {
      try(Connection con = DB.sql2o.open()) {
         String sql = "SELECT age FROM animals WHERE id = :id;";
         String name = con.createQuery(sql)
         .addParameter("id", id)
         .executeAndFetch(String.class);
      }
   }

   @Override
   public boolean equals(Object otherAnimal) {
      if (!(otherAnimal instanceof Animal)) {
         return false;
      } else {
         Animal newAnimal = (Animal) otherAnimal;
         return this.getName().equals(newAnimal.getName());
      }
   }
}
