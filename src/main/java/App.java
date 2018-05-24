import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  	public static void main(String[] args) {
      ProcessBuilder process = new ProcessBuilder();
      Integer port;
      if (process.environment().get("PORT") != null) {
         port = Integer.parseInt(process.environment().get("PORT"));
      } else {
         port = 4567;
      }
      setPort(port);

    	staticFileLocation("/public");
    	String layout = "templates/layout.vtl";

    	get("/", (request, response) -> {
      	Map<String, Object> model = new HashMap<String, Object>();
      	model.put("template", "templates/index.vtl");
      	return new ModelAndView(model, layout);
    	}, new VelocityTemplateEngine());

      get("/sightings", (request, response) -> {
         Map<String, Object> model = new HashMap<String, Object>();

         
         model.put("template", "templates/sightings.vtl");
         return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());


      post("/newSighting", (request, response) -> {
         HashMap<String, Object> model = new HashMap<String, Object>();
         String ranger_name = request.queryParams("ranger_name");
         String animal_name = request.queryParams("animal_name");
         String endangered = request.queryParams("endangered");
         String location = request.queryParams("location");
         String animal_health = request.queryParams("animal_health");
         String animal_age = request.queryParams("animal_age");
         Animal newAnimal = new Animal(animal_name, endangered);
         model.put("template", "templates/sightings.vtl");

         if (endangered.equals("yes")) {
            if(newAnimal.checkEndangered(endangered, animal_health, animal_age)) {
               
               // newAnimal.setEndangered(endangered, health, age);
               // Sighting newSighting = new Sighting(ranger_name, location, newAnimal.getId());
               // newSighting.save();
            } else {
               response.redirect("/failure2");
            }
         }
         return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

    	
  	}
}