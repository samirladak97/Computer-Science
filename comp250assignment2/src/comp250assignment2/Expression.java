package comp250assignment2;

/*
Name: Samir Ladak
Student Number: 260667874
 */

import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;

public class Expression  {
	private ArrayList<String> tokenList;

	//  Constructor    
	/**
	 * The constructor takes in an expression as a string
	 * and tokenizes it (breaks it up into meaningful units)
	 * These tokens are then stored in an array list 'tokenList'.
	 */

	Expression(String expressionString) throws IllegalArgumentException{
		tokenList = new ArrayList<String>();
		StringBuilder token = new StringBuilder();

		//ADD YOUR CODE BELOW HERE
		//..
		
		//take out all white spaces
		expressionString = expressionString.replaceAll("\\s+", "");
		//break the expression into single elements
		String delims = "";
		String[] arithmetic = expressionString.split(delims);
		int i;
		//put all the tokens into the ArrayList
		for (i=0; i<arithmetic.length; i++)
		{
			tokenList.add(arithmetic[i]);
		}
		//if the integer is more than one digit then combine it in the ArrayList
		for (i=0; i<tokenList.size(); i++)
		{
			if (isInteger(tokenList.get(i).toString()) && isInteger(tokenList.get(i+1).toString()))
			{
				String newNum = tokenList.get(i).toString() + tokenList.get(i+1).toString();
				tokenList.set(i, newNum);
				tokenList.remove(i+1);
				i=i-1;
			}
			else if (tokenList.get(i).toString().equals("+") && tokenList.get(i+1).toString().equals("+"))
			{
				String newAdd = tokenList.get(i).toString() + tokenList.get(i+1).toString();
				tokenList.set(i, newAdd);
				tokenList.remove(i+1);
			}
			else if (tokenList.get(i).toString().equals("-") && tokenList.get(i+1).toString().equals("-"))
			{
				String newSub = tokenList.get(i).toString() + tokenList.get(i+1).toString();
				tokenList.set(i, newSub);
				tokenList.remove(i+1);
			}
		}
		
		//..
		//ADD YOUR CODE ABOVE HERE
	}

	/**
	 * This method evaluates the expression and returns the value of the expression
	 * Evaluation is done using 2 stack ADTs, operatorStack to store operators
	 * and valueStack to store values and intermediate results.
	 * - You must fill in code to evaluate an expression using 2 stacks
	 */
	public Integer eval(){
		Stack<String> operatorStack = new Stack<String>();
		Stack<Integer> valueStack = new Stack<Integer>();
		
		//ADD YOUR CODE BELOW HERE
		
		int i;
		for (i=0; i<tokenList.size(); i++)
		{	
			if (tokenList.get(i).toString().equals("(") || tokenList.get(i).toString().equals("["))
			{
				continue;
			}
			else if (isInteger(tokenList.get(i).toString()))
			{
				valueStack.push(Integer.valueOf(tokenList.get(i).toString()));
			}
			else if (tokenList.get(i).toString().equals("+") || tokenList.get(i).toString().equals("-") || tokenList.get(i).toString().equals("*") || tokenList.get(i).toString().equals("/") || tokenList.get(i).toString().equals("++") || tokenList.get(i).toString().equals("--"))
			{
				operatorStack.push(tokenList.get(i).toString());
			}
			else if (tokenList.get(i).toString().equals(")"))
			{
				String operand = operatorStack.pop();
				if (operand.equals("+"))
				{
					//pop twice and add them
					Integer num2 = valueStack.pop();
					Integer num1 = valueStack.pop();
					Integer sum = num1+num2;
					valueStack.push(sum);
				}
				else if (operand.equals("-"))
				{
					//pop twice and subtract
					Integer num2 = valueStack.pop();
					Integer num1 = valueStack.pop();
					Integer difference = num1-num2;
					valueStack.push(difference);
				}
				else if (operand.equals("*"))
				{
					//pop twice and multiply
					Integer num2 = valueStack.pop();
					Integer num1 = valueStack.pop();
					Integer product = num1*num2;
					valueStack.push(product);
				}
				else if (operand.equals("/"))
				{
					//pop twice and divide
					Integer num2 = valueStack.pop();
					Integer num1 = valueStack.pop();
					Integer quotient = num1/num2;
					valueStack.push(quotient);
				}
				else if (operand.equals("++"))
				{
					//pop once and add 1
					Integer num1 = valueStack.pop();
					Integer sum = num1+1;
					valueStack.push(sum);
				}
				else if (operand.equals("--"))
				{
					//pop once and subtract 1
					Integer num1 = valueStack.pop();
					Integer difference = num1-1;
					valueStack.push(difference);
				}
			}
			else if (tokenList.get(i).toString().equals("]"))
			{
				Integer value = valueStack.pop();
				if (value < 0)
				{
					value = value*(-1);
					valueStack.push(value);
				}
				else
				{
					valueStack.push(value);
				}
			}
		}
		
		return valueStack.pop();
		
		//ADD YOUR CODE ABOVE HERE
	}

	//Helper methods
	/**
	 * Helper method to test if a string is an integer
	 * Returns true for strings of integers like "456"
	 * and false for string of non-integers like "+"
	 * - DO NOT EDIT THIS METHOD
	 */
	private boolean isInteger(String element){
		try{
			Integer.valueOf(element);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}

	/**
	 * Method to help print out the expression stored as a list in tokenList.
	 * - DO NOT EDIT THIS METHOD    
	 */

	@Override
	public String toString(){	
		String s = new String(); 
		for (String t : tokenList )
			s = s + "~"+  t;
		return s;		
	}
}
