import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
public class APCSPC{
  public Map<String, Pair<String, Object>> variables = new HashMap<String, Pair<String, Object>>();
  public static void main(String[] args){
    if(args[0].equals("edit")){
      new APCSPC_edit(args[1]);
    }
  }
  public Map<Integer,Pair<Character, String>> tokenize(String code){
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
    return tokens;
  }

  public boolean lang(String[] tokens) {
    return true;
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