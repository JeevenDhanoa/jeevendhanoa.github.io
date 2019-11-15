//GraphBar
//Description: Displays 4 bars of K/T/C/A marks, with variable width/height depending on weight/mark
//Programmed By: Jeeven Dhanoa
//Last Modified: 1/17/2019

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class GraphBar extends JComponent
{
     //Define attributes
     private double [] weightsAndMarks; //The array of weights and marks
     
     //The constructor
     public GraphBar(double [] someWeightsAndMarks)
     {
          //Call superclass constructor
          super();
          
          //Set preferred graph size
          Dimension prefSize = new Dimension(550, 550);
          this.setPreferredSize(prefSize);
          
          //Set values of attributes
          this.weightsAndMarks = someWeightsAndMarks;
          
     } //end constructor 
     
     
     //Draw the actual component 
     public void paintComponent(Graphics g)
     {
          //Variable declaration
          Graphics2D g2; //To be used for dynamically drawing the graph components
          //int widthDiff; //Used for determining how far apart each data point should be 
          int distBetweenBars; //Used to determine how far apart the bars will be
          int [] barWidths = new int [4]; //Contains information about how wide each bar will be
          int remainingWidth; //Used to determine how wide each bar should be on the screen
          double totalWeight = 0; //All the weights added together
          int cummulativeDist; //Used for keeping track of x distance while drawing
          String label = ""; //The K/T/C/A x-axis label
          
          //Set the vale of distBetweenBars
          distBetweenBars = 20;
          
          //Add up all the weights (First four values in weightsAndMarks)
          for (int i = 0; i < 4; i++)
          {
               totalWeight = totalWeight + weightsAndMarks[i];
          } //end for
          
          //Determine how wide each bar will be 
          remainingWidth = 500 - (5 * distBetweenBars);
          
          for (int i = 0; i < 4; i++)
          {
               barWidths[i] = (int)Math.round(weightsAndMarks[i] / totalWeight * remainingWidth);
          } //end for
          
          //Instantiate g2, which will be used to draw the component in 2D space
          super.paintComponent(g);
          g2 = (Graphics2D) g;
          g2.scale (this.getWidth()/ (550), this.getHeight()/550);
          g2.setStroke (new BasicStroke (5.0F / this.getWidth()));
          
          //Set the value of widthDiff according to the number of courses
          //widthDiff = 500/(this.numMarks + 1);
          
          //Draw and label the axes
          g2.drawLine(50, 0, 50, 500);
          g2.drawLine(50, 500, 550, 500);
          
          //Apply an AffineTransform object to g2 in order to rotate the y-axis label
          AffineTransform orig = g2.getTransform();
          AffineTransform at = new AffineTransform();
          at.setToRotation(-Math.PI / 2.0);
          g2.setTransform(at);
          g2.drawString("Percent Mark (%)", -300, 20);
          g2.setTransform(orig);
          
          //Draw the x-axis label
          g2.drawString("Assessment Category", 225, 540);
          
          //Set stroke width to be lower
          g2.setStroke (new BasicStroke (1.0F / this.getWidth()));
          
          
          //Draw vertical axis lines and number labels
          for (int i = 0; i < 20; i++)
          {
               g2.setColor(Color.lightGray);
               g2.drawLine(51, 20 + (24 * i), 550, 20 + (24 * i));
               g2.setColor(Color.black);
               g2.drawLine(45, 20 + (24 * i), 55, 20 + (24 * i));
               g2.drawString("" + (100 - 5 * i), 23, 25 + (24 * i));
          } //end for
          
          //Draw remaining labels/lines at the origin
          g2.drawLine(45, 500, 50, 500);
          g2.drawString("0", 23, 505);
          
          //Draw the actual bars
          cummulativeDist = 50 + distBetweenBars;
          for (int i = 4; i < 8; i++)
          {
               switch(i)
               {
                    case 4:
                         g2.setColor(Color.red);
                         label = "K: ";
                         break;
                    case 5:
                         g2.setColor(Color.green);
                         label = "T: ";
                         break;
                    case 6:
                         g2.setColor(Color.blue);
                         label = "C: ";
                         break;
                    case 7:
                         g2.setColor(Color.yellow);
                         label = "A: ";
                         break;
               } //end switch case
               
               //Add weighting to the label
               label = label +  (double)Math.round(1000 * (weightsAndMarks[i - 4] / totalWeight)) /10  + "%";
               
               //Draw the rectangle and label, increment cummulative distance
               g2.fillRect(cummulativeDist, 500 - (int)(4.8 * this.weightsAndMarks[i]), 
                           barWidths[i - 4], (int)(4.8 * this.weightsAndMarks[i]));
               g2.setColor(Color.black);
               g2.drawRect(cummulativeDist, 500 - (int)(4.8 * this.weightsAndMarks[i]), 
                           barWidths[i - 4], (int)(4.8 * this.weightsAndMarks[i]));
               g2.drawString(label, cummulativeDist + (barWidths[i-4] - 50)/2,  520);
               cummulativeDist = cummulativeDist + distBetweenBars + barWidths[i - 4];
               
          } //end for
          
          
     } //end paintComponent
} //end GraphBar