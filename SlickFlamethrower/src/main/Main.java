package main;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;


public class Main extends BasicGame {

	private ParticleSystem particles;
	private ConfigurableEmitter emitter;
	
	CustomMouse mouse;
	
	private int cd = 0;
	public final static int maxCD = 100;
	
	public Main(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		particles.render();
		g.drawString(emitter.angularOffset.getValue(0f)+"°", 10, 25);
		g.drawString("Schwerkraft: " + emitter.gravityFactor.getValue(0), 10, 40);
		g.drawString("Wind: " + emitter.windFactor.getValue(0),10,55);
		g.drawString("Flammen Partikel: " + particles.getParticleCount(), 10, 80);
		//mouse renders string at 10;95
		
		mouse.render(container, g);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		particles = new ParticleSystem("Data/particle.png",1500);
		
		try {
			emitter = ParticleIO.loadEmitter("Data/test_emitter.xml");
			emitter.setPosition(400, 300);
			particles.addEmitter(emitter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mouse = new CustomMouse().init(container);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if(cd>0) cd--;
		if(cd <= 0){
			float valToAdd = container.getInput().isKeyDown(Input.KEY_LSHIFT)?1f:0.1f;
			if(container.getInput().isKeyDown(Input.KEY_UP)){ emitter.gravityFactor.setValue(emitter.gravityFactor.getValue(0)-valToAdd); cd = maxCD;}
			if(container.getInput().isKeyDown(Input.KEY_DOWN)){ emitter.gravityFactor.setValue(emitter.gravityFactor.getValue(0)+valToAdd); cd = maxCD;}
			if(container.getInput().isKeyDown(Input.KEY_RIGHT)){ emitter.windFactor.setValue(emitter.windFactor.getValue(0)+valToAdd); cd = maxCD;}
			if(container.getInput().isKeyDown(Input.KEY_LEFT)){ emitter.windFactor.setValue(emitter.windFactor.getValue(0)-valToAdd); cd = maxCD;}
		}
		float tx = container.getInput().getAbsoluteMouseX();
		float ty = container.getInput().getAbsoluteMouseY();
		
		if(container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
			emitter.setPosition(tx, ty);
		
		float sx = emitter.getX();
		float sy = emitter.getY();
		
		float dy = ty-sy;
		float dx = tx-sx;
		
		float w = dx==0?0:(float)Math.toDegrees(Math.atan(dy/dx));
		w+=90;
		
		if(tx < sx)
			w+=180;
		emitter.angularOffset.setValue(w);
		
		particles.update(delta);
		
		mouse.update(container, delta);
	}
	
	public static void main(String[] args) throws SlickException{
		AppGameContainer container = new AppGameContainer(new Main("Partikel"));
		container.setDisplayMode(800, 600, false);
		container.setAlwaysRender(true);
		
		container.start();
	}
}
