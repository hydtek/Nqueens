package NQueens;
/*************************************************************************%
 % The class HeuristicSearch built to find the solution to the n-queen    %
 % problem using a stochastic hill climbing. Every column is assign a     %
 % heuristic function which represents how many queens you can attack,    %
 % i create an array of all the best moves with lowest heuristic          %
 % function in an array, and randomize which value i will use on that run.%
 % my random seed can vary because my size of the array can vary.         %
 % the board is look at by column so what ever changes affected by a move %
 % will result in a change in heuristic for adjacent columns              %         
 **************************************************************************/

import java.awt.*; 
import javax.swing.*;
import java.io.*; 

public class HeuristicSearch extends JFrame 
{
    JLayeredPane layeredPane;     //Pane for chess board
    JPanel chessInterface;        //board interface
    int nQueens = 0;              //variable represent dimension/# queens
    char[][] outcome;             //char array to represent board
    private int StateCounter = 0; //counter for how many boards are created
    private int RandomCounter = 0; //counts the different random seeds i use
   
    public HeuristicSearch(){
     
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
       HillClimbing();
        
    }
    //this method randomizes a board to start the hill climbing 
    private char[][] randomBoard(char[][] c){
    
        //each column randomizes a queen on a row
         for(int i = 0; i < nQueens; i++){
             c[(int)(Math.random()*nQueens)][i] ='*';
             
            }
         return c;
   
    }
    //this method recurses intill the board is finish using random best values
    private void HillClimbing(){
       
       int startRow = 0; //default row starting pos
       int startCol = 0; //default column starting pos
       
       outcome = new char[nQueens][nQueens]; 
       outcome = randomBoard(outcome); //creates random board
        
       //recurses till board is done
       while(boardDone(outcome) == false){
         //loop to go through each column and change the board
         for(int i = 0; i < nQueens; i++){
            //best move is a method which randoms best choices to continue with
             outcome = bestMove(outcome, i); 
             StateCounter = StateCounter+1;
              if(boardDone(outcome) == true)
                 break;//board is finished
             }
        }
        
         BuildBoard(outcome);//build Board with completed board
         
       }
    
    //test is the board has any attacking queens
    private boolean boardDone(char[][] c){
        int done = 0;
      
        //loops till finds queen and then checks if valid 
        for(int i = 0; i < nQueens; i++)
            for(int j = 0; j < nQueens; j++){
                if(c[i][j] == '*'){                                    
                  done = setHeurCost(c, i,j);  // see if on path with queens
                  if(done > 0)
                      return false;
                 }
            }     
         return true;
        }
    
    /*this method uses a heuristic cost of attacking queens to
      to discover best route of choices within the column, if multiple
     rows have the lowest heuristic cost then an array is created
     and a random value is picked from the array and continued with*/
    private char[][] bestMove(char[][] out, int startCol){
        int[] temp  = new int[nQueens];  //array to hold lowest h functions
        int temp2   = 0;   //value to hold lowest h function and compare to rest
        int prevRow = 0;   //the old row on that column before change
        int counter = 0;   //counter to know length of BestRow array
        int BestRow[];     //array used to place best rows and random between them
        
        //sets the heuristic cost to each row in a specific column
        for(int i = 0; i<nQueens;i++)
            temp[i] = setHeurCost(out, i, startCol);
         
    /*adds 1 to heur cost for current queen in column(reason is every row is the
        column is attacked by previous queen, giving everyone else 
        one extra queen attack on there heur cost.*/
         for(int j = 0; j<nQueens;j++){
             if(out[j][startCol] == '*'){
                 temp[j] = temp[j]+ 1;
                 prevRow = j;
             }
         }
        temp2 = temp[0]; //temp2 equals first heuristic cost
        //array to find the number of lowest heuristic cost exist
        for(int k = 1; k<nQueens;k++){
            if(temp2 >= temp[k]){
                if(temp2>temp[k]){
                  temp2 = temp[k];
                  counter = 0;
                }
                else if(temp2 == temp[k])
                  counter = counter + 1;
            }
          }   
        //array to place the lowest heuristic cost to be randomized
        BestRow = new int[counter+1];
        int count = 0; //new counter for pos of adding to BestRow array
        for(int s = 0; s<nQueens;s++){
            if(temp[s] == temp2){ //compare lowest value to each value in col
              BestRow[count] = s;
              count++;
            }
        }
        /*randomizes a value in the array to add for best choice, random seed
         is the size of the length of array, so if 3 values are the same h cost
         then the randomizer will random between 0-2 and then change the old
         queen on that column to the new queen */
        
        int random = (int)(Math.random()*BestRow.length);           
        if(RandomCounter < random)
            RandomCounter = random;
        out[prevRow][startCol] = ' ';
        out[BestRow[random]][startCol] = '*';      
        
        return out;
        
    }
 
    
    /*this checks if the move is valid from all queen angles and adds 1 every
      time an attacking queen is found directly or indirectly, zero represents
     no attack queens and the move is valid*/
    private int setHeurCost(char[][] s, int row, int col){
        int tempRow = row;
        int tempCol = col;
        int score = 0;    
            //Row checker
            for(int j = 0; j < nQueens; j++)
                if(s[row][j] == '*' && j!= col){
                 score++;  
                }
            //column checker
            for(int i = 0; i < nQueens; i++)
                if(s[i][col] == '*' && i != row)
                    score++;
            
            //Checks upper left diagonal direction for invalids
            while(tempRow!= 0 && tempCol!=0){
                    
                tempCol--;
                tempRow--;
                if(s[tempRow][tempCol] == '*')
                    score++;
            }
             //resets temperary values
            tempCol = col;
            tempRow = row;
           
            //Checks bottom left diagonal direction for invalids
            while(tempRow!= (nQueens-1) && tempCol!=0){
                tempCol--;
                tempRow++;
                if(s[tempRow][tempCol] == '*')
                    score++;
            }
            
             //resets temperary values
            tempCol = col;
            tempRow = row;
            
            //Checks upper right diagonal direction for invalids
            while(tempRow!= 0 && tempCol!= (nQueens-1)){
                tempCol++;
                tempRow--;
                if(s[tempRow][tempCol] == '*')
                    score++;
            }
            
            //resets temperary values
            tempCol = col;
            tempRow = row;
            
            //Checks bottom right diagonal direction for invalids
            while(tempRow!= (nQueens-1) && tempCol!= (nQueens-1)){
                tempCol++;
                tempRow++;
                if(s[tempRow][tempCol] == '*')
                    score++;
            }
                
        return score;
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
        
        System.out.println("# of random Seeds bounced between: 0-" + RandomCounter);
        System.out.println("# of board configurations: " + StateCounter);
                    
    }
    public static void main(String[] args)
    {
        JFrame frame = new HeuristicSearch();
        frame.setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setResizable( false );
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
        
     }
}


