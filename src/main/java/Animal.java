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
      try {
         this.save(endangered, health, age);
         return true;
      } catch (IllegalArgumentException exception) {
         return false;
      }
   }

   public void save(String endangered, String animal_name, String animal_age) {
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

   // public void animalIsEndangered(String endangered, String health, String age) {
   //    try(Connection con = DB.sql2o.open()) {
   //       String sql = "UPDATE animals SET endangered = :endangered, animal_health = :animal_health, animal_age = :animal_age WHERE id = :id";
   //       con.createQuery(sql)
   //      .addParameter("endangered", endangered)
   //      .addParameter("health", health)
   //      .addParameter("age", age)
   //      .addParameter("id", id)
   //      .executeUpdate();
   //    }
   // }
}
