package tweetspeak.main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import tweetspeak.collections.GrammarRules;
import tweetspeak.divisions.Code;

public class ParseOutput implements ActionListener {

	private String title = "TweetSpeak Tokens", text = "";
	private JFrame frame;
	private JScrollPane scrollPane;
	private JPanel panel1, panel2;
	private JTextArea textArea;
	private JButton buttonSource, buttonParsed, buttonProductionRules, buttonClose;
	
	public ParseOutput() {
		frame = new JFrame(title);
		panel1 = new JPanel();
		panel2 = new JPanel();
		textArea = new JTextArea("");
		buttonSource = new JButton("Source");
		buttonParsed = new JButton("Parse Tree");
		buttonProductionRules = new JButton("Production Rules");
		buttonClose = new JButton("Close");

		text += "ORIGINAL SOURCE: \n============================================================================================================================================\n\n" 
				+ Code.toLines();
		text += "\n============================================================================================================================================\n\n";
	}
	
	public ParseOutput(String filename) {
		this();
		frame.setTitle(title + " - " + filename);
	}
	
	public void launch() {
		textArea.setBackground(Color.BLACK);
		textArea.setFont(new java.awt.Font("Consolas", 0, 14));
		textArea.setForeground(Color.white);
		textArea.setTabSize(2);
		textArea.setText(text);
		textArea.setEditable(false);
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(640, 480));
		scrollPane.setWheelScrollingEnabled(true);
		
		panel1.setLayout(new BorderLayout());
		panel1.add(scrollPane, BorderLayout.CENTER);
		
		panel2.setLayout(new GridLayout(1,4));
		panel2.add(buttonSource);
		panel2.add(buttonParsed);
		panel2.add(buttonProductionRules);
		panel2.add(buttonClose);
		
		frame.setLayout(new BorderLayout());
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
		
		buttonSource.setEnabled(false);
		buttonSource.addActionListener(this);
		buttonParsed.addActionListener(this);
		buttonProductionRules.addActionListener(this);
		buttonClose.addActionListener(this);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		
		if (source == buttonSource) {
			textArea.setText(text);
			buttonSource.setEnabled(false);
			buttonParsed.setEnabled(true);
			buttonProductionRules.setEnabled(true);
			textArea.setWrapStyleWord(false);
		} else if (source == buttonParsed) {
			buttonSource.setEnabled(true);
			buttonParsed.setEnabled(false);
			buttonProductionRules.setEnabled(true);
			/*Tokenizer.reset();
			
			Token token = Tokenizer.getToken();
			String output = "";
			
			while (token != null) {
				if (!token.getName().equals("NO_INDENT") && !token.getName().equals("NEWLINE"))
					output += token.printToken();
				if (token.getName().equals("NEWLINE") 
						|| token.getNextIndex() == Code.getLine(Tokenizer.getLineNumber()).getLineCode().length()) 
						output += "\n";
				token = Tokenizer.getToken();
			}
			
			textArea.setText(output);
			*/
			textArea.setWrapStyleWord(false);
			
		} else if (source == buttonProductionRules) {
			String text = "";
			text += "\nGRAMMAR RULES	: \n============================================================================================================================================\n\n"; 
			text += GrammarRules.printRules();
			text += "\n============================================================================================================================================\n\n";
			textArea.setText(text);
			textArea.setWrapStyleWord(true);
			buttonSource.setEnabled(true);
			buttonParsed.setEnabled(true);
			buttonProductionRules.setEnabled(false);
		} else if (source == buttonClose) frame.dispose();
	}
}