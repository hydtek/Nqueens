package NQueens;
/*********************************************************************%
 % The class BlindSearch is built to find the solution to the n-queen %
 % problem using a depth first approach to the tree, this program can %
 % calculate any board of greater size 3*3 and will generate a real   %
 % board to demonstrate the results .                                 %
 **********************************************************************/
import java.awt.*;
import javax.swing.*;
import java.io.*; 
 
public class BlindSearch extends JFrame 
{
    JLayeredPane layeredPane;     //Pane for chess board
    JPanel chessInterface;        //board interface
    int nQueens = 0;              //variable represent dimension/# queens
    private OutcomeNode parent;   //node to build tree from
    char[][] outcome;             //char array to represent board
    int itemPos = 0;              //where queen pos was when added to array
    private int StateCounter = 0; //counts the number of boards configured
    
    public BlindSearch()
    {
     
       String input = "";
       DataInputStream in = new DataInputStream(System.in); 
       
       System.out.println("Enter the amount of Queens for the problem");    
       System.out.println();
       while(nQueens <4){
           try {						 			
               input = in.readLine();
           }
   	   catch (Exception e) {
                System.out.println("Invalid Input :");
            } 
           
        nQueens = Integer.parseInt(input); //size of board and #queens
        if(nQueens  < 4){
            System.out.println("No solution for this Nqueen problem plz enter 4+ queens");
            System.out.println();
        }
       }
        depth();  //call depth method
        
    }
    //depth first approach to solving nqueens problem
    private void depth(){
       
       int startRow = 0; //default row starting pos
       int startCol = 0; //default column starting pos
      
       outcome = new char[nQueens][nQueens]; 
       parent = new OutcomeNode(nQueens,null); 
       
       //starts loop to find solution
       while(startRow < (nQueens) && startRow >= 0 && startCol < nQueens){
           
           //finds next available outcome
           outcome = findNext(outcome,startRow, startCol); 
           
           //if outcome not -1 add it to tree
           if(itemPos != -1){
                
               parent.setPos(itemPos);
               parent = new OutcomeNode(nQueens, parent);
               StateCounter = StateCounter+1; //new board is configured
               startCol = 0;
               startRow++;
            }
           
     //if outcome is -1, then go back to parent and look for alternate outcome
            else if(itemPos == -1){
               
                parent = parent.getParent();
                startRow--;
                
                //previous parent was on last square go to next parent
                  if(parent.getPos()+1 == nQueens){
                     outcome[startRow][parent.getPos()] = ' ';
                     parent = parent.getParent();
                     startCol = parent.getPos()+1;
                     startRow--;
                     outcome[startRow][parent.getPos()] = ' ';
                   
                  }
                  else{
                     outcome[startRow][parent.getPos()] = ' ';
                     startCol = parent.getPos()+1;
                
                 }
            }
                
         }
       BuildBoard(outcome);
    }
    //looks for an available spot on the row
    private char[][] findNext(char[][] out, int sr,int sc ){
     
      //loop to check row for valid spot
      for(int i = sr; i < (sr+1);i++)
           for(int j = sc; j < nQueens; j++){
               if(validMove(out,i,j) == true){
                   out[i][j] = '*';
                   itemPos = j;
                   return out;
               }
                if((sc + 1) >= nQueens){
                    itemPos = -1;
                    return out;
                }            
           }
      itemPos = -1;
      return out;
    }
    
