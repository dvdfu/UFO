package com.dvdfu.ufo;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Contacter implements ContactListener {
	
	public void beginContact(Contact contact) {
		Fixture fA = contact.getFixtureA();
		Fixture fB = contact.getFixtureB();
		if (fA.getUserData() != null && fA.getUserData().equals("ray")) {
			fB.getBody().setUserData("dead");
		} else if (fB.getUserData() != null && fB.getUserData().equals("ray")) {
			fA.getBody().setUserData("dead");
		}
	}

	public void endContact(Contact contact) {
	}

	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

}
