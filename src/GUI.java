import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
		
	//Actual window resolution 
	int aW = 1280;
	int aH = 800;
	
	//game phases booleans
	boolean bool_hit_stay = true;
	boolean bool_dealer_turn = false;
	boolean bool_play_more = false;
	
	//Color
	Color colorBackground = new Color(39,119,20);
	Color colorButton = new Color(204,204,0);
	
	//Font
	Font fontButton = new Font("Times New Roman", Font.PLAIN,30);
	Font fontCard = new Font("Times New Roman", Font.BOLD,40);
	Font fontQuestions = new Font("Times New Roman", Font.PLAIN,40);
	
	//Questions?
	String play_more_q = "Play More?";
	
	//Button Display
	JButton bHit = new JButton();
	JButton bStay = new JButton();
	JButton bYes = new JButton();
	JButton bNo = new JButton();
	
	//Card grid positioning and dimensions.
	int gridX = 50;
	int gridY = 50;
	int gridW = 900;
	int gridH = 400;
	
	//Card dimensions and spacing
	int cardSpacing = 10;
	int cardEdgeSofting = 7;
	int cardTW = gridW/6;
	int cardTH = gridH/2;
	int cardAW = cardTW-2*cardSpacing;
	int cardAH = cardTH-2*cardSpacing;
	
	//totals and hit-stay grid positioning and dimensions
	int hsX = gridX + gridW + 50;
	int hsY = gridY;
	int hsW = 230;
	int hsH = 400;
	
	//Play more question grid positioning and dimesions
	int pmX = hsX;
	int pmY = hsY + hsH + 50;
	int pmW = hsW;
	int pmH = 200;
	
	//Arraylist taht contains all the cards
	ArrayList<Card> allCards = new ArrayList<Card>();
	ArrayList<Card> playerCards = new ArrayList<Card>();
	ArrayList<Card> dealerCards = new ArrayList<Card>();
	
	//integer used to generate random numbers for the cards to be given
	int rand = new Random().nextInt(52);
			
	public GUI() {
		this.setSize(aW+6, aH+29);
		this.setTitle("BlackJack");
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		Board board = new Board();
		this.setContentPane(board);
		this.setLayout(null);
		
	
	//Button Click Action
		ActHit aHit = new ActHit();
		bHit.addActionListener(aHit);
		bHit.setBounds(hsX+55, hsY+40, 120, 80);
		bHit.setBackground(colorButton);
		bHit.setFont(fontButton);
		bHit.setText("HIT");
		board.add(bHit);
		
		ActStay aStay = new ActStay();
		bStay.addActionListener(aStay);
		bStay.setBounds(hsX+55, hsY+280, 120, 80);
		bStay.setBackground(colorButton);
		bStay.setFont(fontButton);
		bStay.setText("STAY");
		board.add(bStay);
		
		ActYes aYes = new ActYes();
		bYes.addActionListener(aYes);
		bYes.setBounds(pmX+10, pmY+110, 100, 80);
		bYes.setBackground(colorButton);
		bYes.setFont(fontButton);
		bYes.setText("YES");
		board.add(bYes);
		
		ActNo aNo = new ActNo();
		bNo.addActionListener(aNo);
		bNo.setBounds(pmX+120, pmY+110, 100, 80);
		bNo.setBackground(colorButton);
		bNo.setFont(fontButton);
		bNo.setText("NO");
		board.add(bNo);
		
		String shapeS1 = null;
		int id_setter = 0;
		for (int s = 0; s < 4; s++) {
			if (s == 0) {
					shapeS1 = "Spades";
				} else if (s == 1) {
					shapeS1 = "Hearts";
				} else if (s == 2) {
					shapeS1 = "Diamonds";
				} else {
					shapeS1 = "Clubs";
				}
				for (int i = 2; i < 15; i++) {
					allCards.add(new Card(i, shapeS1, id_setter));
					id_setter++;
			}
		}
		
		rand = new Random().nextInt(52);
		playerCards.add(allCards.get(rand));
		allCards.get(rand).cardUsed = true;
		
		rand = new Random().nextInt(52);
		while(true) {
			if(allCards.get(rand).cardUsed == false) {
				dealerCards.add(allCards.get(rand));
				allCards.get(rand).cardUsed = true;
				break;
			} else {
				rand = new Random().nextInt(52);
			}
		}
	
		rand = new Random().nextInt(52);
		while(true) {
			if(allCards.get(rand).cardUsed == false) {
				playerCards.add(allCards.get(rand));
				allCards.get(rand).cardUsed = true;
				break;
			} else {
				rand = new Random().nextInt(52);
			}
		}
		rand = new Random().nextInt(52);
		while(true) {
			if(allCards.get(rand).cardUsed == false) {
				dealerCards.add(allCards.get(rand));
				allCards.get(rand).cardUsed = true;
				break;
			} else {
				rand = new Random().nextInt(52);
			}
		}
		
		for (Card c : playerCards) {
			System.out.println("Player has the card " + c.name + " of " + c.shape);
		}
		for (Card c : dealerCards) {
			System.out.println("Dealer has the card " + c.name + " of " + c.shape);
		}
		
	}
	
	public void refresher() {
		if (bool_hit_stay == true) {
			bHit.setVisible(true);
			bStay.setVisible(true);
			bYes.setVisible(false);
			bNo.setVisible(false);
		} else if (bool_dealer_turn == true) {
			bHit.setVisible(false);
			bStay.setVisible(false);
			bYes.setVisible(false);
			bNo.setVisible(false);
		} else if (bool_play_more == true) {
			bHit.setVisible(false);
			bStay.setVisible(false);
			bYes.setVisible(true);
			bNo.setVisible(true);
		}
	}
	
	public class Board extends JPanel {
		
		public void paintComponent(Graphics g) {
			g.setColor(colorBackground);
			g.fillRect(0, 0, aW, aH);
			
			//Temporary grid painting
			g.setColor(Color.black);
			g.drawRect(gridX, gridY, gridW, gridH);
			//Temporary log borders painting
			g.drawRect(gridX, gridY+gridH+50, gridW, 500);
			//Temporary totals and hit-stay message and buttons grid
			g.drawRect(hsX, hsY, hsW, hsH);
			//Temporary Play more grid
			g.drawRect(pmX, pmY, pmW, pmH);
			if(bool_play_more == true) {
				g.setFont(fontQuestions);
				g.drawString(play_more_q, pmX+25, pmY+60);	
			}
			
			for(int i = 0; i < 6; i++) {
				g.drawRect(gridX + i*cardTW + cardSpacing, gridY + cardSpacing, cardAW, cardAH);
				g.drawRect(gridX + i*cardTW + cardSpacing, gridY + cardSpacing + cardTH, cardAW, cardAH);
			}
			
			//card painting
			int index = 0;
			for (Card c: playerCards) {
				g.setColor(Color.white);
				g.fillRect(gridX + index*cardTW + cardSpacing, gridY + cardSpacing+cardEdgeSofting, cardAW, cardAH-2*cardEdgeSofting);
				g.fillRect(gridX + index*cardTW + cardSpacing + cardEdgeSofting, gridY + cardSpacing, cardAW-2*cardEdgeSofting, cardAH);
				g.fillOval(gridX + index*cardTW + cardSpacing, gridY + cardSpacing, 2*cardEdgeSofting, 2*cardEdgeSofting);
				g.fillOval(gridX + index*cardTW + cardSpacing + cardAW-2*cardEdgeSofting, gridY + cardSpacing, 2*cardEdgeSofting, 2*cardEdgeSofting);
				g.fillOval(gridX + index*cardTW + cardSpacing, gridY + cardSpacing+cardAH-2*cardEdgeSofting, 2*cardEdgeSofting,2*cardEdgeSofting);
				g.fillOval(gridX + index*cardTW + cardSpacing + cardAW-2*cardEdgeSofting, gridY + cardSpacing+cardAH-2*cardEdgeSofting, 2*cardEdgeSofting, 2*cardEdgeSofting);
				g.setColor(Color.black);
				if (c.shape.equalsIgnoreCase("Hearts") || c.shape.equalsIgnoreCase("Diamonds")) {
					g.setColor(Color.red);
				}
				g.setFont(fontCard);
				g.drawString(c.symbol, gridX + index*cardTW + cardSpacing*2, gridY+cardAH);
								
				if (c.shape.equalsIgnoreCase("Spades")) {
					g.setColor(Color.black);	
					g.fillOval(gridX+index*cardTW+40, gridY+85, 40, 40);
					g.fillOval(gridX+index*cardTW+70, gridY+85, 40, 40);
					g.fillArc(gridX+index*cardTW+30, gridY+28, 90, 70, 230, 80);
					g.fillRect(gridX+index*cardTW+70, gridY+90, 10, 50);
				
				} else if (c.shape.equalsIgnoreCase("Clubs")) {	
					g.setColor(Color.black);
					g.fillOval(gridX+index*cardTW+35, gridY+85, 40, 40);
					g.fillOval(gridX+index*cardTW+75, gridY+85, 40, 40);
					g.fillOval(gridX+index*cardTW+55, gridY+55, 40, 40);
					g.fillRect(gridX+index*cardTW+70, gridY+90, 10, 50);		
								
				} else if (c.shape.equalsIgnoreCase("Hearts"))	{
					g.setColor(Color.red);
					g.fillOval(gridX+index*cardTW+40, gridY+70, 40, 40);
					g.fillOval(gridX+index*cardTW+70, gridY+70, 40, 40);
					g.fillArc(gridX+index*cardTW+30, gridY+96, 90, 70, 50, 80);
					
				}  else if (c.shape.equalsIgnoreCase("Diamonds")) {
					g.setColor(Color.red);
					int x1,x2,x3,x4,y1,y2,y3,y4;
					x1 = 75 + gridX + index*cardTW;
					y1 = 70 + gridY;
					x2 = 50 + gridX + index*cardTW;
					y2 = 100 + gridY; 
					x3 = 75 + gridX + index*cardTW;
					y3 = 130 + gridY;
					x4 = 100 + gridX + index*cardTW;
					y4 = 100 + gridY;
					int[] xPoly = {x1, x2, x3, x4};
					int[] yPoly = {y1, y2, y3, y4};
					g.fillPolygon(xPoly, yPoly, 4);
				}
				index++;
			}
		}
	
	}
	
	public class ActHit implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("HIT 하셨습니다!");
		}
	}
	public class ActStay implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("STAY 하셨습니다!");
		}
	}
	public class ActYes implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("YES 하셨습니다!");
		}
	}
	public class ActNo implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("NO 하셨습니다!!");
		}
		
	}
}
	
	
	
	
