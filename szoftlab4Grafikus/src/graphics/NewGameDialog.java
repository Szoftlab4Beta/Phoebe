package graphics;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class NewGameDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	int turns = -1, players = -1;
	String fileName = "Map1";
	
	/**
	 * Create the dialog.
	 */
	public NewGameDialog(GUIButtonActionListener listener) {
		NumberFormat intFormat = NumberFormat.getIntegerInstance();
		
		setTitle("New Game");
		setBounds(100, 100, 450, 185);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		{
			JLabel lblNewLabel = new JLabel("# turns");
			contentPanel.add(lblNewLabel);
		}
		{
			JFormattedTextField formattedTextField = new JFormattedTextField(intFormat);
			formattedTextField.addPropertyChangeListener("value", new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					if(evt.getNewValue() != null)
						turns = Integer.parseInt(evt.getNewValue().toString());
				}
			});
			contentPanel.add(formattedTextField);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("# players");
			contentPanel.add(lblNewLabel_1);
		}
		{
			JFormattedTextField formattedTextField = new JFormattedTextField(intFormat);
			formattedTextField.addPropertyChangeListener("value", new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					if(evt.getNewValue() != null)
						players = Integer.parseInt(evt.getNewValue().toString());
				}
			});
						
			contentPanel.add(formattedTextField);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton newGameButton = new JButton("New Game");
				newGameButton.setActionCommand("NewGame");
				newGameButton.addActionListener(listener);
				buttonPane.add(newGameButton);
				getRootPane().setDefaultButton(newGameButton);
			}
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			{
				JComboBox comboBox = new JComboBox();
				comboBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						fileName = ((JComboBox)e.getSource()).getSelectedItem().toString();
					}
				});
				comboBox.setModel(new DefaultComboBoxModel(new String[] {"Map1", "Map2", "Map3", "Map4"}));
				panel.add(comboBox);
			}
		}
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	public int getPlayerNum(){
		if(turns <= 0)
			return 2;
		return players;
	}
	
	public int getTurns(){
		if(turns <= 0)
			return 30;
		return turns;
	}

	public String getMapFile() {
		return fileName;
	}
}
