package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DisplayManager.createDisplay();
		
		
		Loader loader = new Loader();
		
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivitiy(1);
		
		Entity entity = new Entity(staticModel, new Vector3f(50, 0, -50), 0, 0, 0, 1);
		Light light = new Light(new Vector3f(300, 2000, 2000), new Vector3f(1,1,1));;
		
		Terrain terrain = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(1, -1, loader, new ModelTexture(loader.loadTexture("grass")));
		
		Camera camera = new Camera();
		camera.setPosition(new Vector3f(0.0f, 5.0f, 0.0f));
		MasterRenderer renderer = new MasterRenderer();
		
		RawModel fernModel = OBJLoader.loadObjModel("fern", loader);
		TexturedModel fernStaticModel = new TexturedModel(fernModel, new ModelTexture(loader.loadTexture("fern")));
		ModelTexture fernTexture = fernStaticModel.getTexture();
		fernTexture.setHasTransparency(true);
		fernTexture.setUseFakeLighting(true);
		
		Entity[] fernEntities = new Entity[400];
		
		for (int i = 0; i < fernEntities.length; i++) {
			fernEntities[i] = new Entity(fernStaticModel, new Vector3f((float) Math.random() * 1600, 0, (float) Math.random() * -800), 0, 0, 0, 1); 
		}
		
		while(!Display.isCloseRequested()) {
			entity.increaseRotation(0, 1, 0);
			camera.move();
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			renderer.processEntity(entity);
			
			for (Entity fernEntity : fernEntities) {
				renderer.processEntity(fernEntity);
			}
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanup();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
