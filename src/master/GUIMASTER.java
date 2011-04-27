package master;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class GUIMASTER extends JFrame implements MouseListener,MouseMotionListener,KeyListener {
	
	CommandSender cs;
	JTextField tf;
	
	public GUIMASTER(){
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		String host = JOptionPane.showInputDialog("IP:");
		
		JLabel jl = new ImageReceiver(host,2000);
		tf = new JTextField();
		JScrollPane p = new JScrollPane(jl);
		c.add(p,BorderLayout.CENTER);
		c.add(tf,BorderLayout.SOUTH);
		
		p.addMouseMotionListener(this);
		tf.addKeyListener(this);
		p.addMouseListener(this);
		
		cs = new CommandSender(host,2001);	
		
		
		setSize(800,600);
		setTitle("R.A.T by Carl Eriksson");
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
	}
	public static void main(String[] arg){
		new GUIMASTER();
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON1){
			//leftclicked
			cs.sendMouseClick(1);
		}
		else if(arg0.getButton() == MouseEvent.BUTTON3){
			//rightclicked
			cs.sendMouseClick(3);
		}		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		setTitle("X: "+e.getX()+" , Y: "+e.getY());
		cs.sendMouseMove(e.getX(),e.getY());
	}
	@Override
	public void keyPressed(KeyEvent key) {
		int keyCode = key.getKeyCode();
		cs.sendKeyPressed(keyCode);		
		setTitle("Key: "+key.getKeyCode());
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		//cs.sendKeyPressed(arg0.getKeyCode());
		//setTitle("Key: "+arg0.getKeyCode());
		//tf.setText("");
		
	}
}
