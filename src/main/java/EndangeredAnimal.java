import org.sql2o.*;
import java.util.List;

public class EndangeredAnimal extends Animal {
  private String animal_health;
  private String animal_age;

  public static final String HEALTH_HEALTHY = "healthy";
  public static final String HEALTH_ILL = "ill";
  public static final String HEALTH_OKAY = "okay";

  public static final String AGE_NEWBORN = "newborn";
  public static final String AGE_YOUNG = "young";
  public static final String AGE_ADULT = "adult";

  public EndangeredAnimal(String name, String endangered, String health, String age) {
    super(animal_name, endangered);
    this.animal_health = health;
    this.animal_age = age;
  }

  public String getHealth() {
    return animal_health;
  }

  public String getAge() {
    return animal_age;
  }

  public static EndangeredAnimal find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE id=:id";
      EndangeredAnimal blog = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(EndangeredAnimal.class);
      return blog;
    }
  }
}
