#Include Directory
IDIR = ./inc

#Lib Directory
LDIR = lib

#Compiler
CC = g++

#Compile Flags
CXXFLAGS =	-O2 -g -Wall -fmessage-length=0 -I$(IDIR)

#Source Directory
SDIR = src

#Object Directory
ODIR = obj

#test directory 
TDIR = tests

#Bin Directory
BDIR = bin

#library
LIBS =	$(LDIR)/libUsingGoogleTest.so

#Target
TARGET=$(BDIR)/main

$(ODIR)/%.o: $(SDIR)/%.cpp
	$(CC) -lstdc++ -fPIC $(CXXFLAGS) -c -o $@ $< 

$(LIBS): $(ODIR)/Utils.o
	$(CC) -shared -o $@ $^ 

$(TARGET): $(ODIR)/main.o $(LIBS)
	$(CC) $(CXXFLAGS) -c -o $@ $<
	
all: $(TARGET) 
	$(CC) 

clean:
	rm -f $(ODIR)/*.o $(TARGET) $(LDIR)/*.so
