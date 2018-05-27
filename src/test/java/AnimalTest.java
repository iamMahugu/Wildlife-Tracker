import java.time.LocalDateTime;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class AnimalTest {

   @Before
   public void setUp() {
      DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", "yomz", "@dZumi0991");
   }

   @After
   public void tearDown() {
      try(Connection con = DB.sql2o.open()) {
         String deleteClientsQuery = "DELETE FROM animals *;";
         String deleteStylistsQuery = "DELETE FROM sightings *;";
         con.createQuery(deleteClientsQuery).executeUpdate();
         con.createQuery(deleteStylistsQuery).executeUpdate();
      }
   }

   
      
}
