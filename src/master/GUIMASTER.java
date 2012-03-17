package master;

import java.awt.BorderLayout;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import netPack.KeyEventRAT;
import netPack.MouseEventRAT;

/**
 * The GUI for the master program
 *
 * @author Carl
 *
 */

@SuppressWarnings("serial")
public class GUIMASTER extends JFrame implements MouseListener,MouseMotionListener,KeyListener,WindowListener {
	
	//CommandSender cs;
	JTextField tf;
	//ImageReceiver ir;
	NetWork nw;
	ImageIcon ii;
	JLabel jl;
	
	public GUIMASTER(){
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		//String host = JOptionPane.showInputDialog("IP:");
		//ir = new ImageReceiver(host,2000);
		tf = new JTextField();
		jl = new JLabel();
		jl.setIcon(this.ii);
		JScrollPane p = new JScrollPane(jl);
		c.add(p,BorderLayout.CENTER);
		c.add(tf,BorderLayout.SOUTH);
		
		p.addMouseMotionListener(this);
		tf.addKeyListener(this);
		p.addMouseListener(this);
		
		//cs = new CommandSender(host,2001);
		nw = new NetWork(this.jl,2000);
		
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
			nw.sendCommand(new MouseEventRAT(1));
		}
		else if(arg0.getButton() == MouseEvent.BUTTON3){
			//rightclicked
			nw.sendCommand(new MouseEventRAT(3));
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
		nw.sendCommand(new MouseEventRAT(e.getX(),e.getY()));
	}
	@Override
	public void keyPressed(KeyEvent key) {
		int keyCode = key.getKeyCode();
		nw.sendCommand(new KeyEventRAT(keyCode));
		setTitle("Key: "+key.getKeyCode());
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		//cs.closeAll();
	
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
