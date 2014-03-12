import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


public class Main extends JFrame {

	JLabel urlLbl=new JLabel("Url");
	JLabel bodyLbl=new JLabel("Body");
	JLabel outputLbl=new JLabel("Output");
	JLabel headerLbl=new JLabel("Header");
	JLabel methodLbl=new JLabel("Method");
	JLabel responseLbl=new JLabel("");
	
	JTextField urlTxt=new JTextField();
	JTextArea bodyTxt=new JTextArea();
	JTextArea outputTxt=new JTextArea();
	JTextArea rawoutputTxt=new JTextArea();
	JComboBox methodCombo=new JComboBox();
	
	JScrollPane scrollPane=new JScrollPane();
	JScrollPane HeaderscrollPane=new JScrollPane();
	JScrollPane bodycrollPane=new JScrollPane();
	
	JButton button=new JButton("Send");
	
	JPanel mainPanel=new JPanel();
	
	JPanel responsePanel=new JPanel();
	
	String methodTypes[]=new String[]{"POST","GET","PUT","DELETE"};
	String headers[]=new String[]{"Header Name","Header Value"};
	String headerNames[]=new String[]{"content-type","Accept","Cookie","Accept-Encoding"};
	String headerValues[]=new String[]{"text/xml","text/plain","text/html","application/xml","application/json"};
	String data[][]=new String[][]{{"content-type","Value"},{"dsfds","Value"},{},{},{},{},{},{}};
	
	JTable jTable=new JTable(10,2);
//	JTable jTable=new JTable(data,headers);
	
	JProgressBar progressBar=new JProgressBar();
	String dataResponse="";
	
	JTabbedPane pane=new JTabbedPane();
	JPanel rawResultPanel=new JPanel();
	JPanel htmlResultPanel=new JPanel();
	JScrollPane htmlScrollPane=new JScrollPane();
	
	JEditorPane editorPane=new JEditorPane();

	JMenuBar jMenuBar =new JMenuBar();
	
	static ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
	static int size=0;
	boolean isConsoleStarted=false;
	
	boolean isNew=true;
	String fileName="";
	
