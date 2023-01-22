package View;

import Controller.Controller;
import Model.ADT.*;
import Model.Expression.ArithExpression;
import Model.Expression.ValueExpression;
import Model.Expression.VarExpression;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;
import Exception.MyException;

import java.io.BufferedReader;


public class View {
////    int v; v=2;Print(v)
//    static IStatement ex1= new CompStatement(new VarDeclStatement("v",new IntType()),
//            new CompStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new
//                    VarExpression("v"))));
//
////    int a;int b; a=2+3*5;b=a+1;Print(b)
//    static IStatement ex2 = new CompStatement( new VarDeclStatement("a",new IntType()),
//        new CompStatement(new VarDeclStatement("b",new IntType()),
//                new CompStatement(new AssignStatement("a", new ArithExpression(1,new ValueExpression(new IntValue(2)),new
//                        ArithExpression(3,new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
//                        new CompStatement(new AssignStatement("b",new ArithExpression(1,new VarExpression("a"), new ValueExpression(new
//                                IntValue(1)))), new PrintStatement(new VarExpression("b"))))));
//
////    bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
//    static IStatement ex3 = new CompStatement(new VarDeclStatement("a",new BoolType()),
//            new CompStatement(new VarDeclStatement("v", new IntType()),
//                    new CompStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
//                            new CompStatement(new IfStatement(new VarExpression("a"),new AssignStatement("v",new ValueExpression(new
//                                    IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
//                                    VarExpression("v"))))));

}
