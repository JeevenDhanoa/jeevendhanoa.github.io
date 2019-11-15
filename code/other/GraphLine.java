//GraphLine
//Description: Displays a graph of marks from an ArrayList, shows useful statistical data alongside
//Programmed By: Jeeven Dhanoa
//Last Modified: 1/17/2019

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.*;

public class GraphLine extends JComponent
{
     //Define attributes
     private int numMarks; //The size of the ArrayList containing the marks
     private ArrayList <Double> marksToGraph; //To contain all of the marks that will be used
     private double slope; //The line of best fit's slope
     private double intercept; //The line of best fit's y-intercept
     private double nextMark; //The next predicted mark, as determined by the line of best fit
     
     //The constructor
     public GraphLine(ArrayList <Double> marks)
     {
          //Call superclass constructor
          super();
          
          //Set preferred graph size
          Dimension prefSize = new Dimension(550, 550);
          this.setPreferredSize(prefSize);
          
          //Set values of attributes
          this.marksToGraph = marks;
          this.numMarks = this.marksToGraph.size();
          
          //Determine the slope, intercept of the line of best fit
          performCalculations();
     } //end constructor 
     
     //Perform a least-squares regression to find the slope and intercept of the line of best fit,
     //And the predicted y value of the next data point
     public void performCalculations()
     {
          //Variable declaration
          int sumOfX = 0; //The sum of all x values, where x = the order of the data point in the ArrayList
          double sumOfY = 0; //The sum of all y values, where y = the mark value stored in the ArrayList
          double sumOfXY = 0; //The sum of x times y for all data points
          int sumOfXSquared = 0; //The sum of x times x for all data points
          
          //Use the arithmetic series formula 
          sumOfX = this.numMarks * (this.numMarks - 1) / 2;
          
          //Use the sum of squares formula
          sumOfXSquared = this.numMarks * (this.numMarks + 1) * (2 * this.numMarks + 1) / 6;
          
          //Add up all y values
          for (Double y: this.marksToGraph)
          {
               sumOfY = sumOfY + y.doubleValue();
          } //end for each
          
          //Add up x times y for each data point
          for (int i = 0; i < this.numMarks; i++)
          {
               sumOfXY = sumOfXY + (this.marksToGraph.get(i).doubleValue() * i);
          } //end for each
          
          //Find the slope of the line 
          this.slope = (this.numMarks * sumOfXY - sumOfX * sumOfY) / 
               (this.numMarks * sumOfXSquared - Math.pow(sumOfX, 2)); 
          
          //Find the y-intercept of the line
          this.intercept = (sumOfY - this.slope * sumOfX) / this.numMarks; 
          
          //Find the next data point
          this.nextMark = this.slope * (this.numMarks) + this.intercept;  
          
     } //end performCalculations
     
     //Draw the actual component 
     public void paintComponent(Graphics g)
     {
          //Variable declaration
          Graphics2D g2; //To be used for dynamically drawing the graph components
          int widthDiff; //Used for determining how far apart each data point should be 
          
          
          //Instantiate g2, which will be used to draw the component in 2D space
          super.paintComponent(g);
          g2 = (Graphics2D) g;
          g2.scale (this.getWidth()/ (550), this.getHeight()/550);
          g2.setStroke (new BasicStroke (5.0F / this.getWidth()));
          
          //Set the value of widthDiff according to the number of courses
          widthDiff = 500/(this.numMarks + 1);
          
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
          g2.drawString("Assessment Number", 225, 540);
          
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
          
          //Draw horizontal axis lines and number labels
          for (int i = 1; i <= this.numMarks; i++)
          {
               g2.setColor(Color.lightGray);
               g2.drawLine(50 + widthDiff * i, 0, 50 + widthDiff * i, 499);
               g2.setColor(Color.black);
               g2.drawLine(50 + widthDiff * i, 495, 50 + widthDiff * i, 505);
               g2.drawString("" + (i + 1), 47 + widthDiff * i, 520);  
          } //end for
          
          //Draw remaining labels/lines at the origin
          g2.drawLine(45, 500, 50, 500);
          g2.drawString("0", 23, 505);
          g2.drawLine(50, 500, 50, 505);
          g2.drawString("1", 47, 520);  
          
          //Vertical Stretch Value = 4.8
          
          //Plot each of the data points
          for (int i = 0; i < this.numMarks; i++)
          {
               g2.fillOval((50 + widthDiff * i - 4), 
                           (500 - (int)Math.round(4.8 * this.marksToGraph.get(i).doubleValue()) - 4), 8, 8);
          } //end for
          
          //Plot lines that connect all the data points
          for (int i = 0; i < this.numMarks-1; i++)
          {
               g2.drawLine(50 + widthDiff * i, 500 - (int)Math.round(4.8 * this.marksToGraph.get(i).doubleValue()),
                           50 + widthDiff * (i + 1), 500 - (int)Math.round(4.8 * this.marksToGraph.get(i+1).doubleValue()));
          } //end for
          
          //Manually adjust next predicted mark if it is outside of the allowed range
          if (this.nextMark > 100)
          {
               this.nextMark = 100;
          }
          else if (this.nextMark < 0)
          {
               this.nextMark = 0;
          } //end if
          
          //Plot the predicted next mark and line of best fit
          g2.setColor(Color.RED);
          g2.fillOval(50 + widthDiff * this.numMarks - 4, 
                      500 - (int)Math.round(this.nextMark * 4.8) - 4, 8, 8);
          
          g2.drawLine(50, 500 - (int)Math.round(4.8 * this.intercept), 
                      50 + widthDiff * this.numMarks, 500 - (int)Math.round(4.8 * this.nextMark));
          
     } //end paintComponent
     
     //Return the predicted next mark
     public double getNextMark()
     {
          return this.nextMark;
     } //end getNextMark
} //end GraphLine