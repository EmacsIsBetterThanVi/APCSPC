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
while True:
  os.system("clear")
  sys.stdout.write("\033[{};{}H".format(y, x))
  sys.stdout.write("\033[K")
  sys.stdout.flush()
  c=input()