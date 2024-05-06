import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.util.Arrays;
public class APCSPC{
  public static Map<String, Pair<String, Object>> variables = new HashMap<String, Pair<String, Object>>();
  public static Scanner sc = new Scanner(System.in);
  public static int addr=0;
  public static void main(String[] args){
      List<String> lines = new ArrayList<String>();
      File file = new File(args[0]+".APPC");
      Scanner scn = new Scanner(file);
      while(scn.hasNextLine()){
        lines.add(scn.nextLine());
      }
      boolean canrun = true;
      while(canrun){
        canrun=lang(tokenize(lines.get(addr)));
        addr++;
        if(addr>=lines.size()){
          canrun=false;
        }
      }
  }
  public static Map<Integer,Pair<Character, String>> tokenize(String code){
    Map<Integer,Pair<Character, String>> tokens = new HashMap<Integer, Pair<Character, String>>();
    char[] chararr = new char[256];
    int tt=0;
    int tc=0;
    for(int i = 0; i < code.length(); i++){
      if(code.charAt(i) == '('){
        tokens.putIfAbsent(tt, new Pair<Character, String>('(',new String(chararr)));
        continue;
      } else if(code.charAt(i) == ' '){
        tokens.putIfAbsent(tt, new Pair<Character, String>(' ',new String(chararr)));
        continue;
      } else if(code.charAt(i) == ')'){
        tokens.putIfAbsent(tt, new Pair<Character, String>(')',new String(chararr)));
        continue;
      } 
      else if(code.charAt(i) == ','){
        tokens.putIfAbsent(tt, new Pair<Character, String>(',',new String(chararr)));
        continue;
      }else{
        chararr[tc] = code.charAt(i);
        tc++;
      }
    }
    tokens.putIfAbsent(tt, new Pair<Character, String>('e',new String(chararr)));
    return tokens;
  }

  public static Object lang(Map<Integer,Pair<Character, String>> tokens) {
    if(tokens.get(0).getKey() == ' '){
      if(tokens.get(1).getValue() == "←"){
        while(tokens.get(2).getKey() != 'e'){
          String tmp="";
          for(int i = 0; i < tokens.size()-2; i++){
            tmp=tmp+tokens.get(i).getValue()+tokens.get(i).getKey();
          }
          tokens.set(2, new Pair<Character, String>('e', lang(tokenize())));
        }
        variables.put(tokens.get(0).getValue(), new Pair<String, Object>(TYPEOF(tokens.get(2).getValue()),tokens.get(2).getValue()));
      } else if(tokens.get(1).getValue() == "+"){
        while(tokens.get(2).getKey() != 'e'){
          String tmp="";
          for(int i = 2; i < tokens.size()-2; i++){
            tmp=tmp+tokens.get(i).getValue()+tokens.get(i).getKey();
          }
          tokens.set(2, new Pair<Character, String>('e', lang(tokenize())));
        }
        if(TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "int")
        return Interger.parseInt(tokens.get(0).getValue())+Interger.parseInt(tokens.get(2).getValue());
        if((TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "float") || (TYPEOF(tokens.get(2).getValue()) == "int" && TYPEOF(tokens.get(0).getValue()) == "float") || (TYPEOF(tokens.get(0).getValue()) == "float" && TYPEOF(tokens.get(2).getValue()) == "float"))
          return Double.parseDouble(tokens.get(0).getValue())+Double.parseDouble(tokens.get(2).getValue());
      } else if(tokens.get(1).getValue() == "-"){
        while(tokens.get(2).getKey() != 'e'){
          String tmp="";
          for(int i = 2; i < tokens.size()-2; i++){
            tmp=tmp+tokens.get(i).getValue()+tokens.get(i).getKey();
          }
          tokens.set(2, new Pair<Character, String>('e', lang(tokenize())));
        }
        if(TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "int")
          return Interger.parseInt(tokens.get(0).getValue())-Interger.parseInt(tokens.get(2).getValue());
          if((TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "float") || (TYPEOF(tokens.get(2).getValue()) == "int" && TYPEOF(tokens.get(0).getValue()) == "float") || (TYPEOF(tokens.get(0).getValue()) == "float" && TYPEOF(tokens.get(2).getValue()) == "float"))
            return Double.parseDouble(tokens.get(0).getValue())-Double.parseDouble(tokens.get(2).getValue());
        return tokens.get(0).getValue().replace(tokens.get(2).getValue(), "");
      } else if(tokens.get(1).getValue() == "*"){
        while(tokens.get(2).getKey() != 'e'){
          String tmp="";
          for(int i = 2; i < tokens.size()-2; i++){
            tmp=tmp+tokens.get(i).getValue()+tokens.get(i).getKey();
          }
          tokens.set(2, new Pair<Character, String>('e', lang(tokenize())));
        }
          if(TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "int")
            return Interger.parseInt(tokens.get(0).getValue())*Interger.parseInt(tokens.get(2).getValue());
            if((TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "float") || (TYPEOF(tokens.get(2).getValue()) == "int" && TYPEOF(tokens.get(0).getValue()) == "float") || (TYPEOF(tokens.get(0).getValue()) == "float" && TYPEOF(tokens.get(2).getValue()) == "float"))
              return Double.parseDouble(tokens.get(0).getValue())*Double.parseDouble(tokens.get(2).getValue());
          return tokens.get(0).getValue().indexOf(tokens.get(2).getValue());
      } else if(tokens.get(1).getValue() == "/"){
        while(tokens.get(2).getKey() != 'e'){
          String tmp="";
          for(int i = 2; i < tokens.size()-2; i++){
             tmp=tmp+tokens.get(i).getValue()+tokens.get(i).getKey();
          }
          tokens.set(2, new Pair<Character, String>('e', lang(tokenize())));
        }
          if(TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "int")
            return Interger.parseInt(tokens.get(0).getValue())/Interger.parseInt(tokens.get(2).getValue());
            if((TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "float") || (TYPEOF(tokens.get(2).getValue()) == "int" && TYPEOF(tokens.get(0).getValue()) == "float") || (TYPEOF(tokens.get(0).getValue()) == "float" && TYPEOF(tokens.get(2).getValue()) == "float"))
              return Double.parseDouble(tokens.get(0).getValue())/Double.parseDouble(tokens.get(2).getValue());
          return tokens.get(0).getValue().split(tokens.get(2).getValue());
        }
    }
    return true;
  }
  public static String TYPEOF(String value){
    try{
      Integer.parseInt(getValue());
      return "int";
    } catch(Exception e){
    }
    try{
      Double.parseDouble(getValue());
      return "float";
    }catch(Exception e){
      }
    try{
      Boolean.parseBoolean(getValue());
      return "boolean";
    }catch(Exception e){
      }
    return "string";
  }
}
class Pair<T1, T2> {
  private T1 key;
  private T2 value;

  public Pair(T1 key, T2 value) {
    this.key = key;
    this.value = value;
  }

  public T1 getKey() {
    return key;
  }

  public T2 getValue() {
    return value;
  }

  public void setKey(T1 key) {
    this.key = key;
  }

  public void setValue(T2 value) {
    this.value = value;
  }
}