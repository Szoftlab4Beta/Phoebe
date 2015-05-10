package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import szoftlab4Proto.App;
import szoftlab4Proto.Tile;

public class GraphicsController {

	public enum ImageID{Goo0, Goo1, Goo2, Oil0, Oil1, Oil2, Robot, Janitor};
	
	Map<ImageID, ImageIcon> sprites = new HashMap<ImageID, ImageIcon>();
	JLabel mapImageLabel;
	NewGameDialog newGameDialog;
	List<TileGraphics> tiles = new ArrayList<TileGraphics>();
	JLabel currentRobot;
	ButtonGroup patchButtons;
	JRadioButton noneRadioButton;
	JLayeredPane mapContentPane;
	JPanel controlPane;
	JLabel turnLabel;
	int tileSize = 32;
	
	private JFrame frame;
	
	public GraphicsController(GUIButtonActionListener listener) {
		initialize(listener);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(GUIButtonActionListener listener) {

		frame = new JFrame();
		frame.setBounds(100, 100, 1024, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		sprites.put(ImageID.Oil0, new ImageIcon(App.asFilePath("pictures", "oil1", "png")));
		sprites.put(ImageID.Oil1, new ImageIcon(App.asFilePath("pictures", "oil2", "png")));
		sprites.put(ImageID.Oil2, new ImageIcon(App.asFilePath("pictures", "oil2", "png")));
		sprites.put(ImageID.Goo0, new ImageIcon(App.asFilePath("pictures", "oil3", "png")));
		sprites.put(ImageID.Goo1, new ImageIcon(App.asFilePath("pictures", "oil3", "png")));
		sprites.put(ImageID.Goo2, new ImageIcon(App.asFilePath("pictures", "oil3", "png")));
		sprites.put(ImageID.Robot, new ImageIcon(App.asFilePath("pictures", "robot1", "png")));
		sprites.put(ImageID.Janitor, new ImageIcon(App.asFilePath("pictures", "janitor", "png")));
		
		controlPane = new JPanel();
		frame.getContentPane().add(controlPane, BorderLayout.EAST);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] {0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		controlPane.setLayout(gbl_panel_1);
		
		currentRobot = new JLabel("Robot 0");
		GridBagConstraints gbc_lblRobot = new GridBagConstraints();
		gbc_lblRobot.gridwidth = 3;
		gbc_lblRobot.insets = new Insets(0, 0, 5, 0);
		gbc_lblRobot.gridx = 0;
		gbc_lblRobot.gridy = 0;
		controlPane.add(currentRobot, gbc_lblRobot);
		
		noneRadioButton = new JRadioButton("None");
		noneRadioButton.setOpaque(false);
		noneRadioButton.setSelected(true);
		noneRadioButton.addActionListener(listener);
		noneRadioButton.setActionCommand("Patch_None");
		GridBagConstraints gbc_noneRadioButton = new GridBagConstraints();
		gbc_noneRadioButton.anchor = GridBagConstraints.WEST;
		gbc_noneRadioButton.insets = new Insets(0, 0, 5, 5);
		gbc_noneRadioButton.gridx = 1;
		gbc_noneRadioButton.gridy = 1;
		controlPane.add(noneRadioButton, gbc_noneRadioButton);
		
		JRadioButton oilRadioButton = new JRadioButton("Oil");
		oilRadioButton.addActionListener(listener);
		oilRadioButton.setActionCommand("Oil");
		GridBagConstraints gbc_oilRadioButton = new GridBagConstraints();
		gbc_oilRadioButton.anchor = GridBagConstraints.WEST;
		gbc_oilRadioButton.insets = new Insets(0, 0, 5, 5);
		gbc_oilRadioButton.gridx = 1;
		gbc_oilRadioButton.gridy = 2;
		controlPane.add(oilRadioButton, gbc_oilRadioButton);
		
		JRadioButton gooRadioButton = new JRadioButton("Goo");
		gooRadioButton.addActionListener(listener);
		gooRadioButton.setActionCommand("Goo");
		GridBagConstraints gbc_gooRadioButton = new GridBagConstraints();
		gbc_gooRadioButton.anchor = GridBagConstraints.WEST;
		gbc_gooRadioButton.insets = new Insets(0, 0, 5, 5);
		gbc_gooRadioButton.gridx = 1;
		gbc_gooRadioButton.gridy = 3;
		controlPane.add(gooRadioButton, gbc_gooRadioButton);
		
		patchButtons = new ButtonGroup();
		patchButtons.add(noneRadioButton);
		patchButtons.add(oilRadioButton);
		patchButtons.add(gooRadioButton);
		
		JButton upButton = new JButton("");
		upButton.setIcon(new ImageIcon(App.asFilePath("pictures", "UarrowImage", "png")));
		upButton.setActionCommand("Up");
		upButton.addActionListener(listener);
		GridBagConstraints gbc_upButton = new GridBagConstraints();
		gbc_upButton.insets = new Insets(0, 0, 5, 5);
		gbc_upButton.gridx = 1;
		gbc_upButton.gridy = 6;
		controlPane.add(upButton, gbc_upButton);
		
		JButton leftButton = new JButton("");
		leftButton.setIcon(new ImageIcon(App.asFilePath("pictures", "LarrowImage", "png")));
		leftButton.setActionCommand("Left");
		leftButton.addActionListener(listener);
		GridBagConstraints gbc_leftButton = new GridBagConstraints();
		gbc_leftButton.insets = new Insets(0, 0, 5, 5);
		gbc_leftButton.anchor = GridBagConstraints.WEST;
		gbc_leftButton.gridx = 0;
		gbc_leftButton.gridy = 7;
		controlPane.add(leftButton, gbc_leftButton);
		
		JButton middleButton = new JButton("");
		middleButton.setIcon(new ImageIcon(App.asFilePath("pictures", "xIcon", "png")));
		middleButton.setActionCommand("None");
		middleButton.addActionListener(listener);
		GridBagConstraints gbc_middleButton = new GridBagConstraints();
		gbc_middleButton.insets = new Insets(0, 0, 5, 5);
		gbc_middleButton.gridx = 1;
		gbc_middleButton.gridy = 7;
		controlPane.add(middleButton, gbc_middleButton);
		
		JButton rightButton = new JButton("");
		rightButton.setIcon(new ImageIcon(App.asFilePath("pictures", "RarrowImage", "png")));
		rightButton.setActionCommand("Right");
		rightButton.addActionListener(listener);
		GridBagConstraints gbc_rightButton = new GridBagConstraints();
		gbc_rightButton.insets = new Insets(0, 0, 5, 0);
		gbc_rightButton.gridx = 2;
		gbc_rightButton.gridy = 7;
		controlPane.add(rightButton, gbc_rightButton);
		
		JButton downButton = new JButton("");
		downButton.setIcon(new ImageIcon(App.asFilePath("pictures", "DarrowImage", "png")));
		downButton.setActionCommand("Down");
		downButton.addActionListener(listener);
		GridBagConstraints gbc_downButton = new GridBagConstraints();
		gbc_downButton.insets = new Insets(0, 0, 5, 5);
		gbc_downButton.gridx = 1;
		gbc_downButton.gridy = 8;
		controlPane.add(downButton, gbc_downButton);
		
		JButton confirmButton = new JButton("Confirm");
		confirmButton.setActionCommand("Confirm");
		confirmButton.addActionListener(listener);
		GridBagConstraints gbc_confirmButton = new GridBagConstraints();
		gbc_confirmButton.insets = new Insets(0, 0, 5, 0);
		gbc_confirmButton.gridwidth = 3;
		gbc_confirmButton.gridx = 0;
		gbc_confirmButton.gridy = 10;
		controlPane.add(confirmButton, gbc_confirmButton);
		
		turnLabel = new JLabel("Turn 0/30");
		GridBagConstraints gbc_lblTurn = new GridBagConstraints();
		gbc_lblTurn.gridwidth = 3;
		gbc_lblTurn.insets = new Insets(0, 0, 0, 5);
		gbc_lblTurn.gridx = 0;
		gbc_lblTurn.gridy = 11;
		controlPane.add(turnLabel, gbc_lblTurn);
		
		mapContentPane = new JLayeredPane();
		frame.getContentPane().add(mapContentPane, BorderLayout.CENTER);
		mapContentPane.setLayout(null);
		
		mapImageLabel = new JLabel("");
		mapImageLabel.setBounds(0, 0, 881, 705);
		mapImageLabel.setBackground(new Color(0, 0, 0, 0));		
		mapContentPane.add(mapImageLabel);
		mapContentPane.setLayer(mapImageLabel, 0);
		mapImageLabel.setIcon(null);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenuItem mntmNewGame = new JMenuItem("New Game");
		mntmNewGame.addActionListener(new NewGameMenuListener());
		menuBar.add(mntmNewGame);
		
		newGameDialog = new NewGameDialog(listener);
	}
	
	class NewGameMenuListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			newGameDialog.setVisible(true);
		}
	}
	
