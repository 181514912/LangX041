# LangX041
LangX041 is a small programing language (nano-programing language) with syntax inspired by Java, Python, JS, and C++ and runs on JVM. In the current version, LangX041 can perform arithmetic and conditional operations on integers.

## Tokens and Keywords
- **Comment** - Currently only single line comment is supported. As in Python, use `#` for adding comment. For example,  ` # this is a comment `
- **Statement Termination** - Use *dot operator*(`.`) for statement termination.
- **Importing** - Use `require` to import Java libraries. For example, `require java lang.` to import all classes of `java.lang`
- **Variable Declaration** - Use `let` keyword, as in JS, for variable declaration. For example, `let var = 9.`
- **Condition** - Classical `if` condition can be used whose syntax is as shown.
    ```
        if 2 > 1 do
            # do something
        stop
    ```
- **Looping** - Loop can be created using the keyword `loop_if`. The syntax is as shown.
    ```
        loop_if 2 > 1 do
            # do something >,<,==,>=,<=,!=
        stop
    ```
- **Mathematical Operations** - Currently *addition*(`+`), *subtraction*(`-`), *multiplication*(`*`), *division*(`/`) and *modulo*(`%`) operations can be performed on integers.
- **Mathematical Relations** - Currently *greater than*(`>`), *less than*(`<`), *equals to*(`==`), *greater than equals to*(`>=`), *less than equals to*(`<=`) and *not equals to*(`!=`) operations can be performed on integers.
- **Literals** - Variable names can consist *alphabet*, *number*(0 to 9) and `_` but cannot start with a *number*
- **Executing Java Methods** - We can ONLY execute public static methods in the formate ```ClassName::[Method|Members]( number )```. For example, `System.out.println()` method used for printing output can be used as `System::out::println()`. The property of operator `::` is taken from C++.

## Code Execution

LangX041 is an interpreted language like Perl or Python. It can read a text (source code) from file and parse it to create an object tree for interpreting them i.e. executing instructions.
- Make sure you have **JAVA** installed in the system as **JVM** is required for executing instructions.
- Write your code as per the syntax of the language and save it in a file with extension as `.lgx`
- Use the **JAR** present in `/JAR` folder
- In *command-line* or *terminal* execute the **JAR** while passing the saved `.lgx` file as an argument.


## Sample Code
Consider the following code of finding (even numbers from 0 to 50 ) - 6. Save it in a file named `test.lgx`
```
    # program to print required numbers
    require java lang.
    
    let var = 11.
    var = 0.
    
    loop_if var < 50  do
    	let tmp = 3.
    	if var%2 == 0 do
    		System::out::println( var - tmp * 2 ).
    	stop	# if ends
    	var = var + 1 .
    stop	# loop ends
```

Execute the **JAR** file using the command `java -jar langx041.jar test.lgx`. The output of the execution is
```
-6
-4
-2
0
2
4
6
8
10
12
14
16
18
20
22
24
26
28
30
32
34
36
38
40
42
```

- Make sure to pass the proper path of the source file in the argument. In the sample shown both the **JAR** and **test.lgx** are present in the same folder
- The above code is shown just to highlight the syntax and features of the language. It can further be optimized.

## Digging deep
Any language has four main parts

- **Lexer** - Converts source code to tokens. Implemented using *regular expressions*. 
- **Parser** - Converts tokens into nodes (syntax trees).
- **Interpreter** - Responsible for executing the generated nodes.
- **Runtime** - Can be procedural, class based, prototype based or functional.

In the design of LangX041, [JavaCC](https://en.wikipedia.org/wiki/JavaCC) is used as a lexar and parser generator. It is similar to yacc (another parser) in that it generates a parser from a formal grammar written in EBNF notation. It generates top-down parser, which limits it to the LL(K) class of grammar. Here LL(2) grammar is used for generating syntax tree using **JTB***(Java Tree Builder)*. Visit the official website of [JavaCC](https://javacc.org/) for more information.

Java Reflection is used for making an interpreter of the language. The grammar and tokens of the language can be found in `\src\langx041\MyNewGrammar.jtb` file.

A new language can be designed in many languages e.g. Ruby (using Racc parser), Python (using Ply parser), Java  (using JavaCC parser), C++, etc. There are also many lexar available e.g. Rex for Ruby, JFlex for Java, Ragel, etc. The use of Java for the design of LangX041 is just a choice. If a bug is found make sure to report.

License
----

MIT

