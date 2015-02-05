package server;

import java.io.OutputStream;
import java.io.PrintStream;

public class Interceptor extends PrintStream 
{
    public Interceptor(OutputStream out)
    {
        super(out, true);
    }
    @Override
    public void print(String s)
    {
        super.print(s);
    	Server.console.printToConsole("SYSTEM", s);
    }
}
