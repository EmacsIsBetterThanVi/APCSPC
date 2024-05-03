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
      while(sc.hasNextLine()){
        lines.add(sc.nextLine());
      }
      boolean canrun = true;
      while(canrun){
        canrun=lang(tokenize(lines.get(addr)));
        addr++;
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
    if(tokens.get(0).key == ' '){
      if(tokens.get(1).value == "←"){
        while(tokens.get(2).key != 'e'){
          String tmp="";
          for(int i = 0; i < tokens.size()-2; i++){
            tmp=tmp+tokens.get(i).value+tokens.get(i).key;
          }
          tokens.set(2, new Pair<Character, String>('e', lang(tokenize())))
        }
        variables.put(tokens.get(0).value, new Pair<String, Object>(TYPEOF(tokens.get(2).value),tokens.get(2).value));
      } else if(tokens.get(1).value == "+"){
        while(tokens.get(2).key != 'e'){
          String tmp="";
          for(int i = 2; i < tokens.size()-2; i++){
            tmp=tmp+tokens.get(i).value+tokens.get(i).key;
          }
          tokens.set(2, new Pair<Character, String>('e', lang(tokenize())))
        }
        return tokens.get(0).value+tokens.get(2).value
      } else if(tokens.get(1).value == "-"){
        while(tokens.get(2).key != 'e'){
          String tmp="";
          for(int i = 2; i < tokens.size()-2; i++){
            tmp=tmp+tokens.get(i).value+tokens.get(i).key;
          }
          tokens.set(2, new Pair<Character, String>('e', lang(tokenize())))
        }
        return tokens.get(0).value-tokens.get(2).value
      } else if(tokens.get(1).value == "*"){
        while(tokens.get(2).key != 'e'){
          String tmp="";
          for(int i = 2; i < tokens.size()-2; i++){
            tmp=tmp+tokens.get(i).value+tokens.get(i).key;
          }
          tokens.set(2, new Pair<Character, String>('e', lang(tokenize())))
        }
        return tokens.get(0).value*tokens.get(2).value
      } else if(tokens.get(1).value == "/"){
        while(tokens.get(2).key != 'e'){
          String tmp="";
          for(int i = 2; i < tokens.size()-2; i++){
            tmp=tmp+tokens.get(i).value/tokens.get(i).key;
          }
          tokens.set(2, new Pair<Character, String>('e', lang(tokenize())))
        }
        return tokens.get(0).value+tokens.get(2).value
      }
    }
    return true;
  }
  public static String TYPEOF(String value){
    try{
      Integer.parseInt(value);
      return "int";
    }
    try{
      Double.parseDouble(value);
      return "float";
    }
    try{
      Boolean.parseBoolean(value);
      return "boolean";
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