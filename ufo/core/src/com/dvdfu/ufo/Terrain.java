package com.dvdfu.ufo;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.dvdfu.ufo.components.SpriteComponent;
import com.dvdfu.ufo.objects.GameObj;

public class Terrain extends GameObj {
	private SpriteComponent sprite;
	private int nodes;
	private int res;
	private float var;
	private float[] nodeMap;
	private float[] map;

	public Terrain(World world) {
		super(world);
		sprite = new SpriteComponent(Const.atlas.findRegion("ground"));
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
				// nodeMap[1] = nodeMap[0] + MathUtils.random(-var, var);
				nodeMap[1] = MathUtils.random(-var, var);
			} else {
				// nodeMap[0] = MathUtils.random(-var, var);
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
				mu = 1f * i / res - node;
				if (node > 1 && node < nodes - 2) {
					map[i] = interCubic(nodeMap[node - 1], nodeMap[node], nodeMap[node + 1], nodeMap[node + 2], mu);
				} else {
					map[i] = interCos(nodeMap[node], nodeMap[node + 1], mu);
				}
			} else {
				map[i] = nodeMap[i / res];
			}
		}
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

	public void update() {}

	public void draw(SpriteBatch batch) {
		for (int i = 0; i < map.length - 1; i++) {
			sprite.setSize(10, (var + 50) * 10);
			sprite.draw(batch, i * 10, (Math.min(map[i], map[i + 1]) - var - 50) * 10);
		}
	}

	public void buildBody() {
		nodes = 40;
		res = 20;
		var = 10;
		nodeMap = new float[nodes + 1];
		map = new float[nodes * res + 1];
		buildNodeMap();
		buildMap();
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		PolygonShape bodyShape = new PolygonShape();
		Vector2[] vertices = new Vector2[4];
		for (int i = 0; i < map.length - 1; i++) {
			vertices[0] = new Vector2(i, map[i]);
			vertices[1] = new Vector2(i + 1, map[i + 1]);
			vertices[2] = new Vector2(i, Math.min(map[i], map[i + 1]) - var);
			vertices[3] = new Vector2(i + 1, Math.min(map[i], map[i + 1]) - var);
			bodyShape.set(vertices);
			body = world.createBody(bodyDef);
			body.createFixture(bodyShape, 1);
		}
		bodyShape.dispose();
	}
	
	public float getHeight(float x) {
		int realX = MathUtils.clamp((int) (x + 0.5f), 0, nodes * res);
		return map[realX];
	}
}
