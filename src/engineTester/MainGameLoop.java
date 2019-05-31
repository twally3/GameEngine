package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DisplayManager.createDisplay();
		
		
		Loader loader = new Loader();
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		ModelData data = OBJFileLoader.loadOBJ("dragon");
		RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivitiy(1);
		
		Entity entity = new Entity(staticModel, new Vector3f(50, 0, -50), 0, 0, 0, 1);
		Light light = new Light(new Vector3f(300, 2000, 2000), new Vector3f(1,1,1));;
		
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap);
		Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap);
		
		Camera camera = new Camera();
		camera.setPosition(new Vector3f(0.0f, 5.0f, 0.0f));
		MasterRenderer renderer = new MasterRenderer();
		
		ModelData fernModelData = OBJFileLoader.loadOBJ("fern");
		RawModel fernModel = loader.loadToVAO(fernModelData.getVertices(), fernModelData.getTextureCoords(), fernModelData.getNormals(), fernModelData.getIndices());
		TexturedModel fernStaticModel = new TexturedModel(fernModel, new ModelTexture(loader.loadTexture("fern")));
		ModelTexture fernTexture = fernStaticModel.getTexture();
		fernTexture.setHasTransparency(true);
		fernTexture.setUseFakeLighting(true);
		
		Entity[] fernEntities = new Entity[400];
		
		for (int i = 0; i < fernEntities.length; i++) {
			fernEntities[i] = new Entity(fernStaticModel, new Vector3f((float) Math.random() * 1600 - 800, 0, (float) Math.random() * -800), 0, 0, 0, 1); 
		}
		
		ModelData treeModelData = OBJFileLoader.loadOBJ("tree");
		RawModel treeModel = loader.loadToVAO(treeModelData.getVertices(), treeModelData.getTextureCoords(), treeModelData.getNormals(), treeModelData.getIndices());
		TexturedModel treeStaticModel = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("tree")));
		ModelTexture treeTexture = treeStaticModel.getTexture();
		
		Entity[] treeEntities = new Entity[200];
		
		for (int i = 0; i < treeEntities.length; i++) {
			treeEntities[i] = new Entity(treeStaticModel, new Vector3f((float) Math.random() * 1600 - 800, 0, (float) Math.random() * -800), 0, 0, 0, 10); 
		}
		
		while(!Display.isCloseRequested()) {
			entity.increaseRotation(0, 1, 0);
			camera.move();
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			renderer.processEntity(entity);
			
			for (Entity treeEntity : treeEntities) {
				renderer.processEntity(treeEntity);
			}
			
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
