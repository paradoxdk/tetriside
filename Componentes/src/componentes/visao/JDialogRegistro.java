package componentes.visao;

import javax.swing.JDialog;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import componentes.modelo.bancodedados.BancoDeDados;
import componentes.modelo.bancodedados.Criptografia;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

@SuppressWarnings("serial")
public class JDialogRegistro extends JDialog{
	private JLabel jLabelInsiraOsDados;
	private JLabel jLabelServidor;
	private JTextField jTextFieldServidor;
	private JTextField jTextFieldBanco;
	private JPasswordField jPasswordFieldSenha;
	private JButton jButtonRegistrar;
	private JButton jButtonSair;
	private JLabel jLabelBanco;
	private JLabel jLabelSenha;
	private BancoDeDados bancoDeDados;
	private boolean fechar;
	private JTextField jTextFieldUsuario;
	private JLabel jLabelUsuario;
	
	/**
	 * @wbp.parser.constructor
	 */
	public JDialogRegistro(BancoDeDados bancoDeDados, boolean fechar){
		this(bancoDeDados);
		this.fechar=fechar;
	}
	
	public JDialogRegistro(BancoDeDados bancoDeDados) {
		this.bancoDeDados=bancoDeDados;
		fechar=true;
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setSize(new Dimension(350, 200));
		
		setUndecorated(true);
		setResizable(false);
		setTitle("Database Register");
		//setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/analisa/picaso/logo70.png")));
		getContentPane().setLayout(null);
		
		
		jLabelInsiraOsDados = new JLabel("Insert the data to connect to the database:");
		getContentPane().add(jLabelInsiraOsDados);
		jLabelInsiraOsDados.setBounds(10, 25, 330, 14);
		
		jLabelServidor = new JLabel("SERVER");
		jLabelServidor.setBounds(10, 50, 86, 20);
		getContentPane().add(jLabelServidor);
		
		jTextFieldServidor = new JTextField();
		jTextFieldServidor.setBounds(10, 70, 330, 20);
		getContentPane().add(jTextFieldServidor);
		jTextFieldServidor.setColumns(10);
	
		
		jLabelBanco = new JLabel("DATABASE");
		jLabelBanco.setBounds(10, 100, 100, 20);
		getContentPane().add(jLabelBanco);
		
		jTextFieldBanco = new JTextField();
		jTextFieldBanco.setColumns(10);
		jTextFieldBanco.setBounds(10, 120, 100, 20);
		getContentPane().add(jTextFieldBanco);
	
		
		jPasswordFieldSenha = new JPasswordField();
		
		jPasswordFieldSenha.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				getJPasswordFieldSenha().selectAll();
			}
		});
		
		jLabelUsuario = new JLabel("USER");
		jLabelUsuario.setBounds(120, 103, 91, 14);
		getContentPane().add(jLabelUsuario);
		
		jTextFieldUsuario = new JTextField();
		jTextFieldUsuario.setText("root");
		jTextFieldUsuario.setBounds(120, 120, 91, 20);
		getContentPane().add(jTextFieldUsuario);
		jTextFieldUsuario.setColumns(10);
		jPasswordFieldSenha.setColumns(10);
		jPasswordFieldSenha.setBounds(221, 120, 119, 20);
		getContentPane().add(jPasswordFieldSenha);
		
		jButtonRegistrar = new JButton("OK");
		jButtonRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					File file = new File(System.getProperty("user.home")+"/tetris/");
					file.mkdir();file.mkdirs();
					BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getProperty("user.home")+"/tetris/"+getBancoDeDados().getNome().toLowerCase()+".dabj"));
					bufferedWriter.write(""+Criptografia.Criptografar(jTextFieldServidor.getText())+"\n"
							+ ""+Criptografia.Criptografar(jTextFieldBanco.getText())+"\n"
							+ ""+Criptografia.Criptografar((new String(jPasswordFieldSenha.getPassword())))+"\n"
							+ ""+Criptografia.Criptografar(jTextFieldUsuario.getText())+"");
					JOptionPane.showMessageDialog(null, "Reload the application!");
					
					bufferedWriter.flush();
					bufferedWriter.close();
					
					if(fechar){
						System.exit(0);
					}else{
						dispose();
					}
				}catch(Exception exc){
					JOptionPane.showMessageDialog(null, "Error: "+exc);
					exc.printStackTrace();
				}
			}
		});
		jButtonRegistrar.setBounds(112, 150, 119, 30);
		getContentPane().add(jButtonRegistrar);
		
		
		
		jButtonSair = new JButton("Exit");
		jButtonSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		jButtonSair.setBounds(240, 150, 100, 30);
		getContentPane().add(jButtonSair);
		
		
		
		jLabelSenha = new JLabel("PASSWORD");
		jLabelSenha.setBounds(221, 100, 119, 20);
		getContentPane().add(jLabelSenha);
		
		jTextFieldBanco.setText(bancoDeDados.getNome().toLowerCase());
		
		//Corrige bug grafico que ocorre com janelas dialog no gnome
		setVisible(true);
		setLocationRelativeTo(null);
		setVisible(false);
		setModal(true);
		
		setVisible(true);
	}
	

	public JTextField getJTextFieldServidor() {
		return jTextFieldServidor;
	}

	public JTextField getJTextFieldBanco() {
		return jTextFieldBanco;
	}

	public JPasswordField getJPasswordFieldSenha() {
		return jPasswordFieldSenha;
	}

	public JButton getJButtonRegistrar() {
		return jButtonRegistrar;
	}

	public JButton getJButtonSair() {
		return jButtonSair;
	}


	public BancoDeDados getBancoDeDados() {
		return bancoDeDados;
	}
}

