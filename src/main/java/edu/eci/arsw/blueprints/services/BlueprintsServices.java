package edu.eci.arsw.blueprints.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

@Service
public class BlueprintsServices {

    @Autowired
    BlueprintsPersistence bpp;

    @Autowired
    Filter filter;

    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.saveBlueprint(bp);
    }

    public List<Blueprint> getAllBlueprints() {
        return bpp.getAllBluePrints();
    }

    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        return bpp.getBlueprint(author, name);
    }

    public List<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        return bpp.getBlueprintsByAuthor(author);
    }

    public Blueprint getBlueprintsByName(String name) throws BlueprintNotFoundException {
        return bpp.getBlueprintByName(name);
    }

    public Blueprint updateBlueprint(String author, String name, Blueprint blueprint) throws BlueprintNotFoundException {
        return bpp.updateBlueprint(author, name, blueprint);
    }

    public Blueprint filterBlueprint(Blueprint blueprint) {
        return filter.filterPlain(blueprint);
    }
}