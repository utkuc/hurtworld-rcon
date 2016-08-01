package rcon.main;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class GUI {
	static JFrame frmHurtworldRcon;
	private static JTextField cmdField;
	private static JLabel statusLabel;
	private static JButton btnPath;
	public static String str = "";
	public static boolean pathGiven = false;
	private static JLabel cPathLabel;
	@SuppressWarnings("rawtypes")
	private static JComboBox PredefinedCmd;
	private static JLabel dummyLabelFooter;
	private static JLabel footerLabel;
	private static JLabel preCmd;
	private static JLabel cmdText;

	public static void main(String[] args) {
		// schedule this for the event dispatch thread (edt)
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				displayJFrame();
			}
		});
	}

	private static String chooseFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int option = chooser.showSaveDialog(null);
		File dir = null;
		if (option == JFileChooser.APPROVE_OPTION) {
			dir = chooser.getSelectedFile();
			str = dir.toString();
			pathGiven = true;
		} else if (option == JFileChooser.CANCEL_OPTION) {
			pathGiven = false;
			cPathLabel.setText("Current Path: ");

		}
		return str;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	static void displayJFrame() {
		frmHurtworldRcon = new JFrame("HurtWorld Rcon");
		frmHurtworldRcon.setResizable(false);
		frmHurtworldRcon.setLocation(new Point(65, 12));
		frmHurtworldRcon.setBounds(new Rectangle(65, 12, 0, 0));
		frmHurtworldRcon.getContentPane().setBackground(Color.WHITE);
		frmHurtworldRcon.setBackground(Color.WHITE);
		frmHurtworldRcon.setTitle("HurtWorld Rcon Tool");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 107, 119, 89, 52, 0 };
		gridBagLayout.rowHeights = new int[] { 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30, 0, 6, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		frmHurtworldRcon.getContentPane().setLayout(gridBagLayout);

		System.out.println(str);

		// create our jbutton
		final JButton btnExecute = new JButton("Execute");
		btnExecute.setFont(new Font("Dialog", Font.ITALIC, 12));
		btnExecute.setBackground(Color.LIGHT_GRAY);

		// add the listener to the jbutton to handle the "pressed" event
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pathGiven != false) {
					PrintWriter writer = null;
					try {
						writer = new PrintWriter(str + "/runtimeexec.cfg", "UTF-8");
					} catch (FileNotFoundException | UnsupportedEncodingException e1) {
						statusLabel.setText(
								"<html><font color=\"red\">Wrong Path,Choose 'Serverfiles' folder.</font></html>");
						e1.printStackTrace();
					}
					writer.println(cmdField.getText());
					writer.close();
					statusLabel.setText("Status: Executing Command,Wait 10 Seconds");
					btnExecute.setEnabled(false);
					new SwingWorker() {
						@Override
						protected Object doInBackground() throws Exception {
							Thread.sleep(10150);
							return null;
						}

						@Override
						protected void done() {
							btnExecute.setEnabled(true);
							statusLabel.setText("Status: Succesfull");
						}
					}.execute();

				} else {
					statusLabel.setText("Status: File Path Not Given");
				}
			}
		});

		cmdText = new JLabel("Command Field:");
		GridBagConstraints gbc_cmdText = new GridBagConstraints();
		gbc_cmdText.insets = new Insets(0, 0, 5, 5);
		gbc_cmdText.gridx = 0;
		gbc_cmdText.gridy = 1;
		frmHurtworldRcon.getContentPane().add(cmdText, gbc_cmdText);

		preCmd = new JLabel("Select Predefined Command:");
		GridBagConstraints gbc_preCmd = new GridBagConstraints();
		gbc_preCmd.insets = new Insets(0, 0, 5, 5);
		gbc_preCmd.gridx = 2;
		gbc_preCmd.gridy = 1;
		frmHurtworldRcon.getContentPane().add(preCmd, gbc_preCmd);

		cmdField = new JTextField();
		// txtTypeCommand.setText("");
		PromptSupport.setPrompt("Type Command,ex: adminmessage Test", cmdField);
		PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.HIGHLIGHT_PROMPT, cmdField);
		PromptSupport.setFontStyle(Font.ITALIC, cmdField);

		cmdField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_cmdField = new GridBagConstraints();
		gbc_cmdField.gridwidth = 2;
		gbc_cmdField.anchor = GridBagConstraints.WEST;
		gbc_cmdField.insets = new Insets(0, 0, 5, 5);
		gbc_cmdField.gridx = 0;
		gbc_cmdField.gridy = 2;
		frmHurtworldRcon.getContentPane().add(cmdField, gbc_cmdField);
		cmdField.setColumns(30);

		PredefinedCmd = new JComboBox();
		PredefinedCmd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cmdField.setText((String) PredefinedCmd.getSelectedItem());
				cmdField.grabFocus();
			}
		});
		PredefinedCmd.setMaximumRowCount(7);
		PredefinedCmd.setModel(new DefaultComboBoxModel(new String[] { "adminmessage <message>", "host <port> <map>",
				"host <port> <map> <save>", "servername <name>", "maxplayers <number>", "creativemode <val> ",
				"kick <steamID64> <reason>", "saveserver <saveName>", "loadserver <saveName>", "addadmin   <steamID64>",
				"ban <steamID64>", "unban <steamID64>", "queryport <port> ", "autobackupinterval <seconds> ",
				"wipeinterval <seconds>", "combatlogtimeout <seconds> ", "loadbalancerframebudget <value>",
				"targetfps <value>", "destroyall <partialname>", "quit  ", "quit <seconds> ", "cancelquit  ",
				"playerlootmode <value> ", "spawncooldown <speed> <value> ", "count <partialname> ",
				"chatspambudget <value> ", "chatconnectionmessagesenabled <value> ",
				"chatdeathmessagesenabled <value> ", "bindip <ip> ", "stakedeauthtime <seconds> ",
				"vehicledecaytime <seconds> ", "afkkicktime <seconds> ", "structurecomplexitylimit <category> <value>",
				"maxping <value> ", "autosaveenabled <value> ", "sendbuffersize <value> ",
				"networktimeoutdelay <seconds> ", "loadmod <workshopID> (<workshopID>...) ", "forceload" }));
		PredefinedCmd.setEditable(false);
		GridBagConstraints gbc_PredefinedCmd = new GridBagConstraints();
		gbc_PredefinedCmd.insets = new Insets(0, 0, 5, 5);
		gbc_PredefinedCmd.fill = GridBagConstraints.HORIZONTAL;
		gbc_PredefinedCmd.gridx = 2;
		gbc_PredefinedCmd.gridy = 2;
		frmHurtworldRcon.getContentPane().add(PredefinedCmd, gbc_PredefinedCmd);
		GridBagConstraints gbc_btnExecute = new GridBagConstraints();
		gbc_btnExecute.anchor = GridBagConstraints.WEST;
		gbc_btnExecute.insets = new Insets(0, 0, 5, 5);
		gbc_btnExecute.gridx = 0;
		gbc_btnExecute.gridy = 3;
		frmHurtworldRcon.getContentPane().add(btnExecute, gbc_btnExecute);

		btnPath = new JButton("Choose Path...");
		btnPath.setToolTipText("Navigate to /serverfiles folder");
		btnPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				str = chooseFile();
				if (str.contains("serverfiles")) {
					cPathLabel.setText("<html><font color=\"green\">Current Path: " + str + "</font></html>");
					btnExecute.setEnabled(true);
					statusLabel.setText("Status: Ready!");

				} else {
					cPathLabel.setText("<html><font color=\"red\">Current Path: " + str + "</font></html>");
					btnExecute.setEnabled(false);
					statusLabel.setText("Status: Wrong File Path");
				}

			}
		});
		btnPath.setBackground(Color.GRAY);
		GridBagConstraints gbc_btnPath = new GridBagConstraints();
		gbc_btnPath.insets = new Insets(0, 0, 5, 5);
		gbc_btnPath.gridx = 1;
		gbc_btnPath.gridy = 3;
		frmHurtworldRcon.getContentPane().add(btnPath, gbc_btnPath);
		statusLabel = new JLabel("Status:");
		GridBagConstraints gbc_statusLabel = new GridBagConstraints();
		gbc_statusLabel.gridwidth = 2;
		gbc_statusLabel.insets = new Insets(0, 0, 5, 5);
		gbc_statusLabel.anchor = GridBagConstraints.WEST;
		gbc_statusLabel.gridx = 0;
		gbc_statusLabel.gridy = 4;
		frmHurtworldRcon.getContentPane().add(statusLabel, gbc_statusLabel);
		cPathLabel = new JLabel("Current Path:");
		GridBagConstraints gbc_cPathLabel = new GridBagConstraints();
		gbc_cPathLabel.gridwidth = 3;
		gbc_cPathLabel.anchor = GridBagConstraints.WEST;
		gbc_cPathLabel.insets = new Insets(0, 0, 5, 5);
		gbc_cPathLabel.gridx = 0;
		gbc_cPathLabel.gridy = 5;
		frmHurtworldRcon.getContentPane().add(cPathLabel, gbc_cPathLabel);

		dummyLabelFooter = new JLabel("");
		GridBagConstraints gbc_dummyLabelFooter = new GridBagConstraints();
		gbc_dummyLabelFooter.insets = new Insets(0, 0, 5, 5);
		gbc_dummyLabelFooter.gridx = 0;
		gbc_dummyLabelFooter.gridy = 6;
		frmHurtworldRcon.getContentPane().add(dummyLabelFooter, gbc_dummyLabelFooter);

		footerLabel = new JLabel("Made by Vinerra,Go to Oxide Forums for Support.");
		footerLabel.setFont(new Font("Dialog", Font.ITALIC, 9));
		GridBagConstraints gbc_footerLabel = new GridBagConstraints();
		gbc_footerLabel.anchor = GridBagConstraints.SOUTH;
		gbc_footerLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_footerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_footerLabel.gridx = 0;
		gbc_footerLabel.gridy = 12;
		frmHurtworldRcon.getContentPane().add(footerLabel, gbc_footerLabel);

		// set up the jframe, then display it
		frmHurtworldRcon.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frmHurtworldRcon.setPreferredSize(new Dimension(800, 200));
		frmHurtworldRcon.pack();
		frmHurtworldRcon.setLocationRelativeTo(null);
		frmHurtworldRcon.setVisible(true);
	}
}