package com.dvdfu.ufo.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.dvdfu.ufo.Const;
import com.dvdfu.ufo.components.ImageComponent;
import com.dvdfu.ufo.components.SpriteComponent;

public class Tree {
	public Body tree;
	private float height;
	public SpriteComponent sprite;
	
	public Tree(World world, float width, float height, float radius) {
		BodyDef treeDef = new BodyDef();
		treeDef.type = BodyType.DynamicBody;
		this.height = height;
		
		PolygonShape trunkShape = new PolygonShape();
		trunkShape.setAsBox(width / 2, height / 2, new Vector2(0, height / 2), 0);
		CircleShape leafShape = new CircleShape();
		leafShape.setRadius(radius);
		leafShape.setPosition(new Vector2(0, height));
		
		tree = world.createBody(treeDef);
		tree.createFixture(trunkShape, 1);
		tree.createFixture(leafShape, 0.1f);

		trunkShape.dispose();
		leafShape.dispose();
		
		sprite = new SpriteComponent(new ImageComponent(Const.atlas.findRegion("leaves"), 32));
	}
	
	public void draw(SpriteBatch batch) {
		Vector2 p = tree.getWorldPoint(new Vector2(0, height));
		sprite.drawCentered(batch, p.x * 10, p.y * 10);
	}
}
