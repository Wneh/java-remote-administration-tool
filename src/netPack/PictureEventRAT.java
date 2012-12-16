package netPack;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.swing.ImageIcon;

public class PictureEventRAT extends EventRAT implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ImageIcon ii;
	private BufferedImage bi;
	
	public PictureEventRAT(ImageIcon inputImage){
		this.setIi(inputImage);
	}
	
	public ImageIcon getIi() {
		return ii;
	}
	
	public void setIi(ImageIcon ii) {
		this.ii = ii;
	}

	public BufferedImage getBi() {
		return bi;
	}

	public void setBi(BufferedImage bi) {
		this.bi = bi;
	}
}
