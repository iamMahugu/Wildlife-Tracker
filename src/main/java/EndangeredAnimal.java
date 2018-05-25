import org.sql2o.*;
import java.util.List;

public class EndangeredAnimal extends Animal {
  private String animal_health;
  private String animal_age;

  public static final String NEWBORN = "newborn";
  public static final String YOUNG = "young";
  public static final String ADULT = "adult";
  public static final String HEALTHY = "healthy";
  public static final String ILL = "ill";
  public static final String OKAY = "okay";


  public EndangeredAnimal(String name, String endangered, String health, String age) {
    super(name, endangered);
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
