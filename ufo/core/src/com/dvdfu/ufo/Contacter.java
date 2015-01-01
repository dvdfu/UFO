package com.dvdfu.ufo;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.dvdfu.ufo.objects.GameObj;

public class Contacter implements ContactListener {

	public void beginContact(Contact contact) {
		Fixture fA = contact.getFixtureA();
		Fixture fB = contact.getFixtureB();
		if (fA != null && fB != null) {
			if (fA.getUserData() != null && fB.getUserData() != null) {
				GameObj goA = (GameObj) fA.getUserData();
				GameObj goB = (GameObj) fB.getUserData();
				goA.collide(goB);
				goB.collide(goA);
			}
		}
	}

	public void endContact(Contact contact) {
		Fixture fA = contact.getFixtureA();
		Fixture fB = contact.getFixtureB();
		if (fA != null && fB != null) {
			if (fA.getUserData() != null && fB.getUserData() != null) {
				GameObj goA = (GameObj) fA.getUserData();
				GameObj goB = (GameObj) fB.getUserData();
				goA.uncollide(goB);
				goB.uncollide(goA);
			}
		}
	}

	public void preSolve(Contact contact, Manifold oldManifold) {}

	public void postSolve(Contact contact, ContactImpulse impulse) {}

}
