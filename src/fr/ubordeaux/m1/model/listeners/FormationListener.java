package fr.ubordeaux.m1.model.listeners;

import fr.ubordeaux.m1.model.entities.Formation;
import fr.ubordeaux.m1.model.entities.Session;
import fr.ubordeaux.m1.model.states.SessionState;

public interface FormationListener {
    
    void formationUpdated(Formation source);
    void formationAdded(Formation source);
    void formationRemoved(Formation source);

    void sessionAdded(Formation formation, Session session);
    void sessionRemoved(Formation formation, Session session);
}
