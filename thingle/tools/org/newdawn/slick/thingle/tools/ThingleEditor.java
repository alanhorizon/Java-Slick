package org.newdawn.slick.thingle.tools;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.thingle.Page;
import org.newdawn.slick.thingle.Theme;
import org.newdawn.slick.thingle.Thingle;
import org.newdawn.slick.thingle.Widget;
import org.newdawn.slick.thingle.internal.slick.SlickThinletFactory;
import org.newdawn.slick.thingle.spi.ThingleColor;

/**
 * Displays a visual representation of thingle XML as it is typed.
 * 
 * @author Nathan Sweet <misc@n4te.com>
 */
public class ThingleEditor extends JFrame {
	private Page page;
	private Set<Runnable> slickTasks = new HashSet();
	private SlickThinletFactory thingleContext;
	private Theme theme;
	private JFileChooser fileChooser;
	private boolean isLoaded;

	private Game game = new BasicGame("GuiEditor") {
		private int width, height;

		public void init (GameContainer container) throws SlickException {
			container.setShowFPS(false);
			thingleContext = new SlickThinletFactory(container);
			Thingle.init(thingleContext);
			page = new Page();
			page.setDrawDesktop(false);
			page.enable();
			theme = new Theme();
			page.setTheme(theme);
			hookEvents();
			loadSettings();
		}

		public void render (GameContainer container, Graphics g) throws SlickException {
			page.render();
		}

		public void update (GameContainer container, int delta) throws SlickException {
			Runnable[] tasks = slickTasks.toArray(new Runnable[slickTasks.size()]);
			for (Runnable task : tasks)
				task.run();
			synchronized (slickTasks) {
				for (Runnable task : tasks)
					slickTasks.remove(task);
			}
			
			if (width != container.getWidth() || height != container.getHeight()) {
				width = container.getWidth();
				height = container.getHeight();
				page.layout();
			}
		}
	};

	public ThingleEditor () throws SlickException {
		super("Thingle Editor");

		initialize();

		CanvasGameContainer container = new CanvasGameContainer(game);
		previewPanel.add(container, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
			GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

		setVisible(true);
		container.start();
	}

	private void hookEvents () {
		abstract class ChangeListener implements DocumentListener {
			public void removeUpdate (DocumentEvent evt) {
				addChangedTask();
			}

			public void insertUpdate (DocumentEvent evt) {
				addChangedTask();
			}

			public void changedUpdate (DocumentEvent evt) {
				addChangedTask();
			}

			private void addChangedTask () {
				synchronized (slickTasks) {
					slickTasks.add(new Runnable() {
						public void run () {
							changed();
						}
					});
				}
			}

			abstract void changed ();
		}

		final ChangeListener xmlChangeListener = new ChangeListener() {
			void changed () {
				((TitledBorder)xmlPanel.getBorder()).setTitle("XML");
				try {
					if (xmlTextArea.getText().length() != 0) {
						Widget widget = page.parse(xmlTextArea.getText(), null);
						page.getDesktop().removeChildren();
						page.add(widget);
						page.layout();
					}
					saveSettings();
				} catch (Exception ex) {
					((TitledBorder)xmlPanel.getBorder()).setTitle("XML (error)");
				}
				xmlPanel.repaint();
			}
		};
		xmlTextArea.getDocument().addDocumentListener(xmlChangeListener);

		drawDesktopCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged (ChangeEvent evt) {
				page.setDrawDesktop(drawDesktopCheckBox.isSelected());
				saveSettings();
			}
		});

		final ChangeListener fontChangedListener = new ChangeListener() {
			void changed () {
				configTabs.setTitleAt(0, "Page");
				try {
					if (fontTextField.getText().length() == 0) {
						page.setFont(thingleContext.getDefaultFont());
					} else {
						String[] text = fontTextField.getText().toLowerCase().split(",");
						if (text.length == 2) {
							page.setFont(thingleContext.createBitmapFont(text[0].trim(), text[1].trim()));
						} else if (text.length == 3) {
							int style = 0;
							if (text[1].contains("plain")) style |= Font.PLAIN;
							if (text[1].equalsIgnoreCase("bold")) style |= Font.BOLD;
							if (text[1].equalsIgnoreCase("italic")) style |= Font.ITALIC;
							page.setFont(thingleContext.createFont(text[0].trim(), style, Integer.parseInt(text[2].trim())));
						} else
							throw new IllegalArgumentException();
					}
					xmlChangeListener.changed();
				} catch (Exception ex) {
					ex.printStackTrace();
					configTabs.setTitleAt(0, "Page (error)");
				}
				configTabs.repaint();
			}
		};
		fontTextField.getDocument().addDocumentListener(fontChangedListener);
		
