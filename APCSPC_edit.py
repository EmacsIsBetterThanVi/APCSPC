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
x=0
y=0
file=[""]
if len(sys.argv)==1:
  filename=""
else:
  if sys.argv[1]!="":
    filename=sys.argv[1]
  else:
    filename=""
  try:
    f=open(filename,"r")
    file=f.read().split("\n")
  except:
    print("\007")
    file=[""]
fileorg=file
while True:
  os.system("clear")
  for i in file:
    print(i)
  sys.stdout.write("\033[{};{}H".format(y+1, x+1))
  sys.stdout.flush()
  c=getch()
  if(c=='q'):
    os.system("clear")
    if(file==fileorg):
      exit()
    print("SAVE[y/n]?", end="")
    sys.stdout.flush()
    c=getch()
    if(c=='n'):
      exit()
    elif(c=='y'):
      if filename=="":
        filename=input("Enter a filename: ")
      if filename=="":
        print("\007")
      else:
        f=open(filename,"w")
        for i in file:
          f.write(i+"\n")
        f.close()
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
      else:
        f=open(filename,"w")
        for i in file:
          f.write(i+"\n")
        f.close()
    elif c=='a':
      filename=input("Enter a filename: ")
      if filename=="":
        print("\007")
      else:
        f=open(filename,"w")
        for i in file:
          f.write(i+"\n")
        f.close()
    elif c=='c':
      os.system("clear")
      if file!=fileorg:
        print("SAVE[y/n]?", end="")
        sys.stdout.flush()
        c=getch()
        if(c=='n'):
          filename=""
          file=[""]
          fileorg=[""]
          x=0
          y=0
        elif(c=='y'):
          if filename=="":
            filename=input("Enter a filename: ")
          if filename=="":
            print("\007")
          else:
            f=open(filename,"w")
            for i in file:
              f.write(i+"\n")
            f.close()
            filename=""
            file=[""]
            x=0
            y=0
    elif c=='l':
      if file!=fileorg:
        print("SAVE[y/N]?", end="")
        sys.stdout.flush()
        c=getch()
        if c=='y':
          if filename=="":
            filename=input("Enter a filename: ")
          if filename=="":
            print("\007")
          else:
            f=open(filename,"w")
            for i in file:
              f.write(i+"\n")
            f.close()
      filename=input("Enter a filename: ")
      if filename=="":
        print("\007")
      else:
        try:
          f=open(filename,"r")
          file=f.read().split("\n")
          f.close()
          x=0
          y=0
          fileorg=file
        except:
          print("\007")
  if c=='z':
    if x==len(file[y]):
      file[y]=file[y]+'←'
      x=x+1
    else:
      tmp=list(file[y])
      tmp[x]='←'
      file[y]="".join(tmp)
  if c=='x':
    if x==len(file[y]):
      file[y]=file[y]+'→'
      x=x+1
    else:
      tmp=list(file[y])
      tmp[x]='→'
      file[y]="".join(tmp)
  if c=='v':
    if x==len(file[y]):
      file[y]=file[y]+'≠'
      x=x+1
    else:
      tmp=list(file[y])
      tmp[x]='≠'
      file[y]="".join(tmp)
  if c=='b':
    if x==len(file[y]):
      file[y]=file[y]+'≤'
      x=x+1
    else:
      tmp=list(file[y])
      tmp[x]='≤'
      file[y]="".join(tmp)
  if c=='n':
    if x==len(file[y]):
      file[y]=file[y]+'≥'
      x=x+1
    else:
      tmp=list(file[y])
      tmp[x]='≥'
      file[y]="".join(tmp)
  if c=='g':
    try:
      x=int(input("COL: "))
      y=int(input("ROW: "))
    except:
      print("\007")
  if c=='l':
    c=getch()
    if x==len(file[y]):
      file[y]=file[y]+c
      x=x+1
    else:
      tmp=list(file[y])
      tmp[x]=c
      file[y]="".join(tmp)
  if c not in "qwertyuiopasdfghjklzxcvbnm":
    if x==len(file[y]):
      file[y]=file[y]+c
      x=x+1
    else:
      tmp=list(file[y])
      tmp[x]=c
      file[y]="".join(tmp)