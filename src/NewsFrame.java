import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



/**
 * @author Mulalo Ralinala
 */
public class NewsFrame extends JFrame

{
	private static final long serialVersionUID = 1L;


	private JButton Search;
	private JList<String> news;
	private JTextField word;
	private JLabel input;
	private JLabel ReadNews;
	private JLabel lblNews;
	private JLabel Photo;
	private JPanel myCent;
	private JPanel myNorth;
	private DefaultListModel<String> newslist;
	private JTextArea myNews;
	public String SearchKeyword;
	private 	ArrayList<NewsHeader> mulalo = null;

public String Format(String Token)
{
	String Formatted = "";
	if(Token.contains(" "))
	{
	  StringTokenizer e = new StringTokenizer(Token," ");
	  while(e.hasMoreTokens())
	  {
		  Formatted += e.nextToken() ;
				  if(e.hasMoreTokens())
					  Formatted +=   "+";
	  }
	}else
	{
		return Token;
	}
	
	
	
	return Formatted;
}
	
public NewsFrame()
{
	initialize();
}

	
	private void initialize()
	{
		
		
		myNorth=new JPanel();
		input=new JLabel("Input");
		word=new JTextField(30);
		
		Search=new JButton("Search");
		
		/**
		 * button for searching news
		 */
		Search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("clicked me");
				SearchKeyword = word.getText();
				newslist.clear();
				try {
					
					mulalo = NewsClient.ConnectClient(Format(SearchKeyword));
					//System.out.println(Format("HI MULALO ARE YOU OK?"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for(NewsHeader a : mulalo)
				{
					newslist.addElement(a.title);	
				}
			 repaint();
			}
			
		});
		myNorth.add(input);
		myNorth.add(word);
		myNorth.add(Search);
		
		add(myNorth,BorderLayout.NORTH);
	
	
	
        newslist = new DefaultListModel<>();
        myCent=new JPanel();
		lblNews= new JLabel("Latest News");
	
	
	     	
		 
		
		news = new JList<String>(newslist);
		JScrollPane scrollPane = new JScrollPane();
	   
		
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
        
		 news.setPreferredSize(new Dimension(500, 8000));
	     news.setFixedCellHeight(40);
		 scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	     scrollPane.add(news);
       
		
		 
 
		
		myCent.add(lblNews);
		
		myCent.add(news);
		myCent.add(new JScrollPane(news));
		//myCent.add(scrollPane);
		
		add(myCent,BorderLayout.CENTER);
	
		
		Search=new JButton("Submit");
	
		JTextArea NewsArea= new JTextArea(10,40);
		ReadNews=new JLabel("Read News");

		
		 
		NewsArea.setEditable(false);
		//NewsArea.setPreferredSize(new Dimension(527,300));
		
		NewsArea.setFont(new Font("Arial",Font.ITALIC,25));
		Photo=new JLabel("");
		myCent.add(Photo);
		Photo.setPreferredSize(new Dimension(300,300));
		Photo.setBackground(new Color(245,245,255));
		
		
		myCent.add(NewsArea);
		myCent.add(ReadNews);
		
	
	
		

	/**
	 * display news in the jlist
	 */
	
		news.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting())
				{
				
					String answer = newslist.getElementAt(news.getSelectedIndex());
					 NewsHeader newH =  null;
					 
					for(NewsHeader  d : mulalo)
					{
						if(d.title.equals(answer))
						{
							newH =d;
							break;
						}
					}
				   if(newH != null)
				   { 
					   NewsArea.setLineWrap(true);
					   NewsArea.setText(newH.author +"\n\n\n"+newH.description);
					  
					   if(!newH.Imageurl.equals(""))
					   {
						   try {
							Image image = ImageIO.read(new URL(newH.Imageurl));
							
							  Photo.setIcon( new ImageIcon(image));
							   System.out.println(newH.Imageurl);
							  
						} catch (MalformedURLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						 
					   }
					   repaint();
				   }
					
				}	
				
				
			}

			
		});
	}
}


