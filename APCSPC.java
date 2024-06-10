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
    boolean NAME=false;
    boolean Quoats=false;
    for(int i = 0; i < code.length(); i++){
      if(code.charAt(i)=='"'){
        Quoats=!Quoats;
      } else if(code.charAt(i)=='\\' && Quoats){
        i++;
        chararr[tc]=code.charAt(i);
        tc++;
      }else if(Quoats){
        chararr[tc] = code.charAt(i);
        tc++;
      } else if(code.charAt(i) == '('){
        tokens.putIfAbsent(tt, new Pair<Character, String>('(',new String(chararr)));
        tt++;
        continue;
      } else if(code.charAt(i) == ' '){
        tokens.putIfAbsent(tt, new Pair<Character, String>(' ',new String(chararr)));
        tt++;
        continue;
      } else if(code.charAt(i) == ')'){
        tokens.putIfAbsent(tt, new Pair<Character, String>(')',new String(chararr)));
        tt++;
        continue;
      } 
      else if(code.charAt(i) == ','){
        tokens.putIfAbsent(tt, new Pair<Character, String>(',',new String(chararr)));
        tt++;
        continue;
      } else if(code.charAt(i) == '`'){
        if(name){
          charrarr=((String)variables.get(new String(charrarr)).getValue()).toCharArray();
        } else name=true;
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
          tokens.set(2, new Pair<Character, String>('e', lang(tokenize(tmp).toString())));
        }
        variables.put(tokens.get(0).getValue(), new Pair<String, Object>(TYPEOF(tokens.get(2).getValue()),tokens.get(2).getValue()));
      } else if(tokens.get(1).getValue() == "+"){
        while(tokens.get(2).getKey() != 'e'){
          String tmp="";
          for(int i = 2; i < tokens.size()-2; i++){
            tmp=tmp+tokens.get(i).getValue()+tokens.get(i).getKey();
          }
          tokens.set(2, new Pair<Character, String>('e', lang(tokenize(tmp)).toString()));
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
          tokens.set(2, new Pair<Character, String>('e', lang(tokenize(tmp)).toString()));
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
          tokens.set(2, new Pair<Character, String>('e', lang(tokenize(tmp)).toString()));
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
          tokens.set(2, new Pair<Character, String>('e', lang(tokenize(tmp)).toString()));
        }
          if(TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "int")
            return Interger.parseInt(tokens.get(0).getValue())/Interger.parseInt(tokens.get(2).getValue());
            if((TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "float") || (TYPEOF(tokens.get(2).getValue()) == "int" && TYPEOF(tokens.get(0).getValue()) == "float") || (TYPEOF(tokens.get(0).getValue()) == "float" && TYPEOF(tokens.get(2).getValue()) == "float"))
              return Double.parseDouble(tokens.get(0).getValue())/Double.parseDouble(tokens.get(2).getValue());
          return tokens.get(0).getValue().split(tokens.get(2).getValue());
        } else if(tokens.get(1).getValue() == "MOD"){
      while(tokens.get(2).getKey() != 'e'){
        String tmp="";
        for(int i = 2; i < tokens.size()-2; i++){
           tmp=tmp+tokens.get(i).getValue()+tokens.get(i).getKey();
        }
        tokens.set(2, new Pair<Character, String>('e', lang(tokenize(tmp)).toString()));
      }
        if(TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "int")
          return Interger.parseInt(tokens.get(0).getValue())%Interger.parseInt(tokens.get(2).getValue());
          if((TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "float") || (TYPEOF(tokens.get(2).getValue()) == "int" && TYPEOF(tokens.get(0).getValue()) == "float") || (TYPEOF(tokens.get(0).getValue()) == "float" && TYPEOF(tokens.get(2).getValue()) == "float"))
            return Double.parseDouble(tokens.get(0).getValue())%Double.parseDouble(tokens.get(2).getValue());
        return tokens.get(0).getValue().length()%tokens.get(2).getValue().length();
      } else if(tokens.get(1).getValue() == "="){
      while(tokens.get(2).getKey() != 'e'){
        String tmp="";
        for(int i = 2; i < tokens.size()-2; i++){
           tmp=tmp+tokens.get(i).getValue()+tokens.get(i).getKey();
        }
        tokens.set(2, new Pair<Character, String>('e', lang(tokenize(tmp)).toString()));
      }
        if(TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "int")
          return Interger.parseInt(tokens.get(0).getValue())==Interger.parseInt(tokens.get(2).getValue());
          if((TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "float") || (TYPEOF(tokens.get(2).getValue()) == "int" && TYPEOF(tokens.get(0).getValue()) == "float") || (TYPEOF(tokens.get(0).getValue()) == "float" && TYPEOF(tokens.get(2).getValue()) == "float"))
            return Double.parseDouble(tokens.get(0).getValue())==Double.parseDouble(tokens.get(2).getValue());
        return tokens.get(0).getValue()==tokens.get(2).getValue();
      } else if(tokens.get(1).getValue() == "≠"){
        while(tokens.get(2).getKey() != 'e'){
          String tmp="";
          for(int i = 2; i < tokens.size()-2; i++){
             tmp=tmp+tokens.get(i).getValue()+tokens.get(i).getKey();
          }
          tokens.set(2, new Pair<Character, String>('e', lang(tokenize(tmp)).toString()));
        }
          if(TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "int")
            return Interger.parseInt(tokens.get(0).getValue())!=Interger.parseInt(tokens.get(2).getValue());
            if((TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "float") || (TYPEOF(tokens.get(2).getValue()) == "int" && TYPEOF(tokens.get(0).getValue()) == "float") || (TYPEOF(tokens.get(0).getValue()) == "float" && TYPEOF(tokens.get(2).getValue()) == "float"))
              return Double.parseDouble(tokens.get(0).getValue())!=Double.parseDouble(tokens.get(2).getValue());
          return tokens.get(0).getValue()!=tokens.get(2).getValue();
        } else if(tokens.get(1).getValue() == ">"){
          while(tokens.get(2).getKey() != 'e'){
            String tmp="";
            for(int i = 2; i < tokens.size()-2; i++){
               tmp=tmp+tokens.get(i).getValue()+tokens.get(i).getKey();
            }
            tokens.set(2, new Pair<Character, String>('e', lang(tokenize(tmp)).toString()));
          }
            if(TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "int")
              return Interger.parseInt(tokens.get(0).getValue()) > Interger.parseInt(tokens.get(2).getValue());
              if((TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "float") || (TYPEOF(tokens.get(2).getValue()) == "int" && TYPEOF(tokens.get(0).getValue()) == "float") || (TYPEOF(tokens.get(0).getValue()) == "float" && TYPEOF(tokens.get(2).getValue()) == "float"))
                return Double.parseDouble(tokens.get(0).getValue())>Double.parseDouble(tokens.get(2).getValue());
            return RandFromArray(new String[]{"Chocolate", "Vanilla", "Strawberry", "Mint", "Lemon", "Orange", "Peach"});
          } else if(tokens.get(1).getValue() == "<"){
          while(tokens.get(2).getKey() != 'e'){
            String tmp="";
            for(int i = 2; i < tokens.size()-2; i++){
               tmp=tmp+tokens.get(i).getValue()+tokens.get(i).getKey();
            }
            tokens.set(2, new Pair<Character, String>('e', lang(tokenize(tmp)).toString()));
            if(TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "int")
              return Interger.parseInt(tokens.get(0).getValue()) < Interger.parseInt(tokens.get(2).getValue());
              if((TYPEOF(tokens.get(0).getValue()) == "int" && TYPEOF(tokens.get(2).getValue()) == "float") || (TYPEOF(tokens.get(2).getValue()) == "int" && TYPEOF(tokens.get(0).getValue()) == "float") || (TYPEOF(tokens.get(0).getValue()) == "float" && TYPEOF(tokens.get(2).getValue()) == "float"))
                return Double.parseDouble(tokens.get(0).getValue())<Double.parseDouble(tokens.get(2).getValue());
            return RandFromArray(new String[]{"Sprinkels", "Chocolate", "Chocolate Chips", "Peanuts", "Fruit"});
          }
      }
    } else if(tokens.get(0).getKey() == '('){
      if(tokens.get(0).getValue() == "DISPLAY"){ 
        if(tokens.get(1).getKey() == ','){
          int printcount = tokens.get(1).getValue().split("%").length-1;
          String arr[] = new String[tokens.keySet().length-2];
          for(int i=0; i < array.length; i++){
            if(i>1){
              arr[i-2]=tokens.get(i).getValue();
            }
          }
          System.out.printf(tokens.get(1).getValue(), (Object[]) arr);
        } else if(tokens.get(1).getKey() == ')'){
          System.out.println(tokens.get(1).getValue());
        } 
      } if(tokens.get(0).getValue() == "INPUT"){
          return sc.nextLine();
      }
    }
    return true;
  }
  public static String TYPEOF(String value){
    try{
      Integer.parseInt(value.toString());
      return "int";
    } catch(Exception e){
    }
    try{
      Double.parseDouble(value.toString());
      return "float";
    }catch(Exception e){
      }
    try{
      Boolean.parseBoolean(value.toString());
      return "boolean";
    }catch(Exception e){
      }
    return "string";
  }
  public static string RandFromArray(String[] arr){
    return arr[new Random().nextInt(arr.length)];
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
