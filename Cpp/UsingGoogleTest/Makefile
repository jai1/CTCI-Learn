#Include Directory
IDIR = "inc"

#Lib Directory
LDIR = "lib"

#Compiler
CC = gcc

#Compile Flags
CXXFLAGS =	-O2 -g -Wall -fmessage-length=0 -I$(IDIR)

#Source Directory
SDIR = "src"

#Object Directory
ODIR = "obj"

#test directory 
TDIR="tests"

#Bin Directory
BDIR="bin"

#library
LIBS =	libUsingGoogleTest.so

#Target
TARGET=$(BDIR)/main

$(ODIR)/%.o: $(SDIR)/%.cpp

$(LIBS): *.o
	$(CC) -c -o $(CFLAGS) $@ $^

$(TARGET): $(LIBS)
	$(CC) -c -o $(CFLAGS) $@ $< 
	

all: $(TARGET) 
	$(CC) 

clean:
	rm -f $(OBJS) $(TARGET)