    //this checks if the move is valid from all queen angles
    private Boolean validMove(char[][] s, int row, int col){
        int tempRow = row;
        int tempCol = col;
            
            //Row checker
            for(int j = 0; j < nQueens; j++)
                if(s[row][j] == '*'){
                    return false;
                }
            //column checker
            for(int i = 0; i < nQueens; i++)
                if(s[i][col] == '*' )
                    return false;
            
            //Checks upper left diagonal direction for invalids
            while(tempRow!= 0 && tempCol!=0){
                    
                tempCol--;
                tempRow--;
                if(s[tempRow][tempCol] == '*')
                    return false;
            }
             //resets temperary values
            tempCol = col;
            tempRow = row;
           
            //Checks bottom left diagonal direction for invalids
            while(tempRow!= (nQueens-1) && tempCol!=0){
                tempCol--;
                tempRow++;
                if(s[tempRow][tempCol] == '*')
                    return false;
            }
            
             //resets temperary values
            tempCol = col;
            tempRow = row;
            
            //Checks upper right diagonal direction for invalids
            while(tempRow!= 0 && tempCol!= (nQueens-1)){
                tempCol++;
                tempRow--;
                if(s[tempRow][tempCol] == '*')
                    return false;
            }
            
            //resets temperary values
            tempCol = col;
            tempRow = row;
            
            //Checks bottom right diagonal direction for invalids
            while(tempRow!= (nQueens-1) && tempCol!= (nQueens-1)){
                tempCol++;
                tempRow++;
                if(s[tempRow][tempCol] == '*')
                    return false;
            }
                
        return true;
    }
        //this builds chess board
        public void BuildBoard(char[][] c){
            
         String input = "";
        DataInputStream in = new DataInputStream(System.in);
         //adds peices based on board
        System.out.println("Choose how you want to view board, enter choice and hit enter");
        System.out.println("For Graphical representation of board type: 1");
        System.out.println("For Ascii representation of board type: 2");
        System.out.println();
        while(!input.equals("1") && !input.equals("2")){
        try {						 			
               input = in.readLine();
           }
   	catch (Exception e) {
             System.out.println("Invalid Input :");
           }
        if(!input.equals("1") && !input.equals("2"))
            System.out.println("Invalid Choice");
            System.out.println();
        }
        if(input.equalsIgnoreCase("1")){
        //  Add a chess board to the Layered Pane
            
         System.out.println("Plz enter the path of 'queen.jpg' found in the folder of ");
         System.out.println("source files, use front slashes plz! example : ");
         System.out.println("C:/queen.jpg");
         System.out.println();
         Dimension size = new Dimension(75*nQueens, 75*nQueens);
          try {						 			
               input = in.readLine();
           }
   	catch (Exception e) {
             System.out.println("Invalid Input :");
           }
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize( size );     
           
        chessInterface = new JPanel();
        layeredPane.add(chessInterface, JLayeredPane.DEFAULT_LAYER);
        chessInterface.setLayout( new GridLayout(nQueens, nQueens) );
        chessInterface.setPreferredSize( size );
        chessInterface.setBounds(0, 0, size.width, size.height);
        int numSquare = nQueens * nQueens;
     
        int counter = 0;
        
        //creates chess interface
        for (int i = 0; i < numSquare; i++)
        {
            
            JPanel square = new JPanel( new BorderLayout() );
            chessInterface.add( square );
         
            int row = (i / nQueens) % 2;
            if(nQueens % 2 == 0){
                if (row == 0)
                   square.setBackground( i % 2 == 0 ? Color.BLACK: Color.BLUE );
                else
                   square.setBackground( i % 2 == 0 ? Color.blue : Color.BLACK );
           }   
             if(nQueens % 2 == 1){
             
                 if (row == 0)
                   square.setBackground( i % 2 == 0 ? Color.BLACK: Color.BLUE );
                else
                   square.setBackground( (i-1) % 2 == 0 ? Color.blue : Color.BLACK  );
             }
            
        }
        
        counter = 0;
        for(int i = 0; i<nQueens; i++)
            for(int j = 0; j < nQueens; j++){
                if(c[i][j] == '*'){
                  JLabel piece = new JLabel( new ImageIcon(input));
                  JPanel panel = (JPanel)chessInterface.getComponent(counter);
                  panel.add(piece);
                }
              counter++;
            }
        }
                   
        for(int i = 0; i<nQueens; i++){
            for(int j = 0; j < nQueens; j++){
                if(c[i][j] == '*'){
                    System.out.print(c[i][j]);
                }
                else
                  System.out.print("-");  
            }
              System.out.println();
            }
        
         
        System.out.println("# of board configurations: " + StateCounter);
        
    }
    public static void main(String[] args)
    {
        JFrame frame = new BlindSearch();
        frame.setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setResizable( false );
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
        
     }
}


