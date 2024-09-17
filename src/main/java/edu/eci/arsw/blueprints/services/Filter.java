package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import org.springframework.stereotype.Service;

@Service
public interface Filter {

    public abstract Blueprint filterPlain(Blueprint blueprint);
}
