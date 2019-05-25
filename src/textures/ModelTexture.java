package textures;

public class ModelTexture {
	private int textureID;
	
	private float shineDamper = 1;
	private float reflectivitiy = 0;
	
	public ModelTexture(int id) {
		this.textureID = id;
	}
	
	public int getID() {
		return this.textureID;
	}

	public int getTextureID() {
		return textureID;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivitiy() {
		return reflectivitiy;
	}

	public void setReflectivitiy(float reflectivitiy) {
		this.reflectivitiy = reflectivitiy;
	}
	
	
}
