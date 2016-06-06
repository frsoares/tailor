package com.sleekbyte.tailor.listeners;


import org.antlr.v4.runtime.misc.NotNull;

import com.sleekbyte.tailor.antlr.SwiftBaseListener;
import com.sleekbyte.tailor.antlr.SwiftParser;
import com.sleekbyte.tailor.output.Printer;

public class FPMetricsListener extends SwiftBaseListener {


  private Printer printer;

  private static int closureCounter = 0;
  private static int sumClosuresLength  = 0;

  private static int closureSignature  = 0;

  private static int functionCallsWithClosureExpr = 0;

  private static int mapCallsCounter = 0;
  private static int reduceCallsCounter = 0;
  private static int filterCallsCounter = 0;

  // TODO: more analysis on the call sites of those functions
  // e.g. if they are bundled closer to each other or more spread.

  public FPMetricsListener(Printer printer){
    this.printer = printer;
  }

  @Override 
  public void enterFunctionCallWithClosureExpression(@NotNull SwiftParser.FunctionCallWithClosureExpressionContext ctx) { 

    functionCallsWithClosureExpr += 1;

  }

  @Override 
  public void enterClosureExpression(@NotNull SwiftParser.ClosureExpressionContext ctx) { 

    closureCounter += 1;

    int constructLength = ctx.getStop().getLine() - ctx.getStart().getLine() + 1;

    sumClosuresLength += constructLength;

  }

  @Override 
  public void enterClosureSignature(@NotNull SwiftParser.ClosureSignatureContext ctx) { 
    closureSignature += 1;
    System.out.println(ctx.getText());
  }


  public static void printResults(){

    System.out.println("Number of closures found: " + closureCounter);
    System.out.println("Number of signatures for closures: " + closureSignature);
    System.out.println("Number of function calls with closure expression: " + functionCallsWithClosureExpr);
    System.out.println("Average closure length: "+ sumClosuresLength +"/"+ closureCounter + "=" + (closureCounter!=0?(sumClosuresLength/closureCounter):0));

  }

}

