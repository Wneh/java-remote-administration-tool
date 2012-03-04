package slave;

import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;

public class GUISLAVE extends JFrame implements ActionListener,WindowListener{
	
	private static final long serialVersionUID = -4519823190906100723L;
	private JLabel info;
	private JButton quit;
	private JPanel center,south;
	private String IP;
	private String NAME;
	private NetWork nw;

	public GUISLAVE(){
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		try{
			InetAddress ia = InetAddress.getLocalHost();
			IP = ia.getHostAddress();
			NAME = ia.getHostName();
		}
		catch(Exception e){
			e.printStackTrace();
		} 
		
		info = new JLabel("Lan info: "+NAME+" ("+IP+")");
		quit = new JButton("Exit");
		center = new JPanel();
		south = new JPanel();
		
		center.add(info);
		south.add(quit);
		
		c.add(center,BorderLayout.CENTER);
		c.add(south,BorderLayout.SOUTH);
		
		quit.addActionListener(this);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("R.A.T by Carl Eriksson");
		setSize(300,100);
		
		String host = JOptionPane.showInputDialog(null,"Host:");
		
		nw = new NetWork(host,2000);
	}
	
	public static void main(String[] args) {
		new GUISLAVE();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == quit){
			System.exit(0);
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		//nw.closeAll();	
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
