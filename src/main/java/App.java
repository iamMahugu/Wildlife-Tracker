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
         model.put("sightings", Sighting.all());
         model.put("animals", Animal.all());
         model.put("AnimalClass", Animal.class);
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

         if (endangered.equals("yes")) {
            if(newAnimal.checkEndangered(endangered, animal_health, animal_age)) {
               newAnimal.save();
               newAnimal.setAsEndangered(endangered, animal_health, animal_age);
               Sighting newSighting = new Sighting(ranger_name, location, newAnimal.getId());
               newSighting.save();
            } else {
               response.redirect("/endangeredNotAdded");
            }
         } else if (endangered.equals("no")) {
            if(newAnimal.checkInput(animal_name)) {
            newAnimal.save();
            Sighting newSighting = new Sighting(ranger_name, location, newAnimal.getId());
            newSighting.save();
            } else {
               response.redirect("/noAnimalName");
            }
         }

         model.put("template", "templates/sightings.vtl");
         return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/endangeredNotAdded", (request, response) -> {
         Map<String, Object> model = new HashMap<String, Object>();
         model.put("template", "templates/endangeredNotAdded.vtl");
         return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());


      get("/noAnimalName", (request, response) -> {
         Map<String, Object> model = new HashMap<String, Object>();
         model.put("template", "templates/noAnimalName.vtl");
         return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());
    	
  	}
}