		skinTextField.getDocument().addDocumentListener(new ChangeListener() {
			void changed () {
				configTabs.setTitleAt(0, "Page");
				try {
					Page newPage = new Page();
					newPage.setDrawDesktop(drawDesktopCheckBox.isSelected());
					newPage.enable();
					newPage.setTheme(theme);
					if (skinTextField.getText().length() != 0) newPage.loadSkin(skinTextField.getText());
					page = newPage;
					fontChangedListener.changed();
				} catch (Exception ex) {
					configTabs.setTitleAt(0, "Page (error)");
				}
				configTabs.repaint();
			}
		});


		class ThemeChangeListener extends ChangeListener {
			private JTextField textField;
			private Method setMethod, getMethod;

			public ThemeChangeListener (JTextField textField, String methodName) {
				this.textField = textField;
				textField.getDocument().addDocumentListener(this);
				try {
					setMethod = theme.getClass().getMethod("set" + methodName, new Class[] {ThingleColor.class});
					getMethod = theme.getClass().getMethod("get" + methodName, new Class[0]);
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}

			void changed () {
				configTabs.setTitleAt(1, "Theme");
				try {
					ThingleColor color = null;
					String value = textField.getText();
					if (value.length() == 0) {
						setMethod.invoke(theme, new Object[] {getMethod.invoke(new Theme(), new Object[0])});
					} else {
						int colorValue;
						if (value.startsWith("#")) {
							colorValue = Integer.parseInt(value.substring(1), 16);
						} else if (value.startsWith("0x")) {
							colorValue = Integer.parseInt(value.substring(2), 16);
						} else {
							StringTokenizer st = new StringTokenizer(value, " \r\n\t,");
							colorValue = 0xff000000 | ((Integer.parseInt(st.nextToken()) & 0xff) << 16)
								| ((Integer.parseInt(st.nextToken()) & 0xff) << 8) | (Integer.parseInt(st.nextToken()) & 0xff);
						}
						color = thingleContext.createColor(colorValue);
						setMethod.invoke(theme, new Object[] {color});
					}
					page.setTheme(theme);
					saveSettings();
				} catch (Exception ex) {
					configTabs.setTitleAt(1, "Theme (error)");
				}
				configTabs.repaint();
			}
		}
		new ThemeChangeListener(themeTextBackgroundTextField, "TextBackground");
		new ThemeChangeListener(themeTextTextField, "Text");
		new ThemeChangeListener(themeHoverTextField, "Hover");
		new ThemeChangeListener(themeSelectedTextField, "Selected");
		new ThemeChangeListener(themeFocusTextField, "Focus");
		new ThemeChangeListener(themeBackgroundTextField, "Background");
		new ThemeChangeListener(themePressedTextField, "Pressed");
		new ThemeChangeListener(themeDisabledTextField, "Disabled");
		new ThemeChangeListener(themeBorderTextField, "Border");

		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent evt) {
				ThingleEditor.this.dispose();
			}
		});

		openMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent evt) {
				if (fileChooser == null) fileChooser = new JFileChooser(".");
				if (fileChooser.showOpenDialog(ThingleEditor.this) != JFileChooser.APPROVE_OPTION) return;
				try {
					BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
					StringBuilder buffer = new StringBuilder(512);
					String line = null;
					while ((line = reader.readLine()) != null) {
						buffer.append(line);
						buffer.append(System.getProperty("line.separator"));
					}
					xmlTextArea.setText(buffer.toString());
					reader.close();
				} catch (IOException ex) {
					System.out.println("Error reading file: " + fileChooser.getSelectedFile());
					ex.printStackTrace();
				}
			}
		});

		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent evt) {
				if (fileChooser == null) fileChooser = new JFileChooser(".");
				if (fileChooser.showSaveDialog(ThingleEditor.this) != JFileChooser.APPROVE_OPTION) return;
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()));
					writer.write(xmlTextArea.getText());
					writer.close();
				} catch (IOException ex) {
					System.out.println("Error writing file: " + fileChooser.getSelectedFile());
					ex.printStackTrace();
				}
			}
		});
	}

	private void saveSettings () {
		if (!isLoaded) return;
		Properties props = new Properties();
		props.setProperty("drawDesktopCheckBox", drawDesktopCheckBox.isSelected() ? "1" : "");
		props.setProperty("skinTextField", skinTextField.getText());
		props.setProperty("fontTextField", fontTextField.getText());
		props.setProperty("themeTextBackgroundTextField", themeTextBackgroundTextField.getText());
		props.setProperty("themeTextTextField", themeTextTextField.getText());
		props.setProperty("themeHoverTextField", themeHoverTextField.getText());
		props.setProperty("themeSelectedTextField", themeSelectedTextField.getText());
		props.setProperty("themeFocusTextField", themeFocusTextField.getText());
		props.setProperty("themeBackgroundTextField", themeBackgroundTextField.getText());
		props.setProperty("themePressedTextField", themePressedTextField.getText());
		props.setProperty("themeDisabledTextField", themeDisabledTextField.getText());
		props.setProperty("themeBorderTextField", themeBorderTextField.getText());
		props.setProperty("themeBorderTextField", themeBorderTextField.getText());
		props.setProperty("xml", xmlTextArea.getText());
		if (fileChooser != null) props.setProperty("currentDir", fileChooser.getCurrentDirectory().getAbsolutePath());
		try {
			props.store(new FileOutputStream("thingleEditor.properties"), "ThingleEditor");
		} catch (IOException ex) {
			System.out.println("Error saving properties.");
			ex.printStackTrace();
		}
	}

	private void loadSettings () {
		if (new File("thingleEditor.properties").exists()) {
			Properties props = new Properties();
			try {
				props.load(new FileInputStream("thingleEditor.properties"));
				drawDesktopCheckBox.setSelected(props.getProperty("drawDesktopCheckBox") != null);
				skinTextField.setText(props.getProperty("skinTextField"));
				fontTextField.setText(props.getProperty("fontTextField"));
				themeTextBackgroundTextField.setText(props.getProperty("themeTextBackgroundTextField"));
				themeTextTextField.setText(props.getProperty("themeTextTextField"));
				themeHoverTextField.setText(props.getProperty("themeHoverTextField"));
				themeSelectedTextField.setText(props.getProperty("themeSelectedTextField"));
				themeFocusTextField.setText(props.getProperty("themeFocusTextField"));
				themeBackgroundTextField.setText(props.getProperty("themeBackgroundTextField"));
				themePressedTextField.setText(props.getProperty("themePressedTextField"));
				themeDisabledTextField.setText(props.getProperty("themeDisabledTextField"));
				themeBorderTextField.setText(props.getProperty("themeBorderTextField"));
				themeBorderTextField.setText(props.getProperty("themeBorderTextField"));
				if (props.getProperty("currentDir") != null) fileChooser = new JFileChooser(props.getProperty("currentDir"));
				xmlTextArea.setText(props.getProperty("xml"));
			} catch (IOException ex) {
				System.out.println("Error loading properties.");
				ex.printStackTrace();
			}
		}
		isLoaded = true;
	}

	private void initialize () {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		{
			menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				fileMenu = new JMenu();
				menuBar.add(fileMenu);
				fileMenu.setText("File");
				{
					openMenuItem = new JMenuItem();
					fileMenu.add(openMenuItem);
					openMenuItem.setText("Open XML File...");
				}
				{
					saveMenuItem = new JMenuItem();
					fileMenu.add(saveMenuItem);
					saveMenuItem.setText("Save XML File...");
				}
				fileMenu.addSeparator();
				{
					exitMenuItem = new JMenuItem();
					fileMenu.add(exitMenuItem);
					exitMenuItem.setText("Exit");
				}
			}
		}
		{
			splitPane = new JSplitPane();
			{
				rightPanel = new JPanel();
				splitPane.add(rightPanel, JSplitPane.RIGHT);
				rightPanel.setLayout(new GridBagLayout());
				{
					previewPanel = new JPanel();
					previewPanel.setLayout(new GridBagLayout());
					rightPanel.add(previewPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
					previewPanel.setBorder(BorderFactory.createTitledBorder("Preview"));
				}
			}
			{
				leftPanel = new JPanel();
				splitPane.add(leftPanel, JSplitPane.LEFT);
				leftPanel.setLayout(new GridBagLayout());
				{
					xmlPanel = new JPanel();
					xmlPanel.setLayout(new GridBagLayout());
					leftPanel.add(xmlPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
					xmlPanel.setBorder(BorderFactory.createTitledBorder("XML"));
					{
						xmlScrollPane = new JScrollPane();
						xmlPanel.add(xmlScrollPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
							GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
						{
							xmlTextArea = new JTextArea();
							xmlTextArea.setFont(Font.decode("Courier New-11"));
							xmlTextArea.setTabSize(3);
							xmlScrollPane.setViewportView(xmlTextArea);
						}
					}
				}
				{
					configTabs = new JTabbedPane();
					leftPanel.add(configTabs, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
					{
						pagePanel = new JPanel();
						configTabs.addTab("Page", null, pagePanel, null);
						pagePanel.setLayout(new GridBagLayout());
						{
							skinLabel = new JLabel();
							pagePanel.add(skinLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
								GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
							skinLabel.setText("Skin:");
						}
						{
							skinTextField = new JTextField();
							pagePanel.add(skinTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
						}
						{
							drawDesktopCheckBox = new JCheckBox("Draw desktop");
							pagePanel.add(drawDesktopCheckBox, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
								GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
						}
						{
							fontTextField = new JTextField();
							pagePanel.add(fontTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
						}
						{
							fontLabel = new JLabel();
							pagePanel.add(fontLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
								GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
							fontLabel.setText("Font:");
						}
						pagePanel.add(new JPanel(), new GridBagConstraints(0, 3, 1, 1, 0.0, 1.0, GridBagConstraints.EAST,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					}
					{
						themePanel = new JPanel();
						configTabs.addTab("Theme", null, themePanel, null);
						themePanel.setLayout(new GridBagLayout());
						{
							themeTextTextField = new JTextField();
							themePanel.add(themeTextTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
						}
						{
							JLabel label = new JLabel();
							themePanel.add(label, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
								GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
							label.setText("Background:");
						}
						{
							JLabel label = new JLabel();
							themePanel.add(label, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
								GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
							label.setText("Disabled:");
						}
						{
							themeTextBackgroundTextField = new JTextField();
							themePanel.add(themeTextBackgroundTextField, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
						}
						{
							JLabel label = new JLabel();
							themePanel.add(label, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
								GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
							label.setText("Selected:");
						}
						{
							themeBorderTextField = new JTextField();
							themePanel.add(themeBorderTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
						}
						{
							JLabel label = new JLabel();
							themePanel.add(label, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
								GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
							label.setText("Focus:");
						}
						{
							themeDisabledTextField = new JTextField();
							themePanel.add(themeDisabledTextField, new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
						}
						{
							JLabel label = new JLabel();
							themePanel.add(label, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
								GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
							label.setText("Pressed:");
						}
						{
							themePressedTextField = new JTextField();
							themePanel.add(themePressedTextField, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
						}
						{
							JLabel label = new JLabel();
							themePanel.add(label, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
								GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
							label.setText("Border:");
						}
						{
							themeBackgroundTextField = new JTextField();
							themePanel.add(themeBackgroundTextField, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
						}
						{
							JLabel label = new JLabel();
							themePanel.add(label, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
								GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
							label.setText("Text bground:");
						}
						{
							themeFocusTextField = new JTextField();
							themePanel.add(themeFocusTextField, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
						}
						{
							JLabel label = new JLabel();
							themePanel.add(label, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
								GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
							label.setText("Text:");
						}
						{
							themeSelectedTextField = new JTextField();
							themePanel.add(themeSelectedTextField, new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0,
								GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
						}
						{
							JLabel label = new JLabel();
							themePanel.add(label, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
								GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
							label.setText("Hover:");
						}
						{
							themeHoverTextField = new JTextField();
							themePanel.add(themeHoverTextField, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
								GridBagConstraints.HORIZONTAL, new Insets(0, 5, 5, 5), 0, 0));
						}
					}
				}
			}
			getContentPane().add(splitPane, BorderLayout.CENTER);
			splitPane.setDividerLocation(300);
		}
		setSize(700, 525);
		setLocationRelativeTo(null);
	}

	private JPanel xmlPanel;
	private JSplitPane splitPane;
	private JTabbedPane configTabs;
	private JLabel fontLabel;
	private JPanel themePanel;
	private JTextField themeTextBackgroundTextField;
	private JTextField themeTextTextField;
	private JTextField themeHoverTextField;
	private JTextField themeSelectedTextField;
	private JTextField themeFocusTextField;
	private JTextField themeBackgroundTextField;
	private JTextField themePressedTextField;
	private JTextField themeDisabledTextField;
	private JTextField themeBorderTextField;
	private JTextField fontTextField;
	private JCheckBox drawDesktopCheckBox;
	private JTextField skinTextField;
	private JLabel skinLabel;
	private JMenuItem exitMenuItem, openMenuItem, saveMenuItem;
	private JPanel previewPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel pagePanel;
	private JMenu fileMenu;
	private JMenuBar menuBar;
	private JTextArea xmlTextArea;
	private JScrollPane xmlScrollPane;

	public static void main (String[] args) throws SlickException {
		new ThingleEditor();
	}
}
