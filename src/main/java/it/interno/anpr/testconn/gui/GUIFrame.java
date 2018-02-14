/**
 * 
 */
package it.interno.anpr.testconn.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import it.interno.anpr.config.ConfigHandler;
import it.interno.anpr.config.EnvironmentHandler;
import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.config.WSTypeHandler;

/**
 * @author mttfranci
 *
 */
public class GUIFrame extends JFrame implements WindowListener, ActionListener {

	private static final String OP_3002 = "3002";
	private static final String ENV_TESTCOMUNI = "Test comuni";
	private static final String ENV_PRESUBENTRO = "Presubentro";
	
	private JComboBox<String> operationCombo, envCombo;
	
	private JTextArea inputArea, outputArea;
	
	private JButton connectButton;
	
	private void setUpCompoent() throws Exception {
		String operation = String.valueOf( this.operationCombo.getSelectedItem() );
		String templateText = GUIConfigHandler.readInputTemplate( operation );
		String codMittente = ConfigHandler.getParam(ConfigHandler.ID_SEDE);
		long time = System.currentTimeMillis();
		String idOperazioneComune = codMittente+time;
		templateText = templateText.replaceAll( "PARAM_CODMITTENTE", codMittente );
		templateText = templateText.replaceAll( "PARAM_IDOPERAZIONECOMUNE", idOperazioneComune );
		XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar( new GregorianCalendar() );
		templateText = templateText.replaceAll( "PARAM_DATA_RICHIESTA", String.valueOf( cal ) );
		templateText = templateText.replaceAll( "PARAM_DATARIFERIMENTO", String.valueOf( new java.sql.Date( time ) ) );
		this.setMessageIn( templateText );
	}
	
	public GUIFrame() {
		this.setTitle( "Client connessione ANPR" );
		this.setResizable( true );
		
		int width = 800;
		int height = 600;
		
		
		this.setSize( width, height );
		
		// control panel
		String[] operationList = { OP_3002 };
		//String[] envList = { ENV_TESTCOMUNI, ENV_PRESUBENTRO };
		String[] envList = { ENV_TESTCOMUNI };
		this.operationCombo = new JComboBox<String>( operationList );
		this.operationCombo.setSelectedIndex( 0 );
		this.envCombo = new JComboBox<String>( envList );
		this.envCombo.setSelectedIndex( 0 );
		this.connectButton = new JButton( "Invia" );
		this.connectButton.addActionListener( this );
		
		JPanel controlPanel = new JPanel();
		controlPanel.add( new JLabel( "operazione : " ) );
		controlPanel.add( this.operationCombo );
		controlPanel.add( new JLabel( "ambiente : " ) );
		controlPanel.add( this.envCombo );
		controlPanel.add( this.connectButton );
		
		// data panel
		this.inputArea = new JTextArea( "Qui viene mostrato l'input!" );
		this.outputArea = new JTextArea(  "Area di output" );
		JScrollPane inputPane = new JScrollPane( this.inputArea );
		JScrollPane outputPane = new JScrollPane( this.outputArea );
		JSplitPane dataPanel = new JSplitPane( JSplitPane.VERTICAL_SPLIT, inputPane, outputPane );
		
		
		BorderLayout bl = new BorderLayout();
		this.setLayout( bl );
		this.add( controlPanel, BorderLayout.NORTH );
		this.add( dataPanel , BorderLayout.CENTER );
		
		try {
			EnvironmentHandler.setEnv( EnvironmentHandler.TEST );
			this.setUpCompoent();	
		} catch (Exception e) {
			this.setMessageOut( e.toString() );
		}
		
		
		this.addWindowListener( this );
		
		this.setVisible( true );
		
		dataPanel.setDividerLocation( 0.5 );
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3198724826015846861L;

	public static void main( String[] args ) {
		try {
			try {
				//String look1 = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
				//String look2 = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
				//UIManager.setLookAndFeel( look1 );
				UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.setProperty("javax.net.ssl.trustStore", "keystore/cacerts");
			System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
			ConfigHandler.reload();
			new GUIFrame();
		} catch (Exception e) {
			e.printStackTrace( );
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		if ( e.getSource() == this ) {
			System.out.println( "WINDOWS CLOSING EVENT!" );
			this.setVisible( false );
			this.dispose();
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent e) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}

	private ParamHandler getParamHandler() throws Exception {
		ParamHandler param = new ParamHandler();
		if ( ENV_TESTCOMUNI.equals( this.envCombo.getSelectedItem() ) ) {
			param.setEnvironment(EnvironmentHandler.TEST);
		} else if ( ENV_PRESUBENTRO.equals( this.envCombo.getSelectedItem() ) ) {
			param.setEnvironment(EnvironmentHandler.PRE);
		}
		WSTypeHandler wsHandler = new WSTypeHandler();
		if ( OP_3002.equals( this.operationCombo.getSelectedItem() ) ) {
			wsHandler.setWSFamily(WSTypeHandler.WS3002);	
		}
		param.setWsType(wsHandler);
		param.setDataRequest( this.inputArea.getText() );
		return param;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if ( e.getSource() == this.connectButton ) {	
				String result = SendButtonHandler.handle( this.getParamHandler() );
				this.setMessageOut( result );
			}	
		} catch (Exception ex) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream( baos, true );
			ex.printStackTrace( ps );
			ps.flush();
			this.setMessageOut( baos.toString() );
		}
	}

	private void setMessageIn( String text ) {
		this.inputArea.setText( text );
	}
	
	private void setMessageOut( String text ) {
		this.outputArea.setText( text );
	}
	
}