	public Main() {
		
		this.getContentPane().add(mainPanel,BorderLayout.CENTER);
		
		JPanel bottomPanel=new JPanel();
		//this.getContentPane().add(bottomPanel,BorderLayout.SOUTH);
		String imageFile=this.getClass().getClassLoader().getResource("dt-arrow-dn.png").getFile();
		Icon icon=new ImageIcon(imageFile);
		JButton downArrBtn=new JButton(icon);
		downArrBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		bottomPanel.add(downArrBtn);
		
		mainPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gc=new GridBagConstraints();

		int gridy=0;
		gc.insets=new Insets(4, 5, 2,10);
		
		gc.weightx=0;
		gc.gridx=0;
		gc.gridy=gridy++;
		gc.anchor=GridBagConstraints.LINE_END;
		
		mainPanel.add(urlLbl,gc);
		
		gc.gridx=1;
		gc.weightx=5;
		gc.anchor=GridBagConstraints.LINE_START;
		gc.fill=GridBagConstraints.HORIZONTAL;
		mainPanel.add(urlTxt,gc);
		
		gc.weightx=0;
		gc.gridx=0;
		gc.gridy=gridy++;
		gc.fill=GridBagConstraints.NONE;
		gc.anchor=GridBagConstraints.LINE_END;
		
		mainPanel.add(methodLbl,gc);
		
		gc.gridx=1;
		gc.weightx=5;
		gc.anchor=GridBagConstraints.LINE_START;
		gc.fill=GridBagConstraints.HORIZONTAL;
		mainPanel.add(methodCombo,gc);
		
		gc.weightx=2;
		gc.gridx=0;
		gc.gridy=gridy++;
		
		gc.weightx=4;
		gc.gridwidth=2;
		gc.anchor=GridBagConstraints.FIRST_LINE_START;
		gc.fill=GridBagConstraints.BOTH;
		gc.weighty=1;
		
		mainPanel.add(HeaderscrollPane,gc);
		
		HeaderscrollPane.add(jTable);
		HeaderscrollPane.setViewportView(jTable);
		
		//gc.gridwidth=1;
		gc.weighty=0;
		gc.weightx=2;
		gc.gridx=0;
		gc.gridy=gridy++;
		gc.anchor=GridBagConstraints.FIRST_LINE_END;
		
		mainPanel.add(bodyLbl,gc);
		
		gc.weighty=1;
		gc.gridheight=2;
		gc.gridy=3;
		gc.gridx=0;
		gc.gridy=gridy++;
		gc.anchor=GridBagConstraints.FIRST_LINE_START;
		gc.fill=GridBagConstraints.BOTH;
		gc.weighty=1;
		
		bodycrollPane.add(bodyTxt);
		bodycrollPane.setViewportView(bodyTxt);
		
		mainPanel.add(bodycrollPane,gc);
		gridy++;
		
		gc.weighty=0;
		gc.gridx=0;
		gc.gridy=gridy++;
		gc.weightx=2;
		gc.gridheight=1;
		gc.anchor=GridBagConstraints.FIRST_LINE_END;
		
		mainPanel.add(outputLbl,gc);
		gridy++;
		
		gc.gridheight=2;
		gc.gridx=0;
		gc.gridy=gridy++;
		gc.weightx=4;
		gc.weighty=1;
		gc.anchor=GridBagConstraints.FIRST_LINE_START;
		gc.fill=GridBagConstraints.BOTH;
		gc.weighty=1;
		
		//mainPanel.add(scrollPane,gc);
		mainPanel.add(pane,gc);
		gridy++;
		
		scrollPane.add(rawoutputTxt);
		scrollPane.setViewportView(rawoutputTxt);
		
		gc.gridy=gridy++;
		gc.gridx=0;
		gc.weightx=1;
		gc.anchor=GridBagConstraints.FIRST_LINE_END;
		gc.fill=GridBagConstraints.HORIZONTAL;
		gc.weighty=0;
		gc.gridwidth=1;
		
		mainPanel.add(responsePanel,gc);
		
		gc.gridx=1;
		gc.weightx=0;
		gc.anchor=GridBagConstraints.FIRST_LINE_END;
		gc.fill=GridBagConstraints.NONE;
		gc.weighty=0;
		gc.gridwidth=1;
		
		mainPanel.add(button,gc);
		
//		responsePanel.add(progressBar,gc);
		responsePanel.add(progressBar);
		
		jTable.setGridColor(Color.gray);
		JTableHeader tableHeader=new JTableHeader();
		
		TableColumnModel columnModel=new DefaultTableColumnModel();
		
		//columnModel.
		
		//tableHeader.setColumnModel(columnModel);
		//jTable.setTableHeader(tableHeader);
		
		TableColumn valuesColumn = jTable.getColumnModel().getColumn(1);
		JComboBox comboBox = new JComboBox(headerValues);
		comboBox.setEditable(true);
		valuesColumn.setCellEditor(new DefaultCellEditor(comboBox));
		valuesColumn.setHeaderValue("Header Value");
		
		jTable.setRowHeight(18);
		
		TableColumn headerNameColumn = jTable.getColumnModel().getColumn(0);
		JComboBox comboBox1 = new JComboBox(headerNames);
		comboBox1.setEditable(true);
		headerNameColumn.setCellEditor(new DefaultCellEditor(comboBox1));
		headerNameColumn.setHeaderValue("Header Name");
		
		
		outputTxt.setColumns(100);
		outputTxt.setAutoscrolls(true);
		outputTxt.setLineWrap(true);
		outputTxt.setWrapStyleWord(true);
		
		rawoutputTxt.setLineWrap(true);
		rawoutputTxt.setWrapStyleWord(true);
		
		//bodyTxt.setColumns(100);
		//bodyTxt.setAutoscrolls(true);
		
		methodCombo.setModel(new DefaultComboBoxModel(methodTypes));
		
		progressBar.setVisible(false);
		responsePanel.add(responseLbl);
		responsePanel.setPreferredSize(new Dimension(100,50));
		
		pane.add("Text",htmlResultPanel);
		htmlResultPanel.setLayout(new BorderLayout());
		htmlResultPanel.add(htmlScrollPane,BorderLayout.CENTER);
		
		htmlScrollPane.add(outputTxt);
		htmlScrollPane.setViewportView(outputTxt);
//		htmlScrollPane.add(editorPane);
//		htmlScrollPane.setViewportView(editorPane);
		bodycrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
		
		pane.add("Raw",rawResultPanel);
		
//		JPanel p=new JPanel();
//		p.add(new JLabel("Tab "));
//		p.add(new JButton("Close"));
//		
		
//		pane.add(new ButtonTabComponent(pane));
//		pane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		
		editorPane.setContentType("text/xml");
		
		rawResultPanel.setLayout(new BorderLayout());
		rawResultPanel.add(scrollPane,BorderLayout.CENTER);	
		
		JMenu viewMenu=new JMenu("View");
		JMenu fileMenu=new JMenu("File");

		JMenuItem viewMenuItem=new JMenuItem("Console");
		
		jMenuBar.add(fileMenu);
		jMenuBar.add(viewMenu);
		viewMenu.add(viewMenuItem);
		
		JMenuItem newMenuItem=new JMenuItem("New...");
		JMenuItem openMenuItem=new JMenuItem("Open...");
		JMenuItem saveMenuItem=new JMenuItem("Save");
		JMenuItem quitMenuItem=new JMenuItem("Quit");
		JSeparator separatorMenuItem=new JSeparator();
		
		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(separatorMenuItem);
		fileMenu.add(quitMenuItem);
		
		quitMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		viewMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//System.out.println("view menu clicked....");
				final Popup  popup=new Popup();
				Thread t=new Thread(){
					public void run() {
						while(true){
							
							try {
								//outputStream.flush();
								if(outputStream.size()>size){
									popup.appendText(new String(outputStream.toByteArray()));
									size=outputStream.size();
								}
								if(size>10000){
									outputStream.flush();
									size=0;
								}
								Thread.sleep(2000);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					};
				};
				t.start();
			}
		});
		
