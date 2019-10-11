package capm.installer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.codehaus.groovy.control.CompilationFailedException;

import com.jcraft.jsch.JSchException;

import capm.installer.INTERFACE.Monitoring;
import capm.installer.MODEL.ShellCommandException;
import capm.installer.MODEL.ShellSSH;
import capm.installer.SHARE.SharedResources;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.ListSelectionModel;

public class CAPMInstallerGUI {

	private JFrame frmCapmInstaller;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JTextField textFieldHost;
	private JTextField textFieldID;
	private JTextField textFieldPw;
	private JTextPane textPane;
	private JTabbedPane tabbedPane;
	private boolean isConnecting = false;
	private Thread thread;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CAPMInstallerGUI window = new CAPMInstallerGUI();
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					SwingUtilities.updateComponentTreeUI(window.frmCapmInstaller);
					window.frmCapmInstaller.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CAPMInstallerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCapmInstaller = new JFrame();
		frmCapmInstaller.setTitle("CAPM Installer");
		frmCapmInstaller.setBounds(100, 100, 450, 496);
		frmCapmInstaller.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCapmInstaller.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frmCapmInstaller.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 1, 0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 434, 0 };
		gbl_panel_1.rowHeights = new int[] { 33, 0, 33, 33, 33, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JPanel panel_8 = new JPanel();
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.fill = GridBagConstraints.BOTH;
		gbc_panel_8.gridheight = 2;
		gbc_panel_8.insets = new Insets(0, 0, 5, 0);
		gbc_panel_8.gridx = 0;
		gbc_panel_8.gridy = 0;
		panel_1.add(panel_8, gbc_panel_8);
		panel_8.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblHost = new JLabel("Host");
		panel_8.add(lblHost);

		textFieldHost = new JTextField();
		textFieldHost.setText("192.168.3.82");
		panel_8.add(textFieldHost);
		textFieldHost.setColumns(10);

		JLabel lblId = new JLabel("Username");
		panel_8.add(lblId);

		textFieldID = new JTextField();
		textFieldID.setText("root");
		panel_8.add(textFieldID);
		textFieldID.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		panel_8.add(lblPassword);

		textFieldPw = new JTextField();
		textFieldPw.setText("root");
		panel_8.add(textFieldPw);
		textFieldPw.setColumns(10);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 2;
		panel_1.add(panel_2, gbc_panel_2);
		panel_2.setLayout(null);

		JLabel lblCh = new JLabel("Choose resource & action :");
		lblCh.setBounds(10, 11, 144, 14);
		panel_2.add(lblCh);

		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 3;
		panel_1.add(panel_3, gbc_panel_3);

		final JRadioButton radioButtonDR = new JRadioButton("Vertica DB");
		radioButtonDR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setSelectedIndex(0);
			}
		});
		buttonGroup.add(radioButtonDR);
		radioButtonDR.setSelected(true);
		panel_3.add(radioButtonDR);

		final JRadioButton radioButtonDA = new JRadioButton("Data Aggregation");
		radioButtonDA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(1);
			}
		});
		buttonGroup.add(radioButtonDA);
		panel_3.add(radioButtonDA);

		final JRadioButton radioButtonDC = new JRadioButton("Data Collectors");
		radioButtonDC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(2);
			}
		});
		buttonGroup.add(radioButtonDC);
		panel_3.add(radioButtonDC);

		final JRadioButton radioButtonPM = new JRadioButton("Performance Center");
		radioButtonPM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(3);
			}
		});
		buttonGroup.add(radioButtonPM);
		panel_3.add(radioButtonPM);

		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 4;
		panel_1.add(panel_4, gbc_panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JRadioButton radioButtonInstall = new JRadioButton("Install");
		radioButtonInstall.setSelected(true);
		buttonGroup_1.add(radioButtonInstall);
		panel_4.add(radioButtonInstall);

		final JRadioButton radioButtonUninstall = new JRadioButton("Uninstall");
		buttonGroup_1.add(radioButtonUninstall);
		panel_4.add(radioButtonUninstall);

		JPanel panel_5 = new JPanel();
		frmCapmInstaller.getContentPane().add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));

		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_6.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		panel_5.add(panel_6, BorderLayout.NORTH);

		final JButton btnGo = new JButton("Go!");

		panel_6.add(btnGo);

		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		panel_7.add(scrollPane);

		textPane = new JTextPane();
		textPane.setForeground(new Color(192, 192, 192));
		textPane.setFont(new Font("Consolas", Font.PLAIN, 16));
		textPane.setBackground(new Color(0, 0, 0));
		scrollPane.setViewportView(textPane);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		scrollPane.setColumnHeaderView(tabbedPane);

		JPanel panel_9 = new JPanel();
		tabbedPane.addTab("Vertica", null, panel_9, null);
		panel_9.setLayout(new BorderLayout(0, 0));

		tableDR = new JTable();
		tableDR.setModel(new DefaultTableModel(
				new Object[][] { { "*.bin path", "/root/installDR.bin" },
						{ "extracted path", "/opt/CA/IMDataRepository_vertica9" }, { "database name", "polaris" },
						{ "database admin user", "vertica" }, { "database admin pwd", "polaris" }, },
				new String[] { "New column", "New column" }));
		tableDR.getColumnModel().getColumn(0).setPreferredWidth(126);
		tableDR.getColumnModel().getColumn(1).setPreferredWidth(237);
		tableDR.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableDR.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		tableDR.setBackground(SystemColor.menu);
		panel_9.add(tableDR, BorderLayout.CENTER);

		JPanel panel_11 = new JPanel();
		tabbedPane.addTab("Data Aggregation", null, panel_11, null);
		panel_11.setLayout(new BorderLayout(0, 0));

		tableDA = new JTable();
		tableDA.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		tableDA.setBackground(SystemColor.control);
		tableDA.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableDA.setModel(new DefaultTableModel(
				new Object[][] { { "*.bin path", "/root/installDA.bin" }, { "extracted path", "/opt/IMDataAggregator" },
						{ "database name", "polaris" }, { "database user", "dbuser" }, { "database pwd", "dbpass" },
						{ "database admin user", "vertica" }, { "database admin pwd", "polaris" },
						{ "root username", "root" }, { "vertica ip", "127.0.0.1" }, },
				new String[] { "Attribute", "Value" }));
		tableDA.getColumnModel().getColumn(0).setPreferredWidth(126);
		tableDA.getColumnModel().getColumn(1).setPreferredWidth(237);
		panel_11.add(tableDA);

		JPanel panel_12 = new JPanel();
		tabbedPane.addTab("Data Collectors", null, panel_12, null);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		tableDC = new JTable();
		tableDC.setModel(new DefaultTableModel(
			new Object[][] {
				{"*.bin path", "/root/installDC.bin"},
				{"extracted path", "/opt/IMDataCollector"},
				{"root username", "root"},
				{"data aggregator ip", "127.0.0.1"},
			},
			new String[] {
				"New column", "New column"
			}
		));
		tableDC.getColumnModel().getColumn(0).setPreferredWidth(126);
		tableDC.getColumnModel().getColumn(1).setPreferredWidth(237);
		tableDC.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableDC.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		tableDC.setBackground(SystemColor.menu);
		panel_12.add(tableDC, BorderLayout.CENTER);

		JPanel panel_13 = new JPanel();
		tabbedPane.addTab("P.Center", null, panel_13, null);
		panel_13.setLayout(new BorderLayout(0, 0));
		
		tablePM = new JTable();
		tablePM.setModel(new DefaultTableModel(
			new Object[][] {
				{"*.bin path", "/root/CAPerfCenterSetup.bin"},
				{"extracted path", "/opt/CA"},
			},
			new String[] {
				"New column", "New column"
			}
		));
		tablePM.getColumnModel().getColumn(0).setPreferredWidth(126);
		tablePM.getColumnModel().getColumn(1).setPreferredWidth(237);
		tablePM.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablePM.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		tablePM.setBackground(SystemColor.menu);
		panel_13.add(tablePM, BorderLayout.CENTER);

		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!isConnecting) {

					thread = new Thread(new Runnable() {
						public void run() {

							if (JOptionPane.showConfirmDialog(null, "Are you sure?", "",
									JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
								return;
							isConnecting = true;
							btnGo.setText("Connecting..");
							// shell connecting
							log(Color.GREEN, "Connecting..\n\r");
							final String host = textFieldHost.getText();
							final String id = textFieldID.getText();
							final String pw = textFieldPw.getText();
							ShellSSH shell = new ShellSSH(host, id, pw);
							try {
								shell.connect(15000);
								log(Color.GREEN, "Connected!\n\r");
							} catch (JSchException e) {
								log(Color.RED, e.getMessage() + "\n\r");
								return;
							} catch (IOException e) {
								log(Color.RED, e.getMessage() + "\n\r");
								return;
							}

							// installation
							btnGo.setEnabled(false);
							Installer ins = null;
							TableModel model = null;
							if (buttonGroup.isSelected(radioButtonDR.getModel())) {
								ins = new Installer(shell, "dr.groovy");
								model = tableDR.getModel();
							} else if (buttonGroup.isSelected(radioButtonDA.getModel())) {
								ins = new Installer(shell, "da.groovy");
								model = tableDA.getModel();
							} else if (buttonGroup.isSelected(radioButtonDC.getModel())) {
								ins = new Installer(shell, "dc.groovy");
								model = tableDC.getModel();
							} else if (buttonGroup.isSelected(radioButtonPM.getModel())) {
								ins = new Installer(shell, "pm.groovy");
								model = tablePM.getModel();
							}
							ins.setMonitor(guiMonitor);
							SharedResources.putResource("installer", ins);
							SharedResources.setStep(SharedResources.Step.NEW);

							boolean isUninstall = false;
							isUninstall = buttonGroup_1.isSelected(radioButtonUninstall.getModel());
							log(Color.GREEN, (isUninstall ? "Uninstalling.." : "Installing..") + "\n\r");

							boolean isLooping = true;
							while (isLooping) {
								int n = model.getRowCount();
								for (int i = 0; i < n; i++) {
									String key = (String) model.getValueAt(i, 0);
									String value = (String) model.getValueAt(i, 1);
									SharedResources.putResource(key, value);
								}
								try {
									if (isUninstall)
										ins.uninstall();
									else
										ins.install();
									isLooping = false;
								} catch (IOException e) {
									log(Color.RED, e.getMessage() + "\n\r");
								} catch (ShellCommandException e) {
									log(Color.RED, "an installing error occurred\n\r");
									JOptionPane p = new JOptionPane(
											"'Yes' to retry the current command, 'No' to ignore the current command or 'Cancel' to abort the process",
											JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);
									JDialog k = p.createDialog("An installing error occurred");
									k.setModal(false); // Makes the dialog not modal
									k.setVisible(true);
									Object selectedValue;
									while ((selectedValue = p.getValue()) == JOptionPane.UNINITIALIZED_VALUE) {
										try {
											Thread.sleep(77);
										} catch (InterruptedException e1) {
											e1.printStackTrace();
											return;
										}
									}

									int c = JOptionPane.CANCEL_OPTION;
									if (selectedValue != null) {
										c = (Integer) selectedValue;
									}
									switch (c) {
									case JOptionPane.YES_OPTION:// retry
										SharedResources.setStep(SharedResources.Step.RETRY);
										break;
									case JOptionPane.NO_OPTION:// ignore
										SharedResources.setStep(SharedResources.Step.IGNORE);
										break;
									case JOptionPane.CANCEL_OPTION:// abort
										SharedResources.setStep(SharedResources.Step.NEW);
										isLooping = false;
										break;
									}
								} catch (CompilationFailedException e) {
									log(Color.RED, e.getMessage() + "\n\r");
								} catch (URISyntaxException e) {
									log(Color.RED, e.getMessage() + "\n\r");
								}
							}
							try {
								shell.close();
							} catch (IOException e) {
								log(Color.RED, e.getMessage() + "\n\r");
							}
							btnGo.setEnabled(true);
							btnGo.setText("Go!");
							isConnecting = false;
						}
					});
					thread.start();
				} else {
					thread.interrupt();
					btnGo.setText("Go!");
					isConnecting = false;
				}
			}
		});
	}

	public void log(Color c, String s) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		int len = textPane.getDocument().getLength();
		textPane.setCaretPosition(len);
		textPane.setCharacterAttributes(aset, false);
		textPane.replaceSelection(s);
	}

	public Monitoring guiMonitor = new Monitoring() {

		public void println(String line) {
			log(Color.LIGHT_GRAY, line + "\n\r");
		}

		public void error(String message) {
			log(Color.RED, message + "\n\r");
		}

		public void append(String str) {
			log(Color.LIGHT_GRAY, str);
		}
	};
	private JTable tableDA;
	private JTable tableDR;
	private JTable tableDC;
	private JTable tablePM;
}
