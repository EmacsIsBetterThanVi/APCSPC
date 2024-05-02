import sys
import os
import tty
import termios
def getch():
  fd = sys.stdin.fileno()
  old_settings = termios.tcgetattr(fd)
  try:
    tty.setraw(sys.stdin.fileno())
    ch = sys.stdin.read(1)
  finally:
    termios.tcsetattr(fd, termios.TCSADRAIN, old_settings)
  return ch
os.system("clear")
x=0
y=0
filename=""
file=[""]
while True:
  os.system("clear")
  for i in file:
    print(i)
  sys.stdout.write("\033[{};{}H".format(y+1, x+1))
  sys.stdout.flush()
  c=getch()
  if(c=='q'):
    exit()
  if c=='w' and y>0:
    y-=1
  if c=='s' and y<len(file)-1:
    y+=1
  if c=='a' and x>0:
    x-=1
  if c=='a' and x==0 and y>0:
    y-=1
    x=len(file[y])
  if c=='d' and x<len(file[y]):
    x+=1
  if c=='d' and x==len(file[y]) and y<len(file)-1:
    y+=1
    x=0
  if c=="e":
    y+=1
    x=0
    file.append("")
  if c=='b':
    if x>0:
      tmp=list(file[y])
      tmp.pop(x-1)
      file[y]="".join(tmp)
      x-=1
    elif x==0 and y>0:
      file.pop(y)
      y-=1
      x=len(file[y])
    else:
      print("\007")
  if c=='f':
    c=getch()
    if c=='s':
      if filename=="":
        filename=input("Enter a filename: ")
      if filename=="":
        print("\007")
      f=open(filename,"w")
      for i in file:
        f.write(i+"\n")
      f.close()
    elif c=='l':
      filename=input("Enter a filename: ")
      if filename=="":
        print("\007")
      try:
        f=open(filename,"r")
        file=f.read().split("\n")
        f.close()
        x=0
        y=0
      except:
        print("\007")
  if c=='g':
    try:
      x=int(input("COL: "))
      y=int(input("ROW: "))
    except:
      print("\007")
  if c not in "qwertyuiopasdfghjklzxcvbnm":
    if x==len(file[y]):
      file[y]=file[y]+c
      x=x+1
    else:
      tmp=list(file[y])
      tmp[x]=c
      file[y]="".join(tmp)