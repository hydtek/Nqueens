package NQueens;

import java.io.*;

public class OutcomeNode implements Serializable {
    private OutcomeNode[] next;
    private OutcomeNode parent;
    private int lastMove;
    public OutcomeNode() {

    }
    public OutcomeNode(int numQueens, OutcomeNode p) {
         next = new OutcomeNode[numQueens];
         parent = p;
         lastMove = 0;
    }
    
        public void addToChildren(OutcomeNode c){
          
        int i = 0;
       
        //checks for first available spot
        while(next[i] != null){
   
            i++;
            
            if( i > next.length)
                 break;
        }
         if( i < next.length){
           next[i] = c;
         }
    }
    
    public void setPos(int l){
        lastMove = l;
    }
    public int getPos(){
        return lastMove;
    }
    //gets children at specified index
    public OutcomeNode getChildren (int index){
        return next[index];
    }
     
    public OutcomeNode getParent(){
        return parent;
    }
    
  

}