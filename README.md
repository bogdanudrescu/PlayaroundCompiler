# PlayaroundCompiler
Old Compiler/Interpreter school project.

This was a playaround project, compiling source code into an array of instructions further executed.

In `examples` folder there are some source code samples, hinting towards the language syntax.

Once a file opened in the app editor or written, it must be compiled, only after that is able to run.

In one file there may be several programs implemented. At runtime, a program must be started by typing its name in the Run console.

Example of compiled code:

```
program ec1;
	read a;
	read b;
	read c;
	x = (c - b) / a;
	writeln x;

program ec2;
	read a;
	read b;
	read c;
	d = b * b - 4 * a * c;
	if d < 0 begin
		writeln "Ecuatia nu are radacini reale.";
	else begin
		if d == 0 begin
			x = (0 - b) / (2 * a);
			writeln x;
		else begin
			x1 = ((0 - b)  + sqrt(d)) / (2 * a);
			x2 = ((0 - b)  - sqrt(d)) / (2 * a);
			writeln x1;
			writeln x2;
		end;
	end;
```
