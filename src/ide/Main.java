package ide;

import java.io.File;

import langx041.MyNewGrammar;
import langx041.interpreter.Interpreter;

public class Main {
	public static void main(String[] args) {
		try {
			
			if (args.length == 0) {
				throw new Exception("Error, file does not exists !");
			} else if (!new File(args[0]).exists()) {
				throw new Exception("Error, reading file : " + args[0] + " !");
			} else if (!args[0].substring(args[0].lastIndexOf('.')).trim().equals(".lgx")) {
				throw new Exception("Error, wrong file extension. Must be .lgx");
			}
			
			MyNewGrammar parser = new MyNewGrammar(new java.io.FileInputStream(args[0]));
			new Interpreter().visit(parser.Start());
			
		} catch (Throwable e) {
			System.out.println("Sorry, cannot continue! \n" + e);
			e.printStackTrace();
		}
	}
}
