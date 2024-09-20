package edu.eci.arsw.blueprints.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    BlueprintsServices bps;

    @GetMapping("/{author}/{bpname}")
    public ResponseEntity<?> getBlueprint(@PathVariable("author") String author,
                                          @PathVariable("bpname") String bpname) {
        try {
            return new ResponseEntity<>(bps.getBlueprint(author, bpname), HttpStatus.OK);
        } catch (BlueprintNotFoundException ex) {
            return new ResponseEntity<>("Blueprint not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{author}")
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable("author") String author) {
        try {
            return new ResponseEntity<>(bps.getBlueprintsByAuthor(author), HttpStatus.OK);
        } catch (BlueprintNotFoundException ex) {
            return new ResponseEntity<>("No blueprints found for author: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllBlueprints() {
        return new ResponseEntity<>(bps.getAllBlueprints(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addNewBlueprint(@RequestBody Blueprint bp) {
        try {
            bps.addNewBlueprint(bp);
            return new ResponseEntity<>(bp, HttpStatus.CREATED);
        } catch (BlueprintPersistenceException ex) {
            return new ResponseEntity<>("Error creating blueprint: " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{author}/{bpname}")
    public ResponseEntity<?> updateBlueprint(@PathVariable("author") String author,
                                             @PathVariable("bpname") String bpname,
                                             @RequestBody Blueprint bp) {
        try {
            Blueprint updatedBp = bps.updateBlueprint(author, bpname, bp);
            return new ResponseEntity<>(updatedBp, HttpStatus.OK);
        } catch (BlueprintNotFoundException ex) {
            return new ResponseEntity<>("Blueprint not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}