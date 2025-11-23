package fr.ubordeaux.m1.model;

public interface FormationListener {
    
    void formationUpdated(Formation source);
    void formationAdded(Formation source);
    void formationRemoved(Formation source);

    void sessionAdded(Formation formation, Session session);
    void sessionRemoved(Formation formation, Session session);
}
