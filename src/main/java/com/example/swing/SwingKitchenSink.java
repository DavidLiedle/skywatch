package com.example.swing;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import javax.swing.plaf.metal.*;
import javax.swing.plaf.nimbus.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Vector;
import javax.swing.text.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class SwingKitchenSink extends JFrame {
    
    private JTabbedPane mainTabbedPane;
    private Timer animationTimer;
    
    public SwingKitchenSink() {
        super("Swing Kitchen Sink - Component Showcase");
        initializeUI();
    }
    
    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create menu bar
        setJMenuBar(createMenuBar());
        
        // Create toolbar
        add(createToolBar(), BorderLayout.NORTH);
        
        // Create main tabbed pane
        mainTabbedPane = new JTabbedPane();
        mainTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        // Add all tabs
        mainTabbedPane.addTab("Basic Components", createBasicComponentsPanel());
        mainTabbedPane.addTab("Text Components", createTextComponentsPanel());
        mainTabbedPane.addTab("Advanced Components", createAdvancedComponentsPanel());
        mainTabbedPane.addTab("Containers", createContainersPanel());
        mainTabbedPane.addTab("Dialogs", createDialogsPanel());
        mainTabbedPane.addTab("Tables & Trees", createTablesTreesPanel());
        mainTabbedPane.addTab("Graphics & Animation", createGraphicsPanel());
        mainTabbedPane.addTab("Look & Feel", createLookAndFeelPanel());
        
        add(mainTabbedPane, BorderLayout.CENTER);
        
        // Create status bar
        add(createStatusBar(), BorderLayout.SOUTH);
        
        // Set size and center
        setSize(1200, 800);
        setLocationRelativeTo(null);
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // File menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(new JMenuItem("New", KeyEvent.VK_N));
        fileMenu.add(new JMenuItem("Open", KeyEvent.VK_O));
        fileMenu.add(new JMenuItem("Save", KeyEvent.VK_S));
        fileMenu.addSeparator();
        JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        
        // Edit menu
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        editMenu.add(new JMenuItem("Cut", KeyEvent.VK_T));
        editMenu.add(new JMenuItem("Copy", KeyEvent.VK_C));
        editMenu.add(new JMenuItem("Paste", KeyEvent.VK_P));
        
        // View menu with checkboxes and radio buttons
        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);
        viewMenu.add(new JCheckBoxMenuItem("Show Toolbar", true));
        viewMenu.add(new JCheckBoxMenuItem("Show Status Bar", true));
        viewMenu.addSeparator();
        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem rbMenuItem1 = new JRadioButtonMenuItem("View 1", true);
        JRadioButtonMenuItem rbMenuItem2 = new JRadioButtonMenuItem("View 2");
        group.add(rbMenuItem1);
        group.add(rbMenuItem2);
        viewMenu.add(rbMenuItem1);
        viewMenu.add(rbMenuItem2);
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        JMenuItem aboutItem = new JMenuItem("About", KeyEvent.VK_A);
        aboutItem.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "Swing Kitchen Sink\nVersion 1.0", 
                "About", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(helpMenu);
        
        return menuBar;
    }
    
    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar("Main Toolbar");
        toolBar.setFloatable(true);
        
        toolBar.add(new JButton(UIManager.getIcon("FileView.fileIcon")));
        toolBar.add(new JButton(UIManager.getIcon("FileView.directoryIcon")));
        toolBar.add(new JButton(UIManager.getIcon("FileView.floppyDriveIcon")));
        toolBar.addSeparator();
        
        JButton cutBtn = new JButton("Cut");
        JButton copyBtn = new JButton("Copy");
        JButton pasteBtn = new JButton("Paste");
        
        toolBar.add(cutBtn);
        toolBar.add(copyBtn);
        toolBar.add(pasteBtn);
        toolBar.addSeparator();
        
        JToggleButton toggleBtn = new JToggleButton("Toggle");
        toolBar.add(toggleBtn);
        
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(new JLabel("Search: "));
        JTextField searchField = new JTextField(10);
        searchField.setMaximumSize(searchField.getPreferredSize());
        toolBar.add(searchField);
        
        return toolBar;
    }
    
    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        JLabel statusLabel = new JLabel("Ready");
        statusBar.add(statusLabel);
        statusBar.add(new JSeparator(SwingConstants.VERTICAL));
        statusBar.add(new JLabel("Line: 1, Column: 1"));
        statusBar.add(Box.createHorizontalGlue());
        
        // Add progress bar to status bar
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setPreferredSize(new Dimension(100, 16));
        statusBar.add(progressBar);
        
        return statusBar;
    }
    
    private JComponent createBasicComponentsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Buttons section
        gbc.gridx = 0; gbc.gridy = row++;
        gbc.gridwidth = 2;
        panel.add(createSectionLabel("Buttons"), gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = row++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(new JButton("Normal Button"));
        buttonPanel.add(new JButton("<html><b>HTML</b> Button</html>"));
        JButton iconButton = new JButton("Icon", UIManager.getIcon("FileView.computerIcon"));
        buttonPanel.add(iconButton);
        buttonPanel.add(new JToggleButton("Toggle Button"));
        gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        
        // Radio buttons and checkboxes
        gbc.gridx = 0; gbc.gridy = row++;
        gbc.gridwidth = 2;
        panel.add(createSectionLabel("Selection Controls"), gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = row++;
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup radioGroup = new ButtonGroup();
        JRadioButton radio1 = new JRadioButton("Option 1", true);
        JRadioButton radio2 = new JRadioButton("Option 2");
        JRadioButton radio3 = new JRadioButton("Option 3");
        radioGroup.add(radio1);
        radioGroup.add(radio2);
        radioGroup.add(radio3);
        selectionPanel.add(radio1);
        selectionPanel.add(radio2);
        selectionPanel.add(radio3);
        selectionPanel.add(new JSeparator(SwingConstants.VERTICAL));
        selectionPanel.add(new JCheckBox("Check 1", true));
        selectionPanel.add(new JCheckBox("Check 2"));
        selectionPanel.add(new JCheckBox("Check 3"));
        gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(selectionPanel, gbc);
        
        // Labels
        gbc.gridx = 0; gbc.gridy = row++;
        gbc.gridwidth = 2;
        panel.add(createSectionLabel("Labels"), gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = row++;
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.add(new JLabel("Plain Label"));
        labelPanel.add(new JLabel("Icon Label", UIManager.getIcon("FileView.hardDriveIcon"), JLabel.LEFT));
        JLabel colorLabel = new JLabel("Colored Label");
        colorLabel.setForeground(Color.BLUE);
        labelPanel.add(colorLabel);
        labelPanel.add(new JLabel("<html><i>HTML</i> <u>Label</u></html>"));
        gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(labelPanel, gbc);
        
        // Sliders
        gbc.gridx = 0; gbc.gridy = row++;
        gbc.gridwidth = 2;
        panel.add(createSectionLabel("Sliders"), gbc);
        
        gbc.gridy = row++;
        JSlider slider1 = new JSlider(0, 100, 50);
        slider1.setMajorTickSpacing(25);
        slider1.setMinorTickSpacing(5);
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);
        panel.add(slider1, gbc);
        
        gbc.gridy = row++;
        JSlider slider2 = new JSlider(JSlider.VERTICAL, 0, 100, 25);
        slider2.setMajorTickSpacing(20);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);
        slider2.setPreferredSize(new Dimension(50, 200));
        panel.add(slider2, gbc);
        
        // Progress bars
        gbc.gridx = 0; gbc.gridy = row++;
        gbc.gridwidth = 2;
        panel.add(createSectionLabel("Progress Indicators"), gbc);
        
        gbc.gridy = row++;
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(65);
        progressBar.setStringPainted(true);
        panel.add(progressBar, gbc);
        
        // Spinners
        gbc.gridx = 0; gbc.gridy = row++;
        gbc.gridwidth = 2;
        panel.add(createSectionLabel("Spinners"), gbc);
        
        gbc.gridy = row++;
        JPanel spinnerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        spinnerPanel.add(new JLabel("Number:"));
        spinnerPanel.add(new JSpinner(new SpinnerNumberModel(5, 0, 10, 1)));
        spinnerPanel.add(new JLabel("Date:"));
        spinnerPanel.add(new JSpinner(new SpinnerDateModel()));
        String[] months = {"January", "February", "March", "April", "May", "June"};
        spinnerPanel.add(new JLabel("List:"));
        spinnerPanel.add(new JSpinner(new SpinnerListModel(months)));
        gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(spinnerPanel, gbc);
        
        return new JScrollPane(panel);
    }
    
    private JComponent createTextComponentsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        
        int row = 0;
        
        // Text fields
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(createSectionLabel("Text Fields"), gbc);
        
        gbc.gridy = row++;
        JPanel textFieldPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        textFieldPanel.add(new JLabel("JTextField:"));
        textFieldPanel.add(new JTextField("Regular text field", 20));
        textFieldPanel.add(new JLabel("JPasswordField:"));
        textFieldPanel.add(new JPasswordField("password", 20));
        textFieldPanel.add(new JLabel("JFormattedTextField:"));
        JFormattedTextField formattedField = new JFormattedTextField(new SimpleDateFormat("MM/dd/yyyy"));
        formattedField.setValue(new Date());
        textFieldPanel.add(formattedField);
        panel.add(textFieldPanel, gbc);
        
        // Text areas
        gbc.gridy = row++;
        panel.add(createSectionLabel("Text Areas"), gbc);
        
        gbc.gridy = row++;
        gbc.weighty = 0.3;
        JTextArea textArea = new JTextArea("This is a JTextArea.\nIt supports multiple lines.\nYou can edit text here.", 5, 30);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane textAreaScroll = new JScrollPane(textArea);
        textAreaScroll.setBorder(BorderFactory.createTitledBorder("JTextArea"));
        panel.add(textAreaScroll, gbc);
        
        // Editor pane
        gbc.gridy = row++;
        gbc.weighty = 0.3;
        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setText("<html><body><h2>JEditorPane</h2>" +
            "<p>This component can display <b>HTML</b> content.</p>" +
            "<ul><li>Item 1</li><li>Item 2</li><li>Item 3</li></ul>" +
            "<p>It supports <i>formatting</i> and <u>styling</u>.</p></body></html>");
        editorPane.setEditable(false);
        JScrollPane editorScroll = new JScrollPane(editorPane);
        editorScroll.setBorder(BorderFactory.createTitledBorder("JEditorPane (HTML)"));
        panel.add(editorScroll, gbc);
        
        // Text pane
        gbc.gridy = row++;
        gbc.weighty = 0.3;
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet regular = new SimpleAttributeSet();
        SimpleAttributeSet bold = new SimpleAttributeSet();
        StyleConstants.setBold(bold, true);
        SimpleAttributeSet italic = new SimpleAttributeSet();
        StyleConstants.setItalic(italic, true);
        SimpleAttributeSet color = new SimpleAttributeSet();
        StyleConstants.setForeground(color, Color.BLUE);
        
        try {
            doc.insertString(doc.getLength(), "JTextPane with ", regular);
            doc.insertString(doc.getLength(), "styled ", bold);
            doc.insertString(doc.getLength(), "text ", italic);
            doc.insertString(doc.getLength(), "support", color);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        
        JScrollPane textPaneScroll = new JScrollPane(textPane);
        textPaneScroll.setBorder(BorderFactory.createTitledBorder("JTextPane"));
        panel.add(textPaneScroll, gbc);
        
        return new JScrollPane(panel);
    }
    
    private JComponent createAdvancedComponentsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        
        int row = 0;
        
        // Combo boxes
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(createSectionLabel("Combo Boxes"), gbc);
        
        gbc.gridy = row++;
        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
        JComboBox<String> combo1 = new JComboBox<>(items);
        comboPanel.add(new JLabel("Editable:"));
        combo1.setEditable(true);
        comboPanel.add(combo1);
        
        JComboBox<String> combo2 = new JComboBox<>(items);
        combo2.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setIcon(UIManager.getIcon("FileView.fileIcon"));
                return this;
            }
        });
        comboPanel.add(new JLabel("With Icons:"));
        comboPanel.add(combo2);
        panel.add(comboPanel, gbc);
        
        // Lists
        gbc.gridy = row++;
        panel.add(createSectionLabel("Lists"), gbc);
        
        gbc.gridy = row++;
        gbc.weighty = 0.3;
        JPanel listPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (int i = 1; i <= 20; i++) {
            listModel.addElement("List Item " + i);
        }
        JList<String> list1 = new JList<>(listModel);
        list1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane listScroll1 = new JScrollPane(list1);
        listScroll1.setBorder(BorderFactory.createTitledBorder("Multiple Selection"));
        listPanel.add(listScroll1);
        
        JList<String> list2 = new JList<>(listModel);
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list2.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index % 2 == 0) {
                    setBackground(new Color(240, 240, 240));
                }
                setIcon(UIManager.getIcon("FileView.fileIcon"));
                return this;
            }
        });
        JScrollPane listScroll2 = new JScrollPane(list2);
        listScroll2.setBorder(BorderFactory.createTitledBorder("Custom Renderer"));
        listPanel.add(listScroll2);
        
        panel.add(listPanel, gbc);
        
        // Layered pane
        gbc.gridy = row++;
        panel.add(createSectionLabel("Layered Pane"), gbc);
        
        gbc.gridy = row++;
        gbc.weighty = 0.3;
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(400, 200));
        layeredPane.setBorder(BorderFactory.createTitledBorder("JLayeredPane"));
        
        JLabel label1 = new JLabel("Layer 1", JLabel.CENTER);
        label1.setBounds(50, 50, 100, 50);
        label1.setOpaque(true);
        label1.setBackground(new Color(255, 200, 200));
        
        JLabel label2 = new JLabel("Layer 2", JLabel.CENTER);
        label2.setBounds(100, 75, 100, 50);
        label2.setOpaque(true);
        label2.setBackground(new Color(200, 255, 200));
        
        JLabel label3 = new JLabel("Layer 3", JLabel.CENTER);
        label3.setBounds(150, 100, 100, 50);
        label3.setOpaque(true);
        label3.setBackground(new Color(200, 200, 255));
        
        layeredPane.add(label1, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(label2, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(label3, JLayeredPane.MODAL_LAYER);
        
        panel.add(layeredPane, gbc);
        
        // Tool tips
        gbc.gridy = row++;
        gbc.weighty = 0;
        panel.add(createSectionLabel("Tooltips"), gbc);
        
        gbc.gridy = row++;
        JPanel tooltipPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton tooltipBtn1 = new JButton("Simple Tooltip");
        tooltipBtn1.setToolTipText("This is a simple tooltip");
        tooltipPanel.add(tooltipBtn1);
        
        JButton tooltipBtn2 = new JButton("HTML Tooltip");
        tooltipBtn2.setToolTipText("<html><b>Bold</b> and <i>italic</i><br>Multi-line tooltip</html>");
        tooltipPanel.add(tooltipBtn2);
        
        panel.add(tooltipPanel, gbc);
        
        return new JScrollPane(panel);
    }
    
    private JComponent createContainersPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        
        int row = 0;
        
        // Split panes
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(createSectionLabel("Split Panes"), gbc);
        
        gbc.gridy = row++;
        gbc.weighty = 0.3;
        JSplitPane horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        horizontalSplit.setLeftComponent(new JButton("Left"));
        horizontalSplit.setRightComponent(new JButton("Right"));
        horizontalSplit.setDividerLocation(200);
        horizontalSplit.setBorder(BorderFactory.createTitledBorder("Horizontal Split"));
        panel.add(horizontalSplit, gbc);
        
        gbc.gridy = row++;
        JSplitPane verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        verticalSplit.setTopComponent(new JButton("Top"));
        verticalSplit.setBottomComponent(new JButton("Bottom"));
        verticalSplit.setDividerLocation(100);
        verticalSplit.setBorder(BorderFactory.createTitledBorder("Vertical Split"));
        panel.add(verticalSplit, gbc);
        
        // Tabbed panes
        gbc.gridy = row++;
        gbc.weighty = 0;
        panel.add(createSectionLabel("Tabbed Panes"), gbc);
        
        gbc.gridy = row++;
        gbc.weighty = 0.2;
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Tab 1", new JLabel("Content 1", JLabel.CENTER));
        tabbedPane.addTab("Tab 2", new JLabel("Content 2", JLabel.CENTER));
        tabbedPane.addTab("Tab 3", new JLabel("Content 3", JLabel.CENTER));
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        tabbedPane.setBorder(BorderFactory.createTitledBorder("JTabbedPane"));
        panel.add(tabbedPane, gbc);
        
        // Desktop pane
        gbc.gridy = row++;
        gbc.weighty = 0;
        panel.add(createSectionLabel("Desktop Pane"), gbc);
        
        gbc.gridy = row++;
        gbc.weighty = 0.3;
        JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.setBackground(Color.LIGHT_GRAY);
        desktopPane.setBorder(BorderFactory.createTitledBorder("JDesktopPane"));
        
        JInternalFrame frame1 = new JInternalFrame("Frame 1", true, true, true, true);
        frame1.setBounds(10, 10, 200, 150);
        frame1.setVisible(true);
        desktopPane.add(frame1);
        
        JInternalFrame frame2 = new JInternalFrame("Frame 2", true, true, true, true);
        frame2.setBounds(150, 50, 200, 150);
        frame2.setVisible(true);
        desktopPane.add(frame2);
        
        panel.add(desktopPane, gbc);
        
        return new JScrollPane(panel);
    }
    
    private JComponent createDialogsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Message dialogs
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(createSectionLabel("Message Dialogs"), gbc);
        
        gbc.gridy = row++;
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton infoBtn = new JButton("Information");
        infoBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "This is an information message", "Information", JOptionPane.INFORMATION_MESSAGE));
        messagePanel.add(infoBtn);
        
        JButton warningBtn = new JButton("Warning");
        warningBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "This is a warning message", "Warning", JOptionPane.WARNING_MESSAGE));
        messagePanel.add(warningBtn);
        
        JButton errorBtn = new JButton("Error");
        errorBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "This is an error message", "Error", JOptionPane.ERROR_MESSAGE));
        messagePanel.add(errorBtn);
        
        JButton questionBtn = new JButton("Question");
        questionBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "This is a question message", "Question", JOptionPane.QUESTION_MESSAGE));
        messagePanel.add(questionBtn);
        
        panel.add(messagePanel, gbc);
        
        // Input dialogs
        gbc.gridy = row++;
        panel.add(createSectionLabel("Input Dialogs"), gbc);
        
        gbc.gridy = row++;
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton inputBtn = new JButton("Text Input");
        inputBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter some text:");
            if (input != null) {
                JOptionPane.showMessageDialog(this, "You entered: " + input);
            }
        });
        inputPanel.add(inputBtn);
        
        JButton comboInputBtn = new JButton("Combo Input");
        comboInputBtn.addActionListener(e -> {
            String[] options = {"Option 1", "Option 2", "Option 3"};
            String selected = (String) JOptionPane.showInputDialog(this,
                "Choose an option:", "Selection", JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
            if (selected != null) {
                JOptionPane.showMessageDialog(this, "You selected: " + selected);
            }
        });
        inputPanel.add(comboInputBtn);
        
        panel.add(inputPanel, gbc);
        
        // Confirm dialogs
        gbc.gridy = row++;
        panel.add(createSectionLabel("Confirm Dialogs"), gbc);
        
        gbc.gridy = row++;
        JPanel confirmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton yesNoBtn = new JButton("Yes/No");
        yesNoBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                "Do you want to continue?", "Confirm", JOptionPane.YES_NO_OPTION);
            JOptionPane.showMessageDialog(this, 
                result == JOptionPane.YES_OPTION ? "You clicked Yes" : "You clicked No");
        });
        confirmPanel.add(yesNoBtn);
        
        JButton yesNoCancelBtn = new JButton("Yes/No/Cancel");
        yesNoCancelBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                "Save changes?", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION);
            String message = result == JOptionPane.YES_OPTION ? "Yes" :
                           result == JOptionPane.NO_OPTION ? "No" : "Cancel";
            JOptionPane.showMessageDialog(this, "You clicked: " + message);
        });
        confirmPanel.add(yesNoCancelBtn);
        
        panel.add(confirmPanel, gbc);
        
        // File choosers
        gbc.gridy = row++;
        panel.add(createSectionLabel("File Dialogs"), gbc);
        
        gbc.gridy = row++;
        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton openBtn = new JButton("Open File");
        openBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "text"));
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                JOptionPane.showMessageDialog(this, "Selected: " + file.getName());
            }
        });
        filePanel.add(openBtn);
        
        JButton saveBtn = new JButton("Save File");
        saveBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                JOptionPane.showMessageDialog(this, "Save to: " + file.getName());
            }
        });
        filePanel.add(saveBtn);
        
        JButton colorBtn = new JButton("Choose Color");
        colorBtn.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Choose a Color", Color.WHITE);
            if (color != null) {
                colorBtn.setBackground(color);
            }
        });
        filePanel.add(colorBtn);
        
        panel.add(filePanel, gbc);
        
        return new JScrollPane(panel);
    }
    
    private JComponent createTablesTreesPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        
        int row = 0;
        
        // Table
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(createSectionLabel("JTable"), gbc);
        
        gbc.gridy = row++;
        gbc.weighty = 0.5;
        String[] columnNames = {"First Name", "Last Name", "Age", "Email"};
        Object[][] data = {
            {"John", "Doe", 30, "john@example.com"},
            {"Jane", "Smith", 25, "jane@example.com"},
            {"Bob", "Johnson", 35, "bob@example.com"},
            {"Alice", "Williams", 28, "alice@example.com"},
            {"Charlie", "Brown", 42, "charlie@example.com"},
            {"Diana", "Davis", 31, "diana@example.com"},
            {"Eve", "Miller", 29, "eve@example.com"},
            {"Frank", "Wilson", 38, "frank@example.com"}
        };
        
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);
        
        // Add sorting
        table.setAutoCreateRowSorter(true);
        
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Sortable Table"));
        panel.add(tableScroll, gbc);
        
        // Tree
        gbc.gridy = row++;
        panel.add(createSectionLabel("JTree"), gbc);
        
        gbc.gridy = row++;
        gbc.weighty = 0.5;
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        DefaultMutableTreeNode parent1 = new DefaultMutableTreeNode("Parent 1");
        parent1.add(new DefaultMutableTreeNode("Child 1.1"));
        parent1.add(new DefaultMutableTreeNode("Child 1.2"));
        parent1.add(new DefaultMutableTreeNode("Child 1.3"));
        
        DefaultMutableTreeNode parent2 = new DefaultMutableTreeNode("Parent 2");
        parent2.add(new DefaultMutableTreeNode("Child 2.1"));
        parent2.add(new DefaultMutableTreeNode("Child 2.2"));
        
        DefaultMutableTreeNode parent3 = new DefaultMutableTreeNode("Parent 3");
        DefaultMutableTreeNode subParent = new DefaultMutableTreeNode("Sub-Parent");
        subParent.add(new DefaultMutableTreeNode("Leaf 1"));
        subParent.add(new DefaultMutableTreeNode("Leaf 2"));
        parent3.add(subParent);
        
        root.add(parent1);
        root.add(parent2);
        root.add(parent3);
        
        JTree tree = new JTree(root);
        tree.setShowsRootHandles(true);
        tree.setRootVisible(true);
        
        // Custom renderer
        tree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value,
                    boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                if (leaf) {
                    setIcon(UIManager.getIcon("FileView.fileIcon"));
                } else if (expanded) {
                    setIcon(UIManager.getIcon("FileView.directoryIcon"));
                } else {
                    setIcon(UIManager.getIcon("FileView.closedFolderIcon"));
                }
                return this;
            }
        });
        
        JScrollPane treeScroll = new JScrollPane(tree);
        treeScroll.setBorder(BorderFactory.createTitledBorder("Tree with Custom Icons"));
        panel.add(treeScroll, gbc);
        
        return new JScrollPane(panel);
    }
    
    private JPanel createGraphicsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Create custom painting panel
        JPanel drawingPanel = new JPanel() {
            private int animationX = 0;
            private double angle = 0;
            
            {
                // Animation timer
                animationTimer = new Timer(50, e -> {
                    animationX = (animationX + 5) % getWidth();
                    angle += 0.1;
                    repaint();
                });
                animationTimer.start();
            }
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(230, 230, 250),
                    getWidth(), getHeight(), new Color(250, 230, 230));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Draw shapes
                g2d.setColor(Color.BLUE);
                g2d.fillRect(50, 50, 100, 80);
                
                g2d.setColor(Color.RED);
                g2d.fillOval(200, 50, 100, 100);
                
                g2d.setColor(Color.GREEN);
                int[] xPoints = {350, 400, 450};
                int[] yPoints = {150, 50, 150};
                g2d.fillPolygon(xPoints, yPoints, 3);
                
                // Draw lines
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(50, 200, 450, 200);
                
                // Draw arc
                g2d.setColor(Color.ORANGE);
                g2d.fillArc(50, 250, 100, 100, 0, 90);
                
                // Draw rounded rectangle
                g2d.setColor(Color.MAGENTA);
                g2d.fillRoundRect(200, 250, 100, 80, 20, 20);
                
                // Draw text
                g2d.setColor(Color.DARK_GRAY);
                g2d.setFont(new Font("Arial", Font.BOLD, 24));
                g2d.drawString("Graphics2D Demo", 50, 400);
                
                // Animated elements
                g2d.setColor(new Color(0, 150, 0, 128)); // Semi-transparent
                g2d.fillOval(animationX, 450, 50, 50);
                
                // Rotating square
                AffineTransform oldTransform = g2d.getTransform();
                g2d.translate(400, 350);
                g2d.rotate(angle);
                g2d.setColor(Color.BLUE);
                g2d.fillRect(-25, -25, 50, 50);
                g2d.setTransform(oldTransform);
            }
        };
        
        drawingPanel.setPreferredSize(new Dimension(500, 500));
        drawingPanel.setBorder(BorderFactory.createTitledBorder("Custom Graphics & Animation"));
        
        panel.add(drawingPanel, BorderLayout.CENTER);
        
        // Control panel
        JPanel controlPanel = new JPanel();
        JButton startBtn = new JButton("Start Animation");
        JButton stopBtn = new JButton("Stop Animation");
        
        startBtn.addActionListener(e -> {
            if (animationTimer != null && !animationTimer.isRunning()) {
                animationTimer.start();
            }
        });
        
        stopBtn.addActionListener(e -> {
            if (animationTimer != null && animationTimer.isRunning()) {
                animationTimer.stop();
            }
        });
        
        controlPanel.add(startBtn);
        controlPanel.add(stopBtn);
        panel.add(controlPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JComponent createLookAndFeelPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        int row = 0;
        
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(createSectionLabel("Available Look and Feels"), gbc);
        
        // Get available look and feels
        UIManager.LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
        ButtonGroup lafGroup = new ButtonGroup();
        
        for (UIManager.LookAndFeelInfo laf : lafs) {
            gbc.gridy = row++;
            JRadioButton lafButton = new JRadioButton(laf.getName());
            lafButton.addActionListener(e -> {
                try {
                    UIManager.setLookAndFeel(laf.getClassName());
                    SwingUtilities.updateComponentTreeUI(SwingKitchenSink.this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            lafGroup.add(lafButton);
            panel.add(lafButton, gbc);
            
            // Select current LAF
            if (laf.getClassName().equals(UIManager.getLookAndFeel().getClass().getName())) {
                lafButton.setSelected(true);
            }
        }
        
        // Borders showcase
        gbc.gridy = row++;
        panel.add(Box.createVerticalStrut(20), gbc);
        
        gbc.gridy = row++;
        panel.add(createSectionLabel("Border Types"), gbc);
        
        gbc.gridy = row++;
        JPanel borderPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        
        JLabel label1 = new JLabel("Line Border", JLabel.CENTER);
        label1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        borderPanel.add(label1);
        
        JLabel label2 = new JLabel("Raised Bevel", JLabel.CENTER);
        label2.setBorder(BorderFactory.createRaisedBevelBorder());
        borderPanel.add(label2);
        
        JLabel label3 = new JLabel("Lowered Bevel", JLabel.CENTER);
        label3.setBorder(BorderFactory.createLoweredBevelBorder());
        borderPanel.add(label3);
        
        JLabel label4 = new JLabel("Etched Border", JLabel.CENTER);
        label4.setBorder(BorderFactory.createEtchedBorder());
        borderPanel.add(label4);
        
        JLabel label5 = new JLabel("Titled Border", JLabel.CENTER);
        label5.setBorder(BorderFactory.createTitledBorder("Title"));
        borderPanel.add(label5);
        
        JLabel label6 = new JLabel("Matte Border", JLabel.CENTER);
        label6.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE));
        borderPanel.add(label6);
        
        JLabel label7 = new JLabel("Compound Border", JLabel.CENTER);
        label7.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.RED),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        borderPanel.add(label7);
        
        JLabel label8 = new JLabel("Empty Border", JLabel.CENTER);
        label8.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        label8.setOpaque(true);
        label8.setBackground(Color.LIGHT_GRAY);
        borderPanel.add(label8);
        
        JLabel label9 = new JLabel("Dashed Border", JLabel.CENTER);
        label9.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
        borderPanel.add(label9);
        
        panel.add(borderPanel, gbc);
        
        return new JScrollPane(panel);
    }
    
    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(0, 0, 128));
        return label;
    }
    
    public static void main(String[] args) {
        try {
            // Set Nimbus look and feel for a modern appearance
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to default
        }
        
        SwingUtilities.invokeLater(() -> {
            SwingKitchenSink app = new SwingKitchenSink();
            app.setVisible(true);
        });
    }
}