	public void setMapImage(){
		mapImageLabel.setIcon(new ImageIcon(App.asFilePath("MapFiles", getMapFile(), "png")));
	}

	public int getPlayerNum() {
		return newGameDialog.getPlayerNum();
	}

	public int getTurns() {
		return newGameDialog.getTurns();
	}

	public String getMapFile() {
		return newGameDialog.getMapFile();
	}
	
	public void setMap(Tile[][] mapTiles, int width, int height){
		int dy = frame.getHeight() - mapContentPane.getHeight();
		int dx = frame.getWidth() - mapContentPane.getWidth();
		frame.setSize(width * tileSize + dx, (height < (400 / tileSize) ? 400 : height * tileSize + dy));
		mapImageLabel.setBounds(new Rectangle(0, 0, tileSize * width, tileSize * height));
		Color alphaNull = new Color(0, 0, 0, 0);
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				TileGraphics tile = new TileGraphics(mapTiles[y][x], x, y);
				JLayeredPane panel = new JLayeredPane();
				panel.setBounds(x * 32, y * 32, 32, 32);
				panel.setLayout(new BorderLayout());
				panel.setBackground(alphaNull);
				mapContentPane.add(panel);
				mapContentPane.setLayer(panel, 1);
				tile.panel = panel;
				tiles.add(tile);
				panel.setVisible(true);
			}
		}
	}
	
	public void redrawMap(){
		for (TileGraphics tile : tiles) {
			if(tile.hasChanged()){
				JLayeredPane tilePanel = tile.panel;
				tilePanel.removeAll();
				ImageID[] contents = tile.getSprites();
				if(contents == null){
					continue;
				}
				int layer = 1;
				for (ImageID imageID : contents) {
					JLabel label = new JLabel(sprites.get(imageID));
					tilePanel.add(label, BorderLayout.CENTER);
					tilePanel.setLayer(label, layer);
					layer++;
				}
			}
		}
		frame.repaint();
	}
	
	public void show(){
		frame.setVisible(true);
		newGameDialog.setVisible(false);
	}
	
	public void setCurrentRobot(String name){
		currentRobot.setText(name);
	}

	public void resetButtons() {
		patchButtons.clearSelection();
		noneRadioButton.setSelected(true);
	}

	public void cleanUp() {
		mapContentPane.removeAll();
		
		mapImageLabel = new JLabel("");
		mapImageLabel.setBounds(0, 0, 881, 705);
		mapImageLabel.setBackground(new Color(0, 0, 0, 0));		
		mapContentPane.add(mapImageLabel);
		mapContentPane.setLayer(mapImageLabel, 0);
		mapImageLabel.setIcon(null);
	}

	public void setWinner(int winner) {
		currentRobot.setText("Winner: Robot " + winner);
		enableControls(false);
	}
	
	public void enableControls(boolean enabled){
		Component[] components = controlPane.getComponents();
		for(Component component : components){
			component.setEnabled(enabled);
		}
	}
	
	public void setTurns(int current, int max){
		turnLabel.setText("Turn " + current + "/" + max);
	}
}
