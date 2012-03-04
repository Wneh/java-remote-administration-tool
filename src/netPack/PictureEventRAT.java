package netPack;

import javax.swing.ImageIcon;

public class PictureEventRAT extends EventRAT{
	
	private ImageIcon ii;
	
	public PictureEventRAT(ImageIcon inputImage){
		this.setIi(inputImage);
	}
	
	public ImageIcon getIi() {
		return ii;
	}
	
	public void setIi(ImageIcon ii) {
		this.ii = ii;
	}
}
