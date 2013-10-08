package main;

import java.io.IOException;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class CustomMouse {
	ParticleSystem particles;
	ConfigurableEmitter emitter;
	
	public CustomMouse init(GameContainer container) throws SlickException {
		
		//Make Mouse transparent
		try {
		Cursor emptyCursor = new Cursor(1, 1, 0, 0, 1, BufferUtils.createIntBuffer(1), null);
			Mouse.setNativeCursor(emptyCursor);
		} catch (LWJGLException e1) {
			e1.printStackTrace();
		}
		
		particles = new ParticleSystem("Data/particle.png",1500);
		
		try {
			emitter = ParticleIO.loadEmitter("Data/mouse_emitter.xml");
			emitter.setPosition(0,0);
			particles.addEmitter(emitter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this;
	}
	
	public void update(GameContainer container, int delta)
			throws SlickException {
		//emitter.setPosition(container.getInput().getAbsoluteMouseX(), container.getInput().getAbsoluteMouseY(),false);
		particles.setPosition(container.getInput().getAbsoluteMouseX(), container.getInput().getAbsoluteMouseY());
		particles.update(delta);
	}
	
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		particles.render();
		g.drawString("Maus Partikel: " + particles.getParticleCount(), 10, 95);
	}
}
