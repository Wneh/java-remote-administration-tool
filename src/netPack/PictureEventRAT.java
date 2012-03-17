package netPack;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class PictureEventRAT extends EventRAT implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
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
