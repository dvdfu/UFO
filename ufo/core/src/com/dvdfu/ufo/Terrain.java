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
	private final int nodes = 40;
	private final int res = 4;
	private final float var = 4;
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
		float mu, n1, n2, n3, n4;
		for (int i = 0; i < map.length; i++) {
			if (i % res > 0) { // not a node
				node = i / res;
				mu = 1f * i / res - node;
				n1 = node > 0 ? nodeMap[node - 1] : nodeMap[0];
				n2 = nodeMap[node];
				n3 = node < nodes - 1 ? nodeMap[node + 1] : nodeMap[nodes];
				n4 = node < nodes - 2 ? nodeMap[node + 2] : nodeMap[nodes];
				map[i] = interCubic(n1, n2, n3, n4, mu);
			} else {
				map[i] = nodeMap[i / res];
			}
		}
	}

	private float interLin(float y1, float y2, float mu) {
		return y1 * (1 - mu) + y2 * mu;
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
			for (int j = 0; j < 100 / res + 1; j++) {
				sprite.setSize(1, (var + 50) * 10);
				sprite.draw(batch, i * 100 / res + j, (interLin(map[i], map[i + 1], j * res / 100f) - var - 50) * 10);
			}
		}
	}

	public void buildBody() {
		nodeMap = new float[nodes + 1];
		map = new float[nodes * res + 1];
		buildNodeMap();
		buildMap();
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		PolygonShape bodyShape = new PolygonShape();
		Vector2[] vertices = new Vector2[4];
		for (int i = 0; i < map.length - 1; i++) {
			vertices[0] = new Vector2(i * 10f / res, map[i]);
			vertices[1] = new Vector2((i + 1) * 10f / res, map[i + 1]);
			vertices[2] = new Vector2(i * 10f / res, map[i] - var - 50);
			vertices[3] = new Vector2((i + 1) * 10f / res, map[i + 1] - var - 50);
			bodyShape.set(vertices);
			body = world.createBody(bodyDef);
			body.createFixture(bodyShape, 1);
		}
		bodyShape.dispose();
	}

	public float getHeight(float x) {
		x *= res / 10f;
		int realX = MathUtils.clamp((int) x, 0, nodes * res - 1);
		return interLin(map[realX], map[realX + 1], x % 1);
	}

	public void collide(GameObj object) {}
}
