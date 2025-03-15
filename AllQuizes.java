package everything;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class AllQuizes {
	public static int countLines(String filePath){
		int lineNum = 0;
		FileInputStream inputFile = null;
		try {
			inputFile = new FileInputStream(filePath);
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		Scanner file = new Scanner(inputFile);
		while(file.hasNextLine()) {
			file.nextLine();
			lineNum++;
		}
		file.close();
		return lineNum;
	}
	public static void fillArray(int a, int b, String filePath, String[][] array){
		FileInputStream inputFile = null;
		try {
			inputFile = new FileInputStream(filePath);
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		Scanner file = new Scanner(inputFile);
		if(a == 1) {
			for(int i = 0; i < b; i++) {
				array[a][i] = file.nextLine();
			}
		}
		else{
			for(int i = 0; i < b; i++) {
				array[a][i] = file.nextLine().replace("\\n", "\n");
				}	
			}
		file.close();
		}
		
	public static void main(String[] args) {
		//I want to make a program that will use all the quizzes and let me choose a random number of them to practice.
		//vars
		String answer;
		int requestedNum = 0;
		int lineNum = 0;
		//Getting Questions and template
		// \na) \nb) \nc) \nd) \n
		lineNum = countLines("questions.txt");
		String[][] questions = new String[lineNum][lineNum];
		fillArray(0, lineNum, "questions.txt", questions);
		fillArray(1, lineNum, "answers.txt", questions);
		//input
		System.out.print("How many questions would you like? Currently available " + questions[0].length + ": ");
		Scanner input = new Scanner(System.in);
		requestedNum = input.nextInt();
		//Makes sure there are enough questions
		if(requestedNum > questions[0].length) {
			while(requestedNum > questions[0].length) {
				System.out.print("Not enough questions! Max is " + questions[0].length + ". Try again:");
				requestedNum = input.nextInt();
			}
		}
		final int totalQuestions = requestedNum;
		//Create array to prevent dupe questions
		int[] dontRepeat = new int[totalQuestions];
		for(int i = totalQuestions; i > 0; --i) { //sets all values in the array to a value not linked to a question
			dontRepeat[i - 1] = totalQuestions + 1;
		}
		//loop until done with questions
		while(requestedNum > 0) {
			//Choosing random question with preventing dupe question check
			int question = (int)(Math.random() * totalQuestions);
			for(int i = totalQuestions; i > 0; --i) {
				if(question == dontRepeat[i - 1]) {
					question = (int)(Math.random() * totalQuestions);
					i = totalQuestions;
				} else if (question == dontRepeat[totalQuestions - 1]) { //cause the first if wont check the last value of the array
					question = (int)(Math.random() * totalQuestions);
					i = totalQuestions;
				}
			}
			dontRepeat[requestedNum - 1] = question;
			System.out.println("Question #" + question);
			System.out.println(questions[0][question]);
			answer = input.next();
				if(answer.equals(questions[1][question])) {
					System.out.println("Correct");
				}
				else {
					System.out.println("Incorrect it was " + questions[1][question]);
				}
				--requestedNum;
		}
		//output
		for(int i = totalQuestions; i > 0; --i) {
			System.out.println("You asked for : " + dontRepeat[i - 1]);
		}
		input.close();
	}

}
