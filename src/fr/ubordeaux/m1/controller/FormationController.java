package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.model.entities.Formation;
import fr.ubordeaux.m1.model.entities.Session;
import fr.ubordeaux.m1.model.listeners.FormationListener;
import fr.ubordeaux.m1.model.states.SessionState;

public class FormationController implements FormationListener {
    public void formationUpdated(Formation source){

    }

    public void formationAdded(Formation source){

    }

    public void formationRemoved(Formation source){

    }

    public void sessionAdded(Formation formation, Session session){

    }

    public void sessionRemoved(Formation formation, Session session){

    }

    public void sessionUpdated(Formation formation, Session session){
        
    }

    public void sessionStateChanged(Formation formation, Session session, SessionState oldState, SessionState newState){

    }

    public void sessionFull(Formation formation, Session session){

    }
}
