package fr.ubordeaux.m1.controller;

import fr.ubordeaux.m1.model.Formation;
import fr.ubordeaux.m1.model.FormationListener;
import fr.ubordeaux.m1.model.Session;

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

    public void sessionStateChanged(Formation formation, Session session, Session.EtatSession oldState, Session.EtatSession newState){

    }

    public void sessionFull(Formation formation, Session session){

    }
}
