package com.dvdfu.ufo;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Terrain {
	private World world;
	private int nodes;
	private int res;
	private float var;
	private float[] nodeMap;
	private float[] map;

	public Terrain(World world) {
		this.world = world;
		nodes = 20;
		res = 5;
		var = 2;
		nodeMap = new float[nodes + 1];
		map = new float[nodes * res + 1];
		buildNodeMap();
		buildMap();
		buildBodies();
	}

	private void buildNodeMap() {
		float rise;
		float lower, upper;
		for (int i = 0; i <= nodes; i++) {
			if (i > 1) {
				rise = nodeMap[i - 1] - nodeMap[i - 2];
				if (rise >= 0) {
					lower = -rise / 2;
					upper = var;
				} else {
					lower = -var;
					upper = -rise / 2;
				}
				nodeMap[i] = nodeMap[i - 1] + MathUtils.random(lower, upper);
			} else if (i == 1) {
//				nodeMap[1] = nodeMap[0] + MathUtils.random(-var, var);
				nodeMap[1] = MathUtils.random(-var, var);
			} else {
//				nodeMap[0] = MathUtils.random(-var, var);
				nodeMap[0] = 0;
			}
		}
	}

	private void buildMap() {
		int node;
		float mu;
		for (int i = 0; i < map.length; i++) {
			if (i % res > 0) { // not a node
				node = i / res;
				System.out.println(node);
				mu = 1f * i / res - node;
				if (node > 1 && node < nodes - 2) {
					map[i] = interCubic(nodeMap[node - 1], nodeMap[node],
							nodeMap[node + 1], nodeMap[node + 2], mu);
				} else {
					map[i] = interCos(nodeMap[node], nodeMap[node + 1], mu);
				}
			} else {
				map[i] = nodeMap[i / res];
			}
		}
	}
	
	private void buildBodies() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		PolygonShape bodyShape = new PolygonShape();
		Vector2[] vertices = new Vector2[4];
		for (int i = 0; i < map.length - 1; i++) {
			vertices[0] = new Vector2(i - 50, map[i]);
			vertices[1] = new Vector2(i + 1 - 50, map[i + 1]);
			vertices[2] = new Vector2(i - 50, Math.min(map[i], map[i + 1]) - var);
			vertices[3] = new Vector2(i + 1 - 50, Math.min(map[i], map[i + 1]) - var);
			bodyShape.set(vertices);
			Body body = world.createBody(bodyDef);
			body.createFixture(bodyShape, 1);
		}
		bodyShape.dispose();
	}

	private float interCos(float y1, float y2, float mu) {
		float mu2 = (1 - MathUtils.cos(mu * MathUtils.PI)) / 2;
		return y1 * (1 - mu2) + y2 * mu2;
	}

	private float interCubic(float y0, float y1, float y2, float y3, float mu) {
		float a0, a1, a2, a3, mu2;
		mu2 = mu * mu;
		a0 = y3 - y2 - y0 + y1;
		a1 = y0 - y1 - a0;
		a2 = y2 - y0;
		a3 = y1;
		return a0 * mu * mu2 + a1 * mu2 + a2 * mu + a3;
	}

	public float[] getMap() {
		return map;
	}
}
