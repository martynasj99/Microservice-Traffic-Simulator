package simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import simulator.model.*;
import simulator.model.network.Intersection;
import simulator.model.network.Street;
import simulator.model.view.MapView;
import simulator.service.MapService;

@RestController
@RequestMapping("/map")
public class MapController {

    @Autowired
    private MapService mapService;

    @CrossOrigin(origins = "http://localhost:8080")
    @MessageMapping("/map")
    @GetMapping("")
    public MapView getMap(){
        return mapService.getMap();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/intersections")
    public Iterable<Intersection> getIntersections(){
        return mapService.getIntersections();
    }

    @RequestMapping(value = "/intersection", params = "id", method = RequestMethod.GET)
    public Intersection getIntersectionById(@RequestParam Long id){
        return mapService.getIntersectionById(id);
    }


    @RequestMapping(value = "/intersection", params = "name", method = RequestMethod.GET)
    public Intersection getIntersectionByName(@RequestParam String name){
        return mapService.getIntersectionByName(name);
    }


    @GetMapping("/intersection/{id}/out")
    public Iterable<Street> getOutStreetsAtIntersection(@PathVariable String id){
        return mapService.getOutStreetsAtIntersection(id);
    }

    @GetMapping("/intersection/{id}/in")
    public Iterable<Street> getInStreetsAtIntersection(@PathVariable String id){
        return mapService.getInStreetsAtIntersection(id);
    }

    @GetMapping("/street/{id}")
    public Street getStreetById(@PathVariable Long id){
        return mapService.getStreetById(id);
    }

/*    @PostMapping("/add/{toNode}")
    public void add(@RequestBody Street street, @RequestBody Intersection intersection, @PathVariable Long toNode) throws Exception{
        mapService.addIntersectionStreet(intersection, street, toNode);
    }*/

    @PostMapping("/intersection/add")
    public void addIntersection(@RequestBody Intersection intersection){
        mapService.addIntersection(intersection);
    }

    @PostMapping("/street/add/{from}/{to}")
    public void addStreet(@PathVariable Long from, @PathVariable Long to, @RequestBody Street street) throws Exception{
        mapService.addStreet(from, to, street);
    }

    @GetMapping("/street/{from}/{to}")
    public Street getStreetBetweenTwoNodes(@PathVariable String from, @PathVariable String to){
        return mapService.getStreetBetweenTwoIntersections(from, to);
    }

    @PutMapping("/traffic-lights")
    public void updateTrafficLights(@RequestBody TrafficLightStatus trafficLightStatus){
        mapService.setTrafficLightStatus(trafficLightStatus.getStatus());
    }
}