		KeyStroke saveKeyStroke=KeyStroke.getKeyStroke('S',KeyEvent.CTRL_DOWN_MASK);
		KeyStroke openKeyStroke=KeyStroke.getKeyStroke('O',KeyEvent.CTRL_MASK);
		KeyStroke newKeyStroke=KeyStroke.getKeyStroke('N',KeyEvent.CTRL_MASK);
		KeyStroke quitKeyStroke=KeyStroke.getKeyStroke('Q',KeyEvent.CTRL_MASK);
		saveMenuItem.setAccelerator(saveKeyStroke);
		openMenuItem.setAccelerator(openKeyStroke);
		newMenuItem.setAccelerator(newKeyStroke);
		quitMenuItem.setAccelerator(quitKeyStroke);
		
		saveMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(isNew){
					JFileChooser fileChooser=new JFileChooser();
					fileChooser.showSaveDialog(null);
					File file=fileChooser.getSelectedFile();
					if(file!=null){
						fileName=file.getAbsolutePath();
					}
					System.out.println("select file for saving...."+fileName);
				}
				if(!"".equals(fileName)){
					saveToFile();
				}	
			}
		});
		
		newMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Main main=new Main();
				main.isNew=true;	
			}
		});
		
		openMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				openFile();
			}
		});
		
	
		this.setJMenuBar(jMenuBar);
		
		button.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					//sendData();
					responseLbl.setText("");
					dataResponse="";
					Task1 t=new Task1();
					//t.doInBackground();
					t.execute();
					
					
				} catch (Exception e1) {
					e1.printStackTrace();
					responseLbl.setText(e1.getMessage());
					System.out.println(e1.getCause());
				}
			}
		});
		
		this.setSize(600, 500);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.pack();
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		try {
////		      PlasticLookAndFeel laf = new Plastic3DLookAndFeel();
////		      PlasticLookAndFeel.setCurrentTheme(new ExperienceBlue());
////		      UIManager.setLookAndFeel(laf);
//		    } catch (Exception ex) {
//		      ex.printStackTrace();
//		    }

		//if (System.getProperty("os.name").contains("Mac")) {
		
		System.setOut(new PrintStream(outputStream));
		System.setErr(new PrintStream(outputStream));
		
		  //System.setProperty("apple.laf.useScreenMenuBar", "true");
		  //}
		    java.awt.EventQueue.invokeLater(new Runnable() {
		      @Override
		      public void run() {
		    	  Main main=new Main();
					
		      }
		    });
			

	}
	
	
	public void sendData() throws Exception{
		
		System.out.println("preparing request to send.....");
		URL url=new URL(urlTxt.getText());
		URLConnection urlConnection=(URLConnection)url.openConnection();
		
		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		
		urlConnection.setConnectTimeout(10000);
		urlConnection.setReadTimeout(60000);
		
		outputTxt.setText("");
		rawoutputTxt.setText("");
		
		Map<String,String> map=getHeaders(urlConnection);
		
//		Iterator<String> it=map.keySet().iterator();
//		
//		while (it.hasNext()) {
//			String key = (String) it.next();
//			
//		}
		
		System.out.println("Request Method="+methodCombo.getSelectedItem().toString());
//		urlConnection.setRequestMethod(methodCombo.getSelectedItem().toString());
		
		OutputStream out= urlConnection.getOutputStream();
		String bodyStr=bodyTxt.getText();
		
		out.write(bodyStr.getBytes());
		
		InputStream is=urlConnection.getInputStream();
		//InputStream erroris=urlConnection.getErrorStream();
		
		Map<String,List<String>> responseHeaders=urlConnection.getHeaderFields();
		
		System.out.println("responseHeaders::"+responseHeaders);
		
		BufferedReader br=new BufferedReader(new InputStreamReader(is));
		
		String output="";
		StringBuilder builder=new StringBuilder();
		StringBuilder builderError=new StringBuilder();
		
		while((output=br.readLine())!=null){
			builder.append(output);
			builder.append("\n");
		}
		
		//br.close();
		out.close();
		
		is.close();
		
//		if(erroris!=null){
//		br=new BufferedReader(new InputStreamReader(erroris));
//		
//		while((output=br.readLine())!=null){
//			builderError.append(output);
//			builderError.append("\n");
//		}
//		}
		
		if(!builder.toString().equals("")){
			outputTxt.setText(builder.toString());
			
		}else{
			
			outputTxt.setText(builderError.toString());
		}
		outputTxt.setColumns(100);
		
		
		if(responseHeaders!=null){
			Iterator<String> it=responseHeaders.keySet().iterator();
//			
			while (it.hasNext()) {
				String key = (String) it.next();
				rawoutputTxt.append((key==null?"":key)+" : " +responseHeaders.get(key)+"\n");
			}
			
			rawoutputTxt.append("\n");
			rawoutputTxt.append(outputTxt.getText());
			
		}
		
		//urlConnection.disconnect();
	}
	
	private Map<String,String> getHeaders(URLConnection urlConnection){
		Map<String, String> map=new HashMap<String, String>();
		
		int rowCount=jTable.getRowCount();
		for(int i=0;i<rowCount;i++){
			String header=(String)jTable.getModel().getValueAt(i, 0);
			if(header!=null && !"".equals(header)){
				
				map.put(header,jTable.getModel().getValueAt(i, 1).toString());
				if(urlConnection!=null){
					urlConnection.addRequestProperty(header,jTable.getModel().getValueAt(i, 1).toString());
				}
			}
		}
		
		System.out.println("Map::"+map);
		return map;
	}
	
	
	private void saveToFile(){
		try{
			SendFile sendFile=new SendFile();
			sendFile.setUrl(this.urlTxt.getText());
			sendFile.setMethod(this.methodCombo.getSelectedItem().toString());
			sendFile.setBody(this.bodyTxt.getText());
			sendFile.setHeaderMap(getHeaders(null));
			
			sendFile.saveToFile(fileName);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private void openFile(){
		BufferedReader fileReader=null;
		try {
		JFileChooser fileChooser=new JFileChooser();
		FileFilter fileFilter=new FileNameExtensionFilter("files", "sfxml");
		fileChooser.setFileFilter(fileFilter);
		fileChooser.showOpenDialog(null);
		File file=fileChooser.getSelectedFile();
		if(file!=null){
		System.out.println("Choosen file is::"+file.getAbsolutePath());
		StringBuilder builder=new StringBuilder();
//			fileReader = new BufferedReader(new FileReader(file));
//			String str="";
//			while((str=fileReader.readLine())!=null){
//				builder.append(str);
//			}
			this.dispose();
			Main newWindow=new Main();
			
			ObjectInputStream in=new ObjectInputStream(new FileInputStream(file));
			
			SendFile sendFile=(SendFile)in.readObject();
			
//			newWindow.urlTxt.setText(builder.substring(builder.indexOf("<PATH>")+6, builder.indexOf("</PATH>")));
//			newWindow.bodyTxt.setText(builder.substring(builder.indexOf("<BODY>")+6, builder.indexOf("</BODY>")));
//			newWindow.methodCombo.setSelectedItem(builder.substring(builder.indexOf("<METHOD>")+8, builder.indexOf("</METHOD>")));

			newWindow.urlTxt.setText(sendFile.getUrl());
			newWindow.bodyTxt.setText(sendFile.getBody());
			newWindow.methodCombo.setSelectedItem(sendFile.getMethod());
			
			newWindow.isNew=false;
			newWindow.fileName=file.getAbsolutePath();
			newWindow.setTitle(file.getName());
			
			Map<String, String> headerMap=sendFile.getHeaderMap();
			
			System.out.println("Header map got from file....."+headerMap);
			Set<String> keySet=headerMap.keySet();
			
			int i=0;
			for (String key : keySet) {
				newWindow.jTable.getModel().setValueAt(key,i, 0);
				newWindow.jTable.getModel().setValueAt(headerMap.get(key),i,1);
				i++;
			}
			
			in.close();
		}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally{
			try {
				if(fileReader!=null)
				fileReader.close();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}
	}
	

	class Task1 extends SwingWorker<String, Object>{
		
		@Override
		protected String doInBackground() throws Exception {
			System.out.println("Job started....");
			try{
			progressBar.setIndeterminate(true);
			progressBar.setVisible(true);
			sendData();
			}catch(Exception e){
				dataResponse=e.getMessage()+" "+e.getClass();
				e.printStackTrace();
				System.out.println("Exception::::"+e.getMessage());
				throw e;
			}
			return dataResponse;
		}
		
		@Override
		protected void done() {
			progressBar.setVisible(false);
			if(!"".equals(dataResponse)){
				System.out.println("Exception from action performed....");
				//responseLbl.setText(dataResponse);
				JTextArea l=new JTextArea(dataResponse);
				l.setEditable(false);
				l.setLineWrap(true);
				l.setColumns(30);
				l.setOpaque(false);
				JOptionPane.showMessageDialog(null, l);
				repaint();
				
			}
			System.out.println("Job completed....");
			
		}
		
	}
}

class Popup extends JDialog{
	
	JEditorPane editor=new JEditorPane();
	JScrollPane pane=new JScrollPane();
	
	public Popup(){
		this.getContentPane().add(pane);
		pane.add(editor);
		pane.setViewportView(editor);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setSize(500,400);
		this.setTitle("Console");
	}
	
	public void appendText(String t){
		
		String text=this.editor.getText();
		this.editor.setText(text.concat(t));
		this.editor.repaint();
	}
}
