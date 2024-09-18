/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final Map<Tuple<String, String>, Blueprint> blueprints = new HashMap<>();
    private Blueprint bpp;

    public InMemoryBlueprintPersistence() {
        // load stub data
        Point[] pts = new Point[]{new Point(140, 140), new Point(115, 115)};
        Point[] pts1 = new Point[]{new Point(14, 180), new Point(115, 15)};
        Point[] pts2 = new Point[]{new Point(100, 140), new Point(50, 115)};
        Blueprint bp = new Blueprint("Oscar", "plano_1", pts);
        Blueprint bp1 = new Blueprint("Camilo", "plano_2", pts1);
        Blueprint bp2 = new Blueprint("Camilo", "plano_3", pts2);
        blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);
    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
            throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
        } else {
            blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    public List<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        List<Blueprint> authorBlueprints = new ArrayList<>();

        for (Blueprint blueprint : blueprints.values()) {
            if (author.equals(blueprint.getAuthor())) {
                authorBlueprints.add(blueprint);
            }
        }

        if (authorBlueprints.isEmpty()) {
            throw new BlueprintNotFoundException("No blueprints found for author: " + author);
        }

        return authorBlueprints;
    }

    @Override
    public Blueprint getBlueprintByName(String name) throws BlueprintNotFoundException {
        for (Map.Entry<Tuple<String, String>, Blueprint> entry : blueprints.entrySet()) {
            Blueprint value = entry.getValue();
            if (name.equals(value.getName())) {
                return value;
            }
        }
        return null;

    }

    @Override
    public List<Blueprint> getAllBluePrints() {
        return new ArrayList<Blueprint>(blueprints.values());
    }

    @Override
    public Blueprint updateBlueprint(String author, String name, Blueprint blueprint)
            throws BlueprintNotFoundException {
        Tuple<String, String> key = new Tuple<>(author, name);
        if (blueprints.containsKey(key)) {
            blueprints.put(key, blueprint);
            return blueprint;
        } else {
            throw new BlueprintNotFoundException("The given blueprint does not exist: " + blueprint);
        }
    }

}
